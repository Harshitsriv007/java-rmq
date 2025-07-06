package com.example.req.dto;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class MessageRequest {
//    @NotBlank(message = "Message must not be blank")
    private String message;
    private List<Integer> number;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Integer> getNumber() {
        return number;
    }

    public void setNumber(List<Integer> number) {
        this.number = number;
    }
}
