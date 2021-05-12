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

import java.util.Collections;
import java.util.Iterator;

import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Merge multiple NiceXWPFDocument
 * 
 * @author Sayi
 *
 */
public abstract class AbstractXWPFDocumentMerge implements DocumentMerge<NiceXWPFDocument> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public NiceXWPFDocument merge(NiceXWPFDocument source, NiceXWPFDocument merged) throws Exception {
        return merge(source, Collections.singleton(merged).iterator());
    }

    @Override
    public NiceXWPFDocument merge(NiceXWPFDocument source, Iterator<NiceXWPFDocument> mergedIterator) throws Exception {
        return merge(source, mergedIterator, source.createParagraph().createRun());
    }

    /**
     * the each element in mergedIterator should be the same document!
     */
    @Override
    public abstract NiceXWPFDocument merge(NiceXWPFDocument source, Iterator<NiceXWPFDocument> mergedIterator,
            XWPFRun location) throws Exception;

}
