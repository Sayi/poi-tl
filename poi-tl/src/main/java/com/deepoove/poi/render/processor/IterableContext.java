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
package com.deepoove.poi.render.processor;

public class IterableContext {

    private int start;
    private int end;

    private NumberingContinue numberingContinue;

    public IterableContext(int start, int end) {
        this(start, end, null);
    }

    public IterableContext(int start, int end, NumberingContinue numberingContinue) {
        this.start = start;
        this.end = end;
        this.numberingContinue = numberingContinue;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public NumberingContinue getNumberingContinue() {
        return numberingContinue;
    }

    public void setNumberingContinue(NumberingContinue numberingContinue) {
        this.numberingContinue = numberingContinue;
    }

}
