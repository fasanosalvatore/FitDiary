package it.fitdiary.backend.integration;

import java.util.LinkedHashMap;

public class JSendDTO {
    private Object data;
    private String status;

    public JSendDTO() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JSendDTO(Object data, String status) {
        this.data = data;
        this.status = status;
    }
}
