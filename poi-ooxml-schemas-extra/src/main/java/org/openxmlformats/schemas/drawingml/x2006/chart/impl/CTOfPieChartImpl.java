/*
 * XML Type:  CT_OfPieChart
 * Namespace: http://schemas.openxmlformats.org/drawingml/2006/chart
 * Java type: org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieChart
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.drawingml.x2006.chart.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.QNameSet;

/**
 * An XML CT_OfPieChart(@http://schemas.openxmlformats.org/drawingml/2006/chart).
 *
 * This is a complex type.
 */
public class CTOfPieChartImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieChart {
    private static final long serialVersionUID = 1L;

    public CTOfPieChartImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    private static final QName[] PROPERTY_QNAME = {
        new QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "ofPieType"),
        new QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "varyColors"),
        new QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "ser"),
        new QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "dLbls"),
        new QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "gapWidth"),
        new QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "splitType"),
        new QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "splitPos"),
        new QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "custSplit"),
        new QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "secondPieSize"),
        new QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "serLines"),
        new QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "extLst"),
    };


    /**
     * Gets the "ofPieType" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieType getOfPieType() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieType target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieType)get_store().find_element_user(PROPERTY_QNAME[0], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * Sets the "ofPieType" element
     */
    @Override
    public void setOfPieType(org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieType ofPieType) {
        generatedSetterHelperImpl(ofPieType, PROPERTY_QNAME[0], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "ofPieType" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieType addNewOfPieType() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieType target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieType)get_store().add_element_user(PROPERTY_QNAME[0]);
            return target;
        }
    }

    /**
     * Gets the "varyColors" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean getVaryColors() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean)get_store().find_element_user(PROPERTY_QNAME[1], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "varyColors" element
     */
    @Override
    public boolean isSetVaryColors() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[1]) != 0;
        }
    }

    /**
     * Sets the "varyColors" element
     */
    @Override
    public void setVaryColors(org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean varyColors) {
        generatedSetterHelperImpl(varyColors, PROPERTY_QNAME[1], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "varyColors" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean addNewVaryColors() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean)get_store().add_element_user(PROPERTY_QNAME[1]);
            return target;
        }
    }

    /**
     * Unsets the "varyColors" element
     */
    @Override
    public void unsetVaryColors() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[1], 0);
        }
    }

    /**
     * Gets a List of "ser" elements
     */
    @Override
    public java.util.List<org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer> getSerList() {
        synchronized (monitor()) {
            check_orphaned();
            return new org.apache.xmlbeans.impl.values.JavaListXmlObject<>(
                this::getSerArray,
                this::setSerArray,
                this::insertNewSer,
                this::removeSer,
                this::sizeOfSerArray
            );
        }
    }

    /**
     * Gets array of all "ser" elements
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer[] getSerArray() {
        return getXmlObjectArray(PROPERTY_QNAME[2], new org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer[0]);
    }

    /**
     * Gets ith "ser" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer getSerArray(int i) {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer)get_store().find_element_user(PROPERTY_QNAME[2], i);
            if (target == null) {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }

    /**
     * Returns number of "ser" element
     */
    @Override
    public int sizeOfSerArray() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[2]);
        }
    }

    /**
     * Sets array of all "ser" element  WARNING: This method is not atomicaly synchronized.
     */
    @Override
    public void setSerArray(org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer[] serArray) {
        check_orphaned();
        arraySetterHelper(serArray, PROPERTY_QNAME[2]);
    }

    /**
     * Sets ith "ser" element
     */
    @Override
    public void setSerArray(int i, org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer ser) {
        generatedSetterHelperImpl(ser, PROPERTY_QNAME[2], i, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_ARRAYITEM);
    }

    /**
     * Inserts and returns a new empty value (as xml) as the ith "ser" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer insertNewSer(int i) {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer)get_store().insert_element_user(PROPERTY_QNAME[2], i);
            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "ser" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer addNewSer() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer)get_store().add_element_user(PROPERTY_QNAME[2]);
            return target;
        }
    }

    /**
     * Removes the ith "ser" element
     */
    @Override
    public void removeSer(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[2], i);
        }
    }

    /**
     * Gets the "dLbls" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls getDLbls() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls)get_store().find_element_user(PROPERTY_QNAME[3], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "dLbls" element
     */
    @Override
    public boolean isSetDLbls() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[3]) != 0;
        }
    }

    /**
     * Sets the "dLbls" element
     */
    @Override
    public void setDLbls(org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls dLbls) {
        generatedSetterHelperImpl(dLbls, PROPERTY_QNAME[3], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "dLbls" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls addNewDLbls() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls)get_store().add_element_user(PROPERTY_QNAME[3]);
            return target;
        }
    }

    /**
     * Unsets the "dLbls" element
     */
    @Override
    public void unsetDLbls() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[3], 0);
        }
    }

    /**
     * Gets the "gapWidth" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTGapAmount getGapWidth() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTGapAmount target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTGapAmount)get_store().find_element_user(PROPERTY_QNAME[4], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "gapWidth" element
     */
    @Override
    public boolean isSetGapWidth() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[4]) != 0;
        }
    }

    /**
     * Sets the "gapWidth" element
     */
    @Override
    public void setGapWidth(org.openxmlformats.schemas.drawingml.x2006.chart.CTGapAmount gapWidth) {
        generatedSetterHelperImpl(gapWidth, PROPERTY_QNAME[4], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "gapWidth" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTGapAmount addNewGapWidth() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTGapAmount target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTGapAmount)get_store().add_element_user(PROPERTY_QNAME[4]);
            return target;
        }
    }

    /**
     * Unsets the "gapWidth" element
     */
    @Override
    public void unsetGapWidth() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[4], 0);
        }
    }

    /**
     * Gets the "splitType" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTSplitType getSplitType() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTSplitType target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTSplitType)get_store().find_element_user(PROPERTY_QNAME[5], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "splitType" element
     */
    @Override
    public boolean isSetSplitType() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[5]) != 0;
        }
    }

    /**
     * Sets the "splitType" element
     */
    @Override
    public void setSplitType(org.openxmlformats.schemas.drawingml.x2006.chart.CTSplitType splitType) {
        generatedSetterHelperImpl(splitType, PROPERTY_QNAME[5], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "splitType" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTSplitType addNewSplitType() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTSplitType target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTSplitType)get_store().add_element_user(PROPERTY_QNAME[5]);
            return target;
        }
    }

    /**
     * Unsets the "splitType" element
     */
    @Override
    public void unsetSplitType() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[5], 0);
        }
    }

    /**
     * Gets the "splitPos" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTDouble getSplitPos() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTDouble target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTDouble)get_store().find_element_user(PROPERTY_QNAME[6], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "splitPos" element
     */
    @Override
    public boolean isSetSplitPos() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[6]) != 0;
        }
    }

    /**
     * Sets the "splitPos" element
     */
    @Override
    public void setSplitPos(org.openxmlformats.schemas.drawingml.x2006.chart.CTDouble splitPos) {
        generatedSetterHelperImpl(splitPos, PROPERTY_QNAME[6], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "splitPos" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTDouble addNewSplitPos() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTDouble target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTDouble)get_store().add_element_user(PROPERTY_QNAME[6]);
            return target;
        }
    }

    /**
     * Unsets the "splitPos" element
     */
    @Override
    public void unsetSplitPos() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[6], 0);
        }
    }

    /**
     * Gets the "custSplit" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTCustSplit getCustSplit() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTCustSplit target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTCustSplit)get_store().find_element_user(PROPERTY_QNAME[7], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "custSplit" element
     */
    @Override
    public boolean isSetCustSplit() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[7]) != 0;
        }
    }

    /**
     * Sets the "custSplit" element
     */
    @Override
    public void setCustSplit(org.openxmlformats.schemas.drawingml.x2006.chart.CTCustSplit custSplit) {
        generatedSetterHelperImpl(custSplit, PROPERTY_QNAME[7], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "custSplit" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTCustSplit addNewCustSplit() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTCustSplit target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTCustSplit)get_store().add_element_user(PROPERTY_QNAME[7]);
            return target;
        }
    }

    /**
     * Unsets the "custSplit" element
     */
    @Override
    public void unsetCustSplit() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[7], 0);
        }
    }

    /**
     * Gets the "secondPieSize" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTSecondPieSize getSecondPieSize() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTSecondPieSize target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTSecondPieSize)get_store().find_element_user(PROPERTY_QNAME[8], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "secondPieSize" element
     */
    @Override
    public boolean isSetSecondPieSize() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[8]) != 0;
        }
    }

    /**
     * Sets the "secondPieSize" element
     */
    @Override
    public void setSecondPieSize(org.openxmlformats.schemas.drawingml.x2006.chart.CTSecondPieSize secondPieSize) {
        generatedSetterHelperImpl(secondPieSize, PROPERTY_QNAME[8], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "secondPieSize" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTSecondPieSize addNewSecondPieSize() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTSecondPieSize target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTSecondPieSize)get_store().add_element_user(PROPERTY_QNAME[8]);
            return target;
        }
    }

    /**
     * Unsets the "secondPieSize" element
     */
    @Override
    public void unsetSecondPieSize() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[8], 0);
        }
    }

    /**
     * Gets a List of "serLines" elements
     */
    @Override
    public java.util.List<org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines> getSerLinesList() {
        synchronized (monitor()) {
            check_orphaned();
            return new org.apache.xmlbeans.impl.values.JavaListXmlObject<>(
                this::getSerLinesArray,
                this::setSerLinesArray,
                this::insertNewSerLines,
                this::removeSerLines,
                this::sizeOfSerLinesArray
            );
        }
    }

    /**
     * Gets array of all "serLines" elements
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines[] getSerLinesArray() {
        return getXmlObjectArray(PROPERTY_QNAME[9], new org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines[0]);
    }

    /**
     * Gets ith "serLines" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines getSerLinesArray(int i) {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines)get_store().find_element_user(PROPERTY_QNAME[9], i);
            if (target == null) {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }

    /**
     * Returns number of "serLines" element
     */
    @Override
    public int sizeOfSerLinesArray() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[9]);
        }
    }

    /**
     * Sets array of all "serLines" element  WARNING: This method is not atomicaly synchronized.
     */
    @Override
    public void setSerLinesArray(org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines[] serLinesArray) {
        check_orphaned();
        arraySetterHelper(serLinesArray, PROPERTY_QNAME[9]);
    }

    /**
     * Sets ith "serLines" element
     */
    @Override
    public void setSerLinesArray(int i, org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines serLines) {
        generatedSetterHelperImpl(serLines, PROPERTY_QNAME[9], i, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_ARRAYITEM);
    }

    /**
     * Inserts and returns a new empty value (as xml) as the ith "serLines" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines insertNewSerLines(int i) {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines)get_store().insert_element_user(PROPERTY_QNAME[9], i);
            return target;
        }
    }

    /**
     * Appends and returns a new empty value (as xml) as the last "serLines" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines addNewSerLines() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines)get_store().add_element_user(PROPERTY_QNAME[9]);
            return target;
        }
    }

    /**
     * Removes the ith "serLines" element
     */
    @Override
    public void removeSerLines(int i) {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[9], i);
        }
    }

    /**
     * Gets the "extLst" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTExtensionList getExtLst() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTExtensionList target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTExtensionList)get_store().find_element_user(PROPERTY_QNAME[10], 0);
            return (target == null) ? null : target;
        }
    }

    /**
     * True if has "extLst" element
     */
    @Override
    public boolean isSetExtLst() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().count_elements(PROPERTY_QNAME[10]) != 0;
        }
    }

    /**
     * Sets the "extLst" element
     */
    @Override
    public void setExtLst(org.openxmlformats.schemas.drawingml.x2006.chart.CTExtensionList extLst) {
        generatedSetterHelperImpl(extLst, PROPERTY_QNAME[10], 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }

    /**
     * Appends and returns a new empty "extLst" element
     */
    @Override
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTExtensionList addNewExtLst() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTExtensionList target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTExtensionList)get_store().add_element_user(PROPERTY_QNAME[10]);
            return target;
        }
    }

    /**
     * Unsets the "extLst" element
     */
    @Override
    public void unsetExtLst() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_element(PROPERTY_QNAME[10], 0);
        }
    }
}
