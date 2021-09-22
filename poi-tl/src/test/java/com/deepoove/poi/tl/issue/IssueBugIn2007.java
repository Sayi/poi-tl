/**
 * 
 */
package com.deepoove.poi.tl.issue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;

/**
 * @author fverse
 *
 */
public class IssueBugIn2007 {

	@Test
	public void test() throws IOException {
        Map<String, Object> model = new HashMap<>();
        model.put("ph", "qweasdzxc");
        
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/issue/bug-in-2007.docx");
        template.render(model).writeToFile("out_bug-in-2007.docx");
	}
	
}
