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
package com.deepoove.poi.plugin.math;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;

/**
 * Writes the sample documents used to check the rendering by eye in Word.
 * <p>
 * Automatic tests can only prove the XML is right. Whether a fraction bar, a
 * stretching brace or a blackboard bold letter <em>looks</em> right can only be
 * judged in Word, so every supported construct is written to
 * {@code target/out_math_*.docx} with its source next to it.
 *
 * @author Sayi
 */
@DisplayName("Sample documents for manual Word verification")
class FormulaSampleTest {

    private static final String LATEX_SHOWCASE = "target/out_math_latex_showcase.docx";

    private static final String MATHML_SHOWCASE = "target/out_math_mathml_showcase.docx";

    private static final String EQUIVALENCE = "target/out_math_equivalence.docx";

    /** description, LaTeX source. */
    private static final String[][] LATEX_CATALOGUE = {
            { "分式", "\\frac{a+b}{2}" },
            { "连分式", "\\frac{1}{1+\\frac{1}{x}}" },
            { "平方根", "\\sqrt{x}" },
            { "n 次根", "\\sqrt[3]{x+1}" },
            { "下标", "x_i" },
            { "上标", "x^2" },
            { "上下标", "x_i^2" },
            { "上标含表达式", "x^{n+1}" },
            { "嵌套", "\\frac{\\sqrt{x}}{y^2}" },
            { "求和（限在上下）", "\\sum_{i=1}^{n} i^2" },
            { "连乘", "\\prod_{k=1}^{n} k" },
            { "积分（限在侧）", "\\int_0^1 x\\,dx" },
            { "二重积分", "\\iint_D f(x,y)\\,dA" },
            { "函数", "\\sin x + \\cos y" },
            { "带下标的函数", "\\log_2 n" },
            { "极限", "\\lim_{x \\to 0} \\frac{\\sin x}{x}" },
            { "圆括号", "(a+b)" },
            { "自动伸缩括号", "\\left[\\frac{a}{b}\\right]" },
            { "花括号", "\\left\\{x \\mid x>0\\right\\}" },
            { "二项式", "\\binom{n}{k}" },
            { "pmatrix", "\\begin{pmatrix} a & b \\\\ c & d \\end{pmatrix}" },
            { "bmatrix", "\\begin{bmatrix} 1 & 0 \\\\ 0 & 1 \\end{bmatrix}" },
            { "vmatrix（行列式）", "\\begin{vmatrix} a & b \\\\ c & d \\end{vmatrix}" },
            { "cases", "\\begin{cases} a & x>0 \\\\ b & x<0 \\end{cases}" },
            { "array（指定列对齐）", "\\begin{array}{lr} a & b \\\\ c & d \\end{array}" },
            { "hat", "\\hat{x}" },
            { "bar", "\\bar{x}" },
            { "vec", "\\vec{x}" },
            { "dot", "\\dot{x}" },
            { "tilde", "\\tilde{x}" },
            { "上划线", "\\overline{a+b}" },
            { "下划线", "\\underline{x}" },
            { "上花括号", "\\overbrace{a+b}" },
            { "下花括号", "\\underbrace{a+b}" },
            { "上标注", "\\overset{a}{b}" },
            { "下标注", "\\underset{a}{b}" },
            { "盒子", "\\boxed{E=mc^2}" },
            { "占位（不显示但占宽度）", "\\phantom{x}y" },
            { "前导上下标", "\\prescript{a}{b}{X}" },
            { "希腊字母", "\\alpha\\beta\\gamma\\Delta\\Omega\\varepsilon\\varphi" },
            { "关系与箭头", "a \\le b \\ge c \\ne d \\to e \\Rightarrow f" },
            { "关系的短式别名（本次补齐）", "a \\le b,\\ c \\ge d,\\ e \\ne f,\\ x \\mid y" },
            { "逻辑算符", "p \\wedge q,\\ p \\vee q,\\ p \\implies q,\\ \\therefore q,\\ \\because p" },
            { "长箭头与 gets", "a \\longrightarrow b,\\ a \\longleftarrow b,\\ a \\gets b" },
            { "补充的杂项符号", "\\ni,\\ \\prec,\\ \\succ,\\ \\ominus,\\ \\oslash,\\ \\nexists,\\ \\omicron" },
            { "大型算符（本次补齐）", "\\bigotimes_{i=1}^{n} A_i \\quad \\bigodot_{i} B_i" },
            { "双竖线：既是字符又是定界符", "\\|x\\| \\quad \\left\\|\\frac{a}{b}\\right\\|" },
            { "集合写法（单侧竖线不成对）", "\\{x \\mid x>0\\}" },
            { "文本", "\\text{where } x>0" },
            { "空白", "a\\,b\\:c\\;d\\quad e\\qquad f" },
            { "矩阵中嵌套分式", "\\begin{pmatrix} \\frac{1}{2} & \\sqrt{x} \\\\ 0 & 1 \\end{pmatrix}" },
            { "大算符无被加项（不应出现空输入框）", "\\sum_{i=1}^{n}" },
            { "大算符在分式里无被加项", "\\frac{\\sum_{i=1}^{n}}{k}" },
            { "积分无被积函数", "\\int_0^1" } };

