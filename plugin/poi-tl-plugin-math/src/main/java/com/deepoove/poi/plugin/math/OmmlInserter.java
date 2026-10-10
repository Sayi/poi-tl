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
package com.deepoove.poi.plugin.math;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;

/**
 * Places an OMML element in front of the placeholder run.
 * <p>
 * {@code m:oMath} and {@code m:oMathPara} are siblings of {@code w:r} inside
 * {@code w:p}, never children of the run, so the element is built at the end of
 * the paragraph and then moved - {@code moveXml}, not {@code copyXml}, or the
 * paragraph would keep a duplicate.
 *
 * @author Sayi
 */
final class OmmlInserter {

    private OmmlInserter() {
    }

    static void moveBefore(XmlObject omml, CTR placeholder) {
        XmlCursor source = omml.newCursor();
        XmlCursor target = placeholder.newCursor();
        try {
            source.moveXml(target);
        } finally {
            source.dispose();
            target.dispose();
        }
    }

}
