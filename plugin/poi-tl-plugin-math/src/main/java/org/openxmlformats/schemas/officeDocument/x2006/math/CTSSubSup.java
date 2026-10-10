/*
 * XML Type:  CT_SSubSup
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTSSubSup
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_SSubSup(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTSSubSup extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTSSubSup> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctssubsup117btype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "sSubSupPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTSSubSupPr getSSubSupPr();

    /**
     * True if has "sSubSupPr" element
     */
    boolean isSetSSubSupPr();

    /**
     * Sets the "sSubSupPr" element
     */
    void setSSubSupPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTSSubSupPr sSubSupPr);

    /**
     * Appends and returns a new empty "sSubSupPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTSSubSupPr addNewSSubSupPr();

    /**
     * Unsets the "sSubSupPr" element
     */
    void unsetSSubSupPr();

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
}
