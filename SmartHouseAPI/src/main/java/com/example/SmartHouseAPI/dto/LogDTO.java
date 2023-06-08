package com.example.SmartHouseAPI.dto;

import java.time.LocalDateTime;

public class LogDTO {

    private Integer houseID;
    private Integer deviceID;
    private LocalDateTime exactTime;
    private float receivedValue;

    public LogDTO() {
    }

    public LogDTO(Integer houseID, Integer deviceID, LocalDateTime exactTime, float receivedValue) {
        this.houseID = houseID;
        this.deviceID = deviceID;
        this.exactTime = exactTime;
        this.receivedValue = receivedValue;
    }

    public Integer getHouseID() {
        return houseID;
    }

    public void setHouseID(Integer houseID) {
        this.houseID = houseID;
    }

    public Integer getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID) {
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

    @Override
    public String toString() {
        return "LogDTO{" +
                "houseID=" + houseID +
                ", deviceID=" + deviceID +
                ", exactTime=" + exactTime +
                ", receivedValue=" + receivedValue +
                '}';
    }
}
