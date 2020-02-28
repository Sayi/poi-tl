package com.deepoove.poi.tl.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.Configure.ELMode;
import com.deepoove.poi.resolver.TemplateResolver;

@DisplayName("Tag Regex test case")
public class GramerRegexTest {

    @Test
    public void testDefaultRegex() {
        // 默认tag 正则：Chinese, letters, numbers and underscores
        String defaultRegex = Configure.newBuilder().build().getGrammerRegex();
        Pattern pattern = Pattern.compile(defaultRegex);
        testMatcherText(pattern);
        assertFalse(pattern.matcher("abc-123").matches());
    }

    @Test
    public void testCustomeRegex() {
        // 自定义tag 正则
        String customeRegex = "^[^\\.]+(\\.[^\\.]+)*$";
        Pattern pattern = Pattern.compile(customeRegex);
        testMatcherText(pattern);
    }

    private void testMatcherText(Pattern pattern) {
        assertTrue(pattern.matcher("123").matches());
        assertTrue(pattern.matcher("ABC").matches());
        assertTrue(pattern.matcher("abc123").matches());
        assertTrue(pattern.matcher("_123abc").matches());
        assertTrue(pattern.matcher("abc_123").matches());
        assertTrue(pattern.matcher("好123").matches());
        assertTrue(pattern.matcher("123好_好abc").matches());
        assertTrue(pattern.matcher("abc.123").matches());

        assertTrue(pattern.matcher("abc.123").matches());

        assertTrue(pattern.matcher("abc.123.123").matches());
        assertTrue(pattern.matcher("abc.好.123").matches());
        assertTrue(pattern.matcher("abc.好123").matches());
        assertTrue(pattern.matcher("好.123").matches());
        assertTrue(pattern.matcher("好.123.好").matches());

        assertFalse(pattern.matcher("好..123").matches());
        assertFalse(pattern.matcher("abc..123").matches());
        assertFalse(pattern.matcher("abc23.").matches());
        assertFalse(pattern.matcher("好123.").matches());
        assertFalse(pattern.matcher(".好123").matches());
    }

    @Test
    public void testSpELWithPrefixAndSuffixMatcher() {
        // Spring EL使用的正则，包含前后缀语法
        Configure config = Configure.newBuilder().setElMode(ELMode.SPEL_MODE).build();
        TemplateResolver resolver = new TemplateResolver(config);
        Pattern pattern = resolver.getTemplatePattern();

        // 1
        Matcher matcher = pattern.matcher("lowCase:{{name}}Upcase:{{name.toUpperCase()}}");

        matcher.find();
        assertEquals(matcher.start(), "lowCase:".length());
        assertEquals(matcher.group(), "{{name}}");
        assertEquals(matcher.end(), "lowCase:{{name}}".length());

        matcher.find();
        assertEquals(matcher.start(), "lowCase:{{name}}Upcase:".length());
        assertEquals(matcher.group(), "{{name.toUpperCase()}}");
        assertEquals(matcher.end(), "lowCase:{{name}}Upcase:{{name.toUpperCase()}}".length());
        assertFalse(matcher.find());

        // 2
        matcher = pattern.matcher("lowCase:{{nameUpcase:{{name.toUpperCase()}}");
        matcher.find();
        assertEquals(matcher.start(), "lowCase:{{nameUpcase:".length());
        assertEquals(matcher.group(), "{{name.toUpperCase()}}");
        assertEquals(matcher.end(), "lowCase:{{nameUpcase:{{name.toUpperCase()}}".length());
        assertFalse(matcher.find());

        // 3
        matcher = pattern.matcher("lowCase:{{name}}Upcase:name.toUpperCase()}}");
        matcher.find();
        assertEquals(matcher.start(), "lowCase:".length());
        assertEquals(matcher.group(), "{{name}}");
        assertEquals(matcher.end(), "lowCase:{{name}}".length());

        assertFalse(matcher.find());

    }

    @Test
    public void testSpELWithCustomPrefixAndSuffixMatcher() {
        // Spring EL使用的正则，包含前后缀语法
        Configure config = Configure.newBuilder().buildGramer("${", "}").setElMode(ELMode.SPEL_MODE).build();
        TemplateResolver resolver = new TemplateResolver(config);
        Pattern pattern = resolver.getTemplatePattern();
        
        // 1
        Matcher matcher = pattern.matcher("lowCase:${name}Upcase:${name.toUpperCase()}");

        matcher.find();
        assertEquals(matcher.start(), "lowCase:".length());
        assertEquals(matcher.group(), "${name}");
        assertEquals(matcher.end(), "lowCase:${name}".length());

        matcher.find();
        assertEquals(matcher.start(), "lowCase:${name}Upcase:".length());
        assertEquals(matcher.group(), "${name.toUpperCase()}");
        assertEquals(matcher.end(), "lowCase:${name}Upcase:${name.toUpperCase()}".length());
        assertFalse(matcher.find());

        // 2
        matcher = pattern.matcher("lowCase:${nameUpcase:${name.toUpperCase()}");
        matcher.find();
        assertEquals(matcher.start(), "lowCase:${nameUpcase:".length());
        assertEquals(matcher.group(), "${name.toUpperCase()}");
        assertEquals(matcher.end(), "lowCase:${nameUpcase:${name.toUpperCase()}".length());
        assertFalse(matcher.find());

        // 3
        matcher = pattern.matcher("lowCase:${name}Upcase:name.toUpperCase()}");
        matcher.find();
        assertEquals(matcher.start(), "lowCase:".length());
        assertEquals(matcher.group(), "${name}");
        assertEquals(matcher.end(), "lowCase:${name}".length());

        assertFalse(matcher.find());
    }

}
