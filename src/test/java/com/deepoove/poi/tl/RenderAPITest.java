package com.deepoove.poi.tl;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.render.RenderAPI;

/**
 * @author Sayi
 * @version 0.0.4
 */
public class RenderAPITest {

	@SuppressWarnings("serial")
	@Test
	public void testRenderAPI() throws Exception {
		Map<String, Object> datas = new HashMap<String, Object>() {
			{
				put("header_version", "ver 0.0.3");
				put("hello", "ver 0.0.3");
				put("logo", new PictureRenderData(100, 120, "src/test/resources/logo.png"));
				put("title", new TextRenderData("9d55b8",
						"Deeply in love with the things you love,\\n just deepoove."));
				put("changeLog", new TableRenderData(new ArrayList<RenderData>() {
					{
						add(new TextRenderData("d0d0d0", ""));
						add(new TextRenderData("d0d0d0", "introduce"));
					}
				}, new ArrayList<Object>() {
					{
						add("1;add new # gramer");
						add("2;support insert table");
						add("3;support more style");
					}
				}, "no datas", 10600));
				put("unorderlist", new NumbericRenderData(new ArrayList<TextRenderData>() {
					{
						add(new TextRenderData(
								"Deeply in love with the things you love, just deepoove."));
						add(new TextRenderData(
								"Deeply in love with the things you love, just deepoove."));
						add(new TextRenderData(
								"Deeply in love with the things you love, just deepoove."));
					}
				}));
				put("orderlist", new NumbericRenderData(NumbericRenderData.FMT_DECIMAL,
						new ArrayList<TextRenderData>() {
							{
								add(new TextRenderData(
										"Deeply in love with the things you love, just deepoove."));
								add(new TextRenderData(
										"Deeply in love with the things you love, just deepoove."));
								add(new TextRenderData(
										"Deeply in love with the things you love, just deepoove."));
							}
						}));
				put("website", "http://www.deepoove.com/poi-tl");
			}
		};

		XWPFTemplate template = XWPFTemplate.compile("src/test/resources/PB.docx");

		// 测试是否映射正确
		RenderAPI.debug(template, datas);

		// 测试是否可以自渲染
		RenderAPI.selfRender(template);

		FileOutputStream out = new FileOutputStream("out_PB.docx");
		template.write(out);
		out.flush();
		out.close();
		template.close();
	}

}
