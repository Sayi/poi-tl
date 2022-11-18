package com.deepoove.poi.plugin.toc;

import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.xwpf.BodyContainer;
import com.deepoove.poi.xwpf.BodyContainerFactory;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Experimental: Table of contents（false）
 * @author bajie
 *
 */
public class TocRenderFalsePolicy extends AbstractRenderPolicy<Object> {

    @Override
    protected void afterRender(RenderContext<Object> context) {
        // 清空绑定标签内容
        clearPlaceholder(context, true);
    }

    @Override
    public void doRender(RenderContext<Object> context) throws Exception {
        createTOC(context.getXWPFDocument());
    }

    public void createTOC(NiceXWPFDocument document) {
        // 找到有关键字所在位置
        XWPFParagraph paragraph = null;
        XmlCursor xmlCursor = document.getDocument().getBody().newCursor();
        xmlCursor.selectPath("./*");
        while (xmlCursor.toNextSelection()) {
            // 找到占位符所在位置
			XmlObject xmlObject = xmlCursor.getObject();
			if (xmlObject instanceof CTP) {
                XWPFParagraph xwpfParagraph = new XWPFParagraph((CTP) xmlObject, document);
                if ("$toc$".equals(xwpfParagraph.getText())) {
                    paragraph = xwpfParagraph;
                    break;
                }
            }
        }
        // 关闭游标
        xmlCursor.dispose();
        if (paragraph == null) {
            throw new RuntimeException("没有找到目录占位符，请在所需位置插入 $toc$");
        }
        // 存储标题段落
        List<XWPFParagraph> titleList = new ArrayList<>();
        // 循环文档中所有段落
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph par : paragraphs) {
            String parStyle = par.getStyle();
            // 过滤出所有标题段落
            if (parStyle != null && parStyle.matches("\\d+")) {
                int level = Integer.parseInt(parStyle);
                if (level > 3) {
                    // 过滤出3级以内的标题
                    continue;
                }
                // 集合不能便利的同时修改，需要放到另一个集合中后面一起修改
                titleList.add(par);
            }
        }

        for (XWPFParagraph par : titleList) {
            XmlCursor pCursor = paragraph.getCTP().newCursor();
            //获取书签，书签的对应关系很重要，关系到目录能否正常跳转
            List<CTBookmark> bookmarkList = par.getCTP().getBookmarkStartList();

            CTP p = document.insertNewParagraph(pCursor).getCTP();
            CTPPr ctpPr = p.addNewPPr();
            ctpPr.addNewPStyle().setVal("TOC" + par.getStyle());
            CTTabs tabs = ctpPr.addNewTabs();
            CTTabStop tab = tabs.addNewTab();
            tab.setVal(STTabJc.RIGHT);
            tab.setLeader(STTabTlc.DOT);
            tab.setPos(BigInteger.valueOf(8290));
            ctpPr.addNewRPr().addNewNoProof();


            CTHyperlink ctHyperlink = p.addNewHyperlink();
            ctHyperlink.setAnchor(bookmarkList.get(0).getName());
            CTR run = ctHyperlink.addNewR();
            run.setRsidR("00045404".getBytes());
            run.setRsidRPr("00045404".getBytes());
            run.addNewRPr().addNewNoProof();
            run.addNewT().setStringValue(par.getText());
            run.addNewTab();

            run = ctHyperlink.addNewR();
            run.addNewRPr().addNewNoProof();
            // 设置页码，但是页码是很难计算的这里暂时无法提供
            run.addNewT().setStringValue(".");

            pCursor.dispose();
        }

        // 删除占位符
        List<XWPFRun> runs = paragraph.getRuns();
        for (XWPFRun run : runs) {
            BodyContainer bodyContainer = BodyContainerFactory.getBodyContainer(run);
            bodyContainer.clearPlaceholder(run);
        }
    }
}
