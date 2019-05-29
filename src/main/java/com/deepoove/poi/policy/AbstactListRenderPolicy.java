package com.deepoove.poi.policy;

import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.TableTools;

public abstract class AbstactListRenderPolicy extends AbstractRenderPolicy<List<RenderData>> {

    @Override
    public void doRender(RunTemplate runTemplate, List<RenderData> datas, XWPFTemplate template)
            throws Exception {
        NiceXWPFDocument document = template.getXWPFDocument();
        XWPFRun run = runTemplate.getRun();
        for (RenderData data : datas) {
            if (data instanceof TextRenderData) {
                TextRenderPolicy.Helper.renderTextRun(run, data);
            } else if (data instanceof MiniTableRenderData) {
                List<RowRenderData> rows = ((MiniTableRenderData) data).getDatas();
                int row = rows.size(), col = rows.get(0).getCellDatas().size();
                XWPFTable table = document.insertNewTable(run, row, col);
                TableTools.widthTable(table, MiniTableRenderData.WIDTH_A4_FULL, col);
                for (int i = 0; i < row; i++) {
                    MiniTableRenderPolicy.Helper.renderRow(table, row, rows.get(i));
                }
            } else if (data instanceof NumbericRenderData) {
                NumbericRenderPolicy.Helper.renderNumberic(run, (NumbericRenderData) data);
            } else if (data instanceof PictureRenderData) {
                XWPFParagraph newParagraph = document.insertNewParagraph(run);
                PictureRenderPolicy.Helper.renderPicture(newParagraph.createRun(), (PictureRenderData) data);
            }

        }
    }

}
