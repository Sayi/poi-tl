package com.deepoove.poi.plugin.markdown;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.BulletList;
import org.commonmark.node.Link;
import org.commonmark.node.ListItem;
import org.commonmark.node.Node;
import org.commonmark.node.Paragraph;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;

public class DocsifyTest {

    String docsifyFolder = "/Users/Sayi/docs";

//    @BeforeEach
    @SuppressWarnings("unchecked")
    public void init() throws IOException {
        Path path = Paths.get("docsify");
        Files.createDirectories(path);

        FileUtils.cleanDirectory(new File("docsify"));

        Collection<File> listFiles = FileUtils.listFiles(new File(docsifyFolder), new String[] { "png" }, true);
        for (File src : listFiles) {
            FileUtils.copyFile(src, new File("docsify/" + src.getName()));

        }
    }

//    @Test
    public void testConvertAllInOne() throws Exception {
        StringBuilder all = new StringBuilder();

        String toc = new String(Files.readAllBytes(Paths.get(docsifyFolder, "_sidebar.md")));
        List<Extension> extensions = Arrays.asList(TablesExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        Node node = parser.parse(toc);
        node.accept(new AbstractVisitor() {
            @Override
            public void visit(BulletList listBlock) {
                Node node = listBlock.getFirstChild();
                while (null != node) {
                    if (node instanceof ListItem) {
                        Node itemNode = node.getFirstChild();
                        while (null != itemNode) {
                            Paragraph p = (Paragraph) itemNode;
                            Node toc = p.getFirstChild();
                            Link t = (Link) toc;
                            String destination = t.getDestination();
                            String title = ((Text) t.getFirstChild()).getLiteral();
                            all.append("\n## " + title + "\n");
                            try {
                                all.append(new String(
                                        Files.readAllBytes(Paths.get(docsifyFolder, destination, "README.md"))));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            itemNode = itemNode.getNext();
                        }
                    }
                    node = node.getNext();
                }
            }
        });

        System.out.println(all);
        Path file = Files.createFile(Paths.get("docsify/all.md"));
        Files.write(file, all.toString().getBytes());

    }

}
