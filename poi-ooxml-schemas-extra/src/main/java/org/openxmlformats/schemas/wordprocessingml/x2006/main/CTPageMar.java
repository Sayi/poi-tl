/*
 * XML Type:  CT_PageMar
 * Namespace: http://schemas.openxmlformats.org/wordprocessingml/2006/main
 * Java type: org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.wordprocessingml.x2006.main;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_PageMar(@http://schemas.openxmlformats.org/wordprocessingml/2006/main).
 *
 * This is a complex type.
 */
public interface CTPageMar extends org.apache.xmlbeans.XmlObject
{
    DocumentFactory<org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctpagemar92a3type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "top" attribute
     */
    java.lang.Object getTop();

    /**
     * Gets (as xml) the "top" attribute
     */
    org.openxmlformats.schemas.wordprocessingml.x2006.main.STSignedTwipsMeasure xgetTop();

    /**
     * Sets the "top" attribute
     */
    void setTop(java.lang.Object top);

    /**
     * Sets (as xml) the "top" attribute
     */
    void xsetTop(org.openxmlformats.schemas.wordprocessingml.x2006.main.STSignedTwipsMeasure top);

    /**
     * Gets the "right" attribute
     */
    java.lang.Object getRight();

    /**
     * Gets (as xml) the "right" attribute
     */
    org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure xgetRight();

    /**
     * Sets the "right" attribute
     */
    void setRight(java.lang.Object right);

    /**
     * Sets (as xml) the "right" attribute
     */
    void xsetRight(org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure right);

    /**
     * Gets the "bottom" attribute
     */
    java.lang.Object getBottom();

    /**
     * Gets (as xml) the "bottom" attribute
     */
    org.openxmlformats.schemas.wordprocessingml.x2006.main.STSignedTwipsMeasure xgetBottom();

    /**
     * Sets the "bottom" attribute
     */
    void setBottom(java.lang.Object bottom);

    /**
     * Sets (as xml) the "bottom" attribute
     */
    void xsetBottom(org.openxmlformats.schemas.wordprocessingml.x2006.main.STSignedTwipsMeasure bottom);

    /**
     * Gets the "left" attribute
     */
    java.lang.Object getLeft();

    /**
     * Gets (as xml) the "left" attribute
     */
    org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure xgetLeft();

    /**
     * Sets the "left" attribute
     */
    void setLeft(java.lang.Object left);

    /**
     * Sets (as xml) the "left" attribute
     */
    void xsetLeft(org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure left);

    /**
     * Gets the "header" attribute
     */
    java.lang.Object getHeader();

    /**
     * Gets (as xml) the "header" attribute
     */
    org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure xgetHeader();

    /**
     * Sets the "header" attribute
     */
    void setHeader(java.lang.Object header);

    /**
     * Sets (as xml) the "header" attribute
     */
    void xsetHeader(org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure header);

    /**
     * Gets the "footer" attribute
     */
    java.lang.Object getFooter();

    /**
     * Gets (as xml) the "footer" attribute
     */
    org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure xgetFooter();

    /**
     * Sets the "footer" attribute
     */
    void setFooter(java.lang.Object footer);

    /**
     * Sets (as xml) the "footer" attribute
     */
    void xsetFooter(org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure footer);

    /**
     * Gets the "gutter" attribute
     */
    java.lang.Object getGutter();

    /**
     * Gets (as xml) the "gutter" attribute
     */
    org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure xgetGutter();

    /**
     * Sets the "gutter" attribute
     */
    void setGutter(java.lang.Object gutter);

    /**
     * Sets (as xml) the "gutter" attribute
     */
    void xsetGutter(org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure gutter);
}
