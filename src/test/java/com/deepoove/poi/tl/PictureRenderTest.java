package com.deepoove.poi.tl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.util.BytePictureUtils;

/**
 * 图片模板
 * @author Sayi
 * @version 1.0.0
 */
public class PictureRenderTest {
	
	BufferedImage bufferImage;
	
	@Before
	public void init(){
		bufferImage = BytePictureUtils.newBufferImage(100, 100);
		Graphics2D g = (Graphics2D)bufferImage.getGraphics();
		g.setColor(Color.red);
		g.fillRect(0, 0, 100, 100);
		g.dispose();
		bufferImage.flush();
	}

	@SuppressWarnings("serial")
	@Test
	public void testPictureRender() throws Exception {
		Map<String, Object> datas = new HashMap<String, Object>() {
			{
				//本地图片
				put("localPicture", new PictureRenderData(100, 120, "src/test/resources/logo.png"));
				//本地图片byte数据
				put("localBytePicture", new PictureRenderData(100, 120, ".png", BytePictureUtils.getLocalByteArray(new File("src/test/resources/logo.png"))));
				//网路图片 
				put("urlPicture", new PictureRenderData(100, 100, ".png", BytePictureUtils.getUrlByteArray("https://avatars3.githubusercontent.com/u/1394854?v=3&s=40")));
				// java 图片
				put("bufferImagePicture", new PictureRenderData(100, 120, ".png", BytePictureUtils.getBufferByteArray(bufferImage)));
			}
		};

		XWPFTemplate template = XWPFTemplate.compile("src/test/resources/picture.docx")
				.render(datas);

		FileOutputStream out = new FileOutputStream("out_picture.docx");
		template.write(out);
		out.flush();
		out.close();
		template.close();
	}


}
