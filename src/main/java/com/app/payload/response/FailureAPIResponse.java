package com.app.payload.response;

public class FailureAPIResponse extends APIResponse{
    public FailureAPIResponse(String message) {
        this.message = message;
        this.success = false;
    }

    public FailureAPIResponse(String message,Object data) {
        this.message = message;
        this.success = false;
        this.data = data;
    }

}
