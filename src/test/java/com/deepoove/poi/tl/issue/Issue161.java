package com.deepoove.poi.tl.issue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;

public class Issue161 {

    @Test
    public void testEmptyRun() throws Exception {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("projectName", "t项目名称test");
        dataMap.put("designDeptName", "t设计单位test");
        dataMap.put("applyUnitName", "t申请单位test");
        dataMap.put("ownerDeptName", "t建设单位test");
        dataMap.put("optimizeReason", "t变更原因test");
        dataMap.put("optimizeChangeName", "t审查方案test");
        dataMap.put("changeType", "t变更类型test");
        dataMap.put("moneyChange", "t变更数test");
        XWPFTemplate doc = XWPFTemplate.compile("src/test/resources/issue/161.docx");
        doc.render(dataMap).writeToFile("out_issue_161.docx");

    }

}
