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

import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Presentation MathML to {@link FormulaNode}.
 * <p>
 * MathML is only read, so it needs no schema bindings at all: a plain DOM walk
 * is enough and not a single {@code .xsb} has to be shipped for it. The output
 * is the same syntax tree the LaTeX front end produces, which is what keeps one
 * OMML emitter, one style injection point and one fallback path.
 * <p>
 * Only Presentation MathML is supported; Content MathML ({@code <apply>}) is a
 * different semantic model and is rejected.
 *
 * @author Sayi
 */
final class MathMlParser {

    private static final String MATHML_NS = "http://www.w3.org/1998/Math/MathML";

    /** U+2061 FUNCTION APPLICATION, written between a function name and its argument. */
    private static final String FUNCTION_APPLICATION = "\u2061";

    private static final String[] XML_ENTITIES = { "amp", "lt", "gt", "quot", "apos" };

    /** Named entities that matter in formulas; XML itself defines none of them. */
    private static final Map<String, String> ENTITIES;

    /** Visible accent mark to combining character. */
    private static final Map<String, String> VISIBLE_ACCENTS;

    /** Marks drawn as a bar rather than as a raised accent glyph. */
    private static final Set<String> BAR_MARKS;

    private static final Set<String> BIG_OPERATOR_CHARS;

    /**
     * Opening delimiter to its closing counterpart. A pair of {@code <mo>} fences
     * is what tools emit for parentheses, so pairing them gives Word an
     * {@code m:d} that grows with its content instead of literal glyphs.
     * <p>
     * {@code |} is deliberately absent: it is its own closer, which would make
     * nesting ambiguous.
     */
    private static final Map<String, String> FENCES;

    private static final Pattern NAMED_ENTITY = Pattern.compile("&([A-Za-z][A-Za-z0-9]*);");

    private static final Pattern WIDTH = Pattern.compile("^\\s*(-?[0-9]*\\.?[0-9]+)\\s*([a-z%]*)\\s*$");

    private static final Pattern MATH_ROOT = Pattern.compile("^<\\s*([A-Za-z_][\\w.-]*:)?math[\\s>/]");

