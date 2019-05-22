/*
 * Copyright 2014-2019 the original author or authors.
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

/**
 * 模板表达式的模式
 * 
 * @author Sayi
 * @version
 */
public enum ELModeEnum {

    /**
     * 标准模式
     */
    POI_TL_STANDARD_MODE,
    /**
     * 严格模式
     */
    POI_TL_STICT_MODE,
    /**
     * Spring EL模式
     */
    SPEL_MODE;

}
