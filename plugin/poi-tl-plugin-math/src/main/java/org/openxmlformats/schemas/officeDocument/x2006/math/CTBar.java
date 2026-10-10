/*
 * XML Type:  CT_Bar
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTBar
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_Bar(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTBar extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTBar> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctbareee9type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "barPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTBarPr getBarPr();

    /**
     * True if has "barPr" element
     */
    boolean isSetBarPr();

    /**
     * Sets the "barPr" element
     */
    void setBarPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTBarPr barPr);

    /**
     * Appends and returns a new empty "barPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTBarPr addNewBarPr();

    /**
     * Unsets the "barPr" element
     */
    void unsetBarPr();

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
