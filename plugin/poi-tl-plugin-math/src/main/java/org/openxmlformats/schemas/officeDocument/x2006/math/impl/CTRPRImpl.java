/*
 * XML Type:  CT_RPR
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTRPR
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.XmlObject;

/**
 * An XML CT_RPR(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public class CTRPRImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.openxmlformats.schemas.officeDocument.x2006.math.CTRPR {
    private static final long serialVersionUID = 1L;

    public CTRPRImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    private static final QName[] PROPERTY_QNAME = {
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "lit"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "nor"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "scr"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "sty"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "brk"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "aln"),
    };


    /**
     * Gets the "lit" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getLit() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().find_element_user(PROPERTY_QNAME[0], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "lit" element
     */
    @Override
    public boolean isSetLit() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[0]) != 0;
        }
    }

    /**
     * Sets the "lit" element
     */
    @Override
    public void setLit(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff lit) {
        generatedSetterHelperImpl(lit, PROPERTY_QNAME[0], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "lit" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewLit() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().add_element_user(PROPERTY_QNAME[0]);
            return target;
        }
    }

    /**
     * Unsets the "lit" element
     */
    @Override
    public void unsetLit() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[0], 0);
        }
    }

    /**
     * Gets the "nor" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getNor() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().find_element_user(PROPERTY_QNAME[1], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "nor" element
     */
    @Override
    public boolean isSetNor() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[1]) != 0;
        }
    }

    /**
     * Sets the "nor" element
     */
    @Override
    public void setNor(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff nor) {
        generatedSetterHelperImpl(nor, PROPERTY_QNAME[1], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "nor" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewNor() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().add_element_user(PROPERTY_QNAME[1]);
            return target;
        }
    }

    /**
     * Unsets the "nor" element
     */
    @Override
    public void unsetNor() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[1], 0);
        }
    }

    /**
     * Gets the "scr" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTScript getScr() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTScript target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTScript)get_store().find_element_user(PROPERTY_QNAME[2], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "scr" element
     */
    @Override
    public boolean isSetScr() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[2]) != 0;
        }
    }

    /**
     * Sets the "scr" element
     */
    @Override
    public void setScr(org.openxmlformats.schemas.officeDocument.x2006.math.CTScript scr) {
        generatedSetterHelperImpl(scr, PROPERTY_QNAME[2], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "scr" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTScript addNewScr() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTScript target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTScript)get_store().add_element_user(PROPERTY_QNAME[2]);
            return target;
        }
    }

    /**
     * Unsets the "scr" element
     */
    @Override
    public void unsetScr() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[2], 0);
        }
    }

    /**
     * Gets the "sty" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTStyle getSty() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTStyle target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTStyle)get_store().find_element_user(PROPERTY_QNAME[3], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "sty" element
     */
    @Override
    public boolean isSetSty() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[3]) != 0;
        }
    }

    /**
     * Sets the "sty" element
     */
    @Override
    public void setSty(org.openxmlformats.schemas.officeDocument.x2006.math.CTStyle sty) {
        generatedSetterHelperImpl(sty, PROPERTY_QNAME[3], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "sty" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTStyle addNewSty() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTStyle target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTStyle)get_store().add_element_user(PROPERTY_QNAME[3]);
            return target;
        }
    }

    /**
     * Unsets the "sty" element
     */
    @Override
    public void unsetSty() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[3], 0);
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
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTManualBreak)get_store().find_element_user(PROPERTY_QNAME[4], 0);
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
            return get_store().count_elements(PROPERTY_QNAME[4]) != 0;
        }
    }

    /**
     * Sets the "brk" element
     */
    @Override
    public void setBrk(org.openxmlformats.schemas.officeDocument.x2006.math.CTManualBreak brk) {
        generatedSetterHelperImpl(brk, PROPERTY_QNAME[4], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "brk" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTManualBreak addNewBrk() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTManualBreak target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTManualBreak)get_store().add_element_user(PROPERTY_QNAME[4]);
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
            get_store().remove_element(PROPERTY_QNAME[4], 0);
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
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().find_element_user(PROPERTY_QNAME[5], 0);
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
            return get_store().count_elements(PROPERTY_QNAME[5]) != 0;
        }
    }

    /**
     * Sets the "aln" element
     */
    @Override
    public void setAln(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff aln) {
        generatedSetterHelperImpl(aln, PROPERTY_QNAME[5], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "aln" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewAln() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff)get_store().add_element_user(PROPERTY_QNAME[5]);
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
            get_store().remove_element(PROPERTY_QNAME[5], 0);
        }
    }
}
