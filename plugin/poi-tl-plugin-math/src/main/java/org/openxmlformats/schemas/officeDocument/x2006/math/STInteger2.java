/*
 * XML Type:  ST_Integer2
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.STInteger2
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML ST_Integer2(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is an atomic type that is a restriction of org.openxmlformats.schemas.officeDocument.x2006.math.STInteger2.
 */
public interface STInteger2 extends org.apache.xmlbeans.XmlInteger {
    int getIntValue();
    void setIntValue(int i);
    SimpleTypeFactory<org.openxmlformats.schemas.officeDocument.x2006.math.STInteger2> Factory = new SimpleTypeFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "stinteger23beetype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();

}
