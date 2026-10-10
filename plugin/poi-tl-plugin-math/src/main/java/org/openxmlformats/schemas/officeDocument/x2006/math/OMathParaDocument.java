/*
 * An XML document type.
 * Localname: oMathPara
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.OMathParaDocument
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * A document containing one oMathPara(@http://schemas.openxmlformats.org/officeDocument/2006/math) element.
 *
 * This is a complex type.
 */
public interface OMathParaDocument extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.OMathParaDocument> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "omathpara2b79doctype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "oMathPara" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathPara getOMathPara();

    /**
     * Sets the "oMathPara" element
     */
    void setOMathPara(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathPara oMathPara);

    /**
     * Appends and returns a new empty "oMathPara" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathPara addNewOMathPara();
}
