package com.app.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {
    protected String message = "success";
    protected Boolean success = true;
    protected Object data = null;

    public APIResponse(String message) {
        this.message = message;
    }

    public APIResponse(Object data) {
        this.data = data;
    }

    public APIResponse(boolean success, String message) {
        this.success = success; this.message = message;
    }
}
