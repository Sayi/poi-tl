/*
 * XML Type:  CT_PhantPr
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTPhantPr
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_PhantPr(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTPhantPr extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTPhantPr> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctphantprfe6btype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "show" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getShow();

    /**
     * True if has "show" element
     */
    boolean isSetShow();

    /**
     * Sets the "show" element
     */
    void setShow(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff show);

    /**
     * Appends and returns a new empty "show" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewShow();

    /**
     * Unsets the "show" element
     */
    void unsetShow();

    /**
     * Gets the "zeroWid" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getZeroWid();

    /**
     * True if has "zeroWid" element
     */
    boolean isSetZeroWid();

    /**
     * Sets the "zeroWid" element
     */
    void setZeroWid(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff zeroWid);

    /**
     * Appends and returns a new empty "zeroWid" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewZeroWid();

    /**
     * Unsets the "zeroWid" element
     */
    void unsetZeroWid();

    /**
     * Gets the "zeroAsc" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getZeroAsc();

    /**
     * True if has "zeroAsc" element
     */
    boolean isSetZeroAsc();

    /**
     * Sets the "zeroAsc" element
     */
    void setZeroAsc(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff zeroAsc);

    /**
     * Appends and returns a new empty "zeroAsc" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewZeroAsc();

    /**
     * Unsets the "zeroAsc" element
     */
    void unsetZeroAsc();

    /**
     * Gets the "zeroDesc" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getZeroDesc();

    /**
     * True if has "zeroDesc" element
     */
    boolean isSetZeroDesc();

    /**
     * Sets the "zeroDesc" element
     */
    void setZeroDesc(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff zeroDesc);

    /**
     * Appends and returns a new empty "zeroDesc" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewZeroDesc();

    /**
     * Unsets the "zeroDesc" element
     */
    void unsetZeroDesc();

    /**
     * Gets the "transp" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getTransp();

    /**
     * True if has "transp" element
     */
    boolean isSetTransp();

    /**
     * Sets the "transp" element
     */
    void setTransp(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff transp);

    /**
     * Appends and returns a new empty "transp" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewTransp();

    /**
     * Unsets the "transp" element
     */
    void unsetTransp();

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
