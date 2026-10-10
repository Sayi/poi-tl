/*
 * XML Type:  CT_LimLow
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLow
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.XmlObject;

/**
 * An XML CT_LimLow(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public class CTLimLowImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLow {
    private static final long serialVersionUID = 1L;

    public CTLimLowImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    private static final QName[] PROPERTY_QNAME = {
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "limLowPr"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "e"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "lim"),
    };


    /**
     * Gets the "limLowPr" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLowPr getLimLowPr() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLowPr target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLowPr)get_store().find_element_user(PROPERTY_QNAME[0], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "limLowPr" element
     */
    @Override
    public boolean isSetLimLowPr() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[0]) != 0;
        }
    }

    /**
     * Sets the "limLowPr" element
     */
    @Override
    public void setLimLowPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLowPr limLowPr) {
        generatedSetterHelperImpl(limLowPr, PROPERTY_QNAME[0], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "limLowPr" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLowPr addNewLimLowPr() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLowPr target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLowPr)get_store().add_element_user(PROPERTY_QNAME[0]);
            return target;
        }
    }

    /**
     * Unsets the "limLowPr" element
     */
    @Override
    public void unsetLimLowPr() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[0], 0);
        }
    }

    /**
     * Gets the "e" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg getE() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg)get_store().find_element_user(PROPERTY_QNAME[1], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * Sets the "e" element
     */
    @Override
    public void setE(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg e) {
        generatedSetterHelperImpl(e, PROPERTY_QNAME[1], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "e" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg addNewE() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg)get_store().add_element_user(PROPERTY_QNAME[1]);
            return target;
        }
    }

    /**
     * Gets the "lim" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg getLim() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg)get_store().find_element_user(PROPERTY_QNAME[2], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * Sets the "lim" element
     */
    @Override
    public void setLim(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg lim) {
        generatedSetterHelperImpl(lim, PROPERTY_QNAME[2], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "lim" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg addNewLim() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg)get_store().add_element_user(PROPERTY_QNAME[2]);
            return target;
        }
    }
}