    /** description, LaTeX, the equivalent MathML. */
    private static final String[][] EQUIVALENT = {
            { "分式", "\\frac{a}{b}", "<mfrac><mi>a</mi><mi>b</mi></mfrac>" },
            { "斜线分式", "\\frac{a}{b}", "<mfrac bevelled=\"true\"><mi>a</mi><mi>b</mi></mfrac>" },
            { "平方根", "\\sqrt{x}", "<msqrt><mi>x</mi></msqrt>" },
            { "根号内多项（推断 mrow）", "\\sqrt{x+1}", "<msqrt><mi>x</mi><mo>+</mo><mn>1</mn></msqrt>" },
            { "n 次根", "\\sqrt[3]{x}", "<mroot><mi>x</mi><mn>3</mn></mroot>" },
            { "上标", "x^2", "<msup><mi>x</mi><mn>2</mn></msup>" },
            { "下标", "x_i", "<msub><mi>x</mi><mi>i</mi></msub>" },
            { "上下标", "x_i^2", "<msubsup><mi>x</mi><mi>i</mi><mn>2</mn></msubsup>" },
            { "求和", "\\sum_{i}^{n} i",
                    "<munderover><mo>&#x2211;</mo><mi>i</mi><mi>n</mi></munderover><mi>i</mi>" },
            { "积分", "\\int_0^1 x", "<msubsup><mo>&#x222B;</mo><mn>0</mn><mn>1</mn></msubsup><mi>x</mi>" },
            { "函数", "\\sin x", "<mi>sin</mi><mo>&#x2061;</mo><mi>x</mi>" },
            { "极限", "\\lim_{x} f", "<munder><mo>lim</mo><mi>x</mi></munder><mi>f</mi>" },
            { "上划线", "\\overline{x}", "<mover accent=\"true\"><mi>x</mi><mo>&#xAF;</mo></mover>" },
            { "下划线", "\\underline{x}", "<munder accentunder=\"true\"><mi>x</mi><mo>_</mo></munder>" },
            { "hat", "\\hat{x}", "<mover accent=\"true\"><mi>x</mi><mo>^</mo></mover>" },
            { "上花括号", "\\overbrace{x}", "<mover accent=\"true\"><mi>x</mi><mo>&#x23DE;</mo></mover>" },
            { "上标注", "\\overset{a}{b}", "<mover><mi>b</mi><mi>a</mi></mover>" },
            { "括号（裸 mo 配对）", "(a+b)", "<mo>(</mo><mi>a</mi><mo>+</mo><mi>b</mi><mo>)</mo>" },
            { "括号（mfenced）", "(a+b)", "<mfenced separators=\"\"><mi>a</mi><mo>+</mo><mi>b</mi></mfenced>" },
            { "二项式", "\\binom{n}{k}",
                    "<mfenced open=\"\" close=\"\" separators=\"\">"
                            + "<mfrac><mi>n</mi><mi>k</mi></mfrac></mfenced>" },
            { "矩阵", "\\begin{pmatrix} a & b \\\\ c & d \\end{pmatrix}",
                    "<mo>(</mo><mtable><mtr><mtd><mi>a</mi></mtd><mtd><mi>b</mi></mtd></mtr>"
                            + "<mtr><mtd><mi>c</mi></mtd><mtd><mi>d</mi></mtd></mtr></mtable><mo>)</mo>" },
            { "cases", "\\begin{cases} a & x>0 \\\\ b & x<0 \\end{cases}",
                    "<mo>{</mo><mtable>"
                            + "<mtr><mtd><mi>a</mi></mtd><mtd><mi>x</mi><mo>&gt;</mo><mn>0</mn></mtd></mtr>"
                            + "<mtr><mtd><mi>b</mi></mtd><mtd><mi>x</mi><mo>&lt;</mo><mn>0</mn></mtd></mtr>"
                            + "</mtable>" },
            { "文本", "\\text{if}", "<mtext>if</mtext>" },
            { "双线体", "\\mathbb{R}", "<mi mathvariant=\"double-struck\">R</mi>" },
            { "花体", "\\mathcal{L}", "<mi mathvariant=\"script\">L</mi>" },
            { "哥特体", "\\mathfrak{g}", "<mi mathvariant=\"fraktur\">g</mi>" },
            { "无衬线", "\\mathsf{A}", "<mi mathvariant=\"sans-serif\">A</mi>" },
            { "等宽", "\\mathtt{A}", "<mi mathvariant=\"monospace\">A</mi>" },
            { "粗体", "\\mathbf{x}", "<mi mathvariant=\"bold\">x</mi>" },
            { "粗斜体", "\\boldsymbol{x}", "<mi mathvariant=\"bold-italic\">x</mi>" },
            { "盒子", "\\boxed{x}", "<mpadded><mi>x</mi></mpadded>" },
            { "占位", "\\phantom{x}", "<mphantom><mi>x</mi></mphantom>" },
            { "前导上下标", "\\prescript{a}{b}{X}",
                    "<mmultiscripts><mi>X</mi><mprescripts/><mi>b</mi><mi>a</mi></mmultiscripts>" } };

