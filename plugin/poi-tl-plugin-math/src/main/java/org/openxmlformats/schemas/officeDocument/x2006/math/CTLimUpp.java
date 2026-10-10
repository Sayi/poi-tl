/*
 * XML Type:  CT_LimUpp
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTLimUpp
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_LimUpp(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTLimUpp extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTLimUpp> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctlimuppd44dtype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "limUppPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTLimUppPr getLimUppPr();

    /**
     * True if has "limUppPr" element
     */
    boolean isSetLimUppPr();

    /**
     * Sets the "limUppPr" element
     */
    void setLimUppPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTLimUppPr limUppPr);

    /**
     * Appends and returns a new empty "limUppPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTLimUppPr addNewLimUppPr();

    /**
     * Unsets the "limUppPr" element
     */
    void unsetLimUppPr();

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