    static {
        Map<String, String> entities = new HashMap<String, String>();
        entities.put("InvisibleTimes", "&#x2062;");
        entities.put("ApplyFunction", "&#x2061;");
        entities.put("InvisibleComma", "&#x2063;");
        entities.put("InvisibleSeparator", "&#x2063;");
        entities.put("ThinSpace", "&#x2009;");
        entities.put("MediumSpace", "&#x2005;");
        entities.put("ThickSpace", "&#x2004;");
        entities.put("VeryThinSpace", "&#x200A;");
        entities.put("NegativeThinSpace", "&#x200B;");
        entities.put("nbsp", "&#xA0;");
        entities.put("minus", "&#x2212;");
        entities.put("plusmn", "&#x00B1;");
        entities.put("times", "&#x00D7;");
        entities.put("divide", "&#x00F7;");
        entities.put("middot", "&#x00B7;");
        entities.put("le", "&#x2264;");
        entities.put("ge", "&#x2265;");
        entities.put("ne", "&#x2260;");
        entities.put("equiv", "&#x2261;");
        entities.put("asymp", "&#x2248;");
        entities.put("infin", "&#x221E;");
        entities.put("sum", "&#x2211;");
        entities.put("prod", "&#x220F;");
        entities.put("int", "&#x222B;");
        entities.put("part", "&#x2202;");
        entities.put("nabla", "&#x2207;");
        entities.put("radic", "&#x221A;");
        entities.put("rarr", "&#x2192;");
        entities.put("larr", "&#x2190;");
        entities.put("harr", "&#x2194;");
        entities.put("rArr", "&#x21D2;");
        entities.put("hArr", "&#x21D4;");
        entities.put("pi", "&#x03C0;");
        entities.put("theta", "&#x03B8;");
        entities.put("lambda", "&#x03BB;");
        entities.put("mu", "&#x03BC;");
        entities.put("sigma", "&#x03C3;");
        entities.put("phi", "&#x03D5;");
        entities.put("omega", "&#x03C9;");
        entities.put("Delta", "&#x0394;");
        entities.put("Omega", "&#x03A9;");
        entities.put("hellip", "&#x2026;");
        entities.put("prime", "&#x2032;");
        entities.put("deg", "&#x00B0;");
        entities.put("lowast", "&#x2217;");
        entities.put("cong", "&#x2245;");
        entities.put("prop", "&#x221D;");
        entities.put("forall", "&#x2200;");
        entities.put("exist", "&#x2203;");
        entities.put("isin", "&#x2208;");
        entities.put("notin", "&#x2209;");
        entities.put("cup", "&#x222A;");
        entities.put("cap", "&#x2229;");
        entities.put("sub", "&#x2282;");
        entities.put("sup", "&#x2283;");
        entities.put("empty", "&#x2205;");
        ENTITIES = Collections.unmodifiableMap(entities);

        Map<String, String> accents = new HashMap<String, String>();
        accents.put("^", "\u0302");
        accents.put("\u02c6", "\u0302");
        accents.put("~", "\u0303");
        accents.put("\u02dc", "\u0303");
        accents.put("\u00af", "\u0304");
        accents.put("\u203e", "\u0304");
        accents.put("\u2192", "\u20d7");
        accents.put("\u00b7", "\u0307");
        accents.put(".", "\u0307");
        accents.put("\u00a8", "\u0308");
        accents.put("\u00b4", "\u0301");
        accents.put("`", "\u0300");
        accents.put("\u02c7", "\u030c");
        accents.put("\u02d8", "\u0306");
        accents.put("\u02dd", "\u030b");
        accents.put("\u2227", "\u0302");
        accents.put("\u2228", "\u030c");
        VISIBLE_ACCENTS = Collections.unmodifiableMap(accents);

        Set<String> bars = new HashSet<String>();
        bars.add("_");
        bars.add("\u0332");
        bars.add("\u00af");
        bars.add("\u203e");
        bars.add("\u0304");
        bars.add("\u0305");
        BAR_MARKS = Collections.unmodifiableSet(bars);

        BIG_OPERATOR_CHARS = Collections.unmodifiableSet(new HashSet<String>(LatexSymbols.BIG_OPERATORS.values()));

        Map<String, String> fences = new HashMap<String, String>();
        fences.put("(", ")");
        fences.put("[", "]");
        fences.put("{", "}");
        fences.put("\u27e8", "\u27e9");
        fences.put("\u2308", "\u2309");
        fences.put("\u230a", "\u230b");
        fences.put("\u27e6", "\u27e7");
        FENCES = Collections.unmodifiableMap(fences);
    }

    private MathMlParser() {
    }

    static FormulaNode parse(String source) {
        if (null == source || source.trim().isEmpty()) {
            throw new IllegalArgumentException("MathML source is empty");
        }
        Element root = document(source);
        requireMathMl(root);
        if ("math".equals(localName(root))) return sequence(children(root));
        if ("apply".equals(localName(root)) || "lambda".equals(localName(root))) {
            throw new IllegalArgumentException("Content MathML is not supported; use Presentation MathML");
        }
        return convert(root);
    }

    // ────────────────────────────── parsing ──────────────────────────────

