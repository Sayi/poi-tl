package com.deepoove.poi.policy;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTParaRPr;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.StyleUtils;

/**
 * @author Sayi
 * @version 0.0.5
 */
public class NumbericRenderPolicy extends AbstractRenderPolicy {

    @Override
    protected boolean validate(Object data) {
        if (null == data) return false;

        if (!(data instanceof NumbericRenderData)) {
            throw new RenderException("Error datamodel: correct type is NumbericRenderData, but is "
                    + data.getClass());
        }

        if (CollectionUtils.isEmpty(((NumbericRenderData) data).getNumbers())) {
            logger.debug("Empty NumbericRenderData datamodel: {}", data);
            return false;
        }

        return true;
    }

    @Override
    public void doRender(RunTemplate runTemplate, Object data, XWPFTemplate template)
            throws Exception {
        NiceXWPFDocument doc = template.getXWPFDocument();
        XWPFRun run = runTemplate.getRun();
        NumbericRenderData numbericData = (NumbericRenderData) data;
        List<TextRenderData> datas = numbericData.getNumbers();
        Style fmtStyle = numbericData.getFmtStyle();

        BigInteger numID = doc.addNewNumbericId(numbericData.getNumFmt());
        
        XWPFParagraph paragraph;
        XWPFRun newRun;
        for (TextRenderData line : datas) {
            paragraph = doc.insertNewParagraph(run);
            paragraph.setNumID(numID);
            CTP ctp = paragraph.getCTP();
            CTPPr pPr = ctp.isSetPPr() ? ctp.getPPr() : ctp.addNewPPr();
            CTParaRPr pr = pPr.isSetRPr() ? pPr.getRPr() : pPr.addNewRPr();
            StyleUtils.styleRpr(pr, fmtStyle);
            newRun = paragraph.createRun();
            StyleUtils.styleRun(newRun, line.getStyle());
            newRun.setText(line.getText());
        }

        // 成功后清除标签
        clearPlaceholder(run);
        IRunBody parent = run.getParent();
        if (parent instanceof XWPFParagraph) {
            ((XWPFParagraph) parent).removeRun(runTemplate.getRunPos());
            // To do: 更好的列表样式
            // ((XWPFParagraph) parent).setSpacingBetween(0,
            // LineSpacingRule.AUTO);
        }
    }
}
