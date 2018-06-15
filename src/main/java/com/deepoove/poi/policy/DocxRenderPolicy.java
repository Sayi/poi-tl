package com.deepoove.poi.policy;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;

/**
 * <p>
 * word合并 <br/>
 * 1. docx word合并 2. poi-tl模板渲染后的docx word合并 3. List循环合并
 * </p>
 * 
 * @author Sayi
 * @version
 */
public class DocxRenderPolicy implements RenderPolicy {

    @Override
    public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
        NiceXWPFDocument doc = template.getXWPFDocument();
        RunTemplate runTemplate = (RunTemplate) eleTemplate;
        if (null == data) return;

        XWPFRun run = runTemplate.getRun();
        run.setText("", 0);

        List<NiceXWPFDocument> docMerges = getMergedDocxs((DocxRenderData) data);
        try {
            doc = doc.merge(docMerges, run);
        } catch (Exception e) {
            logger.error("merge docx error", e);
        }
        
        template.reload(doc);
    }

    private List<NiceXWPFDocument> getMergedDocxs(DocxRenderData data) {
        List<NiceXWPFDocument> docs = new ArrayList<NiceXWPFDocument>();
        File docx = data.getDocx();
        List<?> dataList = data.getDataList();
        if (null == dataList || dataList.isEmpty()) {
            try {
                docs.add(new NiceXWPFDocument(new FileInputStream(docx)));
            } catch (Exception e) {
                logger.error("Cannot get the merged docx.", e);
            }
        } else {
            for (int i = 0; i < dataList.size(); i++) {
                XWPFTemplate temp = XWPFTemplate.compile(docx);
                temp.render(dataList.get(i));
                docs.add(temp.getXWPFDocument());
            }

        }
        return docs;
    }

}
