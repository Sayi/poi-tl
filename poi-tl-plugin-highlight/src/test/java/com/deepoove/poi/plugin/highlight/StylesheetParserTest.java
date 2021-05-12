package com.deepoove.poi.plugin.highlight;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.deepoove.poi.plugin.highlight.converter.SelectorStyle;
import com.deepoove.poi.plugin.highlight.converter.StylesheetParser;

public class StylesheetParserTest {
    @Test
    public void testCss() throws Exception {
        parseStylesheet("agate");
        parseStylesheet("androidstudio");
        parseStylesheet("atom-one-dark");
        parseStylesheet("atom-one-light");
        parseStylesheet("darcula");
        parseStylesheet("dark");
        parseStylesheet("default");
        parseStylesheet("googlecode");
        parseStylesheet("github");
        parseStylesheet("hybrid");
        parseStylesheet("idea");
        parseStylesheet("monokai");
        parseStylesheet("monokai-sublime");
        parseStylesheet("ocean");
        parseStylesheet("rainbow");
        parseStylesheet("solarized-dark");
        parseStylesheet("solarized-light");
        parseStylesheet("stackoverflow-light");
        parseStylesheet("stackoverflow-dark");
        parseStylesheet("tomorrow");
        parseStylesheet("tomorrow-night");
        parseStylesheet("vs");
        parseStylesheet("xcode");
        parseStylesheet("zenburn");

    }

    private void parseStylesheet(String name) throws Exception {
        List<SelectorStyle> allCssStyles = StylesheetParser.parse("highlightcss/" + name + ".css");
        assertFalse(allCssStyles.isEmpty());
        System.out.println("============" + name + "======================");
        for (SelectorStyle cs : allCssStyles) {
            System.out.println(cs.getSelectorName() + ":" + cs.getPropertyValues());
        }
    }
}
