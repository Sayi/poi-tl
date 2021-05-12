package com.deepoove.poi.xwpf;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ooxml.POIXMLTypeLoader;
import org.apache.poi.openxml4j.opc.PackageNamespaces;
import org.apache.xmlbeans.XmlOptions;

public class DefaultXmlOptions {

    private static final String MS_OFFICE_URN = "urn:schemas-microsoft-com:office:office";
    private static final String MS_EXCEL_URN = "urn:schemas-microsoft-com:office:excel";
    private static final String MS_WORD_URN = "urn:schemas-microsoft-com:office:word";
    private static final String MS_VML_URN = "urn:schemas-microsoft-com:vml";

    public static final XmlOptions OPTIONS_INNER;
    public static final XmlOptions OPTIONS_OUTER;

    static {
        OPTIONS_INNER = new XmlOptions();
        OPTIONS_INNER.setSaveInner();
        OPTIONS_INNER.setUseDefaultNamespace();
        OPTIONS_INNER.setSaveAggressiveNamespaces();
        OPTIONS_INNER.setCharacterEncoding("UTF-8");
        OPTIONS_INNER.setEntityExpansionLimit(1);
        Map<String, String> map = new HashMap<>();
        map.put("http://schemas.openxmlformats.org/drawingml/2006/main", "a");
        map.put("http://schemas.openxmlformats.org/drawingml/2006/chart", "c");
        map.put("http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing", "wp");
        map.put(PackageNamespaces.MARKUP_COMPATIBILITY, "ve");
        map.put("http://schemas.openxmlformats.org/officeDocument/2006/math", "m");
        map.put("http://schemas.openxmlformats.org/officeDocument/2006/relationships", "r");
        map.put("http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes", "vt");
        map.put("http://schemas.openxmlformats.org/presentationml/2006/main", "p");
        map.put("http://schemas.openxmlformats.org/wordprocessingml/2006/main", "w");
        map.put("http://schemas.microsoft.com/office/word/2006/wordml", "wne");
        map.put(MS_OFFICE_URN, "o");
        map.put(MS_EXCEL_URN, "x");
        map.put(MS_WORD_URN, "w10");
        map.put(MS_VML_URN, "v");
        OPTIONS_INNER.setSaveSuggestedPrefixes(Collections.unmodifiableMap(map));

        OPTIONS_OUTER = POIXMLTypeLoader.DEFAULT_XML_OPTIONS;
    }

}
