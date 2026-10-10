package com.deepoove.poi.tl.plugin.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.control.ControlRenderData.LockType;
import com.deepoove.poi.data.control.Controls;
import com.deepoove.poi.data.control.Option;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.control.ControlRenderPolicy;

public class ControlRenderPolicyTest {

    private static final String W14 = "http://schemas.microsoft.com/office/word/2010/wordml";

    @Test
    public void testCheckBoxStatesGlyphsAndNamespace() throws Exception {
        String xml = render("前缀{{box}}后缀", "box",
                Controls.ofCheckBox(true).title("婚姻").tag("married").lock(LockType.SDT_LOCKED).create());

        assertTrue(xml.contains("xmlns:w14=\"" + W14 + "\""), xml);
        assertTrue(xml.contains("<w14:checkbox>"), xml);
        assertTrue(xml.contains("<w14:checked w14:val=\"1\"/>"), xml);
        assertTrue(xml.contains("w14:val=\"2612\""), xml);
        assertTrue(xml.contains("w14:val=\"2610\""), xml);
        assertTrue(xml.contains("w14:font=\"MS Gothic\""), xml);
        assertTrue(xml.contains("<w:t>☒</w:t>"), xml);
        assertTrue(xml.contains("<w:alias w:val=\"婚姻\"/>"), xml);
        assertTrue(xml.contains("<w:tag w:val=\"married\"/>"), xml);
        assertTrue(xml.contains("<w:lock w:val=\"sdtLocked\"/>"), xml);
        assertFalse(xml.contains("{{box}}"), xml);
        assertOrder(xml, "前缀", "<w:sdt>", "☒", "后缀");
        assertFalse(xml.contains("<w:t></w:t>"), xml);
        assertFalse(xml.contains("<w:t xml:space=\"preserve\"></w:t>"), xml);

        String off = render("{{box}}", "box", false);
        assertTrue(off.contains("<w14:checked w14:val=\"0\"/>"), off);
        assertTrue(off.contains("<w:t>☐</w:t>"), off);
        assertFalse(off.contains("<w14:checked w14:val=\"1\"/>"), off);
    }

    @Test
    public void testDropDownSelectionAndPlaceholder() throws Exception {
        String selected = render("{{audit}}", "audit", Controls.ofDropDown("REJECT")
                .addOption("请选择...", "")
                .addOption("同意", "AGREE")
                .addOption("退回", "REJECT")
                .lock(LockType.CONTENT_LOCKED)
                .create());
        assertTrue(selected.contains("<w:dropDownList"), selected);
        assertFalse(selected.contains("<w:comboBox"), selected);
        assertTrue(selected.contains("w:displayText=\"同意\""), selected);
        assertTrue(selected.contains("w:value=\"AGREE\""), selected);
        assertTrue(selected.contains("w:val=\"REJECT\"") || selected.contains("w:lastValue=\"REJECT\""), selected);
        assertTrue(selected.contains("<w:t>退回</w:t>"), selected);
        assertTrue(selected.contains("<w:lock w:val=\"contentLocked\"/>"), selected);
        assertFalse(selected.contains("showingPlcHdr"), selected);

        String missed = render("{{audit}}", "audit", Controls.ofDropDown("MISSING")
                .placeholder("请选择")
                .addOption("同意", "AGREE")
                .create());
        assertTrue(missed.contains("<w:showingPlcHdr"), missed);
        assertTrue(missed.contains("<w:t>请选择</w:t>"), missed);
        assertFalse(missed.contains("<w:t>同意</w:t>"), missed);

        String combo = render("{{audit}}", "audit",
                Controls.ofDropDown("AGREE").comboBox(true).addOption("同意", "AGREE").create());
        assertTrue(combo.contains("<w:comboBox"), combo);
        assertFalse(combo.contains("<w:dropDownList"), combo);
    }

    @Test
    public void testOptionListAndEmptyList() throws Exception {
        String xml = render("{{audit}}", "audit", Arrays.asList(Option.of("甲", "A"), Option.of("乙", "B")));
        assertTrue(xml.contains("w:displayText=\"甲\""), xml);
        assertTrue(xml.contains("<w:showingPlcHdr"), xml);

        String empty = render("{{audit}}", "audit", Collections.emptyList());
        assertTrue(empty.contains("<w:dropDownList"), empty);
        assertTrue(empty.contains("<w:t>请选择</w:t>"), empty);
    }

