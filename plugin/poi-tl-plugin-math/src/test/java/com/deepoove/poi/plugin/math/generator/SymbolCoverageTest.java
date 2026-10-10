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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Guards the width of the LaTeX subset.
 * <p>
 * The symbol table is maintained by hand, so a command that everybody writes -
 * {@code \\le}, {@code \\mid}, {@code \\implies} - can quietly be missing until
 * somebody's document falls back to plain text. This test names the commands
 * that are expected to work, so removing one is a deliberate act.
 *
 * @author Sayi
 */
@DisplayName("LaTeX command coverage")
class SymbolCoverageTest {

    /** Commands that stand on their own. */
    private static final String[] SYMBOLS = {
            "alpha", "beta", "gamma", "delta", "epsilon", "varepsilon", "zeta", "eta", "theta", "vartheta",
            "iota", "kappa", "lambda", "mu", "nu", "xi", "omicron", "pi", "varpi", "rho", "varrho", "sigma",
            "varsigma", "tau", "upsilon", "phi", "varphi", "chi", "psi", "omega", "Gamma", "Delta", "Theta",
            "Lambda", "Xi", "Pi", "Sigma", "Upsilon", "Phi", "Psi", "Omega",
            "le", "leq", "ge", "geq", "ne", "neq", "equiv", "approx", "sim", "simeq", "cong", "propto", "ll",
            "gg", "prec", "succ", "subset", "supset", "subseteq", "supseteq", "in", "ni", "notin", "emptyset",
            "varnothing", "forall", "exists", "nexists", "neg", "land", "lor", "wedge", "vee", "therefore",
            "because", "mid", "parallel", "perp", "angle",
            "to", "gets", "rightarrow", "leftarrow", "leftrightarrow", "Rightarrow", "Leftarrow",
            "Leftrightarrow", "implies", "iff", "mapsto", "longrightarrow", "longleftarrow", "uparrow",
            "downarrow",
            "times", "div", "cdot", "ast", "star", "circ", "bullet", "pm", "mp", "oplus", "ominus", "otimes",
            "oslash", "cup", "cap", "setminus",
            "sum", "prod", "coprod", "int", "iint", "iiint", "oint", "bigcup", "bigcap", "bigoplus", "bigotimes",
            "bigodot",
            "sin", "cos", "tan", "cot", "sec", "csc", "arcsin", "arccos", "arctan", "sinh", "cosh", "tanh",
            "log", "ln", "lg", "exp", "lim", "max", "min", "sup", "inf", "det", "gcd", "dim", "ker", "deg", "Pr",
            "ldots", "cdots", "dots", "vdots", "ddots", "prime", "partial", "nabla", "infty",
            "quad", "qquad", "vert", "Vert", "|" };

    /** Commands that take arguments, written the way a document would use them. */
    private static final String[] STRUCTURES = {
            "\\frac{a}{b}", "\\dfrac{a}{b}", "\\tfrac{a}{b}", "\\sqrt{x}", "\\sqrt[3]{x}", "\\binom{n}{k}",
            "\\boxed{x}", "\\phantom{x}", "\\overline{x}", "\\underline{x}", "\\overbrace{x}", "\\underbrace{x}",
            "\\overset{a}{b}", "\\underset{a}{b}", "\\prescript{a}{b}{X}", "\\text{if }", "\\mathrm{A}",
            "\\mathit{A}", "\\mathbf{A}", "\\mathcal{A}", "\\mathscr{A}", "\\mathfrak{A}", "\\mathbb{A}",
            "\\mathsf{A}", "\\mathtt{A}", "\\boldsymbol{\\alpha}", "\\bm{\\alpha}",
            "\\hat{x}", "\\widehat{x}", "\\bar{x}", "\\vec{x}", "\\dot{x}", "\\ddot{x}", "\\tilde{x}",
            "\\acute{x}", "\\grave{x}", "\\check{x}", "\\breve{x}",
            "(a+b)", "\\left(a\\right)", "\\left[\\frac{a}{b}\\right]", "\\left\\{x\\right\\}",
            "\\left|x\\right|", "\\left\\|x\\right\\|", "\\left\\Vert x\\right\\Vert",
            "\\left\\vert x\\right\\vert", "\\lvert x\\rvert", "\\lVert x\\rVert", "\\langle x\\rangle",
            "\\lceil x\\rceil", "\\lfloor x\\rfloor", "\\vert x\\vert", "\\|x\\|", "\\Vert x\\Vert",
            "\\{x \\vert x>0\\}",
            "x_i", "x^2", "x_i^2", "x^{n+1}", "\\sum_{i=1}^{n} i", "\\int_0^1 x\\,dx", "\\lim_{x \\to 0} f",
            "\\sin x", "\\log_2 n", "\\text{where } x>0",
            "\\begin{matrix} a & b \\\\ c & d \\end{matrix}",
            "\\begin{pmatrix} a & b \\\\ c & d \\end{pmatrix}",
            "\\begin{bmatrix} a & b \\end{bmatrix}", "\\begin{vmatrix} a & b \\end{vmatrix}",
            "\\begin{Bmatrix} a \\end{Bmatrix}", "\\begin{Vmatrix} a \\end{Vmatrix}",
            "\\begin{cases} a & x>0 \\\\ b & x<0 \\end{cases}",
            "\\begin{array}{lr} a & b \\end{array}",
            "a\\,b\\;c\\quad d\\qquad e", "\\displaystyle\\frac{a}{b}", "\\textstyle x" };

    @Test
    @DisplayName("every command in the documented subset parses")
    void everyCommandParses() {
        List<String> failures = new ArrayList<String>();
        for (String command : SYMBOLS) {
            failures.addAll(parse("\\" + command));
        }
        for (String source : STRUCTURES) {
            failures.addAll(parse(source));
        }
        assertTrue(failures.isEmpty(), "these no longer parse, which drops the documented subset:\n" + failures);
    }

    @Test
    @DisplayName("the big operators that take limits are the ones with limits above")
    void limitOperatorsAreMarked() {
        String[] withLimits = { "sum", "prod", "coprod", "bigcup", "bigcap", "bigoplus", "bigotimes", "bigodot" };
        for (String name : withLimits) {
            FormulaNode.BigOperator operator = (FormulaNode.BigOperator) ((FormulaNode.Row) LatexParser
                    .parse("\\" + name + "_{i}")).getItems().get(0);
            assertTrue(operator.isUnderOver(), "\\" + name + " should put its limits above and below");
        }
        // integrals keep theirs at the side
        for (String name : new String[] { "int", "iint", "iiint", "oint" }) {
            FormulaNode.BigOperator operator = (FormulaNode.BigOperator) ((FormulaNode.Row) LatexParser
                    .parse("\\" + name + "_{0}")).getItems().get(0);
            assertTrue(!operator.isUnderOver(), "\\" + name + " should keep its limits at the side");
        }
    }

    private static List<String> parse(String source) {
        List<String> failures = new ArrayList<String>();
        try {
            LatexParser.parse(source);
        } catch (IllegalArgumentException e) {
            failures.add(source + "  ->  " + e.getMessage());
        }
        return failures;
    }

}
