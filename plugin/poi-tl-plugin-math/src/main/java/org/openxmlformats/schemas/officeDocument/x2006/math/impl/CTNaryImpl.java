/*
 * XML Type:  CT_Nary
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTNary
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.XmlObject;

/**
 * An XML CT_Nary(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public class CTNaryImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.openxmlformats.schemas.officeDocument.x2006.math.CTNary {
    private static final long serialVersionUID = 1L;

    public CTNaryImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    private static final QName[] PROPERTY_QNAME = {
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "naryPr"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "sub"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "sup"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "e"),
    };


    /**
     * Gets the "naryPr" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTNaryPr getNaryPr() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTNaryPr target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTNaryPr)get_store().find_element_user(PROPERTY_QNAME[0], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "naryPr" element
     */
    @Override
    public boolean isSetNaryPr() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[0]) != 0;
        }
    }

    /**
     * Sets the "naryPr" element
     */
    @Override
    public void setNaryPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTNaryPr naryPr) {
        generatedSetterHelperImpl(naryPr, PROPERTY_QNAME[0], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "naryPr" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTNaryPr addNewNaryPr() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTNaryPr target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTNaryPr)get_store().add_element_user(PROPERTY_QNAME[0]);
            return target;
        }
    }

    /**
     * Unsets the "naryPr" element
     */
    @Override
    public void unsetNaryPr() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[0], 0);
        }
    }

    /**
     * Gets the "sub" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg getSub() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg)get_store().find_element_user(PROPERTY_QNAME[1], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * Sets the "sub" element
     */
    @Override
    public void setSub(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg sub) {
        generatedSetterHelperImpl(sub, PROPERTY_QNAME[1], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "sub" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg addNewSub() {
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

    /**
     * Gets the "e" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg getE() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg)get_store().find_element_user(PROPERTY_QNAME[3], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * Sets the "e" element
     */
    @Override
    public void setE(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg e) {
        generatedSetterHelperImpl(e, PROPERTY_QNAME[3], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "e" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg addNewE() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg)get_store().add_element_user(PROPERTY_QNAME[3]);
            return target;
        }
    }
}
