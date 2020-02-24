package com.deepoove.poi.util;

import java.lang.reflect.Field;

public class ReflectionUtils {

    public static <T> T getValue(String fieldName, Object obj, Class<T> retClass) {
        try {
            Field runsField = obj.getClass().getDeclaredField(fieldName);
            runsField.setAccessible(true);
            return retClass.cast(runsField.get(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getValue(String fieldName, Object obj) {
        try {
            Field runsField = obj.getClass().getDeclaredField(fieldName);
            runsField.setAccessible(true);
            return runsField.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
