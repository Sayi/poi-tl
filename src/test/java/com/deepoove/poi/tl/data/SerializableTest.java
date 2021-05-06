//package com.deepoove.poi.tl.data;
//
//import static org.junit.jupiter.api.Assertions.assertArrayEquals;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.util.List;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import com.deepoove.poi.data.Cells;
//import com.deepoove.poi.data.ChartMultiSeriesRenderData;
//import com.deepoove.poi.data.Charts;
//import com.deepoove.poi.data.DocxRenderData;
//import com.deepoove.poi.data.HyperlinkTextRenderData;
//import com.deepoove.poi.data.NumberingRenderData;
//import com.deepoove.poi.data.Numberings;
//import com.deepoove.poi.data.ParagraphRenderData;
//import com.deepoove.poi.data.Paragraphs;
//import com.deepoove.poi.data.PictureRenderData;
//import com.deepoove.poi.data.PictureType;
//import com.deepoove.poi.data.Pictures;
//import com.deepoove.poi.data.RenderData;
//import com.deepoove.poi.data.RowRenderData;
//import com.deepoove.poi.data.Rows;
//import com.deepoove.poi.data.TableRenderData;
//import com.deepoove.poi.data.Tables;
//import com.deepoove.poi.data.TextRenderData;
//import com.deepoove.poi.data.Texts;
//import com.deepoove.poi.data.style.Style;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class SerializableTest {
//
//    ByteArrayOutputStream byteArrayOutputStream;
//    ObjectOutputStream oos;
//
//    ObjectMapper objectMapper = new ObjectMapper();
//    String jsonStr = "";
//
//    @BeforeEach
//    void before() throws Exception {
//        byteArrayOutputStream = new ByteArrayOutputStream();
//        oos = new ObjectOutputStream(byteArrayOutputStream);
//    }
//
//    @AfterEach
//    void after() throws IOException {
//        oos.close();
//        byteArrayOutputStream.close();
//    }
//
//    @Test
//    void testTextRenderData() throws Exception {
//        TextRenderData data = Texts.of("poi-tl").color("00FF00").link("http://deepoove.com").create();
//        TextRenderData result = write(data).getResult(HyperlinkTextRenderData.class);
//
//        assertEquals(result.getText(), data.getText());
//        assertEquals(((HyperlinkTextRenderData) result).getUrl(), ((HyperlinkTextRenderData) data).getUrl());
//        assertEquals(result.getStyle().getColor(), data.getStyle().getColor());
//    }
//
//    @Test
//    void testPictureRenderData() throws Exception {
//        PictureRenderData data = Pictures.ofStream(new FileInputStream("src/test/resources/logo.png"), PictureType.PNG)
//                .size(100, 200).altMeta("NO IMAGE").create();
//        PictureRenderData result = write(data).getResult(PictureRenderData.class);
//
//        assertEquals(result.getPictureStyle().getWidth(), data.getPictureStyle().getWidth());
//        assertEquals(result.getPictureStyle().getHeight(), data.getPictureStyle().getHeight());
//        assertEquals(result.getPictureType(), data.getPictureType());
//        assertEquals(result.getAltMeta(), data.getAltMeta());
//        assertArrayEquals(result.getPictureSupplier().get(), data.getPictureSupplier().get());
//    }
//
//    @Test
//    void testChartRenderData() throws Exception {
//        ChartMultiSeriesRenderData data = Charts
//                .ofComboSeries("ComboChartTitle", new String[] { "中文", "English", "日本語", "português" })
//                .addBarSeries("countries", new Double[] { 15.0, 6.0, 18.0, 231.0 }).create();
//
//        ChartMultiSeriesRenderData result = write(data).getResult(ChartMultiSeriesRenderData.class);
//
//        assertEquals(result.getChartTitle(), data.getChartTitle());
//        assertArrayEquals(result.getCategories(), data.getCategories());
//        assertEquals(result.getSeriesDatas().get(0).getName(), data.getSeriesDatas().get(0).getName());
//        assertEquals(result.getSeriesDatas().get(0).getComboType(), data.getSeriesDatas().get(0).getComboType());
//        assertArrayEquals(result.getSeriesDatas().get(0).getValues(), data.getSeriesDatas().get(0).getValues());
//    }
//
//    @Test
//    void testDocxRenderData() throws Exception {
//        DocxRenderData data = new DocxRenderData(
//                new File("src/test/resources/template/render_include_merge_template.docx"));
//        DocxRenderData result = write(data).getResult(DocxRenderData.class);
//
//        assertArrayEquals(result.getMergedDoc(), data.getMergedDoc());
//    }
//
//    @Test
//    void testTableRenderData() throws Exception {
//        RowRenderData row = Rows.of(Cells.of("lisi").create(), Cells.of("lisi").create()).create();
//        TableRenderData data = Tables.of(row).width(10.01f, null).center().create();
//
//        TableRenderData result = write(data).getResult(TableRenderData.class);
//
//        assertEquals(result.getTableStyle().getWidth(), data.getTableStyle().getWidth());
//        assertEquals(result.getTableStyle().getAlign(), data.getTableStyle().getAlign());
//        checkParagraph(result.getRows().get(0).getCells().get(0).getParagraphs().get(0),
//                data.getRows().get(0).getCells().get(0).getParagraphs().get(0));
//
//    }
//
//    @Test
//    void testParagraphRenderData() throws Exception {
//        ParagraphRenderData data = Paragraphs.of("boshi").addPicture(Pictures.ofLocal("sayi.png").create()).create();
//
//        ParagraphRenderData result = write(data).getResult(ParagraphRenderData.class);
//        checkParagraph(result, data);
//
//    }
//
//    private void checkParagraph(ParagraphRenderData result, ParagraphRenderData data) {
//        List<RenderData> contents = data.getContents();
//        List<RenderData> contents2 = result.getContents();
//        assertEquals(contents2.size(), contents.size());
//        for (int i = 0; i < contents2.size(); i++) {
//            RenderData renderData2 = contents2.get(i);
//            RenderData renderData = contents.get(i);
//            assertEquals(renderData2.getClass(), renderData.getClass());
//            if (renderData instanceof TextRenderData) {
//                assertEquals(((TextRenderData) renderData2).getText(), ((TextRenderData) renderData).getText());
//            }
//            if (renderData instanceof PictureRenderData) {
//                assertArrayEquals(((PictureRenderData) renderData2).getPictureSupplier().get(), ((PictureRenderData) renderData).getPictureSupplier().get());
//            }
//        }
//
//    }
//
//    @Test
//    void testNumberingRenderData() throws Exception {
//        Style fmtStyle = Style.builder().buildColor("00FF00").build();
//        NumberingRenderData data = Numberings.ofBullet()
//                .addItem(new TextRenderData("df2d4f", "Deeply in love with the things you love, just deepoove."))
//                .addItem(Paragraphs.of().addText("Deeply in love with the things you love, just deepoove.")
//                        .glyphStyle(fmtStyle).create())
//                .addItem(new TextRenderData("5285c5", "Deeply in love with the things you love, just deepoove."))
//                .create();
//
//        NumberingRenderData result = write(data).getResult(NumberingRenderData.class);
//
//        assertArrayEquals(result.getFormats().toArray(), data.getFormats().toArray());
//        for (int i = 0; i < 3; i++) {
//            TextRenderData dataTxt = (TextRenderData) data.getItems().get(i).get(0).getContents().get(0);
//            TextRenderData resultTxt = (TextRenderData) data.getItems().get(i).get(0).getContents().get(0);
//            assertEquals(resultTxt.getText(), dataTxt.getText());
//        }
//        assertEquals(result.getItems().get(1).get(0).getParagraphStyle().getGlyphStyle().getColor(),
//                data.getItems().get(1).get(0).getParagraphStyle().getGlyphStyle().getColor());
//    }
//
//    private SerializableTest write(Object data) throws IOException {
//        oos.writeObject(data);
//        return this;
//
////        jsonStr =  objectMapper.writeValueAsString(data);
////        return this;
//    }
//
//    @SuppressWarnings("unchecked")
//    private <T> T getResult(Class<T> clazz) throws IOException, ClassNotFoundException {
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
//        ObjectInputStream in = new ObjectInputStream(byteArrayInputStream);
//        try {
//            return (T) in.readObject();
//        } finally {
//            in.close();
//            byteArrayInputStream.close();
//        }
////        return  objectMapper.readValue(jsonStr, clazz);
//    }
//
//}
