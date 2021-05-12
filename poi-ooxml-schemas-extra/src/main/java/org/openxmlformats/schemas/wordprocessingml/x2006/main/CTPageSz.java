/*
 * XML Type:  CT_PageSz
 * Namespace: http://schemas.openxmlformats.org/wordprocessingml/2006/main
 * Java type: org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz
 *
 * Automatically generated - do not modify.
 */
package org.openxmlformats.schemas.wordprocessingml.x2006.main;


/**
 * An XML CT_PageSz(@http://schemas.openxmlformats.org/wordprocessingml/2006/main).
 *
 * This is a complex type.
 */
public interface CTPageSz extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(CTPageSz.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sD023D6490046BA0250A839A9AD24C443").resolveHandle("ctpagesz2d12type");
    
    /**
     * Gets the "w" attribute
     */
    java.math.BigInteger getW();
    
    /**
     * Gets (as xml) the "w" attribute
     */
    org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure xgetW();
    
    /**
     * True if has "w" attribute
     */
    boolean isSetW();
    
    /**
     * Sets the "w" attribute
     */
    void setW(java.math.BigInteger w);
    
    /**
     * Sets (as xml) the "w" attribute
     */
    void xsetW(org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure w);
    
    /**
     * Unsets the "w" attribute
     */
    void unsetW();
    
    /**
     * Gets the "h" attribute
     */
    java.math.BigInteger getH();
    
    /**
     * Gets (as xml) the "h" attribute
     */
    org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure xgetH();
    
    /**
     * True if has "h" attribute
     */
    boolean isSetH();
    
    /**
     * Sets the "h" attribute
     */
    void setH(java.math.BigInteger h);
    
    /**
     * Sets (as xml) the "h" attribute
     */
    void xsetH(org.openxmlformats.schemas.wordprocessingml.x2006.main.STTwipsMeasure h);
    
    /**
     * Unsets the "h" attribute
     */
    void unsetH();
    
    /**
     * Gets the "orient" attribute
     */
    org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation.Enum getOrient();
    
    /**
     * Gets (as xml) the "orient" attribute
     */
    org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation xgetOrient();
    
    /**
     * True if has "orient" attribute
     */
    boolean isSetOrient();
    
    /**
     * Sets the "orient" attribute
     */
    void setOrient(org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation.Enum orient);
    
    /**
     * Sets (as xml) the "orient" attribute
     */
    void xsetOrient(org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation orient);
    
    /**
     * Unsets the "orient" attribute
     */
    void unsetOrient();
    
    /**
     * Gets the "code" attribute
     */
    java.math.BigInteger getCode();
    
    /**
     * Gets (as xml) the "code" attribute
     */
    org.openxmlformats.schemas.wordprocessingml.x2006.main.STDecimalNumber xgetCode();
    
    /**
     * True if has "code" attribute
     */
    boolean isSetCode();
    
    /**
     * Sets the "code" attribute
     */
    void setCode(java.math.BigInteger code);
    
    /**
     * Sets (as xml) the "code" attribute
     */
    void xsetCode(org.openxmlformats.schemas.wordprocessingml.x2006.main.STDecimalNumber code);
    
    /**
     * Unsets the "code" attribute
     */
    void unsetCode();
    
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
                stl = org.apache.xmlbeans.XmlBeans.typeLoaderForClassLoader(org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz.class.getClassLoader());
                typeLoader = new java.lang.ref.SoftReference(stl);
            }
            return stl;
        }

        public  static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz newInstance() {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz) getTypeLoader().newInstance( type, null ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz) getTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz) getTypeLoader().parse( xmlAsString, type, null ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz) getTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz) getTypeLoader().parse( file, type, null ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz) getTypeLoader().parse( file, type, options ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz) getTypeLoader().parse( u, type, null ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz) getTypeLoader().parse( u, type, options ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz) getTypeLoader().parse( is, type, null ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz) getTypeLoader().parse( is, type, options ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz) getTypeLoader().parse( r, type, null ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz) getTypeLoader().parse( r, type, options ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz) getTypeLoader().parse( sr, type, null ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz) getTypeLoader().parse( sr, type, options ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz) getTypeLoader().parse( node, type, null ); }
        
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz) getTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        @Deprecated
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz) getTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        @Deprecated
        public static org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz) getTypeLoader().parse( xis, type, options ); }
        
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
