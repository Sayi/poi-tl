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

package com.deepoove.poi.render.processor;

import com.deepoove.poi.template.ChartTemplate;
import com.deepoove.poi.template.InlineIterableTemplate;
import com.deepoove.poi.template.IterableTemplate;
import com.deepoove.poi.template.PictImageTemplate;
import com.deepoove.poi.template.PictureTemplate;
import com.deepoove.poi.template.run.RunTemplate;

/**
 * @author Sayi
 */
public interface Visitor {

    /**
     * visit run template
     * 
     * @param runTemplate
     */
    void visit(RunTemplate runTemplate);

    /**
     * visit iterable template
     * 
     * @param iterableTemplate
     */
    void visit(IterableTemplate iterableTemplate);

    /**
     * visit inline iterable template
     * 
     * @param iterableTemplate
     */
    void visit(InlineIterableTemplate iterableTemplate);

    /**
     * visit picture template
     * 
     * @param pictureTemplate
     */
    void visit(PictureTemplate pictureTemplate);

    /**
     * visit pictImage template
     * 
     * @param pictImageTemplate
     */
    void visit(PictImageTemplate pictImageTemplate);

    /**
     * visit chart template
     * 
     * @param referenceTemplate
     */
    void visit(ChartTemplate referenceTemplate);

}
