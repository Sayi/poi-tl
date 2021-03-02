/*
 * Copyright 2014-2020 Sayi
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
package com.deepoove.poi.util;

import org.apache.poi.xwpf.usermodel.IBodyElement;

import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;
import com.deepoove.poi.xwpf.XWPFSection;

public final class PageTools {

    public static int pageWidth(IBodyElement element) {
        BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(element.getBody());
        XWPFSection section = bodyContainer.closelySectPr(element);
        if (null == section) {
            throw new IllegalAccessError("Unable to read the page where the element is located.");
        }
        return section.getPaeContentWidth().intValue();
    }

}
