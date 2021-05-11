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
package com.deepoove.poi.template;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.policy.RenderPolicy;

/**
 * sign + tagName == source
 * 
 * @author Sayi
 */
public abstract class ElementTemplate implements MetaTemplate {
    protected Character sign;
    protected String tagName;
    protected String source;

    public ElementTemplate() {
    }

    /**
     * @return the tagName
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * @param tagName the tagName to set
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Character getSign() {
        return sign;
    }

    public void setSign(Character sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return source;
    }

    @Override
    public String variable() {
        return source;
    }

    public abstract RenderPolicy findPolicy(Configure config);

}
