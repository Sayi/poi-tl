//package com.deepoove.poi.plugin.markdown;
//
//import java.io.File;
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.io.FileUtils;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.reflect.TypeToken;
//
//public class DocusaurusTest {
//
//    String docFolder = "/Users/sai/Sayi/gitlab/tsin_api_gw/docs/zh/latest";
//
//    @BeforeEach
//    @SuppressWarnings("unchecked")
//    public void init() throws IOException {
//        Path path = Paths.get("docusaurus");
//        Files.createDirectories(path);
//
//        FileUtils.cleanDirectory(new File("docusaurus"));
//
//        Collection<File> listFiles = FileUtils.listFiles(new File(docFolder), new String[] { "png" }, true);
//        for (File src : listFiles) {
//            FileUtils.copyFile(src, new File("docusaurus/" + src.getName()));
//
//        }
//    }
//
//    @Test
//    public void testConvertAllInOne() throws Exception {
//        final StringBuilder all = new StringBuilder();
//
//        String toc = new String(Files.readAllBytes(Paths.get(docFolder, "config.json")));
//
//        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
//        Type type = new TypeToken<Map<String, Object>>() {
//        }.getType();
//        Map<String, Object> map = gson.fromJson(toc, type);
//
//        List<Map<String, Object>> contents = (List<Map<String, Object>>) map.get("sidebar");
//
//        contents.forEach((k) -> {
//            System.out.println(k);
//            if ("doc".equals(k.get("type"))) {
//                all.append("\n# " + k.get("id") + "\n");
//                try {
//                    all.append(new String(Files.readAllBytes(Paths.get(docFolder, k.get("id") + ".md"))));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    
//                }
//            } else  if ("category".equals(k.get("type"))) {
//                all.append("\n# " + k.get("label") + "\n");
//                
//                List<Map<String, Object>> items = (List<Map<String, Object>>) k.get("items");
//                items.forEach((t) -> {
//                   if ("category".equals(t.get("type"))) {
//                        all.append("\n# " + t.get("label") + "\n");
//                        
//                        List<String> docItems = (List<String>) t.get("items");
//                        for (String d : docItems) {
//                            all.append("\n# " + d + "\n");
//                            try {
//                                all.append(new String(Files.readAllBytes(Paths.get(docFolder, d + ".md"))));
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                                
//                            }
//                        }
//                    }
//
//                });
//            }
//
//        });
//
////        node.accept(new AbstractVisitor() {
////            @Override
////            public void visit(BulletList listBlock) {
////                Node node = listBlock.getFirstChild();
////                while (null != node) {
////                    if (node instanceof ListItem) {
////                        Node itemNode = node.getFirstChild();
////                        while (null != itemNode) {
////                            Paragraph p = (Paragraph) itemNode;
////                            Node toc = p.getFirstChild();
////                            Link t = (Link) toc;
////                            String destination = t.getDestination();
////                            String title = ((Text) t.getFirstChild()).getLiteral();
////                            all.append("\n## " + title + "\n");
////                            try {
////                                all.append(new String(
////                                        Files.readAllBytes(Paths.get(docFolder, destination, "README.md"))));
////                            } catch (IOException e) {
////                                e.printStackTrace();
////                            }
////                            itemNode = itemNode.getNext();
////                        }
////                    }
////                    node = node.getNext();
////                }
////            }
////        });
//
//        System.out.println(all);
//        Path file = Files.createFile(Paths.get("docusaurus/all.md"));
//        Files.write(file, all.toString().getBytes());
//
//    }
//
//}
