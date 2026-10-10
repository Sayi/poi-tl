/*
 * XML Type:  CT_SSup
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTSSup
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.XmlObject;

/**
 * An XML CT_SSup(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public class CTSSupImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.openxmlformats.schemas.officeDocument.x2006.math.CTSSup {
    private static final long serialVersionUID = 1L;

    public CTSSupImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    private static final QName[] PROPERTY_QNAME = {
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "sSupPr"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "e"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "sup"),
    };


    /**
     * Gets the "sSupPr" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTSSupPr getSSupPr() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTSSupPr target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTSSupPr)get_store().find_element_user(PROPERTY_QNAME[0], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "sSupPr" element
     */
    @Override
    public boolean isSetSSupPr() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[0]) != 0;
        }
    }

    /**
     * Sets the "sSupPr" element
     */
    @Override
    public void setSSupPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTSSupPr sSupPr) {
        generatedSetterHelperImpl(sSupPr, PROPERTY_QNAME[0], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "sSupPr" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTSSupPr addNewSSupPr() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTSSupPr target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTSSupPr)get_store().add_element_user(PROPERTY_QNAME[0]);
            return target;
        }
    }

    /**
     * Unsets the "sSupPr" element
     */
    @Override
    public void unsetSSupPr() {
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
     * Gets the "sup" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg getSup() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg)get_store().find_element_user(PROPERTY_QNAME[2], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * Sets the "sup" element
     */
    @Override
    public void setSup(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg sup) {
        generatedSetterHelperImpl(sup, PROPERTY_QNAME[2], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "sup" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg addNewSup() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg)get_store().add_element_user(PROPERTY_QNAME[2]);
            return target;
        }
    }
}
