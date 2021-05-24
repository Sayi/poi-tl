package com.deepoove.poi.tl.plugin;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.util.LocaleUtil;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.data.Documents;
import com.deepoove.poi.data.Documents.DocumentBuilder;
import com.deepoove.poi.data.ParagraphRenderData;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.TableRenderData;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.Texts;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.comment.CommentRenderData;
import com.deepoove.poi.plugin.comment.CommentRenderPolicy;
import com.deepoove.poi.plugin.comment.Comments;
import com.deepoove.poi.plugin.comment.Comments.CommentBuilder;

public class CommentRenderPolicyTest {

    @Test
    public void testComment() throws IOException {
        ParagraphRenderData paragraph = Paragraphs.of()
                .addText("I consider myself the luckiest😁 man on the face of the")
                .addPicture(Pictures.ofLocal("src/test/resources/sayi.png").size(40, 40).create())
                .addText(Texts.of(" poi-tl").link("http://deepoove.com/poi-tl").create())
                .create();
        TableRenderData table = Tables.of(new String[][] { new String[] { "00", "01", "02", "03", "04" },
                new String[] { "10", "11", "12", "13", "14" }, new String[] { "20", "21", "22", "23", "24" },
                new String[] { "30", "31", "32", "33", "34" } }).create();

        CommentRenderData subcomment = Comments.of("鹅")
                .signature("Sayi", "s", Calendar.getInstance())
                .comment("鹅，是一种动物")
                .create();

        CommentRenderData comment = Comments.of()
                .addText("咏")
                .addSubComment(subcomment)
                .addPicture(Pictures.ofLocal("src/test/resources/sayi.png").size(20, 20).create())
                .signature("Sayi", "s", Calendar.getInstance())
                .comment(Documents.of()
                        .addParagraph(Paragraphs.of("作者骆宾王").create())
                        .addParagraph(paragraph)
                        .addTable(table)
                        .create())
                .create();

        Map<String, Object> data = new HashMap<>();
        data.put("comment", comment);
        data.put("title", "Sayi");
        data.put("picture", Pictures.ofLocal("src/test/resources/sayi.png").size(60, 60).create());
        data.put("table", table);
        data.put("list", Arrays.asList("one", "two"));

        Configure config = Configure.builder().bind("comment", new CommentRenderPolicy()).build();

        XWPFTemplate.compile("src/test/resources/template/render_comment.docx", config)
                .render(data)
                .writeToFile("out_render_comment.docx");
    }

    @Test
    public void testCommentExample() throws IOException {
        // comment
        CommentRenderData comment0 = newCommentBuilder().addText(Texts.of("咏鹅").fontSize(20).bold().create())
                .comment(Documents.of()
                        .addParagraph(Paragraphs.of(Pictures.ofLocal("src/test/resources/logo.png").create()).create())
                        .create())
                .create();
        CommentRenderData comment1 = newCommentBuilder().addText("骆宾王")
                .comment("骆宾王作为“初唐四杰”之一，对荡涤六朝文学颓波，革新初唐浮靡诗风。他一生著作颇丰，是一个才华横溢的诗人。")
                .create();
        CommentRenderData comment2 = newCommentBuilder().addText("曲项").comment("弯着脖子").create();
        CommentRenderData comment3 = newCommentBuilder().addText("拨").comment("划动").create();

        // document 
        DocumentBuilder documentBuilder = Documents.of()
                .addParagraph(Paragraphs.of().addComment(comment0).center().create());
        documentBuilder.addParagraph(Paragraphs.of().addComment(comment1).center().create());
        documentBuilder.addParagraph(Paragraphs.of("鹅，鹅，鹅，").addComment(comment2).addText("向天歌。").center().create());
        documentBuilder.addParagraph(Paragraphs.of("白毛浮绿水，红掌").addComment(comment3).addText("清波。").center().create());

        // render
        XWPFTemplate.create(documentBuilder.create(), Style.builder().buildFontFamily("微软雅黑").buildFontSize(14f).build())
                .writeToFile("out_render_comment_YONG.docx");
    }

    private CommentBuilder newCommentBuilder() {
        return Comments.of().signature("Sayi", "s", LocaleUtil.getLocaleCalendar());
    }

}
