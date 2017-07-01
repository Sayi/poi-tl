package com.deepoove.poi.tl;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.TextRenderData;

/**
 * 列表模板
 * 
 * @author Sayi
 * @version 1.0.0
 */
public class TableRenderTest {

	@SuppressWarnings("serial")
	@Test
	public void testTable() throws Exception {
		Map<String, Object> datas = new HashMap<String, Object>() {
			{
				// 有表格头 有数据
				put("table", new TableRenderData(new ArrayList<RenderData>() {
					{
						add(new TextRenderData("1E915D", "province"));
						add(new TextRenderData("1E915D", "city"));
					}
				}, new ArrayList<Object>() {
					{
						add("beijing;beijing");
						add("zhejiang;hangzhou");
					}
				}, "no datas", 0));
				// 没有表格头 没有数据
				put("blank_table", new TableRenderData(null, null, "no datas", 0));
				// 有数据，没有表格头
				put("no_header_table", new TableRenderData(null, new ArrayList<Object>() {
					{
						add("beijing;beijing");
						add("");
					}
				}, "no datas", 0));
				// 有表格头 没有数据
				put("no_content_table", new TableRenderData(new ArrayList<RenderData>() {
					{
						add(new TextRenderData("1E915D", "province"));
						add(new TextRenderData("1E915D", "city"));
					}
				}, null, "no datas", 8000));
				put("no_content_table2", new TableRenderData(new ArrayList<RenderData>() {
					{
						add(new TextRenderData("1E915D", "province"));
					}
				}, null, "no datas", 8000));
			}
		};
		XWPFTemplate template = XWPFTemplate.compile("src/test/resources/table.docx").render(datas);

		FileOutputStream out = new FileOutputStream("out_table.docx");
		template.write(out);
		out.flush();
		out.close();
		template.close();
	}
}
