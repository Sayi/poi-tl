package com.deepoove.poi.policy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.GramerSymbol;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.policy.TextRenderPolicy;
import com.deepoove.poi.render.RenderAPI;
import com.deepoove.poi.resolver.TemplateResolver;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.StyleUtils;

public class TableListRenderPolicy implements RenderPolicy
{
	private static Logger logger = LoggerFactory.getLogger(TableListRenderPolicy.class);

	public static String dataRegex = "\\$\\{(\\w+)\\}";
	public static Pattern dataPattern = Pattern.compile(dataRegex);

	public char getTag()
	{
		return GramerSymbol.TABLELIST.getSymbol();
	}

	public char getDataTag()
	{
		return GramerSymbol.TABLELISTCELL.getSymbol();
	}

	public String getTagStr(String tag)
	{
		return "{{" + getTag() + tag + "}}";
	}

	@Override
	public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template)
	{
		NiceXWPFDocument doc = template.getXWPFDocument();
		RunTemplate runTemplate = (RunTemplate) eleTemplate;
		XWPFRun run = runTemplate.getRun();
		try
		{
			XmlCursor newCursor = ((XWPFParagraph) run.getParent()).getCTP().newCursor();
			newCursor.toParent();
			// if (newCursor.getObject() instanceof CTTc)
			newCursor.toParent();
			newCursor.toParent();
			XmlObject object = newCursor.getObject();
			XWPFTable table = doc.getTable((CTTbl) object);
			render(table, data, runTemplate.getTagName(), template);
		}
		catch (Exception e)
		{
			logger.error("dynamic table error:" + e.getMessage(), e);
		}
	}

	public void render(XWPFTable table, Object data, String tagName, XWPFTemplate template)
	{
		List<XWPFTableRow> rowList = table.getRows();
		int index = 0;
		boolean findFlag = false;
		String tableListValue = "";
		for (; index < rowList.size(); index++)
		{
			XWPFTableRow xtr = rowList.get(index);
			List listTableCells = xtr.getTableCells();
			for (int j = 0; j < listTableCells.size(); j++)
			{
				XWPFTableCell cell0 = xtr.getCell(j);
				tableListValue = cell0.getText();
				if (cell0 != null && tableListValue.contains(getTagStr(tagName)))
				{
					table.removeRow(index);
					findFlag = true;
					break;
				}
			}
			if (findFlag)
			{
				break;
			}
		}

		if (!findFlag)
		{
			return;
		}

		tableListValue = tableListValue.replace(getTagStr(tagName), "");

		if (!validateNumber(tableListValue))
		{
			return;
		}
		int copyRow = Integer.parseInt(tableListValue);

		if (data == null)
		{
			deleteModel(table, index, copyRow);
			return;
		}

		List dataList = (List) data;
		if (dataList.isEmpty())
		{
			return;
		}
		Collections.reverse(dataList);
		List<XWPFTableRow> copyRowList = new ArrayList<XWPFTableRow>();
		for(int i=0;i<copyRow;i++)
		{
			copyRowList.add(table.getRow(index-1-i));
		}
		
		Map dataMap = null;
		for (int i = 0; i < dataList.size(); i++)
		{
			Object dataObj = dataList.get(i);
			if (dataObj instanceof Map)
			{
				dataMap = (Map) dataObj;
			}
			else
			{
				dataMap = RenderAPI.convert2Map(dataObj);
			}
			CTTbl ctTbl = table.getCTTbl();
			for(int j=0;j<copyRowList.size();j++)
			{
				XWPFTableRow xtr = copyRowList.get(j);
				XWPFTableRow xtrNew = table.insertNewTableRow(index);
				copyPro(xtr,xtrNew);
				changeRowValue(template, dataMap, xtrNew);
//					XWPFTableRow formatRow = table.getRow(index);
//					formatRow.getTableCells().get(0).setText("1");
//					changeRowValue(template, dataMap, formatRow);
//					 formatRow = table.getRow(index+1);
//					 formatRow.getTableCells().get(0).setText("2");
//					 changeRowValue(template, dataMap, formatRow);
//					 formatRow = table.getRow(index+2);
//					 changeRowValue(template, dataMap, formatRow);
			}
		}
		
		deleteModel(table, index, copyRow);
		
//		int formatInt = index - 1;
//		XWPFTableRow formatRow = table.getRow(formatInt);
//		for (int i = 0; i < 5; i++)
//		{
//			formatInt++;
//			if (table.addRow(formatRow, formatInt))
//			{
//				XWPFTableRow formatRow1 = table.getRow(formatInt + 1);
//			}
//
//		}
//		
		

		// if (macList == null || macList.isEmpty())
		// {
		// table.removeRow(formatInt);
		// return;
		// }

		// Map dataMap = new HashMap();
		// if(data instanceof Map)
		// {
		// dataMap = (Map) data;
		// }
		// else
		// {
		// dataMap = RenderAPI.convert2Map(data);
		// }
		

		// StyleUtils.styleRun(insertNewRun, run);
		// XWPFTableRow row = table.insertNewTableRow(formatInt);
		// createCell(row,"第二联系人");
		// createCell(row,"姓 名");
		// createCell(row,mac.getBmcName());
		// createCell(row,"关 系");
		// createCell(row,mac.getBmcRelationName());
		// createCell(row,"手 机");
		// createCell(row,mac.getBmcMobile());
		// createCell(row,"QQ/E-mail");
		// createCell(row,mac.getBmcQq());

		// MembershipApplicationContact mac = macList.get(0);

		// XWPFTableCell cell = table.getRow(formatInt).getCell(1);
		// new break line
		// String[] fragment = mac.getBmcMobile().split("\\n");
		// if (null != fragment)
		// {
		// cell.setText(fragment[0]);
		// for (int i = 1; i < fragment.length; i++)
		// {
		// XWPFParagraph addParagraph = cell.addParagraph();
		// addParagraph.createRun().setText(fragment[i]);
		// }
		// }
		//

		// startRow++;

		// Map map = RenderAPI.convert2Map(mac);
		// List<XWPFTableCell> tableCells = formatRow.getTableCells();
		// for(int j=0;j<tableCells.size();j++)
		// {
		// XWPFTableCell xtc = tableCells.get(j);
		// String cellValue = xtc.getText();
		//
		// Matcher m = pattern.matcher(cellValue);
		// while(m.find())
		// {
		// String key=m.group(1);
		// cellValue = cellValue.replace("${"+key+"}", (String)map.get(key));
		// }
		// xtc.setText(cellValue);
		//// xtc.getParagraphArray(pos)
		//// XWPFParagraph addParagraph = cell.addParagraph();
		//// addParagraph.createRun().setText(fragment[i]);
		// System.out.println(xtc.getText());
		// }

	}

	private void deleteModel(XWPFTable table, int index, int copyRow)
	{
		for(int i=0;i<copyRow;i++)
		{
			table.removeRow(index-1-i);
		}
	}

	private void copyPro(XWPFTableRow sourceRow, XWPFTableRow targetRow)
	{
		// 复制行属性
		targetRow.getCtRow().setTrPr(sourceRow.getCtRow().getTrPr());
		List<XWPFTableCell> cellList = sourceRow.getTableCells();
		if (null == cellList)
		{
			return;
		}
		// 添加列、复制列以及列中段落属性
		XWPFTableCell targetCell = null;
		for (XWPFTableCell sourceCell : cellList)
		{
			targetCell = targetRow.addNewTableCell();
			// 列属性
			targetCell.getCTTc().setTcPr(sourceCell.getCTTc().getTcPr());
			// 段落属性
			targetCell.getParagraphs().get(0).getCTP().setPPr(sourceCell.getParagraphs().get(0).getCTP().getPPr());
			List<XWPFRun> runs = sourceCell.getParagraphs().get(0).getRuns();
			for(int i=0;i<runs.size();i++)
			{
				XWPFRun extraRun = targetCell.getParagraphs().get(0).insertNewRun(i);
				StyleUtils.styleRun(extraRun, runs.get(i));
				extraRun.setText(runs.get(i).getText(0), 0);
			}
//			targetCell.setText(sourceCell.getText());
//			XWPFRun
		}
	}

	private void changeRowValue(XWPFTemplate template, Map dataMap, XWPFTableRow formatRow)
	{
		Configure config = Configure.createDefault().plugin(getDataTag(), new TextRenderPolicy());
		TemplateResolver tr = new TemplateResolver(config);
		List<ElementTemplate> elementTemplates = tr.parseTableRow(formatRow);
		RenderPolicy policy = null;
		for (ElementTemplate runTemplate : elementTemplates)
		{
			logger.debug("TagName:{}, Sign:{}", runTemplate.getTagName(), runTemplate.getSign());
			policy = config.getPolicy(runTemplate.getTagName(), runTemplate.getSign());
			if (null == policy)
				throw new RenderException("cannot find render policy: [" + runTemplate.getTagName() + "]");
			policy.render(runTemplate, dataMap.get(runTemplate.getTagName()), template);
		}
	}

	/**
	 * 
	 * @Title: validateNumber
	 * @Description: 检查是否全数字
	 * @param @param number
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean validateNumber(String number)
	{
		boolean flag = false;
		if (number != null)
		{
			Matcher m = null;
			Pattern p = Pattern.compile("^[0-9]+$");
			m = p.matcher(number);
			flag = m.matches();
		}

		return flag;

	}
}
