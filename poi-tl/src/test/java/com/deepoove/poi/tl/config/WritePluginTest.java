package com.deepoove.poi.tl.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.Numberings;
import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.render.WhereDelegate;
import com.deepoove.poi.tl.source.XWPFTestSupport;
import com.deepoove.poi.util.TableTools;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;

@DisplayName("Full custom plug-in example")
public class WritePluginTest {

    @Test
    public void testPoitlSea() throws IOException {

        // where绑定policy
        Configure config = Configure.builder().bind("sea", new AbstractRenderPolicy<String>() {
            @Override
            public void doRender(RenderContext<String> context) throws Exception {
                // anywhere
                XWPFRun where = context.getWhere();
                // anything
                String thing = context.getThing();
                // do 文本
                where.setText(thing, 0);
            }
        }).bind("sea_img", new AbstractRenderPolicy<String>() {
            @Override
            public void doRender(RenderContext<String> context) throws Exception {
                // anywhere delegate
                WhereDelegate where = context.getWhereDelegate();
                // any thing
                String thing = context.getThing();
                // do 图片
                FileInputStream stream = null;
                try {
                    stream = new FileInputStream(thing);
                    where.addPicture(stream, XWPFDocument.PICTURE_TYPE_JPEG, 400, 450);
                } finally {
                    IOUtils.closeQuietly(stream);
                }
                // clear
                clearPlaceholder(context, false);
            }
        }).bind("sea_feature", new AbstractRenderPolicy<List<String>>() {
            @Override
            public void doRender(RenderContext<List<String>> context) throws Exception {
                // anywhere delegate
                WhereDelegate where = context.getWhereDelegate();
                // anything
                List<String> thing = context.getThing();
                // do 列表
                where.renderNumbering(Numberings.of(thing.toArray(new String[] {})).create());
                // clear
                clearPlaceholder(context, true);
            }
        }).bind("sea_location", new AbstractRenderPolicy<List<String>>() {
            @Override
            public void doRender(RenderContext<List<String>> context) throws Exception {
                // anywhere
                XWPFRun where = context.getWhere();

                // anything
                List<String> thing = context.getThing();

                // do 表格
                int row = thing.size() + 1, col = 2;
                BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(where);
                XWPFTable table = bodyContainer.insertNewTable(where, row, col);
                TableTools.widthTable(table, 14.65f, col);
                TableTools.borderTable(table, 4);

                table.getRow(0).getCell(0).setText("编号");
                table.getRow(0).getCell(1).setText("地点信息");

                for (int i = 0; i < thing.size(); i++) {
                    table.getRow(i + 1).getCell(0).setText("No." + i);
                    table.getRow(i + 1).getCell(1).setText(thing.get(i));
                }

                // clear
                clearPlaceholder(context, true);
            }
        }).build();

        // 初始化where的数据
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("sea", "Hello, world!");
        args.put("sea_img", "src/test/resources/sayi.png");
        args.put("sea_feature", Arrays.asList("面朝大海春暖花开", "今朝有酒今朝醉"));
        args.put("sea_location", Arrays.asList("日落：日落山花红四海", "花海：你想要的都在这里"));

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/config_sea.docx", config)
                .render(args);

        XWPFDocument document = XWPFTestSupport.readNewDocument(template);
        assertEquals(args.get("sea"), document.getParagraphArray(0).getText());
        assertEquals(1, document.getAllPictures().size());
        assertEquals("面朝大海春暖花开", document.getParagraphArray(2).getText());
        assertTrue(null != document.getParagraphArray(2).getNumID());
        assertEquals("今朝有酒今朝醉", document.getParagraphArray(3).getText());
        assertTrue(null != document.getParagraphArray(3).getNumID());
        assertEquals(document.getParagraphArray(2).getNumID(), document.getParagraphArray(3).getNumID());

        assertEquals("日落：日落山花红四海", document.getTables().get(0).getRow(1).getCell(1).getText());
        document.close();
    }

}
