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
package com.deepoove.poi.policy;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.template.ElementTemplate;

/**
 * Do Anything Anywhere
 * 
 * @author Sayi
 * @version 0.0.1
 */
@FunctionalInterface
public interface RenderPolicy {

    /**
     * @param eleTemplate template tag
     * @param data        render data
     * @param template    XWPFTemplate instance
     */
    void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template);

}
