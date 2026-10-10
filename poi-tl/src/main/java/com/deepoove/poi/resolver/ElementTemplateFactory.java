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
package com.deepoove.poi.resolver;

import org.apache.poi.xwpf.usermodel.XWPFChart;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.template.ChartTemplate;
import com.deepoove.poi.template.PictImageTemplate;
import com.deepoove.poi.template.PictureTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.xwpf.CTPictWrapper;

/**
 * Factory to create Element template
 * <p>
 * Implement it to customize how the meta templates(run, picture, pict image and
 * chart) are created from the resolved tags.
 * </p>
 * 
 * @author Sayi
 */
public interface ElementTemplateFactory {

    /**
     * Create a run template
     * 
     * @param config template config
     * @param tag    tag name
     * @param run    run that holds the tag
     * @return run template
     */
    RunTemplate createRunTemplate(Configure config, String tag, XWPFRun run);

    /**
     * Create a picture template from an embedded picture
     * 
     * @param config template config
     * @param tag    tag name
     * @param pic    embedded picture that holds the tag
     * @return picture template
     */
    PictureTemplate createPicureTemplate(Configure config, String tag, XWPFPicture pic);

    /**
     * Create a picture template from a w:pict v:imagedata element
     * 
     * @param config template config
     * @param tag    tag name
     * @param pic    wrapper of the picture element
     * @param run    run that holds the tag
     * @return pict image template
     */
    PictImageTemplate createPictImageTemplate(Configure config, String tag, CTPictWrapper pic, XWPFRun run);

    /**
     * Create a chart template
     * 
     * @param config template config
     * @param tag    tag name
     * @param chart  chart that holds the tag
     * @param run    run that holds the tag
     * @return chart template
     */
    ChartTemplate createChartTemplate(Configure config, String tag, XWPFChart chart, XWPFRun run);

}
