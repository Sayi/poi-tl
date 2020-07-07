package com.deepoove.poi.policy.reference;

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
import com.deepoove.poi.policy.PictureRenderPolicy.Helper;
import com.deepoove.poi.template.PictureTemplate;
import com.deepoove.poi.util.ReflectionUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

public class DefaultPictureTemplateRenderPolicy
        extends AbstractTemplateRenderPolicy<PictureTemplate, PictureRenderData> {

    @Override
    public void doRender(PictureTemplate pictureTemplate, PictureRenderData picdata, XWPFTemplate template)
            throws Exception {
        XWPFPicture t = pictureTemplate.getPicture();
        NiceXWPFDocument doc = template.getXWPFDocument();
        int format = Helper.suggestFileType(picdata.getPath());
        byte[] data = picdata.getData();
        XWPFRun run = (XWPFRun) ReflectionUtils.getValue("run", t);
        if (run.getParent().getPart() instanceof XWPFHeader) {
            XWPFHeaderFooter headerFooter = (XWPFHeaderFooter) run.getParent().getPart();
            setPictureReference(t, headerFooter.addPictureData(data, format));

        } else if (run.getParent().getPart() instanceof XWPFFooter) {
            XWPFHeaderFooter headerFooter = (XWPFHeaderFooter) run.getParent().getPart();
            setPictureReference(t, headerFooter.addPictureData(data, format));
        } else {
            setPictureReference(t, doc.addPictureData(data, format));
        }
    }

    private void setPictureReference(XWPFPicture t, String relationId) {
        CTPicture ctPic = t.getCTPicture();
        CTBlipFillProperties bill = ctPic.getBlipFill();
        CTBlip blip = bill.getBlip();
        blip.setEmbed(relationId);
    }

}
