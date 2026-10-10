package com.deepoove.poi.tl.issue;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackagePartName;
import org.apache.poi.openxml4j.opc.PackagingURIHelper;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.LocaleUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.Documents;
import com.deepoove.poi.data.Documents.DocumentBuilder;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.comment.CommentRenderData;
import com.deepoove.poi.plugin.comment.CommentRenderPolicy;
import com.deepoove.poi.plugin.comment.Comments;

@DisplayName("Issue 745: Comment Time Zone Difference")
public class Issue745Test {

    @Test
    public void testCommentTimezoneInNewDocument() throws Exception {
        // Month is 0-based: Calendar.FEBRUARY = 1
        Calendar localCalendar = LocaleUtil.getLocaleCalendar(2022, 1, 16, 16, 45, 40);

        CommentRenderData comment = Comments.of()
                .signature("Sayi", "s", localCalendar)
                .addText(Texts.of("批注内容").create())
                .comment("这是批注文本")
                .create();

        DocumentBuilder builder = Documents.of()
                .addParagraph(Paragraphs.of().addComment(comment).create());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XWPFTemplate.create(builder.create(), Style.builder().buildFontFamily("微软雅黑").buildFontSize(14f).build())
                .write(out);

        try (OPCPackage pkg = OPCPackage.open(new ByteArrayInputStream(out.toByteArray()))) {
            PackagePartName commentsPartName = PackagingURIHelper.createPartName("/word/comments.xml");
            PackagePart commentsPart = pkg.getPart(commentsPartName);
            assertNotNull(commentsPart, "comments.xml must exist");

            String xml = new String(IOUtils.toByteArray(commentsPart.getInputStream()), "UTF-8");
            assertTrue(xml.contains("2022-02-16T08:45:40Z") || xml.contains("2022-02-16T08:45:40.000Z"),
                    "Comment date in comments.xml must be in UTC ending with Z, got: " + xml);
        }
    }

    @Test
    public void testCommentTimezoneInTemplateWithCommentsExtensible() throws Exception {
        Calendar localCalendar = LocaleUtil.getLocaleCalendar(2022, 1, 16, 16, 45, 40);

        CommentRenderData comment = Comments.of()
                .signature("Sayi", "s", localCalendar)
                .addText("咏")
                .comment("初唐四杰")
                .create();

        Configure config = Configure.builder().bind("comment", new CommentRenderPolicy()).build();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XWPFTemplate.compile("src/test/resources/template/render_comment.docx", config)
                .render(Collections.singletonMap("comment", comment))
                .write(out);

        try (OPCPackage pkg = OPCPackage.open(new ByteArrayInputStream(out.toByteArray()))) {
            PackagePartName commentsPartName = PackagingURIHelper.createPartName("/word/comments.xml");
            PackagePart commentsPart = pkg.getPart(commentsPartName);
            assertNotNull(commentsPart, "comments.xml must exist");
            String commentsXml = new String(IOUtils.toByteArray(commentsPart.getInputStream()), "UTF-8");
            assertTrue(commentsXml.contains("2022-02-16T08:45:40Z") || commentsXml.contains("2022-02-16T08:45:40.000Z"),
                    "Comment date in comments.xml must be in UTC ending with Z: " + commentsXml);

            PackagePartName extensiblePartName = PackagingURIHelper.createPartName("/word/commentsExtensible.xml");
            PackagePart extensiblePart = pkg.getPart(extensiblePartName);
            assertNotNull(extensiblePart, "commentsExtensible.xml must exist");
            String extensibleXml = new String(IOUtils.toByteArray(extensiblePart.getInputStream()), "UTF-8");
            assertTrue(extensibleXml.contains("dateUtc=\"2022-02-16T08:45:40Z\""),
                    "commentsExtensible.xml must contain dateUtc in UTC: " + extensibleXml);

            PackagePartName idsPartName = PackagingURIHelper.createPartName("/word/commentsIds.xml");
            PackagePart idsPart = pkg.getPart(idsPartName);
            assertNotNull(idsPart, "commentsIds.xml must exist");
            String idsXml = new String(IOUtils.toByteArray(idsPart.getInputStream()), "UTF-8");
            assertTrue(idsXml.contains("w16cid:commentId"), "commentsIds.xml must contain commentId: " + idsXml);

            PackagePartName extendedPartName = PackagingURIHelper.createPartName("/word/commentsExtended.xml");
            PackagePart extendedPart = pkg.getPart(extendedPartName);
            assertNotNull(extendedPart, "commentsExtended.xml must exist");
            String extendedXml = new String(IOUtils.toByteArray(extendedPart.getInputStream()), "UTF-8");
            assertTrue(extendedXml.contains("w15:commentEx"), "commentsExtended.xml must contain commentEx: " + extendedXml);
        }
    }

    @Test
    public void testCommentWithDateObject() throws Exception {
        Date now = new Date(1645001140000L); // 2022-02-16 08:45:40 UTC

        CommentRenderData comment = Comments.of()
                .signature("Sayi", "s", now)
                .addText("测试Date对象")
                .comment("内容")
                .create();

        Calendar utc = CommentRenderPolicy.Helper.toUtcCalendar(comment.getCommentContent().getDate());
        assertNotNull(utc);
        assertTrue("UTC".equals(utc.getTimeZone().getID()) || "GMT".equals(utc.getTimeZone().getID()),
                "Timezone must be UTC or GMT");
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formatted = sdf.format(utc.getTime());
        assertTrue(formatted.equals("2022-02-16T08:45:40Z"), "Formatted UTC date should match: " + formatted);
    }

}
