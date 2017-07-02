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
package com.deepoove.poi.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deepoove.poi.policy.NumbericRenderPolicy;
import com.deepoove.poi.policy.PictureRenderPolicy;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.policy.SimpleTableRenderPolicy;
import com.deepoove.poi.policy.TextRenderPolicy;

/**
 * 插件化配置
 * 
 * @author Sayi
 * @version 1.0.0
 */
public class Configure {

	// Highest priority
	private Map<String, RenderPolicy> customPolicys = new HashMap<String, RenderPolicy>(6);
	// Low priority
	private Map<Character, RenderPolicy> defaultPolicys = new HashMap<Character, RenderPolicy>(12);
	private List<Character> gramerChars = new ArrayList<Character>();

	private Configure() {
		plugin(GramerSymbol.TEXT.getSymbol(), new TextRenderPolicy());
		plugin(GramerSymbol.IMAGE.getSymbol(), new PictureRenderPolicy());
		plugin(GramerSymbol.TABLE.getSymbol(), new SimpleTableRenderPolicy());
		plugin(GramerSymbol.NUMBERIC.getSymbol(), new NumbericRenderPolicy());
	}
	
	/**
	 * 获取默认配置
	 * @return
	 */
	public static Configure createDefault(){
		return new Configure();
	}


	/**
	 * 新增语法插件
	 * 
	 * @param c
	 *            模板语法
	 * @param policy
	 *            策略
	 */
	public Configure plugin(char c, RenderPolicy policy) {
		gramerChars.add(Character.valueOf(c));
		defaultPolicys.put(Character.valueOf(c), policy);
		return this;
	}

	/**
	 * 自定义模板
	 * 
	 * @param tagName
	 *            模板名称
	 * @param policy
	 *            策略
	 */
	public void customPolicy(String tagName, RenderPolicy policy) {
		customPolicys.put(tagName, policy);
	}

	public Map<Character, RenderPolicy> getDefaultPolicys() {
		return defaultPolicys;
	}

	public Map<String, RenderPolicy> getCustomPolicys() {
		return customPolicys;
	}

	public List<Character> getGramerChars() {
		return gramerChars;
	}

	public RenderPolicy getCustomPolicy(String tagName) {
		return customPolicys.get(tagName);
	}

	public RenderPolicy getDefaultPolicy(Character sign) {
		return defaultPolicys.get(sign);
	}

}
