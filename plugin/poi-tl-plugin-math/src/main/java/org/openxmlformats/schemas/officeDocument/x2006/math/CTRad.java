/*
 * XML Type:  CT_Rad
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTRad
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_Rad(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTRad extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTRad> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctradc9a7type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "radPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTRadPr getRadPr();

    /**
     * True if has "radPr" element
     */
    boolean isSetRadPr();

    /**
     * Sets the "radPr" element
     */
    void setRadPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTRadPr radPr);

    /**
     * Appends and returns a new empty "radPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTRadPr addNewRadPr();

    /**
     * Unsets the "radPr" element
     */
    void unsetRadPr();

    /**
     * Gets the "deg" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg getDeg();

    /**
     * Sets the "deg" element
     */
    void setDeg(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg deg);

    /**
     * Appends and returns a new empty "deg" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg addNewDeg();

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
