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
package com.deepoove.poi.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 反射工具类
 * @author herowzz
 * @version 1.0.0
 */
public abstract class ReflectUtils {

	private static final Map<Class<?>, ClassProxy> CACHE_REFLECTOR = new ConcurrentHashMap<Class<?>, ClassProxy>();

	/**
	 * 获取bean的代理<br>
	 * 第一次加载后放入缓存中
	 * @param bean 需要代理对象
	 * @return bean所对应的代理对象
	 */
	public static ClassProxy fromCache(Object bean) {
		if (!CACHE_REFLECTOR.containsKey(bean.getClass())) {
			CACHE_REFLECTOR.put(bean.getClass(), new ClassProxy(bean));
		}
		return CACHE_REFLECTOR.get(bean.getClass());
	}

	public static class ClassProxy {

		private Map<String, Method> methodMap = new HashMap<String, Method>();
		private Class<?> classz;
		private Object bean;

		public ClassProxy(Object bean) {
			this.classz = bean.getClass();
			this.bean = bean;
			Method[] methods = classz.getMethods();
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				String methodName = method.getName();
				if (methodName.startsWith("get")) {
					String key = methodName.substring(3, methodName.length()).toLowerCase();
					methodMap.put(key, method);
				}
			}
		}

		/**
		 * 根据参数获取该参数的值
		 * @param propertyName 参数名称<br>
		 *            支持对象之间的关联.例:user.group.role.name
		 * @return 代理对象对应参数的值
		 */
		public Object getValue(String propertyName) {
			String[] properties = propertyName.split("\\.");
			if (properties.length == 1) {
				return bean;
			}
			ClassProxy proxy = this;
			for (int i = 1; i < properties.length; i++) {
				if (i < properties.length - 1)
					proxy = ReflectUtils.fromCache(proxy.getMethodValue(properties[i]));
				else
					return proxy.getMethodValue(properties[i]);
			}
			return "";
		}

		/**
		 * 根据参数获取该参数的值
		 * @param propertyName 参数名称
		 * @return 代理对象对应参数的值
		 */
		public Object getMethodValue(String propertyName) {
			Method method = methodMap.get(propertyName);
			if (method == null) {
				throw new RuntimeException("There is no getter for property named '" + propertyName + "'");
			}
			try {
				return method.invoke(bean);
			} catch (Exception e) {
				throw new RuntimeException("Attemp to getter property '" + propertyName + "' value cause error!", e);
			}
		}
	}

}
