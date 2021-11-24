/*
 * XML Type:  ST_OLEDrawAspect
 * Namespace: urn:schemas-microsoft-com:office:office
 * Java type: com.microsoft.schemas.office.office.STOLEDrawAspect
 *
 * Automatically generated - do not modify.
 */
package com.microsoft.schemas.office.office;


/**
 * An XML ST_OLEDrawAspect(@urn:schemas-microsoft-com:office:office).
 *
 * This is an atomic type that is a restriction of com.microsoft.schemas.office.office.STOLEDrawAspect.
 */
public interface STOLEDrawAspect extends org.apache.xmlbeans.XmlString
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(STOLEDrawAspect.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD023D6490046BA0250A839A9AD24C443").resolveHandle("stoledrawaspect00adtype");
    
    org.apache.xmlbeans.StringEnumAbstractBase enumValue();
    void set(org.apache.xmlbeans.StringEnumAbstractBase e);
    
    static final Enum CONTENT = Enum.forString("Content");
    static final Enum ICON = Enum.forString("Icon");
    
    static final int INT_CONTENT = Enum.INT_CONTENT;
    static final int INT_ICON = Enum.INT_ICON;
    
    /**
     * Enumeration value class for com.microsoft.schemas.office.office.STOLEDrawAspect.
     * These enum values can be used as follows:
     * <pre>
     * enum.toString(); // returns the string value of the enum
     * enum.intValue(); // returns an int value, useful for switches
     * // e.g., case Enum.INT_CONTENT
     * Enum.forString(s); // returns the enum value for a string
     * Enum.forInt(i); // returns the enum value for an int
     * </pre>
     * Enumeration objects are immutable singleton objects that
     * can be compared using == object equality. They have no
     * public constructor. See the constants defined within this
     * class for all the valid values.
     */
    static final class Enum extends org.apache.xmlbeans.StringEnumAbstractBase
    {
        /**
         * Returns the enum value for a string, or null if none.
         */
        public static Enum forString(java.lang.String s)
            { return (Enum)table.forString(s); }
        /**
         * Returns the enum value corresponding to an int, or null if none.
         */
        public static Enum forInt(int i)
            { return (Enum)table.forInt(i); }
        
        private Enum(java.lang.String s, int i)
            { super(s, i); }
        
        static final int INT_CONTENT = 1;
        static final int INT_ICON = 2;
        
        public static final org.apache.xmlbeans.StringEnumAbstractBase.Table table =
            new org.apache.xmlbeans.StringEnumAbstractBase.Table
        (
            new Enum[]
            {
                new Enum("Content", INT_CONTENT),
                new Enum("Icon", INT_ICON),
            }
        );
        private static final long serialVersionUID = 1L;
        private java.lang.Object readResolve() { return forInt(intValue()); } 
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.microsoft.schemas.office.office.STOLEDrawAspect newValue(java.lang.Object obj) {
          return (com.microsoft.schemas.office.office.STOLEDrawAspect) type.newValue( obj ); }
        

        private static java.lang.ref.SoftReference<org.apache.xmlbeans.SchemaTypeLoader> typeLoader;

        private static synchronized org.apache.xmlbeans.SchemaTypeLoader getTypeLoader() {
            org.apache.xmlbeans.SchemaTypeLoader stl = (typeLoader == null) ? null : typeLoader.get();
            if (stl == null) {
                stl = org.apache.xmlbeans.XmlBeans.typeLoaderForClassLoader(com.microsoft.schemas.office.office.STOLEDrawAspect.class.getClassLoader());
                typeLoader = new java.lang.ref.SoftReference(stl);
            }
            return stl;
        }

        public  static com.microsoft.schemas.office.office.STOLEDrawAspect newInstance() {
          return (com.microsoft.schemas.office.office.STOLEDrawAspect) getTypeLoader().newInstance( type, null ); }
        
        public static com.microsoft.schemas.office.office.STOLEDrawAspect newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.microsoft.schemas.office.office.STOLEDrawAspect) getTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.microsoft.schemas.office.office.STOLEDrawAspect parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.microsoft.schemas.office.office.STOLEDrawAspect) getTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.microsoft.schemas.office.office.STOLEDrawAspect parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.microsoft.schemas.office.office.STOLEDrawAspect) getTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.microsoft.schemas.office.office.STOLEDrawAspect parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.office.office.STOLEDrawAspect) getTypeLoader().parse( file, type, null ); }
        
        public static com.microsoft.schemas.office.office.STOLEDrawAspect parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.office.office.STOLEDrawAspect) getTypeLoader().parse( file, type, options ); }
        
        public static com.microsoft.schemas.office.office.STOLEDrawAspect parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.office.office.STOLEDrawAspect) getTypeLoader().parse( u, type, null ); }
        
        public static com.microsoft.schemas.office.office.STOLEDrawAspect parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.office.office.STOLEDrawAspect) getTypeLoader().parse( u, type, options ); }
        
        public static com.microsoft.schemas.office.office.STOLEDrawAspect parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.office.office.STOLEDrawAspect) getTypeLoader().parse( is, type, null ); }
        
        public static com.microsoft.schemas.office.office.STOLEDrawAspect parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.office.office.STOLEDrawAspect) getTypeLoader().parse( is, type, options ); }
        
        public static com.microsoft.schemas.office.office.STOLEDrawAspect parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.office.office.STOLEDrawAspect) getTypeLoader().parse( r, type, null ); }
        
        public static com.microsoft.schemas.office.office.STOLEDrawAspect parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.office.office.STOLEDrawAspect) getTypeLoader().parse( r, type, options ); }
        
        public static com.microsoft.schemas.office.office.STOLEDrawAspect parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.microsoft.schemas.office.office.STOLEDrawAspect) getTypeLoader().parse( sr, type, null ); }
        
        public static com.microsoft.schemas.office.office.STOLEDrawAspect parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.microsoft.schemas.office.office.STOLEDrawAspect) getTypeLoader().parse( sr, type, options ); }
        
        public static com.microsoft.schemas.office.office.STOLEDrawAspect parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.microsoft.schemas.office.office.STOLEDrawAspect) getTypeLoader().parse( node, type, null ); }
        
        public static com.microsoft.schemas.office.office.STOLEDrawAspect parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.microsoft.schemas.office.office.STOLEDrawAspect) getTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        @Deprecated
        public static com.microsoft.schemas.office.office.STOLEDrawAspect parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.microsoft.schemas.office.office.STOLEDrawAspect) getTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        @Deprecated
        public static com.microsoft.schemas.office.office.STOLEDrawAspect parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.microsoft.schemas.office.office.STOLEDrawAspect) getTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        @Deprecated
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return getTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        @Deprecated
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return getTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
