package com.deepoove.poi.policy;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.xwpf.NiceXWPFDocument;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 参考 DocxRenderPolicy
 *
 * @author gudqs7
 * @date 2021/11/20
 */
public class NiceXWPFDocumentRenderPolicy extends AbstractRenderPolicy<Object> {

	private final byte[] inputStreamBytes;
	private final Configure config;

	public NiceXWPFDocumentRenderPolicy(String absolutePath) throws FileNotFoundException {
		this(new File(absolutePath));
	}

	public NiceXWPFDocumentRenderPolicy(File templateFile) throws FileNotFoundException {
		this(templateFile, Configure.createDefault());
	}

	public NiceXWPFDocumentRenderPolicy(InputStream inputStream) {
		this(inputStream, Configure.createDefault());
	}

	public NiceXWPFDocumentRenderPolicy(String absolutePath, Configure config) throws FileNotFoundException {
		this(new File(absolutePath), config);
	}

	public NiceXWPFDocumentRenderPolicy(File templateFile, Configure config) throws FileNotFoundException {
		this(new FileInputStream(templateFile), config);
	}

	public NiceXWPFDocumentRenderPolicy(InputStream inputStream, Configure config) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream(4096);
			byte[] buff = new byte[4096];
			int len;
			while ((len = inputStream.read(buff)) != -1) {
				bos.write(buff, 0, len);
			}
			bos.flush();
			bos.close();
			this.inputStreamBytes = bos.toByteArray(); ;
			this.config = config;
		} catch (Exception e) {
			throw new RuntimeException("init template doc error "+e.getMessage());
		}
	}

	@Override
	public void doRender(RenderContext<Object> context) throws Exception {
		NiceXWPFDocument doc = context.getXWPFDocument();
		XWPFTemplate template = context.getTemplate();

		List<NiceXWPFDocument> xwpfDocumentList = new ArrayList<>();
		Object contextData = context.getData();
		if (contextData instanceof List) {
			List list = (List) contextData;
			for (Object data : list) {
				renderByTemplate(xwpfDocumentList, data);
			}
		} else if (contextData instanceof Object[]) {
			Object[] objArray = (Object[]) contextData;
			for (Object data : objArray) {
				renderByTemplate(xwpfDocumentList, data);
			}
		} else {
			renderByTemplate(xwpfDocumentList, contextData);
		}

		if (xwpfDocumentList.isEmpty()) {
			context.getRun().setText("", 0);
			return;
		}

		doc = doc.merge(xwpfDocumentList, context.getRun());
		template.reload(doc);
	}

	private void renderByTemplate(List<NiceXWPFDocument> xwpfDocumentList, Object data) {
		XWPFTemplate copyOfTemplate = XWPFTemplate.compile(new ByteArrayInputStream(inputStreamBytes), config);
		xwpfDocumentList.add(copyOfTemplate.render(data).getXWPFDocument());
	}


}
