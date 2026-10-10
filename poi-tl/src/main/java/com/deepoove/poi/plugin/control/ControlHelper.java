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
package com.deepoove.poi.plugin.control;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.values.XmlValueDisconnectedException;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.impl.CTPImpl;
import org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STCalendarType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtComboBox;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtDate;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtDropDownList;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtListItem;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSdtRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLock;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STSdtDateMappingType;

import com.deepoove.poi.data.control.CheckBoxControlRenderData;
import com.deepoove.poi.data.control.ControlRenderData;
import com.deepoove.poi.data.control.DateControlRenderData;
import com.deepoove.poi.data.control.DropDownControlRenderData;
import com.deepoove.poi.data.control.Option;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.util.StyleUtils;

/**
 * Builds inline content controls ({@code w:sdt} / {@code CTSdtRun}) in place of a
 * template run.
 * <p>
 * Stateless on purpose: a static flag shared across documents has already caused
 * lost markup in long-running renders.
 */
final class ControlHelper {

    /**
     * WordprocessingML namespace. Not the officeDocument namespace.
     */
    static final String W_NS = "http://schemas.openxmlformats.org/wordprocessingml/2006/main";
    static final String W14_NS = "http://schemas.microsoft.com/office/word/2010/wordml";

    private static final QName W14_CHECKBOX = new QName(W14_NS, "checkbox", "w14");
    private static final QName W14_CHECKED = new QName(W14_NS, "checked", "w14");
    private static final QName W14_CHECKED_STATE = new QName(W14_NS, "checkedState", "w14");
    private static final QName W14_UNCHECKED_STATE = new QName(W14_NS, "uncheckedState", "w14");
    private static final QName W14_VAL = new QName(W14_NS, "val", "w14");
    private static final QName W14_FONT = new QName(W14_NS, "font", "w14");
    private static final QName SDT_QNAME = new QName(W_NS, "sdt");
    /**
     * Every element that may be a sibling of a run inside a paragraph. The index
     * passed to {@code insert_element_user} counts this set, which is the physical
     * order in the paragraph. {@code CTP.insertNewSdt(int)} counts only SDTs and
     * therefore appends the control after the surrounding text.
     */
    private static final QNameSet PARAGRAPH_CONTENT = QNameSet.forArray(new QName[] {
            new QName(W_NS, "r"),
            new QName(W_NS, "hyperlink"),
            new QName(W_NS, "fldSimple"),
            new QName(W_NS, "sdt"),
            new QName(W_NS, "smartTag"),
            new QName(W_NS, "subDoc"),
            new QName(W_NS, "customXml"),
            new QName(W_NS, "del"),
            new QName(W_NS, "ins"),
            new QName(W_NS, "moveFrom"),
            new QName(W_NS, "moveTo"),
            new QName(W_NS, "proofErr"),
            new QName(W_NS, "permStart"),
            new QName(W_NS, "permEnd"),
            new QName(W_NS, "bookmarkStart"),
            new QName(W_NS, "bookmarkEnd"),
            new QName(W_NS, "moveFromRangeStart"),
            new QName(W_NS, "moveFromRangeEnd"),
            new QName(W_NS, "moveToRangeStart"),
            new QName(W_NS, "moveToRangeEnd"),
            new QName(W_NS, "commentRangeStart"),
            new QName(W_NS, "commentRangeEnd"),
            new QName(W_NS, "customXmlInsRangeStart"),
            new QName(W_NS, "customXmlInsRangeEnd"),
            new QName(W_NS, "customXmlDelRangeStart"),
            new QName(W_NS, "customXmlDelRangeEnd"),
            new QName(W_NS, "customXmlMoveFromRangeStart"),
            new QName(W_NS, "customXmlMoveFromRangeEnd"),
            new QName(W_NS, "customXmlMoveToRangeStart"),
            new QName(W_NS, "customXmlMoveToRangeEnd"),
            new QName(W_NS, "oMathPara"),
            new QName(W_NS, "oMath")
    });

    private ControlHelper() {
    }

    /**
     * Inserts a typed run-level SDT immediately before {@code source}.
     * <p>
     * The index counts every paragraph content sibling, matching the physical XML
     * order. A cursor {@code beginElement("sdt")} is not used because that token is
     * not a {@code CTSdtRun}. {@code CTP.insertNewSdt(int)} is not used either: its
     * index counts only existing SDTs, so the control lands after the following text.
     */
    static CTSdtRun insertSdtRunBefore(XWPFParagraph paragraph, CTR source) {
        CTP ctp = paragraph.getCTP();
        int index = indexOf(ctp, source);
        if (index < 0) {
            throw new RenderException("Content control placeholder run is not a direct child of its paragraph");
        }
        synchronized (ctp.monitor()) {
            Object inserted = ((CTPImpl) ctp).get_store().insert_element_user(PARAGRAPH_CONTENT, SDT_QNAME, index);
            if (!(inserted instanceof CTSdtRun)) {
                throw new RenderException("Inserted content control is not a run-level SDT");
            }
            return (CTSdtRun) inserted;
        }
    }

