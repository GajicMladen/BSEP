package com.example.SmartHouseAPI.dto;


public class LogsSerachDTO {

    private Integer realestateID;
    private Integer deviceID;
    private String startDate;
    private String endDate;

    private boolean onlyAlarms;

    public LogsSerachDTO() {
    }

    public LogsSerachDTO(Integer realestateID, Integer deviceID, String startDate, String endDate,boolean  onlyAlarms) {
        this.realestateID = realestateID;
        this.deviceID = deviceID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.onlyAlarms = onlyAlarms;
    }

    public Integer getRealestateID() {
        return realestateID;
    }

    public void setRealestateID(Integer realestateID) {
        this.realestateID = realestateID;
    }

    public Integer getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isOnlyAlarms() {
        return onlyAlarms;
    }

    public void setOnlyAlarms(boolean onlyAlarms) {
        this.onlyAlarms = onlyAlarms;
    }
}
