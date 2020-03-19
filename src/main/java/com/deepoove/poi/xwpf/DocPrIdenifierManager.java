package com.deepoove.poi.xwpf;

import java.util.Random;

import org.apache.poi.ooxml.util.IdentifierManager;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.util.ReflectionUtils;

public class DocPrIdenifierManager {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private IdentifierManager drawingIdManager;
    private long offset;

    public DocPrIdenifierManager(XWPFDocument document) {
        try {
            drawingIdManager = (IdentifierManager) ReflectionUtils.getValue("drawingIdManager", document);
            offset = 991L * (new Random().nextInt(5) + 2);
        } catch (Exception e) {
            logger.info("Cannot get the global drawingIdManager, will use POI default behavior.");
        }
    }

    public boolean enableRegen() {
        return null != drawingIdManager && 0 != offset;
    }

    public long reserveNew() {
        return drawingIdManager.reserve(offset + drawingIdManager.reserveNew());
    }

}
