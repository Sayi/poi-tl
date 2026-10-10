/*
 * XML Type:  CT_SSubSupPr
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTSSubSupPr
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_SSubSupPr(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTSSubSupPr extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTSSubSupPr> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctssubsuppr1919type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "alnScr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getAlnScr();

    /**
     * True if has "alnScr" element
     */
    boolean isSetAlnScr();

    /**
     * Sets the "alnScr" element
     */
    void setAlnScr(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff alnScr);

    /**
     * Appends and returns a new empty "alnScr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewAlnScr();

    /**
     * Unsets the "alnScr" element
     */
    void unsetAlnScr();

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
