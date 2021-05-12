/*
 * XML Type:  CT_PageSz
 * Namespace: http://schemas.openxmlformats.org/wordprocessingml/2006/main
 * Java type: org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.wordprocessingml.x2006.main.impl;
/**
 * An XML CT_PageSz(@http://schemas.openxmlformats.org/wordprocessingml/2006/main).
 *
 * This is a complex type.
 */
public class CTPageSzImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz
{
    private static final long serialVersionUID = 1L;
    
    public CTPageSzImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName W$0 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "w");
    private static final javax.xml.namespace.QName H$2 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "h");
    private static final javax.xml.namespace.QName ORIENT$4 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "orient");
    private static final javax.xml.namespace.QName CODE$6 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "code");
    
    
    /**
     * Gets the "w" attribute
     */
    public java.math.BigInteger getW()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(W$0);
            if (target == null)
            {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }
    
    /**
     * Gets (as xml) the "w" attribute
     */
    public org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure xgetW()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().find_attribute_user(W$0);
            return target;
        }
    }
    
    /**
     * True if has "w" attribute
     */
    public boolean isSetW()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(W$0) != null;
        }
    }
    
    /**
     * Sets the "w" attribute
     */
    public void setW(java.math.BigInteger w)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(W$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(W$0);
            }
            target.setBigIntegerValue(w);
        }
    }
    
    /**
     * Sets (as xml) the "w" attribute
     */
    public void xsetW(org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure w)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().find_attribute_user(W$0);
            if (target == null)
            {
                target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().add_attribute_user(W$0);
            }
            target.set(w);
        }
    }
    
    /**
     * Unsets the "w" attribute
     */
    public void unsetW()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(W$0);
        }
    }
    
    /**
     * Gets the "h" attribute
     */
    public java.math.BigInteger getH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(H$2);
            if (target == null)
            {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }
    
    /**
     * Gets (as xml) the "h" attribute
     */
    public org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure xgetH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().find_attribute_user(H$2);
            return target;
        }
    }
    
    /**
     * True if has "h" attribute
     */
    public boolean isSetH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(H$2) != null;
        }
    }
    
    /**
     * Sets the "h" attribute
     */
    public void setH(java.math.BigInteger h)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(H$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(H$2);
            }
            target.setBigIntegerValue(h);
        }
    }
    
    /**
     * Sets (as xml) the "h" attribute
     */
    public void xsetH(org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure h)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().find_attribute_user(H$2);
            if (target == null)
            {
                target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().add_attribute_user(H$2);
            }
            target.set(h);
        }
    }
    
    /**
     * Unsets the "h" attribute
     */
    public void unsetH()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(H$2);
        }
    }
    
    /**
     * Gets the "orient" attribute
     */
    public org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation.Enum getOrient()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORIENT$4);
            if (target == null)
            {
                return null;
            }
            return (org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "orient" attribute
     */
    public org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation xgetOrient()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation)get_store().find_attribute_user(ORIENT$4);
            return target;
        }
    }
    
    /**
     * True if has "orient" attribute
     */
    public boolean isSetOrient()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ORIENT$4) != null;
        }
    }
    
    /**
     * Sets the "orient" attribute
     */
    public void setOrient(org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation.Enum orient)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ORIENT$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ORIENT$4);
            }
            target.setEnumValue(orient);
        }
    }
    
    /**
     * Sets (as xml) the "orient" attribute
     */
    public void xsetOrient(org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation orient)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation)get_store().find_attribute_user(ORIENT$4);
            if (target == null)
            {
                target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation)get_store().add_attribute_user(ORIENT$4);
            }
            target.set(orient);
        }
    }
    
    /**
     * Unsets the "orient" attribute
     */
    public void unsetOrient()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ORIENT$4);
        }
    }
    
    /**
     * Gets the "code" attribute
     */
    public java.math.BigInteger getCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODE$6);
            if (target == null)
            {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }
    
    /**
     * Gets (as xml) the "code" attribute
     */
    public org.openxmlformats.schemas.wordprocessingml.x2006.main.STDecimalNumber xgetCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STDecimalNumber target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STDecimalNumber)get_store().find_attribute_user(CODE$6);
            return target;
        }
    }
    
    /**
     * True if has "code" attribute
     */
    public boolean isSetCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CODE$6) != null;
        }
    }
    
    /**
     * Sets the "code" attribute
     */
    public void setCode(java.math.BigInteger code)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CODE$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CODE$6);
            }
            target.setBigIntegerValue(code);
        }
    }
    
    /**
     * Sets (as xml) the "code" attribute
     */
    public void xsetCode(org.openxmlformats.schemas.wordprocessingml.x2006.main.STDecimalNumber code)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STDecimalNumber target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STDecimalNumber)get_store().find_attribute_user(CODE$6);
            if (target == null)
            {
                target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STDecimalNumber)get_store().add_attribute_user(CODE$6);
            }
            target.set(code);
        }
    }
    
    /**
     * Unsets the "code" attribute
     */
    public void unsetCode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CODE$6);
        }
    }
}
