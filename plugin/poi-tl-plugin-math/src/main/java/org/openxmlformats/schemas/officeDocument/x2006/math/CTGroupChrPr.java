/*
 * XML Type:  CT_GroupChrPr
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTGroupChrPr
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_GroupChrPr(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTGroupChrPr extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTGroupChrPr> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctgroupchrpr8022type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "chr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTChar getChr();

    /**
     * True if has "chr" element
     */
    boolean isSetChr();

    /**
     * Sets the "chr" element
     */
    void setChr(org.openxmlformats.schemas.officeDocument.x2006.math.CTChar chr);

    /**
     * Appends and returns a new empty "chr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTChar addNewChr();

    /**
     * Unsets the "chr" element
     */
    void unsetChr();

    /**
     * Gets the "pos" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTopBot getPos();

    /**
     * True if has "pos" element
     */
    boolean isSetPos();

    /**
     * Sets the "pos" element
     */
    void setPos(org.openxmlformats.schemas.officeDocument.x2006.math.CTTopBot pos);

    /**
     * Appends and returns a new empty "pos" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTopBot addNewPos();

    /**
     * Unsets the "pos" element
     */
    void unsetPos();

    /**
     * Gets the "vertJc" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTopBot getVertJc();

    /**
     * True if has "vertJc" element
     */
    boolean isSetVertJc();

    /**
     * Sets the "vertJc" element
     */
    void setVertJc(org.openxmlformats.schemas.officeDocument.x2006.math.CTTopBot vertJc);

    /**
     * Appends and returns a new empty "vertJc" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTopBot addNewVertJc();

    /**
     * Unsets the "vertJc" element
     */
    void unsetVertJc();

    /**
     * Gets the "ctrlPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTCtrlPr getCtrlPr();

    /**
     * True if has "ctrlPr" element
     */
    boolean isSetCtrlPr();

    /**
     * Sets the "ctrlPr" element
     */
    void setCtrlPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTCtrlPr ctrlPr);

    /**
     * Appends and returns a new empty "ctrlPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTCtrlPr addNewCtrlPr();

    /**
     * Unsets the "ctrlPr" element
     */
    void unsetCtrlPr();
}
