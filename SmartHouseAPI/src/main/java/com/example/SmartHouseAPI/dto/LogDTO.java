package com.example.SmartHouseAPI.dto;

import java.time.LocalDateTime;

public class LogDTO {

    private String houseID;
    private String deviceID;
    private LocalDateTime exactTime;
    private float receivedValue;

    public LogDTO(String houseID, String deviceID, LocalDateTime exactTime, float receivedValue) {
        this.houseID = houseID;
        this.deviceID = deviceID;
        this.exactTime = exactTime;
        this.receivedValue = receivedValue;
    }

    public String getHouseID() {
        return houseID;
    }

    public void setHouseID(String houseID) {
        this.houseID = houseID;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public LocalDateTime getExactTime() {
        return exactTime;
    }

    public void setExactTime(LocalDateTime exactTime) {
        this.exactTime = exactTime;
    }

    public float getReceivedValue() {
        return receivedValue;
    }

    public void setReceivedValue(float receivedValue) {
        this.receivedValue = receivedValue;
    }
}
