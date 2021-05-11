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
package com.deepoove.poi.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.batik.transcoder.ErrorHandler;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deepoove.poi.exception.RenderException;

public class SVGConvertor {

    private static Logger logger = LoggerFactory.getLogger(SVGConvertor.class);

    public static byte[] toPng(byte[] svgs, float width, float maxHeight) throws TranscoderException, IOException {
        Transcoder t = new PNGTranscoder();
        t.setErrorHandler(new ErrorHandler() {

            @Override
            public void warning(TranscoderException ex) throws TranscoderException {
                logger.warn("WARNING: " + ex.getMessage());
            }

            @Override
            public void fatalError(TranscoderException ex) throws TranscoderException {
                throw ex;
            }

            @Override
            public void error(TranscoderException ex) throws TranscoderException {
                throw ex;
            }
        });
        if (0 != width) t.addTranscodingHint(PNGTranscoder.KEY_WIDTH, width);
        if (0 != maxHeight) t.addTranscodingHint(PNGTranscoder.KEY_MAX_HEIGHT, maxHeight);
        try (ByteArrayInputStream instream = new ByteArrayInputStream(svgs);
                ByteArrayOutputStream ostream = new ByteArrayOutputStream()) {
            TranscoderInput input = new TranscoderInput(instream);
            TranscoderOutput output = new TranscoderOutput(ostream);
            t.transcode(input, output);
            ostream.flush();
            return ostream.toByteArray();
        } catch (Exception e) {
            throw new RenderException("Unable transcode from svg to png, possibly some svg attribute is not supported.",
                    e);
        }
    }

}
