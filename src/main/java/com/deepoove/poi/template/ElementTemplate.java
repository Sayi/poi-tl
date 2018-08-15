/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.template;

public class ElementTemplate {
	protected Character sign;
	protected String tagName;
	protected String fullName;
	protected String source;

	public ElementTemplate() {
	}

	/**
	 * @return the tagName
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * @param tagName the tagName to set
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Character getSign() {
		return sign;
	}

	public void setSign(Character sign) {
		this.sign = sign;
	}

	public String getFullName() {
		return fullName.replaceAll("“|”|’|'", "\"");
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
