package com.deepoove.poi.tl.issue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.tl.source.XWPFTestSupport;

@DisplayName("Issue157 引入文档变量null")
public class Issue157 {

    @Test
    public void testNullMerge() throws Exception {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("title", "testTitle");

        // 不加入这一段， 虽然 {{+teachers}} 设置值了，但是仍然不会渲染
        /*
         * List<Teacher> stuList = new ArrayList<Teacher>(); Teacher p1 = new Teacher();
         * p1.setName("test1"); stuList.add(p1);
         * 
         * p1 = new Teacher(); p1.setName("test2"); stuList.add(p1);
         * 
         * params.put("students", new DocxRenderData(new
         * File("src/test/resources/issue/test_teacher.docx"), stuList));
         */

        List<Teacher> teacherList = new ArrayList<Teacher>();
        Teacher t1 = new Teacher();
        t1.setName("t1");
        t1.setAge(18);
        teacherList.add(t1);

        t1 = new Teacher();
        t1.setName("t2");
        t1.setAge(36);
        teacherList.add(t1);

        params.put("teachers", new DocxRenderData(new File("src/test/resources/issue/157_MERGE.docx"), teacherList));

        XWPFTemplate doc = XWPFTemplate.compile("src/test/resources/issue/157.docx");
        doc.render(params);

        XWPFDocument document = XWPFTestSupport.readNewDocument(doc);
        XWPFParagraph paragraph = document.getParagraphArray(0);
        assertEquals(paragraph.getText(), "testTitle");
        paragraph = document.getParagraphArray(1);
        assertEquals(paragraph.getText(), "老师：t1\n" + "年龄：18");
        paragraph = document.getParagraphArray(2);
        assertEquals(paragraph.getText(), "老师：t2\n" + "年龄：36");

        document.close();

    }

    public static class Teacher {

        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

    }

}
