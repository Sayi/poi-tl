package com.deepoove.poi.config;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure.ConfigureBuilder;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.template.ElementTemplate;

/**
 * @author herowzz
 */
public class ConfigureTest {

	@Test
	public void testCreateDefault() {
		Configure configure = Configure.createDefault();
		assertEquals(configure.getGramerPrefix(), "{{");
		assertEquals(configure.getGramerSuffix(), "}}");

		Map<Character, RenderPolicy> policysMap = configure.getDefaultPolicys();
		Set<Character> charSet = configure.getGramerChars();
		for (GramerSymbol gramerSymbol : GramerSymbol.values()) {
			assertTrue(policysMap.containsKey(gramerSymbol.getSymbol()));
			assertTrue(charSet.contains(gramerSymbol.getSymbol()));
		}
	}

	@Test
	public void testNewBuilder() {
		ConfigureBuilder builder1 = Configure.newBuilder();
		ConfigureBuilder builder2 = Configure.newBuilder();
		assertNotEquals(builder1, builder2);
		assertNotEquals(builder1.hashCode(), builder2.hashCode());
	}

	@Test
	public void testPlugin() {
		char key = '?';
		Configure configure = Configure.createDefault();
		TestRenderPolicy testPolicy = new TestRenderPolicy();
		configure.plugin(key, testPolicy);
		assertTrue(configure.getDefaultPolicys().containsKey(key));
		assertEquals(configure.getDefaultPolicys().get(key), testPolicy);
		assertTrue(configure.getGramerChars().contains(key));
	}

	@Test
	public void testCustomPolicy() {
		String key = "test-custom";
		Configure configure = Configure.createDefault();
		TestRenderPolicy testPolicy = new TestRenderPolicy();
		configure.customPolicy(key, testPolicy);
		assertTrue(configure.getCustomPolicys().containsKey(key));
		assertEquals(configure.getCustomPolicys().get(key), testPolicy);
	}

	@Test
	public void testGetPolicy() {
		String key = "test-custom";
		Configure configure = Configure.createDefault();
		TestRenderPolicy testPolicy = new TestRenderPolicy();
		configure.customPolicy(key, testPolicy);
		assertEquals(configure.getPolicy(key, '#'), testPolicy);

		String keyNotFind = "test-not-find";
		Map<Character, RenderPolicy> policysMap = configure.getDefaultPolicys();
		for (GramerSymbol gramerSymbol : GramerSymbol.values()) {
			assertEquals(configure.getPolicy(keyNotFind, gramerSymbol.getSymbol()), policysMap.get(gramerSymbol.getSymbol()));
		}
		assertNull(configure.getPolicy(keyNotFind, '?'));
	}

	class TestRenderPolicy implements RenderPolicy {

		@Override
		public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
		}

	}

}
