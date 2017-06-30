package com.deepoove.poi.policy;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.xwpf.usermodel.NumberingWrapper;
import org.apache.poi.xwpf.usermodel.XWPFAbstractNum;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLvl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat.Enum;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;

/**
 * @author Sayi
 * @version 0.0.5 
 */
public class NumbericRenderPolicy implements RenderPolicy {

	@Override
	public void render(ElementTemplate runTemplateP, Object data, XWPFTemplate template) {
		NiceXWPFDocument doc = template.getXWPFDocument();
		RunTemplate runTemplate = (RunTemplate) runTemplateP;
		XWPFRun run = runTemplate.getRun();
		if (null == data) return;

		NumbericRenderData numbericData = (NumbericRenderData) data;
		List<TextRenderData> datas = numbericData.getNumbers();
		Pair<Enum, String> numFmt = numbericData.getNumFmt();
		if (datas == null || datas.isEmpty()) {
			return;
		} else {

			XWPFNumbering numbering = doc.getNumbering();
			if (null == numbering) {
				numbering = doc.createNumbering();
			}

			NumberingWrapper numberingWrapper = new NumberingWrapper(numbering);
			CTAbstractNum cTAbstractNum = CTAbstractNum.Factory.newInstance();
			// if we have an existing document, we must determine the next
			// free number first.
			cTAbstractNum.setAbstractNumId(
					BigInteger.valueOf(numberingWrapper.getAbstractNumsSize() + 10));

			Enum fmt = numFmt.getLeft();
			String val = numFmt.getRight();
			CTLvl cTLvl = cTAbstractNum.addNewLvl();
			cTLvl.addNewNumFmt().setVal(fmt);
			cTLvl.addNewLvlText().setVal(val);
			cTLvl.addNewStart().setVal(BigInteger.valueOf(1));
			if (fmt == STNumberFormat.BULLET) {
				// cTLvl.setIlvl(BigInteger.valueOf(0));
				cTLvl.addNewLvlJc().setVal(STJc.LEFT);
			} else {
				// cTLvl.setIlvl(BigInteger.valueOf(0));
			}

			XWPFAbstractNum abstractNum = new XWPFAbstractNum(cTAbstractNum);
			BigInteger abstractNumID = numbering.addAbstractNum(abstractNum);

			BigInteger numID = numbering.addNum(abstractNumID);
			// doc.insertNewParagraph(run);
			for (TextRenderData line : datas) {
				XWPFParagraph paragraph = doc.insertNewParagraph(run);
				paragraph.setNumID(numID);
				XWPFRun run1 = paragraph.createRun();
				styleRun(run1, line.getStyle());
				run1.setText(line.getText());
			}
			// doc.insertNewParagraph(run);
		}
		runTemplate.getRun().setText("", 0);
	}
	
	private void styleRun(XWPFRun run, Style style) {
		if (null != style) {
			String color = style.getColor();
			String fontFamily = style.getFontFamily();
			int fontSize = style.getFontSize();
			Boolean bold = style.isBold();
			Boolean italic = style.isItalic();
			Boolean strike = style.isStrike();
			if (null != color) run.setColor(color);
			if (0 != fontSize) run.setFontSize(fontSize);
			if (null != fontFamily) run.setFontFamily(fontFamily);
			if (null != bold) run.setBold(bold);
			if (null != italic) run.setItalic(italic);
			if (null != strike) run.setStrikeThrough(strike);
		}
	}

}