    @Test
    public void testDateFormatLocaleAndNull() throws Exception {
        java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone("UTC"));
        calendar.clear();
        calendar.set(2025, java.util.Calendar.MAY, 20, 0, 0, 0);
        Date date = calendar.getTime();
        String xml = render("{{day}}", "day", Controls.ofDate(date)
                .format("yyyy年MM月dd日")
                .locale(Locale.SIMPLIFIED_CHINESE)
                .lock(LockType.SDT_CONTENT_LOCKED)
                .create());
        assertTrue(xml.contains("<w:date"), xml);
        java.util.Calendar local = java.util.Calendar.getInstance();
        local.setTime(date);
        String wallClock = String.format(Locale.ROOT, "%04d-%02d-%02dT%02d:%02d:%02d", local.get(java.util.Calendar.YEAR),
                local.get(java.util.Calendar.MONTH) + 1, local.get(java.util.Calendar.DAY_OF_MONTH),
                local.get(java.util.Calendar.HOUR_OF_DAY), local.get(java.util.Calendar.MINUTE),
                local.get(java.util.Calendar.SECOND));
        assertTrue(xml.contains("w:fullDate=\"" + wallClock), xml);
        assertTrue(xml.contains("<w:dateFormat w:val=\"yyyy年MM月dd日\"/>"), xml);
        assertTrue(xml.contains("<w:lid w:val=\"zh-CN\"/>"), xml);
        assertTrue(xml.contains("w:val=\"gregorian\""), xml);
        assertTrue(xml.contains("<w:t>2025年05月20日</w:t>"), xml);
        assertTrue(xml.contains("<w:lock w:val=\"sdtContentLocked\"/>"), xml);
        assertFalse(xml.contains("zh_CN"), xml);

