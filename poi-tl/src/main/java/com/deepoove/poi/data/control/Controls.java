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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import com.deepoove.poi.data.RenderDataBuilder;
import com.deepoove.poi.data.control.ControlRenderData.LockType;
import com.deepoove.poi.data.style.Style;

/**
 * Factory for content-control render data.
 * <p>
 * Builders finish with {@link RenderDataBuilder#create()}, consistent with
 * {@code Texts}, {@code Pictures} and {@code Tables}.
 */
public final class Controls {

    private Controls() {
    }

    public static CheckBoxBuilder ofCheckBox() {
        return new CheckBoxBuilder();
    }

    public static CheckBoxBuilder ofCheckBox(boolean checked) {
        return new CheckBoxBuilder().checked(checked);
    }

    public static DropDownBuilder ofDropDown() {
        return new DropDownBuilder();
    }

    public static DropDownBuilder ofDropDown(String selectedValue) {
        return new DropDownBuilder().selected(selectedValue);
    }

    public static DateBuilder ofDate() {
        return new DateBuilder();
    }

    public static DateBuilder ofDate(Date date) {
        return new DateBuilder().date(date);
    }

    public static DateBuilder ofDate(LocalDate date) {
        return new DateBuilder().date(toDate(date));
    }

    public static DateBuilder ofDate(LocalDateTime date) {
        return new DateBuilder().date(toDate(date));
    }

    /**
     * Checkbox builder. The default glyph is MS Gothic ☒ / ☐.
     */
    public static class CheckBoxBuilder implements RenderDataBuilder<CheckBoxControlRenderData> {
        private final CheckBoxControlRenderData data = new CheckBoxControlRenderData();

        public CheckBoxBuilder checked(boolean checked) {
            data.setChecked(checked);
            return this;
        }

        public CheckBoxBuilder checkedChar(String hexCodePoint) {
            data.setCheckedChar(hexCodePoint);
            return this;
        }

        public CheckBoxBuilder uncheckedChar(String hexCodePoint) {
            data.setUncheckedChar(hexCodePoint);
            return this;
        }

        public CheckBoxBuilder font(String font) {
            data.setFont(font);
            return this;
        }

        /**
         * Word's built-in checkbox glyphs: Wingdings 2 {@code 0052} / {@code 00A3}.
         * Prefer the Unicode default when the document must also render in WPS.
         */
        public CheckBoxBuilder wingdings2() {
            data.setFont("Wingdings 2");
            data.setCheckedChar("0052");
            data.setUncheckedChar("00A3");
            return this;
        }

        public CheckBoxBuilder title(String title) {
            data.setTitle(title);
            return this;
        }

        public CheckBoxBuilder tag(String tag) {
            data.setTag(tag);
            return this;
        }

        public CheckBoxBuilder lock(LockType lock) {
            data.setLock(lock);
            return this;
        }

        public CheckBoxBuilder style(Style style) {
            data.setStyle(style);
            return this;
        }

        @Override
        public CheckBoxControlRenderData create() {
            return data;
        }
    }

    /**
     * Drop-down or combo-box builder. {@code selected} matches {@link Option#getValue()}.
     */
    public static class DropDownBuilder implements RenderDataBuilder<DropDownControlRenderData> {
        private final DropDownControlRenderData data = new DropDownControlRenderData();

        public DropDownBuilder selected(String value) {
            data.setSelectedValue(value);
            return this;
        }

        public DropDownBuilder placeholder(String placeholder) {
            data.setPlaceholder(placeholder);
            return this;
        }

        public DropDownBuilder comboBox(boolean comboBox) {
            data.setComboBox(comboBox);
            return this;
        }

        public DropDownBuilder addOption(String label, String value) {
            data.getOptions().add(Option.of(label, value));
            return this;
        }

        public DropDownBuilder addOption(Option option) {
            if (option != null) data.getOptions().add(option);
            return this;
        }

        public DropDownBuilder options(Collection<Option> options) {
            if (options != null) data.getOptions().addAll(options);
            return this;
        }

        public DropDownBuilder options(Option... options) {
            if (options != null) data.getOptions().addAll(Arrays.asList(options));
            return this;
        }

        public DropDownBuilder title(String title) {
            data.setTitle(title);
            return this;
        }

        public DropDownBuilder tag(String tag) {
            data.setTag(tag);
            return this;
        }

        public DropDownBuilder lock(LockType lock) {
            data.setLock(lock);
            return this;
        }

        public DropDownBuilder style(Style style) {
            data.setStyle(style);
            return this;
        }

        @Override
        public DropDownControlRenderData create() {
            return data;
        }
    }

    /**
     * Date-picker builder. A null date stays empty; pass {@code new Date()} explicitly
     * when the control should open on today.
     */
    public static class DateBuilder implements RenderDataBuilder<DateControlRenderData> {
        private final DateControlRenderData data = new DateControlRenderData();

        public DateBuilder date(Date date) {
            data.setDate(date);
            return this;
        }

        public DateBuilder format(String pattern) {
            data.setFormat(pattern);
            return this;
        }

        public DateBuilder locale(Locale locale) {
            data.setLocale(locale);
            return this;
        }

        public DateBuilder placeholder(String placeholder) {
            data.setPlaceholder(placeholder);
            return this;
        }

        public DateBuilder title(String title) {
            data.setTitle(title);
            return this;
        }

        public DateBuilder tag(String tag) {
            data.setTag(tag);
            return this;
        }

        public DateBuilder lock(LockType lock) {
            data.setLock(lock);
            return this;
        }

        public DateBuilder style(Style style) {
            data.setStyle(style);
            return this;
        }

        @Override
        public DateControlRenderData create() {
            return data;
        }
    }

    static Date toDate(LocalDate date) {
        if (date == null) return null;
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    static Date toDate(LocalDateTime date) {
        if (date == null) return null;
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
    }

}
