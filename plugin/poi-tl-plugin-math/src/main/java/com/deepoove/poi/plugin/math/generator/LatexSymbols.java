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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The frozen LaTeX subset: the single source of truth of what the plugin
 * understands. Anything not listed here is rejected with an actionable error.
 *
 * @author Sayi
 */
final class LatexSymbols {

    /** Commands that map to a single character, emitted straight into {@code m:t}. */
    static final Map<String, String> CHARACTERS;

    /** Upright function names, emitted as {@code m:func} with an upright {@code m:fName}. */
    static final Set<String> FUNCTIONS;

    /** Upright names rendered as {@code m:r} (or {@code m:limLow} with a subscript). */
    static final Set<String> OPERATOR_NAMES;

    /** Large operators, emitted as {@code m:nary}. */
    static final Map<String, String> BIG_OPERATORS;

    /** Accents, emitted as {@code m:acc} with the combining character as {@code m:chr}. */
    static final Map<String, String> ACCENTS;

    /** Opening delimiters, keyed by command name, valued by the rendered character. */
    static final Map<String, String> OPEN_DELIMITERS;

    /** Closing delimiters. */
    static final Map<String, String> CLOSE_DELIMITERS;

    /** Commands taking no argument that only add horizontal space. */
    static final Map<String, String> SPACES;

    /** Emphasis commands: {@code \mathrm}, {@code \mathbf}, {@code \mathit}, {@code \boldsymbol}. */
    static final Map<String, FormulaNode.MathStyle> TEXT_STYLES;

    /** Double struck letters, only the eight codepoints without holes. */
    /** Font variant commands: {@code m:scr} in OMML. */
    static final Map<String, FormulaNode.MathScript> SCRIPT_STYLES;

    /**
     * Delimiters that are also ordinary characters, so they work both bare and
     * after {@code \left} / {@code \right}. Unlike {@code \lvert}, they must not
     * force the parser to look for a closing partner: {@code \{x \vert x>0\}} is a
     * set, not an unclosed delimiter.
     */
    static final Map<String, String> SYMBOL_DELIMITERS;

    /**
     * Large operators whose limits go above and below rather than beside
     * ({@code m:limLoc} {@code undOvr}); integrals keep theirs at the side.
     */
    static final Set<String> LIMIT_OPERATORS;

