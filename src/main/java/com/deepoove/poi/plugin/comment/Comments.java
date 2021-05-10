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
package com.deepoove.poi.plugin.comment;

import java.util.Calendar;

import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.data.Documents;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RenderDataBuilder;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.Texts;

/**
 * Factory method to create {@link CommentRenderData}
 * 
 * @author Sayi
 *
 */
public final class Comments {

    private Comments() {
    }

    public static CommentBuilder of() {
        return new CommentBuilder();
    }

    public static CommentBuilder of(TextRenderData text) {
        CommentBuilder builder = new CommentBuilder();
        builder.addText(text);
        return builder;
    }

    public static CommentBuilder of(String text) {
        CommentBuilder builder = new CommentBuilder();
        builder.addText(text);
        return builder;
    }

    /**
     * Builder to build {@link CommentRenderData}
     *
     */
    public static class CommentBuilder implements RenderDataBuilder<CommentRenderData> {

        private CommentRenderData data;

        private CommentBuilder() {
            data = new CommentRenderData();
        }

        public CommentBuilder signature(String author, String initials, Calendar date) {
            CommentContent comment = getCommentContent();
            comment.setAuthor(author);
            comment.setInitials(initials);
            comment.setDate(date);
            return this;
        }

        public CommentBuilder comment(DocumentRenderData content) {
            CommentContent comment = getCommentContent();
            comment.setContent(content);
            return this;
        }

        public CommentBuilder comment(String text) {
            return comment(Documents.of().addParagraph(Paragraphs.of(text).create()).create());
        }

        public CommentBuilder addText(TextRenderData text) {
            data.getContents().add(text);
            return this;
        }

        public CommentBuilder addText(String text) {
            data.getContents().add(Texts.of(text).create());
            return this;
        }

        public CommentBuilder addPicture(PictureRenderData picture) {
            data.getContents().add(picture);
            return this;
        }

        public CommentBuilder addSubComment(CommentRenderData subcomment) {
            data.getContents().add(subcomment);
            return this;
        }

        private CommentContent getCommentContent() {
            CommentContent comment = data.getCommentContent();
            if (null == comment) {
                comment = new CommentContent();
                data.setCommentContent(comment);
            }
            return comment;
        }

        @Override
        public CommentRenderData create() {
            return data;
        }

    }
}