    private static int indexOf(CTP ctp, CTR source) {
        int index = 0;
        XmlCursor cursor = ctp.newCursor();
        try {
            cursor.selectPath("child::*");
            while (cursor.toNextSelection()) {
                XmlObject object;
                try {
                    object = cursor.getObject();
                } catch (XmlValueDisconnectedException disconnected) {
                    // A property element such as w:pPr has no XmlObject view.
                    continue;
                }
                if (object == source) return index;
                if (object != null) index++;
            }
            return -1;
        } finally {
            cursor.dispose();
        }
    }

    static void renderCheckBox(CTSdtRun sdt, CheckBoxControlRenderData data, CTR source, XWPFParagraph paragraph) {
        CTSdtPr pr = sdt.addNewSdtPr();
        initCommon(pr, data);
        String checkedHex = hex(data.getCheckedChar(), CheckBoxControlRenderData.DEFAULT_CHECKED_CHAR, data);
        String uncheckedHex = hex(data.getUncheckedChar(), CheckBoxControlRenderData.DEFAULT_UNCHECKED_CHAR, data);
        String font = StringUtils.defaultIfBlank(data.getFont(), CheckBoxControlRenderData.DEFAULT_FONT);
        applyW14Checkbox(pr, data.isChecked(), checkedHex, uncheckedHex, font);

        CTR content = sdt.addNewSdtContent().addNewR();
        copyStyle(pr, content, source, data, paragraph);
        forceSymbolFont(content, font);
        setText(content, glyph(data.isChecked() ? checkedHex : uncheckedHex, data));
    }

    static void renderDropDown(CTSdtRun sdt, DropDownControlRenderData data, CTR source, XWPFParagraph paragraph) {
        CTSdtPr pr = sdt.addNewSdtPr();
        initCommon(pr, data);
        // addNewComboBox() and addNewDropDownList() return different types.
        String shown = data.isComboBox() ? fillItems(pr.addNewComboBox(), data) : fillItems(pr.addNewDropDownList(), data);
        if (shown == null) {
            shown = StringUtils.defaultString(data.getPlaceholder());
            pr.addNewShowingPlcHdr();
        }
        CTR content = sdt.addNewSdtContent().addNewR();
        copyStyle(pr, content, source, data, paragraph);
        setText(content, shown);
    }

    static void renderDate(CTSdtRun sdt, DateControlRenderData data, CTR source, XWPFParagraph paragraph) {
        CTSdtPr pr = sdt.addNewSdtPr();
        initCommon(pr, data);
        CTSdtDate ctDate = pr.addNewDate();
        String pattern = StringUtils.defaultIfBlank(data.getFormat(), "yyyy-MM-dd");
        Locale locale = data.getLocale() == null ? Locale.SIMPLIFIED_CHINESE : data.getLocale();
        ctDate.addNewDateFormat().setVal(pattern);
        ctDate.addNewLid().setVal(locale.toLanguageTag());
        ctDate.addNewStoreMappedDataAs().setVal(STSdtDateMappingType.DATE_TIME);
        ctDate.addNewCalendar().setVal(STCalendarType.GREGORIAN);

        String shown;
        if (data.getDate() == null) {
            // Never substitute the generation timestamp for a missing date.
            shown = StringUtils.defaultString(data.getPlaceholder());
            pr.addNewShowingPlcHdr();
        } else {
            // POI writes w:fullDate from the calendar fields in the default timezone.
            // Use that same zone so the stored value and the visible text name the
            // same wall-clock time. Forcing UTC here shifts the value by the offset.
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(data.getDate());
            ctDate.setFullDate(calendar);
            shown = new SimpleDateFormat(pattern, locale).format(data.getDate());
        }
        CTR content = sdt.addNewSdtContent().addNewR();
        copyStyle(pr, content, source, data, paragraph);
        setText(content, shown);
    }

    private static void initCommon(CTSdtPr pr, ControlRenderData data) {
        pr.addNewId().setVal(BigInteger.valueOf(ThreadLocalRandom.current().nextLong(1L, Integer.MAX_VALUE)));
        if (StringUtils.isNotBlank(data.getTitle())) pr.addNewAlias().setVal(data.getTitle());
        if (StringUtils.isNotBlank(data.getTag())) pr.addNewTag().setVal(data.getTag());
        applyLock(pr, data.getLock());
    }

    private static void applyLock(CTSdtPr pr, ControlRenderData.LockType lock) {
        if (lock == null || lock == ControlRenderData.LockType.UNLOCKED) return;
        STLock.Enum value;
        switch (lock) {
        case SDT_LOCKED:
            value = STLock.SDT_LOCKED;
            break;
        case CONTENT_LOCKED:
            value = STLock.CONTENT_LOCKED;
            break;
        case SDT_CONTENT_LOCKED:
            value = STLock.SDT_CONTENT_LOCKED;
            break;
        default:
            return;
        }
        pr.addNewLock().setVal(value);
    }

