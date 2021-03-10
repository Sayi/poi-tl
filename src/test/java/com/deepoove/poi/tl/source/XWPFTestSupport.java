package com.deepoove.poi.tl.source;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.XWPFTemplate;

public class XWPFTestSupport {

    public static XWPFTemplate readNewTemplate(XWPFTemplate template) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        template.write(byteArrayOutputStream);
        template.close();
        return XWPFTemplate.compile(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()),
                template.getConfig());
    }

    public static XWPFDocument readNewDocument(XWPFTemplate template) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        template.write(byteArrayOutputStream);
        template.close();
        return new XWPFDocument(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
    }

    public static ByteArrayInputStream readInputStream(XWPFDocument doc) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        doc.write(stream);
        doc.close();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(stream.toByteArray());
        return inputStream;
    }

    public static InputStream generateTemplate() throws IOException {
        XWPFDocument doc = new XWPFDocument();
        XWPFRun run = doc.createParagraph().createRun();
        run.setFontFamily("微软雅黑");
        run.setFontSize(14);
        run.setText("{{var}}");
        return readInputStream(doc);
    }

}
