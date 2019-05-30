package com.deepoove.poi.util;

import java.util.ArrayList;
import java.util.List;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.exception.RenderException;
import com.deepoove.poi.policy.DocxRenderPolicy;
import com.deepoove.poi.policy.MiniTableRenderPolicy;
import com.deepoove.poi.policy.NumbericRenderPolicy;
import com.deepoove.poi.policy.PictureRenderPolicy;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.policy.TextRenderPolicy;
import com.deepoove.poi.template.ElementTemplate;

/**
 * @author Sayi
 * @version 
 */
@Deprecated
public final class CodeGenUtils {
    
    private static final String importStr = "import com.deepoove.poi.config.Name;\n"
            + "import com.deepoove.poi.data.PictureRenderData;\n"
            + "import com.deepoove.poi.data.MiniTableRenderData;\n"
            + "import com.deepoove.poi.data.TextRenderData;\n"
            + "import com.deepoove.poi.data.DocxRenderData;\n"
            + "import com.deepoove.poi.data.NumbericRenderData;\n";

    public static String generateJavaObject(XWPFTemplate template, String packageStr, String className){
        if (null == template) throw new IllegalArgumentException("template is null,should be setted first.");
        List<ElementTemplate> elementTemplates = template.getElementTemplates();
        if (null == elementTemplates || elementTemplates.isEmpty()) return "";
        Configure config = template.getConfig();
        RenderPolicy policy = null;
        List<String> existFields = new ArrayList<String>();
        StringBuffer sb= new StringBuffer();
        StringBuffer sbGetterSetter= new StringBuffer();
        sb.append(packageStr).append("\n");
        sb.append(importStr);
        sb.append("public class " + upCaseFirstChar(className) + "{").append("\n");
        for (ElementTemplate runTemplate : elementTemplates) {
            policy = config.getPolicy(runTemplate.getTagName(), runTemplate.getSign());
            if (null == policy) 
                throw new RenderException("cannot find render policy: [" + runTemplate.getTagName() + "]");
            
            String tagName = runTemplate.getTagName();
            String field = tagName;
            if (existFields.contains(field)) continue;
            else existFields.add(field);
            if (isOptimusFeild(tagName)) {
                sb.append("@Name(\"" + tagName + "\")\n");
                field = optimusFeild(tagName);
            }
            
            if (policy instanceof TextRenderPolicy){
                sb.append("private String ").append(field).append(";\n");
                sbGetterSetter.append(genGetterSetter("String", field));
            }else if (policy instanceof MiniTableRenderPolicy){
                sb.append("private MiniTableRenderData ").append(field).append(";\n");
                sbGetterSetter.append(genGetterSetter("TableRenderData", field));
            }else if (policy instanceof PictureRenderPolicy){
                sb.append("private PictureRenderData ").append(field).append(";\n");
                sbGetterSetter.append(genGetterSetter("PictureRenderData", field));
            }else if (policy instanceof NumbericRenderPolicy){
                sb.append("private NumbericRenderData ").append(field).append(";\n");
                sbGetterSetter.append(genGetterSetter("NumbericRenderData", field));
            }else if (policy instanceof DocxRenderPolicy){
                sb.append("private DocxRenderData ").append(field).append(";\n");
                sbGetterSetter.append(genGetterSetter("DocxRenderData", field));
            }else{
                sb.append("private Object ").append(field).append(";\n");
                sbGetterSetter.append(genGetterSetter("Object", field));
            }  
        }
        return sb.append(sbGetterSetter.toString()).append("\n").append("}").toString();
    }

    public static String genGetterSetter(String type, String field) {
        StringBuffer sb = new StringBuffer();
        sb.append("public void set").append(upCaseFirstChar(field)).append("(" + type + " ").append(field).append("){\n")
        .append("    this.").append(field).append(" = ").append(field).append(";\n}\n");
        sb.append("public " + type + " get").append(upCaseFirstChar(field)).append("(){\n")
        .append("    return this.").append(field).append(";\n}\n");
        return sb.toString();
    }
    
    public static String upCaseFirstChar(String str) {
        if (null == str) return null;
        char character = str.charAt(0);
        return (character + "").toUpperCase() + str.substring(1);
    }
    
    public static boolean isOptimusFeild(String str) {
        if (null == str) return false;
        String[] split = str.split("_");
        if (split.length <= 1) return false;
        return true;
    }

    public static String optimusFeild(String str) {
        if (null == str) return str;
        String[] split = str.split("_");
        if (split.length <= 1) return str;
        StringBuffer sb = new StringBuffer(split[0]);
        for (int i = 1; i < split.length; i++) {
            sb.append(upCaseFirstChar(split[i]));
        }
        return sb.toString();
    }

}
