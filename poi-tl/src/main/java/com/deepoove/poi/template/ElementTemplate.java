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
package com.deepoove.poi.template;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.policy.RenderPolicy;

/**
 * A meta template that belongs to a single document element.
 * <p>
 * An element template is composed of a grammar sign, a tag name and the source
 * text it was resolved from, where {@code sign + tagName == source}. It also
 * knows which {@link RenderPolicy} has to render it.
 * </p>
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
     * Returns the variable name of this template, namely its tag name.
     *
     * @return the tag name
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * Sets the variable name of this template.
     *
     * @param tagName the tag name to set
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Returns the raw tag text this template was resolved from.
     *
     * @return the source text
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the raw tag text this template was resolved from.
     *
     * @param source the source text to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Returns the grammar sign of this template, for example {@code @} for a
     * picture tag.
     *
     * @return the grammar sign
     */
    public Character getSign() {
        return sign;
    }

    /**
     * Sets the grammar sign of this template.
     *
     * @param sign the grammar sign to set
     */
    public void setSign(Character sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return source;
    }

    /**
     * {@inheritDoc}
     * <p>
     * An element template is identified by its source text.
     * </p>
     */
    @Override
    public String variable() {
        return source;
    }

    /**
     * Finds the render policy that must be applied to this template.
     *
     * @param config the template configuration
     * @return the render policy for this template
     */
    public abstract RenderPolicy findPolicy(Configure config);

}
