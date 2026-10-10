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

import java.util.Date;
import java.util.Locale;

/**
 * A date picker content control ({@code w:date}).
 * <p>
 * A null {@link #date} is left empty. It is not replaced with the generation
 * time. {@link #format} is written to {@code w:dateFormat} and is also used to
 * format the visible text, so the two stay in sync.
 */
public class DateControlRenderData extends ControlRenderData {

    private static final long serialVersionUID = 1L;

    private Date date;
    private String format = "yyyy-MM-dd";
    private Locale locale = Locale.SIMPLIFIED_CHINESE;
    private String placeholder = "请选择日期";

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale == null ? Locale.SIMPLIFIED_CHINESE : locale;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

}
