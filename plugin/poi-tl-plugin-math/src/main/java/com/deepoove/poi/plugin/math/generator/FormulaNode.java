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
import java.util.Collections;
import java.util.List;

import org.openxmlformats.schemas.officeDocument.x2006.math.CTAcc;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTBorderBox;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTBox;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTFPr;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTFType;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTGroupChr;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTGroupChrPr;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTPhant;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTPhantPr;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTSPre;
import org.openxmlformats.schemas.officeDocument.x2006.math.STFType;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTBar;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTD;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTDPr;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTEqArr;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTF;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTFunc;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLow;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTLimUpp;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTM;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTMCPr;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTMPr;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTNary;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTNaryPr;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTMR;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTSSub;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTSSubSup;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTSSup;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTRad;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTRadPr;
import org.openxmlformats.schemas.officeDocument.x2006.math.STLimLoc;
import org.openxmlformats.schemas.officeDocument.x2006.math.STTopBot;
import org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STXAlign;

/**
 * The immutable LaTeX syntax tree.
 * <p>
 * Every node renders itself into an {@link OmmlContainer}, so the LaTeX to OMML
 * mapping sits next to the node it belongs to: no dispatch table and no
 * {@code instanceof} chain to keep in sync. The base constructor is private and
 * the subclasses are nested, which makes the hierarchy closed in Java 8.
 *
 * @author Sayi
 */
abstract class FormulaNode {

    private FormulaNode() {
    }

    /**
     * Math style of a run, mapped to {@code m:rPr/m:sty}.
     */
    public enum MathStyle {

        PLAIN,

        BOLD,

        ITALIC,

        BOLD_ITALIC

    }

    /**
     * Font variant, the orthogonal half of the math style: OMML keeps
     * {@code m:sty} (plain / bold / italic) and {@code m:scr} (script / fraktur /
     * double-struck / sans-serif / monospace) side by side, and the second one is
     * what {@code \mathbb}, {@code \mathcal}, {@code \mathfrak}, {@code \mathsf}
     * and {@code \mathtt} need.
     */
    public enum MathScript {

        SCRIPT,

        FRAKTUR,

        DOUBLE_STRUCK,

        SANS_SERIF,

        MONOSPACE

    }

    abstract void render(OmmlContainer target, OmmlContext ctx);

    /** A sequence of nodes. */
    public static final class Row extends FormulaNode {

        private final List<FormulaNode> items;

        public Row(List<FormulaNode> items) {
            this.items = Collections.unmodifiableList(new ArrayList<FormulaNode>(items));
        }

        public List<FormulaNode> getItems() {
            return items;
        }

        @Override
        void render(OmmlContainer target, OmmlContext ctx) {
            int index = 0;
            while (index < items.size()) {
                FormulaNode item = items.get(index);
                if (!(item instanceof Text)) {
                    item.render(target, ctx);
                    index++;
                    continue;
                }
                Text text = (Text) item;
                StringBuilder value = new StringBuilder(text.getText());
                int next = index + 1;
                while (next < items.size() && items.get(next) instanceof Text
                        && ((Text) items.get(next)).isUpright() == text.isUpright()) {
                    value.append(((Text) items.get(next)).getText());
                    next++;
                }
                OmmlBuilder.run(target, value.toString(), text.isUpright(), ctx);
                index = next;
            }
        }

    }

    /** A literal run; {@code upright} forces {@code m:sty p} (text, function names). */
    public static final class Text extends FormulaNode {

        private final String text;

        private final boolean upright;

        public Text(String text, boolean upright) {
            this.text = text;
            this.upright = upright;
        }

        public String getText() {
            return text;
        }

        public boolean isUpright() {
            return upright;
        }

        @Override
        void render(OmmlContainer target, OmmlContext ctx) {
            OmmlBuilder.run(target, text, upright, ctx);
        }

    }

    /** {@code \frac}, {@code \dfrac}, {@code \tfrac}. */
    public static final class Fraction extends FormulaNode {

        private final FormulaNode numerator;

        private final FormulaNode denominator;

        private final boolean bevelled;

        public Fraction(FormulaNode numerator, FormulaNode denominator) {
            this(numerator, denominator, false);
        }

        /** @param bevelled true for MathML {@code <mfrac bevelled="true">}, a slanted bar */
        public Fraction(FormulaNode numerator, FormulaNode denominator, boolean bevelled) {
            this.numerator = numerator;
            this.denominator = denominator;
            this.bevelled = bevelled;
        }