        String empty = render("{{day}}", "day", Controls.ofDate().placeholder("请选择日期").create());
        assertFalse(empty.contains("w:fullDate"), empty);
        assertTrue(empty.contains("<w:showingPlcHdr"), empty);
        assertTrue(empty.contains("<w:t>请选择日期</w:t>"), empty);
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        assertFalse(empty.contains("<w:t>" + today + "</w:t>"), empty);
    }

    @Test
    public void testLocalDateAndStyleInheritance() throws Exception {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.createRun().setText("前");
        XWPFRun tag = paragraph.createRun();
        tag.setText("{{day}}");
        tag.setFontSize(10.5);
        tag.setFontFamily("Microsoft YaHei");
        paragraph.createRun().setText("后");

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("day", java.time.LocalDate.of(2025, 5, 20));
        Configure config = Configure.builder().bind("day", new ControlRenderPolicy()).build();
        String xml = documentXml(XWPFTemplate.compile(doc, config).render(data));

        assertTrue(xml.contains("<w:t>2025-05-20</w:t>"), xml);
        assertTrue(xml.contains("w:val=\"21\""), xml);
        assertTrue(countOf(xml, "w:val=\"21\"") >= 2, xml);
        assertOrder(xml, "前", "<w:sdt>", "2025-05-20", "后");
    }

    @Test
    public void testExplicitStyleAndExistingSdtBeforeTag() throws Exception {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph paragraph = doc.createParagraph();
        org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtRun existing = paragraph.getCTP().addNewSdt();
        existing.addNewSdtPr();
        existing.addNewSdtContent();
        paragraph.createRun().setText("已有{{box}}");

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("box", Controls.ofCheckBox(true).style(Style.builder().buildFontSize(12).build()).create());
        Configure config = Configure.builder().bind("box", new ControlRenderPolicy()).build();
        String xml = documentXml(XWPFTemplate.compile(doc, config).render(data));

        assertEquals(2, countOf(xml, "<w:sdt>"), xml);
        int first = xml.indexOf("<w:sdt>");
        int checkbox = xml.indexOf("<w14:checkbox>");
        assertTrue(first >= 0 && checkbox > first, xml);
        assertTrue(xml.contains("w:val=\"24\""), xml);
        assertTrue(xml.contains("<w:t>☒</w:t>"), xml);
    }

    @Test
    public void testNullRemovesTagAndKeepsCellParagraph() throws Exception {
        String xml = render("保留{{box}}这里", "box", null);
        assertFalse(xml.contains("<w:sdt"), xml);
        assertFalse(xml.contains("{{box}}"), xml);
        assertTrue(xml.contains("保留"), xml);
        assertTrue(xml.contains("这里"), xml);

        XWPFDocument doc = new XWPFDocument();
        XWPFTable table = doc.createTable(1, 1);
        XWPFTableCell cell = table.getRow(0).getCell(0);
        cell.removeParagraph(0);
        XWPFParagraph paragraph = cell.addParagraph();
        paragraph.createRun().setText("{{box}}");
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("box", true);
        Configure config = Configure.builder().bind("box", new ControlRenderPolicy()).build();
        String cellXml = documentXml(XWPFTemplate.compile(doc, config).render(data));
        assertTrue(cellXml.contains("<w:tc>"), cellXml);
        assertTrue(cellXml.contains("<w:p"), cellXml);
        assertTrue(cellXml.contains("<w14:checkbox>"), cellXml);
        assertTrue(countOf(cellXml, "<w:p ") + countOf(cellXml, "<w:p>") >= 1, cellXml);
    }

    @Test
    public void testIdsAreUniqueAcrossConcurrentDocuments() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(8);
        try {
            Future<?>[] tasks = new Future<?>[20];
            for (int i = 0; i < tasks.length; i++) {
                tasks[i] = pool.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            renderManyAndAssertUniqueIds();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
            for (Future<?> task : tasks) {
                task.get();
            }
        } finally {
            pool.shutdownNow();
        }
    }

    private void renderManyAndAssertUniqueIds() throws Exception {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph paragraph = doc.createParagraph();
        Map<String, Object> data = new HashMap<String, Object>();
        ConfigureBuilder builder = Configure.builder();
        for (int i = 0; i < 30; i++) {
            String tag = "c" + i;
            paragraph.createRun().setText("{{" + tag + "}}");
            data.put(tag, Boolean.valueOf(i % 2 == 0));
            builder.bind(tag, new ControlRenderPolicy());
        }
        String xml = documentXml(XWPFTemplate.compile(doc, builder.build()).render(data));
        Matcher matcher = Pattern.compile("<w:id w:val=\"(\\d+)\"/>").matcher(xml);
        Set<String> ids = new HashSet<String>();
        while (matcher.find()) {
            assertTrue(ids.add(matcher.group(1)), "duplicated sdt id " + matcher.group(1));
        }
        assertEquals(30, ids.size(), xml);
        assertEquals(30, countOf(xml, "<w14:checkbox>"), xml);
    }

    private String render(String paragraph, String tag, Object value) throws Exception {
        XWPFDocument doc = new XWPFDocument();
        doc.createParagraph().createRun().setText(paragraph);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(tag, value);
        Configure config = Configure.builder().bind(tag, new ControlRenderPolicy()).build();
        return documentXml(XWPFTemplate.compile(doc, config).render(data));
    }

    private String documentXml(XWPFTemplate template) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        template.write(out);
        template.close();
        ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(out.toByteArray()));
        ZipEntry entry;
        while ((entry = zip.getNextEntry()) != null) {
            if ("word/document.xml".equals(entry.getName())) {
                ByteArrayOutputStream xml = new ByteArrayOutputStream();
                byte[] buf = new byte[4096];
                int n;
                while ((n = zip.read(buf)) > 0) {
                    xml.write(buf, 0, n);
                }
                return xml.toString(StandardCharsets.UTF_8.name());
            }
        }
        throw new AssertionError("word/document.xml missing");
    }

    private static void assertOrder(String xml, String... parts) {
        int from = -1;
        for (String part : parts) {
            int at = xml.indexOf(part, from + 1);
            assertTrue(at > from, "expected [" + part + "] after previous part in " + xml);
            from = at;
        }
    }

    private static int countOf(String xml, String token) {
        int count = 0;
        int from = 0;
        while (true) {
            int at = xml.indexOf(token, from);
            if (at < 0) return count;
            count++;
            from = at + token.length();
        }
    }

}
