/*
 * XML Type:  CT_MathPr
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTMathPr
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.XmlObject;

/**
 * An XML CT_MathPr(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public class CTMathPrImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.openxmlformats.schemas.officeDocument.x2006.math.CTMathPr {
    private static final long serialVersionUID = 1L;

    public CTMathPrImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    private static final QName[] PROPERTY_QNAME = {
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "mathFont"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "brkBin"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "brkBinSub"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "smallFrac"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "dispDef"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "lMargin"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "rMargin"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "defJc"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "preSp"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "postSp"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "interSp"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "intraSp"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "wrapIndent"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "wrapRight"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "intLim"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "naryLim"),
    };


    /**
     * Gets the "mathFont" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTString getMathFont() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTString target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTString)get_store().find_element_user(PROPERTY_QNAME[0], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "mathFont" element
     */
    @Override
    public boolean isSetMathFont() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[0]) != 0;
        }
    }

    /**
     * Sets the "mathFont" element
     */
    @Override
    public void setMathFont(org.openxmlformats.schemas.officeDocument.x2006.math.CTString mathFont) {
        generatedSetterHelperImpl(mathFont, PROPERTY_QNAME[0], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "mathFont" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTString addNewMathFont() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTString target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTString)get_store().add_element_user(PROPERTY_QNAME[0]);
            return target;
        }
    }

    /**
     * Unsets the "mathFont" element
     */
    @Override
    public void unsetMathFont() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[0], 0);
        }
    }

    /**
     * Gets the "brkBin" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBin getBrkBin() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBin target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBin)get_store().find_element_user(PROPERTY_QNAME[1], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "brkBin" element
     */
    @Override
    public boolean isSetBrkBin() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[1]) != 0;
        }
    }

    /**
     * Sets the "brkBin" element
     */
    @Override
    public void setBrkBin(org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBin brkBin) {
        generatedSetterHelperImpl(brkBin, PROPERTY_QNAME[1], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "brkBin" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBin addNewBrkBin() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBin target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBin)get_store().add_element_user(PROPERTY_QNAME[1]);
            return target;
        }
    }

    /**
     * Unsets the "brkBin" element
     */
    @Override
    public void unsetBrkBin() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[1], 0);
        }
    }

    /**
     * Gets the "brkBinSub" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBinSub getBrkBinSub() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBinSub target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBinSub)get_store().find_element_user(PROPERTY_QNAME[2], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "brkBinSub" element
     */
    @Override
    public boolean isSetBrkBinSub() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[2]) != 0;
        }
    }

    /**
     * Sets the "brkBinSub" element
     */
    @Override
    public void setBrkBinSub(org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBinSub brkBinSub) {
        generatedSetterHelperImpl(brkBinSub, PROPERTY_QNAME[2], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "brkBinSub" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBinSub addNewBrkBinSub() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBinSub target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBinSub)get_store().add_element_user(PROPERTY_QNAME[2]);
            return target;
        }
    }

    /**
     * Unsets the "brkBinSub" element
     */
    @Override
    public void unsetBrkBinSub() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[2], 0);
        }
    }

    /**
     * Gets the "smallFrac" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getSmallFrac() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().find_element_user(PROPERTY_QNAME[3], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "smallFrac" element
     */
    @Override
    public boolean isSetSmallFrac() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[3]) != 0;
        }
    }

    /**
     * Sets the "smallFrac" element
     */
    @Override
    public void setSmallFrac(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff smallFrac) {
        generatedSetterHelperImpl(smallFrac, PROPERTY_QNAME[3], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "smallFrac" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewSmallFrac() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().add_element_user(PROPERTY_QNAME[3]);
            return target;
        }
    }

    /**
     * Unsets the "smallFrac" element
     */
    @Override
    public void unsetSmallFrac() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[3], 0);
        }
    }

    /**
     * Gets the "dispDef" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getDispDef() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().find_element_user(PROPERTY_QNAME[4], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "dispDef" element
     */
    @Override
    public boolean isSetDispDef() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[4]) != 0;
        }
    }

    /**
     * Sets the "dispDef" element
     */
    @Override
    public void setDispDef(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff dispDef) {
        generatedSetterHelperImpl(dispDef, PROPERTY_QNAME[4], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "dispDef" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewDispDef() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().add_element_user(PROPERTY_QNAME[4]);
            return target;
        }
    }

    /**
     * Unsets the "dispDef" element
     */
    @Override
    public void unsetDispDef() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[4], 0);
        }
    }

    /**
     * Gets the "lMargin" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure getLMargin() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure)get_store().find_element_user(PROPERTY_QNAME[5], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "lMargin" element
     */
    @Override
    public boolean isSetLMargin() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[5]) != 0;
        }
    }

    /**
     * Sets the "lMargin" element
     */
    @Override
    public void setLMargin(org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure lMargin) {
        generatedSetterHelperImpl(lMargin, PROPERTY_QNAME[5], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "lMargin" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure addNewLMargin() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure)get_store().add_element_user(PROPERTY_QNAME[5]);
            return target;
        }
    }

    /**
     * Unsets the "lMargin" element
     */
    @Override
    public void unsetLMargin() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[5], 0);
        }
    }

    /**
     * Gets the "rMargin" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure getRMargin() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure)get_store().find_element_user(PROPERTY_QNAME[6], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "rMargin" element
     */
    @Override
    public boolean isSetRMargin() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[6]) != 0;
        }
    }

    /**
     * Sets the "rMargin" element
     */
    @Override
    public void setRMargin(org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure rMargin) {
        generatedSetterHelperImpl(rMargin, PROPERTY_QNAME[6], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "rMargin" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure addNewRMargin() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure)get_store().add_element_user(PROPERTY_QNAME[6]);
            return target;
        }
    }

    /**
     * Unsets the "rMargin" element
     */
    @Override
    public void unsetRMargin() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[6], 0);
        }
    }

    /**
     * Gets the "defJc" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc getDefJc() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc)get_store().find_element_user(PROPERTY_QNAME[7], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "defJc" element
     */
    @Override
    public boolean isSetDefJc() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[7]) != 0;
        }
    }

    /**
     * Sets the "defJc" element
     */
    @Override
    public void setDefJc(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc defJc) {
        generatedSetterHelperImpl(defJc, PROPERTY_QNAME[7], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "defJc" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc addNewDefJc() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc)get_store().add_element_user(PROPERTY_QNAME[7]);
            return target;
        }
    }

    /**
     * Unsets the "defJc" element
     */
    @Override
    public void unsetDefJc() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[7], 0);
        }
    }

    /**
     * Gets the "preSp" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure getPreSp() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure)get_store().find_element_user(PROPERTY_QNAME[8], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "preSp" element
     */
    @Override
    public boolean isSetPreSp() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[8]) != 0;
        }
    }

    /**
     * Sets the "preSp" element
     */
    @Override
    public void setPreSp(org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure preSp) {
        generatedSetterHelperImpl(preSp, PROPERTY_QNAME[8], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "preSp" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure addNewPreSp() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure)get_store().add_element_user(PROPERTY_QNAME[8]);
            return target;
        }
    }

    /**
     * Unsets the "preSp" element
     */
    @Override
    public void unsetPreSp() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[8], 0);
        }
    }

    /**
     * Gets the "postSp" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure getPostSp() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure)get_store().find_element_user(PROPERTY_QNAME[9], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "postSp" element
     */
    @Override
    public boolean isSetPostSp() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[9]) != 0;
        }
    }

    /**
     * Sets the "postSp" element
     */
    @Override
    public void setPostSp(org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure postSp) {
        generatedSetterHelperImpl(postSp, PROPERTY_QNAME[9], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "postSp" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure addNewPostSp() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure)get_store().add_element_user(PROPERTY_QNAME[9]);
            return target;
        }
    }

    /**
     * Unsets the "postSp" element
     */
    @Override
    public void unsetPostSp() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[9], 0);
        }
    }

    /**
     * Gets the "interSp" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure getInterSp() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure)get_store().find_element_user(PROPERTY_QNAME[10], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "interSp" element
     */
    @Override
    public boolean isSetInterSp() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[10]) != 0;
        }
    }

    /**
     * Sets the "interSp" element
     */
    @Override
    public void setInterSp(org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure interSp) {
        generatedSetterHelperImpl(interSp, PROPERTY_QNAME[10], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "interSp" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure addNewInterSp() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure)get_store().add_element_user(PROPERTY_QNAME[10]);
            return target;
        }
    }

    /**
     * Unsets the "interSp" element
     */
    @Override
    public void unsetInterSp() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[10], 0);
        }
    }

    /**
     * Gets the "intraSp" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure getIntraSp() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure)get_store().find_element_user(PROPERTY_QNAME[11], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "intraSp" element
     */
    @Override
    public boolean isSetIntraSp() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[11]) != 0;
        }
    }

    /**
     * Sets the "intraSp" element
     */
    @Override
    public void setIntraSp(org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure intraSp) {
        generatedSetterHelperImpl(intraSp, PROPERTY_QNAME[11], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "intraSp" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure addNewIntraSp() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure)get_store().add_element_user(PROPERTY_QNAME[11]);
            return target;
        }
    }

    /**
     * Unsets the "intraSp" element
     */
    @Override
    public void unsetIntraSp() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[11], 0);
        }
    }

    /**
     * Gets the "wrapIndent" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure getWrapIndent() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure)get_store().find_element_user(PROPERTY_QNAME[12], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "wrapIndent" element
     */
    @Override
    public boolean isSetWrapIndent() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[12]) != 0;
        }
    }

    /**
     * Sets the "wrapIndent" element
     */
    @Override
    public void setWrapIndent(org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure wrapIndent) {
        generatedSetterHelperImpl(wrapIndent, PROPERTY_QNAME[12], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "wrapIndent" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure addNewWrapIndent() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure)get_store().add_element_user(PROPERTY_QNAME[12]);
            return target;
        }
    }

    /**
     * Unsets the "wrapIndent" element
     */
    @Override
    public void unsetWrapIndent() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[12], 0);
        }
    }

    /**
     * Gets the "wrapRight" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getWrapRight() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().find_element_user(PROPERTY_QNAME[13], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "wrapRight" element
     */
    @Override
    public boolean isSetWrapRight() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[13]) != 0;
        }
    }

    /**
     * Sets the "wrapRight" element
     */
    @Override
    public void setWrapRight(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff wrapRight) {
        generatedSetterHelperImpl(wrapRight, PROPERTY_QNAME[13], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "wrapRight" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewWrapRight() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().add_element_user(PROPERTY_QNAME[13]);
            return target;
        }
    }

    /**
     * Unsets the "wrapRight" element
     */
    @Override
    public void unsetWrapRight() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[13], 0);
        }
    }

    /**
     * Gets the "intLim" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc getIntLim() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc)get_store().find_element_user(PROPERTY_QNAME[14], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "intLim" element
     */
    @Override
    public boolean isSetIntLim() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[14]) != 0;
        }
    }

    /**
     * Sets the "intLim" element
     */
    @Override
    public void setIntLim(org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc intLim) {
        generatedSetterHelperImpl(intLim, PROPERTY_QNAME[14], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "intLim" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc addNewIntLim() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc)get_store().add_element_user(PROPERTY_QNAME[14]);
            return target;
        }
    }

    /**
     * Unsets the "intLim" element
     */
    @Override
    public void unsetIntLim() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[14], 0);
        }
    }

    /**
     * Gets the "naryLim" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc getNaryLim() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc)get_store().find_element_user(PROPERTY_QNAME[15], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "naryLim" element
     */
    @Override
    public boolean isSetNaryLim() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[15]) != 0;
        }
    }

    /**
     * Sets the "naryLim" element
     */
    @Override
    public void setNaryLim(org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc naryLim) {
        generatedSetterHelperImpl(naryLim, PROPERTY_QNAME[15], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "naryLim" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc addNewNaryLim() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc)get_store().add_element_user(PROPERTY_QNAME[15]);
            return target;
        }
    }

    /**
     * Unsets the "naryLim" element
     */
    @Override
    public void unsetNaryLim() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[15], 0);
        }
    }
}
