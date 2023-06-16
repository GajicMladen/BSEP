package com.example.SmartHouseAPI.model;

import com.example.SmartHouseAPI.dto.AuthenticationRequest;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.util.Date;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("30m")
public class FailedLogin {

    private String email;

    private Date executionTime;

    private String password;

    private String pin;

    public FailedLogin(AuthenticationRequest authenticationRequest) {
        this.email = authenticationRequest.getEmail();
        this.executionTime = new Date();
        this.password = authenticationRequest.getPassword();
        this.pin = authenticationRequest.getPin();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
