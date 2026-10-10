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
package com.deepoove.poi.render;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.converter.ObjectToTextRenderDataConverter;
import com.deepoove.poi.converter.ToRenderDataConverter;
import com.deepoove.poi.data.NumberingRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.policy.NumberingRenderPolicy;
import com.deepoove.poi.policy.PictureRenderPolicy;
import com.deepoove.poi.policy.TableRenderPolicy;
import com.deepoove.poi.policy.TextRenderPolicy;

/**
 * Renders standard content at the location of the current tag.
 * <p>
 * It is exposed through {@link RenderContext#getWhereDelegate()} and gives a
 * render policy a shortcut to render text, pictures, tables or numbering
 * directly at the tag position, reusing the built-in render policies.
 * </p>
 * 
 * @author Sayi
 * @since 1.5.1
 */
public class WhereDelegate {

    private static final ToRenderDataConverter<Object, TextRenderData> converter = new ObjectToTextRenderDataConverter();

    private final XWPFRun run;

    /**
     * Creates a delegate for the given location.
     *
     * @param run the run where the tag sits
     */
    public WhereDelegate(XWPFRun run) {
        this.run = run;
    }

    /**
     * Returns the run where the tag sits.
     *
     * @return the run
     */
    public XWPFRun getRun() {
        return this.run;
    }

    /**
     * Renders the given data as text at the current location.
     *
     * @param data the data to render, converted to text automatically
     * @throws Exception when rendering fails
     */
    public void renderText(Object data) throws Exception {
        TextRenderData renderData = converter.convert(data);
        TextRenderPolicy.Helper.renderTextRun(run, renderData);
    }

    /**
     * Renders the given data as an ordered or unordered list at the current
     * location.
     *
     * @param data the numbering to render
     * @throws Exception when rendering fails
     */
    public void renderNumbering(NumberingRenderData data) throws Exception {
        NumberingRenderPolicy.Helper.renderNumbering(run, data);
    }

    /**
     * Renders the given data as a picture at the current location.
     *
     * @param data the picture to render
     * @throws Exception when rendering fails
     */
    public void renderPicture(PictureRenderData data) throws Exception {
        PictureRenderPolicy.Helper.renderPicture(run, data);
    }

    /**
     * Renders the given data as a table at the current location.
     *
     * @param data the table to render
     * @throws Exception when rendering fails
     */
    public void renderTable(TableRenderData data) throws Exception {
        TableRenderPolicy.Helper.renderTable(run, data);
    }

    /**
     * Inserts a picture from the given stream at the current location.
     *
     * @param inputStream the picture data
     * @param type        the picture type, see {@code Document.PICTURE_TYPE_*}
     * @param width       the width in pixels
     * @param height      the height in pixels
     * @throws InvalidFormatException when the picture format is not supported
     * @throws IOException            when the stream cannot be read
     */
    public void addPicture(InputStream inputStream, int type, int width, int height)
            throws InvalidFormatException, IOException {
        run.addPicture(inputStream, type, "Generated", Units.pixelToEMU(width), Units.pixelToEMU(height));
    }

}