        public boolean isBevelled() {
            return bevelled;
        }

        public FormulaNode getNumerator() {
            return numerator;
        }

        public FormulaNode getDenominator() {
            return denominator;
        }

        @Override
        void render(OmmlContainer target, OmmlContext ctx) {
            CTF fraction = target.addFraction();
            if (bevelled || ctx.hasRpr()) {
                CTFPr properties = fraction.addNewFPr();
                if (bevelled) {
                    CTFType type = properties.addNewType();
                    type.setVal(STFType.LIN);
                }
                if (ctx.hasRpr()) {
                    OmmlBuilder.ctrl(properties.addNewCtrlPr(), ctx);
                }
            }
            OmmlBuilder.fill(fraction.addNewNum(), numerator, ctx);
            OmmlBuilder.fill(fraction.addNewDen(), denominator, ctx);
        }

    }

    /** {@code \sqrt{x}}, {@code \sqrt[n]{x}}. */
    public static final class Radical extends FormulaNode {

        private final FormulaNode radicand;

        private final FormulaNode degree;

        public Radical(FormulaNode radicand, FormulaNode degree) {
            this.radicand = radicand;
            this.degree = degree;
        }

        public FormulaNode getRadicand() {
            return radicand;
        }

        public FormulaNode getDegree() {
            return degree;
        }

        @Override
        void render(OmmlContainer target, OmmlContext ctx) {
            CTRad radical = target.addRadical();
            CTRadPr properties = radical.addNewRadPr();
            if (null == degree) {
                properties.addNewDegHide();
            }
            if (ctx.hasRpr()) {
                OmmlBuilder.ctrl(properties.addNewCtrlPr(), ctx);
            }
            // Word tolerates a missing m:deg poorly, so always emit the element.
            if (null == degree) {
                radical.addNewDeg();
            } else {
                OmmlBuilder.fill(radical.addNewDeg(), degree, ctx);
            }
            OmmlBuilder.fill(radical.addNewE(), radicand, ctx);
        }

    }

    /** {@code x^2}, {@code x_i}, {@code x_i^2}. */
    public static final class Script extends FormulaNode {

        private final FormulaNode base;

        private final FormulaNode subscript;

        private final FormulaNode superscript;

        public Script(FormulaNode base, FormulaNode subscript, FormulaNode superscript) {
            this.base = base;
            this.subscript = subscript;
            this.superscript = superscript;
        }

        public FormulaNode getBase() {
            return base;
        }

        public FormulaNode getSubscript() {
            return subscript;
        }

        public FormulaNode getSuperscript() {
            return superscript;
        }

        @Override
        void render(OmmlContainer target, OmmlContext ctx) {
            if (null != subscript && null != superscript) {
                CTSSubSup script = target.addSubSuperscript();
                if (ctx.hasRpr()) {
                    OmmlBuilder.ctrl(script.addNewSSubSupPr().addNewCtrlPr(), ctx);
                }
                OmmlBuilder.fill(script.addNewE(), base, ctx);
                OmmlBuilder.fill(script.addNewSub(), subscript, ctx);
                OmmlBuilder.fill(script.addNewSup(), superscript, ctx);
            } else if (null != superscript) {
                CTSSup script = target.addSuperscript();
                if (ctx.hasRpr()) {
                    OmmlBuilder.ctrl(script.addNewSSupPr().addNewCtrlPr(), ctx);
                }
                OmmlBuilder.fill(script.addNewE(), base, ctx);
                OmmlBuilder.fill(script.addNewSup(), superscript, ctx);
            } else {
                CTSSub script = target.addSubscript();
                if (ctx.hasRpr()) {
                    OmmlBuilder.ctrl(script.addNewSSubPr().addNewCtrlPr(), ctx);
                }
                OmmlBuilder.fill(script.addNewE(), base, ctx);
                OmmlBuilder.fill(script.addNewSub(), subscript, ctx);
            }
        }

    }

    /** {@code \sum}, {@code \int} and friends, emitted as {@code m:nary}. */
    public static final class BigOperator extends FormulaNode {

        private final String character;

        private final boolean underOver;

        private final FormulaNode subscript;

        private final FormulaNode superscript;

        private final FormulaNode operand;

        public BigOperator(String character, boolean underOver, FormulaNode subscript, FormulaNode superscript,
                FormulaNode operand) {
            this.character = character;
            this.underOver = underOver;
            this.subscript = subscript;
            this.superscript = superscript;
            this.operand = operand;
        }

