package com.sensor.service.model.service;

import com.sensor.service.model.db.sensor.data.SensorData;

import java.util.Date;

/**
 * Created by rnellaiappan on 12/4/16.
 */
public class SensorDataPoint {
  String timestamp;
  String data;
  
  public SensorDataPoint(){
    
  }
  
  public SensorDataPoint(String timestamp, String data){
    this.data = data;
    this.timestamp = timestamp;
    return;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}
