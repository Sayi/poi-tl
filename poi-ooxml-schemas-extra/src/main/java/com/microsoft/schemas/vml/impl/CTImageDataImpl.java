/*
 * XML Type:  CT_ImageData
 * Namespace: urn:schemas-microsoft-com:vml
 * Java type: com.microsoft.schemas.vml.CTImageData
 *
 * Automatically generated - do not modify.
 */
package com.microsoft.schemas.vml.impl;
/**
 * An XML CT_ImageData(@urn:schemas-microsoft-com:vml).
 *
 * This is a complex type.
 */
public class CTImageDataImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.microsoft.schemas.vml.CTImageData
{
    private static final long serialVersionUID = 1L;
    
    public CTImageDataImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ID$0 = 
        new javax.xml.namespace.QName("", "id");
    private static final javax.xml.namespace.QName SRC$2 = 
        new javax.xml.namespace.QName("", "src");
    private static final javax.xml.namespace.QName CROPLEFT$4 = 
        new javax.xml.namespace.QName("", "cropleft");
    private static final javax.xml.namespace.QName CROPTOP$6 = 
        new javax.xml.namespace.QName("", "croptop");
    private static final javax.xml.namespace.QName CROPRIGHT$8 = 
        new javax.xml.namespace.QName("", "cropright");
    private static final javax.xml.namespace.QName CROPBOTTOM$10 = 
        new javax.xml.namespace.QName("", "cropbottom");
    private static final javax.xml.namespace.QName GAIN$12 = 
        new javax.xml.namespace.QName("", "gain");
    private static final javax.xml.namespace.QName BLACKLEVEL$14 = 
        new javax.xml.namespace.QName("", "blacklevel");
    private static final javax.xml.namespace.QName GAMMA$16 = 
        new javax.xml.namespace.QName("", "gamma");
    private static final javax.xml.namespace.QName GRAYSCALE$18 = 
        new javax.xml.namespace.QName("", "grayscale");
    private static final javax.xml.namespace.QName BILEVEL$20 = 
        new javax.xml.namespace.QName("", "bilevel");
    private static final javax.xml.namespace.QName CHROMAKEY$22 = 
        new javax.xml.namespace.QName("", "chromakey");
    private static final javax.xml.namespace.QName EMBOSSCOLOR$24 = 
        new javax.xml.namespace.QName("", "embosscolor");
    private static final javax.xml.namespace.QName RECOLORTARGET$26 = 
        new javax.xml.namespace.QName("", "recolortarget");
    private static final javax.xml.namespace.QName HREF$28 = 
        new javax.xml.namespace.QName("urn:schemas-microsoft-com:office:office", "href");
    private static final javax.xml.namespace.QName ALTHREF$30 = 
        new javax.xml.namespace.QName("urn:schemas-microsoft-com:office:office", "althref");
    private static final javax.xml.namespace.QName TITLE$32 = 
        new javax.xml.namespace.QName("urn:schemas-microsoft-com:office:office", "title");
    private static final javax.xml.namespace.QName OLEID$34 = 
        new javax.xml.namespace.QName("urn:schemas-microsoft-com:office:office", "oleid");
    private static final javax.xml.namespace.QName DETECTMOUSECLICK$36 = 
        new javax.xml.namespace.QName("urn:schemas-microsoft-com:office:office", "detectmouseclick");
    private static final javax.xml.namespace.QName MOVIE$38 = 
        new javax.xml.namespace.QName("urn:schemas-microsoft-com:office:office", "movie");
    private static final javax.xml.namespace.QName RELID$40 = 
        new javax.xml.namespace.QName("urn:schemas-microsoft-com:office:office", "relid");
    private static final javax.xml.namespace.QName ID2$42 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/officeDocument/2006/relationships", "id");
    private static final javax.xml.namespace.QName PICT$44 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/officeDocument/2006/relationships", "pict");
    private static final javax.xml.namespace.QName HREF2$46 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/officeDocument/2006/relationships", "href");
    
    
    /**
     * Gets the "id" attribute
     */
    public java.lang.String getId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ID$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "id" attribute
     */
    public org.apache.xmlbeans.XmlString xgetId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ID$0);
            return target;
        }
    }
    
    /**
     * True if has "id" attribute
     */
    public boolean isSetId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ID$0) != null;
        }
    }
    
    /**
     * Sets the "id" attribute
     */
    public void setId(java.lang.String id)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ID$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ID$0);
            }
            target.setStringValue(id);
        }
    }
    
    /**
     * Sets (as xml) the "id" attribute
     */
    public void xsetId(org.apache.xmlbeans.XmlString id)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ID$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(ID$0);
            }
            target.set(id);
        }
    }
    
    /**
     * Unsets the "id" attribute
     */
    public void unsetId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ID$0);
        }
    }
    
    /**
     * Gets the "src" attribute
     */
    public java.lang.String getSrc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SRC$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "src" attribute
     */
    public org.apache.xmlbeans.XmlString xgetSrc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SRC$2);
            return target;
        }
    }
    
    /**
     * True if has "src" attribute
     */
    public boolean isSetSrc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(SRC$2) != null;
        }
    }
    
    /**
     * Sets the "src" attribute
     */
    public void setSrc(java.lang.String src)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SRC$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SRC$2);
            }
            target.setStringValue(src);
        }
    }
    
    /**
     * Sets (as xml) the "src" attribute
     */
    public void xsetSrc(org.apache.xmlbeans.XmlString src)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SRC$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SRC$2);
            }
            target.set(src);
        }
    }
    
    /**
     * Unsets the "src" attribute
     */
    public void unsetSrc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(SRC$2);
        }
    }
    
    /**
     * Gets the "cropleft" attribute
     */
    public java.lang.String getCropleft()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CROPLEFT$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "cropleft" attribute
     */
    public org.apache.xmlbeans.XmlString xgetCropleft()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CROPLEFT$4);
            return target;
        }
    }
    
    /**
     * True if has "cropleft" attribute
     */
    public boolean isSetCropleft()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CROPLEFT$4) != null;
        }
    }
    
    /**
     * Sets the "cropleft" attribute
     */
    public void setCropleft(java.lang.String cropleft)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CROPLEFT$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CROPLEFT$4);
            }
            target.setStringValue(cropleft);
        }
    }
    
    /**
     * Sets (as xml) the "cropleft" attribute
     */
    public void xsetCropleft(org.apache.xmlbeans.XmlString cropleft)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CROPLEFT$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CROPLEFT$4);
            }
            target.set(cropleft);
        }
    }
    
    /**
     * Unsets the "cropleft" attribute
     */
    public void unsetCropleft()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CROPLEFT$4);
        }
    }
    
    /**
     * Gets the "croptop" attribute
     */
    public java.lang.String getCroptop()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CROPTOP$6);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "croptop" attribute
     */
    public org.apache.xmlbeans.XmlString xgetCroptop()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CROPTOP$6);
            return target;
        }
    }
    
    /**
     * True if has "croptop" attribute
     */
    public boolean isSetCroptop()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CROPTOP$6) != null;
        }
    }
    
    /**
     * Sets the "croptop" attribute
     */
    public void setCroptop(java.lang.String croptop)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CROPTOP$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CROPTOP$6);
            }
            target.setStringValue(croptop);
        }
    }
    
    /**
     * Sets (as xml) the "croptop" attribute
     */
    public void xsetCroptop(org.apache.xmlbeans.XmlString croptop)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CROPTOP$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CROPTOP$6);
            }
            target.set(croptop);
        }
    }
    
    /**
     * Unsets the "croptop" attribute
     */
    public void unsetCroptop()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CROPTOP$6);
        }
    }
    
    /**
     * Gets the "cropright" attribute
     */
    public java.lang.String getCropright()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CROPRIGHT$8);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "cropright" attribute
     */
    public org.apache.xmlbeans.XmlString xgetCropright()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CROPRIGHT$8);
            return target;
        }
    }
    
    /**
     * True if has "cropright" attribute
     */
    public boolean isSetCropright()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CROPRIGHT$8) != null;
        }
    }
    
    /**
     * Sets the "cropright" attribute
     */
    public void setCropright(java.lang.String cropright)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CROPRIGHT$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CROPRIGHT$8);
            }
            target.setStringValue(cropright);
        }
    }
    
    /**
     * Sets (as xml) the "cropright" attribute
     */
    public void xsetCropright(org.apache.xmlbeans.XmlString cropright)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CROPRIGHT$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CROPRIGHT$8);
            }
            target.set(cropright);
        }
    }
    
    /**
     * Unsets the "cropright" attribute
     */
    public void unsetCropright()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CROPRIGHT$8);
        }
    }
    
    /**
     * Gets the "cropbottom" attribute
     */
    public java.lang.String getCropbottom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CROPBOTTOM$10);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "cropbottom" attribute
     */
    public org.apache.xmlbeans.XmlString xgetCropbottom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CROPBOTTOM$10);
            return target;
        }
    }
    
    /**
     * True if has "cropbottom" attribute
     */
    public boolean isSetCropbottom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CROPBOTTOM$10) != null;
        }
    }
    
    /**
     * Sets the "cropbottom" attribute
     */
    public void setCropbottom(java.lang.String cropbottom)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CROPBOTTOM$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CROPBOTTOM$10);
            }
            target.setStringValue(cropbottom);
        }
    }
    
    /**
     * Sets (as xml) the "cropbottom" attribute
     */
    public void xsetCropbottom(org.apache.xmlbeans.XmlString cropbottom)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(CROPBOTTOM$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(CROPBOTTOM$10);
            }
            target.set(cropbottom);
        }
    }
    
    /**
     * Unsets the "cropbottom" attribute
     */
    public void unsetCropbottom()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CROPBOTTOM$10);
        }
    }
    
    /**
     * Gets the "gain" attribute
     */
    public java.lang.String getGain()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(GAIN$12);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "gain" attribute
     */
    public org.apache.xmlbeans.XmlString xgetGain()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(GAIN$12);
            return target;
        }
    }
    
    /**
     * True if has "gain" attribute
     */
    public boolean isSetGain()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(GAIN$12) != null;
        }
    }
    
    /**
     * Sets the "gain" attribute
     */
    public void setGain(java.lang.String gain)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(GAIN$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(GAIN$12);
            }
            target.setStringValue(gain);
        }
    }
    
    /**
     * Sets (as xml) the "gain" attribute
     */
    public void xsetGain(org.apache.xmlbeans.XmlString gain)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(GAIN$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(GAIN$12);
            }
            target.set(gain);
        }
    }
    
    /**
     * Unsets the "gain" attribute
     */
    public void unsetGain()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(GAIN$12);
        }
    }
    
    /**
     * Gets the "blacklevel" attribute
     */
    public java.lang.String getBlacklevel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(BLACKLEVEL$14);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "blacklevel" attribute
     */
    public org.apache.xmlbeans.XmlString xgetBlacklevel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(BLACKLEVEL$14);
            return target;
        }
    }
    
    /**
     * True if has "blacklevel" attribute
     */
    public boolean isSetBlacklevel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(BLACKLEVEL$14) != null;
        }
    }
    
    /**
     * Sets the "blacklevel" attribute
     */
    public void setBlacklevel(java.lang.String blacklevel)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(BLACKLEVEL$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(BLACKLEVEL$14);
            }
            target.setStringValue(blacklevel);
        }
    }
    
    /**
     * Sets (as xml) the "blacklevel" attribute
     */
    public void xsetBlacklevel(org.apache.xmlbeans.XmlString blacklevel)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(BLACKLEVEL$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(BLACKLEVEL$14);
            }
            target.set(blacklevel);
        }
    }
    
    /**
     * Unsets the "blacklevel" attribute
     */
    public void unsetBlacklevel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(BLACKLEVEL$14);
        }
    }
    
    /**
     * Gets the "gamma" attribute
     */
    public java.lang.String getGamma()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(GAMMA$16);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "gamma" attribute
     */
    public org.apache.xmlbeans.XmlString xgetGamma()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(GAMMA$16);
            return target;
        }
    }
    
    /**
     * True if has "gamma" attribute
     */
    public boolean isSetGamma()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(GAMMA$16) != null;
        }
    }
    
    /**
     * Sets the "gamma" attribute
     */
    public void setGamma(java.lang.String gamma)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(GAMMA$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(GAMMA$16);
            }
            target.setStringValue(gamma);
        }
    }
    
    /**
     * Sets (as xml) the "gamma" attribute
     */
    public void xsetGamma(org.apache.xmlbeans.XmlString gamma)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(GAMMA$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(GAMMA$16);
            }
            target.set(gamma);
        }
    }
    
    /**
     * Unsets the "gamma" attribute
     */
    public void unsetGamma()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(GAMMA$16);
        }
    }
    
    /**
     * Gets the "grayscale" attribute
     */
    public com.microsoft.schemas.vml.STTrueFalse.Enum getGrayscale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(GRAYSCALE$18);
            if (target == null)
            {
                return null;
            }
            return (com.microsoft.schemas.vml.STTrueFalse.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "grayscale" attribute
     */
    public com.microsoft.schemas.vml.STTrueFalse xgetGrayscale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.vml.STTrueFalse target = null;
            target = (com.microsoft.schemas.vml.STTrueFalse)get_store().find_attribute_user(GRAYSCALE$18);
            return target;
        }
    }
    
    /**
     * True if has "grayscale" attribute
     */
    public boolean isSetGrayscale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(GRAYSCALE$18) != null;
        }
    }
    
    /**
     * Sets the "grayscale" attribute
     */
    public void setGrayscale(com.microsoft.schemas.vml.STTrueFalse.Enum grayscale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(GRAYSCALE$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(GRAYSCALE$18);
            }
            target.setEnumValue(grayscale);
        }
    }
    
    /**
     * Sets (as xml) the "grayscale" attribute
     */
    public void xsetGrayscale(com.microsoft.schemas.vml.STTrueFalse grayscale)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.vml.STTrueFalse target = null;
            target = (com.microsoft.schemas.vml.STTrueFalse)get_store().find_attribute_user(GRAYSCALE$18);
            if (target == null)
            {
                target = (com.microsoft.schemas.vml.STTrueFalse)get_store().add_attribute_user(GRAYSCALE$18);
            }
            target.set(grayscale);
        }
    }
    
    /**
     * Unsets the "grayscale" attribute
     */
    public void unsetGrayscale()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(GRAYSCALE$18);
        }
    }
    
    /**
     * Gets the "bilevel" attribute
     */
    public com.microsoft.schemas.vml.STTrueFalse.Enum getBilevel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(BILEVEL$20);
            if (target == null)
            {
                return null;
            }
            return (com.microsoft.schemas.vml.STTrueFalse.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "bilevel" attribute
     */
    public com.microsoft.schemas.vml.STTrueFalse xgetBilevel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.vml.STTrueFalse target = null;
            target = (com.microsoft.schemas.vml.STTrueFalse)get_store().find_attribute_user(BILEVEL$20);
            return target;
        }
    }
    
    /**
     * True if has "bilevel" attribute
     */
    public boolean isSetBilevel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(BILEVEL$20) != null;
        }
    }
    
    /**
     * Sets the "bilevel" attribute
     */
    public void setBilevel(com.microsoft.schemas.vml.STTrueFalse.Enum bilevel)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(BILEVEL$20);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(BILEVEL$20);
            }
            target.setEnumValue(bilevel);
        }
    }
    
    /**
     * Sets (as xml) the "bilevel" attribute
     */
    public void xsetBilevel(com.microsoft.schemas.vml.STTrueFalse bilevel)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.vml.STTrueFalse target = null;
            target = (com.microsoft.schemas.vml.STTrueFalse)get_store().find_attribute_user(BILEVEL$20);
            if (target == null)
            {
                target = (com.microsoft.schemas.vml.STTrueFalse)get_store().add_attribute_user(BILEVEL$20);
            }
            target.set(bilevel);
        }
    }
    
    /**
     * Unsets the "bilevel" attribute
     */
    public void unsetBilevel()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(BILEVEL$20);
        }
    }
    
    /**
     * Gets the "chromakey" attribute
     */
    public java.lang.String getChromakey()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CHROMAKEY$22);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "chromakey" attribute
     */
    public com.microsoft.schemas.vml.STColorType xgetChromakey()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.vml.STColorType target = null;
            target = (com.microsoft.schemas.vml.STColorType)get_store().find_attribute_user(CHROMAKEY$22);
            return target;
        }
    }
    
    /**
     * True if has "chromakey" attribute
     */
    public boolean isSetChromakey()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(CHROMAKEY$22) != null;
        }
    }
    
    /**
     * Sets the "chromakey" attribute
     */
    public void setChromakey(java.lang.String chromakey)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(CHROMAKEY$22);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(CHROMAKEY$22);
            }
            target.setStringValue(chromakey);
        }
    }
    
    /**
     * Sets (as xml) the "chromakey" attribute
     */
    public void xsetChromakey(com.microsoft.schemas.vml.STColorType chromakey)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.vml.STColorType target = null;
            target = (com.microsoft.schemas.vml.STColorType)get_store().find_attribute_user(CHROMAKEY$22);
            if (target == null)
            {
                target = (com.microsoft.schemas.vml.STColorType)get_store().add_attribute_user(CHROMAKEY$22);
            }
            target.set(chromakey);
        }
    }
    
    /**
     * Unsets the "chromakey" attribute
     */
    public void unsetChromakey()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(CHROMAKEY$22);
        }
    }
    
    /**
     * Gets the "embosscolor" attribute
     */
    public java.lang.String getEmbosscolor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EMBOSSCOLOR$24);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "embosscolor" attribute
     */
    public com.microsoft.schemas.vml.STColorType xgetEmbosscolor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.vml.STColorType target = null;
            target = (com.microsoft.schemas.vml.STColorType)get_store().find_attribute_user(EMBOSSCOLOR$24);
            return target;
        }
    }
    
    /**
     * True if has "embosscolor" attribute
     */
    public boolean isSetEmbosscolor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(EMBOSSCOLOR$24) != null;
        }
    }
    
    /**
     * Sets the "embosscolor" attribute
     */
    public void setEmbosscolor(java.lang.String embosscolor)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(EMBOSSCOLOR$24);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(EMBOSSCOLOR$24);
            }
            target.setStringValue(embosscolor);
        }
    }
    
    /**
     * Sets (as xml) the "embosscolor" attribute
     */
    public void xsetEmbosscolor(com.microsoft.schemas.vml.STColorType embosscolor)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.vml.STColorType target = null;
            target = (com.microsoft.schemas.vml.STColorType)get_store().find_attribute_user(EMBOSSCOLOR$24);
            if (target == null)
            {
                target = (com.microsoft.schemas.vml.STColorType)get_store().add_attribute_user(EMBOSSCOLOR$24);
            }
            target.set(embosscolor);
        }
    }
    
    /**
     * Unsets the "embosscolor" attribute
     */
    public void unsetEmbosscolor()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(EMBOSSCOLOR$24);
        }
    }
    
    /**
     * Gets the "recolortarget" attribute
     */
    public java.lang.String getRecolortarget()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(RECOLORTARGET$26);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "recolortarget" attribute
     */
    public com.microsoft.schemas.vml.STColorType xgetRecolortarget()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.vml.STColorType target = null;
            target = (com.microsoft.schemas.vml.STColorType)get_store().find_attribute_user(RECOLORTARGET$26);
            return target;
        }
    }
    
    /**
     * True if has "recolortarget" attribute
     */
    public boolean isSetRecolortarget()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(RECOLORTARGET$26) != null;
        }
    }
    
    /**
     * Sets the "recolortarget" attribute
     */
    public void setRecolortarget(java.lang.String recolortarget)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(RECOLORTARGET$26);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(RECOLORTARGET$26);
            }
            target.setStringValue(recolortarget);
        }
    }
    
    /**
     * Sets (as xml) the "recolortarget" attribute
     */
    public void xsetRecolortarget(com.microsoft.schemas.vml.STColorType recolortarget)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.vml.STColorType target = null;
            target = (com.microsoft.schemas.vml.STColorType)get_store().find_attribute_user(RECOLORTARGET$26);
            if (target == null)
            {
                target = (com.microsoft.schemas.vml.STColorType)get_store().add_attribute_user(RECOLORTARGET$26);
            }
            target.set(recolortarget);
        }
    }
    
    /**
     * Unsets the "recolortarget" attribute
     */
    public void unsetRecolortarget()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(RECOLORTARGET$26);
        }
    }
    
    /**
     * Gets the "href" attribute
     */
    public java.lang.String getHref()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(HREF$28);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "href" attribute
     */
    public org.apache.xmlbeans.XmlString xgetHref()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(HREF$28);
            return target;
        }
    }
    
    /**
     * True if has "href" attribute
     */
    public boolean isSetHref()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(HREF$28) != null;
        }
    }
    
    /**
     * Sets the "href" attribute
     */
    public void setHref(java.lang.String href)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(HREF$28);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(HREF$28);
            }
            target.setStringValue(href);
        }
    }
    
    /**
     * Sets (as xml) the "href" attribute
     */
    public void xsetHref(org.apache.xmlbeans.XmlString href)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(HREF$28);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(HREF$28);
            }
            target.set(href);
        }
    }
    
    /**
     * Unsets the "href" attribute
     */
    public void unsetHref()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(HREF$28);
        }
    }
    
    /**
     * Gets the "althref" attribute
     */
    public java.lang.String getAlthref()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ALTHREF$30);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "althref" attribute
     */
    public org.apache.xmlbeans.XmlString xgetAlthref()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ALTHREF$30);
            return target;
        }
    }
    
    /**
     * True if has "althref" attribute
     */
    public boolean isSetAlthref()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ALTHREF$30) != null;
        }
    }
    
    /**
     * Sets the "althref" attribute
     */
    public void setAlthref(java.lang.String althref)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ALTHREF$30);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ALTHREF$30);
            }
            target.setStringValue(althref);
        }
    }
    
    /**
     * Sets (as xml) the "althref" attribute
     */
    public void xsetAlthref(org.apache.xmlbeans.XmlString althref)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(ALTHREF$30);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(ALTHREF$30);
            }
            target.set(althref);
        }
    }
    
    /**
     * Unsets the "althref" attribute
     */
    public void unsetAlthref()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ALTHREF$30);
        }
    }
    
    /**
     * Gets the "title" attribute
     */
    public java.lang.String getTitle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TITLE$32);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "title" attribute
     */
    public org.apache.xmlbeans.XmlString xgetTitle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TITLE$32);
            return target;
        }
    }
    
    /**
     * True if has "title" attribute
     */
    public boolean isSetTitle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(TITLE$32) != null;
        }
    }
    
    /**
     * Sets the "title" attribute
     */
    public void setTitle(java.lang.String title)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TITLE$32);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TITLE$32);
            }
            target.setStringValue(title);
        }
    }
    
    /**
     * Sets (as xml) the "title" attribute
     */
    public void xsetTitle(org.apache.xmlbeans.XmlString title)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(TITLE$32);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(TITLE$32);
            }
            target.set(title);
        }
    }
    
    /**
     * Unsets the "title" attribute
     */
    public void unsetTitle()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(TITLE$32);
        }
    }
    
    /**
     * Gets the "oleid" attribute
     */
    public float getOleid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OLEID$34);
            if (target == null)
            {
                return 0.0f;
            }
            return target.getFloatValue();
        }
    }
    
    /**
     * Gets (as xml) the "oleid" attribute
     */
    public org.apache.xmlbeans.XmlFloat xgetOleid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_attribute_user(OLEID$34);
            return target;
        }
    }
    
    /**
     * True if has "oleid" attribute
     */
    public boolean isSetOleid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(OLEID$34) != null;
        }
    }
    
    /**
     * Sets the "oleid" attribute
     */
    public void setOleid(float oleid)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OLEID$34);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(OLEID$34);
            }
            target.setFloatValue(oleid);
        }
    }
    
    /**
     * Sets (as xml) the "oleid" attribute
     */
    public void xsetOleid(org.apache.xmlbeans.XmlFloat oleid)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_attribute_user(OLEID$34);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlFloat)get_store().add_attribute_user(OLEID$34);
            }
            target.set(oleid);
        }
    }
    
    /**
     * Unsets the "oleid" attribute
     */
    public void unsetOleid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(OLEID$34);
        }
    }
    
    /**
     * Gets the "detectmouseclick" attribute
     */
    public com.microsoft.schemas.office.office.STTrueFalse.Enum getDetectmouseclick()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DETECTMOUSECLICK$36);
            if (target == null)
            {
                return null;
            }
            return (com.microsoft.schemas.office.office.STTrueFalse.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "detectmouseclick" attribute
     */
    public com.microsoft.schemas.office.office.STTrueFalse xgetDetectmouseclick()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.office.office.STTrueFalse target = null;
            target = (com.microsoft.schemas.office.office.STTrueFalse)get_store().find_attribute_user(DETECTMOUSECLICK$36);
            return target;
        }
    }
    
    /**
     * True if has "detectmouseclick" attribute
     */
    public boolean isSetDetectmouseclick()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DETECTMOUSECLICK$36) != null;
        }
    }
    
    /**
     * Sets the "detectmouseclick" attribute
     */
    public void setDetectmouseclick(com.microsoft.schemas.office.office.STTrueFalse.Enum detectmouseclick)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DETECTMOUSECLICK$36);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DETECTMOUSECLICK$36);
            }
            target.setEnumValue(detectmouseclick);
        }
    }
    
    /**
     * Sets (as xml) the "detectmouseclick" attribute
     */
    public void xsetDetectmouseclick(com.microsoft.schemas.office.office.STTrueFalse detectmouseclick)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.office.office.STTrueFalse target = null;
            target = (com.microsoft.schemas.office.office.STTrueFalse)get_store().find_attribute_user(DETECTMOUSECLICK$36);
            if (target == null)
            {
                target = (com.microsoft.schemas.office.office.STTrueFalse)get_store().add_attribute_user(DETECTMOUSECLICK$36);
            }
            target.set(detectmouseclick);
        }
    }
    
    /**
     * Unsets the "detectmouseclick" attribute
     */
    public void unsetDetectmouseclick()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DETECTMOUSECLICK$36);
        }
    }
    
    /**
     * Gets the "movie" attribute
     */
    public float getMovie()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MOVIE$38);
            if (target == null)
            {
                return 0.0f;
            }
            return target.getFloatValue();
        }
    }
    
    /**
     * Gets (as xml) the "movie" attribute
     */
    public org.apache.xmlbeans.XmlFloat xgetMovie()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_attribute_user(MOVIE$38);
            return target;
        }
    }
    
    /**
     * True if has "movie" attribute
     */
    public boolean isSetMovie()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(MOVIE$38) != null;
        }
    }
    
    /**
     * Sets the "movie" attribute
     */
    public void setMovie(float movie)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(MOVIE$38);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(MOVIE$38);
            }
            target.setFloatValue(movie);
        }
    }
    
    /**
     * Sets (as xml) the "movie" attribute
     */
    public void xsetMovie(org.apache.xmlbeans.XmlFloat movie)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_attribute_user(MOVIE$38);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlFloat)get_store().add_attribute_user(MOVIE$38);
            }
            target.set(movie);
        }
    }
    
    /**
     * Unsets the "movie" attribute
     */
    public void unsetMovie()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(MOVIE$38);
        }
    }
    
    /**
     * Gets the "relid" attribute
     */
    public java.lang.String getRelid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(RELID$40);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "relid" attribute
     */
    public com.microsoft.schemas.office.office.STRelationshipId xgetRelid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.office.office.STRelationshipId target = null;
            target = (com.microsoft.schemas.office.office.STRelationshipId)get_store().find_attribute_user(RELID$40);
            return target;
        }
    }
    
    /**
     * True if has "relid" attribute
     */
    public boolean isSetRelid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(RELID$40) != null;
        }
    }
    
    /**
     * Sets the "relid" attribute
     */
    public void setRelid(java.lang.String relid)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(RELID$40);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(RELID$40);
            }
            target.setStringValue(relid);
        }
    }
    
    /**
     * Sets (as xml) the "relid" attribute
     */
    public void xsetRelid(com.microsoft.schemas.office.office.STRelationshipId relid)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.office.office.STRelationshipId target = null;
            target = (com.microsoft.schemas.office.office.STRelationshipId)get_store().find_attribute_user(RELID$40);
            if (target == null)
            {
                target = (com.microsoft.schemas.office.office.STRelationshipId)get_store().add_attribute_user(RELID$40);
            }
            target.set(relid);
        }
    }
    
    /**
     * Unsets the "relid" attribute
     */
    public void unsetRelid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(RELID$40);
        }
    }
    
    /**
     * Gets the "id" attribute
     */
    public java.lang.String getId2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ID2$42);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "id" attribute
     */
    public org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId xgetId2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().find_attribute_user(ID2$42);
            return target;
        }
    }
    
    /**
     * True if has "id" attribute
     */
    public boolean isSetId2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(ID2$42) != null;
        }
    }
    
    /**
     * Sets the "id" attribute
     */
    public void setId2(java.lang.String id2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ID2$42);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ID2$42);
            }
            target.setStringValue(id2);
        }
    }
    
    /**
     * Sets (as xml) the "id" attribute
     */
    public void xsetId2(org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId id2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().find_attribute_user(ID2$42);
            if (target == null)
            {
                target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().add_attribute_user(ID2$42);
            }
            target.set(id2);
        }
    }
    
    /**
     * Unsets the "id" attribute
     */
    public void unsetId2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(ID2$42);
        }
    }
    
    /**
     * Gets the "pict" attribute
     */
    public java.lang.String getPict()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICT$44);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "pict" attribute
     */
    public org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId xgetPict()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().find_attribute_user(PICT$44);
            return target;
        }
    }
    
    /**
     * True if has "pict" attribute
     */
    public boolean isSetPict()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PICT$44) != null;
        }
    }
    
    /**
     * Sets the "pict" attribute
     */
    public void setPict(java.lang.String pict)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PICT$44);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PICT$44);
            }
            target.setStringValue(pict);
        }
    }
    
    /**
     * Sets (as xml) the "pict" attribute
     */
    public void xsetPict(org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId pict)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().find_attribute_user(PICT$44);
            if (target == null)
            {
                target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().add_attribute_user(PICT$44);
            }
            target.set(pict);
        }
    }
    
    /**
     * Unsets the "pict" attribute
     */
    public void unsetPict()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PICT$44);
        }
    }
    
    /**
     * Gets the "href" attribute
     */
    public java.lang.String getHref2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(HREF2$46);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "href" attribute
     */
    public org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId xgetHref2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().find_attribute_user(HREF2$46);
            return target;
        }
    }
    
    /**
     * True if has "href" attribute
     */
    public boolean isSetHref2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(HREF2$46) != null;
        }
    }
    
    /**
     * Sets the "href" attribute
     */
    public void setHref2(java.lang.String href2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(HREF2$46);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(HREF2$46);
            }
            target.setStringValue(href2);
        }
    }
    
    /**
     * Sets (as xml) the "href" attribute
     */
    public void xsetHref2(org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId href2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().find_attribute_user(HREF2$46);
            if (target == null)
            {
                target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().add_attribute_user(HREF2$46);
            }
            target.set(href2);
        }
    }
    
    /**
     * Unsets the "href" attribute
     */
    public void unsetHref2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(HREF2$46);
        }
    }
}
