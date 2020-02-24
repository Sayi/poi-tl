package com.deepoove.poi.template;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.render.processor.Visitor;
import com.deepoove.poi.template.run.RunTemplate;

public class IterableTemplate implements MetaTemplate {

    protected RunTemplate startMark;
    protected RunTemplate endMark;

    protected List<MetaTemplate> templates = new ArrayList<MetaTemplate>();

    public IterableTemplate() {
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public IterableTemplate buildIfInline() {
        XWPFRun startRun = startMark.getRun();
        XWPFRun endRun = endMark.getRun();

        if (startRun.getParent() == endRun.getParent()) {
            InlineIterableTemplate instance = new InlineIterableTemplate();
            instance.startMark = startMark;
            instance.endMark = endMark;
            instance.templates = templates;
            return instance;
        }
        return this;
    }

    public RunTemplate getStartMark() {
        return startMark;
    }

    public void setStartMark(RunTemplate startMark) {
        this.startMark = startMark;
    }

    public RunTemplate getEndMark() {
        return endMark;
    }

    public void setEndMark(RunTemplate endMark) {
        this.endMark = endMark;
    }

    public List<MetaTemplate> getTemplates() {
        return templates;
    }

    public void setTemplates(List<MetaTemplate> templates) {
        this.templates = templates;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(startMark);
        templates.forEach(temp -> sb.append(temp).append(" "));
        sb.append(endMark);
        return sb.toString();
    }
}
