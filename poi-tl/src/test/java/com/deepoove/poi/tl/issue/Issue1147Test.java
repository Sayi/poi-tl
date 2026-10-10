package com.deepoove.poi.tl.issue;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.render.compute.EnvModel;
import com.deepoove.poi.render.compute.SpELRenderDataCompute;
import com.deepoove.poi.render.processor.EnvIterator;

@DisplayName("Issue 1147: useSpringEL with LoopRowTableRenderPolicy and blank expressions")
public class Issue1147Test {

    @Test
    public void testLoopRowTableRenderWithSpringELAndBlankTags() throws Exception {
        XWPFDocument doc = new XWPFDocument();
        XWPFTable table = doc.createTable(2, 4);
        // Header row
        table.getRow(0).getCell(0).setText("{{regionLoop}}");
        table.getRow(0).getCell(1).setText("姓名");
        table.getRow(0).getCell(2).setText("状态");
        table.getRow(0).getCell(3).setText("年龄");

        // Template row to be looped
        table.getRow(1).getCell(0).setText("[_index + 1]");
        table.getRow(1).getCell(1).setText("[name]");
        table.getRow(1).getCell(2).setText("[ ]"); // checkbox bracket or blank tag
        table.getRow(1).getCell(3).setText("[age]");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        doc.write(out);
        doc.close();

        List<Map<String, Object>> dataModel = new ArrayList<>();
        Map<String, Object> item1 = new HashMap<>();
        item1.put("name", "张三");
        item1.put("age", 25);
        dataModel.add(item1);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("name", "李四");
        item2.put("age", 30);
        dataModel.add(item2);

        LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
        Configure configure = Configure.builder().useSpringEL().bind("regionLoop", policy).build();

        assertDoesNotThrow(() -> {
            XWPFTemplate template = XWPFTemplate.compile(new ByteArrayInputStream(out.toByteArray()), configure);
            template.render(Collections.singletonMap("regionLoop", dataModel));
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            template.write(result);
            template.close();

            try (XWPFDocument resultDoc = new XWPFDocument(new ByteArrayInputStream(result.toByteArray()))) {
                XWPFTable resTable = resultDoc.getTables().get(0);
                // Table should have 1 header row + 2 data rows = 3 rows
                assertEquals(3, resTable.getRows().size());
                assertEquals("1", resTable.getRow(1).getCell(0).getText());
                assertEquals("张三", resTable.getRow(1).getCell(1).getText());
                assertEquals("[ ]", resTable.getRow(1).getCell(2).getText());
                assertEquals("25", resTable.getRow(1).getCell(3).getText());

                assertEquals("2", resTable.getRow(2).getCell(0).getText());
                assertEquals("李四", resTable.getRow(2).getCell(1).getText());
                assertEquals("[ ]", resTable.getRow(2).getCell(2).getText());
                assertEquals("30", resTable.getRow(2).getCell(3).getText());
            }
        });
    }

    @Test
    public void testSpELComputeBlankAndEmptyExpressionSafe() {
        Map<String, Object> root = new HashMap<>();
        root.put("title", "测试标题");
        EnvModel envModel = EnvModel.of(root, EnvIterator.makeEnv(0, false));
        SpELRenderDataCompute compute = new SpELRenderDataCompute(envModel, true);

        // Blank or whitespace or null expression should safely return null without throwing "IllegalStateException: No node"
        assertNull(compute.compute(null));
        assertNull(compute.compute(""));
        assertNull(compute.compute("   "));
        assertNull(compute.compute("\t"));
        assertNull(compute.compute("\n"));

        // Valid expressions still evaluate correctly
        assertEquals("测试标题", compute.compute("title"));
        assertEquals(0, compute.compute("_index"));
        assertEquals(1, compute.compute("_index + 1"));
    }

}
