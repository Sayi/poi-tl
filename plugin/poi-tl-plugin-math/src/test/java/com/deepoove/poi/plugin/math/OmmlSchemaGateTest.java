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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;

/**
 * Guards the copied schema layer. This is the only runtime landmine of the
 * plugin: a copied class without its compiled schema resource only fails when
 * that structure is first used, which may well be in production.
 * <p>
 * The gate is deterministic - it resolves every copied type instead of trying
 * one structure after another.
 *
 * @author Sayi
 */
@DisplayName("Copied OMML schema layer")
class OmmlSchemaGateTest {

    private static final String MATH_PACKAGE = "org.openxmlformats.schemas.officeDocument.x2006.math";
    private static final File SOURCE_DIR = new File("src/main/java/org/openxmlformats/schemas/officeDocument/x2006/math");
    private static final File XSB_DIR = new File("src/main/resources/org/apache/poi/schemas/ooxml/system/ooxml");

    @Test
    @DisplayName("every copied type resolves its compiled schema resource")
    void everyCopiedTypeResolves() throws Exception {
        List<String> types = copiedTypeNames();
        assertEquals(70, types.size(), "the copied type set changed unexpectedly: " + types);
        for (String type : types) {
            Object schemaType = Class.forName(type).getField("type").get(null);
            assertNotNull(schemaType, type);
        }
    }

    @Test
    @DisplayName("every schema handle has its .xsb resource next to it")
    void everyHandleHasItsResource() throws Exception {
        Set<String> handles = handlesInSources();
        assertTrue(handles.size() >= 70, "unexpectedly few handles: " + handles.size());
        List<String> missing = new ArrayList<String>();
        for (String handle : handles) {
            if (!new File(XSB_DIR, handle + ".xsb").isFile()) {
                missing.add(handle + ".xsb");
            }
        }
        assertTrue(missing.isEmpty(), "copy the matching .xsb into src/main/resources: " + missing);
    }

    @Test
    @DisplayName("copied types never shadow poi-ooxml-lite")
    void copiedTypesDoNotShadowLite() throws Exception {
        Set<String> lite = liteMathTypeNames();
        assertFalse(lite.isEmpty(), "poi-ooxml-lite should provide some math types");
        for (String type : copiedTypeNames()) {
            String simpleName = type.substring(type.lastIndexOf('.') + 1);
            assertFalse(lite.contains(simpleName),
                    simpleName + " already exists in poi-ooxml-lite; copying it would shadow the jar");
        }
    }

    @Test
    @DisplayName("no .xsb was put under src/main/java, where Maven would drop it")
    void schemaResourcesAreNotUnderJavaSources() {
        assertTrue(!containsXsb(SOURCE_DIR), ".xsb files under src/main/java never reach the jar");
    }

    // ────────────────────────────── helpers ──────────────────────────────

    private static List<String> copiedTypeNames() {
        List<String> names = new ArrayList<String>();
        File[] files = SOURCE_DIR.listFiles();
        assertNotNull(files, "cannot list " + SOURCE_DIR.getAbsolutePath());
        Arrays.sort(files);
        for (File file : files) {
            String name = file.getName();
            if (file.isFile() && name.endsWith(".java")) {
                names.add(MATH_PACKAGE + "." + name.substring(0, name.length() - ".java".length()));
            }
        }
        return names;
    }

    private static Set<String> handlesInSources() throws IOException {
        Pattern handle = Pattern.compile("\"([a-z0-9]+type)\"");
        Set<String> handles = new TreeSet<String>();
        File[] files = SOURCE_DIR.listFiles();
        assertNotNull(files, "cannot list " + SOURCE_DIR.getAbsolutePath());
        for (File file : files) {
            if (!file.isFile() || !file.getName().endsWith(".java")) continue;
            String source = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
            Matcher matcher = handle.matcher(source);
            while (matcher.find()) {
                handles.add(matcher.group(1));
            }
        }
        return handles;
    }

    private static Set<String> liteMathTypeNames() throws IOException {
        File jar = new File(URI.create(CTOMath.class.getProtectionDomain().getCodeSource().getLocation().toString()));
        Set<String> names = new HashSet<String>();
        try (ZipFile zip = new ZipFile(jar)) {
            java.util.Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (!name.startsWith("org/openxmlformats/schemas/officeDocument/x2006/math/")) continue;
                if (!name.endsWith(".class")) continue;
                String simple = name.substring(name.lastIndexOf('/') + 1, name.length() - ".class".length());
                if (simple.indexOf('$') >= 0) continue;
                names.add(simple);
            }
        }
        return names;
    }

    private static boolean containsXsb(File directory) {
        File[] files = directory.listFiles();
        if (null == files) return false;
        for (File file : files) {
            if (file.isDirectory() && containsXsb(file)) return true;
            if (file.isFile() && file.getName().endsWith(".xsb")) return true;
        }
        return false;
    }

}
