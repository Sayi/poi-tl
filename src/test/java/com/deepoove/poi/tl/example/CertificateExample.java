package com.deepoove.poi.tl.example;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Pictures;

/**
 * @author Sayi
 */
public class CertificateExample {

    @SuppressWarnings("serial")
    @Test
    public void testRenderTextBox() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("name", "Poi-tl");
                put("department", "DEEPOOVE.COM");
                put("y", "2020");
                put("m", "8");
                put("d", "19");
                put("img", Pictures.ofLocal("src/test/resources/lu.png").size(120, 120).create());
            }
        };

        XWPFTemplate.compile("src/test/resources/certificate/certificate.docx").render(datas)
                .writeToFile("out_example_certificate.docx");

    }

}