    private static final String[][] CHARACTER_PAIRS = {
            { "alpha", "\u03b1" }, { "beta", "\u03b2" }, { "gamma", "\u03b3" }, { "delta", "\u03b4" },
            { "epsilon", "\u03f5" }, { "varepsilon", "\u03b5" }, { "zeta", "\u03b6" }, { "eta", "\u03b7" },
            { "theta", "\u03b8" }, { "vartheta", "\u03d1" }, { "iota", "\u03b9" }, { "kappa", "\u03ba" },
            { "lambda", "\u03bb" }, { "mu", "\u03bc" }, { "nu", "\u03bd" }, { "xi", "\u03be" },
            { "pi", "\u03c0" }, { "varpi", "\u03d6" }, { "rho", "\u03c1" }, { "varrho", "\u03f1" },
            { "sigma", "\u03c3" }, { "varsigma", "\u03c2" }, { "tau", "\u03c4" }, { "upsilon", "\u03c5" },
            { "phi", "\u03d5" }, { "varphi", "\u03c6" }, { "chi", "\u03c7" }, { "psi", "\u03c8" },
            { "omega", "\u03c9" }, { "omicron", "\u03bf" },
            { "Gamma", "\u0393" }, { "Delta", "\u0394" }, { "Theta", "\u0398" }, { "Lambda", "\u039b" },
            { "Xi", "\u039e" }, { "Pi", "\u03a0" }, { "Sigma", "\u03a3" }, { "Upsilon", "\u03a5" },
            { "Phi", "\u03a6" }, { "Psi", "\u03a8" }, { "Omega", "\u03a9" },
            { "infty", "\u221e" }, { "partial", "\u2202" }, { "nabla", "\u2207" }, { "emptyset", "\u2205" },
            { "varnothing", "\u2205" }, { "ldots", "\u2026" }, { "cdots", "\u22ef" }, { "dots", "\u2026" },
            { "vdots", "\u22ee" }, { "ddots", "\u22f1" }, { "prime", "\u2032" }, { "degree", "\u00b0" },
            { "angle", "\u2220" }, { "triangle", "\u25b3" }, { "square", "\u25a1" }, { "perp", "\u22a5" },
            { "parallel", "\u2225" }, { "cong", "\u2245" }, { "simeq", "\u2243" }, { "sim", "\u223c" },
            { "approx", "\u2248" }, { "equiv", "\u2261" }, { "propto", "\u221d" }, { "ll", "\u226a" },
            { "gg", "\u226b" },
            { "pm", "\u00b1" }, { "mp", "\u2213" }, { "times", "\u00d7" }, { "div", "\u00f7" },
            { "cdot", "\u22c5" }, { "ast", "\u2217" }, { "star", "\u22c6" }, { "circ", "\u2218" },
            { "bullet", "\u2219" }, { "oplus", "\u2295" }, { "otimes", "\u2297" }, { "odot", "\u2299" },
            { "leq", "\u2264" }, { "le", "\u2264" }, { "geq", "\u2265" }, { "ge", "\u2265" },
            { "neq", "\u2260" }, { "ne", "\u2260" }, { "in", "\u2208" },
            { "notin", "\u2209" }, { "subset", "\u2282" }, { "subseteq", "\u2286" }, { "supset", "\u2283" },
            { "supseteq", "\u2287" }, { "cup", "\u222a" }, { "cap", "\u2229" }, { "setminus", "\u2216" },
            { "forall", "\u2200" }, { "exists", "\u2203" }, { "nexists", "\u2204" }, { "neg", "\u00ac" },
            { "land", "\u2227" }, { "lor", "\u2228" }, { "wedge", "\u2227" }, { "vee", "\u2228" },
            { "ni", "\u220b" }, { "mid", "\u2223" }, { "prec", "\u227a" }, { "succ", "\u227b" },
            { "ominus", "\u2296" }, { "oslash", "\u2298" }, { "therefore", "\u2234" },
            { "because", "\u2235" },
            { "to", "\u2192" }, { "rightarrow", "\u2192" }, { "leftarrow", "\u2190" },
            { "gets", "\u2190" }, { "longrightarrow", "\u27f6" }, { "longleftarrow", "\u27f5" },
            { "leftrightarrow", "\u2194" }, { "Rightarrow", "\u21d2" }, { "Leftarrow", "\u21d0" },
            { "Leftrightarrow", "\u21d4" }, { "iff", "\u27fa" }, { "implies", "\u27f9" },
            { "mapsto", "\u21a6" },
            { "uparrow", "\u2191" }, { "downarrow", "\u2193" } };

