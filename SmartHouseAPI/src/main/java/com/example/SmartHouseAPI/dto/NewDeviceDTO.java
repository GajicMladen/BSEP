package com.example.SmartHouseAPI.dto;

import com.example.SmartHouseAPI.enums.DeviceType;

public class NewDeviceDTO {
    private String deviceDescription;
    private DeviceType deviceType;

    private Long realestateID;

    public NewDeviceDTO() {
    }

    public NewDeviceDTO(String deviceDescription, DeviceType deviceType) {
        this.deviceDescription = deviceDescription;
        this.deviceType = deviceType;
    }

    public NewDeviceDTO(String deviceDescription, DeviceType deviceType, Long realestateID) {
        this.deviceDescription = deviceDescription;
        this.deviceType = deviceType;
        this.realestateID = realestateID;
    }

    public Long getRealestateID() {
        return realestateID;
    }

    public void setRealestateID(Long realestateID) {
        this.realestateID = realestateID;
    }

    public String getDeviceDescription() {
        return deviceDescription;
    }

    public void setDeviceDescription(String deviceDescription) {
        this.deviceDescription = deviceDescription;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
}
