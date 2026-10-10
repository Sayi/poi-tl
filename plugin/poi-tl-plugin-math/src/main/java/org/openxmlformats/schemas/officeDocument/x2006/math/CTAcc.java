/*
 * XML Type:  CT_Acc
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTAcc
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_Acc(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTAcc extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTAcc> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctaccd5bbtype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "accPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTAccPr getAccPr();

    /**
     * True if has "accPr" element
     */
    boolean isSetAccPr();

    /**
     * Sets the "accPr" element
     */
    void setAccPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTAccPr accPr);

    /**
     * Appends and returns a new empty "accPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTAccPr addNewAccPr();

    /**
     * Unsets the "accPr" element
     */
    void unsetAccPr();

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
