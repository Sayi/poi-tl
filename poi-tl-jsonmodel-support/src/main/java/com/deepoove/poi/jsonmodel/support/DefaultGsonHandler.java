/*
 * Copyright 2014-2022 Sayi
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
package com.deepoove.poi.jsonmodel.support;

import java.util.ArrayList;
import java.util.List;

import com.deepoove.poi.data.*;
import com.deepoove.poi.plugin.comment.CommentRenderData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

/**
 * 
 * define type for all render data class <br />
 * Json string to Map<String, Object> and Map<String, Object> to Json string.
 * 
 */
public class DefaultGsonHandler implements GsonHandler {

    private static final String BYTES = "bytes";
    private static final String URL = "url";
    private static final String FILE = "file";
    private static final String CHART_SINGLE = "chart-single";
    private static final String CHART_MULTI = "chart-multi";
    private static final String DOC = "doc";
    private static final String ATTACHMENT = "attachment";
    private static final String IMPORT = "import";
    private static final String COMMENT = "comment";
    private static final String TABLE = "table";
    private static final String NUMBERING = "numbering";
    private static final String PARAGRAPH = "paragraph";
    private static final String BOOKMARK = "bookmark";
    private static final String LINK = "link";
    private static final String TEXT = "text";

    private static final String TYPE_NAME = "type";

    private Gson parser, writer;

    @Override
    public Gson parser() {
        if (null != parser) return parser;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(createRenderTypeAdapter(true));
        gsonBuilder.registerTypeAdapterFactory(createTextTypeAdapter(true));
        gsonBuilder.registerTypeAdapterFactory(createPictureTypeAdapter(true));
        createTypeAdapters(true).forEach(factory -> gsonBuilder.registerTypeAdapterFactory(factory));
        parser = gsonBuilder.create();
        return parser;

    }

    @Override
    public Gson writer() {
        if (null != writer) return writer;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(createRenderTypeAdapter(false));
        gsonBuilder.registerTypeAdapterFactory(createTextTypeAdapter(false));
        gsonBuilder.registerTypeAdapterFactory(createPictureTypeAdapter(false));
        createTypeAdapters(false).forEach(factory -> gsonBuilder.registerTypeAdapterFactory(factory));
        writer = gsonBuilder.create();
        return writer;
    }

    protected RuntimeTypeAdapterFactory<RenderData> createRenderTypeAdapter(boolean readable) {
        return RuntimeTypeAdapterFactory.of(RenderData.class, TYPE_NAME, readable)
                .registerSubtype(TextRenderData.class, TEXT)
                .registerSubtype(HyperlinkTextRenderData.class, LINK)
                .registerSubtype(BookmarkTextRenderData.class, BOOKMARK)
                .registerSubtype(ParagraphRenderData.class, PARAGRAPH)
                .registerSubtype(NumberingRenderData.class, NUMBERING)
                .registerSubtype(TableRenderData.class, TABLE)
                .registerSubtype(CommentRenderData.class, COMMENT)
                .registerSubtype(DocxRenderData.class, IMPORT)
                .registerSubtype(AttachmentRenderData.class, ATTACHMENT)
                .registerSubtype(DocumentRenderData.class, DOC)
                .registerSubtype(ChartMultiSeriesRenderData.class, CHART_MULTI)
                .registerSubtype(ChartSingleSeriesRenderData.class, CHART_SINGLE)
                .registerSubtype(FilePictureRenderData.class, FILE)
                .registerSubtype(UrlPictureRenderData.class, URL)
                .registerSubtype(ByteArrayPictureRenderData.class, BYTES);
    }

    protected List<RuntimeTypeAdapterFactory<?>> createTypeAdapters(boolean readable) {
        List<RuntimeTypeAdapterFactory<?>> list = new ArrayList<>();
        list.add(RuntimeTypeAdapterFactory.of(ParagraphRenderData.class, TYPE_NAME, readable)
                .registerSubtype(ParagraphRenderData.class, PARAGRAPH));
        list.add(RuntimeTypeAdapterFactory.of(NumberingRenderData.class, TYPE_NAME, readable)
                .registerSubtype(NumberingRenderData.class, NUMBERING));
        list.add(RuntimeTypeAdapterFactory.of(TableRenderData.class, TYPE_NAME, readable)
                .registerSubtype(TableRenderData.class, TABLE));
        list.add(RuntimeTypeAdapterFactory.of(CommentRenderData.class, TYPE_NAME, readable)
                .registerSubtype(CommentRenderData.class, COMMENT));
        list.add(RuntimeTypeAdapterFactory.of(DocxRenderData.class, TYPE_NAME, readable)
                .registerSubtype(DocxRenderData.class, IMPORT));
        list.add(RuntimeTypeAdapterFactory.of(AttachmentRenderData.class, TYPE_NAME, readable)
                .registerSubtype(AttachmentRenderData.class, ATTACHMENT));
        list.add(RuntimeTypeAdapterFactory.of(DocumentRenderData.class, TYPE_NAME, readable)
                .registerSubtype(DocumentRenderData.class, DOC));
        list.add(RuntimeTypeAdapterFactory.of(ChartMultiSeriesRenderData.class, TYPE_NAME, readable)
                .registerSubtype(ChartMultiSeriesRenderData.class, CHART_MULTI));
        list.add(RuntimeTypeAdapterFactory.of(ChartSingleSeriesRenderData.class, TYPE_NAME, readable)
                .registerSubtype(ChartSingleSeriesRenderData.class, CHART_SINGLE));
        list.add(RuntimeTypeAdapterFactory.of(HyperlinkTextRenderData.class, TYPE_NAME, readable)
                .registerSubtype(HyperlinkTextRenderData.class, LINK));
        list.add(RuntimeTypeAdapterFactory.of(BookmarkTextRenderData.class, TYPE_NAME, readable)
                .registerSubtype(BookmarkTextRenderData.class, BOOKMARK));
        list.add(RuntimeTypeAdapterFactory.of(FilePictureRenderData.class, TYPE_NAME, readable)
                .registerSubtype(FilePictureRenderData.class, FILE));
        list.add(RuntimeTypeAdapterFactory.of(UrlPictureRenderData.class, TYPE_NAME, readable)
                .registerSubtype(UrlPictureRenderData.class, URL));
        list.add(RuntimeTypeAdapterFactory.of(ByteArrayPictureRenderData.class, TYPE_NAME, readable)
                .registerSubtype(ByteArrayPictureRenderData.class, BYTES));
        return list;
    }

    protected RuntimeTypeAdapterFactory<TextRenderData> createTextTypeAdapter(boolean readable) {
        return RuntimeTypeAdapterFactory.of(TextRenderData.class, TYPE_NAME, readable)
                .registerSubtype(TextRenderData.class, TEXT)
                .registerSubtype(HyperlinkTextRenderData.class, LINK)
                .registerSubtype(BookmarkTextRenderData.class, BOOKMARK);
    }

    protected RuntimeTypeAdapterFactory<PictureRenderData> createPictureTypeAdapter(boolean readable) {
        return RuntimeTypeAdapterFactory.of(PictureRenderData.class, TYPE_NAME, readable)
                .registerSubtype(FilePictureRenderData.class, FILE)
                .registerSubtype(UrlPictureRenderData.class, URL)
                .registerSubtype(ByteArrayPictureRenderData.class, BYTES);
    }

}
