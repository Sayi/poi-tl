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
package com.deepoove.poi.data.control;

import com.deepoove.poi.data.RenderData;
import com.deepoove.poi.data.style.Style;

/**
 * Common metadata of a Word content control (SDT).
 */
public abstract class ControlRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    /**
     * How the generated content control may be edited in Word.
     * <p>
     * This does not turn on document protection. Text outside the control stays
     * editable.
     */
    public enum LockType {
        /** Control can be edited and deleted. */
        UNLOCKED,
        /** Control cannot be deleted; its content can still be edited. */
        SDT_LOCKED,
        /** Content is read-only; the control itself can still be deleted. */
        CONTENT_LOCKED,
        /** Control cannot be deleted and its content cannot be edited. */
        SDT_CONTENT_LOCKED
    }

    /** Hover title, written to {@code w:alias}. */
    private String title;
    /** Business key, written to {@code w:tag}, used to read the value back later. */
    private String tag;
    private LockType lock = LockType.UNLOCKED;
    /** When non-null, overrides the style inherited from the template run. */
    private Style style;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public LockType getLock() {
        return lock;
    }

    public void setLock(LockType lock) {
        this.lock = lock == null ? LockType.UNLOCKED : lock;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

}
