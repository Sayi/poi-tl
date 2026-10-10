/*
 * XML Type:  CT_BarPr
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTBarPr
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_BarPr(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTBarPr extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTBarPr> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctbarpr5307type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "pos" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTopBot getPos();

    /**
     * True if has "pos" element
     */
    boolean isSetPos();

    /**
     * Sets the "pos" element
     */
    void setPos(org.openxmlformats.schemas.officeDocument.x2006.math.CTTopBot pos);

    /**
     * Appends and returns a new empty "pos" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTopBot addNewPos();

    /**
     * Unsets the "pos" element
     */
    void unsetPos();

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
