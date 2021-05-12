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

import java.util.Iterator;

import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * Merge multiple word elements in a specified location or at the end
 * 
 * @author Sayi
 *
 */
public interface DocumentMerge<T> {

    /**
     * Merge one element into another
     * 
     * @param source doc
     * @param merged merged doc
     * @return merged result
     * @throws Exception
     */
    public T merge(T source, T merged) throws Exception;

    /**
     * Merge some element into another at the end
     * 
     * @param source         doc
     * @param mergedIterator merged docs
     * @return
     * @throws Exception
     */
    public T merge(T source, Iterator<T> mergedIterator) throws Exception;

    /**
     * Merge some elements into another in a specified location
     * 
     * @param source         doc
     * @param mergedIterator merged docs
     * @param location       a specified location
     * @return
     * @throws Exception
     */
    public T merge(T source, Iterator<T> mergedIterator, XWPFRun location) throws Exception;

}
