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

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.deepoove.poi.data.DocumentRenderData;

/**
 * Author, timestamp and rich body of a single Word comment.
 * <p>
 * Instances are carried by {@link CommentRenderData} and rendered by
 * {@link CommentRenderPolicy} as a comment bubble anchored to the tag.
 * </p>
 *
 * @author Sayi
 *
 */
public class CommentContent implements Serializable {

    private static final long serialVersionUID = 1L;
    private String author;
    private String initials;
    private Calendar date;

    private DocumentRenderData content;

    /**
     * Returns the author shown in the comment bubble.
     *
     * @return the author name
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author shown in the comment bubble.
     *
     * @param author the author name
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns the author initials shown in the comment bubble.
     *
     * @return the initials
     */
    public String getInitials() {
        return initials;
    }

    /**
     * Sets the author initials shown in the comment bubble.
     *
     * @param initials the initials
     */
    public void setInitials(String initials) {
        this.initials = initials;
    }

    /**
     * Returns the comment creation date.
     *
     * @return the date, may be {@code null}
     */
    public Calendar getDate() {
        return date;
    }

    /**
     * Sets the comment creation date.
     *
     * @param date the date, may be {@code null}
     */
    public void setDate(Calendar date) {
        this.date = date;
    }

    /**
     * Sets the comment creation date from a {@link Date}.
     *
     * @param date the date, may be {@code null} to clear the value
     */
    public void setDate(Date date) {
        if (null == date) {
            this.date = null;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            this.date = cal;
        }
    }

    /**
     * Returns the rich body rendered inside the comment bubble.
     *
     * @return the comment body
     */
    public DocumentRenderData getContent() {
        return content;
    }

    /**
     * Sets the rich body rendered inside the comment bubble.
     *
     * @param content the comment body
     */
    public void setContent(DocumentRenderData content) {
        this.content = content;
    }

}
