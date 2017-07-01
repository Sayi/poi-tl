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
package com.deepoove.poi.template.cell;

import org.apache.poi.xwpf.usermodel.XWPFTableCell;

import com.deepoove.poi.config.GramerSymbol;
import com.deepoove.poi.template.ElementTemplate;

public class CellTemplate extends ElementTemplate {

	protected XWPFTableCell cell;

	public static CellTemplate create(GramerSymbol parseGramer, String tagName,
			XWPFTableCell cell) {
		CellTemplate template = null;
		if (parseGramer == GramerSymbol.IMAGE) {
			template = new PictureCellTemplate();
		} else if (parseGramer == GramerSymbol.TEXT) {
			template = new TextCellTemplate();
		}
		template.tagName = tagName;
		template.cell = cell;
		return template;
	}

	/**
	 * @return the cell
	 */
	public XWPFTableCell getCell() {
		return cell;
	}

	/**
	 * @param cell
	 *            the cell to set
	 */
	public void setCell(XWPFTableCell cell) {
		this.cell = cell;
	}

}
