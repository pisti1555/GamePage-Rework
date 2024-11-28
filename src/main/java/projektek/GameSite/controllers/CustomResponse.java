package projektek.GameSite.controllers;

import java.util.Map;

public class CustomResponse {
    private String message;
    private Object data;
    private Map<String, String> errors;

    public CustomResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }
    public CustomResponse(String message, Object data, Map<String, String> errors) {
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
