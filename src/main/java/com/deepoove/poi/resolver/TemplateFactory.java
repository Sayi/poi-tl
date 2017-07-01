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

import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.deepoove.poi.config.GramerSymbol;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;

/**
 * basic docx element：run
 * 
 * @author Sayi
 * @version 1.0.0
 */
public class TemplateFactory extends ElementTemplate {

	public static RunTemplate createRunTemplate(String tag, List<Character> gramerChars, XWPFRun run) {
		RunTemplate template = new RunTemplate();
		
		char fisrtChar = tag.charAt(0);
		Character symbol = Character.valueOf('\0');
		for (Character chara : gramerChars){
			if (chara.equals(fisrtChar)){
				symbol = Character.valueOf(fisrtChar);
				break;
			}
		}
		template.setSource(GramerSymbol.GRAMER_PREFIX + tag + GramerSymbol.GRAMER_SUFFIX);
		template.setTagName(symbol.equals(Character.valueOf('\0')) ? tag : tag.substring(1));
		template.setSign(symbol);
		template.setRun(run);
		return template;
	}
	
	

}
