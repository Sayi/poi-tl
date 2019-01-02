package com.deepoove.poi.tl.issue;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;

public class Issue161 {

    @Test
    public void testEmptyRun() throws Exception {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("projectName", "项目名称test");
        dataMap.put("designDeptName", "设计单位test");
        dataMap.put("applyUnitName", "申请单位test");
        dataMap.put("ownerDeptName", "建设单位test");
        dataMap.put("optimizeReason", "变更原因test");
        dataMap.put("optimizeChangeName", "审查方案test");
        dataMap.put("changeType", "变更类型test");
        dataMap.put("moneyChange", "变更数test");
        XWPFTemplate doc = XWPFTemplate.compile("src/test/resources/issue/161.docx");
        doc.render(dataMap);

        FileOutputStream fos = new FileOutputStream("out_issue_161.docx");
        doc.write(fos);
        fos.flush();
        fos.close();

    }

}
