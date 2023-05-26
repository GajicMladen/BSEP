package com.example.SmartHouseAPI.dto;

public class LogSendDTO {

    private String house;
    private String device;
    private String exactTime;
    private float receivedValue;

    private boolean isAlarm;

    public LogSendDTO(String house, String device, String exactTime, float receivedValue,boolean isAlarm) {
        this.house = house;
        this.device = device;
        this.exactTime = exactTime;
        this.receivedValue = receivedValue;
        this.isAlarm = isAlarm;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public boolean isAlarm() {
        return isAlarm;
    }

    public void setAlarm(boolean alarm) {
        isAlarm = alarm;
    }

    public String getExactTime() {
        return exactTime;
    }

    public void setExactTime(String exactTime) {
        this.exactTime = exactTime;
    }

    public float getReceivedValue() {
        return receivedValue;
    }

    public void setReceivedValue(float receivedValue) {
        this.receivedValue = receivedValue;
    }
}
