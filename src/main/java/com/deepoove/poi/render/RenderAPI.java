/*
 * Copyright 2014-2015 the original author or authors.
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

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.el.ELObject;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.policy.DocxRenderPolicy;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.util.ObjectUtils;

/**
 * @author Sayi
 * @version
 * @since 0.0.3
 * @see com.deepoove.poi.render.Render
 */
@Deprecated
public class RenderAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(RenderAPI.class);

    public static void render(XWPFTemplate template, Object dataModel) {

        ObjectUtils.requireNonNull(template, "Template is null, should be setted first.");
        ObjectUtils.requireNonNull(dataModel, "Data-Model is null, should be setted first.");

        LOGGER.info("Render the template file start...");

        int docxCount = 0;
        Configure config = template.getConfig();

        // 模板
        List<ElementTemplate> elementTemplates = template.getElementTemplates();
        if (null == elementTemplates || elementTemplates.isEmpty()) { return; }
        // 策略
        RenderPolicy policy = null;
        // 数据模型
        ELObject elObject = ELObject.create(dataModel);

        try {

            for (ElementTemplate runTemplate : elementTemplates) {
                policy = config.getPolicy(runTemplate.getTagName(), runTemplate.getSign());
                if (null == policy) { throw new RenderException(
                        "Cannot find render policy: [" + runTemplate.getTagName() + "]"); }

                if (policy instanceof DocxRenderPolicy) {
                    docxCount++;
                } else {
                    doRender(runTemplate, elObject, policy, template);
                }
            }

            if (docxCount >= 1) template.reload(template.getXWPFDocument().generate());

            NiceXWPFDocument current = null;
            for (int i = 0; i < docxCount; i++) {
                current = template.getXWPFDocument();
                elementTemplates = template.getElementTemplates();
                if (null == elementTemplates || elementTemplates.isEmpty()) {
                    break;
                }

                for (ElementTemplate runTemplate : elementTemplates) {
                    policy = config.getPolicy(runTemplate.getTagName(), runTemplate.getSign());
                    if (null == policy || !(policy instanceof DocxRenderPolicy)) {
                        continue;
                    }

                    doRender(runTemplate, elObject, policy, template);

                    // 没有最终合并，继续下一个合并
                    if (current == template.getXWPFDocument()) {
                        i++;
                        continue;
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new RenderException("Render docx error", e);
        }
        LOGGER.info("Render the template file successed.");
    }

    private static void doRender(ElementTemplate ele, ELObject model, RenderPolicy policy,
            XWPFTemplate template) {
        LOGGER.debug("Start render TemplateName:{}, Sign:{}, policy:{}", ele.getTagName(),
                ele.getSign(), policy.getClass().getSimpleName());
        policy.render(ele, model.eval(ele.getTagName()), template);
    }

    /**
     * 自我渲染
     * 
     * @param template
     */
    public static void selfRender(XWPFTemplate template) {
        ObjectUtils.requireNonNull(template, "Template is null, should be setted first.");
        List<ElementTemplate> elementTemplates = template.getElementTemplates();
        if (null == elementTemplates || elementTemplates.isEmpty()) return;
        RenderPolicy policy = null;
        for (ElementTemplate runTemplate : elementTemplates) {
            LOGGER.debug("Start self-render TemplateName:{}, Sign:{}", runTemplate.getTagName(),
                    runTemplate.getSign());
            policy = template.getConfig().getDefaultPolicys().get(Character.valueOf('\0'));
            policy.render(runTemplate, new TextRenderData(runTemplate.getSource()), template);
        }
    }

    /**
     * 协助调试：判断是否有缺失模板
     * 
     * @param template
     * @param datas
     */
    // TODO 数据模型为对象而不仅仅是Map
    @Deprecated
    public static void debug(XWPFTemplate template, Map<String, Object> datas) {
        List<ElementTemplate> all = template.getElementTemplates();
        LOGGER.debug("Template tag number is:{}", (null == all ? 0 : all.size()));
        if ((all == null || all.isEmpty()) && (null == datas || datas.isEmpty())) {
            LOGGER.debug("No template gramer find and no render data find");
            return;
        }
        Set<String> tagtKeys = new HashSet<String>();
        for (ElementTemplate ele : all) {
            LOGGER.debug("Parse the tag：{}", ele.getTagName());
            tagtKeys.add(ele.getTagName());
        }

        Set<String> keySet = datas.keySet();
        HashSet<String> copySet = new HashSet<String>(keySet);

        copySet.removeAll(tagtKeys);
        Iterator<String> iterator = copySet.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            LOGGER.warn("Cannot find the gramer tag in template:" + key);
        }
        tagtKeys.removeAll(keySet);
        iterator = tagtKeys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            LOGGER.warn("Cannot find the feild in java Map or Object:" + key);
        }

    }

}
