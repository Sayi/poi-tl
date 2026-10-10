package com.deepoove.poi.tl.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.config.GrammarSymbol;
import com.deepoove.poi.config.GramerSymbol;

public class ConfigureBuilderTest {

    ConfigureBuilder builder;

    @BeforeEach
    public void init() {
        builder = Configure.builder();
    }

    @Test
    public void testSpringEL() {
        Configure config = builder.build();
        assertEquals(Configure.DEFAULT_GRAMMAR_REGEX, config.getGrammarRegex());

        config = builder.useSpringEL().build();
        assertNotEquals(Configure.DEFAULT_GRAMMAR_REGEX, config.getGrammarRegex());

    }

    @Test
    @SuppressWarnings("deprecation")
    public void testDeprecatedSpellingStillWorks() {
        Configure legacy = builder.buildGramer("${", "}").buidIterableLeft('^').buildGrammerRegex("[a-z]+").build();
        Configure canonical = Configure.builder().buildGrammar("${", "}").buildIterableLeft('^')
                .buildGrammarRegex("[a-z]+").build();

        assertEquals(canonical.getGrammarPrefix(), legacy.getGramerPrefix());
        assertEquals(canonical.getGrammarSuffix(), legacy.getGramerSuffix());
        assertEquals(canonical.getGrammarRegex(), legacy.getGrammerRegex());
        assertEquals(canonical.getGrammarChars(), legacy.getGramerChars());
        assertEquals(canonical.getIterable(), legacy.getIterable());
        assertEquals(Configure.DEFAULT_GRAMMAR_REGEX, Configure.DEFAULT_GRAMER_REGEX);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testDeprecatedSymbolStillWorks() {
        assertEquals(GrammarSymbol.TEXT, GramerSymbol.TEXT);
        assertEquals(GrammarSymbol.ITERABLE_START.getSymbol(), GramerSymbol.ITERABLE_START.getSymbol());
        assertEquals(GrammarSymbol.BLOCK_END.getSymbol(), GramerSymbol.BLOCK_END.getSymbol());
    }

}
