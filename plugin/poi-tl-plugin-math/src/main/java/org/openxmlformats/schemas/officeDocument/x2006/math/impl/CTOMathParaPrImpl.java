/*
 * XML Type:  CT_OMathParaPr
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathParaPr
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.XmlObject;

/**
 * An XML CT_OMathParaPr(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public class CTOMathParaPrImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathParaPr {
    private static final long serialVersionUID = 1L;

    public CTOMathParaPrImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    private static final QName[] PROPERTY_QNAME = {
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "jc"),
    };


    /**
     * Gets the "jc" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc getJc() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc)get_store().find_element_user(PROPERTY_QNAME[0], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "jc" element
     */
    @Override
    public boolean isSetJc() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[0]) != 0;
        }
    }

    /**
     * Sets the "jc" element
     */
    @Override
    public void setJc(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc jc) {
        generatedSetterHelperImpl(jc, PROPERTY_QNAME[0], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "jc" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc addNewJc() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc)get_store().add_element_user(PROPERTY_QNAME[0]);
            return target;
        }
    }

    /**
     * Unsets the "jc" element
     */
    @Override
    public void unsetJc() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[0], 0);
        }
    }
}
