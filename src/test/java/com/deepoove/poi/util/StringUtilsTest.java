package com.deepoove.poi.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author wangzz
 */
public class StringUtilsTest {

	@Test
	public void testUncapitalize() {
		assertEquals(StringUtils.uncapitalize(null), null);
		assertEquals(StringUtils.uncapitalize(""), "");
		assertEquals(StringUtils.uncapitalize("cat"), "cat");
		assertEquals(StringUtils.uncapitalize("CatDog"), "catDog");
		assertEquals(StringUtils.uncapitalize("CATDog"), "cATDog");
	}

}
