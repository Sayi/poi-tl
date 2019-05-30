/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.resolver;

/**
 * 奔跑的边界
 * 
 * @author Sayi
 * @version 0.0.2
 */
public class RunEdge {

    /**
     * 起始文本在段落内部的全局位置
     */
    private int allEdge;

    /**
     * 起始文本在run内部的位置
     */
    private int runEdge;

    /**
     * 起始文本所在的run在段落内的位置
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
        return "段落内第" + runPos + "个run,标签为" + tag + ", 在这个run内的边界位置为：" + runEdge;
    }

}
