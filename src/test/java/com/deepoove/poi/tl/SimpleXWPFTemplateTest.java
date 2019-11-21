package com.deepoove.poi.tl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.render.WhereDelegate;
import com.deepoove.poi.util.TableTools;

/**
 * 在文档的任何地方做任何事情（Do Anything Anywhere）是poi-tl的星辰大海。
 * 
 * @author Sayi
 * @version 1.5.1
 */
public class SimpleXWPFTemplateTest {

    @Test
    public void testPoitlSea() throws IOException {

        // where绑定policy
        Configure config = Configure.newBuilder().bind("sea", new AbstractRenderPolicy<String>() {
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
                    where.addPicture(stream, XWPFDocument.PICTURE_TYPE_JPEG, 500, 300);
                }
                finally {
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
                where.renderNumberic(NumbericRenderData.build(thing.toArray(new String[] {})));
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
                XWPFTable table = context.getXWPFDocument().insertNewTable(where, row, col);
                TableTools.widthTable(table, MiniTableRenderData.WIDTH_A4_FULL, col);
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
        args.put("sea_img", "src/test/resources/sea.jpg");
        args.put("sea_feature", Arrays.asList("面朝大海春暖花开", "今朝有酒今朝醉"));
        args.put("sea_location", Arrays.asList("日落：日落山花红四海", "花海：你想要的都在这里"));

        XWPFTemplate.compile("src/test/resources/sea.docx", config).render(args)
                .writeToFile("out_sea.docx");
    }

}
