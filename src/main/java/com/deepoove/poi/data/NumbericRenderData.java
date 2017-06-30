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
package com.deepoove.poi.data;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat.Enum;

/**
 * @author Sayi
 * @version 0.0.5
 */
public class NumbericRenderData implements RenderData {

	private List<TextRenderData> numbers;

	private Pair<Enum, String> numFmt;

	/**
	 * 1. 2. 3.
	 */
	public static final Pair<Enum, String> FMT_DECIMAL = Pair.of(STNumberFormat.DECIMAL, "%1.");
	/**
	 * 1) 2) 3)
	 */
	public static final Pair<Enum, String> FMT_DECIMAL_PARENTHESES = Pair.of(STNumberFormat.DECIMAL,
			"%1)");
	/**
	 * ● ● ●
	 */
	public static final Pair<Enum, String> FMT_BULLET = Pair.of(STNumberFormat.BULLET, "●");
	/**
	 * a. b. c.
	 */
	public static final Pair<Enum, String> FMT_LOWER_LETTER = Pair.of(STNumberFormat.LOWER_LETTER,
			"%1.");
	/**
	 * i ⅱ ⅲ
	 */
	public static final Pair<Enum, String> FMT_LOWER_ROMAN = Pair.of(STNumberFormat.LOWER_ROMAN,
			"%1.");
	/**
	 * A. B. C.
	 */
	public static final Pair<Enum, String> FMT_UPPER_LETTER = Pair.of(STNumberFormat.UPPER_LETTER,
			"%1.");
	/**
	 * Ⅰ Ⅱ Ⅲ
	 */
	public static final Pair<Enum, String> FMT_UPPER_ROMAN = Pair.of(STNumberFormat.UPPER_ROMAN,
			"%1.");
//	/**
//	 * 一、 二、 三、
//	 */
//	public static final Pair<Enum, String> FMT_CHINESE_COUNTING_THOUSAND = Pair
//			.of(STNumberFormat.CHINESE_COUNTING_THOUSAND, "%1、");
//	/**
//	 * (一) (二) (三)
//	 */
//	public static final Pair<Enum, String> FMT_CHINESE_COUNTING_THOUSAND_PARENTHESES = Pair
//			.of(STNumberFormat.CHINESE_COUNTING_THOUSAND, "(%1)");

	public NumbericRenderData(Pair<Enum, String> numFmt, List<TextRenderData> numbers) {
		this.numFmt = numFmt;
		this.numbers = numbers;
	}

	public NumbericRenderData(List<TextRenderData> numbers) {
		this(FMT_BULLET, numbers);
	}

	public List<TextRenderData> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<TextRenderData> numbers) {
		this.numbers = numbers;
	}

	public Pair<Enum, String> getNumFmt() {
		return numFmt;
	}

	public void setNumFmt(Pair<Enum, String> numFmt) {
		this.numFmt = numFmt;
	}

}
