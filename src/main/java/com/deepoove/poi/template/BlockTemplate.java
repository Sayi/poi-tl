/*
 * Copyright 2014-2021 Sayi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.deepoove.poi.template;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.template.run.RunTemplate;

public abstract class BlockTemplate implements MetaTemplate {

    protected RunTemplate startMark;
    protected RunTemplate endMark;

    /**
     * nested meta template
     */
    protected List<MetaTemplate> templates = new ArrayList<MetaTemplate>();

    public BlockTemplate(RunTemplate startMark) {
        this.startMark = startMark;
    }

    @Override
    public String variable() {
        return startMark.variable();
    }

    public RunTemplate getStartMark() {
        return startMark;
    }

    public XWPFRun getStartRun() {
        return startMark.getRun();
    }

    public void setStartMark(RunTemplate startMark) {
        this.startMark = startMark;
    }

    public RunTemplate getEndMark() {
        return endMark;
    }

    public XWPFRun getEndRun() {
        return endMark.getRun();
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
