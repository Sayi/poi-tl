/*
 * XML Type:  ST_SpacingRule
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.STSpacingRule
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML ST_SpacingRule(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is an atomic type that is a restriction of org.openxmlformats.schemas.officeDocument.x2006.math.STSpacingRule.
 */
public interface STSpacingRule extends org.apache.xmlbeans.XmlInteger {
    int getIntValue();
    void setIntValue(int i);
    SimpleTypeFactory<org.openxmlformats.schemas.officeDocument.x2006.math.STSpacingRule> Factory = new SimpleTypeFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "stspacingrule00adtype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();

}
