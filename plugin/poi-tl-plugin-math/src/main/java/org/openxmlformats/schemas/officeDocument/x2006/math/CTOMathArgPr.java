/*
 * XML Type:  CT_OMathArgPr
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArgPr
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_OMathArgPr(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTOMathArgPr extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArgPr> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctomathargprccb1type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "argSz" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTInteger2 getArgSz();

    /**
     * True if has "argSz" element
     */
    boolean isSetArgSz();

    /**
     * Sets the "argSz" element
     */
    void setArgSz(org.openxmlformats.schemas.officeDocument.x2006.math.CTInteger2 argSz);

    /**
     * Appends and returns a new empty "argSz" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTInteger2 addNewArgSz();

    /**
     * Unsets the "argSz" element
     */
    void unsetArgSz();
}
