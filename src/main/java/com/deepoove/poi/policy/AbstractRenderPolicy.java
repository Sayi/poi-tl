package com.deepoove.poi.policy;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;

/**
 * 抽象策略
 * @author Sayi
 * @version 
 */
public abstract class AbstractRenderPolicy implements RenderPolicy {

    /**
     * 校验data model
     * 
     * @param data
     * @return
     */
    protected abstract boolean validate(Object data);

    /**
     * 执行模板渲染
     * 
     * @param runTemplate 文档模板
     * @param data 数据模型
     * @param template 文档对象
     * @throws Exception
     */
    public abstract void doRender(RunTemplate runTemplate, Object data, XWPFTemplate template)
            throws Exception;

    /* 
     * 骨架
     * (non-Javadoc)
     * @see com.deepoove.poi.policy.RenderPolicy#render(com.deepoove.poi.template.ElementTemplate, java.lang.Object, com.deepoove.poi.XWPFTemplate)
     */
    @Override
    public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
        RunTemplate runTemplate = (RunTemplate) eleTemplate;
        // validate
        if (!validate(data)) {
            // clearPlaceholder(runTemplate.getRun());
            return;
        }

        // do render
        try {
            doRender(runTemplate, data, template);
        } catch (Exception e) {
            doRenderException(runTemplate, data, e);
        }

    }

    protected void doRenderException(RunTemplate runTemplate, Object data, Exception e) {
        throw new RenderException("Render template: " + runTemplate + " error.", e);
    }

    protected void clearPlaceholder(XWPFRun run) {
        run.setText("", 0);
    }

}
