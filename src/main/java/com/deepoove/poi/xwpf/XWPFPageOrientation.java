/*
 * Copyright 2014-2021 Sayi
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
package com.deepoove.poi.xwpf;

import java.util.HashMap;
import java.util.Map;

public enum XWPFPageOrientation {

    PORTRAIT(1),
    LANDSCAPE(2);

    private static Map<Integer, XWPFPageOrientation> imap = new HashMap<>();

    static {
        for (XWPFPageOrientation p : values()) {
            imap.put(p.getValue(), p);
        }
    }

    private final int value;

    private XWPFPageOrientation(int val) {
        value = val;
    }

    public static XWPFPageOrientation valueOf(int type) {
        XWPFPageOrientation err = imap.get(type);
        if (err == null) throw new IllegalArgumentException("Unknown page orient: " + type);
        return err;
    }

    public int getValue() {
        return value;
    }
}
