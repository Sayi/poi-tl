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

import java.util.ArrayList;
import java.util.List;

import com.deepoove.poi.data.RenderData;

/**
 * Comment structure
 * <p>
 * Holds the visible runs of the tag together with an optional
 * {@link CommentContent} that supplies the comment author, date and body. Bind it
 * to a tag handled by {@link CommentRenderPolicy}.
 * </p>
 * 
 * @author Sayi
 *
 */
public class CommentRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    private List<RenderData> contents = new ArrayList<>();
    private CommentContent commentContent;

    /**
     * Returns the runs rendered where the tag sits.
     *
     * @return the visible content, never {@code null}
     */
    public List<RenderData> getContents() {
        return contents;
    }

    /**
     * Sets the runs rendered where the tag sits.
     *
     * @param contents the visible content
     */
    public void setContents(List<RenderData> contents) {
        this.contents = contents;
    }

    /**
     * Returns the comment bubble metadata and body.
     *
     * @return the comment content, may be {@code null}
     */
    public CommentContent getCommentContent() {
        return commentContent;
    }

    /**
     * Sets the comment bubble metadata and body.
     *
     * @param comment the comment content
     */
    public void setCommentContent(CommentContent comment) {
        this.commentContent = comment;
    }

}
