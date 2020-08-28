package com.deepoove.poi.tl.policy;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.CellV2RenderData;
import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.RowV2RenderData;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.data.style.BorderStyle;
import com.deepoove.poi.data.style.CellStyle;
import com.deepoove.poi.data.style.RowStyle;
import com.deepoove.poi.data.style.TableV2Style;
import com.deepoove.poi.policy.TableRenderPolicy;
import com.deepoove.poi.util.UnitUtils;

@DisplayName("Table Render test case")
public class TableRenderTest {

    RowV2RenderData row0, row1, row2;
    CellV2RenderData cell, cell1, cell2;

    @BeforeEach
    public void init() {
        cell = new CellV2RenderData();
        List<ParagraphRenderData> paragraphs = new ArrayList<>();
        paragraphs.add(Paragraphs.of().addText(Texts.of("Hello").color("ffffff").create()).create());
        paragraphs.add(Paragraphs.of().addText(Texts.of("World").color("ffffff").bold().create()).left().create());
        cell.setParagraphs(paragraphs);

        cell1 = new CellV2RenderData();
        paragraphs = new ArrayList<>();
        paragraphs.add(Paragraphs.of().addText("document").create());
        cell1.setParagraphs(paragraphs);

        cell2 = new CellV2RenderData();
        paragraphs = new ArrayList<>();
        paragraphs.add(Paragraphs.of().addPicture(Pictures.ofLocal("src/test/resources/sayi.png").size(40, 40).create())
                .create());
        paragraphs.add(Paragraphs.of().addText("Sayi").create());
        CellStyle cellStyle = new CellStyle();
        cellStyle.setAlign(XWPFVertAlign.CENTER);
        cellStyle.setDefaultParagraphAlign(ParagraphAlignment.CENTER);
        cell2.setCellStyle(cellStyle);
        cell2.setParagraphs(paragraphs);

        row0 = new RowV2RenderData();
        RowStyle rowStyle = new RowStyle();
        CellStyle defaultCellStyle = new CellStyle();
        defaultCellStyle.setBackgroundColor("f58d71");
        defaultCellStyle.setAlign(XWPFVertAlign.CENTER);
        defaultCellStyle.setDefaultParagraphAlign(ParagraphAlignment.CENTER);
        rowStyle.setDefaultCellStyle(defaultCellStyle);
        rowStyle.setHeight(UnitUtils.cm2Twips(2.54f));

        row0.setRowStyle(rowStyle);
        row0.addCell(cell).addCell(cell).addCell(cell).addCell(cell);

        row1 = new RowV2RenderData();
        row1.addCell(cell2).addCell(cell1).addCell(cell1).addCell(cell1);

        row2 = new RowV2RenderData();
        row2.addCell(cell2).addCell(cell1).addCell(cell1).addCell(cell1);
    }

    @SuppressWarnings("serial")
    @Test
    public void testTable() throws Exception {

        Map<String, Object> datas = new HashMap<String, Object>() {
            {

                TableRenderData table = new TableRenderData();
                TableV2Style tableStyle = new TableV2Style();
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
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/render_tablev2.docx", config)
                .render(datas);

        FileOutputStream out = new FileOutputStream("out_render_tablev2.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }
}
