/*
 * XML Type:  CT_LimLow
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLow
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_LimLow(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTLimLow extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLow> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctlimlowd6cetype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "limLowPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLowPr getLimLowPr();

    /**
     * True if has "limLowPr" element
     */
    boolean isSetLimLowPr();

    /**
     * Sets the "limLowPr" element
     */
    void setLimLowPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLowPr limLowPr);

    /**
     * Appends and returns a new empty "limLowPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLowPr addNewLimLowPr();

    /**
     * Unsets the "limLowPr" element
     */
    void unsetLimLowPr();

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
     * Gets the "lim" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg getLim();

    /**
     * Sets the "lim" element
     */
    void setLim(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg lim);

    /**
     * Appends and returns a new empty "lim" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg addNewLim();
}