    @Test
    @DisplayName("LaTeX showcase")
    void latexShowcase() throws Exception {
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        XWPFDocument document = new XWPFDocument();
        Counter counter = new Counter();

        heading(document, "poi-tl-plugin-math · LaTeX 目视验收样本");
        note(document, "每行左侧是源码，右侧是渲染结果。请在 Word 里双击公式确认可编辑，并核对观感。");

        heading(document, "一、行内公式（随正文排版）");
        XWPFParagraph inlines = document.createParagraph();
        run(inlines, "质能方程 ");
        inline(inlines, data, counter, "E=mc^2");
        run(inlines, " 写在正文里，应与文字基线和行高协调。");
        XWPFParagraph twoInlines = document.createParagraph();
        run(twoInlines, "同一段两个公式：");
        inline(twoInlines, data, counter, "\\frac{a}{b}");
        run(twoInlines, " 与 ");
        inline(twoInlines, data, counter, "\\sqrt{x^2+y^2}");
        run(twoInlines, " 结束。");

        heading(document, "二、行间公式（三种对齐）");
        display(document, data, counter, FormulaAlign.LEFT, "\\int_0^1 x^2\\,dx=\\frac{1}{3}", FormulaDialect.LATEX);
        display(document, data, counter, FormulaAlign.CENTER, "\\sum_{i=1}^{n} i=\\frac{n(n+1)}{2}",
                FormulaDialect.LATEX);
        display(document, data, counter, FormulaAlign.RIGHT, "e^{i\\pi}+1=0", FormulaDialect.LATEX);

        heading(document, "三、结构清单");
        for (String[] entry : LATEX_CATALOGUE) {
            catalogue(document, data, counter, entry[0], entry[1], Formulas.latex(entry[1]).build());
        }

        heading(document, "四、字体变体（m:scr 与 m:sty 是两个正交维度）");
        catalogue(document, data, counter, "直立", "\\mathrm{ABC}", Formulas.latex("\\mathrm{ABC}").build());
        catalogue(document, data, counter, "斜体", "\\mathit{ABC}", Formulas.latex("\\mathit{ABC}").build());
        catalogue(document, data, counter, "粗体", "\\mathbf{ABC}", Formulas.latex("\\mathbf{ABC}").build());
        catalogue(document, data, counter, "粗斜体（希腊字母也能变粗）", "\\boldsymbol{\\alpha\\beta}",
                Formulas.latex("\\boldsymbol{\\alpha\\beta}").build());
        catalogue(document, data, counter, "双线体（任意字母，不再白名单）", "\\mathbb{ABC}",
                Formulas.latex("\\mathbb{ABC}").build());
        catalogue(document, data, counter, "花体", "\\mathcal{ABC}", Formulas.latex("\\mathcal{ABC}").build());
        catalogue(document, data, counter, "哥特体", "\\mathfrak{ABC}", Formulas.latex("\\mathfrak{ABC}").build());
        catalogue(document, data, counter, "无衬线", "\\mathsf{ABC}", Formulas.latex("\\mathsf{ABC}").build());
        catalogue(document, data, counter, "等宽", "\\mathtt{ABC}", Formulas.latex("\\mathtt{ABC}").build());

        heading(document, "五、样式覆盖");
        catalogue(document, data, counter, "字号 28 半磅（14pt）", "fontSize(28)",
                Formulas.latex("\\frac{a}{b}").fontSize(28).build());
        catalogue(document, data, counter, "红色", "color(C00000)",
                Formulas.latex("\\sum_{i=1}^{n} i").color("C00000").build());
        catalogue(document, data, counter, "粗体 + 斜体", "bold().italic()",
                Formulas.latex("\\int_0^1 x\\,dx").bold(true).italic(true).build());
        catalogue(document, data, counter, "蓝色 + 粗体", "color(0070C0).bold()",
                Formulas.latex("E=mc^2").color("0070C0").bold(true).build());
        catalogue(document, data, counter, "指定数学字体", "mathFont(Cambria Math)",
                Formulas.latex("\\sqrt{x}").mathFont("Cambria Math").build());

        heading(document, "六、继承占位 Run 的格式（这一行公式也应变成 16pt 深绿加粗）");
        inherited(document, data, counter, "f(x)=\\sum_{i=1}^{n} a_i x^i");

        heading(document, "七、正文字体不应泄漏进公式（右侧公式应仍是数学字体，不是宋体）");
        bodyFont(document, data, counter, "宋体", "\\frac{a}{b}");
        bodyFont(document, data, counter, "Times New Roman", "\\sum_{i=1}^{n} a_i");
        bodyFont(document, data, counter, "微软雅黑", "\\sqrt{x^2+y^2}");

        heading(document, "八、降级（公式出错时文档不能崩）");
        catalogue(document, data, counter, "未知命令", "\\foo", Formulas.latex("\\foo").build());
        catalogue(document, data, counter, "空公式", "(空)", Formulas.latex("   ").build());
        catalogue(document, data, counter, "自定义降级文案", "altMeta",
                Formulas.latex("\\foo").altMeta("公式渲染失败，请看原始文本").build());

        heading(document, "九、表格单元格内的公式");
        corpusTable(document, data, counter);

        heading(document, "十、循环块内的公式（每一项各自渲染成一行）");
        loop(document, data);

        write(document, data, LATEX_SHOWCASE, LATEX_CATALOGUE.length + 20);
    }

