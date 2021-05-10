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

import java.math.BigInteger;

import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;

public class XWPFPageMargin {

    private BigInteger top;
    private BigInteger bottom;
    private BigInteger right;
    private BigInteger left;
    private BigInteger gutter;

    public XWPFPageMargin(CTPageMar pgMar) {
        this.left = pgMar.getLeft();
        this.top = pgMar.getTop();
        this.bottom = pgMar.getBottom();
        this.right = pgMar.getRight();
        this.gutter = pgMar.getGutter();
    }

    public BigInteger getTop() {
        return top;
    }

    public BigInteger getBottom() {
        return bottom;
    }

    public BigInteger getRight() {
        return right;
    }

    public BigInteger getLeft() {
        return left;
    }

    public BigInteger getGutter() {
        return gutter;
    }

}
