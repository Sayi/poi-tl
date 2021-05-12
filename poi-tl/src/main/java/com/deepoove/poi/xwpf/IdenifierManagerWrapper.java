package com.deepoove.poi.xwpf;

import org.apache.poi.ooxml.util.IdentifierManager;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.util.ReflectionUtils;

public class IdenifierManagerWrapper {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String XPATH_DRAWING = "declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' \n"
            + "        declare namespace mc='http://schemas.openxmlformats.org/markup-compatibility/2006' .//mc:AlternateContent/mc:Choice/w:drawing";

    private IdentifierManager drawingIdManager;

    public IdenifierManagerWrapper(XWPFDocument document) {
        try {
            drawingIdManager = (IdentifierManager) ReflectionUtils.getValue("drawingIdManager", document);
            // 0 will corrupt the document
            reserve(0);
        } catch (Exception e) {
            logger.info("Cannot get the global drawingIdManager, use custom identifier.");
        }
    }

    public boolean isValid() {
        return null != drawingIdManager;
    }

    public long reserveNew() {
        return drawingIdManager.reserveNew();
    }

    public long reserve(long id) {
        return drawingIdManager.reserve(id);

    }

}