    static {
        Map<String, String> chars = new HashMap<String, String>();
        for (String[] pair : CHARACTER_PAIRS) {
            chars.put(pair[0], pair[1]);
        }
        // escaped literals
        chars.put("%", "%");
        chars.put("&", "&");
        chars.put("#", "#");
        chars.put("_", "_");
        chars.put("$", "$");
        chars.put("^", "^");
        chars.put("~", "~");
        chars.put("vert", "|");
        chars.put("Vert", "\u2016");
        chars.put("|", "\u2016");
        CHARACTERS = Collections.unmodifiableMap(chars);

        Set<String> functions = new HashSet<String>();
        Collections.addAll(functions, "sin", "cos", "tan", "cot", "sec", "csc", "arcsin", "arccos", "arctan",
                "sinh", "cosh", "tanh", "log", "ln", "lg", "exp");
        FUNCTIONS = Collections.unmodifiableSet(functions);

        Set<String> names = new HashSet<String>();
        Collections.addAll(names, "lim", "max", "min", "sup", "inf", "det", "gcd", "dim", "ker", "deg", "Pr");
        OPERATOR_NAMES = Collections.unmodifiableSet(names);

        Map<String, String> big = new HashMap<String, String>();
        big.put("sum", "\u2211");
        big.put("prod", "\u220f");
        big.put("coprod", "\u2210");
        big.put("int", "\u222b");
        big.put("iint", "\u222c");
        big.put("iiint", "\u222d");
        big.put("oint", "\u222e");
        big.put("bigcup", "\u22c3");
        big.put("bigcap", "\u22c2");
        big.put("bigoplus", "\u2a01");
        big.put("bigotimes", "\u2a02");
        big.put("bigodot", "\u2a00");
        BIG_OPERATORS = Collections.unmodifiableMap(big);

        Map<String, String> symbolDelimiters = new HashMap<String, String>();
        symbolDelimiters.put("vert", "|");
        symbolDelimiters.put("Vert", "\u2016");
        symbolDelimiters.put("|", "\u2016");
        SYMBOL_DELIMITERS = Collections.unmodifiableMap(symbolDelimiters);

        Set<String> limits = new HashSet<String>();
        limits.add("sum");
        limits.add("prod");
        limits.add("coprod");
        limits.add("bigcup");
        limits.add("bigcap");
        limits.add("bigoplus");
        limits.add("bigotimes");
        limits.add("bigodot");
        LIMIT_OPERATORS = Collections.unmodifiableSet(limits);

        Map<String, String> accents = new HashMap<String, String>();
        accents.put("hat", "\u0302");
        accents.put("widehat", "\u0302");
        accents.put("bar", "\u0304");
        accents.put("vec", "\u20d7");
        accents.put("dot", "\u0307");
        accents.put("ddot", "\u0308");
        accents.put("tilde", "\u0303");
        accents.put("acute", "\u0301");
        accents.put("grave", "\u0300");
        accents.put("check", "\u030c");
        accents.put("breve", "\u0306");
        ACCENTS = Collections.unmodifiableMap(accents);

        Map<String, String> open = new HashMap<String, String>();
        open.put("(", "(");
        open.put("[", "[");
        open.put("{", "{");
        open.put("lvert", "|");
        open.put("lVert", "\u2016");
        open.put("langle", "\u27e8");
        open.put("lfloor", "\u230a");
        open.put("lceil", "\u2308");
        OPEN_DELIMITERS = Collections.unmodifiableMap(open);

        Map<String, String> close = new HashMap<String, String>();
        close.put(")", ")");
        close.put("]", "]");
        close.put("}", "}");
        close.put("rvert", "|");
        close.put("rVert", "\u2016");
        close.put("rangle", "\u27e9");
        close.put("rfloor", "\u230b");
        close.put("rceil", "\u2309");
        CLOSE_DELIMITERS = Collections.unmodifiableMap(close);

        Map<String, String> spaces = new HashMap<String, String>();
        spaces.put(",", "\u2009");
        spaces.put(":", "\u2005");
        spaces.put(";", "\u2004");
        spaces.put(" ", " ");
        spaces.put("~", "\u00a0");
        spaces.put("quad", "\u2003");
        spaces.put("qquad", "\u2003\u2003");
        spaces.put("!", "");
        SPACES = Collections.unmodifiableMap(spaces);

        Map<String, FormulaNode.MathStyle> styles = new HashMap<String, FormulaNode.MathStyle>();
        styles.put("mathrm", FormulaNode.MathStyle.PLAIN);
        styles.put("boldsymbol", FormulaNode.MathStyle.BOLD_ITALIC);
        styles.put("bm", FormulaNode.MathStyle.BOLD_ITALIC);
        styles.put("mathbf", FormulaNode.MathStyle.BOLD);
        styles.put("mathit", FormulaNode.MathStyle.ITALIC);
        TEXT_STYLES = Collections.unmodifiableMap(styles);

        Map<String, FormulaNode.MathScript> scripts = new HashMap<String, FormulaNode.MathScript>();
        scripts.put("mathcal", FormulaNode.MathScript.SCRIPT);
        scripts.put("mathscr", FormulaNode.MathScript.SCRIPT);
        scripts.put("mathfrak", FormulaNode.MathScript.FRAKTUR);
        scripts.put("mathbb", FormulaNode.MathScript.DOUBLE_STRUCK);
        scripts.put("mathsf", FormulaNode.MathScript.SANS_SERIF);
        scripts.put("mathtt", FormulaNode.MathScript.MONOSPACE);
        SCRIPT_STYLES = Collections.unmodifiableMap(scripts);
    }

    private LatexSymbols() {
    }

}
