package com.deepoove.poi.util;

import java.lang.reflect.Field;
import java.util.Objects;

public class ReflectionUtils {

    public static Object getValue(String fieldName, Object obj) {
        Objects.requireNonNull(obj, "Class must not be null");
        Objects.requireNonNull(fieldName, "Name must not be null");
        try {
            Field field = findField(obj.getClass(), fieldName);
            if (null == field) {
                throw new NoSuchFieldException("No Such field:" + fieldName);
            }
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            throw new IllegalStateException("Relefect field " + fieldName + " error", e);
        }
    }

    public static Field findField(Class<?> clazz, String name) {
        Objects.requireNonNull(clazz, "Class must not be null");
        Objects.requireNonNull(name, "Name must not be null");
        Class<?> searchType = clazz;
        while (Object.class != searchType && searchType != null) {
            Field field;
            try {
                field = searchType.getDeclaredField(name);
                if (null != field) return field;
            } catch (NoSuchFieldException e) {
                // no-op
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

}
