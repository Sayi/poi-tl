/*
 * XML Type:  CT_BorderBox
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTBorderBox
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_BorderBox(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTBorderBox extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTBorderBox> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctborderbox15bdtype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "borderBoxPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTBorderBoxPr getBorderBoxPr();

    /**
     * True if has "borderBoxPr" element
     */
    boolean isSetBorderBoxPr();

    /**
     * Sets the "borderBoxPr" element
     */
    void setBorderBoxPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTBorderBoxPr borderBoxPr);

    /**
     * Appends and returns a new empty "borderBoxPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTBorderBoxPr addNewBorderBoxPr();

    /**
     * Unsets the "borderBoxPr" element
     */
    void unsetBorderBoxPr();

    /**
     * Gets the "e" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg getE();

    /**
     * Sets the "e" element
     */
    void setE(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg e);

    /**
     * Appends and returns a new empty "e" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg addNewE();
}
