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
package com.deepoove.poi.plugin.control;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtRun;

import com.deepoove.poi.data.control.CheckBoxControlRenderData;
import com.deepoove.poi.data.control.ControlRenderData;
import com.deepoove.poi.data.control.Controls;
import com.deepoove.poi.data.control.DateControlRenderData;
import com.deepoove.poi.data.control.DropDownControlRenderData;
import com.deepoove.poi.data.control.Option;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.util.ParagraphUtils;
import com.deepoove.poi.xwpf.XWPFParagraphWrapper;

/**
 * Renders an inline Word content control in place of a template tag.
 * <p>
 * Bind it explicitly. It is not a default policy, so a plain {@code Boolean} or
 * {@code Date} rendered by {@code TextRenderPolicy} is unchanged.
 * <p>
 * Accepted values: {@link ControlRenderData}, {@link Boolean} (checkbox),
 * {@link Date} / {@link LocalDate} / {@link LocalDateTime} (date picker), and
 * {@code List<Option>} (drop-down).
 */
public class ControlRenderPolicy extends AbstractRenderPolicy<Object> {

    /**
     * {@inheritDoc}
     * <p>
     * Any non-null value is accepted; values that cannot be converted are rejected
     * while rendering.
     * </p>
     *
     * @param data the value bound to the tag
     * @return {@code true} when the value is not {@code null}
     */
    @Override
    protected boolean validate(Object data) {
        return data != null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Converts the bound value into a content control model, inserts the matching
     * {@code w:sdt} element before the tag run and removes the placeholder run.
     * </p>
     *
     * @param context the render context holding the target run and the control data
     * @throws Exception if the placeholder is not in a paragraph or the value cannot
     *         be converted
     */
    @Override
    public void doRender(RenderContext<Object> context) throws Exception {
        ControlRenderData data = convert(context.getData());
        XWPFRun run = context.getRun();
        if (!(run.getParent() instanceof XWPFParagraph)) {
            throw new RenderException("Content control placeholder must sit in a paragraph: " + context.getEleTemplate());
        }
        XWPFParagraph paragraph = (XWPFParagraph) run.getParent();
        CTR source = run.getCTR();
        // Capture before insertion. removeRun's index is into XWPFRun, and an SDT is not one.
        Integer runPos = ParagraphUtils.getRunPos(run);
        if (runPos == null) {
            throw new RenderException("Content control placeholder run cannot be located: " + context.getEleTemplate());
        }

        CTSdtRun sdt = ControlHelper.insertSdtRunBefore(paragraph, source);
        if (data instanceof CheckBoxControlRenderData) {
            ControlHelper.renderCheckBox(sdt, (CheckBoxControlRenderData) data, source, paragraph);
        } else if (data instanceof DropDownControlRenderData) {
            ControlHelper.renderDropDown(sdt, (DropDownControlRenderData) data, source, paragraph);
        } else if (data instanceof DateControlRenderData) {
            ControlHelper.renderDate(sdt, (DateControlRenderData) data, source, paragraph);
        } else {
            throw new RenderException("Unsupported content control: " + data.getClass().getName());
        }
        new XWPFParagraphWrapper(paragraph).removeRun(runPos);
    }

    private ControlRenderData convert(Object raw) {
        if (raw instanceof ControlRenderData) return (ControlRenderData) raw;
        if (raw instanceof Boolean) return Controls.ofCheckBox(((Boolean) raw).booleanValue()).create();
        if (raw instanceof Date) return Controls.ofDate((Date) raw).create();
        if (raw instanceof LocalDate) return Controls.ofDate((LocalDate) raw).create();
        if (raw instanceof LocalDateTime) return Controls.ofDate((LocalDateTime) raw).create();
        if (raw instanceof List) {
            List<?> list = (List<?>) raw;
            if (list.isEmpty() || list.get(0) instanceof Option) {
                DropDownControlRenderData data = Controls.ofDropDown().create();
                for (Object item : list) {
                    if (item instanceof Option) data.getOptions().add((Option) item);
                    else throw unsupported(raw);
                }
                return data;
            }
        }
        throw unsupported(raw);
    }

    private RenderException unsupported(Object raw) {
        return new RenderException("Unsupported data for ControlRenderPolicy: " + raw.getClass().getName());
    }

}
