/*
 * XML Type:  ST_Script
 * Namespace: http://schemas.openxmlformats.org/officeDocument/2006/math
 * Java type: org.openxmlformats.schemas.officeDocument.x2006.math.STScript
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.officeDocument.x2006.math;

import org.apache.xmlbeans.impl.schema.ElementFactory;
import org.apache.xmlbeans.impl.schema.AbstractDocumentFactory;
import org.apache.xmlbeans.impl.schema.DocumentFactory;
import org.apache.xmlbeans.impl.schema.SimpleTypeFactory;


/**
 * An XML ST_Script(@http://schemas.openxmlformats.org/officeDocument/2006/math).
 *
 * This is an atomic type that is a restriction of org.openxmlformats.schemas.officeDocument.x2006.math.STScript.
 */
public interface STScript extends org.apache.xmlbeans.XmlString {
    SimpleTypeFactory<org.openxmlformats.schemas.officeDocument.x2006.math.STScript> Factory = new SimpleTypeFactory<>(org.apache.poi.schemas.ooxml.system.ooxml.TypeSystemHolder.typeSystem, "stscript4637type");
    org.apache.xmlbeans.SchemaType type = Factory.getType();


    org.apache.xmlbeans.StringEnumAbstractBase getEnumValue();
    void setEnumValue(org.apache.xmlbeans.StringEnumAbstractBase e);

    Enum ROMAN = Enum.forString("roman");
    Enum SCRIPT = Enum.forString("script");
    Enum FRAKTUR = Enum.forString("fraktur");
    Enum DOUBLE_STRUCK = Enum.forString("double-struck");
    Enum SANS_SERIF = Enum.forString("sans-serif");
    Enum MONOSPACE = Enum.forString("monospace");

    int INT_ROMAN = Enum.INT_ROMAN;
    int INT_SCRIPT = Enum.INT_SCRIPT;
    int INT_FRAKTUR = Enum.INT_FRAKTUR;
    int INT_DOUBLE_STRUCK = Enum.INT_DOUBLE_STRUCK;
    int INT_SANS_SERIF = Enum.INT_SANS_SERIF;
    int INT_MONOSPACE = Enum.INT_MONOSPACE;

    /**
     * Enumeration value class for org.openxmlformats.schemas.officeDocument.x2006.math.STScript.
     * These enum values can be used as follows:
     * <pre>
     * enum.toString(); // returns the string value of the enum
     * enum.intValue(); // returns an int value, useful for switches
     * // e.g., case Enum.INT_ROMAN
     * Enum.forString(s); // returns the enum value for a string
     * Enum.forInt(i); // returns the enum value for an int
     * </pre>
     * Enumeration objects are immutable singleton objects that
     * can be compared using == object equality. They have no
     * public constructor. See the constants defined within this
     * class for all the valid values.
     */
    final class Enum extends org.apache.xmlbeans.StringEnumAbstractBase {
        /**
         * Returns the enum value for a string, or null if none.
         */
        public static Enum forString(java.lang.String s) {
            return (Enum)table.forString(s);
        }

        /**
         * Returns the enum value corresponding to an int, or null if none.
         */
        public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
        }

        private Enum(java.lang.String s, int i) {
            super(s, i);
        }

        static final int INT_ROMAN = 1;
        static final int INT_SCRIPT = 2;
        static final int INT_FRAKTUR = 3;
        static final int INT_DOUBLE_STRUCK = 4;
        static final int INT_SANS_SERIF = 5;
        static final int INT_MONOSPACE = 6;

        public static final org.apache.xmlbeans.StringEnumAbstractBase.Table table =
            new org.apache.xmlbeans.StringEnumAbstractBase.Table(new Enum[] {
            new Enum("roman", INT_ROMAN),
            new Enum("script", INT_SCRIPT),
            new Enum("fraktur", INT_FRAKTUR),
            new Enum("double-struck", INT_DOUBLE_STRUCK),
            new Enum("sans-serif", INT_SANS_SERIF),
            new Enum("monospace", INT_MONOSPACE),
        });
        private static final long serialVersionUID = 1L;
        private java.lang.Object readResolve() {
            return forInt(intValue());
        }
    }
}
