package com.deepoove.poi.tl.policy;

import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.LineSpacingRule;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.policy.MiniTableRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.template.run.RunTemplate;

public class MyTableRenderPolicy extends MiniTableRenderPolicy {

    @Override
    protected void afterRender(RenderContext<MiniTableRenderData> context) {
        XWPFRun run = ((RunTemplate) context.getEleTemplate()).getRun();
        // int posOfParagraph = doc.getPosOfParagraph(run.getParagraph());
        // doc.removeBodyElement(posOfParagraph);
        IRunBody parent = run.getParent();
        if (parent instanceof XWPFParagraph) {
            // Hack
            ((XWPFParagraph) parent).setSpacingBetween(0, LineSpacingRule.AUTO);
        }
    }

}
