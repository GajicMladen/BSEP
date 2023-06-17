package com.example.SmartHouseAPI.model;


import com.example.SmartHouseAPI.enums.FailedLoginType;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import java.util.Date;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("1d")
public class LoginViolation {

    private String email;

    private Date executionTime;

    private FailedLoginType failedLoginType;

    public LoginViolation(String email) {
        this.email = email;
        this.executionTime = new Date();
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

    public FailedLoginType getFailedLoginType() {
        return failedLoginType;
    }

    public void setFailedLoginType(FailedLoginType failedLoginType) {
        this.failedLoginType = failedLoginType;
    }
}
