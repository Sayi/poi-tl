package com.deepoove.poi.tl.mypolicy;

import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;

public class MyTableRenderPolicy extends DynamicTableRenderPolicy {

	@Override
	public void render(XWPFTable table, Object data) {
		TableRenderData tableData = (TableRenderData) data;
		List<Object> datas = tableData.getDatas();
		if (null == datas || datas.isEmpty()) {
			table.getRow(1).getCell(0).removeParagraph(0);
			table.getRow(1).getCell(0).setText(tableData.getNoDatadesc());
		} else {
			// XWPFTableRow row = table.getRow(1);

			table.removeRow(1);
			for (Object obj : datas) {
				XWPFTableRow row = table.insertNewTableRow(1);
				String str = obj.toString();
				String[] split = str.split(";");
				XWPFTableCell cell0 = row.createCell();
				cell0.setText(split[0]);
				XWPFTableCell cell1 = row.createCell();
				cell1.setText(split[1]);
				XWPFTableCell cell2 = row.createCell();
				cell2.setText(split[2]);
			}
		}

	}

}
