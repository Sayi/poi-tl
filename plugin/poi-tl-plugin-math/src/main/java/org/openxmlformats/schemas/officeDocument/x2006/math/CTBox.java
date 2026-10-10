/*
 * XML Type:  CT_Box
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTBox
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_Box(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTBox extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTBox> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctbox7a31type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "boxPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTBoxPr getBoxPr();

    /**
     * True if has "boxPr" element
     */
    boolean isSetBoxPr();

    /**
     * Sets the "boxPr" element
     */
    void setBoxPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTBoxPr boxPr);

    /**
     * Appends and returns a new empty "boxPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTBoxPr addNewBoxPr();

    /**
     * Unsets the "boxPr" element
     */
    void unsetBoxPr();

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
