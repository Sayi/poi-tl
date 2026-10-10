/*
 * Copyright 2014-2026 Sayi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.plugin.math.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A recursive descent parser for the frozen LaTeX subset.
 * <p>
 * Everything outside the subset is rejected with a position and an actionable
 * message; nothing is ever skipped silently.
 *
 * @author Sayi
 */
public final class LatexParser {

    private static final int MAX_DEPTH = 32;

    private static final int STOP_NONE = 0;
    private static final int STOP_BRACE = 1;
    private static final int STOP_RIGHT = 2;
    private static final int STOP_CELL = 4;
    private static final int STOP_CLOSE = 8;

    /** Bare characters that open a delimiter. */
    private static final List<String> CHAR_OPEN = Arrays.asList("(", "[");

    /** Bare characters that close a delimiter. */
    private static final List<String> CHAR_CLOSE = Arrays.asList(")", "]");

    /** Pairs of opening and closing delimiters that must match. */
    private static final Map<String, String> DELIMITER_PAIRS;

    /** Environment name to opening, closing and kind (0 matrix, 1 equation array). */
    private static final Map<String, String[]> ENVIRONMENTS;

    private final String source;
    private final List<Token> tokens;
    private int position;
    private int depth;

    static {
        Map<String, String> pairs = new LinkedHashMap<String, String>();
        pairs.put("(", ")");
        pairs.put("[", "]");
        pairs.put("{", "}");
        pairs.put("lvert", "rvert");
        pairs.put("lVert", "rVert");
        pairs.put("langle", "rangle");
        pairs.put("lfloor", "rfloor");
        pairs.put("lceil", "rceil");
        DELIMITER_PAIRS = Collections.unmodifiableMap(pairs);

        Map<String, String[]> environments = new LinkedHashMap<String, String[]>();
        environments.put("matrix", new String[] { null, null, "0" });
        environments.put("pmatrix", new String[] { "(", ")", "0" });
        environments.put("bmatrix", new String[] { "[", "]", "0" });
        environments.put("Bmatrix", new String[] { "{", "}", "0" });
        environments.put("vmatrix", new String[] { "|", "|", "0" });
        environments.put("Vmatrix", new String[] { "\u2016", "\u2016", "0" });
        environments.put("cases", new String[] { "{", "", "1" });
        environments.put("array", new String[] { null, null, "0" });
        ENVIRONMENTS = Collections.unmodifiableMap(environments);
    }

    private LatexParser(String latex) {
        this.source = latex;
        this.tokens = tokenize(latex);
    }

    /**
     * @param latex LaTeX source
     * @return the syntax tree
     * @throws IllegalArgumentException when the source is outside the supported subset
     */
    public static FormulaNode parse(String latex) {
        if (null == latex) {
            throw new IllegalArgumentException("Latex content must not be null");
        }
        LatexParser parser = new LatexParser(latex);
        FormulaNode node = parser.parseRow(STOP_NONE);
        if (parser.position < parser.tokens.size()) {
            Token token = parser.peek();
            throw parser.error("Unexpected '" + token.display() + "'", token.position);
        }
        return node;
    }

    // ────────────────────────────── grammar ──────────────────────────────

    private FormulaNode.Row parseRow(int stop) {
        List<FormulaNode> items = new ArrayList<FormulaNode>();
        while (position < tokens.size() && !atStop(stop)) {
            items.add(parseItem(stop));
        }
        return new FormulaNode.Row(items);
    }

    private boolean atStop(int stop) {
        Token token = peek();
        if (null == token) return true;
        switch (token.kind) {
        case RBRACE:
            return (stop & STOP_BRACE) != 0;
        case AMP:
        case ROWBREAK:
            return (stop & STOP_CELL) != 0;
        case COMMAND:
            if ("right".equals(token.value)) return (stop & STOP_RIGHT) != 0;
            if ("end".equals(token.value)) return (stop & STOP_CELL) != 0;
            if (isClosingDelimiter(token)) return (stop & STOP_CLOSE) != 0;
            return false;
        case CHAR:
            return (stop & STOP_CLOSE) != 0 && CHAR_CLOSE.contains(token.value);
        default:
            return false;
        }
    }

