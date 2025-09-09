/*
 * Copyright 2014-2025 Sayi
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
	PDF(new String[]{"25504446"},
		"Package",
		"application/vnd.openxmlformats-officedocument.oleObject",
		POIXMLDocument.OLE_OBJECT_REL_TYPE,
		new String[]{".pdf"},
		"data:image/png;base64,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"),
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
