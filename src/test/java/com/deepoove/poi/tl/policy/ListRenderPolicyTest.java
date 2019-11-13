package com.deepoove.poi.tl.policy;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat.Enum;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.ListRenderPolicy;

public class ListRenderPolicyTest {

    RowRenderData header = RowRenderData.build(new TextRenderData("FF00FF", "姓名"),
            new TextRenderData("FF00FF", "学历"));

    RowRenderData row0 = RowRenderData.build(
            new HyperLinkTextRenderData("张三", "http://deepoove.com"),
            new TextRenderData("1E915D", "研究生"));

    RowRenderData row1 = RowRenderData.build("李四", "博士");

    RowRenderData row2 = RowRenderData.build("王五", "博士后");

    @SuppressWarnings("serial")
    @Test
    public void testListData() throws Exception {

        Map<String, Object> datas = new HashMap<String, Object>() {
            {

                final List<Object> list = new ArrayList<Object>() {
                    {
                        add(new TextRenderData("ver 0.0.3"));
                        add(new PictureRenderData(100, 120, "src/test/resources/logo.png"));
                        add(new TextRenderData("9d55b8",
                                "Deeply in love with the things you love, just deepoove."));
                        add(new TextRenderData("ver 0.0.4"));
                        add(new PictureRenderData(100, 120, "src/test/resources/logo.png"));
                        add(getData(NumbericRenderData.FMT_LOWER_LETTER));
                        add(new MiniTableRenderData(header, Arrays.asList(row0, row1, row2)));
                    }
                };
//                List<Object> list2 = new ArrayList<Object>() {
//                    {
//                        add(list);
//                        add(list);
//                    }
//                };

                put("website", list);
            }
        };

        Configure config = Configure.newBuilder().customPolicy("website", new ListRenderPolicy() {})
                .build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/list.docx", config);

        template.render(datas);

        FileOutputStream out = new FileOutputStream("out_list.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

    @SuppressWarnings("serial")
    private NumbericRenderData getData(Pair<Enum, String> pair) {
        return new NumbericRenderData(pair, new ArrayList<TextRenderData>() {
            {
                add(new TextRenderData("df2d4f",
                        "Deeply in love with the things you love, just deepoove."));
                add(new TextRenderData("Deeply in love with the things you love, just deepoove."));
                add(new TextRenderData("5285c5",
                        "Deeply in love with the things you love, just deepoove."));
            }
        });
    }

}
