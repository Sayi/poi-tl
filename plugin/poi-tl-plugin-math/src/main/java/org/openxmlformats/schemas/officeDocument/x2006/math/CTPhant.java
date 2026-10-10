/*
 * XML Type:  CT_Phant
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTPhant
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_Phant(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTPhant extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTPhant> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctphant234dtype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "phantPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTPhantPr getPhantPr();

    /**
     * True if has "phantPr" element
     */
    boolean isSetPhantPr();

    /**
     * Sets the "phantPr" element
     */
    void setPhantPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTPhantPr phantPr);

    /**
     * Appends and returns a new empty "phantPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTPhantPr addNewPhantPr();

    /**
     * Unsets the "phantPr" element
     */
    void unsetPhantPr();

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
