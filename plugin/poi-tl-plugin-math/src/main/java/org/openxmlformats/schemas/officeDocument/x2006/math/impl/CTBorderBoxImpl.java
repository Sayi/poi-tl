/*
 * XML Type:  CT_BorderBox
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTBorderBox
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.XmlObject;

/**
 * An XML CT_BorderBox(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public class CTBorderBoxImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.openxmlformats.schemas.officeDocument.x2006.math.CTBorderBox {
    private static final long serialVersionUID = 1L;

    public CTBorderBoxImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    private static final QName[] PROPERTY_QNAME = {
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "borderBoxPr"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "e"),
    };


    /**
     * Gets the "borderBoxPr" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTBorderBoxPr getBorderBoxPr() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTBorderBoxPr target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTBorderBoxPr)get_store().find_element_user(PROPERTY_QNAME[0], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "borderBoxPr" element
     */
    @Override
    public boolean isSetBorderBoxPr() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[0]) != 0;
        }
    }

    /**
     * Sets the "borderBoxPr" element
     */
    @Override
    public void setBorderBoxPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTBorderBoxPr borderBoxPr) {
        generatedSetterHelperImpl(borderBoxPr, PROPERTY_QNAME[0], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "borderBoxPr" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTBorderBoxPr addNewBorderBoxPr() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTBorderBoxPr target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTBorderBoxPr)get_store().add_element_user(PROPERTY_QNAME[0]);
            return target;
        }
    }

    /**
     * Unsets the "borderBoxPr" element
     */
    @Override
    public void unsetBorderBoxPr() {
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
}
