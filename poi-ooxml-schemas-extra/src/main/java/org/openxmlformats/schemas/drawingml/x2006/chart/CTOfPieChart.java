/*
 * XML Type:  CT_OfPieChart
 * Namespace: http://schemas.openxmlformats.org/drawingml/2006/chart
 * Java type: org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieChart
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.drawingml.x2006.chart;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_OfPieChart(@http://schemas.openxmlformats.org/drawingml/2006/chart).
 *
 * This is a complex type.
 */
public interface CTOfPieChart extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieChart> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctofpiechartbbb3type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "ofPieType" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieType getOfPieType();

    /**
     * Sets the "ofPieType" element
     */
    void setOfPieType(org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieType ofPieType);

    /**
     * Appends and returns a new empty "ofPieType" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieType addNewOfPieType();

    /**
     * Gets the "varyColors" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean getVaryColors();

    /**
     * True if has "varyColors" element
     */
    boolean isSetVaryColors();

    /**
     * Sets the "varyColors" element
     */
    void setVaryColors(org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean varyColors);

    /**
     * Appends and returns a new empty "varyColors" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean addNewVaryColors();

    /**
     * Unsets the "varyColors" element
     */
    void unsetVaryColors();

    /**
     * Gets a List of "ser" elements
     */
    java.util.List<org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer> getSerList();

    /**
     * Gets array of all "ser" elements
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer[] getSerArray();

    /**
     * Gets ith "ser" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer getSerArray(int i);

    /**
     * Returns number of "ser" element
     */
    int sizeOfSerArray();

    /**
     * Sets array of all "ser" element
     */
    void setSerArray(org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer[] serArray);

    /**
     * Sets ith "ser" element
     */
    void setSerArray(int i, org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer ser);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "ser" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer insertNewSer(int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "ser" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer addNewSer();

    /**
     * Removes the ith "ser" element
     */
    void removeSer(int i);

    /**
     * Gets the "dLbls" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls getDLbls();

    /**
     * True if has "dLbls" element
     */
    boolean isSetDLbls();

    /**
     * Sets the "dLbls" element
     */
    void setDLbls(org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls dLbls);

    /**
     * Appends and returns a new empty "dLbls" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls addNewDLbls();

    /**
     * Unsets the "dLbls" element
     */
    void unsetDLbls();

    /**
     * Gets the "gapWidth" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTGapAmount getGapWidth();

    /**
     * True if has "gapWidth" element
     */
    boolean isSetGapWidth();

    /**
     * Sets the "gapWidth" element
     */
    void setGapWidth(org.openxmlformats.schemas.drawingml.x2006.chart.CTGapAmount gapWidth);

    /**
     * Appends and returns a new empty "gapWidth" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTGapAmount addNewGapWidth();

    /**
     * Unsets the "gapWidth" element
     */
    void unsetGapWidth();

    /**
     * Gets the "splitType" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTSplitType getSplitType();

    /**
     * True if has "splitType" element
     */
    boolean isSetSplitType();

    /**
     * Sets the "splitType" element
     */
    void setSplitType(org.openxmlformats.schemas.drawingml.x2006.chart.CTSplitType splitType);

    /**
     * Appends and returns a new empty "splitType" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTSplitType addNewSplitType();

    /**
     * Unsets the "splitType" element
     */
    void unsetSplitType();

    /**
     * Gets the "splitPos" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTDouble getSplitPos();

    /**
     * True if has "splitPos" element
     */
    boolean isSetSplitPos();

    /**
     * Sets the "splitPos" element
     */
    void setSplitPos(org.openxmlformats.schemas.drawingml.x2006.chart.CTDouble splitPos);

    /**
     * Appends and returns a new empty "splitPos" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTDouble addNewSplitPos();

    /**
     * Unsets the "splitPos" element
     */
    void unsetSplitPos();

    /**
     * Gets the "custSplit" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTCustSplit getCustSplit();

    /**
     * True if has "custSplit" element
     */
    boolean isSetCustSplit();

    /**
     * Sets the "custSplit" element
     */
    void setCustSplit(org.openxmlformats.schemas.drawingml.x2006.chart.CTCustSplit custSplit);

    /**
     * Appends and returns a new empty "custSplit" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTCustSplit addNewCustSplit();

    /**
     * Unsets the "custSplit" element
     */
    void unsetCustSplit();

    /**
     * Gets the "secondPieSize" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTSecondPieSize getSecondPieSize();

    /**
     * True if has "secondPieSize" element
     */
    boolean isSetSecondPieSize();

    /**
     * Sets the "secondPieSize" element
     */
    void setSecondPieSize(org.openxmlformats.schemas.drawingml.x2006.chart.CTSecondPieSize secondPieSize);

    /**
     * Appends and returns a new empty "secondPieSize" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTSecondPieSize addNewSecondPieSize();

    /**
     * Unsets the "secondPieSize" element
     */
    void unsetSecondPieSize();

    /**
     * Gets a List of "serLines" elements
     */
    java.util.List<org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines> getSerLinesList();

    /**
     * Gets array of all "serLines" elements
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines[] getSerLinesArray();

    /**
     * Gets ith "serLines" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines getSerLinesArray(int i);

    /**
     * Returns number of "serLines" element
     */
    int sizeOfSerLinesArray();

    /**
     * Sets array of all "serLines" element
     */
    void setSerLinesArray(org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines[] serLinesArray);

    /**
     * Sets ith "serLines" element
     */
    void setSerLinesArray(int i, org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines serLines);

    /**
     * Inserts and returns a new empty value (as xml) as the ith "serLines" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines insertNewSerLines(int i);

    /**
     * Appends and returns a new empty value (as xml) as the last "serLines" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines addNewSerLines();

    /**
     * Removes the ith "serLines" element
     */
    void removeSerLines(int i);

    /**
     * Gets the "extLst" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTExtensionList getExtLst();

    /**
     * True if has "extLst" element
     */
    boolean isSetExtLst();

    /**
     * Sets the "extLst" element
     */
    void setExtLst(org.openxmlformats.schemas.drawingml.x2006.chart.CTExtensionList extLst);

    /**
     * Appends and returns a new empty "extLst" element
     */
    org.openxmlformats.schemas.drawingml.x2006.chart.CTExtensionList addNewExtLst();

    /**
     * Unsets the "extLst" element
     */
    void unsetExtLst();
}
