//package com.deepoove.poi.tl.policy.ref;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//import java.io.FileInputStream;
//import java.util.HashMap;
//import java.util.List;
//
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFPicture;
//import org.apache.poi.xwpf.usermodel.XWPFTable;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import com.deepoove.poi.XWPFTemplate;
//import com.deepoove.poi.config.Configure;
//import com.deepoove.poi.policy.ref.OptionalTextPictureRefRenderPolicy;
//import com.deepoove.poi.policy.ref.OptionalTextTableRefRenderPolicy;
//import com.deepoove.poi.policy.ref.ReplaceOptionalTextPictureRefRenderPolicy;
//
//@DisplayName("ReferencePolicy test case")
//public class ReferencePolicyTest {
//
//    private static String template_file = "src/test/resources/template/reference_policy.docx";
//
//    @Test
//    @DisplayName("Replace placeholder picture with 'let's img' as optional text")
//    public void testReplacePictureByOptionalText() throws Exception {
//        Configure configure = Configure.newBuilder()
//                .referencePolicy(new ReplaceOptionalTextPictureRefRenderPolicy("let's img",
//                        new FileInputStream("src/test/resources/sayi.png"), XWPFDocument.PICTURE_TYPE_PNG))
//                .build();
//
//        XWPFTemplate template = XWPFTemplate.compile(template_file, configure).render(new HashMap<>());
//
//        template.writeToFile("out_reference_replace_optional_picture.docx");
//    }
//
//    @Test
//    public void testOptionalTextTable() throws Exception {
//        Configure configure = Configure.newBuilder().referencePolicy(new OptionalTextTableRefRenderPolicy() {
//
//            @Override
//            public void doRender(XWPFTable t, XWPFTemplate template) {
//                System.out.println(t);
//                assertNotNull(t);
//            }
//
//            @Override
//            public String optionalText() {
//                return "let's table";
//            }
//        }).build();
//        XWPFTemplate.compile(template_file, configure).render(new HashMap<>()).close();
//
//    }
//
//    @Test
//    public void testOptionalTextPicture() throws Exception {
//        Configure configure = Configure.newBuilder().referencePolicy(new OptionalTextPictureRefRenderPolicy() {
//
//            @Override
//            public void doRender(List<XWPFPicture> t, XWPFTemplate template) {
//                System.out.println(t);
//                assertNotNull(t);
//                assertEquals(t.size(), 6);
//            }
//
//            @Override
//            public String optionalText() {
//                return "let's img";
//            }
//        }).build();
//        XWPFTemplate.compile(template_file, configure).render(new HashMap<>()).close();;
//
//    }
//
//}
