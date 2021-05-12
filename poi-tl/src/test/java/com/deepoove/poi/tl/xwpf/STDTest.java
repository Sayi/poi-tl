package com.deepoove.poi.tl.xwpf;

import java.util.HashMap;
import java.util.Map;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.HyperlinkTextRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.TextRenderData;

/**
 * @author Sayi
 */
public class STDTest {

//    paragraph.getIRuns().stream().filter(r -> r instanceof XWPFSDT).forEach(r -> {
//        ISDTContent isdtContent = ((XWPFSDT) r).getContent();
//        if (isdtContent instanceof XWPFSDTContent) {
//            @SuppressWarnings("unchecked")
//            List<ISDTContents> contents = (List<ISDTContents>) ReflectionUtils.getValue("bodyElements",
//                    isdtContent);
//            List<XWPFRun> collect = contents.stream()
//                    .filter(c -> c instanceof XWPFRun)
//                    .map(c -> (XWPFRun) c)
//                    .collect(Collectors.toList());
//            // to do refactor sdtcontent
//            resolveXWPFRuns(collect, metaTemplates, stack);
//        }
//    });

    @SuppressWarnings("serial")
    public void testRender() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>() {
            {
                put("name", "Poi-tl");
                put("word", "模板引擎");
                put("time", "2019-05-31");
                put("author", new TextRenderData("000000", "Sayi卅一"));
                put("introduce", new HyperlinkTextRenderData("http://www.deepoove.com", "http://www.deepoove.com"));
                put("portrait", new PictureRenderData(60, 60, "src/test/resources/sayi.png"));

            }
        };

        XWPFTemplate.compile("src/test/resources/template/template_sdt.docx")
                .render(data)
                .writeToFile("out_template_sdt.docx");

    }

}
