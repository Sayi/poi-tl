package com.deepoove.poi.tl.example.policy;

import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.tl.example.ExperienceData;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author WQ
 * @date 2021/11/20
 */
public class MyUpdateExcelPolicy extends AbstractRenderPolicy<List<ExperienceData>> {

	@Override
	public void doRender(RenderContext<List<ExperienceData>> context) throws Exception {
		NiceXWPFDocument doc = context.getXWPFDocument();
		List<ExperienceData> dataList = context.getData();
		if (dataList == null || dataList.isEmpty()) {
			return;
		}
		List<PackagePart> allEmbeddedParts = doc.getAllEmbeddedParts();
		for (PackagePart allEmbeddedPart : allEmbeddedParts) {
			String extension = allEmbeddedPart.getPartName().getExtension();
			if (
				extension.equals("xls")
				|| extension.equals("xlsx")
			) {
				try (
					InputStream inputStream = allEmbeddedPart.getInputStream();
					OutputStream outputStream = allEmbeddedPart.getOutputStream()
				) {

					Workbook workbook = WorkbookFactory.create(inputStream);
					Sheet sheet = workbook.getSheetAt(0);

					for (int i = 0; i < dataList.size(); i++) {
						ExperienceData experienceData = dataList.get(i);
						Row row = sheet.createRow(i + 1);
						row.createCell(0).setCellValue(experienceData.getCompany());;
						row.createCell(1).setCellValue(experienceData.getDepartment());;
						row.createCell(2).setCellValue(experienceData.getTime());;
						row.createCell(3).setCellValue(experienceData.getPosition());;
					}

					workbook.write(outputStream);
				}
			} else if (extension.equals("docx")) {
				try (
					InputStream inputStream = allEmbeddedPart.getInputStream();
					OutputStream outputStream = allEmbeddedPart.getOutputStream()
				) {
					XWPFDocument xwpfDocument = new XWPFDocument(inputStream);
					List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();
					for (XWPFParagraph paragraph : paragraphs) {
						List<XWPFRun> runs = paragraph.getRuns();
						for (XWPFRun run : runs) {
							run.setText("hello", 0);
						}
					}
					xwpfDocument.write(outputStream);
				}
			}
		}

		context.getRun().setText("", 0);
	}


}
