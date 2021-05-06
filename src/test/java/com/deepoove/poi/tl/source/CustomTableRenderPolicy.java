package com.deepoove.poi.tl.source;

import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import com.deepoove.poi.data.style.BorderStyle;
import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.util.TableTools;
import com.deepoove.poi.util.UnitUtils;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;

/**
 * 通过
 *  <code>
 * Configure.builder().bind("report", new
 * CustomTableRenderPolicy());
 * </code> 
 * 将模板report的策略设置成自定义的表格策略
 * 
 * @author Sayi
 * @version
 */
public class CustomTableRenderPolicy extends AbstractRenderPolicy<Object> {

    @Override
    protected void afterRender(RenderContext<Object> context) {
        // 清空模板标签
        clearPlaceholder(context, true);
    }

    @Override
    public void doRender(RenderContext<Object> context) throws Exception {

        XWPFRun run = context.getRun();
        BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(run);
        // 定义行列
        int row = 10, col = 8;
        // 插入表格
        XWPFTable table = bodyContainer.insertNewTable(run, row, col);

        // 表格宽度
        TableTools.setWidth(table, UnitUtils.cm2Twips(14.63f) + "", null);
        // 边框和样式
        TableTools.borderTable(table, BorderStyle.DEFAULT);

        // TODO 调用XWPFTable API操作表格
        // TODO 调用TableRenderPolicy.Helper.renderRow方法快速方便的渲染一行数据
        // TODO 调用TableTools类方法操作表格，比如合并单元格
        // ......
        TableTools.mergeCellsHorizonal(table, 0, 0, 7);
        TableTools.mergeCellsVertically(table, 0, 1, 9);

    }

}
