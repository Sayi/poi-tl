package com.deepoove.poi.tl.asserting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Expression render behavior")
public class ElBehaviorTest {

    @Test
    public void readsANestedMapPath() throws Exception {
        Map<String, Object> user = new HashMap<String, Object>();
        user.put("name", "Sayi");
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{user.name}}"),
                RenderAsserts.mapOf("user", user));

        assertEquals("Sayi", document.getParagraphs().get(0).getText());
        document.close();
    }

    @Test
    public void readsAJavaBeanGetter() throws Exception {
        User user = new User();
        user.setName("Sayi");
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{name}}"), user);

        assertEquals("Sayi", document.getParagraphs().get(0).getText());
        document.close();
    }

    @Test
    public void missingFieldRendersAsClearedText() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{user.missing}}"),
                RenderAsserts.mapOf("user", new HashMap<String, Object>()));

        assertEquals("", RenderAsserts.paragraphText(document));
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    public static class User {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
