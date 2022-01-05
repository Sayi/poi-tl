/*
 * XML Type:  CT_CustSplit
 * Namespace: http://schemas.openxmlformats.org/drawingml/2006/chart
 * Java type: org.openxmlformats.schemas.drawingml.x2006.chart.CTCustSplit
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.drawingml.x2006.chart;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_CustSplit(@http://schemas.openxmlformats.org/drawingml/2006/chart).
 *
 * This is a complex type.
 */
public interface CTCustSplit extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.drawingml.x2006.chart.CTCustSplit> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctcustsplit93bftype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets a List of "secondPiePt" elements
     */
    java.util.List<org.openxmlformats.schemas.drawingml.x2006.chart.CTUnsignedInt> getSecondPiePtList();

    /**
     * Gets array of all "secondPiePt" elements
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTUnsignedInt[] getSecondPiePtArray();

    /**
     * Gets ith "secondPiePt" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTUnsignedInt getSecondPiePtArray(int i);

    /**
     * Returns number of "secondPiePt" element
     */
    int sizeOfSecondPiePtArray();

    /**
     * Sets array of all "secondPiePt" element
     */
    void setSecondPiePtArray(org.openxmlformats.schemas.drawingml.x2006.chart.CTUnsignedInt[] secondPiePtArray);

    /**
     * Sets ith "secondPiePt" element
     */
    void setSecondPiePtArray(int i, org.openxmlformats.schemas.drawingml.x2006.chart.CTUnsignedInt secondPiePt);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "secondPiePt" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTUnsignedInt insertNewSecondPiePt(int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "secondPiePt" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTUnsignedInt addNewSecondPiePt();

    /**
     * Removes the ith "secondPiePt" element
     */
    void removeSecondPiePt(int i);
}
