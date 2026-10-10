/*
 * XML Type:  CT_EqArrPr
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTEqArrPr
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_EqArrPr(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTEqArrPr extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTEqArrPr> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "cteqarrpr1305type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "baseJc" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTYAlign getBaseJc();

    /**
     * True if has "baseJc" element
     */
    boolean isSetBaseJc();

    /**
     * Sets the "baseJc" element
     */
    void setBaseJc(org.openxmlformats.schemas.officeDocument.x2006.math.CTYAlign baseJc);

    /**
     * Appends and returns a new empty "baseJc" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTYAlign addNewBaseJc();

    /**
     * Unsets the "baseJc" element
     */
    void unsetBaseJc();

    /**
     * Gets the "maxDist" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getMaxDist();

    /**
     * True if has "maxDist" element
     */
    boolean isSetMaxDist();

    /**
     * Sets the "maxDist" element
     */
    void setMaxDist(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff maxDist);

    /**
     * Appends and returns a new empty "maxDist" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewMaxDist();

    /**
     * Unsets the "maxDist" element
     */
    void unsetMaxDist();

    /**
     * Gets the "objDist" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getObjDist();

    /**
     * True if has "objDist" element
     */
    boolean isSetObjDist();

    /**
     * Sets the "objDist" element
     */
    void setObjDist(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff objDist);

    /**
     * Appends and returns a new empty "objDist" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewObjDist();

    /**
     * Unsets the "objDist" element
     */
    void unsetObjDist();

    /**
     * Gets the "rSpRule" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTSpacingRule getRSpRule();

    /**
     * True if has "rSpRule" element
     */
    boolean isSetRSpRule();

    /**
     * Sets the "rSpRule" element
     */
    void setRSpRule(org.openxmlformats.schemas.officeDocument.x2006.math.CTSpacingRule rSpRule);

    /**
     * Appends and returns a new empty "rSpRule" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTSpacingRule addNewRSpRule();

    /**
     * Unsets the "rSpRule" element
     */
    void unsetRSpRule();

    /**
     * Gets the "rSp" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTUnSignedInteger getRSp();

    /**
     * True if has "rSp" element
     */
    boolean isSetRSp();

    /**
     * Sets the "rSp" element
     */
    void setRSp(org.openxmlformats.schemas.officeDocument.x2006.math.CTUnSignedInteger rSp);

    /**
     * Appends and returns a new empty "rSp" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTUnSignedInteger addNewRSp();

    /**
     * Unsets the "rSp" element
     */
    void unsetRSp();

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
