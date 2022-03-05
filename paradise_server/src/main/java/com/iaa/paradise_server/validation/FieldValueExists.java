package com.iaa.paradise_server.validation;

public interface FieldValueExists {
    boolean fieldValueExists(String fieldName, Object value) throws UnsupportedOperationException;
}
