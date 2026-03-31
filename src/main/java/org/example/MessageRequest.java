package org.example;

public class MessageRequest {
    private String message;

    // Default constructor is required for JSON parsing
    public MessageRequest() {}

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}