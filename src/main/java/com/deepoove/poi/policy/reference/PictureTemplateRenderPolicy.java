package com.deepoove.poi.policy.reference;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFHeaderFooter;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTBlip;
import org.openxmlformats.schemas.drawingml.x2006.main.CTBlipFillProperties;
import org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.template.PictureTemplate;
import com.deepoove.poi.util.ReflectionUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

public class PictureTemplateRenderPolicy extends AbstractTemplateRenderPolicy<PictureTemplate, PictureRenderData> {

    @Override
    public void doRender(PictureTemplate pictureTemplate, PictureRenderData picdata, XWPFTemplate template)
            throws Exception {
        int format = XWPFDocument.PICTURE_TYPE_PNG;
        byte[] data = picdata.getData();
        XWPFPicture t = pictureTemplate.getPicture();
        NiceXWPFDocument doc = template.getXWPFDocument();
        String docId = null;
        String hid = null;
        String fid = null;
        try {
            logger.info("Replace the picture data for the reference object: {}", t);
            XWPFRun run = (XWPFRun) ReflectionUtils.getValue("run", t);
            if (run.getParent().getPart() instanceof XWPFHeader) {
                XWPFHeaderFooter headerFooter = (XWPFHeaderFooter) run.getParent().getPart();
                setPictureReference(t, hid == null ? hid = headerFooter.addPictureData(data, format) : hid);

            } else if (run.getParent().getPart() instanceof XWPFFooter) {
                XWPFHeaderFooter headerFooter = (XWPFHeaderFooter) run.getParent().getPart();
                setPictureReference(t, fid == null ? fid = headerFooter.addPictureData(data, format) : fid);
            } else {
                setPictureReference(t, docId == null ? docId = doc.addPictureData(data, format) : docId);
            }
        } catch (Exception e) {
            throw new RenderException("ReferenceRenderPolicy render error", e);
        }
    }

    private void setPictureReference(XWPFPicture t, String relationId) {
        CTPicture ctPic = t.getCTPicture();
        CTBlipFillProperties bill = ctPic.getBlipFill();
        CTBlip blip = bill.getBlip();
        blip.setEmbed(relationId);
    }

}
