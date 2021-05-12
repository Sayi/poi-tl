package com.deepoove.poi.tl.source;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.TableRenderPolicy;
import com.deepoove.poi.util.ByteUtils;

/**
 * 复杂模板
 * 
 * @author Sayi
 * @version 1.0.0
 */
@Deprecated
public class ComplexRenderTest {

    @SuppressWarnings("serial")
    public void testComplexDocx() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("A", "B+");
                put("d_number", "12929");
                put("d_obj", "电商国际业务产品序列");
                put("m_vin", "V900909.345");
                put("d_man", "风清扬");
                put("d_date", "2017-07-01");
                put("d_accident", "未发现异常");
                put("d_safeWord", "未发现异常");
                put("d_operWord", new TextRenderData("df2d4f", "三处异常"));
                put("d_cfgWord", "未发现异常");
                put("d_depart", "deepoove.com");
                put("metalNumber", "与描述相符");
                put("metalWord", "没有质量问题");
                put("metalPicture", new PictureRenderData(100, 120, "src/test/resources/logo.png"));
                put("s_safeWord", "没有安全问题");
                put("s_treadWord", "没有安全问题");
                put("s_brakeWord", "没有安全问题");
                put("s_fluidWord", "没有安全问题");
                put("s_antiWord", "没有安全问题");

                put("brakePicture", new PictureRenderData(220, 135, "src/test/resources/logo.png"));
                put("treadPicture", new PictureRenderData(220, 135, PictureType.PNG, ByteUtils
                        .getLocalByteArray(new File("src/test/resources/logo.png"))));
                put("fluidPicture", new PictureRenderData(220, 135, "src/test/resources/logo.png"));
                put("antiPicture", new PictureRenderData(75, 170, "src/test/resources/logo.png"));

                put("o_operWord", "正常");
                put("zhidong", "正常");
                put("qidong", "正常");
                put("jiasu", "正常");
                put("yunsu", "正常");
                put("zhxiang", "正常");

                String code = "detect_";
                for (int i = 0; i < 25; i++) {
                    put(code + i + "pic",
                            new PictureRenderData(8, 8, "src/test/resources/source/0-1.png"));
                    put(code + i, new TextRenderData("df2d4f", "玻璃"));
                    put(code + i + "pro", new TextRenderData("模糊"));
                }
                List<String> cfgs = new ArrayList<String>() {
                    {
                        add("fbsz");
                        add("czld");
                        add("zpzy");
                        add("ddxt");
                        add("sqdd");
                        add("dvdd");
                        add("dcyx");
                        add("ddtj");
                        add("qjtc");
                        add("ddtc");
                        add("qhdd");
                        add("ddzd");
                        add("aqql");
                        add("zdhw");
                        add("yjqd");
                        add("dsxh");
                    }
                };
                for (String text : cfgs) {
                    put(text + "_p", new PictureRenderData(8, 8, "src/test/resources/source/0-1.png"));
                    put(text + "_v", new PictureRenderData(8, 8, "src/test/resources/source/0-1.png"));
                }
                put("c_cfgWord", "一切正常");

                put("m_vin", "No.234234");
                put("m_plate", "5ABC");
                put("m_engin", "L5ou52QWW");
                put("m_firstp", "2016-09-09");
                put("m_insure", "2016-09-09");
                put("m_annual", "2016-09-09");
                put("m_mile", "2 米");

                put("m_key", "5");

                RowRenderData headers = Rows.of(new TextRenderData("d0d0d0", "过户主体"),
                        new TextRenderData("d0d0d0", "过户时间"), new TextRenderData("d0d0d0", "过户方式")).create();
                put("table",
                        Tables.of(headers,
                                Rows.of("1", "add new # gramer", "3").create(),
                                        Rows.of("2", "add new # gramer", "3").create()).create());

            }
        };

        Configure build = Configure.builder().bind("table", new TableRenderPolicy()).build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/complex.docx", build);
        // 动态持有XWPFTable对象
        template.render(datas);

        FileOutputStream out = new FileOutputStream("out_complex.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();

        // //输出流，便于在servlet下载
        // ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // template.write(bos);
        // bos.flush();
        // bos.close();
        // template.close();
    }

}
