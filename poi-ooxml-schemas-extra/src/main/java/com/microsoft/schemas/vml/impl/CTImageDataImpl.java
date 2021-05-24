/*
 * XML Type:  CT_ImageData
 * Namespace: urn:schemas-microsoft-com:vml
 * Java type: com.microsoft.schemas.vml.CTImageData
 *
 * Automatically generated - do not modify.
 */
package com.microsoft.schemas.vml.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.QNameSet;

/**
 * An XML CT_ImageData(@urn:schemas-microsoft-com:vml).
 *
 * This is a complex type.
 */
public class CTImageDataImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.microsoft.schemas.vml.CTImageData {
    private static final long serialVersionUID = 1L;

    public CTImageDataImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    private static final QName[] PROPERTY_QNAME = {
        new QName("", "id"),
        new QName("", "src"),
        new QName("", "cropleft"),
        new QName("", "croptop"),
        new QName("", "cropright"),
        new QName("", "cropbottom"),
        new QName("", "gain"),
        new QName("", "blacklevel"),
        new QName("", "gamma"),
        new QName("", "grayscale"),
        new QName("", "bilevel"),
        new QName("", "chromakey"),
        new QName("", "embosscolor"),
        new QName("", "recolortarget"),
        new QName("urn:schemas-microsoft-com:office:office", "href"),
        new QName("urn:schemas-microsoft-com:office:office", "althref"),
        new QName("urn:schemas-microsoft-com:office:office", "title"),
        new QName("urn:schemas-microsoft-com:office:office", "oleid"),
        new QName("urn:schemas-microsoft-com:office:office", "detectmouseclick"),
        new QName("urn:schemas-microsoft-com:office:office", "movie"),
        new QName("urn:schemas-microsoft-com:office:office", "relid"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/relationships", "id"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/relationships", "pict"),
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/relationships", "href"),
    };