    @Test
    @DisplayName("MathML showcase")
    void mathMlShowcase() throws Exception {
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        XWPFDocument document = new XWPFDocument();
        Counter counter = new Counter();

        heading(document, "poi-tl-plugin-math · MathML 目视验收样本");
        note(document, "每行左侧是 MathML 元素，右侧是渲染结果。MathML 不需要任何额外的 schema 包或 .xsb。");

        heading(document, "一、与 LaTeX 等价的结构");
        for (String[] entry : EQUIVALENT) {
            catalogue(document, data, counter, entry[0], entry[2], Formulas.mathml(entry[2]).build());
        }

        heading(document, "二、MathML 独有或更直接的结构");
        catalogue(document, data, counter, "mpadded（分组盒，无可见边框）",
                "<mpadded><mi>x</mi></mpadded>",
                Formulas.mathml("<mpadded><mi>x</mi></mpadded>").build());
        catalogue(document, data, counter, "menclose box（可见边框）",
                "<menclose notation=\"box\"><mi>x</mi></menclose>",
                Formulas.mathml("<menclose notation=\"box\"><mi>x</mi></menclose>").build());
        catalogue(document, data, counter, "menclose top（上横线）",
                "<menclose notation=\"top\"><mi>x</mi></menclose>",
                Formulas.mathml("<menclose notation=\"top\"><mi>x</mi></menclose>").build());
        catalogue(document, data, counter, "mspace（宽 2em）",
                "<mi>a</mi><mspace width=\"2em\"/><mi>b</mi>",
                Formulas.mathml("<mi>a</mi><mspace width=\"2em\"/><mi>b</mi>").build());
        catalogue(document, data, counter, "mfenced 列表（分隔符 ,）",
                "<mfenced><mi>a</mi><mi>b</mi><mi>c</mi></mfenced>",
                Formulas.mathml("<mfenced><mi>a</mi><mi>b</mi><mi>c</mi></mfenced>").build());
        catalogue(document, data, counter, "mtable columnalign",
                "<mtable columnalign=\"left right\">",
                Formulas.mathml("<mtable columnalign=\"left right\">"
                        + "<mtr><mtd><mi>a</mi></mtd><mtd><mi>long</mi></mtd></mtr>"
                        + "<mtr><mtd><mi>b</mi></mtd><mtd><mi>x</mi></mtd></mtr></mtable>").build());
        catalogue(document, data, counter, "semantics：有 presentation 时用它",
                "<semantics><mrow>…</mrow><annotation>\\frac{1}{2}</annotation></semantics>",
                Formulas.mathml("<semantics><mrow><mi>a</mi></mrow>"
                        + "<annotation encoding=\"application/x-tex\">\\frac{1}{2}</annotation></semantics>").build());
        catalogue(document, data, counter, "semantics：没有 presentation 时回退到 TeX 注解",
                "<semantics><annotation-xml encoding=\"application/x-tex\">\\frac{1}{2}</annotation-xml></semantics>",
                Formulas.mathml("<semantics>"
                        + "<annotation-xml encoding=\"application/x-tex\">\\frac{1}{2}"
                        + "</annotation-xml></semantics>").build());
        catalogue(document, data, counter, "命名实体 &InvisibleTimes;",
                "<mi>a</mi>&InvisibleTimes;<mi>b</mi>",
                Formulas.mathml("<mi>a</mi>&InvisibleTimes;<mi>b</mi>").build());
        catalogue(document, data, counter, "带 xmlns 的完整文档（无前缀）",
                "<math xmlns=\"http://www.w3.org/1998/Math/MathML\">",
                Formulas.mathml("<math xmlns=\"http://www.w3.org/1998/Math/MathML\">"
                        + "<mfrac><mi>a</mi><mi>b</mi></mfrac></math>").build());
        catalogue(document, data, counter, "带命名空间前缀",
                "<m:math xmlns:m=\"http://www.w3.org/1998/Math/MathML\">",
                Formulas.mathml("<m:math xmlns:m=\"http://www.w3.org/1998/Math/MathML\">"
                        + "<m:mfrac><m:mi>a</m:mi><m:mi>b</m:mi></m:mfrac></m:math>").build());

        heading(document, "三、行间公式");
        display(document, data, counter, FormulaAlign.CENTER,
                "<mfrac><mn>1</mn><msqrt><mi>x</mi></msqrt></mfrac>", FormulaDialect.MATHML);

        heading(document, "四、降级（Content MathML 不支持，应回退为原文）");
        catalogue(document, data, counter, "Content MathML", "<apply><plus/></apply>",
                Formulas.mathml("<apply><plus/></apply>").build());
        catalogue(document, data, counter, "未知实体", "<mi>&Sqrt;</mi>",
                Formulas.mathml("<mi>&Sqrt;</mi>").build());

        write(document, data, MATHML_SHOWCASE, EQUIVALENT.length + 10);
    }

