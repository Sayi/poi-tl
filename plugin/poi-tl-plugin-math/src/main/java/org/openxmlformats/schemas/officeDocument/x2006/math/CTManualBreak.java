/*
 * XML Type:  CT_ManualBreak
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTManualBreak
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_ManualBreak(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTManualBreak extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTManualBreak> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctmanualbreak1563type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "alnAt" attribute
     */
    int getAlnAt();

    /**
     * Gets (as xml) the "alnAt" attribute
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.STInteger255 xgetAlnAt();

    /**
     * True if has "alnAt" attribute
     */
    boolean isSetAlnAt();

    /**
     * Sets the "alnAt" attribute
     */
    void setAlnAt(int alnAt);

    /**
     * Sets (as xml) the "alnAt" attribute
     */
    void xsetAlnAt(org.openxmlformats.schemas.officeDocument.x2006.math.STInteger255 alnAt);

    /**
     * Unsets the "alnAt" attribute
     */
    void unsetAlnAt();
}