    private FormulaNode parseItem(int stop) {
        FormulaNode atom = parseAtom(stop);
        FormulaNode subscript = null;
        FormulaNode superscript = null;
        while (true) {
            Token token = peek();
            if (null != token && Kind.UNDERSCORE == token.kind) {
                next();
                if (null != subscript) throw error("Duplicate subscript", token.position);
                subscript = parseScriptArgument();
            } else if (null != token && Kind.CARET == token.kind) {
                next();
                if (null != superscript) throw error("Duplicate superscript", token.position);
                superscript = parseScriptArgument();
            } else {
                break;
            }
        }
        if (atom instanceof FormulaNode.BigOperator) {
            FormulaNode.BigOperator operator = (FormulaNode.BigOperator) atom;
            FormulaNode operand = atStop(stop) ? emptyRow() : wrap(parseItem(stop));
            return new FormulaNode.BigOperator(operator.getCharacter(), operator.isUnderOver(), subscript,
                    superscript, operand);
        }
        if (atom instanceof FormulaNode.Function) {
            FormulaNode.Function function = (FormulaNode.Function) atom;
            FormulaNode argument = atStop(stop) ? emptyRow() : wrap(parseItem(stop));
            return new FormulaNode.Function(function.getName(), argument);
        }
        if (atom instanceof FormulaNode.OperatorName && null != subscript) {
            return new FormulaNode.Limit(false, atom, subscript);
        }
        if (null == subscript && null == superscript) return atom;
        return new FormulaNode.Script(atom, subscript, superscript);
    }

    private FormulaNode parseScriptArgument() {
        Token token = peek();
        if (null == token) throw error("Missing subscript or superscript body", source.length());
        if (Kind.LBRACE == token.kind) {
            return parseGroup();
        }
        return wrap(parseAtom(STOP_NONE));
    }

    private FormulaNode parseGroup() {
        Token brace = peek();
        if (null == brace || Kind.LBRACE != brace.kind) {
            throw error("Expected '{'", null == brace ? source.length() : brace.position);
        }
        next();
        enter(brace);
        FormulaNode.Row row = parseRow(STOP_BRACE);
        expect(Kind.RBRACE, "Unclosed '{'", brace.position);
        leave();
        return row;
    }

    private FormulaNode parseAtom(int stop) {
        Token token = peek();
        if (null == token) throw error("Unexpected end of input", source.length());
        switch (token.kind) {
        case LBRACE:
            return parseGroup();
        case RBRACE:
            throw error("Unmatched '}'", token.position);
        case CARET:
        case UNDERSCORE:
            throw error("'" + token.value + "' has no base expression", token.position);
        case AMP:
        case ROWBREAK:
            throw error("'" + token.display() + "' is only allowed inside a matrix, cases or array environment",
                    token.position);
        case CHAR:
            next();
            return parseCharacter(token);
        case COMMAND:
            return parseCommand(token, stop);
        default:
            throw error("Unexpected '" + token.display() + "'", token.position);
        }
    }

    /** The token has already been consumed by the caller. */
    private FormulaNode parseCharacter(Token token) {
        String character = token.value;
        if (CHAR_OPEN.contains(character)) {
            return parsePairedDelimiter(character, token);
        }
        if (CHAR_CLOSE.contains(character)) {
            throw error("Unmatched '" + character + "'", token.position);
        }
        if ("~".equals(character)) {
            return new FormulaNode.Text("\u00a0", false);
        }
        return new FormulaNode.Text(character, false);
    }

