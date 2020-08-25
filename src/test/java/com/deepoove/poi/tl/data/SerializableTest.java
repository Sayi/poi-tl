package com.deepoove.poi.tl.data;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;

import com.deepoove.poi.data.ChartMultiSeriesRenderData;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.Numberings;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.SeriesRenderData;
import com.deepoove.poi.data.SeriesRenderData.ComboType;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.data.style.StyleBuilder;
import com.deepoove.poi.data.style.TableStyle;

public class SerializableTest {

    ByteArrayOutputStream byteArrayOutputStream;
    ObjectOutputStream oos;

    @BeforeEach
    void before() throws Exception {
        byteArrayOutputStream = new ByteArrayOutputStream();
        oos = new ObjectOutputStream(byteArrayOutputStream);
    }

    @AfterEach
    void after() throws IOException {
        oos.close();
        byteArrayOutputStream.close();
    }

    @Test
    void testTextRenderData() throws Exception {
        TextRenderData data = Texts.of("poi-tl").color("00FF00").link("http://deepoove.com").create();
        TextRenderData result = write(data).getResult(TextRenderData.class);

        assertEquals(result.getText(), data.getText());
        assertEquals(((HyperLinkTextRenderData) result).getUrl(), ((HyperLinkTextRenderData) data).getUrl());
        assertEquals(result.getStyle().getColor(), data.getStyle().getColor());
    }

    @Test
    void testPictureRenderData() throws Exception {
        PictureRenderData data = Pictures.ofStream(new FileInputStream("src/test/resources/logo.png"), PictureType.PNG)
                .size(100, 200).altMeta("NO IMAGE").create();
        PictureRenderData result = write(data).getResult(PictureRenderData.class);

        assertEquals(result.getWidth(), data.getWidth());
        assertEquals(result.getHeight(), data.getHeight());
        assertEquals(result.getPictureType(), data.getPictureType());
        assertEquals(result.getAltMeta(), data.getAltMeta());
        assertArrayEquals(result.getImage(), data.getImage());
    }

    @Test
    void testChartRenderData() throws Exception {
        ChartMultiSeriesRenderData data = new ChartMultiSeriesRenderData();
        data.setChartTitle("ComboChartTitle");
        data.setCategories(new String[] { "中文", "English", "日本語", "português" });
        List<SeriesRenderData> series = new ArrayList<>();
        SeriesRenderData b0 = new SeriesRenderData();
        b0.setName("countries");
        b0.setComboType(ComboType.BAR);
        b0.setValues(new Double[] { 15.0, 6.0, 18.0, 231.0 });
        series.add(b0);
        data.setSeriesDatas(series);
        ChartMultiSeriesRenderData result = write(data).getResult(ChartMultiSeriesRenderData.class);

        assertEquals(result.getChartTitle(), data.getChartTitle());
        assertArrayEquals(result.getCategories(), data.getCategories());
        assertEquals(result.getSeriesDatas().get(0).getName(), data.getSeriesDatas().get(0).getName());
        assertEquals(result.getSeriesDatas().get(0).getComboType(), data.getSeriesDatas().get(0).getComboType());
        assertArrayEquals(result.getSeriesDatas().get(0).getValues(), data.getSeriesDatas().get(0).getValues());
    }

    @Test
    void testDocxRenderData() throws Exception {
        DocxRenderData data = new DocxRenderData(
                new File("src/test/resources/template/render_include_merge_template.docx"));
        DocxRenderData result = write(data).getResult(DocxRenderData.class);

        assertArrayEquals(result.getDocx(), data.getDocx());
    }

    @Test
    void testMiniTableRenderData() throws Exception {
        RowRenderData row1 = RowRenderData.build("李四", "博士");
        MiniTableRenderData data = new MiniTableRenderData(Arrays.asList(row1));
        data.setNoDatadesc("NO DESC");
        data.setWidth(10.01f);
        TableStyle style = new TableStyle();
        style.setAlign(STJc.CENTER);
        data.setStyle(style);
        MiniTableRenderData result = write(data).getResult(MiniTableRenderData.class);

        assertEquals(result.getNoDatadesc(), data.getNoDatadesc());
        assertEquals(result.getWidth(), data.getWidth());
        assertEquals(result.getStyle().getAlign(), data.getStyle().getAlign());
        assertEquals(result.getRows().get(0).getCells().get(0).getCellText().getText(),
                data.getRows().get(0).getCells().get(0).getCellText().getText());
    }

    @Test
    void testNumbericRenderData() throws Exception {
        Style fmtStyle = StyleBuilder.newBuilder().buildColor("00FF00").build();
        NumbericRenderData data = Numberings.ofBullet()
                .addItem(new TextRenderData("df2d4f", "Deeply in love with the things you love, just deepoove."))
                .addItem(new TextRenderData("Deeply in love with the things you love, just deepoove."))
                .addItem(new TextRenderData("5285c5", "Deeply in love with the things you love, just deepoove."))
                .style(fmtStyle).create();

        NumbericRenderData result = write(data).getResult(NumbericRenderData.class);

        assertEquals(result.getFormat(), data.getFormat());
        assertEquals(result.getStyle().getColor(), data.getStyle().getColor());
        for (int i = 0; i < 3; i++) {
            TextRenderData dataTxt = (TextRenderData) data.getItems().get(i);
            TextRenderData resultTxt = (TextRenderData) result.getItems().get(i);
            assertEquals(resultTxt.getText(), dataTxt.getText());
        }
    }

    private SerializableTest write(Object data) throws IOException {
        oos.writeObject(data);
        return this;
    }

    @SuppressWarnings("unchecked")
    private <T> T getResult(Class<T> clazz) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteArrayInputStream);
        try {
            return (T) in.readObject();
        } finally {
            in.close();
            byteArrayInputStream.close();
        }
    }

}
