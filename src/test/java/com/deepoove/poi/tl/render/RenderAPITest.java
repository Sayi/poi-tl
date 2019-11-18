package com.deepoove.poi.tl.render;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ELMode;
import com.deepoove.poi.policy.AbstractRenderPolicy.DiscardHandler;
import com.deepoove.poi.render.Render;
import com.deepoove.poi.render.RenderAPI;
import com.deepoove.poi.render.RenderDataCompute;

/**
 * @author Sayi
 * @version 0.0.4
 */
public class RenderAPITest {

    @SuppressWarnings({ "serial", "deprecation" })
    @Test
    public void testRenderAPI() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("author", "deepoove");
                put("date", "2016-09-40");
                put("dfa", "呵呵");
                put("fafd", "我被渲染了");
            }
        };
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/run_merge.docx");

        // 测试是否映射正确
        RenderAPI.debug(template, datas);

        // 测试是否可以自渲染
        RenderAPI.selfRender(template);

        FileOutputStream out = new FileOutputStream("out_self.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

    @SuppressWarnings("serial")
    @Test
    public void testRenderMerge() throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                put("author", "deepoove");
                put("date", "2016-09-40");
                put("dfa", "呵呵");
                put("fafd", "我被渲染了");
            }
        };

        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/run_merge.docx")
                .render(datas);;

        FileOutputStream out = new FileOutputStream("out_template_run_merge.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }

    public class DataMerge {
        private String author = "deepoove";
        private String date = "2019-05-10";
        private String dfa = "呵呵";
        private String fafd = "我被渲染了";

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDfa() {
            return dfa;
        }

        public void setDfa(String dfa) {
            this.dfa = dfa;
        }

        public String getFafd() {
            return fafd;
        }

        public void setFafd(String fafd) {
            this.fafd = fafd;
        }

    }

    // 自定义一个RenderDataCompute，当表达式找不到时不会报错
    RenderDataCompute renderDataCompute = new RenderDataCompute() {

        SpelExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext(new DataMerge());

        @Override
        public Object compute(String el) {
            try {
                return parser.parseExpression(el).getValue(context);
            } catch (Exception e) {
            }
            return null;
        }

    };

    @Test
    public void testRenderMerge2() throws Exception {
        Configure config = Configure.newBuilder().setElMode(ELMode.SPEL_MODE)
                .buildGramer("{{", "}}").build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/run_merge.docx", config);
        new Render(renderDataCompute).render(template);

        template.writeToFile("out_template_run_merge2.docx");
    }

    @Test
    public void testRenderMerge3() throws Exception {
        // Configure config =
        // Configure.newBuilder().buildReg("((?!\\$\\{)(?!\\}).)*").buildGramer("${",
        // "}").build();
        Configure config = Configure.newBuilder().setElMode(ELMode.SPEL_MODE)
                .buildGramer("${", "}").setValidErrorHandler(new DiscardHandler()).build();
        XWPFTemplate template = XWPFTemplate.compile("src/test/resources/run_merge3.docx", config);
        new Render(renderDataCompute).render(template);

        template.writeToFile("out_template_run_merge3.docx");
    }

}
