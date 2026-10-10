/*
 * XML Type:  CT_RPR
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTRPR
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_RPR(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTRPR extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTRPR> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctrprd468type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "lit" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getLit();

    /**
     * True if has "lit" element
     */
    boolean isSetLit();

    /**
     * Sets the "lit" element
     */
    void setLit(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff lit);

    /**
     * Appends and returns a new empty "lit" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewLit();

    /**
     * Unsets the "lit" element
     */
    void unsetLit();

    /**
     * Gets the "nor" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getNor();

    /**
     * True if has "nor" element
     */
    boolean isSetNor();

    /**
     * Sets the "nor" element
     */
    void setNor(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff nor);

    /**
     * Appends and returns a new empty "nor" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewNor();

    /**
     * Unsets the "nor" element
     */
    void unsetNor();

    /**
     * Gets the "scr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTScript getScr();

    /**
     * True if has "scr" element
     */
    boolean isSetScr();

    /**
     * Sets the "scr" element
     */
    void setScr(org.openxmlformats.schemas.officeDocument.x2006.math.CTScript scr);

    /**
     * Appends and returns a new empty "scr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTScript addNewScr();

    /**
     * Unsets the "scr" element
     */
    void unsetScr();

    /**
     * Gets the "sty" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTStyle getSty();

    /**
     * True if has "sty" element
     */
    boolean isSetSty();

    /**
     * Sets the "sty" element
     */
    void setSty(org.openxmlformats.schemas.officeDocument.x2006.math.CTStyle sty);

    /**
     * Appends and returns a new empty "sty" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTStyle addNewSty();

    /**
     * Unsets the "sty" element
     */
    void unsetSty();

    /**
     * Gets the "brk" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTManualBreak getBrk();

    /**
     * True if has "brk" element
     */
    boolean isSetBrk();

    /**
     * Sets the "brk" element
     */
    void setBrk(org.openxmlformats.schemas.officeDocument.x2006.math.CTManualBreak brk);

    /**
     * Appends and returns a new empty "brk" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTManualBreak addNewBrk();

    /**
     * Unsets the "brk" element
     */
    void unsetBrk();

    /**
     * Gets the "aln" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getAln();

    /**
     * True if has "aln" element
     */
    boolean isSetAln();

    /**
     * Sets the "aln" element
     */
    void setAln(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff aln);

    /**
     * Appends and returns a new empty "aln" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewAln();

    /**
     * Unsets the "aln" element
     */
    void unsetAln();
}
