package com.sensor.service.model.service;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by sindhya on 11/20/16.
 */
public class GetSensorDataResponse {

    List<SensorDataPoint> sensorDataPoints;
    String sensorDataType;
    Integer physicalSensorId;

    public Integer getPhysicalSensorId() {
        return physicalSensorId;
    }

    public void setPhysicalSensorId(Integer physicalSensorId) {
        this.physicalSensorId = physicalSensorId;
    }

    public List<SensorDataPoint> getSensorDataPoints() {
        return sensorDataPoints;
    }

    public void setSensorDataPoints(List<SensorDataPoint> sensorDataPoints) {
        this.sensorDataPoints = sensorDataPoints;
    }

    public String getSensorDataType() {
        return sensorDataType;
    }

    public void setSensorDataType(String sensorDataType) {
        this.sensorDataType = sensorDataType;
    }
}
