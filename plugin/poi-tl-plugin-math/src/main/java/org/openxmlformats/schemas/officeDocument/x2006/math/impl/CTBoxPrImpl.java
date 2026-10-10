/*
 * XML Type:  CT_BoxPr
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTBoxPr
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.XmlObject;

/**
 * An XML CT_BoxPr(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public class CTBoxPrImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.openxmlformats.schemas.officeDocument.x2006.math.CTBoxPr {
    private static final long serialVersionUID = 1L;

    public CTBoxPrImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    private static final QName[] PROPERTY_QNAME = {
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "opEmu"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "noBreak"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "diff"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "brk"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "aln"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "ctrlPr"),
    };


    /**
     * Gets the "opEmu" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getOpEmu() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().find_element_user(PROPERTY_QNAME[0], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "opEmu" element
     */
    @Override
    public boolean isSetOpEmu() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[0]) != 0;
        }
    }

    /**
     * Sets the "opEmu" element
     */
    @Override
    public void setOpEmu(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff opEmu) {
        generatedSetterHelperImpl(opEmu, PROPERTY_QNAME[0], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "opEmu" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewOpEmu() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().add_element_user(PROPERTY_QNAME[0]);
            return target;
        }
    }

    /**
     * Unsets the "opEmu" element
     */
    @Override
    public void unsetOpEmu() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[0], 0);
        }
    }

    /**
     * Gets the "noBreak" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getNoBreak() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().find_element_user(PROPERTY_QNAME[1], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "noBreak" element
     */
    @Override
    public boolean isSetNoBreak() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[1]) != 0;
        }
    }

    /**
     * Sets the "noBreak" element
     */
    @Override
    public void setNoBreak(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff noBreak) {
        generatedSetterHelperImpl(noBreak, PROPERTY_QNAME[1], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "noBreak" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewNoBreak() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().add_element_user(PROPERTY_QNAME[1]);
            return target;
        }
    }

    /**
     * Unsets the "noBreak" element
     */
    @Override
    public void unsetNoBreak() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[1], 0);
        }
    }

    /**
     * Gets the "diff" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getDiff() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().find_element_user(PROPERTY_QNAME[2], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "diff" element
     */
    @Override
    public boolean isSetDiff() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[2]) != 0;
        }
    }

    /**
     * Sets the "diff" element
     */
    @Override
    public void setDiff(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff diff) {
        generatedSetterHelperImpl(diff, PROPERTY_QNAME[2], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "diff" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewDiff() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().add_element_user(PROPERTY_QNAME[2]);
            return target;
        }
    }

    /**
     * Unsets the "diff" element
     */
    @Override
    public void unsetDiff() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[2], 0);
        }
    }

    /**
     * Gets the "brk" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTManualBreak getBrk() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTManualBreak target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTManualBreak)get_store().find_element_user(PROPERTY_QNAME[3], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "brk" element
     */
    @Override
    public boolean isSetBrk() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[3]) != 0;
        }
    }

    /**
     * Sets the "brk" element
     */
    @Override
    public void setBrk(org.openxmlformats.schemas.officeDocument.x2006.math.CTManualBreak brk) {
        generatedSetterHelperImpl(brk, PROPERTY_QNAME[3], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "brk" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTManualBreak addNewBrk() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTManualBreak target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTManualBreak)get_store().add_element_user(PROPERTY_QNAME[3]);
            return target;
        }
    }

    /**
     * Unsets the "brk" element
     */
    @Override
    public void unsetBrk() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[3], 0);
        }
    }

    /**
     * Gets the "aln" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getAln() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().find_element_user(PROPERTY_QNAME[4], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "aln" element
     */
    @Override
    public boolean isSetAln() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[4]) != 0;
        }
    }

    /**
     * Sets the "aln" element
     */
    @Override
    public void setAln(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff aln) {
        generatedSetterHelperImpl(aln, PROPERTY_QNAME[4], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "aln" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewAln() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().add_element_user(PROPERTY_QNAME[4]);
            return target;
        }
    }

    /**
     * Unsets the "aln" element
     */
    @Override
    public void unsetAln() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[4], 0);
        }
    }

    /**
     * Gets the "ctrlPr" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTCtrlPr getCtrlPr() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTCtrlPr target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTCtrlPr)get_store().find_element_user(PROPERTY_QNAME[5], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "ctrlPr" element
     */
    @Override
    public boolean isSetCtrlPr() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[5]) != 0;
        }
    }

    /**
     * Sets the "ctrlPr" element
     */
    @Override
    public void setCtrlPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTCtrlPr ctrlPr) {
        generatedSetterHelperImpl(ctrlPr, PROPERTY_QNAME[5], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "ctrlPr" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTCtrlPr addNewCtrlPr() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTCtrlPr target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTCtrlPr)get_store().add_element_user(PROPERTY_QNAME[5]);
            return target;
        }
    }

    /**
     * Unsets the "ctrlPr" element
     */
    @Override
    public void unsetCtrlPr() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[5], 0);
        }
    }
}