    /**
     * Gets the "id" attribute
     */
    @Override
    public java.lang.String getId() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[0]);
            if (target == null) {
                return null;
            }
            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "id" attribute
     */
    @Override
    public org.apache.xmlbeans.XmlString xgetId() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[0]);
            return target;
        }
    }

    /**
     * True if has "id" attribute
     */
    @Override
    public boolean isSetId() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[0]) != null;
        }
    }

    /**
     * Sets the "id" attribute
     */
    @Override
    public void setId(java.lang.String id) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[0]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[0]);
            }
            target.setStringValue(id);
        }
    }

    /**
     * Sets (as xml) the "id" attribute
     */
    @Override
    public void xsetId(org.apache.xmlbeans.XmlString id) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[0]);
            if (target == null) {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PROPERTY_QNAME[0]);
            }
            target.set(id);
        }
    }

    /**
     * Unsets the "id" attribute
     */
    @Override
    public void unsetId() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[0]);
        }
    }

    /**
     * Gets the "src" attribute
     */
    @Override
    public java.lang.String getSrc() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[1]);
            if (target == null) {
                return null;
            }
            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "src" attribute
     */
    @Override
    public org.apache.xmlbeans.XmlString xgetSrc() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[1]);
            return target;
        }
    }

    /**
     * True if has "src" attribute
     */
    @Override
    public boolean isSetSrc() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[1]) != null;
        }
    }

    /**
     * Sets the "src" attribute
     */
    @Override
    public void setSrc(java.lang.String src) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[1]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[1]);
            }
            target.setStringValue(src);
        }
    }

    /**
     * Sets (as xml) the "src" attribute
     */
    @Override
    public void xsetSrc(org.apache.xmlbeans.XmlString src) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[1]);
            if (target == null) {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PROPERTY_QNAME[1]);
            }
            target.set(src);
        }
    }

    /**
     * Unsets the "src" attribute
     */
    @Override
    public void unsetSrc() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[1]);
        }
    }

    /**
     * Gets the "cropleft" attribute
     */
    @Override
    public java.lang.String getCropleft() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[2]);
            if (target == null) {
                return null;
            }
            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "cropleft" attribute
     */
    @Override
    public org.apache.xmlbeans.XmlString xgetCropleft() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[2]);
            return target;
        }
    }

    /**
     * True if has "cropleft" attribute
     */
    @Override
    public boolean isSetCropleft() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[2]) != null;
        }
    }

    /**
     * Sets the "cropleft" attribute
     */
    @Override
    public void setCropleft(java.lang.String cropleft) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[2]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[2]);
            }
            target.setStringValue(cropleft);
        }
    }

    /**
     * Sets (as xml) the "cropleft" attribute
     */
    @Override
    public void xsetCropleft(org.apache.xmlbeans.XmlString cropleft) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[2]);
            if (target == null) {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PROPERTY_QNAME[2]);
            }
            target.set(cropleft);
        }
    }

    /**
     * Unsets the "cropleft" attribute
     */
    @Override
    public void unsetCropleft() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[2]);
        }
    }

    /**
     * Gets the "croptop" attribute
     */
    @Override
    public java.lang.String getCroptop() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[3]);
            if (target == null) {
                return null;
            }
            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "croptop" attribute
     */
    @Override
    public org.apache.xmlbeans.XmlString xgetCroptop() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[3]);
            return target;
        }
    }

    /**
     * True if has "croptop" attribute
     */
    @Override
    public boolean isSetCroptop() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[3]) != null;
        }
    }

    /**
     * Sets the "croptop" attribute
     */
    @Override
    public void setCroptop(java.lang.String croptop) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[3]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[3]);
            }
            target.setStringValue(croptop);
        }
    }

    /**
     * Sets (as xml) the "croptop" attribute
     */
    @Override
    public void xsetCroptop(org.apache.xmlbeans.XmlString croptop) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[3]);
            if (target == null) {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PROPERTY_QNAME[3]);
            }
            target.set(croptop);
        }
    }

    /**
     * Unsets the "croptop" attribute
     */
    @Override
    public void unsetCroptop() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[3]);
        }
    }

    /**
     * Gets the "cropright" attribute
     */
    @Override
    public java.lang.String getCropright() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[4]);
            if (target == null) {
                return null;
            }
            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "cropright" attribute
     */
    @Override
    public org.apache.xmlbeans.XmlString xgetCropright() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[4]);
            return target;
        }
    }

    /**
     * True if has "cropright" attribute
     */
    @Override
    public boolean isSetCropright() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[4]) != null;
        }
    }

    /**
     * Sets the "cropright" attribute
     */
    @Override
    public void setCropright(java.lang.String cropright) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[4]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[4]);
            }
            target.setStringValue(cropright);
        }
    }

    /**
     * Sets (as xml) the "cropright" attribute
     */
    @Override
    public void xsetCropright(org.apache.xmlbeans.XmlString cropright) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[4]);
            if (target == null) {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PROPERTY_QNAME[4]);
            }
            target.set(cropright);
        }
    }

    /**
     * Unsets the "cropright" attribute
     */
    @Override
    public void unsetCropright() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[4]);
        }
    }

    /**
     * Gets the "cropbottom" attribute
     */
    @Override
    public java.lang.String getCropbottom() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[5]);
            if (target == null) {
                return null;
            }
            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "cropbottom" attribute
     */
    @Override
    public org.apache.xmlbeans.XmlString xgetCropbottom() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[5]);
            return target;
        }
    }

    /**
     * True if has "cropbottom" attribute
     */
    @Override
    public boolean isSetCropbottom() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[5]) != null;
        }
    }

    /**
     * Sets the "cropbottom" attribute
     */
    @Override
    public void setCropbottom(java.lang.String cropbottom) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[5]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[5]);
            }
            target.setStringValue(cropbottom);
        }
    }

    /**
     * Sets (as xml) the "cropbottom" attribute
     */
    @Override
    public void xsetCropbottom(org.apache.xmlbeans.XmlString cropbottom) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[5]);
            if (target == null) {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PROPERTY_QNAME[5]);
            }
            target.set(cropbottom);
        }
    }

    /**
     * Unsets the "cropbottom" attribute
     */
    @Override
    public void unsetCropbottom() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[5]);
        }
    }

    /**
     * Gets the "gain" attribute
     */
    @Override
    public java.lang.String getGain() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[6]);
            if (target == null) {
                return null;
            }
            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "gain" attribute
     */
    @Override
    public org.apache.xmlbeans.XmlString xgetGain() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[6]);
            return target;
        }
    }

    /**
     * True if has "gain" attribute
     */
    @Override
    public boolean isSetGain() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[6]) != null;
        }
    }

    /**
     * Sets the "gain" attribute
     */
    @Override
    public void setGain(java.lang.String gain) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[6]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[6]);
            }
            target.setStringValue(gain);
        }
    }

    /**
     * Sets (as xml) the "gain" attribute
     */
    @Override
    public void xsetGain(org.apache.xmlbeans.XmlString gain) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[6]);
            if (target == null) {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PROPERTY_QNAME[6]);
            }
            target.set(gain);
        }
    }

    /**
     * Unsets the "gain" attribute
     */
    @Override
    public void unsetGain() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[6]);
        }
    }

    /**
     * Gets the "blacklevel" attribute
     */
    @Override
    public java.lang.String getBlacklevel() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[7]);
            if (target == null) {
                return null;
            }
            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "blacklevel" attribute
     */
    @Override
    public org.apache.xmlbeans.XmlString xgetBlacklevel() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[7]);
            return target;
        }
    }

    /**
     * True if has "blacklevel" attribute
     */
    @Override
    public boolean isSetBlacklevel() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[7]) != null;
        }
    }

    /**
     * Sets the "blacklevel" attribute
     */
    @Override
    public void setBlacklevel(java.lang.String blacklevel) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[7]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[7]);
            }
            target.setStringValue(blacklevel);
        }
    }

    /**
     * Sets (as xml) the "blacklevel" attribute
     */
    @Override
    public void xsetBlacklevel(org.apache.xmlbeans.XmlString blacklevel) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[7]);
            if (target == null) {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PROPERTY_QNAME[7]);
            }
            target.set(blacklevel);
        }
    }

    /**
     * Unsets the "blacklevel" attribute
     */
    @Override
    public void unsetBlacklevel() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[7]);
        }
    }

    /**
     * Gets the "gamma" attribute
     */
    @Override
    public java.lang.String getGamma() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[8]);
            if (target == null) {
                return null;
            }
            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "gamma" attribute
     */
    @Override
    public org.apache.xmlbeans.XmlString xgetGamma() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[8]);
            return target;
        }
    }

    /**
     * True if has "gamma" attribute
     */
    @Override
    public boolean isSetGamma() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[8]) != null;
        }
    }

    /**
     * Sets the "gamma" attribute
     */
    @Override
    public void setGamma(java.lang.String gamma) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[8]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[8]);
            }
            target.setStringValue(gamma);
        }
    }

    /**
     * Sets (as xml) the "gamma" attribute
     */
    @Override
    public void xsetGamma(org.apache.xmlbeans.XmlString gamma) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[8]);
            if (target == null) {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PROPERTY_QNAME[8]);
            }
            target.set(gamma);
        }
    }

    /**
     * Unsets the "gamma" attribute
     */
    @Override
    public void unsetGamma() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[8]);
        }
    }

    /**
     * Gets the "grayscale" attribute
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse.Enum getGrayscale() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[9]);
            if (target == null) {
                return null;
            }
            return (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse.Enum)target.getEnumValue();
        }
    }

    /**
     * Gets (as xml) the "grayscale" attribute
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse xgetGrayscale() {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse)get_store().find_attribute_user(PROPERTY_QNAME[9]);
            return target;
        }
    }

    /**
     * True if has "grayscale" attribute
     */
    @Override
    public boolean isSetGrayscale() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[9]) != null;
        }
    }

    /**
     * Sets the "grayscale" attribute
     */
    @Override
    public void setGrayscale(org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse.Enum grayscale) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[9]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[9]);
            }
            target.setEnumValue(grayscale);
        }
    }

    /**
     * Sets (as xml) the "grayscale" attribute
     */
    @Override
    public void xsetGrayscale(org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse grayscale) {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse)get_store().find_attribute_user(PROPERTY_QNAME[9]);
            if (target == null) {
                target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse)get_store().add_attribute_user(PROPERTY_QNAME[9]);
            }
            target.set(grayscale);
        }
    }

    /**
     * Unsets the "grayscale" attribute
     */
    @Override
    public void unsetGrayscale() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[9]);
        }
    }

    /**
     * Gets the "bilevel" attribute
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse.Enum getBilevel() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[10]);
            if (target == null) {
                return null;
            }
            return (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse.Enum)target.getEnumValue();
        }
    }

    /**
     * Gets (as xml) the "bilevel" attribute
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse xgetBilevel() {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse)get_store().find_attribute_user(PROPERTY_QNAME[10]);
            return target;
        }
    }

    /**
     * True if has "bilevel" attribute
     */
    @Override
    public boolean isSetBilevel() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[10]) != null;
        }
    }

    /**
     * Sets the "bilevel" attribute
     */
    @Override
    public void setBilevel(org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse.Enum bilevel) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[10]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[10]);
            }
            target.setEnumValue(bilevel);
        }
    }

    /**
     * Sets (as xml) the "bilevel" attribute
     */
    @Override
    public void xsetBilevel(org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse bilevel) {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse)get_store().find_attribute_user(PROPERTY_QNAME[10]);
            if (target == null) {
                target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse)get_store().add_attribute_user(PROPERTY_QNAME[10]);
            }
            target.set(bilevel);
        }
    }

    /**
     * Unsets the "bilevel" attribute
     */
    @Override
    public void unsetBilevel() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[10]);
        }
    }

    /**
     * Gets the "chromakey" attribute
     */
    @Override
    public java.lang.String getChromakey() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[11]);
            if (target == null) {
                return null;
            }
            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "chromakey" attribute
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType xgetChromakey() {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType)get_store().find_attribute_user(PROPERTY_QNAME[11]);
            return target;
        }
    }

    /**
     * True if has "chromakey" attribute
     */
    @Override
    public boolean isSetChromakey() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[11]) != null;
        }
    }

    /**
     * Sets the "chromakey" attribute
     */
    @Override
    public void setChromakey(java.lang.String chromakey) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[11]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[11]);
            }
            target.setStringValue(chromakey);
        }
    }

    /**
     * Sets (as xml) the "chromakey" attribute
     */
    @Override
    public void xsetChromakey(org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType chromakey) {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType)get_store().find_attribute_user(PROPERTY_QNAME[11]);
            if (target == null) {
                target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType)get_store().add_attribute_user(PROPERTY_QNAME[11]);
            }
            target.set(chromakey);
        }
    }

    /**
     * Unsets the "chromakey" attribute
     */
    @Override
    public void unsetChromakey() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[11]);
        }
    }

    /**
     * Gets the "embosscolor" attribute
     */
    @Override
    public java.lang.String getEmbosscolor() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[12]);
            if (target == null) {
                return null;
            }
            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "embosscolor" attribute
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType xgetEmbosscolor() {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType)get_store().find_attribute_user(PROPERTY_QNAME[12]);
            return target;
        }
    }

    /**
     * True if has "embosscolor" attribute
     */
    @Override
    public boolean isSetEmbosscolor() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[12]) != null;
        }
    }

    /**
     * Sets the "embosscolor" attribute
     */
    @Override
    public void setEmbosscolor(java.lang.String embosscolor) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[12]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[12]);
            }
            target.setStringValue(embosscolor);
        }
    }

    /**
     * Sets (as xml) the "embosscolor" attribute
     */
    @Override
    public void xsetEmbosscolor(org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType embosscolor) {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType)get_store().find_attribute_user(PROPERTY_QNAME[12]);
            if (target == null) {
                target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType)get_store().add_attribute_user(PROPERTY_QNAME[12]);
            }
            target.set(embosscolor);
        }
    }

    /**
     * Unsets the "embosscolor" attribute
     */
    @Override
    public void unsetEmbosscolor() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[12]);
        }
    }

    /**
     * Gets the "recolortarget" attribute
     */
    @Override
    public java.lang.String getRecolortarget() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[13]);
            if (target == null) {
                return null;
            }
            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "recolortarget" attribute
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType xgetRecolortarget() {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType)get_store().find_attribute_user(PROPERTY_QNAME[13]);
            return target;
        }
    }

    /**
     * True if has "recolortarget" attribute
     */
    @Override
    public boolean isSetRecolortarget() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[13]) != null;
        }
    }

    /**
     * Sets the "recolortarget" attribute
     */
    @Override
    public void setRecolortarget(java.lang.String recolortarget) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[13]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[13]);
            }
            target.setStringValue(recolortarget);
        }
    }

    /**
     * Sets (as xml) the "recolortarget" attribute
     */
    @Override
    public void xsetRecolortarget(org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType recolortarget) {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType)get_store().find_attribute_user(PROPERTY_QNAME[13]);
            if (target == null) {
                target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STColorType)get_store().add_attribute_user(PROPERTY_QNAME[13]);
            }
            target.set(recolortarget);
        }
    }

    /**
     * Unsets the "recolortarget" attribute
     */
    @Override
    public void unsetRecolortarget() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[13]);
        }
    }

    /**
     * Gets the "href" attribute
     */
    @Override
    public java.lang.String getHref() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[14]);
            if (target == null) {
                return null;
            }
            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "href" attribute
     */
    @Override
    public org.apache.xmlbeans.XmlString xgetHref() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[14]);
            return target;
        }
    }

    /**
     * True if has "href" attribute
     */
    @Override
    public boolean isSetHref() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[14]) != null;
        }
    }

    /**
     * Sets the "href" attribute
     */
    @Override
    public void setHref(java.lang.String href) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[14]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[14]);
            }
            target.setStringValue(href);
        }
    }

    /**
     * Sets (as xml) the "href" attribute
     */
    @Override
    public void xsetHref(org.apache.xmlbeans.XmlString href) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[14]);
            if (target == null) {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PROPERTY_QNAME[14]);
            }
            target.set(href);
        }
    }

    /**
     * Unsets the "href" attribute
     */
    @Override
    public void unsetHref() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[14]);
        }
    }

    /**
     * Gets the "althref" attribute
     */
    @Override
    public java.lang.String getAlthref() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[15]);
            if (target == null) {
                return null;
            }
            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "althref" attribute
     */
    @Override
    public org.apache.xmlbeans.XmlString xgetAlthref() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[15]);
            return target;
        }
    }

    /**
     * True if has "althref" attribute
     */
    @Override
    public boolean isSetAlthref() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[15]) != null;
        }
    }

    /**
     * Sets the "althref" attribute
     */
    @Override
    public void setAlthref(java.lang.String althref) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[15]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[15]);
            }
            target.setStringValue(althref);
        }
    }

    /**
     * Sets (as xml) the "althref" attribute
     */
    @Override
    public void xsetAlthref(org.apache.xmlbeans.XmlString althref) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[15]);
            if (target == null) {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PROPERTY_QNAME[15]);
            }
            target.set(althref);
        }
    }

    /**
     * Unsets the "althref" attribute
     */
    @Override
    public void unsetAlthref() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[15]);
        }
    }

    /**
     * Gets the "title" attribute
     */
    @Override
    public java.lang.String getTitle() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[16]);
            if (target == null) {
                return null;
            }
            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "title" attribute
     */
    @Override
    public org.apache.xmlbeans.XmlString xgetTitle() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[16]);
            return target;
        }
    }

    /**
     * True if has "title" attribute
     */
    @Override
    public boolean isSetTitle() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[16]) != null;
        }
    }

    /**
     * Sets the "title" attribute
     */
    @Override
    public void setTitle(java.lang.String title) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[16]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[16]);
            }
            target.setStringValue(title);
        }
    }

    /**
     * Sets (as xml) the "title" attribute
     */
    @Override
    public void xsetTitle(org.apache.xmlbeans.XmlString title) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROPERTY_QNAME[16]);
            if (target == null) {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PROPERTY_QNAME[16]);
            }
            target.set(title);
        }
    }

    /**
     * Unsets the "title" attribute
     */
    @Override
    public void unsetTitle() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[16]);
        }
    }

    /**
     * Gets the "oleid" attribute
     */
    @Override
    public float getOleid() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[17]);
            if (target == null) {
                return 0.0f;
            }
            return target.getFloatValue();
        }
    }

    /**
     * Gets (as xml) the "oleid" attribute
     */
    @Override
    public org.apache.xmlbeans.XmlFloat xgetOleid() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_attribute_user(PROPERTY_QNAME[17]);
            return target;
        }
    }

    /**
     * True if has "oleid" attribute
     */
    @Override
    public boolean isSetOleid() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[17]) != null;
        }
    }

    /**
     * Sets the "oleid" attribute
     */
    @Override
    public void setOleid(float oleid) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[17]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[17]);
            }
            target.setFloatValue(oleid);
        }
    }

    /**
     * Sets (as xml) the "oleid" attribute
     */
    @Override
    public void xsetOleid(org.apache.xmlbeans.XmlFloat oleid) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_attribute_user(PROPERTY_QNAME[17]);
            if (target == null) {
                target = (org.apache.xmlbeans.XmlFloat)get_store().add_attribute_user(PROPERTY_QNAME[17]);
            }
            target.set(oleid);
        }
    }

    /**
     * Unsets the "oleid" attribute
     */
    @Override
    public void unsetOleid() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[17]);
        }
    }

    /**
     * Gets the "detectmouseclick" attribute
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse.Enum getDetectmouseclick() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[18]);
            if (target == null) {
                return null;
            }
            return (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse.Enum)target.getEnumValue();
        }
    }

    /**
     * Gets (as xml) the "detectmouseclick" attribute
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse xgetDetectmouseclick() {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse)get_store().find_attribute_user(PROPERTY_QNAME[18]);
            return target;
        }
    }

    /**
     * True if has "detectmouseclick" attribute
     */
    @Override
    public boolean isSetDetectmouseclick() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[18]) != null;
        }
    }

    /**
     * Sets the "detectmouseclick" attribute
     */
    @Override
    public void setDetectmouseclick(org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse.Enum detectmouseclick) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[18]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[18]);
            }
            target.setEnumValue(detectmouseclick);
        }
    }

    /**
     * Sets (as xml) the "detectmouseclick" attribute
     */
    @Override
    public void xsetDetectmouseclick(org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse detectmouseclick) {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse)get_store().find_attribute_user(PROPERTY_QNAME[18]);
            if (target == null) {
                target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse)get_store().add_attribute_user(PROPERTY_QNAME[18]);
            }
            target.set(detectmouseclick);
        }
    }

    /**
     * Unsets the "detectmouseclick" attribute
     */
    @Override
    public void unsetDetectmouseclick() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[18]);
        }
    }

    /**
     * Gets the "movie" attribute
     */
    @Override
    public float getMovie() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[19]);
            if (target == null) {
                return 0.0f;
            }
            return target.getFloatValue();
        }
    }

    /**
     * Gets (as xml) the "movie" attribute
     */
    @Override
    public org.apache.xmlbeans.XmlFloat xgetMovie() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_attribute_user(PROPERTY_QNAME[19]);
            return target;
        }
    }

    /**
     * True if has "movie" attribute
     */
    @Override
    public boolean isSetMovie() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[19]) != null;
        }
    }

    /**
     * Sets the "movie" attribute
     */
    @Override
    public void setMovie(float movie) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[19]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[19]);
            }
            target.setFloatValue(movie);
        }
    }

    /**
     * Sets (as xml) the "movie" attribute
     */
    @Override
    public void xsetMovie(org.apache.xmlbeans.XmlFloat movie) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlFloat target = null;
            target = (org.apache.xmlbeans.XmlFloat)get_store().find_attribute_user(PROPERTY_QNAME[19]);
            if (target == null) {
                target = (org.apache.xmlbeans.XmlFloat)get_store().add_attribute_user(PROPERTY_QNAME[19]);
            }
            target.set(movie);
        }
    }

    /**
     * Unsets the "movie" attribute
     */
    @Override
    public void unsetMovie() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[19]);
        }
    }

    /**
     * Gets the "relid" attribute
     */
    @Override
    public java.lang.String getRelid() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[20]);
            if (target == null) {
                return null;
            }
            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "relid" attribute
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId xgetRelid() {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().find_attribute_user(PROPERTY_QNAME[20]);
            return target;
        }
    }

    /**
     * True if has "relid" attribute
     */
    @Override
    public boolean isSetRelid() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[20]) != null;
        }
    }

    /**
     * Sets the "relid" attribute
     */
    @Override
    public void setRelid(java.lang.String relid) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[20]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[20]);
            }
            target.setStringValue(relid);
        }
    }

    /**
     * Sets (as xml) the "relid" attribute
     */
    @Override
    public void xsetRelid(org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId relid) {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().find_attribute_user(PROPERTY_QNAME[20]);
            if (target == null) {
                target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().add_attribute_user(PROPERTY_QNAME[20]);
            }
            target.set(relid);
        }
    }

    /**
     * Unsets the "relid" attribute
     */
    @Override
    public void unsetRelid() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[20]);
        }
    }

    /**
     * Gets the "id" attribute
     */
    @Override
    public java.lang.String getId2() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[21]);
            if (target == null) {
                return null;
            }
            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "id" attribute
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId xgetId2() {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().find_attribute_user(PROPERTY_QNAME[21]);
            return target;
        }
    }

    /**
     * True if has "id" attribute
     */
    @Override
    public boolean isSetId2() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[21]) != null;
        }
    }

    /**
     * Sets the "id" attribute
     */
    @Override
    public void setId2(java.lang.String id2) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[21]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[21]);
            }
            target.setStringValue(id2);
        }
    }

    /**
     * Sets (as xml) the "id" attribute
     */
    @Override
    public void xsetId2(org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId id2) {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().find_attribute_user(PROPERTY_QNAME[21]);
            if (target == null) {
                target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().add_attribute_user(PROPERTY_QNAME[21]);
            }
            target.set(id2);
        }
    }

    /**
     * Unsets the "id" attribute
     */
    @Override
    public void unsetId2() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[21]);
        }
    }

    /**
     * Gets the "pict" attribute
     */
    @Override
    public java.lang.String getPict() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[22]);
            if (target == null) {
                return null;
            }
            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "pict" attribute
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId xgetPict() {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().find_attribute_user(PROPERTY_QNAME[22]);
            return target;
        }
    }

    /**
     * True if has "pict" attribute
     */
    @Override
    public boolean isSetPict() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[22]) != null;
        }
    }

    /**
     * Sets the "pict" attribute
     */
    @Override
    public void setPict(java.lang.String pict) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[22]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[22]);
            }
            target.setStringValue(pict);
        }
    }

    /**
     * Sets (as xml) the "pict" attribute
     */
    @Override
    public void xsetPict(org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId pict) {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().find_attribute_user(PROPERTY_QNAME[22]);
            if (target == null) {
                target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().add_attribute_user(PROPERTY_QNAME[22]);
            }
            target.set(pict);
        }
    }

    /**
     * Unsets the "pict" attribute
     */
    @Override
    public void unsetPict() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[22]);
        }
    }

    /**
     * Gets the "href" attribute
     */
    @Override
    public java.lang.String getHref2() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[23]);
            if (target == null) {
                return null;
            }
            return target.getStringValue();
        }
    }

    /**
     * Gets (as xml) the "href" attribute
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId xgetHref2() {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().find_attribute_user(PROPERTY_QNAME[23]);
            return target;
        }
    }

    /**
     * True if has "href" attribute
     */
    @Override
    public boolean isSetHref2() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[23]) != null;
        }
    }

    /**
     * Sets the "href" attribute
     */
    @Override
    public void setHref2(java.lang.String href2) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[23]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[23]);
            }
            target.setStringValue(href2);
        }
    }

    /**
     * Sets (as xml) the "href" attribute
     */
    @Override
    public void xsetHref2(org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId href2) {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().find_attribute_user(PROPERTY_QNAME[23]);
            if (target == null) {
                target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().add_attribute_user(PROPERTY_QNAME[23]);
            }
            target.set(href2);
        }
    }

    /**
     * Unsets the "href" attribute
     */
    @Override
    public void unsetHref2() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[23]);
        }
    }
}
