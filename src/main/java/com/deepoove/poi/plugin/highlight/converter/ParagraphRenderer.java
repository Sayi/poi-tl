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
package com.deepoove.poi.plugin.highlight.converter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFTable.XWPFBorderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codewaves.codehighlight.core.StyleRenderer;
import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.Paragraphs.ParagraphBuilder;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.data.Texts.TextBuilder;
import com.deepoove.poi.data.style.BorderStyle;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.highlight.HighlightStyle;
import com.deepoove.poi.util.StyleUtils;

/**
 * @author Sayi
 */
public class ParagraphRenderer implements StyleRenderer<ParagraphRenderData> {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ParagraphRenderer.class);

    private ParagraphBuilder of;
    private LinkedList<String> styleStack;
    private String blockResult;
    private String abortResult;

    private String fontFamily;
    private double fontSize;
    private List<SelectorStyle> cssStyle;

    public ParagraphRenderer(HighlightStyle style) {
        String theme = (null == style || null == style.getTheme()) ? "poitl" : style.getTheme();
        try {
            cssStyle = StylesheetParser.parse("highlightcss/" + theme + ".css");
            this.fontFamily = null == style ? null : style.getFontFamily();
            this.fontSize = null == style ? 0f : style.getFontSize();
        } catch (Exception e) {
            throw new IllegalArgumentException("Illegal highlight theme:" + theme, e);
        }
        of = Paragraphs.of();
        style();
    }

    private void style() {
        Map<String, String> pv = new HashMap<String, String>();
        for (SelectorStyle css : cssStyle) {
            if (".hljs".equals(css.getSelectorName())) {
                pv.putAll(css.getPropertyValues());
            }
        }
        ParagraphStyle paragraphStyle = StyleUtils.retriveParagraphStyleFromCss(pv);
        if (fontFamily != null && fontSize != 0f) {
            Style defaultTextStyle = paragraphStyle.getDefaultTextStyle();
            if (null == defaultTextStyle) {
                defaultTextStyle = Style.builder().build();
                paragraphStyle.setDefaultTextStyle(defaultTextStyle);
            }
            defaultTextStyle.setFontFamily(fontFamily);
            defaultTextStyle.setFontSize(fontSize);
        }
        if (null != paragraphStyle.getBackgroundColor()) {
            BorderStyle borderStyle = BorderStyle.builder()
                    .withColor(paragraphStyle.getBackgroundColor())
                    .withType(XWPFBorderType.SINGLE)
                    .withSize(48)
                    .build();
            paragraphStyle.setLeftBorder(borderStyle);
            paragraphStyle.setRightBorder(borderStyle);
            paragraphStyle.setBottomBorder(borderStyle);
            paragraphStyle.setTopBorder(borderStyle);
        }
        of.paraStyle(paragraphStyle).allowWordBreak().left();
    }

    @Override
    public void onStart() {
        styleStack = new LinkedList<String>();
        blockResult = "";
    }

    @Override
    public void onFinish() {
        appendBlock();
    }

    @Override
    public void onPushStyle(String style) {
        appendBlock();
        styleStack.push(style);
    }

    @Override
    public void onPopStyle() {
        appendBlock();
        styleStack.pop();
    }

    @Override
    public void onPushCodeBlock(CharSequence block) {
        blockResult += block;
    }

    private void appendBlock() {
        if (blockResult.isEmpty()) return;
        TextBuilder ofText = Texts.of(blockResult.toString());
        Map<String, String> pv = new HashMap<String, String>();
        for (int i = styleStack.size() - 1; i >= 0; i--) {
            String clazz = styleStack.get(i);
            for (SelectorStyle css : cssStyle) {
                if ((".hljs-" + clazz).equals(css.getSelectorName())) {
                    pv.putAll(css.getPropertyValues());
                }
            }
        }
        ofText.style(StyleUtils.retriveStyleFromCss(pv));
        of.addText(ofText.create());
        blockResult = "";

    }

    @Override
    public void onPushSubLanguage(String name, ParagraphRenderData code) {
        of.addParagraph(code);
    }

    @Override
    public void onPushOriginalSubLanguage(String name, CharSequence code) {
        of.addText(Texts.of(code.toString()).create());
    }

    @Override
    public void onAbort(CharSequence code, Exception e) {
        LOGGER.debug("Unable parse highlight code", e);
        abortResult = code.toString();
//         of.addText(Texts.of(code.toString()).create());
    }

    public ParagraphRenderData getResult() {
        ParagraphRenderData data = of.create();
        if (null != abortResult) {
            data.getContents().clear();
            data.getContents().add(Texts.of(abortResult).create());
        }
        return data;
    }

}
