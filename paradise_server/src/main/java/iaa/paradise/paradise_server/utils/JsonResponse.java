package iaa.paradise.paradise_server.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonResponse {
    private String message;
    private Object data;
    private boolean success;
    private Map<String, List<String>> errors;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long timeTaken;

    public JsonResponse() {
    }

    public JsonResponse(String message, Object data, boolean success, Map<String, List<String>> errors,
            long timeTaken) {
        this.message = message;
        this.data = data;
        this.success = success;
        this.errors = errors;
        this.timeTaken = timeTaken;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public long getTimeTaken() {
        return timeTaken;
    }
}
