/*
 * XML Type:  CT_SPre
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTSPre
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_SPre(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTSPre extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTSPre> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctspre2f82type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "sPrePr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTSPrePr getSPrePr();

    /**
     * True if has "sPrePr" element
     */
    boolean isSetSPrePr();

    /**
     * Sets the "sPrePr" element
     */
    void setSPrePr(org.openxmlformats.schemas.officeDocument.x2006.math.CTSPrePr sPrePr);

    /**
     * Appends and returns a new empty "sPrePr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTSPrePr addNewSPrePr();

    /**
     * Unsets the "sPrePr" element
     */
    void unsetSPrePr();

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
