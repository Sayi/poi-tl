/*
 * XML Type:  ST_RelationshipId
 * Namespace: urn:schemas-microsoft-com:office:office
 * Java type: com.microsoft.schemas.office.office.STRelationshipId
 *
 * Automatically generated - do not modify.
 */
package com.microsoft.schemas.office.office;


/**
 * An XML ST_RelationshipId(@urn:schemas-microsoft-com:office:office).
 *
 * This is an atomic type that is a restriction of com.microsoft.schemas.office.office.STRelationshipId.
 */
public interface STRelationshipId extends org.apache.xmlbeans.XmlString
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(STRelationshipId.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD023D6490046BA0250A839A9AD24C443").resolveHandle("strelationshipid95a6type");
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static com.microsoft.schemas.office.office.STRelationshipId newValue(java.lang.Object obj) {
          return (com.microsoft.schemas.office.office.STRelationshipId) type.newValue( obj ); }
        

        private static java.lang.ref.SoftReference<org.apache.xmlbeans.SchemaTypeLoader> typeLoader;

        private static synchronized org.apache.xmlbeans.SchemaTypeLoader getTypeLoader() {
            org.apache.xmlbeans.SchemaTypeLoader stl = (typeLoader == null) ? null : typeLoader.get();
            if (stl == null) {
                stl = org.apache.xmlbeans.XmlBeans.typeLoaderForClassLoader(com.microsoft.schemas.office.office.STRelationshipId.class.getClassLoader());
                typeLoader = new java.lang.ref.SoftReference(stl);
            }
            return stl;
        }

        public  static com.microsoft.schemas.office.office.STRelationshipId newInstance() {
          return (com.microsoft.schemas.office.office.STRelationshipId) getTypeLoader().newInstance( type, null ); }
        
        public static com.microsoft.schemas.office.office.STRelationshipId newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (com.microsoft.schemas.office.office.STRelationshipId) getTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static com.microsoft.schemas.office.office.STRelationshipId parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (com.microsoft.schemas.office.office.STRelationshipId) getTypeLoader().parse( xmlAsString, type, null ); }
        
        public static com.microsoft.schemas.office.office.STRelationshipId parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.microsoft.schemas.office.office.STRelationshipId) getTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static com.microsoft.schemas.office.office.STRelationshipId parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.office.office.STRelationshipId) getTypeLoader().parse( file, type, null ); }
        
        public static com.microsoft.schemas.office.office.STRelationshipId parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.office.office.STRelationshipId) getTypeLoader().parse( file, type, options ); }
        
        public static com.microsoft.schemas.office.office.STRelationshipId parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.office.office.STRelationshipId) getTypeLoader().parse( u, type, null ); }
        
        public static com.microsoft.schemas.office.office.STRelationshipId parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.office.office.STRelationshipId) getTypeLoader().parse( u, type, options ); }
        
        public static com.microsoft.schemas.office.office.STRelationshipId parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.office.office.STRelationshipId) getTypeLoader().parse( is, type, null ); }
        
        public static com.microsoft.schemas.office.office.STRelationshipId parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.office.office.STRelationshipId) getTypeLoader().parse( is, type, options ); }
        
        public static com.microsoft.schemas.office.office.STRelationshipId parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.office.office.STRelationshipId) getTypeLoader().parse( r, type, null ); }
        
        public static com.microsoft.schemas.office.office.STRelationshipId parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (com.microsoft.schemas.office.office.STRelationshipId) getTypeLoader().parse( r, type, options ); }
        
        public static com.microsoft.schemas.office.office.STRelationshipId parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (com.microsoft.schemas.office.office.STRelationshipId) getTypeLoader().parse( sr, type, null ); }
        
        public static com.microsoft.schemas.office.office.STRelationshipId parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.microsoft.schemas.office.office.STRelationshipId) getTypeLoader().parse( sr, type, options ); }
        
        public static com.microsoft.schemas.office.office.STRelationshipId parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (com.microsoft.schemas.office.office.STRelationshipId) getTypeLoader().parse( node, type, null ); }
        
        public static com.microsoft.schemas.office.office.STRelationshipId parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (com.microsoft.schemas.office.office.STRelationshipId) getTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        @Deprecated
        public static com.microsoft.schemas.office.office.STRelationshipId parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.microsoft.schemas.office.office.STRelationshipId) getTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        @Deprecated
        public static com.microsoft.schemas.office.office.STRelationshipId parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (com.microsoft.schemas.office.office.STRelationshipId) getTypeLoader().parse( xis, type, options ); }
        
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
