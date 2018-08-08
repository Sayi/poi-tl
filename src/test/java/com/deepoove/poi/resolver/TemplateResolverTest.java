package com.deepoove.poi.resolver;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.deepoove.poi.config.Configure;

/**
 * @author herowzz
 */
public class TemplateResolverTest {

	@Test
	public void testTemplateResolver() {
		Configure configure = Configure.createDefault();
		TemplateResolver templateResolver = new TemplateResolver(configure);
		Pattern TAG_PATTERN = templateResolver.TAG_PATTERN;
		assertFalse(TAG_PATTERN.matcher("aa{%name}vv").find());
		for (Character c : configure.getGramerChars()) {
			Matcher matcher = TAG_PATTERN.matcher("aaa{{" + c + "name.nnn.ccc.sss}}bbb");
			assertTrue(matcher.find());
			assertEquals(matcher.group(), "{{" + c + "name.nnn.ccc.sss}}");
		}
		assertFalse(TAG_PATTERN.matcher("aa{{~name}}vv").find());
		assertFalse(TAG_PATTERN.matcher("aa{{^name}}vv").find());
		assertFalse(TAG_PATTERN.matcher("aa{{%name}}vv").find());

		Matcher matcherChn = TAG_PATTERN.matcher("aaa地方{{张三3.家庭2.姊妹1.钱5}}bbb测测");
		assertTrue(matcherChn.find());
		assertEquals(matcherChn.group(), "{{张三3.家庭2.姊妹1.钱5}}");

	}

}
