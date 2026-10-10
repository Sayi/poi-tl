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
package com.deepoove.poi.resolver;

/**
 * Running boundary
 * <p>
 * It records where a tag starts or ends: the global offset inside the paragraph
 * text, the index of the run and the offset inside that run.
 * </p>
 * 
 * @author Sayi
 * @version 0.0.2
 */
public class RunEdge {

    /**
     * The global position of the starting text inside the paragraph
     */
    private int allEdge;

    /**
     * The position of the starting text inside the run
     */
    private int runEdge;

    /**
     * The position of the run within the paragraph where the starting text is
     * located
     */
    private int runPos;

    private String tag;

    private String text;

    /**
     * Create a run edge
     * 
     * @param allPos global position of the starting text inside the paragraph
     * @param tag    matched tag text
     */
    public RunEdge(int allPos, String tag) {
        this.allEdge = allPos;
        this.tag = tag;
    }

    /**
     * Get the global position of the starting text inside the paragraph
     * 
     * @return global position inside the paragraph
     */
    public int getAllEdge() {
        return allEdge;
    }

    /**
     * Set the global position of the starting text inside the paragraph
     * 
     * @param allEdge global position inside the paragraph
     */
    public void setAllEdge(int allEdge) {
        this.allEdge = allEdge;
    }

    /**
     * Get the position of the run within the paragraph
     * 
     * @return position of the run within the paragraph
     */
    public int getRunPos() {
        return runPos;
    }

    /**
     * Set the position of the run within the paragraph
     * 
     * @param runPos position of the run within the paragraph
     */
    public void setRunPos(int runPos) {
        this.runPos = runPos;
    }

    /**
     * Get the position of the starting text inside the run
     * 
     * @return position of the starting text inside the run
     */
    public int getRunEdge() {
        return runEdge;
    }

    /**
     * Set the position of the starting text inside the run
     * 
     * @param runEdge position of the starting text inside the run
     */
    public void setRunEdge(int runEdge) {
        this.runEdge = runEdge;
    }

    /**
     * Get the matched tag text
     * 
     * @return matched tag text
     */
    public String getTag() {
        return tag;
    }

    /**
     * Set the matched tag text
     * 
     * @param tag matched tag text
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Get the text of the run that holds the boundary
     * 
     * @return text of the run
     */
    public String getText() {
        return text;
    }

    /**
     * Set the text of the run that holds the boundary
     * 
     * @param text text of the run
     */
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("The run position of ").append(tag).append(" is ").append(runPos)
                .append(", Offset in run is ").append(runEdge);
        return sb.toString();
    }

}