        public String getCharacter() {
            return character;
        }

        public boolean isUnderOver() {
            return underOver;
        }

        public FormulaNode getSubscript() {
            return subscript;
        }

        public FormulaNode getSuperscript() {
            return superscript;
        }

        public FormulaNode getOperand() {
            return operand;
        }

        @Override
        void render(OmmlContainer target, OmmlContext ctx) {
            CTNary nary = target.addNary();
            CTNaryPr properties = nary.addNewNaryPr();
            properties.addNewChr().setVal(character);
            properties.addNewLimLoc().setVal(underOver ? STLimLoc.UND_OVR : STLimLoc.SUB_SUP);
            if (null == subscript) {
                properties.addNewSubHide();
            }
            if (null == superscript) {
                properties.addNewSupHide();
            }
            if (ctx.hasRpr()) {
                OmmlBuilder.ctrl(properties.addNewCtrlPr(), ctx);
            }
            if (null != subscript) {
                OmmlBuilder.fill(nary.addNewSub(), subscript, ctx);
            }
            if (null != superscript) {
                OmmlBuilder.fill(nary.addNewSup(), superscript, ctx);
            }
            if (isEmpty(operand)) {
                hideEmptyOperand(nary.addNewE());
            } else {
                OmmlBuilder.fill(nary.addNewE(), operand, ctx);
            }
        }

        /**
         * A large operator with nothing to operate on would leave an empty
         * {@code m:e}, which Word draws as an empty input box - {@code \sum_{i=1}^{n}}
         * on its own, or inside a fraction, would show one. A hidden, zero width
         * phantom takes its place, so the operator and its limits are all that is
         * drawn.
         * <p>
         * The phantom keeps its {@code m:e} even though it is empty: the schema
         * requires it, and a hidden zero width phantom makes it invisible.
         */
        private static void hideEmptyOperand(CTOMathArg argument) {
            CTPhant phantom = argument.addNewPhant();
            CTPhantPr properties = phantom.addNewPhantPr();
            properties.addNewShow().setVal("off");
            properties.addNewZeroWid().setVal("on");
            phantom.addNewE();
        }

        private static boolean isEmpty(FormulaNode node) {
            return null == node || node instanceof Row && ((Row) node).getItems().isEmpty();
        }

    }

    /** {@code \left( ... \right)} and bare {@code ( ... )}. */
    public static final class Delimiter extends FormulaNode {

        private final String begin;

        private final String end;

        private final String separator;

        private final List<FormulaNode> arguments;

        public Delimiter(String begin, String end, FormulaNode body) {
            this(begin, end, null, Collections.singletonList(body));
        }

        /**
         * @param separator  the {@code m:sepChr} written between arguments, or null for
         *                   the OMML default
         * @param arguments  one {@code m:e} each, as produced by MathML {@code <mfenced>}
         */
        public Delimiter(String begin, String end, String separator, List<FormulaNode> arguments) {
            this.begin = begin;
            this.end = end;
            this.separator = separator;
            this.arguments = Collections.unmodifiableList(new ArrayList<FormulaNode>(arguments));
        }

        public String getBegin() {
            return begin;
        }

        public String getEnd() {
            return end;
        }

        public List<FormulaNode> getArguments() {
            return arguments;
        }

        public String getSeparator() {
            return separator;
        }

        /** @return the single argument, convenient for the LaTeX front end */
        public FormulaNode getBody() {
            return arguments.get(0);
        }

        @Override
        void render(OmmlContainer target, OmmlContext ctx) {
            CTD delimiter = target.addDelimiter();
            CTDPr properties = delimiter.addNewDPr();
            // Both characters are always set explicitly: the schema default is a pair of
            // parentheses, which is wrong for \binom and \begin{cases}.
            properties.addNewBegChr().setVal(begin);
            if (null != separator) {
                properties.addNewSepChr().setVal(separator);
            }
            properties.addNewEndChr().setVal(end);
            properties.addNewGrow();
            if (ctx.hasRpr()) {
                OmmlBuilder.ctrl(properties.addNewCtrlPr(), ctx);
            }
            for (FormulaNode argument : arguments) {
                OmmlBuilder.fill(delimiter.addNewE(), argument, ctx);
            }
        }

    }

