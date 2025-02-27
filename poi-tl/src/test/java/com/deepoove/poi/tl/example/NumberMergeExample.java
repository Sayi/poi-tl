package com.deepoove.poi.tl.example;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.Includes;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * @author vinceLin054
 * @version NumberMergeExample.java, v 0.1 2024年08月14日 14:34
 */
public class NumberMergeExample {


    /**
     * case1: 存在相同样式及不同样式的序号
     * @throws Exception
     */
    @Test
    public void testNumberMergeCase1() throws Exception {
        XWPFTemplate.compile("src/test/resources/number/case1_1.docx").render(new HashMap<String, Object>() {
            {
                put("term1", Includes.ofLocal("src/test/resources/number/case1_2.docx").create());
                put("term2", Includes.ofLocal("src/test/resources/number/case1_3.docx").create());
            }
        }).writeToFile("src/test/resources/number/case1_result.docx");
    }


    /**
     * case2: 不存在相同的样式
     * @throws Exception
     */
    @Test
    public void testNumberMergeCase2() throws Exception {
        XWPFTemplate.compile("src/test/resources/number/case2_1.docx").render(new HashMap<String, Object>() {
            {
                put("term", Includes.ofLocal("src/test/resources/number/case2_2.docx").create());
            }
        }).writeToFile("src/test/resources/number/case2_result.docx");
    }
}