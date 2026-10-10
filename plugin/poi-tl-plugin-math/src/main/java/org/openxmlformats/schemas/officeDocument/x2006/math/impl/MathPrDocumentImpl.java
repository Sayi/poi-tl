/*
 * An XML document type.
 * Localname: mathPr
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.MathPrDocument
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.XmlObject;

/**
 * A document containing one mathPr(@http://schemas.openxmlformats.org/officeDocument/2006/math) element.
 *
 * This is a complex type.
 */
public class MathPrDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.openxmlformats.schemas.officeDocument.x2006.math.MathPrDocument {
    private static final long serialVersionUID = 1L;

    public MathPrDocumentImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    private static final QName[] PROPERTY_QNAME = {
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "mathPr"),
    };


    /**
     * Gets the "mathPr" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTMathPr getMathPr() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTMathPr target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTMathPr)get_store().find_element_user(PROPERTY_QNAME[0], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * Sets the "mathPr" element
     */
    @Override
    public void setMathPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTMathPr mathPr) {
        generatedSetterHelperImpl(mathPr, PROPERTY_QNAME[0], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "mathPr" element
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.CTMathPr addNewMathPr() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.CTMathPr target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.CTMathPr)get_store().add_element_user(PROPERTY_QNAME[0]);
            return target;
        }
    }
}