    /**
     * The body of a matrix-like environment: {@code m:eqArr} when rows are
     * equations, {@code m:m} otherwise. Delimiters are added by the parser as a
     * surrounding {@link Delimiter}, never here.
     */
    public static final class Matrix extends FormulaNode {

        private final List<List<FormulaNode>> rows;

        private final boolean equationArray;

        private final String columnSpec;

        /**
         * @param rows          one list of cells per row
         * @param equationArray true to emit {@code m:eqArr} instead of {@code m:m}
         * @param columnSpec    per column alignment such as {@code cc} or {@code lr}
         */
        public Matrix(List<List<FormulaNode>> rows, boolean equationArray, String columnSpec) {
            this.rows = rows;
            this.equationArray = equationArray;
            this.columnSpec = columnSpec;
        }

        public List<List<FormulaNode>> getRows() {
            return rows;
        }

        public boolean isEquationArray() {
            return equationArray;
        }

        public String getColumnSpec() {
            return columnSpec;
        }

        @Override
        void render(OmmlContainer target, OmmlContext ctx) {
            if (equationArray) {
                CTEqArr array = target.addEquationArray();
                if (ctx.hasRpr()) {
                    OmmlBuilder.ctrl(array.addNewEqArrPr().addNewCtrlPr(), ctx);
                }
                for (List<FormulaNode> row : rows) {
                    OmmlBuilder.fill(array.addNewE(), flatten(row), ctx);
                }
            } else {
                CTM matrix = target.addMatrix();
                CTMPr properties = matrix.addNewMPr();
                appendColumns(properties, rows.isEmpty() ? 0 : rows.get(0).size());
                if (ctx.hasRpr()) {
                    OmmlBuilder.ctrl(properties.addNewCtrlPr(), ctx);
                }
                for (List<FormulaNode> row : rows) {
                    CTMR matrixRow = matrix.addNewMr();
                    for (FormulaNode cell : row) {
                        OmmlBuilder.fill(matrixRow.addNewE(), cell, ctx);
                    }
                }
            }
        }

        /** Groups consecutive columns sharing an alignment into one {@code m:mc}. */
        private void appendColumns(CTMPr properties, int columnCount) {
            int index = 0;
            while (index < columnCount) {
                char align = alignmentAt(index);
                int count = 1;
                while (index + count < columnCount && alignmentAt(index + count) == align) {
                    count++;
                }
                CTMCPr column = properties.addNewMcs().addNewMc().addNewMcPr();
                column.addNewCount().setVal(count);
                column.addNewMcJc().setVal(toAlign(align));
                index += count;
            }
        }

        private char alignmentAt(int column) {
            if (null == columnSpec || column >= columnSpec.length()) return 'c';
            return columnSpec.charAt(column);
        }

        private static STXAlign.Enum toAlign(char spec) {
            if ('l' == spec) return STXAlign.LEFT;
            if ('r' == spec) return STXAlign.RIGHT;
            return STXAlign.CENTER;
        }

        private static FormulaNode flatten(List<FormulaNode> cells) {
            List<FormulaNode> items = new ArrayList<FormulaNode>();
            for (FormulaNode cell : cells) {
                if (cell instanceof Row) {
                    items.addAll(((Row) cell).getItems());
                } else {
                    items.add(cell);
                }
            }
            return new Row(items);
        }

    }

    /** Accents such as {@code \hat} and {@code \vec}. */
    public static final class Accent extends FormulaNode {

        private final String character;

        private final FormulaNode body;

        public Accent(String character, FormulaNode body) {
            this.character = character;
            this.body = body;
        }

        public String getCharacter() {
            return character;
        }

        public FormulaNode getBody() {
            return body;
        }

        @Override
        void render(OmmlContainer target, OmmlContext ctx) {
            CTAcc accent = target.addAccent();
            accent.addNewAccPr().addNewChr().setVal(character);
            if (ctx.hasRpr()) {
                OmmlBuilder.ctrl(accent.getAccPr().addNewCtrlPr(), ctx);
            }
            OmmlBuilder.fill(accent.addNewE(), body, ctx);
        }

    }

    /** The overline and underline commands. */
    public static final class Bar extends FormulaNode {

        private final boolean over;

        private final FormulaNode body;

        public Bar(boolean over, FormulaNode body) {
            this.over = over;
            this.body = body;
        }

        public boolean isOver() {
            return over;
        }

        public FormulaNode getBody() {
            return body;
        }

