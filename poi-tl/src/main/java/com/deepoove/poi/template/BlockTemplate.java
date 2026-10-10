/*
 * Copyright 2014-2026 Sayi
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

/**
 * A meta template that spans a block of the document.
 * <p>
 * A block template is delimited by a start mark and an end mark and owns the
 * meta templates nested between them. Subclasses such as
 * {@link IterableTemplate} render the enclosed content repeatedly.
 * </p>
 */
public abstract class BlockTemplate implements MetaTemplate {

    protected RunTemplate startMark;
    protected RunTemplate endMark;

    /**
     * nested meta template
     */
    protected List<MetaTemplate> templates = new ArrayList<MetaTemplate>();

    /**
     * Creates a block template delimited by the given start mark.
     *
     * @param startMark the run holding the block start tag
     */
    public BlockTemplate(RunTemplate startMark) {
        this.startMark = startMark;
    }

    /**
     * {@inheritDoc}
     * <p>
     * The variable name of a block template is the one of its start mark.
     * </p>
     */
    @Override
    public String variable() {
        return startMark.variable();
    }

    /**
     * Returns the run holding the block start tag.
     *
     * @return the start mark
     */
    public RunTemplate getStartMark() {
        return startMark;
    }

    /**
     * Returns the run holding the block start tag.
     *
     * @return the start run
     */
    public XWPFRun getStartRun() {
        return startMark.getRun();
    }

    /**
     * Sets the run holding the block start tag.
     *
     * @param startMark the start mark
     */
    public void setStartMark(RunTemplate startMark) {
        this.startMark = startMark;
    }

    /**
     * Returns the run holding the block end tag.
     *
     * @return the end mark
     */
    public RunTemplate getEndMark() {
        return endMark;
    }

    /**
     * Returns the run holding the block end tag.
     *
     * @return the end run
     */
    public XWPFRun getEndRun() {
        return endMark.getRun();
    }

    /**
     * Sets the run holding the block end tag.
     *
     * @param endMark the end mark
     */
    public void setEndMark(RunTemplate endMark) {
        this.endMark = endMark;
    }

    /**
     * Returns the meta templates nested inside this block.
     *
     * @return the nested meta templates
     */
    public List<MetaTemplate> getTemplates() {
        return templates;
    }

    /**
     * Sets the meta templates nested inside this block.
     *
     * @param templates the nested meta templates
     */
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
