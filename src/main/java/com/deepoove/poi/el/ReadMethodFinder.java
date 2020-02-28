package com.deepoove.poi.el;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ReadMethodFinder {

    private static Logger logger = LoggerFactory.getLogger(ReadMethodFinder.class);

    public static Method find(Class<?> objClass, String key) {
        try {
            PropertyDescriptor propDesc = new PropertyDescriptor(key, objClass);
            return propDesc.getReadMethod();
        } catch (IntrospectionException e1) {
            logger.error("Fail introspector the property: {} from {}, {}", key, objClass, e1.getMessage());
        }
        return null;
    }

}
