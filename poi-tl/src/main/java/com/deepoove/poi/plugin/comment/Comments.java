/*
 * Copyright 2014-2026 Sayi
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
import java.util.Date;

import com.deepoove.poi.data.DocumentRenderData;
import com.deepoove.poi.data.Documents;
import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RenderDataBuilder;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.Texts;

/**
 * Factory method to create {@link CommentRenderData}
 * <p>
 * Start with {@link #of()} or one of the text overloads and chain the builder to
 * describe both the rendered content and the comment bubble.
 * </p>
 * 
 * @author Sayi
 *
 */
public final class Comments {

    private Comments() {
    }

    /**
     * Creates a builder for a comment without initial content.
     *
     * @return a new comment builder
     */
    public static CommentBuilder of() {
        return new CommentBuilder();
    }

    /**
     * Creates a builder for a comment whose visible content is the given text.
     *
     * @param text the text rendered where the tag sits
     * @return a new comment builder
     */
    public static CommentBuilder of(TextRenderData text) {
        CommentBuilder builder = new CommentBuilder();
        builder.addText(text);
        return builder;
    }

    /**
     * Creates a builder for a comment whose visible content is the given text.
     *
     * @param text the text rendered where the tag sits
     * @return a new comment builder
     */
    public static CommentBuilder of(String text) {
        CommentBuilder builder = new CommentBuilder();
        builder.addText(text);
        return builder;
    }

    /**
     * Builder to build {@link CommentRenderData}
     * <p>
     * Chains the visible content ({@code addText}, {@code addPicture},
     * {@code addSubComment}) with the optional comment bubble described by
     * {@code signature} and {@code comment}.
     * </p>
     */
    public static class CommentBuilder implements RenderDataBuilder<CommentRenderData> {

        private CommentRenderData data;

        private CommentBuilder() {
            data = new CommentRenderData();
        }

        /**
         * Sets the author, initials and date shown on the comment bubble.
         *
         * @param author   the comment author
         * @param initials the author initials
         * @param date     the creation date, may be {@code null}
         * @return this builder
         */
        public CommentBuilder signature(String author, String initials, Calendar date) {
            CommentContent comment = getCommentContent();
            comment.setAuthor(author);
            comment.setInitials(initials);
            comment.setDate(date);
            return this;
        }

        /**
         * Sets the author, initials and date shown on the comment bubble.
         *
         * @param author   the comment author
         * @param initials the author initials
         * @param date     the creation date, may be {@code null}
         * @return this builder
         */
        public CommentBuilder signature(String author, String initials, Date date) {
            if (null == date) {
                return signature(author, initials, (Calendar) null);
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return signature(author, initials, calendar);
        }

        /**
         * Sets the rich body shown inside the comment bubble.
         *
         * @param content the comment body
         * @return this builder
         */
        public CommentBuilder comment(DocumentRenderData content) {
            CommentContent comment = getCommentContent();
            comment.setContent(content);
            return this;
        }

        /**
         * Sets a plain-text body shown inside the comment bubble.
         *
         * @param text the comment body text
         * @return this builder
         */
        public CommentBuilder comment(String text) {
            return comment(Documents.of().addParagraph(Paragraphs.of(text).create()).create());
        }

        /**
         * Appends text rendered where the tag sits.
         *
         * @param text the text to append
         * @return this builder
         */
        public CommentBuilder addText(TextRenderData text) {
            data.getContents().add(text);
            return this;
        }

        /**
         * Appends text rendered where the tag sits.
         *
         * @param text the text to append
         * @return this builder
         */
        public CommentBuilder addText(String text) {
            data.getContents().add(Texts.of(text).create());
            return this;
        }

        /**
         * Appends a picture rendered where the tag sits.
         *
         * @param picture the picture to append
         * @return this builder
         */
        public CommentBuilder addPicture(PictureRenderData picture) {
            data.getContents().add(picture);
            return this;
        }

        /**
         * Appends a nested comment as part of the visible content.
         *
         * @param subcomment the nested comment data
         * @return this builder
         */
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

        /**
         * Returns the assembled comment data.
         *
         * @return the comment render data
         */
        @Override
        public CommentRenderData create() {
            return data;
        }

    }
}
