/*
 * XML Type:  CT_Func
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.CTFunc
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML CT_Func(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is a complex type.
 */
public interface CTFunc extends org.apache.xmlbeans.XmlObject {
    DocumentFactory<org.openxmlformats.schemas.officeDocument.x2006.math.CTFunc> Factory = new DocumentFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "ctfunc816etype");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    /**
     * Gets the "funcPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTFuncPr getFuncPr();

    /**
     * True if has "funcPr" element
     */
    boolean isSetFuncPr();

    /**
     * Sets the "funcPr" element
     */
    void setFuncPr(org.openxmlformats.schemas.officeDocument.x2006.math.CTFuncPr funcPr);

    /**
     * Appends and returns a new empty "funcPr" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTFuncPr addNewFuncPr();

    /**
     * Unsets the "funcPr" element
     */
    void unsetFuncPr();

    /**
     * Gets the "fName" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg getFName();

    /**
     * Sets the "fName" element
     */
    void setFName(org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg fName);

    /**
     * Appends and returns a new empty "fName" element
     */
    org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg addNewFName();

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
