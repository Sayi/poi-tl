package com.deepoove.poi.tl.plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.plugin.table.MultipleRowTableRenderPolicy;

public class MultipleRowTableRenderPolicyTest {

    @Test
    public void testMultiRow() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("title", "某某某会议");
        params.put("date", new Date());
        params.put("address", "某某会议室");

        List<Report> reports = new ArrayList<>();
        reports.add(new Report("王五", new Date(), "汇报内容1"));
        reports.add(new Report("张三", new Date(), "汇报内容2"));
        reports.add(new Report("李四", new Date(), "汇报内容3"));
        params.put("reports", reports);

        ConfigureBuilder builder = Configure.builder();
        builder.bind("reports", new MultipleRowTableRenderPolicy());
        XWPFTemplate xt = XWPFTemplate.compile("src/test/resources/template/render-multiple-row.docx", builder.build())
                .render(params);
        xt.writeToFile("out_render-multiple-row.docx");
    }

    static class Report {
        private String author;

        private Date time;

        private String content;

        public Report(String author, Date time, String content) {
            this.author = author;
            this.time = time;
            this.content = content;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