    private FormulaNode parseCommand(Token token, int stop) {
        String name = token.value;
        int at = token.position;
        next();

        if ("frac".equals(name) || "dfrac".equals(name) || "tfrac".equals(name)) {
            return new FormulaNode.Fraction(requiredGroup(name, at), requiredGroup(name, at));
        }
        if ("sqrt".equals(name)) {
            return parseRadical(at);
        }
        if ("left".equals(name)) {
            return parseLeftRight(at);
        }
        if ("begin".equals(name)) {
            return parseEnvironment(at);
        }
        if ("overline".equals(name)) {
            return new FormulaNode.Bar(true, requiredGroup(name, at));
        }
        if ("underline".equals(name)) {
            return new FormulaNode.Bar(false, requiredGroup(name, at));
        }
        if (LatexSymbols.ACCENTS.containsKey(name)) {
            return new FormulaNode.Accent(LatexSymbols.ACCENTS.get(name), requiredGroup(name, at));
        }
        if ("overset".equals(name) || "stackrel".equals(name)) {
            // \overset{top}{base}: the first group is the upper limit, the second the body
            FormulaNode top = requiredGroup(name, at);
            return new FormulaNode.Limit(true, requiredGroup(name, at), top);
        }
        if ("underset".equals(name)) {
            FormulaNode bottom = requiredGroup(name, at);
            return new FormulaNode.Limit(false, requiredGroup(name, at), bottom);
        }
        if ("binom".equals(name)) {
            FormulaNode numerator = requiredGroup(name, at);
            FormulaNode denominator = requiredGroup(name, at);
            return new FormulaNode.Delimiter("", "", new FormulaNode.Fraction(numerator, denominator));
        }
        if ("boxed".equals(name)) {
            return new FormulaNode.Box(requiredGroup(name, at));
        }
        if ("phantom".equals(name)) {
            return new FormulaNode.Phantom(requiredGroup(name, at));
        }
        if ("overbrace".equals(name)) {
            return new FormulaNode.GroupChar("\u23de", true, requiredGroup(name, at));
        }
        if ("underbrace".equals(name)) {
            return new FormulaNode.GroupChar("\u23df", false, requiredGroup(name, at));
        }
        if ("prescript".equals(name)) {
            // \prescript{pre-sup}{pre-sub}{base}, as in mathtools
            FormulaNode presuperscript = requiredGroup(name, at);
            FormulaNode presubscript = requiredGroup(name, at);
            return new FormulaNode.PreScript(requiredGroup(name, at), presubscript, presuperscript);
        }
        if ("text".equals(name)) {
            return parseText(at);
        }
        if (LatexSymbols.TEXT_STYLES.containsKey(name)) {
            return new FormulaNode.Styled(LatexSymbols.TEXT_STYLES.get(name), requiredGroup(name, at));
        }
        if (LatexSymbols.SCRIPT_STYLES.containsKey(name)) {
            return new FormulaNode.Styled(LatexSymbols.SCRIPT_STYLES.get(name), requiredGroup(name, at));
        }
        if ("displaystyle".equals(name) || "textstyle".equals(name)) {
            return emptyRow();
        }
        if (LatexSymbols.FUNCTIONS.contains(name)) {
            return new FormulaNode.Function(name, null);
        }
        if (LatexSymbols.OPERATOR_NAMES.contains(name)) {
            return new FormulaNode.OperatorName(name);
        }
        if (LatexSymbols.BIG_OPERATORS.containsKey(name)) {
            String character = LatexSymbols.BIG_OPERATORS.get(name);
            boolean underOver = LatexSymbols.LIMIT_OPERATORS.contains(name);
            return new FormulaNode.BigOperator(character, underOver, null, null, null);
        }
        if (LatexSymbols.SPACES.containsKey(name)) {
            return new FormulaNode.Text(LatexSymbols.SPACES.get(name), false);
        }
        if (DELIMITER_PAIRS.containsKey(name)) {
            return parsePairedDelimiter(name, token);
        }
        if (isClosingDelimiter(token) || "right".equals(name) || "end".equals(name)) {
            throw error("'" + token.display() + "' without matching opening", at);
        }
        if (LatexSymbols.CHARACTERS.containsKey(name)) {
            return new FormulaNode.Text(LatexSymbols.CHARACTERS.get(name), false);
        }
        throw error("Unsupported LaTeX command '\\" + name + "'." + unsupportedHint(name), at);
    }

    private FormulaNode parseRadical(int at) {
        FormulaNode degree = null;
        Token token = peek();
        if (null != token && Kind.CHAR == token.kind && "[".equals(token.value)) {
            next();
            FormulaNode.Row row = parseRow(STOP_CLOSE);
            Token close = peek();
            if (null == close || Kind.CHAR != close.kind || !"]".equals(close.value)) {
                throw error("'\\sqrt[' has no matching ']'", at);
            }
            next();
            degree = row;
        }
        return new FormulaNode.Radical(requiredGroup("sqrt", at), degree);
    }

