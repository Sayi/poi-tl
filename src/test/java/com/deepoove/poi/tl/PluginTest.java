package com.deepoove.poi.tl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.policy.SelfRenderPolicy;
import com.deepoove.poi.policy.TextRenderPolicy;

/**
 * 插件
 * 
 * @author Sayi
 * @version 1.0.0
 */
public class PluginTest {

	@SuppressWarnings("serial")
	@Test
	public void testCustomGramerPlugin() throws Exception {
		Map<String, Object> datas = new HashMap<String, Object>() {
			{
				put("text", "text value");
				put("custom_template_gramer", "custom_template_gramer value");
			}
		};

		//新增语法插件
		Configure plugin = Configure.createDefault().plugin('%', new TextRenderPolicy());

		XWPFTemplate template = XWPFTemplate
				.compile(new File("src/test/resources/plugin.docx"), plugin);
		
		//自定义策略
		template.registerPolicy("text", new SelfRenderPolicy());
		
		//渲染
		template.render(datas);

		FileOutputStream out = new FileOutputStream("out_plugin.docx");
		template.write(out);
		out.flush();
		out.close();
		template.close();
	}
	
	

}
