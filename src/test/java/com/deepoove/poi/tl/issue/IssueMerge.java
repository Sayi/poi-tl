package com.deepoove.poi.tl.issue;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocxRenderData;

public class IssueMerge {

    @SuppressWarnings("serial")
    @Test
    public void test143() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>();
        datas.put("date", "2018-11-12");
        datas.put("first", new DocxRenderData(new File("src/test/resources/issue/merge_1.docx"), new ArrayList<Map<String, Object>>(){{
            add(new HashMap<String, Object>(){{
                put("f_title", "一级标题1");
                put("second", new DocxRenderData(new File("src/test/resources/issue/merge_2.docx"), new ArrayList<Map<String, Object>>(){{
                    add(new HashMap<String, Object>(){{
                        put("s_title", "二级标题1");
                        put("three", new DocxRenderData(new File("src/test/resources/issue/merge_3.docx"), new ArrayList<Map<String, Object>>(){{
                            add(new HashMap<String, Object>(){{
                                put("t_title", "三级标题1");
                                put("content", "三级内容1");
                            }});
                            add(new HashMap<String, Object>(){{
                                put("t_title", "三级标题2");
                                put("content", "三级内容2");
                            }});
                            add(new HashMap<String, Object>(){{
                                put("t_title", "三级标题2");
                                put("content", "三级内容2");
                            }});
                        }}));
                    }});
                    add(new HashMap<String, Object>(){{
                        put("s_title", "二级标题2");
                        put("three", new DocxRenderData(new File("src/test/resources/issue/merge_3.docx"), new ArrayList<Map<String, Object>>(){{
                            add(new HashMap<String, Object>(){{
                                put("t_title", "三级标题1");
                                put("content", "三级内容1");
                            }});
                            add(new HashMap<String, Object>(){{
                                put("t_title", "三级标题2");
                                put("content", "三级内容2");
                            }});
                            add(new HashMap<String, Object>(){{
                                put("t_title", "三级标题2");
                                put("content", "三级内容2");
                            }});
                        }}));
                    }});
                    
                }}));
            }});

            
        
        }}));

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/issue/merge.docx")
                .render(datas);;

        FileOutputStream out = new FileOutputStream("out_issue_merge.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

}
