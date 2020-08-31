package com.deepoove.poi.tl.policy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.util.BytePictureUtils;

@DisplayName("Picture Render test case")
public class PictureRenderTest {

    BufferedImage bufferImage;

    @BeforeEach
    public void init() {
        bufferImage = BytePictureUtils.newBufferImage(100, 100);
        Graphics2D g = (Graphics2D) bufferImage.getGraphics();
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, 100, 100);
        g.setColor(Color.BLACK);
        g.drawString("Java Image", 0, 50);
        g.dispose();
        bufferImage.flush();
    }

    @Test
    public void testPictureRender() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>();
        // local file path
        datas.put("localPicture", Pictures.ofLocal("src/test/resources/sayi.png").size(120, 120).create());
        // input stream
        datas.put("localBytePicture", Pictures
                .ofStream(new FileInputStream("src/test/resources/logo.png"), PictureType.PNG).size(100, 120).create());
        // network url
        datas.put("urlPicture",
                Pictures.ofUrl("http://deepoove.com/images/icecream.png", PictureType.PNG).size(100, 100).create());
        // java bufferedImage
        datas.put("bufferImagePicture", Pictures.ofBufferedImage(bufferImage, PictureType.PNG).size(100, 100).create());
        // alt attribute for not exist image
        datas.put("image", Pictures.ofLocal("not_exist_image.png").altMeta("No Image!").create());

        XWPFTemplate.compile("src/test/resources/template/render_picture.docx").render(datas)
                .writeToFile("out_render_picture.docx");

    }

}
