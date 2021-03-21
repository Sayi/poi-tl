package com.deepoove.poi.tl.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.render.compute.RenderDataComputeFactory;
import com.deepoove.poi.tl.source.XWPFTestSupport;
import com.google.common.collect.Maps;

public class RenderDataComputeFactoryTest {

    @Test
    public void testConfigRenderDataCompute() throws IOException {
        RenderDataComputeFactory renderDataComputeFactory = model -> el -> "123";
        Configure config = Configure.builder().setRenderDataComputeFactory(renderDataComputeFactory).build();

        XWPFDocument doc = new XWPFDocument();
        doc.createParagraph().createRun().setText("{{name}}");
        InputStream inputStream = XWPFTestSupport.readInputStream(doc);

        XWPFTemplate template = XWPFTemplate.compile(inputStream, config).render(Maps.newHashMap());
        XWPFDocument document = XWPFTestSupport.readNewDocument(template);
        assertEquals("123", document.getParagraphArray(0).getText());

    }

}
