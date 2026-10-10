/*
 * XML Type:  CT_BoxPr
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTBoxPr
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_BoxPr(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTBoxPr extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTBoxPr> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctboxpr2c4ftype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "opEmu" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getOpEmu();

    /**
     * True if has "opEmu" element
     */
    boolean isSetOpEmu();

    /**
     * Sets the "opEmu" element
     */
    void setOpEmu(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff opEmu);

    /**
     * Appends and returns a new empty "opEmu" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewOpEmu();

    /**
     * Unsets the "opEmu" element
     */
    void unsetOpEmu();

    /**
     * Gets the "noBreak" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getNoBreak();

    /**
     * True if has "noBreak" element
     */
    boolean isSetNoBreak();

    /**
     * Sets the "noBreak" element
     */
    void setNoBreak(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff noBreak);

    /**
     * Appends and returns a new empty "noBreak" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewNoBreak();

    /**
     * Unsets the "noBreak" element
     */
    void unsetNoBreak();

    /**
     * Gets the "diff" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getDiff();

    /**
     * True if has "diff" element
     */
    boolean isSetDiff();

    /**
     * Sets the "diff" element
     */
    void setDiff(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff diff);

    /**
     * Appends and returns a new empty "diff" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewDiff();

    /**
     * Unsets the "diff" element
     */
    void unsetDiff();

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
