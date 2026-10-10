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

import com.deepoove.poi.XWPFTemplate;

/**
 * The interface of render
 * <p>
 * A render expands every tag of a compiled {@link XWPFTemplate} with the values
 * provided by a data model. The default implementation is
 * {@link DefaultRender}; a custom implementation can be registered through the
 * configuration to replace the rendering process.
 * </p>
 * 
 * @author Sayi
 *
 */
public interface Render {

    /**
     * Renders the given template with the given data model.
     *
     * @param template the compiled template
     * @param root     the root object of the data model
     */
    void render(XWPFTemplate template, Object root);

}
