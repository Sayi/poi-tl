package com.deepoove.poi.tl.issue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Includes;
import com.deepoove.poi.data.RenderData;

public class Issue761 {

    @Test
    public void test761() throws IOException {
        Map<String, Object> mainData = new HashMap<>();
        mainData.put("sub1", genSub2());
        mainData.put("sub2", null);
        mainData.put("sub3", null);
        mainData.put("sub4", null);
        mainData.put("sub5", genSub1());
        mainData.put("sub6", genSub2());
        mainData.put("sub7", genSub2());
        XWPFTemplate render = XWPFTemplate.compile("src/test/resources/issue/761.docx").render(mainData);
        render.writeToFile("target/out_issue_761.docx");
    }

    private static RenderData genSub2() {
        return Includes.ofLocal("src/test/resources/issue/761_sub2.docx").create();
    }

    private static RenderData genSub1() {
        Map<String, Object> sub1Data = new HashMap<>();
        sub1Data.put("subsub1", genSubSub("subsub1"));
        sub1Data.put("subsub2", genSubSub("subsub2"));
        sub1Data.put("subsub3", genSubSub("subsub3"));
        return Includes.ofLocal("src/test/resources/issue/761_sub1.docx").setRenderModel(sub1Data).create();
    }

    private static RenderData genSubSub(String title) {
        Map<String, Object> subsub = new HashMap<>();
        subsub.put("desc", title);
        return Includes.ofLocal("src/test/resources/issue/761_subsub.docx").setRenderModel(subsub).create();
    }

//    这是sub2
//    subsub1
//    subsub2
//    subsub3
//    这是sub2
//    这是sub2

}
