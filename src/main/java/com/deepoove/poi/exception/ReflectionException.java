package com.deepoove.poi.exception;

import org.apache.commons.lang3.ClassUtils;

public class ReflectionException extends RuntimeException {

    private static final long serialVersionUID = -5336295846040984205L;

    public ReflectionException() {}

    public ReflectionException(String msg) {
        super(msg);
    }

    public ReflectionException(String msg, Exception e) {
        super(msg, e);
    }

    public ReflectionException(String name, Class<?> clazz, Exception e) {
        this("Error Reflect " + name + "from class " + ClassUtils.getShortClassName(clazz), e);
    }

}
