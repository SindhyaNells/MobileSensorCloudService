package com.sensor.service.model.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * Created by rnellaiappan on 12/4/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorDataRequest {
  
  private int sensorId;
  private Date timestamp;
  private String sensorDataType;
  private String sensorDataValue;

  public int getSensorId() {
    return sensorId;
  }

  public void setSensorId(int sensorId) {
    this.sensorId = sensorId;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getSensorDataType() {
    return sensorDataType;
  }

  public void setSensorDataType(String sensorDataType) {
    this.sensorDataType = sensorDataType;
  }

  public String getSensorDataValue() {
    return sensorDataValue;
  }

  public void setSensorDataValue(String sensorDataValue) {
    this.sensorDataValue = sensorDataValue;
  }
}
