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

package com.deepoove.poi.xwpf;

import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.template.IterableTemplate;

/**
 * Factory to create RunBodyContext
 */
public class RunBodyContextFactory {

    public static RunBodyContext getRunBodyContext(IRunBody body) {
        if (body instanceof XWPFStructuredDocumentTagContent) {
            return new SDTContentContext((XWPFStructuredDocumentTagContent) body);
        } else {
            return new ParagraphContext(new XWPFParagraphWrapper((XWPFParagraph) body));
        }
    }

    public static RunBodyContext getRunBodyContext(XWPFRun run) {
        return getRunBodyContext(run.getParent());
    }

    public static RunBodyContext getRunBodyContext(IterableTemplate iterableTemplate) {
        return getRunBodyContext(iterableTemplate.getStartRun());
    }

}
