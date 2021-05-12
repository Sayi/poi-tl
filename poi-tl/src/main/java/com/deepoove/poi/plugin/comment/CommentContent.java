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

import java.io.Serializable;
import java.util.Calendar;

import com.deepoove.poi.data.DocumentRenderData;

/**
 * @author Sayi
 *
 */
public class CommentContent implements Serializable {

    private static final long serialVersionUID = 1L;
    private String author;
    private String initials;
    private Calendar date;

    private DocumentRenderData content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public DocumentRenderData getContent() {
        return content;
    }

    public void setContent(DocumentRenderData content) {
        this.content = content;
    }

}
