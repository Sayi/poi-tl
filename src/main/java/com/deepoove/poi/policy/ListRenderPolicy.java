package com.deepoove.poi.policy;

import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.util.StyleUtils;

public class ListRenderPolicy extends AbstractRenderPolicy<List<Object>> {

    @Override
    protected boolean validate(List<Object> data) {
        return (null != data && !data.isEmpty());
    }

    @Override
    public void doRender(RenderContext<List<Object>> context) throws Exception {
        NiceXWPFDocument document = context.getXWPFDocument();
        XWPFRun run = context.getRun();
        List<Object> datas = context.getData();
        for (Object data : datas) {
            if (data instanceof TextRenderData) {
                XWPFRun createRun = document.insertNewParagraph(run).createRun();
                StyleUtils.styleRun(createRun, run);
                TextRenderPolicy.Helper.renderTextRun(createRun, data);
            } else if (data instanceof MiniTableRenderData) {
                MiniTableRenderPolicy.Helper.renderMiniTable(run, (MiniTableRenderData) data);
            } else if (data instanceof NumbericRenderData) {
                NumbericRenderPolicy.Helper.renderNumberic(run, (NumbericRenderData) data);
            } else if (data instanceof PictureRenderData) {
                PictureRenderPolicy.Helper.renderPicture(document.insertNewParagraph(run).createRun(), (PictureRenderData) data);
            }

        }
    }
    
    

    @Override
    protected void afterRender(RenderContext<List<Object>> context) {
        clearPlaceholder(context, true);
    }

}
