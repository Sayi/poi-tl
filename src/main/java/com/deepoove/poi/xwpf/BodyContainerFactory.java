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

package com.deepoove.poi.xwpf;

import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.XWPFHeaderFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;

import com.deepoove.poi.template.IterableTemplate;

public class BodyContainerFactory {

    public static BodyContainer getBodyContainer(IBody body) {
        BodyContainer bodyContainer = null;
        if (body instanceof XWPFTableCell) {
            bodyContainer = new CellBodyContainer((XWPFTableCell) body);
        } else if (body instanceof XWPFHeaderFooter) {
            bodyContainer = new HeaderFooterBodyContainer((XWPFHeaderFooter) body);
        } else {
            bodyContainer = new DocumentBodyContainer((NiceXWPFDocument) body);
        }
        return bodyContainer;
    }

    public static BodyContainer getBodyContainer(XWPFRun run) {
        return getBodyContainer(((XWPFParagraph) run.getParent()).getBody());
    }

    public static BodyContainer getBodyContainer(IterableTemplate iterableTemplate) {
        return getBodyContainer(iterableTemplate.getStartRun());
    }

}