    @Test
    @DisplayName("same formula in both syntaxes, side by side")
    void equivalenceSheet() throws Exception {
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        XWPFDocument document = new XWPFDocument();
        Counter counter = new Counter();

        heading(document, "poi-tl-plugin-math · 两种语法逐行对照");
        note(document, "同一个数学结构，中间列来自 LaTeX，右列来自 MathML。两列应当看起来完全一样。"
                + "自动测试已断言两列产出的 document.xml 逐字节相同，这里供人工确认观感。");

        XWPFTable table = document.createTable(1, 3);
        header(table.getRow(0), "说明", "LaTeX", "MathML");
        for (String[] entry : EQUIVALENT) {
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(entry[0]);
            formulaIn(row.getCell(1), data, counter, Formulas.latex(entry[1]).build());
            formulaIn(row.getCell(2), data, counter, Formulas.mathml(entry[2]).build());
        }

        write(document, data, EQUIVALENCE, 2 * EQUIVALENT.length);
    }

    // ────────────────────────────── building ──────────────────────────────

    private static final class Counter {

        private int value;

        String next() {
            return "f" + ++value;
        }

    }

    private static void run(XWPFParagraph paragraph, String text) {
        paragraph.createRun().setText(text);
    }

    private static void inline(XWPFParagraph paragraph, Map<String, Object> data, Counter counter, String latex) {
        String tag = counter.next();
        paragraph.createRun().setText("{{$" + tag + "}}");
        data.put(tag, Formulas.latex(latex).build());
    }

