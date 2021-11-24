package com.deepoove.poi.tl.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageTest {

    public void testGenAttachmentImage() throws IOException {
        String name = "shencanggongyuming.docx";
//        String name = "深藏功与名.docx";
        BufferedImage image = new BufferedImage(64 + 10 * name.length(), 64, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = image.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 64 + 10 * name.length(), 64);

        BufferedImage read = ImageIO.read(new File("src/test/resources/xlsx.png"));
        g.drawImage(read, 0, 0, 64, 64, null);

        g.setColor(Color.BLACK);
        g.drawString(name, 64 + 4, 32 + 4);

        g.dispose();

        File outputfile = new File("out_image.png");
        ImageIO.write(image, "png", outputfile);
    }

}
