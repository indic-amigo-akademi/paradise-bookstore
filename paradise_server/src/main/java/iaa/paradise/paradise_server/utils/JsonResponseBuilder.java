package iaa.paradise.paradise_server.utils;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class JsonResponseBuilder {
    private String message;
    private Object data;
    private boolean success;
    private Map<String, List<String>> errors;
    private long timeTaken;

    public JsonResponseBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public JsonResponseBuilder setData(Object data) {
        this.data = data;
        return this;
    }

    public JsonResponseBuilder setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public JsonResponseBuilder setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
        return this;
    }

    public JsonResponseBuilder setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
        return this;
    }

    public JsonResponse build() {
        return new JsonResponse(message, data, success, errors, timeTaken);
    }
}