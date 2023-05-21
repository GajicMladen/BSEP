package com.example.SmartHouseAPI.model;

import com.example.SmartHouseAPI.enums.DeviceType;
import jakarta.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "realestate_id")
    private Realestate realestate;

    public Device() {
    }

    public Device(Long deviceID, DeviceType deviceType, boolean readData, String description, Realestate realestate) {
        this.deviceID = deviceID;
        this.deviceType = deviceType;
        this.readData = readData;
        this.description = description;
        this.realestate = realestate;
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
