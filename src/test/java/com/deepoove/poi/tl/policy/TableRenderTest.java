package com.deepoove.poi.tl.policy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.Cells;
import com.deepoove.poi.data.MergeCellRule;
import com.deepoove.poi.data.MergeCellRule.Grid;
import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.data.style.BorderStyle;
import com.deepoove.poi.data.style.CellStyle;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.data.style.RowStyle;
import com.deepoove.poi.data.style.TableStyle;
import com.deepoove.poi.policy.TableRenderPolicy;
import com.deepoove.poi.util.UnitUtils;

@DisplayName("Table Render test case")
public class TableRenderTest {

    @SuppressWarnings("serial")
    @Test
    public void testTable() throws Exception {
        RowRenderData row0, row1, row2;
        CellRenderData cell, cell1, cell2;

        cell = new CellRenderData();
        List<ParagraphRenderData> paragraphs = new ArrayList<>();
        paragraphs.add(Paragraphs.of().addText(Texts.of("Hello").color("ffffff").create()).create());
        paragraphs.add(Paragraphs.of().addText(Texts.of("World").color("ffffff").bold().create()).left().create());
        cell.setParagraphs(paragraphs);

        cell1 = new CellRenderData();
        paragraphs = new ArrayList<>();
        paragraphs.add(Paragraphs.of().addText("document").create());
        cell1.setParagraphs(paragraphs);

        cell2 = new CellRenderData();
        paragraphs = new ArrayList<>();
        paragraphs.add(Paragraphs.of().addPicture(Pictures.ofLocal("src/test/resources/sayi.png").size(40, 40).create())
                .create());
        paragraphs.add(Paragraphs.of().addText("Sayi").create());
        CellStyle cellStyle = new CellStyle();
        cellStyle.setVertAlign(XWPFVertAlign.CENTER);
        cellStyle.setDefaultParagraphStyle(ParagraphStyle.builder().withAlign(ParagraphAlignment.CENTER).build());
        cell2.setCellStyle(cellStyle);
        cell2.setParagraphs(paragraphs);

        row0 = new RowRenderData();
        RowStyle rowStyle = new RowStyle();
        CellStyle defaultCellStyle = new CellStyle();
        defaultCellStyle.setBackgroundColor("f58d71");
        defaultCellStyle.setVertAlign(XWPFVertAlign.CENTER);
        defaultCellStyle.setDefaultParagraphStyle(ParagraphStyle.builder().withAlign(ParagraphAlignment.CENTER).build());
        rowStyle.setDefaultCellStyle(defaultCellStyle);
        rowStyle.setHeight(UnitUtils.cm2Twips(2.54f));

        row0.setRowStyle(rowStyle);
        row0.addCell(cell).addCell(cell).addCell(cell).addCell(cell);

        row1 = new RowRenderData();
        row1.addCell(cell2).addCell(cell1).addCell(cell1).addCell(cell1);

        row2 = new RowRenderData();
        row2.addCell(cell2).addCell(cell1).addCell(cell1).addCell(cell1);

        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                TableRenderData table = new TableRenderData();
                TableStyle tableStyle = new TableStyle();
//                tableStyle.setWidth("100%");
//                tableStyle.setWidth("auto");
                tableStyle.setWidth(UnitUtils.cm2Twips(14.63f) + "");
                int[] colWidth = new int[] { UnitUtils.cm2Twips(5.63f), UnitUtils.cm2Twips(3.0f),
                        UnitUtils.cm2Twips(3.0f), UnitUtils.cm2Twips(3.0f) };
                tableStyle.setColWidths(colWidth);
                tableStyle.setLeftBorder(BorderStyle.DEFAULT);
                tableStyle.setRightBorder(BorderStyle.DEFAULT);
                tableStyle.setTopBorder(BorderStyle.DEFAULT);
                tableStyle.setBottomBorder(BorderStyle.DEFAULT);
                tableStyle.setInsideHBorder(BorderStyle.DEFAULT);
                tableStyle.setInsideVBorder(BorderStyle.DEFAULT);
                table.setTableStyle(tableStyle);

                table.addRow(row0).addRow(row1).addRow(row2);
                put("table", table);
            }
        };

        Configure config = Configure.newBuilder().bind("table", new TableRenderPolicy()).build();
        XWPFTemplate.compile("src/test/resources/template/render_tablev2.docx", config).render(datas)
                .writeToFile("out_render_tablev2.docx");

    }

    @SuppressWarnings("serial")
    @Test
    public void testTableByBuilder() throws Exception {
        RowRenderData row0, row1, row2;
        CellRenderData cell, cell1, cell2;

        cell = Cells.of(Texts.of("Hello").color("ffffff").create())
                .addParagraph(Paragraphs.of(Texts.of("World").color("ffffff").bold().create()).left().create())
                .create();
        cell1 = Cells.of("document").create();
        cell2 = Cells.of(Pictures.ofLocal("src/test/resources/sayi.png").size(40, 40).create()).addParagraph("Sayi")
                .center().create();

        row0 = Rows.of(cell, cell, cell, cell).center().rowHeight(2.54f).bgColor("f58d71").create();
        row1 = Rows.of(cell2, cell1, cell1, cell1).create();
        row2 = Rows.of(cell2, cell1, cell1, cell1).create();

        TableRenderData table = Tables.of(row0, row1, row2).width(14.63f, new double[] { 5.63f, 3.0f, 3.0f, 3.0f })
                .border(BorderStyle.DEFAULT).create();

        RowRenderData rowNoData = Rows.of("没有数据", null, null, null).create();
        RowRenderData header = Rows.of("列0", "列1", "列2", "列3").horizontalCenter().bgColor("f58d71").create();
        TableRenderData table1 = Tables.of(header, rowNoData).border(BorderStyle.DEFAULT).create();
        MergeCellRule rule = MergeCellRule.builder().map(Grid.of(1, 0), Grid.of(1, 3)).build();
        table1.setMergeRule(rule);

        TableRenderData table2 = Tables.of(new String[][] { new String[] { "00", "01", "02", "03", "04" },
                new String[] { "10", "11", "12", "13", "14" }, new String[] { "20", "21", "22", "23", "24" },
                new String[] { "30", "31", "32", "33", "34" } }).border(BorderStyle.DEFAULT).create();
        MergeCellRule rule2 = MergeCellRule.builder().map(Grid.of(0, 0), Grid.of(1, 1))
                .map(Grid.of(1, 2), Grid.of(2, 3)).map(Grid.of(2, 0), Grid.of(2, 1)).map(Grid.of(1, 4), Grid.of(3, 4))
                .map(Grid.of(3, 1), Grid.of(3, 2))
                .build();
        table2.setMergeRule(rule2);

        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("table", table);
                put("no_data_table", table1);
                put("complex_table", table2);
            }
        };

        Configure config = Configure.newBuilder().bind("table", new TableRenderPolicy())
                .bind("complex_table", new TableRenderPolicy()).bind("no_data_table", new TableRenderPolicy()).build();
        XWPFTemplate.compile("src/test/resources/template/render_tablev2.docx", config).render(datas)
                .writeToFile("out_render_table_builder.docx");

    }
}
