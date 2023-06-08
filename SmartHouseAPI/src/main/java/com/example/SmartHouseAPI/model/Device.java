package com.example.SmartHouseAPI.model;

import com.example.SmartHouseAPI.enums.DeviceType;

import javax.persistence.*;


@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id", nullable = false)
    private Long deviceID;

    @Column(name = "device_type")
    private DeviceType deviceType;

    @Column(name = "read_data")
    private boolean readData;

    @Column(name = "description")
    private String description;


    @Column(name = "up_limit")
    private float upLimit;


    @Column(name = "down_limit")
    private float downLimit;
    
    @Column(name = "occurrences_number")
    private int occurrencesNumber;

    @Column(name = "time_range_minutes")
    private int timeRangeMinutes;

    @ManyToOne
    @JoinColumn(name = "realestate_id")
    private Realestate realestate;

    public Device() {
    }

    public Device(Long deviceID, DeviceType deviceType, boolean readData, String description, float upLimit, float downLimit, int occurrencesNumber, int timeRangeMinutes, Realestate realestate) {
        this.deviceID = deviceID;
        this.deviceType = deviceType;
        this.readData = readData;
        this.description = description;
        this.upLimit = upLimit;
        this.downLimit = downLimit;
        this.occurrencesNumber = occurrencesNumber;
        this.timeRangeMinutes = timeRangeMinutes;
        this.realestate = realestate;
    }

    public int getOccurrencesNumber() {
        return occurrencesNumber;
    }

    public void setOccurrencesNumber(int occurrencesNumber) {
        this.occurrencesNumber = occurrencesNumber;
    }

    public int getTimeRangeMinutes() {
        return timeRangeMinutes;
    }

    public void setTimeRangeMinutes(int timeRangeMinutes) {
        this.timeRangeMinutes = timeRangeMinutes;
    }

    public float getUpLimit() {
        return upLimit;
    }

    public void setUpLimit(float upLimit) {
        this.upLimit = upLimit;
    }

    public float getDownLimit() {
        return downLimit;
    }

    public void setDownLimit(float downLimit) {
        this.downLimit = downLimit;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public boolean isReadData() {
        return readData;
    }

    public void setReadData(boolean readData) {
        this.readData = readData;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Realestate getRealestate() {
        return realestate;
    }

    public void setRealestate(Realestate realestate) {
        this.realestate = realestate;
    }


    public Long getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Long deviceID) {
        this.deviceID = deviceID;
    }
}
