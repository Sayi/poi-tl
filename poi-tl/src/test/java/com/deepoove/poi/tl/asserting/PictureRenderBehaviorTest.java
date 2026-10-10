package com.deepoove.poi.tl.asserting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;

@DisplayName("Picture render behavior")
public class PictureRenderBehaviorTest {

    private static final byte[] PNG = png(0x01);
    private static final byte[] OTHER_PNG = png(0x02);

    @Test
    public void insertsOnePngPart() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{@img}}"),
                RenderAsserts.mapOf("img", Pictures.ofBytes(PNG, PictureType.PNG).size(40, 40).create()));

        assertEquals(1, document.getAllPictures().size());
        assertEquals(PictureType.PNG.type(), document.getAllPictures().get(0).getPictureType());
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void clearsTheTagWhenThePictureIsMissing() throws Exception {
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{@img}}"), new HashMap<String, Object>());

        assertEquals(0, document.getAllPictures().size());
        assertEquals("", RenderAsserts.paragraphText(document));
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    @Test
    public void keepsDistinctPartsForTwoPictures() throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("left", Pictures.ofBytes(PNG, PictureType.PNG).size(20, 20).create());
        model.put("right", Pictures.ofBytes(OTHER_PNG, PictureType.PNG).size(20, 20).create());
        XWPFDocument document = RenderAsserts.render(RenderAsserts.paragraph("{{@left}}{{@right}}"), model);

        assertEquals(2, document.getAllPictures().size());
        Set<String> names = new HashSet<String>();
        for (XWPFPictureData picture : document.getAllPictures()) {
            names.add(picture.getPackagePart().getPartName().getName());
        }
        assertEquals(2, names.size());
        RenderAsserts.assertNoTags(document);
        document.close();
    }

    private static byte[] png(int marker) {
        return new byte[] { (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A, (byte) marker, 0x02, 0x03, 0x04 };
    }

}
