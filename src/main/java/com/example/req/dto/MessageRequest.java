package com.example.req.dto;

import javax.validation.constraints.NotBlank;

public class MessageRequest {
    @NotBlank(message = "Message must not be blank")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
