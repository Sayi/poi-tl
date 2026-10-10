/*
 * Copyright 2014-2026 Sayi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.plugin.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FormulaRenderData and style")
class FormulaRenderDataTest {

    @Test
    void defaultsAreInlineAndInherited() {
        FormulaRenderData data = FormulaRenderData.of("x^2").build();
        assertTrue(!data.isDisplay());
        assertNull(data.getAltMeta());
        assertEquals(FormulaAlign.CENTER, data.getStyle().getAlign());
        assertNull(data.getStyle().getFontSize());
        assertNull(data.getStyle().getColor());
        assertNull(data.getStyle().getMathFont(), "no font is forced unless asked");
    }

    @Test
    void buildAndCreateProduceEquivalentData() {
        FormulaRenderDataBuilder builder = FormulaRenderData.of("\\frac{1}{2}").display().color("C00000").altMeta("alt");
        FormulaRenderData built = builder.build();
        FormulaRenderData created = builder.create();
        assertNotSame(built, created);
        assertEquals(built.getSource(), created.getSource());
        assertEquals(built.isDisplay(), created.isDisplay());
        assertEquals(built.getAltMeta(), created.getAltMeta());
        assertEquals(built.getStyle().getColor(), created.getStyle().getColor());
    }

    @Test
    void displayAndInlineCanBeToggled() {
        FormulaRenderDataBuilder builder = FormulaRenderData.of("x").display();
        assertTrue(builder.build().isDisplay());
        assertTrue(!builder.inline().build().isDisplay());
    }

    @Test
    void facadeShortcuts() {
        assertTrue(!Formulas.of("x").isDisplay());
        assertTrue(!Formulas.inline("x").isDisplay());
        assertTrue(Formulas.display("x").isDisplay());
        assertEquals(FormulaAlign.RIGHT, Formulas.display("x", FormulaAlign.RIGHT).getStyle().getAlign());
    }

    @Test
    void illegalValuesAreRejectedAtBuildTime() {
        assertThrows(IllegalArgumentException.class, () -> FormulaRenderData.of("x").color("ZZZ"));
        assertThrows(IllegalArgumentException.class, () -> FormulaRenderData.of("x").color("12345"));
        assertThrows(IllegalArgumentException.class, () -> FormulaRenderData.of("x").fontSize(0));
        assertThrows(IllegalArgumentException.class, () -> FormulaRenderData.of(null));
    }

    @Test
    void colorsAcceptTheHashPrefixAndAreNormalized() {
        assertEquals("C00000", FormulaRenderData.of("x").color("#c00000").build().getStyle().getColor());
    }

    @Test
    void serializationRoundTripKeepsTheFormula() throws Exception {
        FormulaRenderData data = FormulaRenderData.of("\\sum_{i=1}^{n} i").display().fontSize(24).color("0000FF")
                .altMeta("fallback").build();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(data);
        }
        FormulaRenderData copy;
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            copy = (FormulaRenderData) in.readObject();
        }
        assertEquals(data.getSource(), copy.getSource());
        assertEquals(data.isDisplay(), copy.isDisplay());
        assertEquals(data.getAltMeta(), copy.getAltMeta());
        assertEquals(data.getStyle().getFontSize(), copy.getStyle().getFontSize());
        assertEquals(data.getStyle().getColor(), copy.getStyle().getColor());
    }

}
