package com.deepoove.poi.tl.render;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;

public class NumberingContinueTest {

    @SuppressWarnings("serial")
    @Test
    public void testNumberingContinue() throws Exception {
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/template/iterable_numbering.docx");
        template.render(new HashMap<String, Object>() {
            {
                put("list", Arrays.asList(new MyList(), new MyList()));
            }
        });
        template.writeToFile("out_iterable_numbering.docx");
    }

    static class MyList {
        List<SubList> sublist = new ArrayList<>();

        public MyList() {
            sublist.add(new SubList());
            sublist.add(new SubList());
        }
    }

    static class SubList {
        List<String> subsublist;

        public SubList() {
            subsublist = Arrays.asList("1", "2", "3");
        }
    }

}
