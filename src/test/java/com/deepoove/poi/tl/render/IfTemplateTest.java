package com.deepoove.poi.tl.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.template.MetaTemplate;

public class IfTemplateTest {

    @SuppressWarnings("serial")
    @Test
    public void testIf() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("title", "poi-tl");
                put("showUser", false);
                put("user", "Sayi");
                put("showDate", false);
                put("date", "2020-02-10");

            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/iterable_if1.docx");
        template.render(datas);
        
        
        
        template.writeToFile("out_condition.docx");
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

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/iterable_if2.docx");

        List<MetaTemplate> elementTemplates = template.getElementTemplates();
        for (MetaTemplate temp : elementTemplates) {
            System.out.println(temp);
        }

        template.render(datas);
        template.writeToFile("out_iterable_if2.docx");
    }

}
