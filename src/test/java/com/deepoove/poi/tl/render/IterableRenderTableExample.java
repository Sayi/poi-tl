package com.deepoove.poi.tl.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;

@DisplayName("Foreach table example")
public class IterableRenderTableExample {

    @SuppressWarnings("serial")
    @Test
    public void testForTable() throws Exception {
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("projectName", "t项目名称test");
        dataMap.put("designDeptName", "t设计单位test");
        dataMap.put("applyUnitName", "t申请单位test");
        dataMap.put("ownerDeptName", "t建设单位test");
        dataMap.put("optimizeReason", "t变更原因test");
        dataMap.put("optimizeChangeName", "t审查方案test");
        dataMap.put("changeType", "t变更类型test");
        dataMap.put("moneyChange", "t变更数test");

        List<Map<String, String>> mores = new ArrayList<Map<String, String>>();
        mores.add(dataMap);
        mores.add(dataMap);
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("mores", mores);

            }
        };
        XWPFTemplate doc = XWPFTemplate.compile("src/test/resources/template/iterable_table.docx");

        doc.render(datas);
        doc.writeToFile("out_iterable_table.docx");

    }

}
