/*
 * XML Type:  CT_OfPieChart
 * Namespace: http://schemas.openxmlformats.org/drawingml/2006/chart
 * Java type: org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieChart
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.drawingml.x2006.chart.impl;
/**
 * An XML CT_OfPieChart(@http://schemas.openxmlformats.org/drawingml/2006/chart).
 *
 * This is a complex type.
 */
public class CTOfPieChartImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieChart
{
    private static final long serialVersionUID = 1L;
    
    public CTOfPieChartImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName OFPIETYPE$0 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "ofPieType");
    private static final javax.xml.namespace.QName VARYCOLORS$2 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "varyColors");
    private static final javax.xml.namespace.QName SER$4 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "ser");
    private static final javax.xml.namespace.QName DLBLS$6 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "dLbls");
    private static final javax.xml.namespace.QName GAPWIDTH$8 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "gapWidth");
    private static final javax.xml.namespace.QName SPLITTYPE$10 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "splitType");
    private static final javax.xml.namespace.QName SPLITPOS$12 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "splitPos");
    private static final javax.xml.namespace.QName CUSTSPLIT$14 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "custSplit");
    private static final javax.xml.namespace.QName SECONDPIESIZE$16 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "secondPieSize");
    private static final javax.xml.namespace.QName SERLINES$18 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "serLines");
    private static final javax.xml.namespace.QName EXTLST$20 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/drawingml/2006/chart", "extLst");
    
    
    /**
     * Gets the "ofPieType" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieType getOfPieType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieType target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieType)get_store().find_element_user(OFPIETYPE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "ofPieType" element
     */
    public void setOfPieType(org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieType ofPieType)
    {
        generatedSetterHelperImpl(ofPieType, OFPIETYPE$0, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "ofPieType" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieType addNewOfPieType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieType target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTOfPieType)get_store().add_element_user(OFPIETYPE$0);
            return target;
        }
    }
    
    /**
     * Gets the "varyColors" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean getVaryColors()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean)get_store().find_element_user(VARYCOLORS$2, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "varyColors" element
     */
    public boolean isSetVaryColors()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(VARYCOLORS$2) != 0;
        }
    }
    
    /**
     * Sets the "varyColors" element
     */
    public void setVaryColors(org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean varyColors)
    {
        generatedSetterHelperImpl(varyColors, VARYCOLORS$2, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "varyColors" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean addNewVaryColors()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean)get_store().add_element_user(VARYCOLORS$2);
            return target;
        }
    }
    
    /**
     * Unsets the "varyColors" element
     */
    public void unsetVaryColors()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(VARYCOLORS$2, 0);
        }
    }
    
    /**
     * Gets a List of "ser" elements
     */
    public java.util.List<org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer> getSerList()
    {
        final class SerList extends java.util.AbstractList<org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer>
        {
            @Override
            public org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer get(int i)
                { return CTOfPieChartImpl.this.getSerArray(i); }
            
            @Override
            public org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer set(int i, org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer o)
            {
                org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer old = CTOfPieChartImpl.this.getSerArray(i);
                CTOfPieChartImpl.this.setSerArray(i, o);
                return old;
            }
            
            @Override
            public void add(int i, org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer o)
                { CTOfPieChartImpl.this.insertNewSer(i).set(o); }
            
            @Override
            public org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer remove(int i)
            {
                org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer old = CTOfPieChartImpl.this.getSerArray(i);
                CTOfPieChartImpl.this.removeSer(i);
                return old;
            }
            
            @Override
            public int size()
                { return CTOfPieChartImpl.this.sizeOfSerArray(); }
            
        }
        
        synchronized (monitor())
        {
            check_orphaned();
            return new SerList();
        }
    }
    
    /**
     * Gets array of all "ser" elements
     * @deprecated
     */
    @Deprecated
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer[] getSerArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List<org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer> targetList = new java.util.ArrayList<org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer>();
            get_store().find_all_element_users(SER$4, targetList);
            org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer[] result = new org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "ser" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer getSerArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer)get_store().find_element_user(SER$4, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "ser" element
     */
    public int sizeOfSerArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SER$4);
        }
    }
    
    /**
     * Sets array of all "ser" element  WARNING: This method is not atomicaly synchronized.
     */
    public void setSerArray(org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer[] serArray)
    {
        check_orphaned();
        arraySetterHelper(serArray, SER$4);
    }
    
    /**
     * Sets ith "ser" element
     */
    public void setSerArray(int i, org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer ser)
    {
        generatedSetterHelperImpl(ser, SER$4, i, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_ARRAYITEM);
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "ser" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer insertNewSer(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer)get_store().insert_element_user(SER$4, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "ser" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer addNewSer()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTPieSer)get_store().add_element_user(SER$4);
            return target;
        }
    }
    
    /**
     * Removes the ith "ser" element
     */
    public void removeSer(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SER$4, i);
        }
    }
    
    /**
     * Gets the "dLbls" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls getDLbls()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls)get_store().find_element_user(DLBLS$6, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "dLbls" element
     */
    public boolean isSetDLbls()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DLBLS$6) != 0;
        }
    }
    
    /**
     * Sets the "dLbls" element
     */
    public void setDLbls(org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls dLbls)
    {
        generatedSetterHelperImpl(dLbls, DLBLS$6, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "dLbls" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls addNewDLbls()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls)get_store().add_element_user(DLBLS$6);
            return target;
        }
    }
    
    /**
     * Unsets the "dLbls" element
     */
    public void unsetDLbls()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DLBLS$6, 0);
        }
    }
    
    /**
     * Gets the "gapWidth" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTGapAmount getGapWidth()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTGapAmount target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTGapAmount)get_store().find_element_user(GAPWIDTH$8, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "gapWidth" element
     */
    public boolean isSetGapWidth()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(GAPWIDTH$8) != 0;
        }
    }
    
    /**
     * Sets the "gapWidth" element
     */
    public void setGapWidth(org.openxmlformats.schemas.drawingml.x2006.chart.CTGapAmount gapWidth)
    {
        generatedSetterHelperImpl(gapWidth, GAPWIDTH$8, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "gapWidth" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTGapAmount addNewGapWidth()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTGapAmount target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTGapAmount)get_store().add_element_user(GAPWIDTH$8);
            return target;
        }
    }
    
    /**
     * Unsets the "gapWidth" element
     */
    public void unsetGapWidth()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(GAPWIDTH$8, 0);
        }
    }
    
    /**
     * Gets the "splitType" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTSplitType getSplitType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTSplitType target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTSplitType)get_store().find_element_user(SPLITTYPE$10, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "splitType" element
     */
    public boolean isSetSplitType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SPLITTYPE$10) != 0;
        }
    }
    
    /**
     * Sets the "splitType" element
     */
    public void setSplitType(org.openxmlformats.schemas.drawingml.x2006.chart.CTSplitType splitType)
    {
        generatedSetterHelperImpl(splitType, SPLITTYPE$10, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "splitType" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTSplitType addNewSplitType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTSplitType target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTSplitType)get_store().add_element_user(SPLITTYPE$10);
            return target;
        }
    }
    
    /**
     * Unsets the "splitType" element
     */
    public void unsetSplitType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SPLITTYPE$10, 0);
        }
    }
    
    /**
     * Gets the "splitPos" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTDouble getSplitPos()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTDouble target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTDouble)get_store().find_element_user(SPLITPOS$12, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "splitPos" element
     */
    public boolean isSetSplitPos()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SPLITPOS$12) != 0;
        }
    }
    
    /**
     * Sets the "splitPos" element
     */
    public void setSplitPos(org.openxmlformats.schemas.drawingml.x2006.chart.CTDouble splitPos)
    {
        generatedSetterHelperImpl(splitPos, SPLITPOS$12, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "splitPos" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTDouble addNewSplitPos()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTDouble target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTDouble)get_store().add_element_user(SPLITPOS$12);
            return target;
        }
    }
    
    /**
     * Unsets the "splitPos" element
     */
    public void unsetSplitPos()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SPLITPOS$12, 0);
        }
    }
    
    /**
     * Gets the "custSplit" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTCustSplit getCustSplit()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTCustSplit target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTCustSplit)get_store().find_element_user(CUSTSPLIT$14, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "custSplit" element
     */
    public boolean isSetCustSplit()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CUSTSPLIT$14) != 0;
        }
    }
    
    /**
     * Sets the "custSplit" element
     */
    public void setCustSplit(org.openxmlformats.schemas.drawingml.x2006.chart.CTCustSplit custSplit)
    {
        generatedSetterHelperImpl(custSplit, CUSTSPLIT$14, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "custSplit" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTCustSplit addNewCustSplit()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTCustSplit target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTCustSplit)get_store().add_element_user(CUSTSPLIT$14);
            return target;
        }
    }
    
    /**
     * Unsets the "custSplit" element
     */
    public void unsetCustSplit()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CUSTSPLIT$14, 0);
        }
    }
    
    /**
     * Gets the "secondPieSize" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTSecondPieSize getSecondPieSize()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTSecondPieSize target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTSecondPieSize)get_store().find_element_user(SECONDPIESIZE$16, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "secondPieSize" element
     */
    public boolean isSetSecondPieSize()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SECONDPIESIZE$16) != 0;
        }
    }
    
    /**
     * Sets the "secondPieSize" element
     */
    public void setSecondPieSize(org.openxmlformats.schemas.drawingml.x2006.chart.CTSecondPieSize secondPieSize)
    {
        generatedSetterHelperImpl(secondPieSize, SECONDPIESIZE$16, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "secondPieSize" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTSecondPieSize addNewSecondPieSize()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTSecondPieSize target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTSecondPieSize)get_store().add_element_user(SECONDPIESIZE$16);
            return target;
        }
    }
    
    /**
     * Unsets the "secondPieSize" element
     */
    public void unsetSecondPieSize()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SECONDPIESIZE$16, 0);
        }
    }
    
    /**
     * Gets a List of "serLines" elements
     */
    public java.util.List<org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines> getSerLinesList()
    {
        final class SerLinesList extends java.util.AbstractList<org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines>
        {
            @Override
            public org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines get(int i)
                { return CTOfPieChartImpl.this.getSerLinesArray(i); }
            
            @Override
            public org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines set(int i, org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines o)
            {
                org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines old = CTOfPieChartImpl.this.getSerLinesArray(i);
                CTOfPieChartImpl.this.setSerLinesArray(i, o);
                return old;
            }
            
            @Override
            public void add(int i, org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines o)
                { CTOfPieChartImpl.this.insertNewSerLines(i).set(o); }
            
            @Override
            public org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines remove(int i)
            {
                org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines old = CTOfPieChartImpl.this.getSerLinesArray(i);
                CTOfPieChartImpl.this.removeSerLines(i);
                return old;
            }
            
            @Override
            public int size()
                { return CTOfPieChartImpl.this.sizeOfSerLinesArray(); }
            
        }
        
        synchronized (monitor())
        {
            check_orphaned();
            return new SerLinesList();
        }
    }
    
    /**
     * Gets array of all "serLines" elements
     * @deprecated
     */
    @Deprecated
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines[] getSerLinesArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List<org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines> targetList = new java.util.ArrayList<org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines>();
            get_store().find_all_element_users(SERLINES$18, targetList);
            org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines[] result = new org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "serLines" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines getSerLinesArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines)get_store().find_element_user(SERLINES$18, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "serLines" element
     */
    public int sizeOfSerLinesArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SERLINES$18);
        }
    }
    
    /**
     * Sets array of all "serLines" element  WARNING: This method is not atomicaly synchronized.
     */
    public void setSerLinesArray(org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines[] serLinesArray)
    {
        check_orphaned();
        arraySetterHelper(serLinesArray, SERLINES$18);
    }
    
    /**
     * Sets ith "serLines" element
     */
    public void setSerLinesArray(int i, org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines serLines)
    {
        generatedSetterHelperImpl(serLines, SERLINES$18, i, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_ARRAYITEM);
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "serLines" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines insertNewSerLines(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines)get_store().insert_element_user(SERLINES$18, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "serLines" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines addNewSerLines()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines)get_store().add_element_user(SERLINES$18);
            return target;
        }
    }
    
    /**
     * Removes the ith "serLines" element
     */
    public void removeSerLines(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SERLINES$18, i);
        }
    }
    
    /**
     * Gets the "extLst" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTExtensionList getExtLst()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTExtensionList target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTExtensionList)get_store().find_element_user(EXTLST$20, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * True if has "extLst" element
     */
    public boolean isSetExtLst()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(EXTLST$20) != 0;
        }
    }
    
    /**
     * Sets the "extLst" element
     */
    public void setExtLst(org.openxmlformats.schemas.drawingml.x2006.chart.CTExtensionList extLst)
    {
        generatedSetterHelperImpl(extLst, EXTLST$20, 0, org.apache.xmlbeans.impl.values.XmlObjectBase.KIND_SETTERHELPER_SINGLETON);
    }
    
    /**
     * Appends and returns a new empty "extLst" element
     */
    public org.openxmlformats.schemas.drawingml.x2006.chart.CTExtensionList addNewExtLst()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.drawingml.x2006.chart.CTExtensionList target = null;
            target = (org.openxmlformats.schemas.drawingml.x2006.chart.CTExtensionList)get_store().add_element_user(EXTLST$20);
            return target;
        }
    }
    
    /**
     * Unsets the "extLst" element
     */
    public void unsetExtLst()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(EXTLST$20, 0);
        }
    }
}
