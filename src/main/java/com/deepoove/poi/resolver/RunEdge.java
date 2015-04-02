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

	private int allPos;

	private int runPos;

	private int runEdge;

	private String tag;
	private String text;

	public RunEdge(int allPos, String tag) {
		this.allPos = allPos;
		this.tag = tag;
	}

	public int getAllPos() {
		return allPos;
	}

	public void setAllPos(int allPos) {
		this.allPos = allPos;
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
		return text;
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
		return "第" + runPos + "个run,tag标签为" + tag + ",在这个run内的边界位置为：" + runEdge;
	}

}
