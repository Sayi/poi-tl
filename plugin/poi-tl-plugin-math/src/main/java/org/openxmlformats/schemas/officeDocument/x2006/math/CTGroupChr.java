/*
 * XML Type:  CT_GroupChr
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTGroupChr
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_GroupChr(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTGroupChr extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTGroupChr> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctgroupchra6c4type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "groupChrPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTGroupChrPr getGroupChrPr();

    /**
     * True if has "groupChrPr" element
     */
    boolean isSetGroupChrPr();

    /**
     * Sets the "groupChrPr" element
     */
    void setGroupChrPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTGroupChrPr groupChrPr);

    /**
     * Appends and returns a new empty "groupChrPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTGroupChrPr addNewGroupChrPr();

    /**
     * Unsets the "groupChrPr" element
     */
    void unsetGroupChrPr();

    /**
     * Gets the "e" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg getE();

    /**
     * Sets the "e" element
     */
    void setE(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg e);

    /**
     * Appends and returns a new empty "e" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg addNewE();
}
