package com.deepoove.poi.tl.render;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.render.DefaultRender;
import com.deepoove.poi.template.MetaTemplate;

/**
 * @author Sayi
 */
public class IterableTemplateTest {

    @SuppressWarnings("serial")
    @Test
    public void testRenderMap() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("title", "poi-tl");
                put("showUser", false);
                // put("showUser", false);
                put("user", "Sayi");
                put("showDate", false);
                put("date", "2020-02-10");

            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/condition.docx");

        List<MetaTemplate> elementTemplates = template.getElementTemplates();
        for (MetaTemplate temp : elementTemplates) {
            System.out.println(temp);
        }

        template.render(datas);
        template.writeToFile("out_condition.docx");
    }
    
    @SuppressWarnings("serial")
    @Test
    public void testIterable() throws Exception {
        List<Map<String, Object>> contents = new ArrayList<>();
        contents.add(new HashMap<String, Object>() {
            {
                put("title", "1.1 小节");
                put("word", "Sayi");
               

            }
        });
        contents.add(new HashMap<String, Object>() {
            {
                put("title", "1.2 小节");
                put("word", "Deepoove");
                
                
            }
        });
        List<Map<String, Object>> users = new ArrayList<>();
        users.add(new HashMap<String, Object>() {
            {
                put("title", "第一章");
                put("name", "Sayi");
                put("contents", contents);
               

            }
        });
        users.add(new HashMap<String, Object>() {
            {
                put("title", "第二章");
                put("name", "Deepoove");
                
                
            }
        });
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("title", "poi-tl");
                put("users", users);
               

            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/condition2.docx");

        List<MetaTemplate> elementTemplates = template.getElementTemplates();
        for (MetaTemplate temp : elementTemplates) {
            System.out.println(temp);
        }

        template.render(datas);
        template.writeToFile("out_condition2.docx");
    }
    
    @SuppressWarnings("serial")
    @Test
    public void testInternal() throws Exception {
       
        List<Map<String, Object>> users = new ArrayList<>();
        users.add(new HashMap<String, Object>() {
            {
                put("name", "Sayi");
               

            }
        });
        users.add(new HashMap<String, Object>() {
            {
                put("name", "Deepoove");
                
                
            }
        });
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("title", "poi-tl");
                put("users", users);
               

            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/condition3.docx");

        List<MetaTemplate> elementTemplates = template.getElementTemplates();
        for (MetaTemplate temp : elementTemplates) {
            System.out.println(temp);
        }

        template.render(datas);
        template.writeToFile("out_condition3.docx");
    }
    
    @SuppressWarnings("serial")
    @Test
    public void testTableInternal() throws Exception {
        List<Map<String, Object>> addrs = new ArrayList<>();
        addrs.add(new HashMap<String, Object>() {
            {
                put("position", "Sayi");
               

            }
        });
        addrs.add(new HashMap<String, Object>() {
            {
                put("position", "Deepoove");
                
                
            }
        });
       
        List<Map<String, Object>> users = new ArrayList<>();
        users.add(new HashMap<String, Object>() {
            {
                put("name", "Sayi");
                put("addrs", addrs);
               

            }
        });
        users.add(new HashMap<String, Object>() {
            {
                put("name", "Deepoove");
                
                
            }
        });
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("title", "poi-tl");
                put("users", users);
               

            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/condition4.docx");

        List<MetaTemplate> elementTemplates = template.getElementTemplates();
        for (MetaTemplate temp : elementTemplates) {
            System.out.println(temp);
        }

        template.render(datas);
        template.writeToFile("out_condition4.docx");
    }
    
    @SuppressWarnings("serial")
    @Test
    public void testShow() throws Exception {
        List<Map<String, Object>> addrs = new ArrayList<>();
        addrs.add(new HashMap<String, Object>() {
            {
                put("position", "Sayi");
               

            }
        });
        addrs.add(new HashMap<String, Object>() {
            {
                put("position", "Deepoove");
                
                
            }
        });
       
        List<Map<String, Object>> users = new ArrayList<>();
        users.add(new HashMap<String, Object>() {
            {
                put("name", "Sayi");
                put("addrs", addrs);
               

            }
        });
        users.add(new HashMap<String, Object>() {
            {
                put("name", "Deepoove");
                
                
            }
        });
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("title", "poi-tl");
                put("show", true);
                put("users", users);
               

            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/condition5.docx");

        List<MetaTemplate> elementTemplates = template.getElementTemplates();
        for (MetaTemplate temp : elementTemplates) {
            System.out.println(temp);
        }

        template.render(datas);
        template.writeToFile("out_condition5.docx");
    }
    
    @SuppressWarnings("serial")
    @Test
    public void testFor() throws Exception {
        RowRenderData header = RowRenderData.build(new TextRenderData("FFFFFF", "姓名"),
                new TextRenderData("FFFFFF", "学历"));

        RowRenderData row0 = RowRenderData.build(
                new HyperLinkTextRenderData("张三", "http://deepoove.com"),
                new TextRenderData("1E915D", "研究生"));

        RowRenderData row1 = RowRenderData.build("李四", "博士");

        RowRenderData row2 = RowRenderData.build("王五", "博士后");
        
        final TextRenderData textRenderData = new TextRenderData("负责生产BUG，然后修复BUG，同时有效实施招聘行为");
        Style style = new Style();
        style.setFontSize(10);
        style.setColor("7F7F7F");
        style.setFontFamily("微软雅黑");
        textRenderData.setStyle(style);
        List<Map<String, Object>> addrs = new ArrayList<>();
        addrs.add(new HashMap<String, Object>() {
            {
                put("position", "Sayi");
               

            }
        });
        addrs.add(new HashMap<String, Object>() {
            {
                put("position", "Deepoove");
                
                
            }
        });
       
        List<Map<String, Object>> users = new ArrayList<>();
        users.add(new HashMap<String, Object>() {
            {
                put("name", "Sayi");
                put("addrs", addrs);
                put("list", new NumbericRenderData(NumbericRenderData.FMT_DECIMAL, 
                Arrays.asList(textRenderData, textRenderData)));
                put("image", new PictureRenderData(120, 120, "src/test/resources/sayi.png"));
                put("table",
                        new MiniTableRenderData(Arrays.asList(row0, row1, row2)));
               

            }
        });
        users.add(new HashMap<String, Object>() {
            {
                put("name", "Deepoove");
                put("list", new NumbericRenderData(NumbericRenderData.FMT_DECIMAL,
                        Arrays.asList(textRenderData, textRenderData)));
                put("image", new PictureRenderData(120, 120, "src/test/resources/sayi.png"));
                
                
            }
        });
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("title", "poi-tl");
                put("users", users);
               

            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/condition6.docx");

//        List<MetaTemplate> elementTemplates = template.getElementTemplates();
//        for (MetaTemplate temp : elementTemplates) {
//            System.out.println(temp);
//        }

        template.render(datas);
        template.writeToFile("out_condition6.docx");
    }


}
