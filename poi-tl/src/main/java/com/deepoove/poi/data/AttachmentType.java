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
package com.deepoove.poi.data;

import org.apache.poi.ooxml.POIXMLDocument;

import static com.deepoove.poi.util.ByteUtils.startsWith;

/**
 * @author Sayi
 */
public enum AttachmentType {
    DOCX(
        new String[]{"504B0304", "DOCF11E0"},
        "Word.Document.12",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
		POIXMLDocument.PACK_OBJECT_REL_TYPE,
        new String[]{".docx", ".doc"},
        "data:image/png;base64,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"),
	XLSX(
        new String[]{"504B0304", "DOCF11E0"},
        "Excel.Sheet.12",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
		POIXMLDocument.PACK_OBJECT_REL_TYPE,
        new String[]{".xlsx", ".xls"},
        "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAAAXNSR0IArs4c6QAABXFJREFUeF7tW3tQVFUc/u7dxQU1bPJZoBLQDIWwV0EqnRo0iLKEQEbyQVpjCgnGkDkRwWypNWpOBiNPS0GzUEHy1eAjBcEnfzQp5oROWWg6IiEUk+De25xloL3cu487tevZ7v7+2/O49/d953e/3zlnz2GgcmNowx9qCJ/wvaHxvLP8umcERBoiPdvQwTFGgeMFhmNY6CGAA+DppfEYdtpwusMZJDiFgDBD2IjuuzzHsqyeFwSOIUAZTLAE0KUJ4Aycn5HX6hme5xgGnADTqPopGU2XIUCfow/mGU3viPaOqh7ASCVg5dpSR0BgXqBu8M1hHCMInMAwHCAQoGRkvf4tWOoICMqKGD7Io4eMqt4EtleYQhwBlBoN4HK4yUawWQwEDmAediZYuXf5hPrka7Wabkf5cRco/Ca++jJ5vikLEAJ4sGcc9UKlz/XV+4LVsEq7KWq/76VqFgwE1RLA8GzE3llVZ90EqPUTcEeA+xNwa4BbBKnNAiey6zBEN0SU12dWx/f/nhOUhLlBL9ud99+tz8G5VvEWA9Ui6CaAtghInbZENtwECCg6WmIxFCP8JyNs/CTZ+sKjxRb7URcBAaP8UZW2U+Jwj7EH4e8/YRFI+euboR8bKqr/804XlpSl4lyL5W0/6gggCFYlfICZ3AsSsBsO5mFzfZmkPCr4GaxPWispzz9SgE21n1kVMGcshhSLoE6rw5ncExLHr9++gZj1MyTlX6Zsw2MPPSoqP/tTIxaXpYLnedcjgHhMtCBl2mKJ82sOrMP2U1/1l88Kj0du7HuSdqnlaThx6aTN9EVlBPR53ZBdi6G6oSIQTVcvYG5xcn+Z3OhvaSjHJzWf2gRPGpSmFMFrkHi3jeTyPiPzgJARFjeVpdF4seK/mwcQHSB6MNBW7MhCzfmDSAxPQE5stqi6vasdcXkJaO+6bRcBVIqguecVqV8g6MEgEZi6H48jfVsG5JS/jxy70AOgnoCJ4zhsWSRV8jlF80HC39zqmxuwdOsye7Gb2lFPAHHy46Q1iA6OEgHb1Vhl+gTM7cUNcfi1reX/R8DI+0bg8Ns1VoEVHytFwbdFisCTxjHTn4VWqxX1M1/MKBHAvoc4ZDGUEZ2OV59aKAvwZmcrotbFKAZPOlCdBs0RLX8uE8lT5lkESTThwrUfFJPgEgQEjgpAZdoOq+AIeEKCUnOJT2Db4jKE+IonIycvn8KTAeLF0co9q0HEUYlRnwXkprpXbv2Csvpy5MZJp8DT10bj1h9tdnNANQFjho1BzVv7JWCI2lc27sb2lHKM9h4tSY8kEuw1qglYGW9A7MSZIizX2n/D7II56PyrE4uefg3pUUslWN/Ymo6GZulqUo4UagkgEx8yARpoGw7lYfPx3j0BDavB18uqMPYBX1EzIojzSxbAyBttBgKViyFvL28Q4Rs/fJwIwM+tV0wLHXMjqZGkyIGWf3gjNtV9bpMAKtNgZkwGFkz9Z8nbh8KSyu9O3wn/kf4SsPOKX8H5q01WSaCOgKmPTEFBcr7E6eYbl5C4MUkWzIzQ5/FR4ipJ3aGmI1hescJ1CPDQeKBkYSEmjZ8ocfrN7Zk4drHWIhi5uQJpvHLPh9jVWGmxH1UiaGlLnHhvbWub1D/uHyFLHKnb+91+tPwuv0qkigCbiuWABm4CaPtnyAGDbPWRqo8A6tKgsyPATYATzgkq/mvMmVHgjgB3BDj+qKz7E1D7OcF98dWmY8L9Z4WpOi4f7LOa9dDccZTwCsbu0gOzD1zvJ8D8Raq8MGGL6cD0QN3g+1VyZcYWGeb1qrk0pYQU7h3OzzhIJdfm7CVGVRcn7SXFzxDp6d3TwTGsCq7O2ksKaaeay9NKSHFk278BpaKbbiHMt1QAAAAASUVORK5CYII="),
	OLE(new String[0],
		"Package",
		"application/vnd.openxmlformats-officedocument.oleObject",
		POIXMLDocument.OLE_OBJECT_REL_TYPE,
		new String[]{""},
		"data:image/png;base64,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")
	;

    private final String[] fileMagics;
    private final String programId;
    private final String contentType;
	private final String relType;
    private final String[] extensions;
    private final String icon;

    AttachmentType(String[] fileMagics, String programId, String contentType, String relType, String[] extensions, String icon) {
        this.fileMagics = fileMagics;
        this.programId = programId;
        this.contentType = contentType;
		this.relType = relType;
        this.extensions = extensions;
        this.icon = icon;
    }

    public String programId() {
        return programId;
    }

    public String contentType() {
        return contentType;
    }

	public String relType() {
		return relType;
	}

	public String[] extensions() {
        return extensions;
    }

    public String ext() {
        return extensions[0];
    }

    public String icon() {
        return icon;
    }

    public static AttachmentType suggestFileType(byte[] bytes) {
        for (AttachmentType type : values()) {
            for (String magic : type.fileMagics) {
                if (startsWith(bytes, magic.getBytes())) {
                    return type;
                }
            }
        }
        return null;
    }

    public static AttachmentType suggestFileType(String fileLocation) {
        if (null == fileLocation) return null;
        for (AttachmentType type : values()) {
            for (String extension : type.extensions) {
                if (fileLocation.endsWith(extension)) {
                    return type;
                }
            }
        }
        return null;
    }
}
