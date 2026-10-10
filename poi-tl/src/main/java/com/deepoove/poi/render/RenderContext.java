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

import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

/**
 * The context of a single tag being rendered.
 * <p>
 * A render policy receives a context and reads from it everything it needs: the
 * resolved template, the bound data, the enclosing document and the run where
 * the tag sits. It is the central extension point when writing a custom
 * {@link com.deepoove.poi.policy.RenderPolicy}.
 * </p>
 * 
 * @param <T> the type of the data bound to the tag
 * 
 * @author Sayi
 */
public class RenderContext<T> {

    private final ElementTemplate eleTemplate;
    private final T data;
    private final XWPFTemplate template;
    private final WhereDelegate where;

    /**
     * Creates the context of one tag.
     *
     * @param eleTemplate the template being rendered
     * @param data        the data bound to the template
     * @param template    the template instance being rendered
     */
    public RenderContext(ElementTemplate eleTemplate, T data, XWPFTemplate template) {
        this.eleTemplate = eleTemplate;
        this.data = data;
        this.template = template;
        where = new WhereDelegate(((RunTemplate) this.eleTemplate).getRun());
    }

    /**
     * Returns the template being rendered.
     *
     * @return the element template
     */
    public ElementTemplate getEleTemplate() {
        return eleTemplate;
    }

    /**
     * Returns the data bound to the template.
     *
     * @return the data, may be {@code null}
     */
    public T getThing() {
        return data;
    }

    /**
     * Returns the data bound to the template.
     *
     * @return the data, may be {@code null}
     */
    public T getData() {
        return data;
    }

    /**
     * Returns the template instance being rendered.
     *
     * @return the template
     */
    public XWPFTemplate getTemplate() {
        return template;
    }

    /**
     * Returns the document of the template being rendered.
     *
     * @return the document
     */
    public NiceXWPFDocument getXWPFDocument() {
        return this.template.getXWPFDocument();
    }

    /**
     * Returns the helper that renders content at the current location.
     *
     * @return the location delegate
     */
    public WhereDelegate getWhereDelegate() {
        return where;
    }

    /**
     * Returns the run where the tag sits.
     *
     * @return the run
     */
    public XWPFRun getWhere() {
        return getRun();
    }

    /**
     * Returns the run where the tag sits.
     *
     * @return the run
     */
    public XWPFRun getRun() {
        return ((RunTemplate) eleTemplate).getRun();
    }

    /**
     * Returns the body that contains the tag, for example the document, a table
     * cell, a header or a footer.
     *
     * @return the container of the run
     */
    public IBody getContainer() {
        // XWPFTableCell、XWPFDocument、XWPFHeaderFooter、XWPFAbstractFootnoteEndnote
        return ((XWPFParagraph) getRun().getParent()).getBody();
    }

    /**
     * Returns the configuration of the template being rendered.
     *
     * @return the configuration
     */
    public Configure getConfig() {
        return getTemplate().getConfig();
    }

    /**
     * Returns the raw source text of the tag being rendered.
     *
     * @return the tag source
     */
    public Object getTagSource() {
        return getEleTemplate().getSource();
    }

}
