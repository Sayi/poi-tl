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

package com.deepoove.poi.xwpf;

import java.util.List;

import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;

import com.deepoove.poi.util.ParagraphUtils;
import com.deepoove.poi.util.ReflectionUtils;

/**
 * {@link IBody} operation
 * 
 * @author Sayi
 *
 */
public interface BodyContainer extends ParentContext {

    /**
     * get the position of paragraph in bodyElements
     * 
     * @param ctp paragraph
     * @return the position of paragraph
     */
    default int getPosOfParagraphCTP(CTP ctp) {
        IBodyElement current;
        List<IBodyElement> bodyElements = getTarget().getBodyElements();
        for (int i = 0; i < bodyElements.size(); i++) {
            current = bodyElements.get(i);
            if (current.getElementType() == BodyElementType.PARAGRAPH) {
                if (((XWPFParagraph) current).getCTP().equals(ctp)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * get the position of paragraph in bodyElements
     * 
     * @param paragraph
     * @return the position of paragraph
     */
    default int getPosOfParagraph(XWPFParagraph paragraph) {
        return getPosOfParagraphCTP(paragraph.getCTP());
    }

    /**
     * get all bodyElements
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    default List<IBodyElement> getBodyElements() {
        return (List<IBodyElement>) ReflectionUtils.getValue("bodyElements", getTarget());
    }

    /**
     * remove body element from bodyElements
     * 
     * @param pos the position of bodyElement
     */
    void removeBodyElement(int pos);

    /**
     * insert paragraph at position of the cursor
     * 
     * @param insertPostionCursor
     * @return the inserted paragraph
     */
    default XWPFParagraph insertNewParagraph(XmlCursor insertPostionCursor) {
        return getTarget().insertNewParagraph(insertPostionCursor);
    }

    /**
     * insert paragraph at position of run
     * 
     * @param run
     * @return the inserted paragraph
     */
    default XWPFParagraph insertNewParagraph(XWPFRun run) {
        XmlCursor cursor = ((XWPFParagraph) run.getParent()).getCTP().newCursor();
        return insertNewParagraph(cursor);
    }

    /**
     * get the position of paragraph in paragraphs
     * 
     * @param paragraph
     * @return the position of paragraph
     */
    default int getParaPos(XWPFParagraph paragraph) {
        List<XWPFParagraph> paragraphs = getTarget().getParagraphs();
        for (int i = 0; i < paragraphs.size(); i++) {
            if (paragraphs.get(i) == paragraph) {
                return i;
            }
        }
        return -1;
    }

    /**
     * set paragraph at position
     * 
     * @param paragraph
     * @param pos
     */
    void setParagraph(XWPFParagraph paragraph, int pos);

    /**
     * container itself
     * 
     * @return
     */
    IBody getTarget();

    /**
     * insert table at position of the cursor
     * 
     * @param insertPostionCursor
     * @return the inserted table
     */
    default XWPFTable insertNewTbl(XmlCursor insertPostionCursor) {
        return getTarget().insertNewTbl(insertPostionCursor);
    }

    /**
     * get the position of table in tables
     * 
     * @param table
     * @return the position of table
     */
    default int getTablePos(XWPFTable table) {
        List<XWPFTable> tables = getTarget().getTables();
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i) == table) {
                return i;
            }
        }
        return -1;
    }

    /**
     * set table
     * 
     * @param tablePos
     * @param table
     */
    void setTable(int tablePos, XWPFTable table);

    /**
     * update body elements
     * 
     * @param bodyElement
     * @param copy
     */
    default void updateBodyElements(IBodyElement bodyElement, IBodyElement copy) {
        int pos = -1;
        List<IBodyElement> bodyElements = getBodyElements();
        for (int i = 0; i < bodyElements.size(); i++) {
            if (bodyElements.get(i) == bodyElement) {
                pos = i;
            }
        }
        if (-1 != pos) bodyElements.set(pos, copy);
    }

    /**
     * insert table at position of the run
     * 
     * @param run
     * @param row
     * @param col
     * @return
     */
    XWPFTable insertNewTable(XWPFRun run, int row, int col);

    /**
     * clear run
     * 
     * @param run
     */
    default void clearPlaceholder(XWPFRun run) {
        IRunBody parent = run.getParent();
        run.setText("", 0);
        if (parent instanceof XWPFParagraph) {
            String paragraphText = ParagraphUtils.trimLine((XWPFParagraph) parent);
            boolean havePictures = ParagraphUtils.havePictures((XWPFParagraph) parent);
            boolean havePageBreak = ParagraphUtils.havePageBreak((XWPFParagraph) parent);
            boolean haveObject = ParagraphUtils.haveObject((XWPFParagraph) parent);
            if ("".equals(paragraphText) && !havePictures && !havePageBreak && !haveObject) {
                int pos = getPosOfParagraph((XWPFParagraph) parent);
                removeBodyElement(pos);
            }
        }
    }

    /**
     * Get closely SectPr
     * 
     * @param element
     * @return
     */
    XWPFSection closelySectPr(IBodyElement element);

    /**
     * Get width of the element page
     * 
     * @param element
     * @return
     */
    int elementPageWidth(IBodyElement element);

}
