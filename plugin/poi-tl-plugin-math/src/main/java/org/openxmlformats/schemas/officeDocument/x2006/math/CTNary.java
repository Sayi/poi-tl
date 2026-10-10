/*
 * XML Type:  CT_Nary
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTNary
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_Nary(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTNary extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTNary> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctnarye738type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "naryPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTNaryPr getNaryPr();

    /**
     * True if has "naryPr" element
     */
    boolean isSetNaryPr();

    /**
     * Sets the "naryPr" element
     */
    void setNaryPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTNaryPr naryPr);

    /**
     * Appends and returns a new empty "naryPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTNaryPr addNewNaryPr();

    /**
     * Unsets the "naryPr" element
     */
    void unsetNaryPr();

    /**
     * Gets the "sub" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg getSub();

    /**
     * Sets the "sub" element
     */
    void setSub(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg sub);

    /**
     * Appends and returns a new empty "sub" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg addNewSub();

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
