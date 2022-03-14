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
package com.deepoove.poi.config;

import java.util.Arrays;
import java.util.List;

import com.deepoove.poi.data.*;
import com.deepoove.poi.plugin.comment.CommentRenderData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

public class DefaultGsonProvider implements GsonProvider {

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
    private static final String TYPE = "type";

    public Gson read, write;

    protected RuntimeTypeAdapterFactory<RenderData> createTypeAdapter(boolean isRead) {
        return RuntimeTypeAdapterFactory.of(RenderData.class, TYPE, isRead)
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

    protected List<RuntimeTypeAdapterFactory<?>> createTypeAdapterIndividually(boolean isRead) {
        return Arrays.asList(
                RuntimeTypeAdapterFactory.of(ParagraphRenderData.class, TYPE, isRead)
                        .registerSubtype(ParagraphRenderData.class, PARAGRAPH),
                RuntimeTypeAdapterFactory.of(NumberingRenderData.class, TYPE, isRead)
                        .registerSubtype(NumberingRenderData.class, NUMBERING),
                RuntimeTypeAdapterFactory.of(TableRenderData.class, TYPE, isRead)
                        .registerSubtype(TableRenderData.class, TABLE),
                RuntimeTypeAdapterFactory.of(CommentRenderData.class, TYPE, isRead)
                        .registerSubtype(CommentRenderData.class, COMMENT),
                RuntimeTypeAdapterFactory.of(DocxRenderData.class, TYPE, isRead)
                        .registerSubtype(DocxRenderData.class, IMPORT),
                RuntimeTypeAdapterFactory.of(AttachmentRenderData.class, TYPE, isRead)
                        .registerSubtype(AttachmentRenderData.class, ATTACHMENT),
                RuntimeTypeAdapterFactory.of(DocumentRenderData.class, TYPE, isRead)
                        .registerSubtype(DocumentRenderData.class, DOC),
                RuntimeTypeAdapterFactory.of(ChartMultiSeriesRenderData.class, TYPE, isRead)
                        .registerSubtype(ChartMultiSeriesRenderData.class, CHART_MULTI),
                RuntimeTypeAdapterFactory.of(ChartSingleSeriesRenderData.class, TYPE, isRead)
                        .registerSubtype(ChartSingleSeriesRenderData.class, CHART_SINGLE),
                RuntimeTypeAdapterFactory.of(HyperlinkTextRenderData.class, TYPE, isRead)
                        .registerSubtype(HyperlinkTextRenderData.class, LINK),
                RuntimeTypeAdapterFactory.of(BookmarkTextRenderData.class, TYPE, isRead)
                        .registerSubtype(BookmarkTextRenderData.class, BOOKMARK),
                RuntimeTypeAdapterFactory.of(FilePictureRenderData.class, TYPE, isRead)
                        .registerSubtype(FilePictureRenderData.class, FILE),
                RuntimeTypeAdapterFactory.of(UrlPictureRenderData.class, TYPE, isRead)
                        .registerSubtype(UrlPictureRenderData.class, URL),
                RuntimeTypeAdapterFactory.of(ByteArrayPictureRenderData.class, TYPE, isRead)
                        .registerSubtype(ByteArrayPictureRenderData.class, BYTES));
    }

    protected RuntimeTypeAdapterFactory<TextRenderData> createTextTypeAdapter(boolean isRead) {
        return RuntimeTypeAdapterFactory.of(TextRenderData.class, TYPE, isRead)
                .registerSubtype(TextRenderData.class, TEXT)
                .registerSubtype(HyperlinkTextRenderData.class, LINK)
                .registerSubtype(BookmarkTextRenderData.class, BOOKMARK);
    }

    protected RuntimeTypeAdapterFactory<PictureRenderData> createPictureTypeAdapter(boolean isRead) {
        return RuntimeTypeAdapterFactory.of(PictureRenderData.class, TYPE, isRead)
                .registerSubtype(FilePictureRenderData.class, FILE)
                .registerSubtype(UrlPictureRenderData.class, URL)
                .registerSubtype(ByteArrayPictureRenderData.class, BYTES);
    }

    @Override
    public Gson read() {
        if (null != read) return read;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(createTypeAdapter(true));
        gsonBuilder.registerTypeAdapterFactory(createTextTypeAdapter(true));
        gsonBuilder.registerTypeAdapterFactory(createPictureTypeAdapter(true));
        createTypeAdapterIndividually(true).forEach(factory -> gsonBuilder.registerTypeAdapterFactory(factory));
        read = gsonBuilder.create();
        return read;

    }

    @Override
    public Gson write() {
        if (null != write) return write;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(createTypeAdapter(false));
        gsonBuilder.registerTypeAdapterFactory(createTextTypeAdapter(false));
        gsonBuilder.registerTypeAdapterFactory(createPictureTypeAdapter(false));
        createTypeAdapterIndividually(false).forEach(factory -> gsonBuilder.registerTypeAdapterFactory(factory));
        write = gsonBuilder.create();
        return write;
    }

}
