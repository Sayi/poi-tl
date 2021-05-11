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

import static com.deepoove.poi.policy.DocumentRenderPolicy.Helper.renderDocument;
import static com.deepoove.poi.policy.ParagraphRenderPolicy.Helper.renderParagraph;

import java.math.BigInteger;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.data.Paragraphs;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.util.ParagraphUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.deepoove.poi.xwpf.XWPFParagraphWrapper;

/**
 * comment render
 * 
 * @author Sayi
 *
 */
public class CommentRenderPolicy extends AbstractRenderPolicy<CommentRenderData> {

    @Override
    protected boolean validate(CommentRenderData data) {
        if (null == data) return false;
        if (null == data.getContents()) {
            throw new RenderException("CommentRenderData must set content!");
        }
        return true;
    }

    @Override
    public void doRender(RenderContext<CommentRenderData> context) throws Exception {
        Helper.renderComment(context.getRun(), context.getData());
    }

    @Override
    protected void afterRender(RenderContext<CommentRenderData> context) {
        clearPlaceholder(context, false);
    }

    public static class Helper {

        public static void renderComment(XWPFRun run, CommentRenderData data) throws Exception {
            XWPFParagraph paragraph = (XWPFParagraph) run.getParent();
            XWPFParagraphWrapper parentContext = new XWPFParagraphWrapper(paragraph);
            BigInteger cId = BigInteger.ZERO;
            CommentContent commentContent = data.getCommentContent();
            if (null != commentContent) {
                XWPFComments comments = ((NiceXWPFDocument) paragraph.getDocument()).createComments();
                XWPFComment newComment = comments.addComment();
                newComment.setAuthor(commentContent.getAuthor());
                newComment.setDate(commentContent.getDate());
                newComment.setInitials(commentContent.getInitials());
                renderDocument(newComment.createParagraph().createRun(), commentContent.getContent());
                cId = newComment.getCtComment().getId();
                parentContext.insertNewCommentRangeStart(run, cId);
            }
            renderParagraph(run, Paragraphs.of().addList(data.getContents()).create());
            if (null != commentContent) {
                parentContext.insertNewCommentRangeEnd(run, cId);
                XWPFRun newRun = parentContext.insertNewRun(ParagraphUtils.getRunPos(run));
                newRun.getCTR().addNewCommentReference().setId(cId);
            }
        }

    }

}
