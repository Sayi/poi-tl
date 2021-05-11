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
 * 
 * @author Sayi
 */
public interface ElementTemplateFactory {

    RunTemplate createRunTemplate(Configure config, String tag, XWPFRun run);

    PictureTemplate createPicureTemplate(Configure config, String tag, XWPFPicture pic);

    PictImageTemplate createPictImageTemplate(Configure config, String tag, CTPictWrapper pic, XWPFRun run);

    ChartTemplate createChartTemplate(Configure config, String tag, XWPFChart chart, XWPFRun run);

}
