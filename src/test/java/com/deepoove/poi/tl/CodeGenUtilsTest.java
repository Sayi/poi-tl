package com.deepoove.poi.tl;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.render.RenderAPI;
import com.deepoove.poi.tl.datasource.ComplexVOTest;
import com.deepoove.poi.util.CodeGenUtils;

public final class CodeGenUtilsTest {
	
	@Test
	public void testCodeGen(){
		XWPFTemplate template = XWPFTemplate.compile("src/test/resources/complex.docx");
		System.out.println(CodeGenUtils.generateJavaObject(template, "package com.deepoove.poi.tl.datasource;", "MyData"));
	}
	
	@Test
	public void testObject(){
		XWPFTemplate template = XWPFTemplate.compile("src/test/resources/complex.docx");
		ComplexVOTest vo = new ComplexVOTest();
		RenderAPI.debug(template, vo);
	}
}