    private FormulaNode parseLeftRight(int at) {
        String begin = readDelimiter("\\left", at);
        enterToken(at);
        FormulaNode.Row body = parseRow(STOP_RIGHT);
        Token right = peek();
        if (null == right || Kind.COMMAND != right.kind || !"right".equals(right.value)) {
            throw error("'\\left' has no matching '\\right'", at);
        }
        next();
        String end = readDelimiter("\\right", right.position);
        leave();
        return new FormulaNode.Delimiter(begin, end, body);
    }

    private String readDelimiter(String command, int at) {
        Token token = peek();
        if (null == token) throw error("'" + command + "' requires a delimiter", at);
        if (Kind.CHAR == token.kind && ".".equals(token.value)) {
            next();
            return "";
        }
        if (Kind.CHAR == token.kind) {
            next();
            return token.value;
        }
        if (Kind.COMMAND == token.kind && LatexSymbols.SYMBOL_DELIMITERS.containsKey(token.value)) {
            next();
            return LatexSymbols.SYMBOL_DELIMITERS.get(token.value);
        }
        if (Kind.COMMAND == token.kind
                && (DELIMITER_PAIRS.containsKey(token.value) || DELIMITER_PAIRS.containsValue(token.value))) {
            next();
            String value = token.value;
            if (DELIMITER_PAIRS.containsKey(value)) return openCharacter(value);
            if (DELIMITER_PAIRS.containsValue(value)) return closeCharacter(value);
        }
        throw error("'" + command + "' is followed by '" + token.display() + "', which is not a delimiter", at);
    }

    /** The opening token has already been consumed by the caller. */
    private FormulaNode parsePairedDelimiter(String openKey, Token at) {
        String expectedClose = DELIMITER_PAIRS.get(openKey);
        enter(at);
        FormulaNode.Row body = parseRow(STOP_CLOSE);
        Token close = peek();
        if (null == close || !isClosingDelimiter(close)) {
            throw error("'" + at.display() + "' has no matching closing delimiter", at.position);
        }
        String foundKey = closeKey(close);
        if (!expectedClose.equals(foundKey)) {
            throw error("Expected closing delimiter for '" + at.display() + "' but found '" + close.display() + "'",
                    close.position);
        }
        next();
        leave();
        return new FormulaNode.Delimiter(openCharacter(openKey), closeCharacter(foundKey), body);
    }

    private FormulaNode parseText(int at) {
        Token token = peek();
        if (null == token || Kind.RAW != token.kind) {
            throw error("'\\text' requires a '{...}' argument", at);
        }
        next();
        return new FormulaNode.Text(token.value, true);
    }


    private FormulaNode parseEnvironment(int at) {
        String name = readRawGroup();
        String[] environment = ENVIRONMENTS.get(name);
        if (null == environment) {
            throw error("Unsupported environment '" + name + "'; supported environments are "
                    + ENVIRONMENTS.keySet(), at);
        }
        String columnSpec = "array".equals(name) ? readRawGroup() : null;

        List<List<FormulaNode>> rows = new ArrayList<List<FormulaNode>>();
        List<FormulaNode> cells = new ArrayList<FormulaNode>();
        enterToken(at);
        while (true) {
            cells.add(parseRow(STOP_CELL));
            Token token = peek();
            if (null == token) {
                throw error("Environment '" + name + "' is not closed with '\\end{" + name + "}'", at);
            }
            if (Kind.AMP == token.kind) {
                next();
                continue;
            }
            if (Kind.ROWBREAK == token.kind) {
                next();
                skipRowBreakArgument();
                rows.add(cells);
                cells = new ArrayList<FormulaNode>();
                continue;
            }
            if (Kind.COMMAND == token.kind && "end".equals(token.value)) {
                next();
                String closed = readRawGroup();
                if (!name.equals(closed)) {
                    throw error("Environment '" + name + "' is closed by '\\end{" + closed + "}'", token.position);
                }
                rows.add(cells);
                break;
            }
            throw error("Unexpected '" + token.display() + "' inside environment '" + name + "'", token.position);
        }
        leave();

        boolean equationArray = "1".equals(environment[2]);
        FormulaNode matrix = new FormulaNode.Matrix(rows, equationArray, columnSpec);
        if (null == environment[0] && null == environment[1]) {
            return matrix;
        }
        return new FormulaNode.Delimiter(null == environment[0] ? "" : environment[0],
                null == environment[1] ? "" : environment[1], matrix);
    }