    private static void display(XWPFDocument document, Map<String, Object> data, Counter counter, FormulaAlign align,
            String source, FormulaDialect dialect) {
        XWPFParagraph paragraph = document.createParagraph();
        label(paragraph, "align=" + align + "    " + source + "    →    ");
        String tag = counter.next();
        paragraph.createRun().setText("{{$" + tag + "}}");
        FormulaRenderDataBuilder builder = FormulaDialect.MATHML == dialect ? Formulas.mathml(source)
                : Formulas.latex(source);
        data.put(tag, builder.display().align(align).build());
    }

    /** One catalogue line: description and source, then the rendered formula. */
    private static void catalogue(XWPFDocument document, Map<String, Object> data, Counter counter, String description,
            String source, FormulaRenderData formula) {
        XWPFParagraph paragraph = document.createParagraph();
        label(paragraph, description + "    " + source + "    →    ");
        String tag = counter.next();
        paragraph.createRun().setText("{{$" + tag + "}}");
        data.put(tag, formula);
    }

    private static void inherited(XWPFDocument document, Map<String, Object> data, Counter counter, String latex) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun label = label(paragraph, "占位 Run 已设为 16pt / 深绿 / 加粗    →    ");
        label.setFontSize(16);
        label.setColor("1F7A1F");
        label.setBold(true);
        String tag = counter.next();
        XWPFRun slot = paragraph.createRun();
        slot.setText("{{$" + tag + "}}");
        slot.setFontSize(16);
        slot.setColor("1F7A1F");
        slot.setBold(true);
        data.put(tag, Formulas.latex(latex).build());
    }

    /** The placeholder run carries a body font; the formula must not inherit it. */
    private static void bodyFont(XWPFDocument document, Map<String, Object> data, Counter counter, String font,
            String latex) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun label = label(paragraph, "占位 Run 字体 = " + font + "    " + latex + "    →    ");
        label.setFontFamily(font);
        String tag = counter.next();
        XWPFRun slot = paragraph.createRun();
        slot.setText("{{$" + tag + "}}");
        slot.setFontFamily(font);
        data.put(tag, Formulas.latex(latex).build());
    }

    private static void corpusTable(XWPFDocument document, Map<String, Object> data, Counter counter) {
        String[] latex = { "\\frac{a}{b}", "\\sqrt{x}", "\\sum_{i=1}^{n} i" };
        String[] mathml = { "<mfrac><mi>a</mi><mi>b</mi></mfrac>", "<msqrt><mi>x</mi></msqrt>",
                "<munderover><mo>&#x2211;</mo><mi>i</mi><mi>n</mi></munderover><mi>i</mi>" };
        // createTable(rows, cols) only ever yields one row, so add the rest explicitly
        XWPFTable table = document.createTable(1, 3);
        header(table.getRow(0), "行", "LaTeX", "MathML");
        for (int row = 0; row < latex.length; row++) {
            XWPFTableRow tableRow = table.createRow();
            tableRow.getCell(0).setText(String.valueOf(row + 1));
            formulaIn(tableRow.getCell(1), data, counter, Formulas.latex(latex[row]).build());
            formulaIn(tableRow.getCell(2), data, counter, Formulas.mathml(mathml[row]).build());
        }
    }

    /**
     * Inside a foreach block the model is the item, so the tag {@code eq} is
     * resolved against each map - no unique tag is needed here.
     */
    private static void loop(XWPFDocument document, Map<String, Object> data) {
        document.createParagraph().createRun().setText("{{?items}}");
        XWPFParagraph row = document.createParagraph();
        label(row, "循环项（字段 eq）    →    ");
        row.createRun().setText("{{$eq}}");
        document.createParagraph().createRun().setText("{{/items}}");

        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        items.add(item("\\frac{1}{2}"));
        items.add(item("\\sqrt{2}"));
        items.add(item("e^{i\\pi}+1=0"));
        data.put("items", items);
    }

    private static Map<String, Object> item(String latex) {
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("eq", latex);
        return item;
    }

    private static void heading(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setBold(true);
        run.setFontSize(14);
        run.setText(text);
    }

    private static void note(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(9);
        run.setColor("808080");
        run.setText(text);
    }

    private static XWPFRun label(XWPFParagraph paragraph, String text) {
        XWPFRun run = paragraph.createRun();
        run.setFontFamily("Consolas");
        run.setFontSize(9);
        run.setColor("808080");
        run.setText(text);
        return run;
    }

    private static void header(XWPFTableRow row, String first, String second, String third) {
        row.getCell(0).setText(first);
        row.getCell(1).setText(second);
        row.getCell(2).setText(third);
    }

    private static void formulaIn(XWPFTableCell cell, Map<String, Object> data, Counter counter,
            FormulaRenderData formula) {
        String tag = counter.next();
        cell.getParagraphs().get(0).createRun().setText("{{$" + tag + "}}");
        data.put(tag, formula);
    }

    // ────────────────────────────── output ──────────────────────────────

    private static void write(XWPFDocument document, Map<String, Object> data, String path, int minimumFormulas)
            throws Exception {
        Configure configure = Configure.builder().addPlugin('$', new FormulaRenderPolicy()).build();
        File output = new File(path);
        try (XWPFTemplate template = XWPFTemplate.compile(document, configure)) {
            template.render(data);
            template.writeToFile(path);
        }
        int formulas = formulasIn(output);
        assertTrue(formulas >= minimumFormulas,
                path + " rendered " + formulas + " formulas, expected at least " + minimumFormulas);
        System.out.println("[sample] " + output.getAbsolutePath() + "  (" + formulas + " formulas, "
                + output.length() / 1024 + " KB)");
    }

    /** Counts {@code m:oMath} in the written package: one per formula, inline or display. */
    private static int formulasIn(File docx) throws Exception {
        try (ZipInputStream zip = new ZipInputStream(new FileInputStream(docx))) {
            ZipEntry entry;
            while (null != (entry = zip.getNextEntry())) {
                if ("word/document.xml".equals(entry.getName())) {
                    String xml = read(zip);
                    int count = 0;
                    int index = 0;
                    // exactly "<m:oMath>": an <m:oMathPara> carries one of these inside,
                    // so a display equation is still counted once
                    while ((index = xml.indexOf("<m:oMath>", index)) >= 0) {
                        count++;
                        index++;
                    }
                    return count;
                }
            }
        }
        throw new IllegalStateException("word/document.xml is missing from " + docx);
    }

    private static String read(InputStream in) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        return new String(out.toByteArray(), StandardCharsets.UTF_8);
    }

}
