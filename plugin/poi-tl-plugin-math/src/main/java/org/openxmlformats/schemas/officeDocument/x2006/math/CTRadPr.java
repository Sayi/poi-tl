/*
 * XML Type:  CT_RadPr
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTRadPr
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_RadPr(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTRadPr extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTRadPr> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctradpr7645type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "degHide" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getDegHide();

    /**
     * True if has "degHide" element
     */
    boolean isSetDegHide();

    /**
     * Sets the "degHide" element
     */
    void setDegHide(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff degHide);

    /**
     * Appends and returns a new empty "degHide" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewDegHide();

    /**
     * Unsets the "degHide" element
     */
    void unsetDegHide();

    /**
     * Gets the "ctrlPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTCtrlPr getCtrlPr();

    /**
     * True if has "ctrlPr" element
     */
    boolean isSetCtrlPr();

    /**
     * Sets the "ctrlPr" element
     */
    void setCtrlPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTCtrlPr ctrlPr);

    /**
     * Appends and returns a new empty "ctrlPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTCtrlPr addNewCtrlPr();

    /**
     * Unsets the "ctrlPr" element
     */
    void unsetCtrlPr();
}
