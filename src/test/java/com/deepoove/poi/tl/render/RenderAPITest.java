package com.deepoove.poi.tl.render;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.render.RenderAPI;

/**
 * @author Sayi
 * @version 0.0.4
 */
public class RenderAPITest {

	@SuppressWarnings({ "serial", "deprecation" })
	@Test
	public void testRenderAPI() throws Exception {
		Map<String, Object> datas = new HashMap<String, Object>() {
			{
				put("author", "deepoove");
				put("date", "2016-09-40");
				put("dfa", "呵呵");
				put("fafd", "我被渲染了");
			}
		};
		XWPFTemplate template = XWPFTemplate.compile("src/test/resources/run_merge.docx");

		// 测试是否映射正确
		RenderAPI.debug(template, datas);

		// 测试是否可以自渲染
		RenderAPI.selfRender(template);

		FileOutputStream out = new FileOutputStream("out_self.docx");
		template.write(out);
		out.flush();
		out.close();
		template.close();
	}
	
	@SuppressWarnings("serial")
	@Test
	public void testRenderMerge() throws Exception {
		Map<String, Object> datas = new HashMap<String, Object>() {
			{
				put("author", "deepoove");
				put("date", "2016-09-40");
				put("dfa", "呵呵");
				put("fafd", "我被渲染了");
			}
		};

		XWPFTemplate template = XWPFTemplate.compile("src/test/resources/run_merge.docx").render(datas);
		;

		FileOutputStream out = new FileOutputStream("out_template_run_merge.docx");
		template.write(out);
		out.flush();
		out.close();
		template.close();
	}

}
