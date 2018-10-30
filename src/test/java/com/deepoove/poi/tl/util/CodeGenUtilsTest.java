package com.deepoove.poi.tl.util;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.render.RenderAPI;
import com.deepoove.poi.tl.source.MyDataModel;
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
		MyDataModel vo = new MyDataModel();
//		RenderAPI.debug(template, vo);
	}
}
