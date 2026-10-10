/*
 * XML Type:  CT_ManualBreak
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTManualBreak
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.XmlObject;

/**
 * An XML CT_ManualBreak(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public class CTManualBreakImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements org.openxmlformats.schemas.officeDocument.x2006.math.CTManualBreak {
    private static final long serialVersionUID = 1L;

    public CTManualBreakImpl(org.apache.xmlbeans.SchemaType sType) {
        super(sType);
    }

    private static final QName[] PROPERTY_QNAME = {
        new QName("http://schemas.openxmlformats.org/officeDocument/2006/math", "alnAt"),
    };


    /**
     * Gets the "alnAt" attribute
     */
    @Override
    public int getAlnAt() {
        synchronized (monitor()) {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[0]);
            return (target == null) ? 0 : target.getIntValue();
        }
    }

    /**
     * Gets (as xml) the "alnAt" attribute
     */
    @Override
    public org.openxmlformats.schemas.officeDocument.x2006.math.STInteger255 xgetAlnAt() {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.STInteger255 target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.STInteger255)get_store().find_attribute_user(PROPERTY_QNAME[0]);
            return target;
        }
    }

    /**
     * True if has "alnAt" attribute
     */
    @Override
    public boolean isSetAlnAt() {
        synchronized (monitor()) {
            check_orphaned();
            return get_store().find_attribute_user(PROPERTY_QNAME[0]) != null;
        }
    }

    /**
     * Sets the "alnAt" attribute
     */
    @Override
    public void setAlnAt(int alnAt) {
        synchronized (monitor()) {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROPERTY_QNAME[0]);
            if (target == null) {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROPERTY_QNAME[0]);
            }
            target.setIntValue(alnAt);
        }
    }

    /**
     * Sets (as xml) the "alnAt" attribute
     */
    @Override
    public void xsetAlnAt(org.openxmlformats.schemas.officeDocument.x2006.math.STInteger255 alnAt) {
        synchronized (monitor()) {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.math.STInteger255 target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.math.STInteger255)get_store().find_attribute_user(PROPERTY_QNAME[0]);
            if (target == null) {
                target = (org.openxmlformats.schemas.officeDocument.x2006.math.STInteger255)get_store().add_attribute_user(PROPERTY_QNAME[0]);
            }
            target.set(alnAt);
        }
    }

    /**
     * Unsets the "alnAt" attribute
     */
    @Override
    public void unsetAlnAt() {
        synchronized (monitor()) {
            check_orphaned();
            get_store().remove_attribute(PROPERTY_QNAME[0]);
        }
    }
}
