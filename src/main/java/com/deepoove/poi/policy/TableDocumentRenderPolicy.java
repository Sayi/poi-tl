package com.deepoove.poi.policy;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.*;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 表格中嵌套子word模版
 * 可参考test中的PollutionWordExample类
 * @author aofavx
 */
public class TableDocumentRenderPolicy extends DocxRenderPolicy {

    /**
     * 表格的列数
     */
    private int cols=1;
    private int row;

    public TableDocumentRenderPolicy(int cols){
        this.cols=cols;
    }

    @Override
    public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
        NiceXWPFDocument doc = template.getXWPFDocument();
        RunTemplate runTemplate = (RunTemplate) eleTemplate;
        XWPFRun run = runTemplate.getRun();
        run.setText("", 0);
        if (null == data) {return;}
        File docx = ((DocxRenderData) data).getDocx();
        List<?> dataList = ((DocxRenderData) data).getDataList();
        if (null == dataList || dataList.isEmpty()) {
            try {
                // 待合并的文档不是模板
                doc.merge(new NiceXWPFDocument(new FileInputStream(docx)));
            } catch (Exception e) {
                logger.error("Cannot get the merged docx.", e);
            }
        }

        this.row=dataList.size()%cols==0 ? dataList.size()/cols : dataList.size()/cols+1;
        createTableWithHeaders(doc, run, row, cols);

        for(int i=0;i<dataList.size();i++){
            if(dataList.get(i)==null){
                continue;
            }
            XWPFTableCell cell = template.getXWPFDocument().getTables().get(0).getRow(i/cols).getCell(i%cols);
            XWPFTemplate temp = XWPFTemplate.compile(docx);
            temp.render(dataList.get(i));
            try {
                List<NiceXWPFDocument> docMerges =new ArrayList<NiceXWPFDocument>();
                docMerges.add(temp.getXWPFDocument());
                doc=doc.merge(docMerges,cell.addParagraph().createRun());
                template.reload(doc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private XWPFTable createTableWithHeaders(NiceXWPFDocument doc, XWPFRun run, int row, int col) {
        XWPFTable table = doc.insertNewTable(run, row, col);
        doc.widthTable(table, MiniTableRenderData.WIDTH_A4_FULL, row, col);
        return table;
    }


}
