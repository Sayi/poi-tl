package com.deepoove.poi.tl.util;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.render.RenderAPI;
import com.deepoove.poi.tl.source.DataSourceTest;
import com.deepoove.poi.util.CodeGenUtils;

public final class CodeGenUtilsTest {
	
	@Test
	public void testCodeGen(){
		XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template.docx");
		System.out.println(CodeGenUtils.generateJavaObject(template, "package com.deepoove.poi.tl.datasource;", "DataSourceTest"));
	}
	
	@Test
	public void testObject(){
		XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template.docx");
		DataSourceTest vo = new DataSourceTest();
		RenderAPI.debug(template, vo);
	}
}