    /** {@code \\[2pt]} line spacing is accepted and ignored. */
    private void skipRowBreakArgument() {
        Token token = peek();
        if (null == token || Kind.CHAR != token.kind || !"[".equals(token.value)) return;
        next();
        while (position < tokens.size()) {
            Token current = next();
            if (Kind.CHAR == current.kind && "]".equals(current.value)) return;
        }
        throw error("'\\\\[' has no matching ']'", token.position);
    }

    private String readRawGroup() {
        Token brace = peek();
        if (null == brace || Kind.LBRACE != brace.kind) {
            throw error("Expected '{'", null == brace ? source.length() : brace.position);
        }
        next();
        StringBuilder builder = new StringBuilder();
        while (position < tokens.size()) {
            Token token = next();
            if (Kind.RBRACE == token.kind) return builder.toString();
            builder.append(token.kind == Kind.COMMAND ? token.value : token.value);
        }
        throw error("Unclosed '{'", brace.position);
    }

    private FormulaNode requiredGroup(String command, int at) {
        Token token = peek();
        if (null == token || Kind.LBRACE != token.kind) {
            throw error("Command '\\" + command + "' requires a '{...}' argument", at);
        }
        return parseGroup();
    }

    private boolean isClosingDelimiter(Token token) {
        if (Kind.CHAR == token.kind) return CHAR_CLOSE.contains(token.value);
        if (Kind.COMMAND == token.kind) return DELIMITER_PAIRS.containsValue(token.value);
        return false;
    }

    private String closeKey(Token token) {
        return token.value;
    }

    private String openCharacter(String key) {
        if ("(".equals(key)) return "(";
        if ("[".equals(key)) return "[";
        if ("{".equals(key)) return "{";
        if ("lvert".equals(key)) return "|";
        if ("lVert".equals(key)) return "\u2016";
        if ("langle".equals(key)) return "\u27e8";
        if ("lfloor".equals(key)) return "\u230a";
        if ("lceil".equals(key)) return "\u2308";
        throw new IllegalArgumentException("Unknown opening delimiter " + key);
    }

    private String closeCharacter(String key) {
        if (")".equals(key)) return ")";
        if ("]".equals(key)) return "]";
        if ("}".equals(key)) return "}";
        if ("rvert".equals(key)) return "|";
        if ("rVert".equals(key)) return "\u2016";
        if ("rangle".equals(key)) return "\u27e9";
        if ("rfloor".equals(key)) return "\u230b";
        if ("rceil".equals(key)) return "\u2309";
        throw new IllegalArgumentException("Unknown closing delimiter " + key);
    }

    // ────────────────────────────── helpers ──────────────────────────────

    /** Argument positions of OMML (m:e, m:sub, m:sup, ...) are always a Row. */
    private FormulaNode wrap(FormulaNode node) {
        return node instanceof FormulaNode.Row ? node : new FormulaNode.Row(Arrays.asList(node));
    }

    private FormulaNode.Row emptyRow() {
        return new FormulaNode.Row(Collections.<FormulaNode>emptyList());
    }

    private void enter(Token token) {
        enterToken(token.position);
    }

    private void enterToken(int at) {
        if (++depth > MAX_DEPTH) {
            throw error("Expression is nested deeper than " + MAX_DEPTH + " levels", at);
        }
    }

    private void leave() {
        depth--;
    }

    private Token peek() {
        return position < tokens.size() ? tokens.get(position) : null;
    }

    private Token next() {
        return tokens.get(position++);
    }

    private void expect(Kind kind, String message, int at) {
        if (position >= tokens.size() || tokens.get(position).kind != kind) {
            throw error(message, at);
        }
        next();
    }

    private IllegalArgumentException error(String message, int at) {
        return new IllegalArgumentException(message + " (at position " + at + ")");
    }