    private static Element document(String source) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setExpandEntityReferences(false);
        factory.setXIncludeAware(false);
        // Formula text is untrusted input: no DTD, no external entities.
        feature(factory, "http://apache.org/xml/features/disallow-doctype-decl", true);
        feature(factory, "http://xml.org/sax/features/external-general-entities", false);
        feature(factory, "http://xml.org/sax/features/external-parameter-entities", false);
        try {
            Document parsed = factory.newDocumentBuilder()
                    .parse(new InputSource(new StringReader(asDocument(source))));
            return parsed.getDocumentElement();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid MathML: " + e.getMessage(), e);
        }
    }

    /**
     * A MathML fragment has several top level elements and is not a well formed
     * document on its own, so it gets the {@code <math>} root it implies.
     */
    private static String asDocument(String source) {
        String prepared = substituteEntities(source).trim();
        if (MATH_ROOT.matcher(prepared).find()) return prepared;
        return "<math xmlns=\"" + MATHML_NS + "\">" + prepared + "</math>";
    }

    private static void feature(DocumentBuilderFactory factory, String name, boolean value) {
        try {
            factory.setFeature(name, value);
        } catch (Exception ignored) {
            // best effort: the JDK parser supports these, others may not
        }
    }

    /**
     * Replaces the named entities that show up in real formulas. Anything else is
     * reported instead of left to the XML parser, because XML defines only five
     * predefined entities and a bare "entity not defined" would be a mystery.
     */
    private static String substituteEntities(String source) {
        String prepared = source;
        for (Map.Entry<String, String> entity : ENTITIES.entrySet()) {
            prepared = prepared.replace("&" + entity.getKey() + ";", entity.getValue());
        }
        Matcher matcher = NAMED_ENTITY.matcher(prepared);
        while (matcher.find()) {
            String name = matcher.group(1);
            if (!isPredefinedEntity(name)) {
                throw new IllegalArgumentException("Unknown entity '&" + name
                        + ";' in MathML: named entities are not defined in XML,"
                        + " use a numeric reference such as '&#x2062;'");
            }
        }
        return prepared;
    }

    private static boolean isPredefinedEntity(String name) {
        for (String known : XML_ENTITIES) {
            if (known.equals(name)) return true;
        }
        return false;
    }

    private static void requireMathMl(Element element) {
        String namespace = element.getNamespaceURI();
        if (null != namespace && !namespace.isEmpty() && !MATHML_NS.equals(namespace)) {
            throw new IllegalArgumentException("Unsupported MathML namespace: " + namespace);
        }
    }

    // ────────────────────────────── mapping ──────────────────────────────

    /**
     * Converts a sibling list, resolving the two lookahead rules of MathML:
     * function application, and the operand of a large operator.
     */
    private static FormulaNode sequence(List<Element> raw) {
        List<Element> elements = collapseFences(raw);
        List<FormulaNode> items = new ArrayList<FormulaNode>();
        int index = 0;
        while (index < elements.size()) {
            Element element = elements.get(index);
            if (isFunctionName(element)) {
                int argument = skipApplication(elements, index + 1);
                if (argument < elements.size()) {
                    items.add(new FormulaNode.Function(functionName(element), convert(elements.get(argument))));
                    index = argument + 1;
                    continue;
                }
            }
            FormulaNode node = convert(element);
            // mrow (and mspace, none, mtd) exist only to group: flatten them, or the
            // tree gains a level for every group the author wrote.
            if (node instanceof FormulaNode.Row) {
                items.addAll(((FormulaNode.Row) node).getItems());
                index++;
                continue;
            }
            if (node instanceof FormulaNode.BigOperator) {
                FormulaNode.BigOperator operator = (FormulaNode.BigOperator) node;
                int operand = skipApplication(elements, index + 1);
                if (null == operator.getOperand() && operand < elements.size()) {
                    items.add(new FormulaNode.BigOperator(operator.getCharacter(), operator.isUnderOver(),
                            operator.getSubscript(), operator.getSuperscript(), convert(elements.get(operand))));
                    index = operand + 1;
                    continue;
                }
            }
            items.add(node);
            index++;
        }
        return new FormulaNode.Row(items);
    }

    /**
     * Replaces each balanced pair of {@code <mo>} fences with one synthetic
     * {@code <mfenced>}, so the rest of the mapping only has to know one shape.
     * A lone fence directly in front of a table is the MathML spelling of
     * {@code \begin{cases}} / {@code pmatrix} and is folded too.
     */
    private static List<Element> collapseFences(List<Element> elements) {
        List<Element> collapsed = new ArrayList<Element>();
        int index = 0;
        while (index < elements.size()) {
            Element element = elements.get(index);
            String open = openingFence(element);
            if (null == open) {
                collapsed.add(element);
                index++;
                continue;
            }
            int close = closingFence(elements, index, open);
            Element following = close < 0 ? elementAt(elements, index + 1) : null;
            if (close < 0 && (null == following || !"mtable".equals(localName(following)))) {
                collapsed.add(element);
                index++;
                continue;
            }
            // a lone fence folds together with the table right behind it
            collapsed.add(fenced(elements, index, close < 0 ? index + 2 : close, open,
                    close < 0 ? "" : FENCES.get(open)));
            index = close < 0 ? index + 2 : close + 1;
        }
        return collapsed;
    }

    /**
     * @return the synthetic {@code <mfenced>} holding the enclosed elements; the
     *         enclosed nodes are moved into it, which is what makes the rest of
     *         the mapping see a single, ordinary fence
     */
    private static Element fenced(List<Element> elements, int from, int to, String open, String close) {
        Element fence = elements.get(from).getOwnerDocument().createElementNS(MATHML_NS, "mfenced");
        fence.setAttribute("open", open);
        fence.setAttribute("close", close);
        // no separator: the enclosed elements are one expression, not a list
        fence.setAttribute("separators", "");
        for (int index = from + 1; index < to; index++) {
            fence.appendChild(elements.get(index));
        }
        return fence;
    }

    private static String openingFence(Element element) {
        if (!"mo".equals(localName(element))) return null;
        String value = trimmed(element.getTextContent());
        return FENCES.containsKey(value) ? value : null;
    }

    /**
     * @return the index of the matching closing fence, or -1 when the fence is
     *         never closed (or the nesting crosses, in which case the pair is left
     *         alone rather than guessed at)
     */
    private static int closingFence(List<Element> elements, int from, String open) {
        Deque<String> expected = new ArrayDeque<String>();
        expected.push(FENCES.get(open));
        for (int index = from + 1; index < elements.size(); index++) {
            Element element = elements.get(index);
            if (!"mo".equals(localName(element))) continue;
            String value = trimmed(element.getTextContent());
            String nested = FENCES.get(value);
            if (null != nested) {
                expected.push(nested);
                continue;
            }
            if (!FENCES.containsValue(value)) continue;
            if (!expected.peek().equals(value)) return -1;
            expected.pop();
            if (expected.isEmpty()) return index;
        }
        return -1;
    }

    private static FormulaNode convert(Element element) {
        String name = localName(element);
        if ("mrow".equals(name) || "math".equals(name)) return sequence(children(element));
        if ("semantics".equals(name)) return semantics(element);

        if ("mi".equals(name)) return token(element);
        if ("mn".equals(name) || "mo".equals(name)) return token(element);
        if ("mtext".equals(name) || "ms".equals(name)) return new FormulaNode.Text(text(element), true);
        if ("mspace".equals(name)) return new FormulaNode.Row(spaces(attribute(element, "width")));

        if ("mfrac".equals(name)) return fraction(element);
        if ("msqrt".equals(name)) {
            // msqrt takes (MathML:any)*, so the whole content is one radicand
            requireArguments(element, 1);
            return new FormulaNode.Radical(sequence(children(element)), null);
        }
        if ("mroot".equals(name)) return radical(element);

        if ("msub".equals(name)) return script(element, true, false);
        if ("msup".equals(name)) return script(element, false, true);
        if ("msubsup".equals(name)) return script(element, true, true);
        if ("mmultiscripts".equals(name)) return multiScripts(element);

        if ("munder".equals(name) || "mover".equals(name) || "munderover".equals(name)) return underOver(element);

        if ("mfenced".equals(name)) return fence(element);
        if ("mtable".equals(name)) return table(element);
        if ("mstyle".equals(name)) return styled(element);

        if ("mpadded".equals(name)) return new FormulaNode.Box(sequence(children(element)));
        if ("mphantom".equals(name)) return new FormulaNode.Phantom(sequence(children(element)));
        if ("menclose".equals(name)) return enclose(element);

        if ("mtd".equals(name) || "mlabeledtr".equals(name)) return sequence(children(element));
        if ("none".equals(name) || "maligngroup".equals(name) || "malignmark".equals(name)) return empty();
        if ("apply".equals(name) || "ci".equals(name) || "cn".equals(name) || "csymbol".equals(name)) {
            throw new IllegalArgumentException("Content MathML is not supported; use Presentation MathML");
        }
        throw new IllegalArgumentException("Unsupported MathML element '<" + name + ">'");
    }

    private static FormulaNode token(Element element) {
        String name = localName(element);
        String value = trimmed(element.getTextContent());
        if ("mo".equals(name) && LatexSymbols.OPERATOR_NAMES.contains(value)) {
            return new FormulaNode.OperatorName(value);
        }
        if (value.isEmpty()) {
            return empty();
        }
        String variant = attribute(element, "mathvariant");
        // A multi character identifier is a name, not a product of variables.
        boolean upright = value.length() > 1 || "normal".equals(variant);
        return variant(text(value, upright), variant);
    }

    /**
     * Wraps a token in the style its {@code mathvariant} asks for. OMML keeps the
     * emphasis ({@code m:sty}) and the font variant ({@code m:scr}) apart, so
     * {@code bold-sans-serif} becomes both.
     */
    private static FormulaNode variant(FormulaNode body, String variant) {
        if (null == variant) return body;
        FormulaNode.MathStyle emphasis = emphasis(variant);
        FormulaNode.MathScript script = mathScript(variant);
        if (null == emphasis && null == script) return body;
        return new FormulaNode.Styled(emphasis, script, body);
    }

    private static FormulaNode.MathStyle emphasis(String variant) {
        if (variant.contains("bold")) {
            return variant.contains("italic") ? FormulaNode.MathStyle.BOLD_ITALIC : FormulaNode.MathStyle.BOLD;
        }
        if (variant.contains("italic")) return FormulaNode.MathStyle.ITALIC;
        return null;
    }

    private static FormulaNode.MathScript mathScript(String variant) {
        if (variant.contains("double-struck")) return FormulaNode.MathScript.DOUBLE_STRUCK;
        if (variant.contains("fraktur")) return FormulaNode.MathScript.FRAKTUR;
        if (variant.contains("script")) return FormulaNode.MathScript.SCRIPT;
        if (variant.contains("sans-serif")) return FormulaNode.MathScript.SANS_SERIF;
        if (variant.contains("monospace")) return FormulaNode.MathScript.MONOSPACE;
        return null;
    }

    private static FormulaNode text(String value, boolean upright) {
        return new FormulaNode.Text(value, upright);
    }

    private static FormulaNode fraction(Element element) {
        requireArguments(element, 2);
        List<Element> kids = children(element);
        boolean bevelled = "true".equals(attribute(element, "bevelled"));
        return new FormulaNode.Fraction(argument(kids, 2, 0), argument(kids, 2, 1), bevelled);
    }

    private static FormulaNode radical(Element element) {
        requireArguments(element, 2);
        List<Element> kids = children(element);
        return new FormulaNode.Radical(argument(kids, 2, 0), argument(kids, 2, 1));
    }

    private static FormulaNode script(Element element, boolean subscript, boolean superscript) {
        int arity = subscript && superscript ? 3 : 2;
        requireArguments(element, arity);
        List<Element> kids = children(element);
        Element baseElement = elementAt(kids, 0);
        String baseText = null == baseElement ? "" : trimmed(baseElement.getTextContent());
        FormulaNode lower = subscript ? argument(kids, arity, 1) : null;
        FormulaNode upper = superscript ? argument(kids, arity, subscript ? 2 : 1) : null;
        // <msubsup><mo>&#x222B;</mo>..</msubsup> is how MathML writes an integral with
        // its limits; msub/msup ask for side scripts, which is m:nary with subSup.
        if (BIG_OPERATOR_CHARS.contains(baseText)) {
            return new FormulaNode.BigOperator(baseText, false, lower, upper, null);
        }
        return new FormulaNode.Script(argument(kids, arity, 0), lower, upper);
    }

    private static FormulaNode underOver(Element element) {
        String name = localName(element);
        int arity = "munderover".equals(name) ? 3 : 2;
        requireArguments(element, arity);
        List<Element> kids = children(element);
        boolean hasUnder = "munder".equals(name) || "munderover".equals(name);
        boolean hasOver = "mover".equals(name) || "munderover".equals(name);
        Element baseElement = elementAt(kids, 0);
        Element underElement = hasUnder ? elementAt(kids, 1) : null;
        Element overElement = hasOver ? elementAt(kids, hasUnder ? 2 : 1) : null;
        FormulaNode base = argument(kids, arity, 0);

        String baseText = null == baseElement ? "" : trimmed(baseElement.getTextContent());
        // A large operator carries its limits inside m:nary, not as a limit element.
        if (BIG_OPERATOR_CHARS.contains(baseText)) {
            return new FormulaNode.BigOperator(baseText, true, optional(underElement), optional(overElement), null);
        }
        Element mark = hasOver ? overElement : underElement;
        if (isAccent(element) && null != mark) {
            return accent(mark, base, hasOver);
        }
        FormulaNode body = LatexSymbols.OPERATOR_NAMES.contains(baseText)
                ? new FormulaNode.OperatorName(baseText)
                : base;
        if (hasUnder && hasOver) {
            body = new FormulaNode.Limit(false, body, optional(underElement));
            return new FormulaNode.Limit(true, body, optional(overElement));
        }
        return new FormulaNode.Limit(hasOver, body, optional(hasOver ? overElement : underElement));
    }

    private static boolean isAccent(Element element) {
        String name = localName(element);
        if ("mover".equals(name)) return "true".equals(attribute(element, "accent"));
        if ("munder".equals(name)) return "true".equals(attribute(element, "accentunder"));
        return false;
    }

    private static FormulaNode accent(Element markElement, FormulaNode base, boolean over) {
        String mark = trimmed(markElement.getTextContent());
        if ("\u23de".equals(mark) || "\u23df".equals(mark)) {
            return new FormulaNode.GroupChar(mark, over, base);
        }
        if (BAR_MARKS.contains(mark)) {
            return new FormulaNode.Bar(over, base);
        }
        String combining = VISIBLE_ACCENTS.get(mark);
        return new FormulaNode.Accent(null == combining ? mark : combining, base);
    }

    private static FormulaNode multiScripts(Element element) {
        List<Element> kids = children(element);
        int prescripts = kids.size();
        for (int i = 0; i < kids.size(); i++) {
            if ("mprescripts".equals(localName(kids.get(i)))) prescripts = i;
        }
        FormulaNode result = at(kids, 0);
        FormulaNode preSub = pair(kids, prescripts + 1, 0, kids.size());
        FormulaNode preSup = pair(kids, prescripts + 1, 1, kids.size());
        if (null != preSub || null != preSup) {
            result = new FormulaNode.PreScript(result, preSub, preSup);
        }
        FormulaNode sub = pair(kids, 1, 0, prescripts);
        FormulaNode sup = pair(kids, 1, 1, prescripts);
        if (null != sub || null != sup) {
            result = new FormulaNode.Script(result, sub, sup);
        }
        return result;
    }

    /** Reads the {@code offset}-th script of a (sub, sup) pair, or null when absent. */
    private static FormulaNode pair(List<Element> kids, int from, int offset, int end) {
        int index = from + offset;
        return index < end ? convert(kids.get(index)) : null;
    }

    private static FormulaNode fence(Element element) {
        List<Element> kids = children(element);
        String open = attribute(element, "open", "(");
        String close = attribute(element, "close", ")");
        // One table between delimiters is a matrix; behind a brace it is the MathML
        // spelling of \begin{cases}, which OMML models as an equation array.
        if (1 == kids.size() && "mtable".equals(localName(kids.get(0)))) {
            return new FormulaNode.Delimiter(open, close, table(kids.get(0), "{".equals(open)));
        }
        String separators = element.hasAttribute("separators") ? element.getAttribute("separators") : ",";
        // An empty separator means the children are one expression, not a list.
        if (separators.isEmpty()) {
            return new FormulaNode.Delimiter(open, close, sequence(kids));
        }
        if (1 == kids.size()) {
            return new FormulaNode.Delimiter(open, close, convert(kids.get(0)));
        }
        List<FormulaNode> arguments = new ArrayList<FormulaNode>();
        for (Element kid : kids) {
            arguments.add(convert(kid));
        }
        return new FormulaNode.Delimiter(open, close, separators.substring(0, 1), arguments);
    }

    private static FormulaNode table(Element element) {
        return table(element, false);
    }

    private static FormulaNode table(Element element, boolean equationArray) {
        List<List<FormulaNode>> rows = new ArrayList<List<FormulaNode>>();
        for (Element row : children(element)) {
            if (!"mtr".equals(localName(row))) continue;
            List<FormulaNode> cells = new ArrayList<FormulaNode>();
            for (Element cell : children(row)) {
                cells.add(convert(cell));
            }
            rows.add(cells);
        }
        return new FormulaNode.Matrix(rows, equationArray, columnSpec(element, rows));
    }

    private static String columnSpec(Element table, List<List<FormulaNode>> rows) {
        if (!table.hasAttribute("columnalign")) return "";
        String[] aligns = table.getAttribute("columnalign").trim().split("\\s+");
        int columns = rows.isEmpty() ? aligns.length : rows.get(0).size();
        StringBuilder spec = new StringBuilder();
        for (int i = 0; i < columns; i++) {
            String align = aligns[1 == aligns.length ? 0 : Math.min(i, aligns.length - 1)];
            if ("left".equals(align)) spec.append('l');
            else if ("right".equals(align)) spec.append('r');
            else spec.append('c');
        }
        return spec.toString();
    }

    private static FormulaNode styled(Element element) {
        FormulaNode body = sequence(children(element));
        // here "normal" must be forced onto the whole subtree, unlike on a token,
        // where the upright flag of the text already covers it
        String variant = attribute(element, "mathvariant");
        return "normal".equals(variant) ? new FormulaNode.Styled(FormulaNode.MathStyle.PLAIN, body)
                : variant(body, variant);
    }

    private static FormulaNode enclose(Element element) {
        String notation = attribute(element, "notation", "longdiv");
        FormulaNode body = sequence(children(element));
        if ("box".equals(notation) || "roundedbox".equals(notation)) return new FormulaNode.BorderBox(body);
        if ("top".equals(notation)) return new FormulaNode.Bar(true, body);
        if ("bottom".equals(notation)) return new FormulaNode.Bar(false, body);
        throw new IllegalArgumentException("MathML <menclose notation=\"" + notation
                + "\"> has no OMML equivalent; use notation=\"box\", \"top\" or \"bottom\"");
    }

    /**
     * Uses the presentation tree, which is what the author sees. Only when there
     * is none does it fall back to a TeX annotation, which is better than failing.
     */
    private static FormulaNode semantics(Element element) {
        List<Element> kids = children(element);
        for (Element kid : kids) {
            String name = localName(kid);
            if (!"annotation".equals(name) && !"annotation-xml".equals(name)) {
                return convert(kid);
            }
        }
        for (Element kid : kids) {
            String name = localName(kid);
            if (("annotation".equals(name) || "annotation-xml".equals(name))
                    && "application/x-tex".equalsIgnoreCase(attribute(kid, "encoding"))) {
                return LatexParser.parse(kid.getTextContent());
            }
        }
        throw new IllegalArgumentException("MathML <semantics> without a presentation tree");
    }

    // ────────────────────────────── helpers ──────────────────────────────

    /**
     * Too few children is a typo, not a formula.
     * <p>
     * Too many is not: MathML lets an element carry more children than it has
     * arguments, and the surplus belongs to the <em>last</em> argument as an
     * inferred mrow - {@code <msqrt>x+1</msqrt>} is the everyday example.
     */
    private static void requireArguments(Element element, int arity) {
        int actual = children(element).size();
        if (actual < arity) {
            throw new IllegalArgumentException("MathML <" + localName(element) + "> needs at least " + arity
                    + (1 == arity ? " child element" : " child elements") + ", found " + actual);
        }
    }

    /**
     * @param index argument position; the last one absorbs any surplus children
     */
    private static FormulaNode argument(List<Element> kids, int arity, int index) {
        if (index < arity - 1 || kids.size() <= arity) return at(kids, index);
        return sequence(kids.subList(index, kids.size()));
    }

    private static List<FormulaNode> spaces(String width) {
        if (null == width || width.isEmpty()) return Collections.emptyList();
        Matcher matcher = WIDTH.matcher(width);
        if (!matcher.matches()) return Collections.emptyList();
        double value = Double.parseDouble(matcher.group(1));
        if (value <= 0) return Collections.emptyList();
        List<FormulaNode> spaces = new ArrayList<FormulaNode>();
        if ("em".equals(matcher.group(2)) && value >= 1) {
            for (int i = 0; i < (int) Math.round(value); i++) {
                spaces.add(new FormulaNode.Text("\u2003", false));
            }
        } else {
            spaces.add(new FormulaNode.Text("\u2005", false));
        }
        return spaces;
    }

    private static boolean isFunctionName(Element element) {
        String name = localName(element);
        return ("mi".equals(name) || "mo".equals(name))
                && LatexSymbols.FUNCTIONS.contains(trimmed(element.getTextContent()));
    }

    private static String functionName(Element element) {
        return trimmed(element.getTextContent());
    }

    /** U+2061 sits between a function name and its argument and is not drawn. */
    private static int skipApplication(List<Element> elements, int index) {
        while (index < elements.size()
                && FUNCTION_APPLICATION.equals(trimmed(elements.get(index).getTextContent()))) {
            index++;
        }
        return index;
    }

    private static List<Element> children(Element element) {
        List<Element> children = new ArrayList<Element>();
        NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                children.add((Element) node);
            }
        }
        return children;
    }

    private static Element elementAt(List<Element> elements, int index) {
        return index < elements.size() ? elements.get(index) : null;
    }

    private static FormulaNode at(List<Element> elements, int index) {
        return optional(elementAt(elements, index));
    }

    private static FormulaNode optional(Element element) {
        return null == element ? empty() : convert(element);
    }

    private static FormulaNode empty() {
        return new FormulaNode.Row(Collections.<FormulaNode>emptyList());
    }

    private static String localName(Element element) {
        String name = element.getLocalName();
        if (null != name) return name;
        String tag = element.getTagName();
        int colon = tag.indexOf(':');
        return colon < 0 ? tag : tag.substring(colon + 1);
    }

    private static String attribute(Element element, String name) {
        return element.hasAttribute(name) ? element.getAttribute(name).trim() : null;
    }

    private static String attribute(Element element, String name, String fallback) {
        String value = attribute(element, name);
        return null == value ? fallback : value;
    }

    private static String trimmed(String value) {
        return null == value ? "" : value.trim();
    }

    private static String text(Element element) {
        return trimmed(element.getTextContent());
    }

}
