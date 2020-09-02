package com.deepoove.poi.tl.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;

public class ConfigureBuilderTest {

    ConfigureBuilder builder;

    @BeforeEach
    public void init() {
        builder = Configure.builder();
    }

    @Test
    public void testSpringEL() {
        Configure config = builder.build();
        assertEquals(Configure.DEFAULT_GRAMER_REGEX, config.getGrammerRegex());

        config = builder.useSpringEL().build();
        assertNotEquals(Configure.DEFAULT_GRAMER_REGEX, config.getGrammerRegex());

    }

}
