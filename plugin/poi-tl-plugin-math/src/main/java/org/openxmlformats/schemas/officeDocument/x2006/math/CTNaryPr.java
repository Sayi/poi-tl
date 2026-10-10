/*
 * XML Type:  CT_NaryPr
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTNaryPr
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_NaryPr(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTNaryPr extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTNaryPr> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctnarypr7396type");
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
     * Gets the "limLoc" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc getLimLoc();

    /**
     * True if has "limLoc" element
     */
    boolean isSetLimLoc();

    /**
     * Sets the "limLoc" element
     */
    void setLimLoc(org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc limLoc);

    /**
     * Appends and returns a new empty "limLoc" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc addNewLimLoc();

    /**
     * Unsets the "limLoc" element
     */
    void unsetLimLoc();

    /**
     * Gets the "grow" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getGrow();

    /**
     * True if has "grow" element
     */
    boolean isSetGrow();

    /**
     * Sets the "grow" element
     */
    void setGrow(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff grow);

    /**
     * Appends and returns a new empty "grow" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewGrow();

    /**
     * Unsets the "grow" element
     */
    void unsetGrow();

    /**
     * Gets the "subHide" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getSubHide();

    /**
     * True if has "subHide" element
     */
    boolean isSetSubHide();

    /**
     * Sets the "subHide" element
     */
    void setSubHide(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff subHide);

    /**
     * Appends and returns a new empty "subHide" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewSubHide();

    /**
     * Unsets the "subHide" element
     */
    void unsetSubHide();

    /**
     * Gets the "supHide" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getSupHide();

    /**
     * True if has "supHide" element
     */
    boolean isSetSupHide();

    /**
     * Sets the "supHide" element
     */
    void setSupHide(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff supHide);

    /**
     * Appends and returns a new empty "supHide" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewSupHide();

    /**
     * Unsets the "supHide" element
     */
    void unsetSupHide();

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
