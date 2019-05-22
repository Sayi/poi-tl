package com.deepoove.poi.policy;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;

/**
 * 抽象策略
 * 
 * @author Sayi
 * @version
 */
public abstract class AbstractRenderPolicy<T> implements RenderPolicy {

    /**
     * 执行模板渲染
     * 
     * @param runTemplate
     *            文档模板
     * @param data
     *            数据模型
     * @param template
     *            文档对象
     * @throws Exception
     */
    public abstract void doRender(RunTemplate runTemplate, T data, XWPFTemplate template)
            throws Exception;

    /*
     * 骨架 (non-Javadoc)
     * 
     * @see
     * com.deepoove.poi.policy.RenderPolicy#render(com.deepoove.poi.template.
     * ElementTemplate, java.lang.Object, com.deepoove.poi.XWPFTemplate)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
        RunTemplate runTemplate = (RunTemplate) eleTemplate;

        // type safe
        T model = null;
        try {
            model = (T) data;
        } catch (ClassCastException e) {
            throw new RenderException(
                    "Error Render Data format for template: " + eleTemplate.getSource(), e);
        }

        // validate
        if (null == model || !validate(model)) {
            doValidError(template, runTemplate);
            return;
        }

        // do render
        try {
            doRender(runTemplate, model, template);
        } catch (Exception e) {
            doRenderException(runTemplate, model, e);
        }

    }

    /**
     * 校验data model
     * 
     * @param data
     * @return
     */
    protected boolean validate(T data) {
        return true;
    };

    /**
     * 校验失败
     * @param template
     * @param runTemplate
     */
    protected void doValidError(XWPFTemplate template, RunTemplate runTemplate) {
        if (template.getConfig().isNullToBlank()) {
            clearPlaceholder(runTemplate.getRun());
        }
    }

    /**
     * 发生异常
     * @param runTemplate
     * @param data
     * @param e
     */
    protected void doRenderException(RunTemplate runTemplate, T data, Exception e) {
        throw new RenderException("Render template:" + runTemplate + " error", e);
    }

    /**
     * 清空标签
     * @param run
     */
    protected void clearPlaceholder(XWPFRun run) {
        run.setText("", 0);
    }

}
