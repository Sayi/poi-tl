/*
 * XML Type:  CT_OLEObject
 * Namespace: urn:schemas-microsoft-com:office:office
 * Java type: com.microsoft.schemas.office.office.CTOLEObject
 *
 * Automatically generated - do not modify.
 */
package com.microsoft.schemas.office.office.impl;
/**
 * An XML CT_OLEObject(@urn:schemas-microsoft-com:office:office).
 *
 * This is a complex type.
 */
public class CTOLEObjectImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements com.microsoft.schemas.office.office.CTOLEObject
{
    private static final long serialVersionUID = 1L;
    
    public CTOLEObjectImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName LINKTYPE$0 = 
        new javax.xml.namespace.QName("urn:schemas-microsoft-com:office:office", "LinkType");
    private static final javax.xml.namespace.QName LOCKEDFIELD$2 = 
        new javax.xml.namespace.QName("urn:schemas-microsoft-com:office:office", "LockedField");
    private static final javax.xml.namespace.QName FIELDCODES$4 = 
        new javax.xml.namespace.QName("urn:schemas-microsoft-com:office:office", "FieldCodes");
    private static final javax.xml.namespace.QName TYPE$6 = 
        new javax.xml.namespace.QName("", "Type");
    private static final javax.xml.namespace.QName PROGID$8 = 
        new javax.xml.namespace.QName("", "ProgID");
    private static final javax.xml.namespace.QName SHAPEID$10 = 
        new javax.xml.namespace.QName("", "ShapeID");
    private static final javax.xml.namespace.QName DRAWASPECT$12 = 
        new javax.xml.namespace.QName("", "DrawAspect");
    private static final javax.xml.namespace.QName OBJECTID$14 = 
        new javax.xml.namespace.QName("", "ObjectID");
    private static final javax.xml.namespace.QName ID$16 = 
        new javax.xml.namespace.QName("http://schemas.openxmlformats.org/officeDocument/2006/relationships", "id");
    private static final javax.xml.namespace.QName UPDATEMODE$18 = 
        new javax.xml.namespace.QName("", "UpdateMode");
    
    
    /**
     * Gets the "LinkType" element
     */
    public com.microsoft.schemas.office.office.STOLELinkType.Enum getLinkType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LINKTYPE$0, 0);
            if (target == null)
            {
                return null;
            }
            return (com.microsoft.schemas.office.office.STOLELinkType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "LinkType" element
     */
    public com.microsoft.schemas.office.office.STOLELinkType xgetLinkType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.office.office.STOLELinkType target = null;
            target = (com.microsoft.schemas.office.office.STOLELinkType)get_store().find_element_user(LINKTYPE$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "LinkType" element
     */
    public boolean isSetLinkType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LINKTYPE$0) != 0;
        }
    }
    
    /**
     * Sets the "LinkType" element
     */
    public void setLinkType(com.microsoft.schemas.office.office.STOLELinkType.Enum linkType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LINKTYPE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LINKTYPE$0);
            }
            target.setEnumValue(linkType);
        }
    }
    
    /**
     * Sets (as xml) the "LinkType" element
     */
    public void xsetLinkType(com.microsoft.schemas.office.office.STOLELinkType linkType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.office.office.STOLELinkType target = null;
            target = (com.microsoft.schemas.office.office.STOLELinkType)get_store().find_element_user(LINKTYPE$0, 0);
            if (target == null)
            {
                target = (com.microsoft.schemas.office.office.STOLELinkType)get_store().add_element_user(LINKTYPE$0);
            }
            target.set(linkType);
        }
    }
    
    /**
     * Unsets the "LinkType" element
     */
    public void unsetLinkType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LINKTYPE$0, 0);
        }
    }
    
    /**
     * Gets the "LockedField" element
     */
    public com.microsoft.schemas.office.office.STTrueFalseBlank.Enum getLockedField()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCKEDFIELD$2, 0);
            if (target == null)
            {
                return null;
            }
            return (com.microsoft.schemas.office.office.STTrueFalseBlank.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "LockedField" element
     */
    public com.microsoft.schemas.office.office.STTrueFalseBlank xgetLockedField()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.office.office.STTrueFalseBlank target = null;
            target = (com.microsoft.schemas.office.office.STTrueFalseBlank)get_store().find_element_user(LOCKEDFIELD$2, 0);
            return target;
        }
    }
    
    /**
     * True if has "LockedField" element
     */
    public boolean isSetLockedField()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LOCKEDFIELD$2) != 0;
        }
    }
    
    /**
     * Sets the "LockedField" element
     */
    public void setLockedField(com.microsoft.schemas.office.office.STTrueFalseBlank.Enum lockedField)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOCKEDFIELD$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LOCKEDFIELD$2);
            }
            target.setEnumValue(lockedField);
        }
    }
    
    /**
     * Sets (as xml) the "LockedField" element
     */
    public void xsetLockedField(com.microsoft.schemas.office.office.STTrueFalseBlank lockedField)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.office.office.STTrueFalseBlank target = null;
            target = (com.microsoft.schemas.office.office.STTrueFalseBlank)get_store().find_element_user(LOCKEDFIELD$2, 0);
            if (target == null)
            {
                target = (com.microsoft.schemas.office.office.STTrueFalseBlank)get_store().add_element_user(LOCKEDFIELD$2);
            }
            target.set(lockedField);
        }
    }
    
    /**
     * Unsets the "LockedField" element
     */
    public void unsetLockedField()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LOCKEDFIELD$2, 0);
        }
    }
    
    /**
     * Gets the "FieldCodes" element
     */
    public java.lang.String getFieldCodes()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIELDCODES$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "FieldCodes" element
     */
    public org.apache.xmlbeans.XmlString xgetFieldCodes()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIELDCODES$4, 0);
            return target;
        }
    }
    
    /**
     * True if has "FieldCodes" element
     */
    public boolean isSetFieldCodes()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FIELDCODES$4) != 0;
        }
    }
    
    /**
     * Sets the "FieldCodes" element
     */
    public void setFieldCodes(java.lang.String fieldCodes)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FIELDCODES$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FIELDCODES$4);
            }
            target.setStringValue(fieldCodes);
        }
    }
    
    /**
     * Sets (as xml) the "FieldCodes" element
     */
    public void xsetFieldCodes(org.apache.xmlbeans.XmlString fieldCodes)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FIELDCODES$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FIELDCODES$4);
            }
            target.set(fieldCodes);
        }
    }
    
    /**
     * Unsets the "FieldCodes" element
     */
    public void unsetFieldCodes()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FIELDCODES$4, 0);
        }
    }
    
    /**
     * Gets the "Type" attribute
     */
    public com.microsoft.schemas.office.office.STOLEType.Enum getType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TYPE$6);
            if (target == null)
            {
                return null;
            }
            return (com.microsoft.schemas.office.office.STOLEType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "Type" attribute
     */
    public com.microsoft.schemas.office.office.STOLEType xgetType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.office.office.STOLEType target = null;
            target = (com.microsoft.schemas.office.office.STOLEType)get_store().find_attribute_user(TYPE$6);
            return target;
        }
    }
    
    /**
     * True if has "Type" attribute
     */
    public boolean isSetType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(TYPE$6) != null;
        }
    }
    
    /**
     * Sets the "Type" attribute
     */
    public void setType(com.microsoft.schemas.office.office.STOLEType.Enum type)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TYPE$6);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TYPE$6);
            }
            target.setEnumValue(type);
        }
    }
    
    /**
     * Sets (as xml) the "Type" attribute
     */
    public void xsetType(com.microsoft.schemas.office.office.STOLEType type)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.office.office.STOLEType target = null;
            target = (com.microsoft.schemas.office.office.STOLEType)get_store().find_attribute_user(TYPE$6);
            if (target == null)
            {
                target = (com.microsoft.schemas.office.office.STOLEType)get_store().add_attribute_user(TYPE$6);
            }
            target.set(type);
        }
    }
    
    /**
     * Unsets the "Type" attribute
     */
    public void unsetType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(TYPE$6);
        }
    }
    
    /**
     * Gets the "ProgID" attribute
     */
    public java.lang.String getProgID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROGID$8);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ProgID" attribute
     */
    public org.apache.xmlbeans.XmlString xgetProgID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROGID$8);
            return target;
        }
    }
    
    /**
     * True if has "ProgID" attribute
     */
    public boolean isSetProgID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(PROGID$8) != null;
        }
    }
    
    /**
     * Sets the "ProgID" attribute
     */
    public void setProgID(java.lang.String progID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PROGID$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PROGID$8);
            }
            target.setStringValue(progID);
        }
    }
    
    /**
     * Sets (as xml) the "ProgID" attribute
     */
    public void xsetProgID(org.apache.xmlbeans.XmlString progID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PROGID$8);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PROGID$8);
            }
            target.set(progID);
        }
    }
    
    /**
     * Unsets the "ProgID" attribute
     */
    public void unsetProgID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(PROGID$8);
        }
    }
    
    /**
     * Gets the "ShapeID" attribute
     */
    public java.lang.String getShapeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SHAPEID$10);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ShapeID" attribute
     */
    public org.apache.xmlbeans.XmlString xgetShapeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SHAPEID$10);
            return target;
        }
    }
    
    /**
     * True if has "ShapeID" attribute
     */
    public boolean isSetShapeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(SHAPEID$10) != null;
        }
    }
    
    /**
     * Sets the "ShapeID" attribute
     */
    public void setShapeID(java.lang.String shapeID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(SHAPEID$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(SHAPEID$10);
            }
            target.setStringValue(shapeID);
        }
    }
    
    /**
     * Sets (as xml) the "ShapeID" attribute
     */
    public void xsetShapeID(org.apache.xmlbeans.XmlString shapeID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(SHAPEID$10);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(SHAPEID$10);
            }
            target.set(shapeID);
        }
    }
    
    /**
     * Unsets the "ShapeID" attribute
     */
    public void unsetShapeID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(SHAPEID$10);
        }
    }
    
    /**
     * Gets the "DrawAspect" attribute
     */
    public com.microsoft.schemas.office.office.STOLEDrawAspect.Enum getDrawAspect()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DRAWASPECT$12);
            if (target == null)
            {
                return null;
            }
            return (com.microsoft.schemas.office.office.STOLEDrawAspect.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "DrawAspect" attribute
     */
    public com.microsoft.schemas.office.office.STOLEDrawAspect xgetDrawAspect()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.office.office.STOLEDrawAspect target = null;
            target = (com.microsoft.schemas.office.office.STOLEDrawAspect)get_store().find_attribute_user(DRAWASPECT$12);
            return target;
        }
    }
    
    /**
     * True if has "DrawAspect" attribute
     */
    public boolean isSetDrawAspect()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(DRAWASPECT$12) != null;
        }
    }
    
    /**
     * Sets the "DrawAspect" attribute
     */
    public void setDrawAspect(com.microsoft.schemas.office.office.STOLEDrawAspect.Enum drawAspect)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(DRAWASPECT$12);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(DRAWASPECT$12);
            }
            target.setEnumValue(drawAspect);
        }
    }
    
    /**
     * Sets (as xml) the "DrawAspect" attribute
     */
    public void xsetDrawAspect(com.microsoft.schemas.office.office.STOLEDrawAspect drawAspect)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.office.office.STOLEDrawAspect target = null;
            target = (com.microsoft.schemas.office.office.STOLEDrawAspect)get_store().find_attribute_user(DRAWASPECT$12);
            if (target == null)
            {
                target = (com.microsoft.schemas.office.office.STOLEDrawAspect)get_store().add_attribute_user(DRAWASPECT$12);
            }
            target.set(drawAspect);
        }
    }
    
    /**
     * Unsets the "DrawAspect" attribute
     */
    public void unsetDrawAspect()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(DRAWASPECT$12);
        }
    }
    
    /**
     * Gets the "ObjectID" attribute
     */
    public java.lang.String getObjectID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OBJECTID$14);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ObjectID" attribute
     */
    public org.apache.xmlbeans.XmlString xgetObjectID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(OBJECTID$14);
            return target;
        }
    }
    
    /**
     * True if has "ObjectID" attribute
     */
    public boolean isSetObjectID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(OBJECTID$14) != null;
        }
    }
    
    /**
     * Sets the "ObjectID" attribute
     */
    public void setObjectID(java.lang.String objectID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(OBJECTID$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(OBJECTID$14);
            }
            target.setStringValue(objectID);
        }
    }
    
    /**
     * Sets (as xml) the "ObjectID" attribute
     */
    public void xsetObjectID(org.apache.xmlbeans.XmlString objectID)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(OBJECTID$14);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(OBJECTID$14);
            }
            target.set(objectID);
        }
    }
    
    /**
     * Unsets the "ObjectID" attribute
     */
    public void unsetObjectID()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(OBJECTID$14);
        }
    }
    
    /**
     * Gets the "id" attribute
     */
    public java.lang.String getId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ID$16);
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
    public org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId xgetId()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().find_attribute_user(ID$16);
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
            return get_store().find_attribute_user(ID$16) != null;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(ID$16);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(ID$16);
            }
            target.setStringValue(id);
        }
    }
    
    /**
     * Sets (as xml) the "id" attribute
     */
    public void xsetId(org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId id)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId target = null;
            target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().find_attribute_user(ID$16);
            if (target == null)
            {
                target = (org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId)get_store().add_attribute_user(ID$16);
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
            get_store().remove_attribute(ID$16);
        }
    }
    
    /**
     * Gets the "UpdateMode" attribute
     */
    public com.microsoft.schemas.office.office.STOLEUpdateMode.Enum getUpdateMode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(UPDATEMODE$18);
            if (target == null)
            {
                return null;
            }
            return (com.microsoft.schemas.office.office.STOLEUpdateMode.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "UpdateMode" attribute
     */
    public com.microsoft.schemas.office.office.STOLEUpdateMode xgetUpdateMode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.office.office.STOLEUpdateMode target = null;
            target = (com.microsoft.schemas.office.office.STOLEUpdateMode)get_store().find_attribute_user(UPDATEMODE$18);
            return target;
        }
    }
    
    /**
     * True if has "UpdateMode" attribute
     */
    public boolean isSetUpdateMode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(UPDATEMODE$18) != null;
        }
    }
    
    /**
     * Sets the "UpdateMode" attribute
     */
    public void setUpdateMode(com.microsoft.schemas.office.office.STOLEUpdateMode.Enum updateMode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(UPDATEMODE$18);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(UPDATEMODE$18);
            }
            target.setEnumValue(updateMode);
        }
    }
    
    /**
     * Sets (as xml) the "UpdateMode" attribute
     */
    public void xsetUpdateMode(com.microsoft.schemas.office.office.STOLEUpdateMode updateMode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            com.microsoft.schemas.office.office.STOLEUpdateMode target = null;
            target = (com.microsoft.schemas.office.office.STOLEUpdateMode)get_store().find_attribute_user(UPDATEMODE$18);
            if (target == null)
            {
                target = (com.microsoft.schemas.office.office.STOLEUpdateMode)get_store().add_attribute_user(UPDATEMODE$18);
            }
            target.set(updateMode);
        }
    }
    
    /**
     * Unsets the "UpdateMode" attribute
     */
    public void unsetUpdateMode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(UPDATEMODE$18);
        }
    }
}
