package com.deepoove.poi.tl.issue;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFNum;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.tl.source.XWPFTestSupport;
import com.deepoove.poi.xwpf.XWPFNumberingWrapper;

@DisplayName("Issue257 列表序号合并重新开始编号")
public class Issue257 {

    @Test
    public void testDocxMerge() throws Exception {

        // 编号继续前一个编号可以修改为重新开始编号
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("docx",
                new DocxRenderData(new File("src/test/resources/issue/257_MERGE.docx"), Arrays.asList(1, 2)));

        XWPFTemplate doc = XWPFTemplate.compile("src/test/resources/issue/257.docx");
        doc.render(params);

        XWPFDocument document = XWPFTestSupport.readNewDocument(doc);
        XWPFNumbering numbering = document.getNumbering();
        XWPFNumberingWrapper numberingWrapper = new XWPFNumberingWrapper(numbering);
        List<XWPFNum> nums = numberingWrapper.getNums();
        Set<BigInteger> abstracNumSet = new HashSet<>();
        for (XWPFNum num : nums) {
            BigInteger abstractNumId = num.getCTNum().getAbstractNumId().getVal();
            assertTrue(abstracNumSet.add(abstractNumId));
        }

        document.close();

    }

}
