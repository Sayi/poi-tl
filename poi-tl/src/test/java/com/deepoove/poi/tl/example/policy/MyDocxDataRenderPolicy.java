package com.deepoove.poi.tl.example.policy;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

import java.util.List;

/**
 * @author WQ
 * @date 2021/11/20
 */
public class MyDocxDataRenderPolicy extends AbstractRenderPolicy<List<NiceXWPFDocument>> {

	@Override
	public void doRender(RenderContext<List<NiceXWPFDocument>> context) throws Exception {
		NiceXWPFDocument doc = context.getXWPFDocument();
		XWPFTemplate template = context.getTemplate();
		List<NiceXWPFDocument> data = context.getData();
		if (data == null || data.isEmpty()) {
			return;
		}

		doc = doc.merge(data, context.getRun());
		template.reload(doc);
		System.out.println("");
	}


}
