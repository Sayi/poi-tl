/*
 * XML Type:  CT_DataBinding
 * Namespace: http://schemas.openxmlformats.org/wordprocessingml/2006/main
 * Java type: org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.wordprocessingml.x2006.main.impl;
/**
 * An XML CT_DataBinding(@http://schemas.openxmlformats.org/wordprocessingml/2006/main).
 *
 * This is a complex type.
 */
public class CTDataBindingImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding
{
    private static final long serialVersionUID = 1L;
    
    public CTDataBindingImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PREFIXMAPPINGS$0 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "prefixMappings");
    private static final javax.xml.namespace.QName XPATH$2 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "xpath");
    private static final javax.xml.namespace.QName STOREITEMID$4 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "storeItemID");
    
    
    /**
     * Gets the "prefixMappings" attribute
     */
    public java.lang.String getPrefixMappings()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PREFIXMAPPINGS$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "prefixMappings" attribute
     */
    public org.openxmlformats.schemas.wordprocessingml.x2006.main.STString xgetPrefixMappings()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STString target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STString)get_store().find_attribute_user(PREFIXMAPPINGS$0);
            return target;
        }
    }
    
    /**
     * True if has "prefixMappings" attribute
     */
    public boolean isSetPrefixMappings()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PREFIXMAPPINGS$0) != null;
        }
    }
    
    /**
     * Sets the "prefixMappings" attribute
     */
    public void setPrefixMappings(java.lang.String prefixMappings)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PREFIXMAPPINGS$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PREFIXMAPPINGS$0);
            }
            target.setStringValue(prefixMappings);
        }
    }
    
    /**
     * Sets (as xml) the "prefixMappings" attribute
     */
    public void xsetPrefixMappings(org.openxmlformats.schemas.wordprocessingml.x2006.main.STString prefixMappings)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STString target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STString)get_store().find_attribute_user(PREFIXMAPPINGS$0);
            if (target == null)
            {
                target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STString)get_store().add_attribute_user(PREFIXMAPPINGS$0);
            }
            target.set(prefixMappings);
        }
    }
    
    /**
     * Unsets the "prefixMappings" attribute
     */
    public void unsetPrefixMappings()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PREFIXMAPPINGS$0);
        }
    }
    
    /**
     * Gets the "xpath" attribute
     */
    public java.lang.String getXpath()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(XPATH$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "xpath" attribute
     */
    public org.openxmlformats.schemas.wordprocessingml.x2006.main.STString xgetXpath()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STString target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STString)get_store().find_attribute_user(XPATH$2);
            return target;
        }
    }
    
    /**
     * Sets the "xpath" attribute
     */
    public void setXpath(java.lang.String xpath)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(XPATH$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(XPATH$2);
            }
            target.setStringValue(xpath);
        }
    }
    
    /**
     * Sets (as xml) the "xpath" attribute
     */
    public void xsetXpath(org.openxmlformats.schemas.wordprocessingml.x2006.main.STString xpath)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STString target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STString)get_store().find_attribute_user(XPATH$2);
            if (target == null)
            {
                target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STString)get_store().add_attribute_user(XPATH$2);
            }
            target.set(xpath);
        }
    }
    
    /**
     * Gets the "storeItemID" attribute
     */
    public java.lang.String getStoreItemID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STOREITEMID$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "storeItemID" attribute
     */
    public org.openxmlformats.schemas.wordprocessingml.x2006.main.STString xgetStoreItemID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STString target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STString)get_store().find_attribute_user(STOREITEMID$4);
            return target;
        }
    }
    
    /**
     * Sets the "storeItemID" attribute
     */
    public void setStoreItemID(java.lang.String storeItemID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(STOREITEMID$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(STOREITEMID$4);
            }
            target.setStringValue(storeItemID);
        }
    }
    
    /**
     * Sets (as xml) the "storeItemID" attribute
     */
    public void xsetStoreItemID(org.openxmlformats.schemas.wordprocessingml.x2006.main.STString storeItemID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STString target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STString)get_store().find_attribute_user(STOREITEMID$4);
            if (target == null)
            {
                target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STString)get_store().add_attribute_user(STOREITEMID$4);
            }
            target.set(storeItemID);
        }
    }
}
