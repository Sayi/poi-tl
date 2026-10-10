package com.deepoove.poi.tl.asserting;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.MetaTemplate;
import com.deepoove.poi.tl.source.XWPFTestSupport;

/**
 * Helpers for in-memory template rendering assertions. Existing sample tests are
 * left untouched; new behavior tests build their own documents.
 */
public final class RenderAsserts {

    private RenderAsserts() {
    }

    public static void setRunText(XWPFParagraph paragraph, String text) {
        if (paragraph.getRuns().isEmpty()) {
            paragraph.createRun().setText(text);
        } else {
            paragraph.getRuns().get(0).setText(text, 0);
        }
    }

    public static void setCellText(XWPFTableCell cell, String text) {
        XWPFParagraph paragraph = cell.getParagraphs().isEmpty() ? cell.addParagraph() : cell.getParagraphs().get(0);
        setRunText(paragraph, text);
    }

    public static XWPFDocument paragraph(String... runTexts) {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        for (String text : runTexts) {
            paragraph.createRun().setText(text);
        }
        return document;
    }

    public static XWPFDocument render(XWPFDocument document, Object model) throws IOException {
        return render(document, Configure.createDefault(), model);
    }

    public static XWPFDocument render(XWPFDocument document, Configure configure, Object model) throws IOException {
        XWPFTemplate template = XWPFTemplate.compile(document, configure).render(model);
        return XWPFTestSupport.readNewDocument(template);
    }

    public static byte[] toBytes(XWPFDocument document) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            document.write(out);
            return out.toByteArray();
        } finally {
            document.close();
        }
    }

    public static String paragraphText(XWPFDocument document) {
        StringBuilder text = new StringBuilder();
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            if (text.length() > 0) {
                text.append('\n');
            }
            text.append(paragraph.getText());
        }
        return text.toString();
    }

    public static String cellText(XWPFTable table, int row, int col) {
        return table.getRow(row).getCell(col).getText();
    }

    public static List<ElementTemplate> elementTemplates(XWPFDocument document) throws IOException {
        return elementTemplates(document, Configure.createDefault());
    }

    public static List<ElementTemplate> elementTemplates(XWPFDocument document, Configure configure) throws IOException {
        try (XWPFTemplate template = XWPFTemplate.compile(document, configure)) {
            List<ElementTemplate> elements = new ArrayList<ElementTemplate>();
            for (MetaTemplate meta : template.getElementTemplates()) {
                if (meta instanceof ElementTemplate) {
                    elements.add((ElementTemplate) meta);
                }
            }
            return elements;
        }
    }

    public static void assertNoTags(XWPFDocument document) {
        assertNoTags(document, "{{", "}}");
    }

    public static void assertNoTags(XWPFDocument document, String prefix, String suffix) {
        assertFalse(collectText(document).contains(prefix), "rendered document still contains " + prefix);
        assertFalse(collectText(document).contains(suffix), "rendered document still contains " + suffix);
    }

    public static void assertEveryCellHasParagraph(XWPFDocument document) {
        for (IBodyElement element : document.getBodyElements()) {
            if (element instanceof XWPFTable) {
                assertTableCells((XWPFTable) element);
            }
        }
    }

    private static void assertTableCells(XWPFTable table) {
        for (XWPFTableRow row : table.getRows()) {
            for (XWPFTableCell cell : row.getTableCells()) {
                assertFalse(cell.getParagraphs().isEmpty(), "table cell lost its paragraph");
                for (XWPFTable nested : cell.getTables()) {
                    assertTableCells(nested);
                }
            }
        }
    }

    private static String collectText(XWPFDocument document) {
        StringBuilder text = new StringBuilder();
        appendBody(text, document.getBodyElements());
        for (XWPFHeader header : document.getHeaderList()) {
            appendBody(text, header.getBodyElements());
        }
        for (XWPFFooter footer : document.getFooterList()) {
            appendBody(text, footer.getBodyElements());
        }
        return text.toString();
    }

    private static void appendBody(StringBuilder text, List<IBodyElement> elements) {
        for (IBodyElement element : elements) {
            if (element instanceof XWPFParagraph) {
                text.append(((XWPFParagraph) element).getText());
            } else if (element instanceof XWPFTable) {
                for (XWPFTableRow row : ((XWPFTable) element).getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        text.append(cell.getText());
                        appendBody(text, cell.getBodyElements());
                    }
                }
            }
        }
    }

    public static String runColor(XWPFRun run) {
        if (run.getColor() != null) {
            return run.getColor();
        }
        if (run.getCTR().isSetRPr() && run.getCTR().getRPr().sizeOfColorArray() > 0) {
            Object value = run.getCTR().getRPr().getColorArray(0).getVal();
            return value == null ? null : value.toString();
        }
        return null;
    }

    public static Map<String, Object> mapOf(String key, Object value) {
        Map<String, Object> model = new java.util.HashMap<String, Object>();
        model.put(key, value);
        return model;
    }

}
