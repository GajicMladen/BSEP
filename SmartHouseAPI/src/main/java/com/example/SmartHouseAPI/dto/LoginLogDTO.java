package com.example.SmartHouseAPI.dto;

public class LoginLogDTO {

    private Long id;

    private String executionTime;

    private String email;

    private String message;

    public LoginLogDTO(Long id, String executionTime, String email, String message) {
        this.id = id;
        this.executionTime = executionTime;
        this.email = email;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(String executionTime) {
        this.executionTime = executionTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
