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

import java.util.ArrayList;
import java.util.List;

import com.deepoove.poi.data.RenderData;

/**
 * Comment structure
 * 
 * @author Sayi
 *
 */
public class CommentRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    private List<RenderData> contents = new ArrayList<>();
    private CommentContent commentContent;

    public List<RenderData> getContents() {
        return contents;
    }

    public void setContents(List<RenderData> contents) {
        this.contents = contents;
    }

    public CommentContent getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(CommentContent comment) {
        this.commentContent = comment;
    }

}
