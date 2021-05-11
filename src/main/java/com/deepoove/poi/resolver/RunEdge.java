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
package com.deepoove.poi.resolver;

/**
 * Running boundary
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

    public RunEdge(int allPos, String tag) {
        this.allEdge = allPos;
        this.tag = tag;
    }

    public int getAllEdge() {
        return allEdge;
    }

    public void setAllEdge(int allEdge) {
        this.allEdge = allEdge;
    }

    public int getRunPos() {
        return runPos;
    }

    public void setRunPos(int runPos) {
        this.runPos = runPos;
    }

    public int getRunEdge() {
        return runEdge;
    }

    public void setRunEdge(int runEdge) {
        this.runEdge = runEdge;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getText() {
        return text;
    }

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
