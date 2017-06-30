package org.apache.poi.xwpf.usermodel;

import java.util.List;

/**
 * This is a utility class so that I can get access to the protected fields
 * within XWPFNumbering.
 */
public class NumberingWrapper {

	private final XWPFNumbering numbering;

	public NumberingWrapper(XWPFNumbering numbering) {
		this.numbering = numbering;
	}

	public List<XWPFAbstractNum> getAbstractNums() {
		return numbering.abstractNums;
	}

	public List<XWPFNum> getNums() {
		return numbering.nums;
	}

	public XWPFNumbering getNumbering() {
		return numbering;
	}

	public int getAbstractNumsSize() {
		return numbering.abstractNums == null ? 0 : numbering.abstractNums.size();
	}

}
