/*
 * An XML document type.
 * Localname: mathPr
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.MathPrDocument
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * A document containing one mathPr(@http://schemas.openxmlformats.org/officeDocument/2006/math) element.
 *
 * This is a complex type.
 */
public interface MathPrDocument extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.MathPrDocument> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "mathprd7b4doctype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "mathPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTMathPr getMathPr();

    /**
     * Sets the "mathPr" element
     */
    void setMathPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTMathPr mathPr);

    /**
     * Appends and returns a new empty "mathPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTMathPr addNewMathPr();
}
