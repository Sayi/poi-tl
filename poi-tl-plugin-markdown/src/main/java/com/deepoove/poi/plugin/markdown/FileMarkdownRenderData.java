/*
 * Copyright 2014-2021 Sayi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.plugin.markdown;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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

public class FileMarkdownRenderData extends MarkdownRenderData {

    private static final long serialVersionUID = 1L;

    private String path;

    public String getMarkdown() {
        try {
            return suggest();
        } catch (Exception e) {
            throw new RuntimeException("", e);
        }
    }

    private String suggest() throws Exception {
        Path filePath = Paths.get(path);
        if (!Files.isDirectory(filePath)) {
            return new String(Files.readAllBytes(filePath));
        }
        Path docsifyPath = Paths.get(path, "_sidebar.md");
        if (!Files.exists(docsifyPath)) {
            Path readme = Paths.get(path, "README.md");
            if (!Files.exists(readme)) {
                throw new RuntimeException("directory is not docsify, no _sidebar\\README.md file.");
            }
            return new String(Files.readAllBytes(readme));
        }
        StringBuilder all = new StringBuilder();
        String toc = new String(Files.readAllBytes(Paths.get(path, "_sidebar.md")));
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
                                all.append(new String((destination.endsWith("md") || destination.endsWith("MD"))
                                        ? Files.readAllBytes(Paths.get(path, destination))
                                        : Files.readAllBytes(Paths.get(path, destination, "README.md"))));
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
        return all.toString();

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
