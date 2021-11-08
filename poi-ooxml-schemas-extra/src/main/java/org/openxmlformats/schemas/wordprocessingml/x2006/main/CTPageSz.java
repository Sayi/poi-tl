/*
 * XML Type:  CT_PageSz
 * Namespace: http://schemas.openxmlformats.org/wordprocessingml/2006/main
 * Java type: org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.wordprocessingml.x2006.main;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_PageSz(@http://schemas.openxmlformats.org/wordprocessingml/2006/main).
 *
 * This is a complex type.
 */
public interface CTPageSz extends org.apache.xmlbeans.XmlObject
{
    DocumentFactory<org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctpagesz2d12type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "w" attribute
     */
    java.lang.Object getW();

    /**
     * Gets (as xml) the "w" attribute
     */
    org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure xgetW();

    /**
     * True if has "w" attribute
     */
    boolean isSetW();

    /**
     * Sets the "w" attribute
     */
    void setW(java.lang.Object w);

    /**
     * Sets (as xml) the "w" attribute
     */
    void xsetW(org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure w);

    /**
     * Unsets the "w" attribute
     */
    void unsetW();

    /**
     * Gets the "h" attribute
     */
    java.lang.Object getH();

    /**
     * Gets (as xml) the "h" attribute
     */
    org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure xgetH();

    /**
     * True if has "h" attribute
     */
    boolean isSetH();

    /**
     * Sets the "h" attribute
     */
    void setH(java.lang.Object h);

    /**
     * Sets (as xml) the "h" attribute
     */
    void xsetH(org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure h);

    /**
     * Unsets the "h" attribute
     */
    void unsetH();

    /**
     * Gets the "orient" attribute
     */
    org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation.Enum getOrient();

    /**
     * Gets (as xml) the "orient" attribute
     */
    org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation xgetOrient();

    /**
     * True if has "orient" attribute
     */
    boolean isSetOrient();

    /**
     * Sets the "orient" attribute
     */
    void setOrient(org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation.Enum orient);

    /**
     * Sets (as xml) the "orient" attribute
     */
    void xsetOrient(org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation orient);

    /**
     * Unsets the "orient" attribute
     */
    void unsetOrient();

    /**
     * Gets the "code" attribute
     */
    java.math.BigInteger getCode();

    /**
     * Gets (as xml) the "code" attribute
     */
    org.openxmlformats.schemas.wordprocessingml.x2006.main.STDecimalNumber xgetCode();

    /**
     * True if has "code" attribute
     */
    boolean isSetCode();

    /**
     * Sets the "code" attribute
     */
    void setCode(java.math.BigInteger code);

    /**
     * Sets (as xml) the "code" attribute
     */
    void xsetCode(org.openxmlformats.schemas.wordprocessingml.x2006.main.STDecimalNumber code);

    /**
     * Unsets the "code" attribute
     */
    void unsetCode();
}
