package com.deepoove.poi.tl.issue;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.data.Documents;
import com.deepoove.poi.data.Includes;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.tl.source.XWPFTestSupport;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

public class Issue966 {

	@Test
	public void test966() throws IOException {
		String text = "{{+docx}}";
		DocumentRenderData data = Documents.of().addParagraph(Paragraphs.of(text).addParagraph(Paragraphs.of("123").create()).create()).create();
		XWPFTemplate template = XWPFTemplate.create(data);
		XWPFTemplate xwpfTemplate = XWPFTestSupport.readNewTemplate(template);
		xwpfTemplate.render(new HashMap<String, Object>() {
			{
				put("docx", Includes.ofLocal("src/test/resources/issue/966.docx")
					.create());
			}
		}).writeToFile("target/out_966.docx");
	}

}
