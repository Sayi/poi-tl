/*
 * XML Type:  CT_OMathParaPr
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathParaPr
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_OMathParaPr(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTOMathParaPr extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathParaPr> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctomathparapr8d43type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "jc" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc getJc();

    /**
     * True if has "jc" element
     */
    boolean isSetJc();

    /**
     * Sets the "jc" element
     */
    void setJc(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc jc);

    /**
     * Appends and returns a new empty "jc" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc addNewJc();

    /**
     * Unsets the "jc" element
     */
    void unsetJc();
}
