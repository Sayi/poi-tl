/*
 * XML Type:  CT_PageMar
 * Namespace: http://schemas.openxmlformats.org/wordprocessingml/2006/main
 * Java type: org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.wordprocessingml.x2006.main.impl;
/**
 * An XML CT_PageMar(@http://schemas.openxmlformats.org/wordprocessingml/2006/main).
 *
 * This is a complex type.
 */
public class CTPageMarImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar
{
    private static final long serialVersionUID = 1L;
    
    public CTPageMarImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TOP$0 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "top");
    private static final javax.xml.namespace.QName RIGHT$2 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "right");
    private static final javax.xml.namespace.QName BOTTOM$4 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "bottom");
    private static final javax.xml.namespace.QName LEFT$6 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "left");
    private static final javax.xml.namespace.QName HEADER$8 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "header");
    private static final javax.xml.namespace.QName FOOTER$10 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "footer");
    private static final javax.xml.namespace.QName GUTTER$12 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "gutter");
    
    
    /**
     * Gets the "top" attribute
     */
    public java.math.BigInteger getTop()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TOP$0);
            if (target == null)
            {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }
    
    /**
     * Gets (as xml) the "top" attribute
     */
    public org.openxmlformats.schemas.wordprocessingml.x2006.main.STSignedTwipsMeasure xgetTop()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STSignedTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STSignedTwipsMeasure)get_store().find_attribute_user(TOP$0);
            return target;
        }
    }
    
    /**
     * Sets the "top" attribute
     */
    public void setTop(java.math.BigInteger top)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TOP$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TOP$0);
            }
            target.setBigIntegerValue(top);
        }
    }
    
    /**
     * Sets (as xml) the "top" attribute
     */
    public void xsetTop(org.openxmlformats.schemas.wordprocessingml.x2006.main.STSignedTwipsMeasure top)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STSignedTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STSignedTwipsMeasure)get_store().find_attribute_user(TOP$0);
            if (target == null)
            {
                target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STSignedTwipsMeasure)get_store().add_attribute_user(TOP$0);
            }
            target.set(top);
        }
    }
    
    /**
     * Gets the "right" attribute
     */
    public java.math.BigInteger getRight()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(RIGHT$2);
            if (target == null)
            {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }
    
    /**
     * Gets (as xml) the "right" attribute
     */
    public org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure xgetRight()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().find_attribute_user(RIGHT$2);
            return target;
        }
    }
    
    /**
     * Sets the "right" attribute
     */
    public void setRight(java.math.BigInteger right)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(RIGHT$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(RIGHT$2);
            }
            target.setBigIntegerValue(right);
        }
    }
    
    /**
     * Sets (as xml) the "right" attribute
     */
    public void xsetRight(org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure right)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().find_attribute_user(RIGHT$2);
            if (target == null)
            {
                target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().add_attribute_user(RIGHT$2);
            }
            target.set(right);
        }
    }
    
    /**
     * Gets the "bottom" attribute
     */
    public java.math.BigInteger getBottom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(BOTTOM$4);
            if (target == null)
            {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }
    
    /**
     * Gets (as xml) the "bottom" attribute
     */
    public org.openxmlformats.schemas.wordprocessingml.x2006.main.STSignedTwipsMeasure xgetBottom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STSignedTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STSignedTwipsMeasure)get_store().find_attribute_user(BOTTOM$4);
            return target;
        }
    }
    
    /**
     * Sets the "bottom" attribute
     */
    public void setBottom(java.math.BigInteger bottom)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(BOTTOM$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(BOTTOM$4);
            }
            target.setBigIntegerValue(bottom);
        }
    }
    
    /**
     * Sets (as xml) the "bottom" attribute
     */
    public void xsetBottom(org.openxmlformats.schemas.wordprocessingml.x2006.main.STSignedTwipsMeasure bottom)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STSignedTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STSignedTwipsMeasure)get_store().find_attribute_user(BOTTOM$4);
            if (target == null)
            {
                target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STSignedTwipsMeasure)get_store().add_attribute_user(BOTTOM$4);
            }
            target.set(bottom);
        }
    }
    
    /**
     * Gets the "left" attribute
     */
    public java.math.BigInteger getLeft()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LEFT$6);
            if (target == null)
            {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }
    
    /**
     * Gets (as xml) the "left" attribute
     */
    public org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure xgetLeft()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().find_attribute_user(LEFT$6);
            return target;
        }
    }
    
    /**
     * Sets the "left" attribute
     */
    public void setLeft(java.math.BigInteger left)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(LEFT$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(LEFT$6);
            }
            target.setBigIntegerValue(left);
        }
    }
    
    /**
     * Sets (as xml) the "left" attribute
     */
    public void xsetLeft(org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure left)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().find_attribute_user(LEFT$6);
            if (target == null)
            {
                target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().add_attribute_user(LEFT$6);
            }
            target.set(left);
        }
    }
    
    /**
     * Gets the "header" attribute
     */
    public java.math.BigInteger getHeader()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(HEADER$8);
            if (target == null)
            {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }
    
    /**
     * Gets (as xml) the "header" attribute
     */
    public org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure xgetHeader()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().find_attribute_user(HEADER$8);
            return target;
        }
    }
    
    /**
     * Sets the "header" attribute
     */
    public void setHeader(java.math.BigInteger header)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(HEADER$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(HEADER$8);
            }
            target.setBigIntegerValue(header);
        }
    }
    
    /**
     * Sets (as xml) the "header" attribute
     */
    public void xsetHeader(org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure header)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().find_attribute_user(HEADER$8);
            if (target == null)
            {
                target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().add_attribute_user(HEADER$8);
            }
            target.set(header);
        }
    }
    
    /**
     * Gets the "footer" attribute
     */
    public java.math.BigInteger getFooter()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FOOTER$10);
            if (target == null)
            {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }
    
    /**
     * Gets (as xml) the "footer" attribute
     */
    public org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure xgetFooter()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().find_attribute_user(FOOTER$10);
            return target;
        }
    }
    
    /**
     * Sets the "footer" attribute
     */
    public void setFooter(java.math.BigInteger footer)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(FOOTER$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(FOOTER$10);
            }
            target.setBigIntegerValue(footer);
        }
    }
    
    /**
     * Sets (as xml) the "footer" attribute
     */
    public void xsetFooter(org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure footer)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().find_attribute_user(FOOTER$10);
            if (target == null)
            {
                target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().add_attribute_user(FOOTER$10);
            }
            target.set(footer);
        }
    }
    
    /**
     * Gets the "gutter" attribute
     */
    public java.math.BigInteger getGutter()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(GUTTER$12);
            if (target == null)
            {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }
    
    /**
     * Gets (as xml) the "gutter" attribute
     */
    public org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure xgetGutter()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().find_attribute_user(GUTTER$12);
            return target;
        }
    }
    
    /**
     * Sets the "gutter" attribute
     */
    public void setGutter(java.math.BigInteger gutter)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(GUTTER$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(GUTTER$12);
            }
            target.setBigIntegerValue(gutter);
        }
    }
    
    /**
     * Sets (as xml) the "gutter" attribute
     */
    public void xsetGutter(org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure gutter)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().find_attribute_user(GUTTER$12);
            if (target == null)
            {
                target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure)get_store().add_attribute_user(GUTTER$12);
            }
            target.set(gutter);
        }
    }
}
