package com.deepoove.poi.tl.mypolicy;

import java.util.Iterator;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.policy.PictureRenderPolicy;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.policy.TextRenderPolicy;
import com.deepoove.poi.resolver.TemplateFactory;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.StyleUtils;

public class ListDataRenderPolicy implements RenderPolicy {
    
    final PictureRenderPolicy  pictureRenderPolicy = new PictureRenderPolicy();
    final TextRenderPolicy  textRenderPolicy = new TextRenderPolicy();

    @SuppressWarnings({ "unchecked" })
    @Override
    public void render(ElementTemplate eleTemplate, Object dataList, XWPFTemplate template) {
        RunTemplate runTemplate = (RunTemplate) eleTemplate;
        XWPFRun run = runTemplate.getRun();

        if (dataList == null || !(dataList instanceof Iterable)) {
            logger.warn("Error render {}, should be Iterable:{}", runTemplate.getTagName(),
                    dataList.getClass());
            return;
        }

        Iterable<Object> list = (Iterable<Object>) dataList;
        Iterator<Object> iterator = list.iterator();
        RenderPolicy policy = null;
        while (iterator.hasNext()) {
            Object next = iterator.next();
            policy = getPolicy(next);
            if (null == policy) {
                logger.warn("cannot find render policy: [" + runTemplate.getTagName() + "]");
                return;
            }
            XWPFRun insertNewRun = ((XWPFParagraph) run.getParent()).insertNewRun(runTemplate.getRunPos());
            StyleUtils.styleRun(insertNewRun, run);
            RunTemplate createRunTemplate = TemplateFactory.createRunTemplate(
                    runTemplate.getSign() + runTemplate.getTagName(),
                    template.getConfig(), insertNewRun);
            
            policy.render(createRunTemplate, next, template);

            // 使用poi api,需要自己处理数据之间的格式
            // NiceXWPFDocument xwpfDocument = template.getXWPFDocument();
             insertNewRun.addBreak();//数据之间换行
        }
        run.setText("", 0);
    }

    private RenderPolicy getPolicy(Object data) {
        if (data instanceof PictureRenderData) {
            return pictureRenderPolicy;
        } else if (data instanceof Iterable){
            return new ListDataRenderPolicy();
        }else{
            return textRenderPolicy;
        }
    }

}
