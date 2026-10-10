/*
 * XML Type:  CT_F
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTF
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_F(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTF extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTF> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctfaff6type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "fPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTFPr getFPr();

    /**
     * True if has "fPr" element
     */
    boolean isSetFPr();

    /**
     * Sets the "fPr" element
     */
    void setFPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTFPr fPr);

    /**
     * Appends and returns a new empty "fPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTFPr addNewFPr();

    /**
     * Unsets the "fPr" element
     */
    void unsetFPr();

    /**
     * Gets the "num" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg getNum();

    /**
     * Sets the "num" element
     */
    void setNum(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg num);

    /**
     * Appends and returns a new empty "num" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg addNewNum();

    /**
     * Gets the "den" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg getDen();

    /**
     * Sets the "den" element
     */
    void setDen(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg den);

    /**
     * Appends and returns a new empty "den" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg addNewDen();
}
