package com.deepoove.poi.tl.policy.ref;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.FileInputStream;
import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.policy.reference.AbstractTemplateRenderPolicy;
import com.deepoove.poi.template.PictureTemplate;

@DisplayName("ReferencePolicy test case")
public class ReferencePolicyTest {

    private static String template_file = "src/test/resources/template/reference_policy.docx";

    @Test
    @DisplayName("Replace placeholder picture with 'let's img' as optional text")
    public void testReplacePictureByOptionalText() throws Exception {

        @SuppressWarnings("serial")
        XWPFTemplate template = XWPFTemplate.compile(template_file).render(new HashMap<String, Object>() {
            {
                put("img",
                        new PictureRenderData(100, 120, ".png", new FileInputStream("src/test/resources/sayi.png")));
            }
        });

        template.writeToFile("out_reference_replace_optional_picture.docx");
    }

    @Test
    public void testOptionalTextPicture() throws Exception {
        Configure configure = Configure.newBuilder()
                .bind("img", new AbstractTemplateRenderPolicy<PictureTemplate, Void>() {
                    @Override
                    public void doRender(PictureTemplate eleTemplate, Void t, XWPFTemplate template) throws Exception {
                        System.out.println(t);
                        assertNotNull(eleTemplate.getPicture());
                    }
                }).build();
        XWPFTemplate.compile(template_file, configure).render(new HashMap<>()).close();;

    }

}
