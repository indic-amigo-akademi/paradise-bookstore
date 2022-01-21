package com.iaa.paradise_server.Validation;

public interface FieldValueExists {
    boolean fieldValueExists(String fieldName, Object value) throws UnsupportedOperationException;
}
