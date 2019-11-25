package com.deepoove.poi.util;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoitlIOUtils {

    private static Logger logger = LoggerFactory.getLogger(PoitlIOUtils.class);

    public static void closeLoggerQuietly(final Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (final IOException ignored) {
                logger.error("Close failed", ignored);
            }
        }
    }

    public static void closeQuietlyMulti(final Closeable... cls) {
        for (Closeable c : cls) {
            closeQuietly(c);
        }
    }

    public static void closeQuietly(final Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (final IOException ignored) {}
        }
    }

}
