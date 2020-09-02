package com.deepoove.poi.tl.issue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.tl.source.XWPFTestSupport;

@DisplayName("Issue161 enter回车")
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
        doc.render(dataMap);

        XWPFDocument document = XWPFTestSupport.readNewDocument(doc);
        XWPFTable table = document.getTableArray(0);
        XWPFTableCell cell = table.getRow(1).getCell(0);
        XWPFTable xwpfTable = cell.getTableArray(0);

        assertEquals(dataMap.get("projectName"), xwpfTable.getRow(1).getCell(1).getText());
        assertEquals(dataMap.get("designDeptName"), xwpfTable.getRow(2).getCell(1).getText());
        assertEquals(dataMap.get("applyUnitName"), xwpfTable.getRow(2).getCell(3).getText());

        String text = xwpfTable.getRow(3).getCell(0).getText();
        assertTrue(-1 != text.indexOf(dataMap.get("ownerDeptName")));
        assertTrue(-1 != text.indexOf(dataMap.get("optimizeReason")));
        assertTrue(-1 != text.indexOf(dataMap.get("optimizeChangeName")));
        assertTrue(-1 != text.indexOf(dataMap.get("changeType")));
        assertTrue(-1 != text.indexOf(dataMap.get("moneyChange")));

        document.close();

    }

}
