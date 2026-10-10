/*
 * XML Type:  CT_MathPr
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTMathPr
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_MathPr(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTMathPr extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTMathPr> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctmathpr9a88type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "mathFont" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTString getMathFont();

    /**
     * True if has "mathFont" element
     */
    boolean isSetMathFont();

    /**
     * Sets the "mathFont" element
     */
    void setMathFont(org.openxmlformats.schemas.officeDocument.x2006.math.CTString mathFont);

    /**
     * Appends and returns a new empty "mathFont" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTString addNewMathFont();

    /**
     * Unsets the "mathFont" element
     */
    void unsetMathFont();

    /**
     * Gets the "brkBin" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBin getBrkBin();

    /**
     * True if has "brkBin" element
     */
    boolean isSetBrkBin();

    /**
     * Sets the "brkBin" element
     */
    void setBrkBin(org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBin brkBin);

    /**
     * Appends and returns a new empty "brkBin" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBin addNewBrkBin();

    /**
     * Unsets the "brkBin" element
     */
    void unsetBrkBin();

    /**
     * Gets the "brkBinSub" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBinSub getBrkBinSub();

    /**
     * True if has "brkBinSub" element
     */
    boolean isSetBrkBinSub();

    /**
     * Sets the "brkBinSub" element
     */
    void setBrkBinSub(org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBinSub brkBinSub);

    /**
     * Appends and returns a new empty "brkBinSub" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTBreakBinSub addNewBrkBinSub();

    /**
     * Unsets the "brkBinSub" element
     */
    void unsetBrkBinSub();

    /**
     * Gets the "smallFrac" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getSmallFrac();

    /**
     * True if has "smallFrac" element
     */
    boolean isSetSmallFrac();

    /**
     * Sets the "smallFrac" element
     */
    void setSmallFrac(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff smallFrac);

    /**
     * Appends and returns a new empty "smallFrac" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewSmallFrac();

    /**
     * Unsets the "smallFrac" element
     */
    void unsetSmallFrac();

    /**
     * Gets the "dispDef" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getDispDef();

    /**
     * True if has "dispDef" element
     */
    boolean isSetDispDef();

    /**
     * Sets the "dispDef" element
     */
    void setDispDef(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff dispDef);

    /**
     * Appends and returns a new empty "dispDef" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewDispDef();

    /**
     * Unsets the "dispDef" element
     */
    void unsetDispDef();

    /**
     * Gets the "lMargin" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure getLMargin();

    /**
     * True if has "lMargin" element
     */
    boolean isSetLMargin();

    /**
     * Sets the "lMargin" element
     */
    void setLMargin(org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure lMargin);

    /**
     * Appends and returns a new empty "lMargin" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure addNewLMargin();

    /**
     * Unsets the "lMargin" element
     */
    void unsetLMargin();

    /**
     * Gets the "rMargin" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure getRMargin();

    /**
     * True if has "rMargin" element
     */
    boolean isSetRMargin();

    /**
     * Sets the "rMargin" element
     */
    void setRMargin(org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure rMargin);

    /**
     * Appends and returns a new empty "rMargin" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure addNewRMargin();

    /**
     * Unsets the "rMargin" element
     */
    void unsetRMargin();

    /**
     * Gets the "defJc" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc getDefJc();

    /**
     * True if has "defJc" element
     */
    boolean isSetDefJc();

    /**
     * Sets the "defJc" element
     */
    void setDefJc(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc defJc);

    /**
     * Appends and returns a new empty "defJc" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathJc addNewDefJc();

    /**
     * Unsets the "defJc" element
     */
    void unsetDefJc();

    /**
     * Gets the "preSp" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure getPreSp();

    /**
     * True if has "preSp" element
     */
    boolean isSetPreSp();

    /**
     * Sets the "preSp" element
     */
    void setPreSp(org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure preSp);

    /**
     * Appends and returns a new empty "preSp" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure addNewPreSp();

    /**
     * Unsets the "preSp" element
     */
    void unsetPreSp();

    /**
     * Gets the "postSp" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure getPostSp();

    /**
     * True if has "postSp" element
     */
    boolean isSetPostSp();

    /**
     * Sets the "postSp" element
     */
    void setPostSp(org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure postSp);

    /**
     * Appends and returns a new empty "postSp" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure addNewPostSp();

    /**
     * Unsets the "postSp" element
     */
    void unsetPostSp();

    /**
     * Gets the "interSp" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure getInterSp();

    /**
     * True if has "interSp" element
     */
    boolean isSetInterSp();

    /**
     * Sets the "interSp" element
     */
    void setInterSp(org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure interSp);

    /**
     * Appends and returns a new empty "interSp" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure addNewInterSp();

    /**
     * Unsets the "interSp" element
     */
    void unsetInterSp();

    /**
     * Gets the "intraSp" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure getIntraSp();

    /**
     * True if has "intraSp" element
     */
    boolean isSetIntraSp();

    /**
     * Sets the "intraSp" element
     */
    void setIntraSp(org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure intraSp);

    /**
     * Appends and returns a new empty "intraSp" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure addNewIntraSp();

    /**
     * Unsets the "intraSp" element
     */
    void unsetIntraSp();

    /**
     * Gets the "wrapIndent" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure getWrapIndent();

    /**
     * True if has "wrapIndent" element
     */
    boolean isSetWrapIndent();

    /**
     * Sets the "wrapIndent" element
     */
    void setWrapIndent(org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure wrapIndent);

    /**
     * Appends and returns a new empty "wrapIndent" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTTwipsMeasure addNewWrapIndent();

    /**
     * Unsets the "wrapIndent" element
     */
    void unsetWrapIndent();

    /**
     * Gets the "wrapRight" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff getWrapRight();

    /**
     * True if has "wrapRight" element
     */
    boolean isSetWrapRight();

    /**
     * Sets the "wrapRight" element
     */
    void setWrapRight(org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff wrapRight);

    /**
     * Appends and returns a new empty "wrapRight" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOnOff addNewWrapRight();

    /**
     * Unsets the "wrapRight" element
     */
    void unsetWrapRight();

    /**
     * Gets the "intLim" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc getIntLim();

    /**
     * True if has "intLim" element
     */
    boolean isSetIntLim();

    /**
     * Sets the "intLim" element
     */
    void setIntLim(org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc intLim);

    /**
     * Appends and returns a new empty "intLim" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc addNewIntLim();

    /**
     * Unsets the "intLim" element
     */
    void unsetIntLim();

    /**
     * Gets the "naryLim" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc getNaryLim();

    /**
     * True if has "naryLim" element
     */
    boolean isSetNaryLim();

    /**
     * Sets the "naryLim" element
     */
    void setNaryLim(org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc naryLim);

    /**
     * Appends and returns a new empty "naryLim" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLoc addNewNaryLim();

    /**
     * Unsets the "naryLim" element
     */
    void unsetNaryLim();
}
