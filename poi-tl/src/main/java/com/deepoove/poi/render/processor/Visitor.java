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

package com.deepoove.poi.render.processor;

import com.deepoove.poi.template.ChartTemplate;
import com.deepoove.poi.template.InlineIterableTemplate;
import com.deepoove.poi.template.IterableTemplate;
import com.deepoove.poi.template.PictImageTemplate;
import com.deepoove.poi.template.PictureTemplate;
import com.deepoove.poi.template.run.RunTemplate;

/**
 * Visitor over the meta templates resolved from a document.
 * <p>
 * Each concrete meta template dispatches itself to the matching
 * {@code visit} overload, which keeps the processing logic out of the template
 * classes and lets different passes (rendering, logging) reuse the same tree.
 * </p>
 *
 * @author Sayi
 */
public interface Visitor {

    /**
     * Visits a run template.
     *
     * @param runTemplate the run template to visit
     */
    void visit(RunTemplate runTemplate);

    /**
     * Visits an iterable template.
     *
     * @param iterableTemplate the iterable template to visit
     */
    void visit(IterableTemplate iterableTemplate);

    /**
     * Visits an inline iterable template.
     *
     * @param iterableTemplate the inline iterable template to visit
     */
    void visit(InlineIterableTemplate iterableTemplate);

    /**
     * Visits a picture template.
     *
     * @param pictureTemplate the picture template to visit
     */
    void visit(PictureTemplate pictureTemplate);

    /**
     * Visits a picture template backed by a legacy VML pict element.
     *
     * @param pictImageTemplate the pict image template to visit
     */
    void visit(PictImageTemplate pictImageTemplate);

    /**
     * Visits a chart template.
     *
     * @param referenceTemplate the chart template to visit
     */
    void visit(ChartTemplate referenceTemplate);

}
