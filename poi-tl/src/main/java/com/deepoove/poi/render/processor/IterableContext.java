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
package com.deepoove.poi.render.processor;

/**
 * Positions delimiting the content of an iterable block.
 * <p>
 * {@code start} and {@code end} are body element indices for paragraph level
 * loops, or run indices for inline loops; the content between them is copied
 * once per element of the iteration.
 * </p>
 */
public class IterableContext {

    private int start;
    private int end;

    private NumberingContinue numberingContinue;

    /**
     * Creates a context without numbering continuity handling.
     *
     * @param start the start position of the block content
     * @param end   the end position of the block content
     */
    public IterableContext(int start, int end) {
        this(start, end, null);
    }

    /**
     * Creates a context with numbering continuity handling.
     *
     * @param start             the start position of the block content
     * @param end               the end position of the block content
     * @param numberingContinue the numbering helper applied to copied paragraphs
     */
    public IterableContext(int start, int end, NumberingContinue numberingContinue) {
        this.start = start;
        this.end = end;
        this.numberingContinue = numberingContinue;
    }

    /**
     * Returns the start position of the block content.
     *
     * @return the start position
     */
    public int getStart() {
        return start;
    }

    /**
     * Sets the start position of the block content.
     *
     * @param start the start position to set
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * Returns the end position of the block content.
     *
     * @return the end position
     */
    public int getEnd() {
        return end;
    }

    /**
     * Sets the end position of the block content.
     *
     * @param end the end position to set
     */
    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * Returns the helper that keeps list numbering continuous across iterations.
     *
     * @return the numbering helper, or {@code null} when not applicable
     */
    public NumberingContinue getNumberingContinue() {
        return numberingContinue;
    }

    /**
     * Sets the helper that keeps list numbering continuous across iterations.
     *
     * @param numberingContinue the numbering helper to set
     */
    public void setNumberingContinue(NumberingContinue numberingContinue) {
        this.numberingContinue = numberingContinue;
    }

}
