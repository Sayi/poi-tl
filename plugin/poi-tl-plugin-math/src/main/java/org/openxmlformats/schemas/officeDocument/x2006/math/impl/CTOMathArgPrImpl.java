/*
 * XML Type:  CT_OMathArgPr
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArgPr
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.XmlObject;

/**
 * An XML CT_OMathArgPr(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public class CTOMathArgPrImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArgPr {
    private static final long serialVersionUID = 1L;

    public CTOMathArgPrImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    private static final QName[] PROPERTY_QNAME = {
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "argSz"),
    };


    /**
     * Gets the "argSz" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTInteger2 getArgSz() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTInteger2 target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTInteger2)get_store().find_element_user(PROPERTY_QNAME[0], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "argSz" element
     */
    @Override
    public boolean isSetArgSz() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[0]) != 0;
        }
    }

    /**
     * Sets the "argSz" element
     */
    @Override
    public void setArgSz(org.openxmlformats.schemas.officeDocument.x2006.math.CTInteger2 argSz) {
        generatedSetterHelperImpl(argSz, PROPERTY_QNAME[0], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "argSz" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTInteger2 addNewArgSz() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTInteger2 target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTInteger2)get_store().add_element_user(PROPERTY_QNAME[0]);
            return target;
        }
    }

    /**
     * Unsets the "argSz" element
     */
    @Override
    public void unsetArgSz() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[0], 0);
        }
    }
}