        @Override
        void render(OmmlContainer target, OmmlContext ctx) {
            CTBar bar = target.addBar();
            bar.addNewBarPr().addNewPos().setVal(over ? STTopBot.TOP : STTopBot.BOT);
            if (ctx.hasRpr()) {
                OmmlBuilder.ctrl(bar.getBarPr().addNewCtrlPr(), ctx);
            }
            OmmlBuilder.fill(bar.addNewE(), body, ctx);
        }

    }

    /** The overset, underset and lim commands. */
    public static final class Limit extends FormulaNode {

        private final boolean upper;

        private final FormulaNode body;

        private final FormulaNode limit;

        public Limit(boolean upper, FormulaNode body, FormulaNode limit) {
            this.upper = upper;
            this.body = body;
            this.limit = limit;
        }

        public boolean isUpper() {
            return upper;
        }

        public FormulaNode getBody() {
            return body;
        }

        public FormulaNode getLimit() {
            return limit;
        }

        @Override
        void render(OmmlContainer target, OmmlContext ctx) {
            if (upper) {
                CTLimUpp limUpp = target.addUpperLimit();
                if (ctx.hasRpr()) {
                    OmmlBuilder.ctrl(limUpp.addNewLimUppPr().addNewCtrlPr(), ctx);
                }
                OmmlBuilder.fill(limUpp.addNewE(), body, ctx);
                OmmlBuilder.fill(limUpp.addNewLim(), limit, ctx);
            } else {
                CTLimLow limLow = target.addLowerLimit();
                if (ctx.hasRpr()) {
                    OmmlBuilder.ctrl(limLow.addNewLimLowPr().addNewCtrlPr(), ctx);
                }
                OmmlBuilder.fill(limLow.addNewE(), body, ctx);
                OmmlBuilder.fill(limLow.addNewLim(), limit, ctx);
            }
        }

    }

    /** {@code \sin x} and friends: {@code m:func} with an upright name. */
    public static final class Function extends FormulaNode {

        private final String name;

        private final FormulaNode argument;

        public Function(String name, FormulaNode argument) {
            this.name = name;
            this.argument = argument;
        }

        public String getName() {
            return name;
        }

        public FormulaNode getArgument() {
            return argument;
        }

        @Override
        void render(OmmlContainer target, OmmlContext ctx) {
            CTFunc function = target.addFunction();
            if (ctx.hasRpr()) {
                OmmlBuilder.ctrl(function.addNewFuncPr().addNewCtrlPr(), ctx);
            }
            OmmlBuilder.run(OmmlContainer.of(function.addNewFName()), name, true, ctx);
            if (null == argument) {
                function.addNewE();
            } else {
                OmmlBuilder.fill(function.addNewE(), argument, ctx);
            }
        }

    }

    /**
     * An upright operator name such as {@code \lim}; the parser turns it into
     * {@code m:limLow} when a subscript follows, otherwise it stays a plain run.
     */
    public static final class OperatorName extends FormulaNode {

        private final String name;

        public OperatorName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        void render(OmmlContainer target, OmmlContext ctx) {
            OmmlBuilder.run(target, name, true, ctx);
        }

    }

    /** {@code \mathrm}, {@code \mathbf}, {@code \mathit}. */
    public static final class Styled extends FormulaNode {

        private final MathStyle mathStyle;

        private final MathScript mathScript;

        private final FormulaNode body;

        public Styled(MathStyle mathStyle, FormulaNode body) {
            this(mathStyle, null, body);
        }

        public Styled(MathScript mathScript, FormulaNode body) {
            this(null, mathScript, body);
        }

        public Styled(MathStyle mathStyle, MathScript mathScript, FormulaNode body) {
            this.mathStyle = mathStyle;
            this.mathScript = mathScript;
            this.body = body;
        }

        /** @return the emphasis, or null when only a font variant is set */
        public MathStyle getMathStyle() {
            return mathStyle;
        }

        /** @return the font variant, or null for the default math font */
        public MathScript getMathScript() {
            return mathScript;
        }

        public FormulaNode getBody() {
            return body;
        }

        @Override
        void render(OmmlContainer target, OmmlContext ctx) {
            body.render(target, ctx.withMathStyle(mathStyle).withMathScript(mathScript));
        }

    }

    /** {@code \\boxed{}} and MathML {@code <mpadded>}: a grouping box, no visible border. */
    public static final class Box extends FormulaNode {

        private final FormulaNode body;

        public Box(FormulaNode body) {
            this.body = body;
        }

        public FormulaNode getBody() {
            return body;
        }

