/*
 * An XML document type.
 * Localname: oMath
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.OMathDocument
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * A document containing one oMath(@http://schemas.openxmlformats.org/officeDocument/2006/math) element.
 *
 * This is a complex type.
 */
public interface OMathDocument extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.OMathDocument> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "omathd479doctype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "oMath" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath getOMath();

    /**
     * Sets the "oMath" element
     */
    void setOMath(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath oMath);

    /**
     * Appends and returns a new empty "oMath" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath addNewOMath();
}
