package com.example.SmartHouseAPI.model;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

@Table("logs")
public class Log{

    @PrimaryKeyColumn(name = "house_id",type = PrimaryKeyType.PARTITIONED,ordering = Ordering.ASCENDING)
    private String houseID;
    @PrimaryKeyColumn(name = "device_id",type = PrimaryKeyType.CLUSTERED,ordering = Ordering.ASCENDING)
    private String deviceID;
    @PrimaryKeyColumn(name = "exact_time",type = PrimaryKeyType.CLUSTERED,ordering = Ordering.DESCENDING)
    private LocalDateTime exactTime;

    @Column("received_value")
    private float receivedValue;

    public Log(String houseID, String deviceID, LocalDateTime exactTime, float receivedValue) {
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
