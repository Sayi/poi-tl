package com.deepoove.poi.tl.source;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.util.PoitlIOUtils;

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

    public static InputStream readInputStream(XWPFDocument doc) throws IOException {
        return PoitlIOUtils.docToInputStream(doc);
    }

}
