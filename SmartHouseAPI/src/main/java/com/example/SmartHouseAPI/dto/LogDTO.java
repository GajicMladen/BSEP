package com.example.SmartHouseAPI.dto;

import java.time.LocalDateTime;

public class LogDTO {

    private Long houseID;
    private Long deviceID;
    private LocalDateTime exactTime;
    private float receivedValue;

    public LogDTO(Long houseID, Long deviceID, LocalDateTime exactTime, float receivedValue) {
        this.houseID = houseID;
        this.deviceID = deviceID;
        this.exactTime = exactTime;
        this.receivedValue = receivedValue;
    }

    public Long getHouseID() {
        return houseID;
    }

    public void setHouseID(Long houseID) {
        this.houseID = houseID;
    }

    public Long getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Long deviceID) {
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
