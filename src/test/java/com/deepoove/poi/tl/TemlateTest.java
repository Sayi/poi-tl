package com.deepoove.poi.tl;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.SimpleTableRenderPolicy;
import com.deepoove.poi.render.RenderAPI;

public class TemlateTest {

	@Test
	public void testName() throws Exception {
		Map<String, Object> datas = new HashMap<String, Object>(){{
			put("author", new TextRenderData("e14800", "author:\\nsayi\\n卅一"));
			put("aad", new TextRenderData("我相信"));
			put("madan", new TextRenderData("梦。"));
			put("day", new TextRenderData("2015-03-24"));
			put("ada", new TextRenderData("for U"));
			put("d_man", new TextRenderData("for man"));
			put("m_vin", new TextRenderData("2015323125r329487239"));
			put("d_date", new TextRenderData("for date"));
			put("date", new TextRenderData("2014 09 05"));
			put("icon", new PictureRenderData(50, 50, "src/test/resources/image24.png"));
			put("fuck_image", new PictureRenderData(150, 150, "src/test/resources/image24.png"));
			
			String code = "detect_22";
			put(code + "pic",  new PictureRenderData(8, 8, "src/test/resources/image24.png"));
			put(code, new TextRenderData("ff4200", "后争气"));
			put(code + "pro", new TextRenderData("ff4200", "传动轴不灵"));
			code = "detect_3";
			put(code + "pic",  new PictureRenderData(8, 8, "src/test/resources/image24.png"));
			put(code, new TextRenderData("ff4200", "后争气"));
			put(code + "pro", new TextRenderData("ff4200", "传动轴不灵"));
			
//			put("table0", new TableRenderData(new ArrayList<RenderData>(){{
//				add(new TextRenderData("d0d0d0", "过户主体"));
//				add(new TextRenderData("d0d0d0", "过户时间"));
//				add(new TextRenderData("d0d0d0", "过户方式"));
//				//add(new TextRenderData("d0d0d0", "过户text"));
//			}},new ArrayList<Object>(){{
//				add("sayi;2014;4544");
//				add("sayi2;2014;4544");
//			}}, "该车上牌后第一次出售，无过户记录"));
			put("table", new TableRenderData(new ArrayList<RenderData>(){{
				add(new TextRenderData("d0d0d0", "过户主体"));
				add(new TextRenderData("d0d0d0", "过户时间"));
				add(new TextRenderData("d0d0d0", "过户方式"));
				//add(new TextRenderData("d0d0d0", "过户text"));
			}},new ArrayList<Object>(){{
//				add("sayi;2014;4544");
//				add("sayi2;2014;4544");
			}}, "该车上牌后第一次出售，无过户记录", 9500));
		}};

		XWPFTemplate doc = XWPFTemplate
				.create("src/test/resources/template.docx");
		doc.registerPolicy("table0", new SimpleTableRenderPolicy());
		doc.registerPolicy("table", new SimpleTableRenderPolicy());
		
		RenderAPI.debug(doc, datas);
		RenderAPI.render(doc, datas);
		//RenderAPI.elegantRender(doc);

		FileOutputStream out = new FileOutputStream("modify.docx");
		doc.write(out);
		out.flush();
		out.close();
	}
	
	
	@Test
	public void testTable() throws Exception {
		Map<String, Object> datas = new HashMap<String, Object>(){{
			put("author", new TextRenderData("e14800", "author:\\nsayi\\n卅一"));
			put("china", new TableRenderData(new ArrayList<RenderData>(){{
				add(new TextRenderData("1E915D", "省份"));
				add(new TextRenderData("1E915D", "城市"));
				add(new TextRenderData("1E915D", "code"));
				//add(new TextRenderData("d0d0d0", "过户text"));
			}},new ArrayList<Object>(){{
//				add("sayi;2014;4544");
//				add("sayi2;2014;4544");
			}}, "无记录", 9500));
		}};
		XWPFTemplate doc = XWPFTemplate
				.create("src/test/resources/PB.docx");
		doc.registerPolicy("china", new SimpleTableRenderPolicy());
		
		RenderAPI.debug(doc, datas);
		RenderAPI.render(doc, datas);

		FileOutputStream out = new FileOutputStream("modify.docx");
		doc.write(out);
		out.flush();
		out.close();
	}
	
	@Test
	public void testHeaderFooter() throws Exception {
		Map<String, Object> datas = new HashMap<String, Object>(){{
			put("header_version", new TextRenderData("e14800", "卅一"));
			put("header_logo",  new PictureRenderData(8, 8, "src/test/resources/image24.png"));
			
		}};
		XWPFTemplate doc = XWPFTemplate
				.create("src/test/resources/PB.docx");
		
		RenderAPI.debug(doc, datas);
		RenderAPI.render(doc, datas);
		
		FileOutputStream out = new FileOutputStream("modify.docx");
		doc.write(out);
		out.flush();
		out.close();
	}
	
	@Test
	public void testDynamicTable() throws Exception {
		Map<String, Object> datas = new HashMap<String, Object>(){{
			put("author", new TextRenderData("e14800", "author:\\nsayi\\n卅一"));
			put("table", new TableRenderData(new ArrayList<RenderData>(){{
				add(new TextRenderData("1E915D", "省份"));
				add(new TextRenderData("1E915D", "城市"));
				add(new TextRenderData("1E915D", "code"));
				//add(new TextRenderData("d0d0d0", "过户text"));
			}},new ArrayList<Object>(){{
				//add("sayi;2014;4544");
				//add("sayi2;2014;4544");
			}}, "无记录",0));
		}};
		XWPFTemplate doc = XWPFTemplate
				.create("src/test/resources/template_ultimate_A.docx");
		doc.registerPolicy("table", new MyTableRenderPolicy());
		
		RenderAPI.debug(doc, datas);
		RenderAPI.render(doc, datas);

		FileOutputStream out = new FileOutputStream("modify.docx");
		doc.write(out);
		out.flush();
		out.close();
	}
	
	@Test
	public void testAddGramer() throws Exception {
		Map<String, Object> datas = new HashMap<String, Object>(){{
			put("author", new TextRenderData("e14800", "author:\\nsayi\\n卅一"));
		}};
		XWPFTemplate doc = XWPFTemplate
				.create("src/test/resources/PG.docx");
		//doc.registerPolicy("table", new MyTableRenderPolicy());
		
		RenderAPI.debug(doc, datas);
		RenderAPI.render(doc, datas);
		
		FileOutputStream out = new FileOutputStream("modify.docx");
		doc.write(out);
		out.flush();
		out.close();
	}
	
	
	
	

}
