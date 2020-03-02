package com.deepoove.poi.policy;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.xwpf.Container;
import com.deepoove.poi.xwpf.ContainerFactory;

public class CommentPolicy implements RenderPolicy {

    @Override
    public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
        RunTemplate runTemplate = (RunTemplate) eleTemplate;
        XWPFRun run = runTemplate.getRun();
        Container container = ContainerFactory.getContainer(run);
        container.clearPlaceholder(run);
    }

}
