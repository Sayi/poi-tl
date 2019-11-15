/*
 * Copyright 2014-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.render;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.NumbericRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.policy.MiniTableRenderPolicy;
import com.deepoove.poi.policy.NumbericRenderPolicy;
import com.deepoove.poi.policy.PictureRenderPolicy;
import com.deepoove.poi.policy.TextRenderPolicy;

/**
 * 对当前位置的委托，提供更多操作当前位置的方法。
 * 
 * @author Sayi
 * @version 1.5.1
 */
public class WhereDelegate {

    private final XWPFRun run;

    public WhereDelegate(XWPFRun run) {
        this.run = run;
    }

    public XWPFRun getRun() {
        return this.run;
    }

    public void renderText(Object data) {
        TextRenderPolicy.Helper.renderTextRun(run, data);
    }

    public void renderNumberic(NumbericRenderData data) throws Exception {
        NumbericRenderPolicy.Helper.renderNumberic(run, data);
    }

    public void renderPicture(PictureRenderData data) throws Exception {
        PictureRenderPolicy.Helper.renderPicture(run, data);
    }

    public void renderMiniTable(MiniTableRenderData data) {
        MiniTableRenderPolicy.Helper.renderMiniTable(run, data);
    }

    public void addPicture(InputStream inputStream, int type, int width, int height)
            throws InvalidFormatException, IOException {
        run.addPicture(inputStream, type, "Generated", width * PictureRenderPolicy.Helper.EMU,
                height * PictureRenderPolicy.Helper.EMU);
    }

}
