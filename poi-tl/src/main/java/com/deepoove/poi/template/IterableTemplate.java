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

import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.render.processor.Visitor;
import com.deepoove.poi.template.run.RunTemplate;

/**
 * A block template that renders its nested content more than once.
 * <p>
 * It is created for the grammar rules {@code {{?tag}}...{{/tag}}} and
 * {@code {{tag}}...{{/tag}}}, which stand for conditional blocks and foreach
 * loops. Whether the block repeats or is simply kept/removed depends on the
 * bound data: a collection is iterated, an unknown or empty value drops the
 * block.
 * </p>
 *
 * @see InlineIterableTemplate
 */
public class IterableTemplate extends BlockTemplate {

    /**
     * Creates an iterable template with the given start mark.
     *
     * @param startMark the run holding the block start tag
     */
    public IterableTemplate(RunTemplate startMark) {
        super(startMark);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    /**
     * Converts this template to an {@link InlineIterableTemplate} when its start
     * and end marks live in the same paragraph.
     * <p>
     * The inline form can be rendered without copying whole paragraphs, which
     * preserves the surrounding text when the loop is written inline.
     * </p>
     *
     * @return an inline iterable template when both marks share a parent,
     *         otherwise this instance
     */
    public IterableTemplate buildIfInline() {
        XWPFRun startRun = startMark.getRun();
        XWPFRun endRun = endMark.getRun();

        if (startRun.getParent() == endRun.getParent()) {
            InlineIterableTemplate instance = new InlineIterableTemplate(startMark);
            instance.endMark = endMark;
            instance.templates = templates;
            return instance;
        }
        return this;
    }

}
