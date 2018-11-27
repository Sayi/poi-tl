package com.deepoove.poi.tl.issue;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocxRenderData;

public class Issue157 {

    @Test
    public void testNullMerge() throws Exception {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("title", "testTitle");

        // 不加入这一段， 虽然 {{+teachers}} 设置值了，但是仍然不会渲染
        /*
         * List<Teacher> stuList = new ArrayList<Teacher>(); Teacher p1 = new
         * Teacher(); p1.setName("test1"); stuList.add(p1);
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

        params.put("teachers", new DocxRenderData(
                new File("src/test/resources/issue/test_teacher.docx"), teacherList));

        XWPFTemplate doc = XWPFTemplate.compile("src/test/resources/issue//test_stu_tea.docx");
        doc.render(params);

        FileOutputStream fos = new FileOutputStream("out_issue_157.docx");
        doc.write(fos);
        fos.flush();
        fos.close();

    }

}
