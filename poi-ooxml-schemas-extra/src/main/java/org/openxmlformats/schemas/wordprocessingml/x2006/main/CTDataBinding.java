/*
 * XML Type:  CT_DataBinding
 * Namespace: http://schemas.openxmlformats.org/wordprocessingml/2006/main
 * Java type: org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.wordprocessingml.x2006.main;


/**
 * An XML CT_DataBinding(@http://schemas.openxmlformats.org/wordprocessingml/2006/main).
 *
 * This is a complex type.
 */
public interface CTDataBinding extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CTDataBinding.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD023D6490046BA0250A839A9AD24C443").resolveHandle("ctdatabinding9077type");
    
    /**
     * Gets the "prefixMappings" attribute
     */
    java.lang.String getPrefixMappings();
    
    /**
     * Gets (as xml) the "prefixMappings" attribute
     */
    org.openxmlformats.schemas.wordprocessingml.x2006.main.STString xgetPrefixMappings();
    
    /**
     * True if has "prefixMappings" attribute
     */
    boolean isSetPrefixMappings();
    
    /**
     * Sets the "prefixMappings" attribute
     */
    void setPrefixMappings(java.lang.String prefixMappings);
    
    /**
     * Sets (as xml) the "prefixMappings" attribute
     */
    void xsetPrefixMappings(org.openxmlformats.schemas.wordprocessingml.x2006.main.STString prefixMappings);
    
    /**
     * Unsets the "prefixMappings" attribute
     */
    void unsetPrefixMappings();
    
    /**
     * Gets the "xpath" attribute
     */
    java.lang.String getXpath();
    
    /**
     * Gets (as xml) the "xpath" attribute
     */
    org.openxmlformats.schemas.wordprocessingml.x2006.main.STString xgetXpath();
    
    /**
     * Sets the "xpath" attribute
     */
    void setXpath(java.lang.String xpath);
    
    /**
     * Sets (as xml) the "xpath" attribute
     */
    void xsetXpath(org.openxmlformats.schemas.wordprocessingml.x2006.main.STString xpath);
    
    /**
     * Gets the "storeItemID" attribute
     */
    java.lang.String getStoreItemID();
    
    /**
     * Gets (as xml) the "storeItemID" attribute
     */
    org.openxmlformats.schemas.wordprocessingml.x2006.main.STString xgetStoreItemID();
    
    /**
     * Sets the "storeItemID" attribute
     */
    void setStoreItemID(java.lang.String storeItemID);
    
    /**
     * Sets (as xml) the "storeItemID" attribute
     */
    void xsetStoreItemID(org.openxmlformats.schemas.wordprocessingml.x2006.main.STString storeItemID);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {

        private static java.lang.ref.SoftReference<org.apache.xmlbeans.SchemaTypeLoader> typeLoader;

        private static synchronized org.apache.xmlbeans.SchemaTypeLoader getTypeLoader() {
            org.apache.xmlbeans.SchemaTypeLoader stl = (typeLoader == null) ? null : typeLoader.get();
            if (stl == null) {
                stl = org.apache.xmlbeans.XmlBeans.typeLoaderForClassLoader(org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding.class.getClassLoader());
                typeLoader = new java.lang.ref.SoftReference(stl);
            }
            return stl;
        }

        public  static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding newInstance() {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding) getTypeLoader().newInstance( type, null ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding) getTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding) getTypeLoader().parse( xmlAsString, type, null ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding) getTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding) getTypeLoader().parse( file, type, null ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding) getTypeLoader().parse( file, type, options ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding) getTypeLoader().parse( u, type, null ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding) getTypeLoader().parse( u, type, options ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding) getTypeLoader().parse( is, type, null ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding) getTypeLoader().parse( is, type, options ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding) getTypeLoader().parse( r, type, null ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding) getTypeLoader().parse( r, type, options ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding) getTypeLoader().parse( sr, type, null ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding) getTypeLoader().parse( sr, type, options ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding) getTypeLoader().parse( node, type, null ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding) getTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        @Deprecated
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding) getTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        @Deprecated
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDataBinding) getTypeLoader().parse( xis, type, options ); }
        
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
