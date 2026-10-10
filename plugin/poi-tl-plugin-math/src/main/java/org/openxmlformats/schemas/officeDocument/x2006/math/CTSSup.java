/*
 * XML Type:  CT_SSup
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTSSup
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_SSup(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTSSup extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTSSup> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctssup1cf7type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "sSupPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTSSupPr getSSupPr();

    /**
     * True if has "sSupPr" element
     */
    boolean isSetSSupPr();

    /**
     * Sets the "sSupPr" element
     */
    void setSSupPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTSSupPr sSupPr);

    /**
     * Appends and returns a new empty "sSupPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTSSupPr addNewSSupPr();

    /**
     * Unsets the "sSupPr" element
     */
    void unsetSSupPr();

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

    /**
     * Gets the "sup" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg getSup();

    /**
     * Sets the "sup" element
     */
    void setSup(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg sup);

    /**
     * Appends and returns a new empty "sup" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg addNewSup();
}
