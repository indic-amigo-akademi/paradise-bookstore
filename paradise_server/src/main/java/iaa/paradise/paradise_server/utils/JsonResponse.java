package iaa.paradise.paradise_server.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonResponse {
    private String message;
    private Object data;
    private boolean success;
    private Map<String, List<String>> errors;
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

    public Map<String, Object> toJsonMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("success", success);
        if (message != null) {
            map.put("message", message);
        }
        if (data != null) {
            map.put("data", data);
        }
        if (errors != null) {
            map.put("errors", errors);
        }
        if (timeTaken != 0) {
            map.put("timeTaken", timeTaken);
        }

        return map;
    }
}
