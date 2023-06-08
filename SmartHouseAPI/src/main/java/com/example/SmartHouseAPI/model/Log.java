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
    private Integer houseID;
    @PrimaryKeyColumn(name = "device_id",type = PrimaryKeyType.CLUSTERED,ordering = Ordering.ASCENDING)
    private Integer deviceID;
    @PrimaryKeyColumn(name = "exact_time",type = PrimaryKeyType.CLUSTERED,ordering = Ordering.DESCENDING)
    private LocalDateTime exactTime;

    @Column("received_value")
    private float receivedValue;

    @Column("is_alarm")
    private boolean isAlarm;

    public Log(Integer houseID, Integer deviceID, LocalDateTime exactTime, float receivedValue,boolean isAlarm) {
        this.houseID = houseID;
        this.deviceID = deviceID;
        this.exactTime = exactTime;
        this.receivedValue = receivedValue;
        this.isAlarm = isAlarm;
    }

    public boolean isAlarm() {
        return isAlarm;
    }

    public void setAlarm(boolean alarm) {
        isAlarm = alarm;
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
        return "Log{" +
                "houseID=" + houseID +
                ", deviceID=" + deviceID +
                ", exactTime=" + exactTime +
                ", receivedValue=" + receivedValue +
                ", isAlarm=" + isAlarm +
                '}';
    }
}