    private String unsupportedHint(String name) {
        if (Arrays.asList("newcommand", "def", "renewcommand", "DeclareMathOperator").contains(name)) {
            return " Macro definitions are not supported; inline the definition instead.";
        }
        if (Arrays.asList("label", "ref", "eqref", "cite", "tag").contains(name)) {
            return " Numbering and cross references belong to the template, not to the formula.";
        }
        if (Arrays.asList("over", "atop", "choose").contains(name)) {
            return " Use '\\frac{a}{b}' instead.";
        }
        if (Arrays.asList("color", "textcolor", "fbox").contains(name)) {
            return " Use the formula style (color) instead; \\boxed is supported.";
        }
        return "";
    }

    // ───────────────────────────── tokenizer ─────────────────────────────

    private enum Kind {
        COMMAND, RAW, LBRACE, RBRACE, CARET, UNDERSCORE, AMP, ROWBREAK, CHAR
    }

    private static final class Token {

        private final Kind kind;
        private final String value;
        private final int position;

        private Token(Kind kind, String value, int position) {
            this.kind = kind;
            this.value = value;
            this.position = position;
        }

        private String display() {
            return Kind.COMMAND == kind ? "\\" + value : value;
        }

    }

    private static List<Token> tokenize(String source) {
        List<Token> tokens = new ArrayList<Token>();
        int index = 0;
        while (index < source.length()) {
            char current = source.charAt(index);
            if (Character.isWhitespace(current)) {
                index++;
                continue;
            }
            int start = index;
            if ('\\' == current) {
                index = readCommand(source, index, tokens);
                continue;
            }
            switch (current) {
            case '{':
                tokens.add(new Token(Kind.LBRACE, "{", start));
                index++;
                continue;
            case '}':
                tokens.add(new Token(Kind.RBRACE, "}", start));
                index++;
                continue;
            case '^':
                tokens.add(new Token(Kind.CARET, "^", start));
                index++;
                continue;
            case '_':
                tokens.add(new Token(Kind.UNDERSCORE, "_", start));
                index++;
                continue;
            case '&':
                tokens.add(new Token(Kind.AMP, "&", start));
                index++;
                continue;
            default:
                break;
            }
            int codePoint = source.codePointAt(index);
            tokens.add(new Token(Kind.CHAR, new String(Character.toChars(codePoint)), start));
            index += Character.charCount(codePoint);
        }
        return tokens;
    }

    private static int readCommand(String source, int index, List<Token> tokens) {
        int start = index;
        if (index + 1 >= source.length()) {
            throw new IllegalArgumentException("Dangling '\\' at end of input (at position " + start + ")");
        }
        char next = source.charAt(index + 1);
        if ('\\' == next) {
            tokens.add(new Token(Kind.ROWBREAK, "\\\\", start));
            return index + 2;
        }
        if (!Character.isLetter(next)) {
            tokens.add(new Token(Kind.COMMAND, String.valueOf(next), start));
            return index + 2;
        }
        int end = index + 1;
        while (end < source.length() && Character.isLetter(source.charAt(end))) {
            end++;
        }
        String name = source.substring(index + 1, end);
        tokens.add(new Token(Kind.COMMAND, name, start));
        if (!"text".equals(name)) return end;

        int cursor = end;
        while (cursor < source.length() && Character.isWhitespace(source.charAt(cursor))) {
            cursor++;
        }
        if (cursor >= source.length() || '{' != source.charAt(cursor)) {
            throw new IllegalArgumentException("Command '\\text' requires a '{...}' argument (at position " + start + ")");
        }
        StringBuilder raw = new StringBuilder();
        int level = 1;
        cursor++;
        while (cursor < source.length() && level > 0) {
            char character = source.charAt(cursor);
            if ('\\' == character && cursor + 1 < source.length()) {
                raw.append(character).append(source.charAt(cursor + 1));
                cursor += 2;
                continue;
            }
            if ('{' == character) {
                level++;
            } else if ('}' == character) {
                level--;
                if (0 == level) {
                    cursor++;
                    break;
                }
            }
            raw.append(character);
            cursor++;
        }
        if (level > 0) {
            throw new IllegalArgumentException("Unclosed '{' after '\\text' (at position " + start + ")");
        }
        tokens.add(new Token(Kind.RAW, raw.toString(), start));
        return cursor;
    }

}
