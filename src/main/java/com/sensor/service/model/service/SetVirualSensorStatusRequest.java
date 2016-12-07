package com.sensor.service.model.service;

/**
 * Created by rnellaiappan on 12/6/16.
 */
public class SetVirualSensorStatusRequest {
  
  Integer sensorId;
  String userId;

  public Integer getSensorId() {
    return sensorId;
  }

  public void setSensorId(Integer sensorId) {
    this.sensorId = sensorId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
}