    /**
     * poi-ooxml-lite has no typed w14 checkbox. The element is written with a cursor.
     * Attributes must use the w14 namespace; an un-namespaced {@code val} is ignored.
     */
    private static void applyW14Checkbox(CTSdtPr pr, boolean checked, String checkedHex, String uncheckedHex, String font) {
        XmlCursor cursor = pr.newCursor();
        try {
            cursor.toEndToken();
            cursor.beginElement(W14_CHECKBOX);

            cursor.beginElement(W14_CHECKED);
            cursor.insertAttributeWithValue(W14_VAL, checked ? "1" : "0");
            cursor.toParent();

            cursor.beginElement(W14_CHECKED_STATE);
            cursor.insertAttributeWithValue(W14_VAL, checkedHex);
            cursor.insertAttributeWithValue(W14_FONT, font);
            cursor.toParent();

            cursor.beginElement(W14_UNCHECKED_STATE);
            cursor.insertAttributeWithValue(W14_VAL, uncheckedHex);
            cursor.insertAttributeWithValue(W14_FONT, font);
            cursor.toParent();
        } finally {
            cursor.dispose();
        }
    }

    private static String fillItems(CTSdtDropDownList list, DropDownControlRenderData data) {
        String shown = null;
        for (Option option : data.getOptions()) {
            if (option == null) continue;
            CTSdtListItem item = list.addNewListItem();
            String label = StringUtils.defaultString(option.getLabel());
            String value = option.getValue() == null ? "" : option.getValue();
            item.setDisplayText(label);
            item.setValue(value);
            if (shown == null && value.equals(data.getSelectedValue()) && data.getSelectedValue() != null) {
                shown = label;
                list.setLastValue(value);
            }
        }
        return shown;
    }

    private static String fillItems(CTSdtComboBox list, DropDownControlRenderData data) {
        String shown = null;
        for (Option option : data.getOptions()) {
            if (option == null) continue;
            CTSdtListItem item = list.addNewListItem();
            String label = StringUtils.defaultString(option.getLabel());
            String value = option.getValue() == null ? "" : option.getValue();
            item.setDisplayText(label);
            item.setValue(value);
            if (shown == null && value.equals(data.getSelectedValue()) && data.getSelectedValue() != null) {
                shown = label;
                list.setLastValue(value);
            }
        }
        return shown;
    }

    private static void copyStyle(CTSdtPr pr, CTR target, CTR source, ControlRenderData data, XWPFParagraph paragraph) {
        if (source.isSetRPr()) {
            CTRPr copy = (CTRPr) source.getRPr().copy();
            target.setRPr(copy);
            pr.setRPr((CTRPr) copy.copy());
        }
        if (data.getStyle() != null) {
            XWPFRun wrapper = new XWPFRun(target, (IRunBody) paragraph);
            StyleUtils.styleRun(wrapper, data.getStyle());
            if (target.isSetRPr()) pr.setRPr((CTRPr) target.getRPr().copy());
        }
    }

    /** Symbol fonts must win over the east-Asian body font, or ☒ renders as a missing glyph. */
    private static void forceSymbolFont(CTR target, String font) {
        CTRPr rPr = target.isSetRPr() ? target.getRPr() : target.addNewRPr();
        CTFonts fonts = rPr.sizeOfRFontsArray() > 0 ? rPr.getRFontsArray(0) : rPr.addNewRFonts();
        fonts.setAscii(font);
        fonts.setHAnsi(font);
        fonts.setCs(font);
    }

    private static void setText(CTR target, String text) {
        CTText ctText = target.addNewT();
        String value = text == null ? "" : text;
        ctText.setStringValue(value);
        if (needsPreserve(value)) ctText.setSpace(SpaceAttribute.Space.PRESERVE);
    }

    private static boolean needsPreserve(String value) {
        return value.startsWith(" ") || value.endsWith(" ") || value.indexOf('\t') >= 0;
    }

    private static String hex(String raw, String fallback, ControlRenderData data) {
        String value = StringUtils.defaultIfBlank(raw, fallback);
        if (value.startsWith("0x") || value.startsWith("0X")) value = value.substring(2);
        if (!value.matches("[0-9A-Fa-f]{1,6}")) {
            throw new RenderException("Illegal checkbox character code '" + raw + "' for tag " + data.getTag());
        }
        return value.toUpperCase(Locale.ROOT);
    }

    private static String glyph(String hex, ControlRenderData data) {
        int codePoint;
        try {
            codePoint = Integer.parseInt(hex, 16);
        } catch (NumberFormatException e) {
            throw new RenderException("Illegal checkbox character code '" + hex + "' for tag " + data.getTag(), e);
        }
        if (!Character.isValidCodePoint(codePoint)) {
            throw new RenderException("Illegal checkbox character code '" + hex + "' for tag " + data.getTag());
        }
        return new String(Character.toChars(codePoint));
    }

}
