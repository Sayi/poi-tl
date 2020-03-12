package com.deepoove.poi.tl.policy;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.BookmarkRenderPolicy;

@DisplayName("Bookmark Render test case")
public class BookmarkRenderPolicyTest {

    @SuppressWarnings("serial")
    @Test
    public void testBookmarkRender() throws Exception {

        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("anchor1", new HyperLinkTextRenderData("poi-tl", "anchor:poi-tl"));
                put("anchor2", new HyperLinkTextRenderData("文字", "anchor:我是绿色且换行的文字"));
                put("title", "poi-tl");
                put("text", new TextRenderData("28a745", "我是绿色且换行的文字"));

            }
        };

        BookmarkRenderPolicy bookmarkRenderPolicy = new BookmarkRenderPolicy();
        Configure config = Configure.newBuilder().bind("title", bookmarkRenderPolicy).bind("text", bookmarkRenderPolicy)
                .build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/render_bookmark.docx", config)
                .render(datas);

        System.out.println(template.getXWPFDocument().getDocument().toString());

        template.writeToFile("out_render_bookmark.docx");

    }

}
