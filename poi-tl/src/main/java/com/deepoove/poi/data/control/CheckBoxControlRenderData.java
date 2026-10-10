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

/**
 * A clickable checkbox content control ({@code w14:checkbox}).
 * <p>
 * Character codes are hexadecimal Unicode code points without a {@code U+} prefix,
 * for example {@code 2612} for ☒. The displayed glyph is written into
 * {@code w:sdtContent} and must match {@link #checked}; Word does not repaint the
 * content from {@code w14:checked} when the file is opened.
 */
public class CheckBoxControlRenderData extends ControlRenderData {

    private static final long serialVersionUID = 1L;

    public static final String DEFAULT_CHECKED_CHAR = "2612";
    public static final String DEFAULT_UNCHECKED_CHAR = "2610";
    public static final String DEFAULT_FONT = "MS Gothic";

    private boolean checked;
    private String checkedChar = DEFAULT_CHECKED_CHAR;
    private String uncheckedChar = DEFAULT_UNCHECKED_CHAR;
    private String font = DEFAULT_FONT;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getCheckedChar() {
        return checkedChar;
    }

    public void setCheckedChar(String checkedChar) {
        this.checkedChar = checkedChar;
    }

    public String getUncheckedChar() {
        return uncheckedChar;
    }

    public void setUncheckedChar(String uncheckedChar) {
        this.uncheckedChar = uncheckedChar;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

}
