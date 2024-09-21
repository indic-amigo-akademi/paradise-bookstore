package iaa.paradise.paradise_server.utils.validation;

public interface FieldValueExists {
    boolean fieldValueExists(String fieldName, Object value) throws UnsupportedOperationException;
}