        @Override
        void render(OmmlContainer target, OmmlContext ctx) {
            CTBox box = target.addBox();
            if (ctx.hasRpr()) {
                OmmlBuilder.ctrl(box.addNewBoxPr().addNewCtrlPr(), ctx);
            }
            OmmlBuilder.fill(box.addNewE(), body, ctx);
        }

    }

    /** MathML {@code <menclose notation="box">}: a box with a visible border. */
    public static final class BorderBox extends FormulaNode {

        private final FormulaNode body;

        public BorderBox(FormulaNode body) {
            this.body = body;
        }

        public FormulaNode getBody() {
            return body;
        }

        @Override
        void render(OmmlContainer target, OmmlContext ctx) {
            CTBorderBox borderBox = target.addBorderBox();
            if (ctx.hasRpr()) {
                OmmlBuilder.ctrl(borderBox.addNewBorderBoxPr().addNewCtrlPr(), ctx);
            }
            OmmlBuilder.fill(borderBox.addNewE(), body, ctx);
        }

    }

    /** {@code \\phantom{}} and MathML {@code <mphantom>}: reserves space, draws nothing. */
    public static final class Phantom extends FormulaNode {

        private final FormulaNode body;

        public Phantom(FormulaNode body) {
            this.body = body;
        }

        public FormulaNode getBody() {
            return body;
        }

        @Override
        void render(OmmlContainer target, OmmlContext ctx) {
            CTPhant phantom = target.addPhantom();
            if (ctx.hasRpr()) {
                OmmlBuilder.ctrl(phantom.addNewPhantPr().addNewCtrlPr(), ctx);
            }
            OmmlBuilder.fill(phantom.addNewE(), body, ctx);
        }

    }

    /**
     * MathML {@code <mmultiscripts>} and {@code \\prescript{}{}}: scripts written
     * <em>before</em> the base.
     * <p>
     * Note the OMML element order of {@code m:sPre}: properties, then {@code sub},
     * {@code sup}, and only then {@code e} - the opposite of {@code m:sSubSup}.
     */
    public static final class PreScript extends FormulaNode {

        private final FormulaNode base;

        private final FormulaNode subscript;

        private final FormulaNode superscript;

        public PreScript(FormulaNode base, FormulaNode subscript, FormulaNode superscript) {
            this.base = base;
            this.subscript = subscript;
            this.superscript = superscript;
        }

        public FormulaNode getBase() {
            return base;
        }

        public FormulaNode getSubscript() {
            return subscript;
        }

        public FormulaNode getSuperscript() {
            return superscript;
        }

        @Override
        void render(OmmlContainer target, OmmlContext ctx) {
            CTSPre script = target.addPreScript();
            if (ctx.hasRpr()) {
                OmmlBuilder.ctrl(script.addNewSPrePr().addNewCtrlPr(), ctx);
            }
            OmmlBuilder.fill(script.addNewSub(), null == subscript ? new Row(Collections.<FormulaNode>emptyList())
                    : subscript, ctx);
            OmmlBuilder.fill(script.addNewSup(), null == superscript ? new Row(Collections.<FormulaNode>emptyList())
                    : superscript, ctx);
            OmmlBuilder.fill(script.addNewE(), base, ctx);
        }

    }

    /** {@code \\overbrace{}} / {@code \\underbrace{}} and MathML {@code \u23de}/{@code \u23df}. */
    public static final class GroupChar extends FormulaNode {

        private final String character;

        private final boolean over;

        private final FormulaNode body;

        public GroupChar(String character, boolean over, FormulaNode body) {
            this.character = character;
            this.over = over;
            this.body = body;
        }

        public String getCharacter() {
            return character;
        }

        public boolean isOver() {
            return over;
        }

        public FormulaNode getBody() {
            return body;
        }

        @Override
        void render(OmmlContainer target, OmmlContext ctx) {
            CTGroupChr group = target.addGroupChar();
            CTGroupChrPr properties = group.addNewGroupChrPr();
            properties.addNewChr().setVal(character);
            properties.addNewPos().setVal(over ? STTopBot.TOP : STTopBot.BOT);
            properties.addNewVertJc().setVal(over ? STTopBot.BOT : STTopBot.TOP);
            if (ctx.hasRpr()) {
                OmmlBuilder.ctrl(properties.addNewCtrlPr(), ctx);
            }
            OmmlBuilder.fill(group.addNewE(), body, ctx);
        }

    }

}
