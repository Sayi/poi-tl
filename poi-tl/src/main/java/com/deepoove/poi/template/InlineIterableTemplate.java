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

import com.deepoove.poi.render.processor.Visitor;
import com.deepoove.poi.template.run.RunTemplate;

/**
 * An {@link IterableTemplate} whose start and end marks live in the same
 * paragraph.
 * <p>
 * Rendering inline keeps the text that surrounds the block, so several loops
 * can be written inside a single paragraph without breaking its layout.
 * </p>
 */
public class InlineIterableTemplate extends IterableTemplate {

    /**
     * Creates an inline iterable template with the given start mark.
     *
     * @param startMark the run holding the block start tag
     */
    public InlineIterableTemplate(RunTemplate startMark) {
        super(startMark);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
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
