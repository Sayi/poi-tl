package com.deepoove.poi.tl.example;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;

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
                put("img", new PictureRenderData(120, 120, ".png", new FileInputStream("src/test/resources/lu.png")));
            }
        };

        XWPFTemplate.compile("src/test/resources/certificate/certificate.docx").render(datas)
                .writeToFile("out_example_certificate.docx");

    }

}
