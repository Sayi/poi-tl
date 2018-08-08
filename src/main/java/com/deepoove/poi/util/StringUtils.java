/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.util;

/**
 * {@link java.lang.String}工具类
 * @author wangzz
 */
public abstract class StringUtils {

	/**
	 * 将首字母转大写<br>
	 * 如果传入null则直接返回null.
	 * 
	 * <pre>
	 * StringUtils.uncapitalize(null)  = null
	 * StringUtils.uncapitalize("")    = ""
	 * StringUtils.uncapitalize("cat") = "cat"
	 * StringUtils.uncapitalize("Cat") = "cat"
	 * StringUtils.uncapitalize("CATct") = "cATct"
	 * </pre>
	 *
	 * @param 需要转换的字符串,可以为null
	 * @return 转换后的字符串
	 */
	public static String uncapitalize(final String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		final int firstCodepoint = str.codePointAt(0);
		final int newCodePoint = Character.toLowerCase(firstCodepoint);
		if (firstCodepoint == newCodePoint) {
			return str;
		}
		final int newCodePoints[] = new int[strLen];
		int outOffset = 0;
		newCodePoints[outOffset++] = newCodePoint;
		for (int inOffset = Character.charCount(firstCodepoint); inOffset < strLen;) {
			final int codepoint = str.codePointAt(inOffset);
			newCodePoints[outOffset++] = codepoint;
			inOffset += Character.charCount(codepoint);
		}
		return new String(newCodePoints, 0, outOffset);
	}

	/**
	 * 根据开始,结束区间截取字符串<br>
	 * 若不存在开始结束区间则返回null. 若存在多个则返回第一个.
	 * @param str 需要截取的字符串
	 * @param open 开始标记字符
	 * @param close 结束标记字符
	 * @return 中间截取的字符
	 */
	public static String substringBetween(final String str, final String open, final String close) {
		if (str == null || open == null || close == null) {
			return null;
		}
		final int start = str.indexOf(open);
		if (start != -1) {
			final int end = str.indexOf(close, start + open.length());
			if (end != -1) {
				return str.substring(start + open.length(), end);
			}
		}
		return null;
	}

}
