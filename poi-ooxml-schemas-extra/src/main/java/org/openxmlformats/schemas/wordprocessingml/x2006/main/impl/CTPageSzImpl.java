/*
 * XML Type:  CT_PageSz
 * Namespace: http://schemas.openxmlformats.org/wordprocessingml/2006/main
 * Java type: org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.wordprocessingml.x2006.main.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.QNameSet;

/**
 * An XML CT_PageSz(@http://schemas.openxmlformats.org/wordprocessingml/2006/main).
 *
 * This is a complex type.
 */
public class CTPageSzImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz {
    private static final long serialVersionUID = 1L;

    public CTPageSzImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    private static final QName[] PROPERTY_QNAME = {
        new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "w"),
        new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "h"),
        new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "orient"),
        new QName("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "code"),
    };


    /**
     * Gets the "w" attribute
     */
    @Override
    public java.lang.Object getW() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[0]);
            if (target == null) {
                return null;
            }
            return target.getObjectValue();
        }
    }

    /**
     * Gets (as xml) the "w" attribute
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure xgetW() {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure)get_store().find_attribute_user(PROPERTY_QNAME[0]);
            return target;
        }
    }

    /**
     * True if has "w" attribute
     */
    @Override
    public boolean isSetW() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[0]) != null;
        }
    }

    /**
     * Sets the "w" attribute
     */
    @Override
    public void setW(java.lang.Object w) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[0]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[0]);
            }
            target.setObjectValue(w);
        }
    }

    /**
     * Sets (as xml) the "w" attribute
     */
    @Override
    public void xsetW(org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure w) {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure)get_store().find_attribute_user(PROPERTY_QNAME[0]);
            if (target == null) {
                target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure)get_store().add_attribute_user(PROPERTY_QNAME[0]);
            }
            target.set(w);
        }
    }

    /**
     * Unsets the "w" attribute
     */
    @Override
    public void unsetW() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[0]);
        }
    }

    /**
     * Gets the "h" attribute
     */
    @Override
    public java.lang.Object getH() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[1]);
            if (target == null) {
                return null;
            }
            return target.getObjectValue();
        }
    }

    /**
     * Gets (as xml) the "h" attribute
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure xgetH() {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure)get_store().find_attribute_user(PROPERTY_QNAME[1]);
            return target;
        }
    }

    /**
     * True if has "h" attribute
     */
    @Override
    public boolean isSetH() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[1]) != null;
        }
    }

    /**
     * Sets the "h" attribute
     */
    @Override
    public void setH(java.lang.Object h) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[1]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[1]);
            }
            target.setObjectValue(h);
        }
    }

    /**
     * Sets (as xml) the "h" attribute
     */
    @Override
    public void xsetH(org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure h) {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure)get_store().find_attribute_user(PROPERTY_QNAME[1]);
            if (target == null) {
                target = (org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTwipsMeasure)get_store().add_attribute_user(PROPERTY_QNAME[1]);
            }
            target.set(h);
        }
    }

    /**
     * Unsets the "h" attribute
     */
    @Override
    public void unsetH() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[1]);
        }
    }

    /**
     * Gets the "orient" attribute
     */
    @Override
    public org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation.Enum getOrient() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[2]);
            if (target == null) {
                return null;
            }
            return (org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation.Enum)target.getEnumValue();
        }
    }

    /**
     * Gets (as xml) the "orient" attribute
     */
    @Override
    public org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation xgetOrient() {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation)get_store().find_attribute_user(PROPERTY_QNAME[2]);
            return target;
        }
    }

    /**
     * True if has "orient" attribute
     */
    @Override
    public boolean isSetOrient() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[2]) != null;
        }
    }

    /**
     * Sets the "orient" attribute
     */
    @Override
    public void setOrient(org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation.Enum orient) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[2]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[2]);
            }
            target.setEnumValue(orient);
        }
    }

    /**
     * Sets (as xml) the "orient" attribute
     */
    @Override
    public void xsetOrient(org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation orient) {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation)get_store().find_attribute_user(PROPERTY_QNAME[2]);
            if (target == null) {
                target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation)get_store().add_attribute_user(PROPERTY_QNAME[2]);
            }
            target.set(orient);
        }
    }

    /**
     * Unsets the "orient" attribute
     */
    @Override
    public void unsetOrient() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[2]);
        }
    }

    /**
     * Gets the "code" attribute
     */
    @Override
    public java.math.BigInteger getCode() {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[3]);
            if (target == null) {
                return null;
            }
            return target.getBigIntegerValue();
        }
    }

    /**
     * Gets (as xml) the "code" attribute
     */
    @Override
    public org.openxmlformats.schemas.wordprocessingml.x2006.main.STDecimalNumber xgetCode() {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STDecimalNumber target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STDecimalNumber)get_store().find_attribute_user(PROPERTY_QNAME[3]);
            return target;
        }
    }

    /**
     * True if has "code" attribute
     */
    @Override
    public boolean isSetCode() {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[3]) != null;
        }
    }

    /**
     * Sets the "code" attribute
     */
    @Override
    public void setCode(java.math.BigInteger code) {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[3]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[3]);
            }
            target.setBigIntegerValue(code);
        }
    }

    /**
     * Sets (as xml) the "code" attribute
     */
    @Override
    public void xsetCode(org.openxmlformats.schemas.wordprocessingml.x2006.main.STDecimalNumber code) {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.wordprocessingml.x2006.main.STDecimalNumber target = null;
            target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STDecimalNumber)get_store().find_attribute_user(PROPERTY_QNAME[3]);
            if (target == null) {
                target = (org.openxmlformats.schemas.wordprocessingml.x2006.main.STDecimalNumber)get_store().add_attribute_user(PROPERTY_QNAME[3]);
            }
            target.set(code);
        }
    }

    /**
     * Unsets the "code" attribute
     */
    @Override
    public void unsetCode() {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[3]);
        }
    }
}
