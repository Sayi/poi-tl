package com.deepoove.poi.xwpf;

import java.util.List;

import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTxbxContent;

import com.deepoove.poi.util.ReflectionUtils;

public class TextBoxBodyContainer implements BodyContainer {

    private XWPFTextboxContent textbox;

    public TextBoxBodyContainer(XWPFTextboxContent textbox) {
        this.textbox = textbox;
    }

    @Override
    public void removeBodyElement(int pos) {
        List<IBodyElement> bodyElements = getBodyElements();
        if (pos >= 0 && pos < bodyElements.size()) {
            BodyElementType type = bodyElements.get(pos).getElementType();
            if (type == BodyElementType.TABLE) {
                // TODO remove table
            }
            if (type == BodyElementType.PARAGRAPH) {
                textbox.removeParagraph((XWPFParagraph) bodyElements.get(pos));
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setParagraph(XWPFParagraph paragraph, int pos) {
        List<XWPFParagraph> paragraphs = (List<XWPFParagraph>) ReflectionUtils.getValue("paragraphs", textbox);
        paragraphs.set(pos, paragraph);
        CTTxbxContent ctTxbxContent = textbox.getCTTxbxContent();
        ctTxbxContent.setPArray(pos, paragraph.getCTP());
    }

    @Override
    public IBody getTarget() {
        return textbox;
    }

    @Override
    public void setTable(int pos, XWPFTable table) {
        throw new UnsupportedOperationException();

    }

    @Override
    public XWPFTable insertNewTable(XWPFRun run, int row, int col) {
        throw new UnsupportedOperationException();
    }

    @Override
    public XWPFSection closelySectPr(IBodyElement element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int elementPageWidth(IBodyElement element) {
        return 8295;
    }

}
