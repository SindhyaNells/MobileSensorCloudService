package com.sensor.service.model.db.sensor.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Date;

/**
 * Created by rnellaiappan on 12/4/16.
 */

@DynamoDBTable(tableName = "sensor_data")
public class SensorData {

  private int sensorId;
  private String timestamp;
  private String sensorDataType;
  private String sensorDataValue;


  @DynamoDBHashKey(attributeName="sensor_id")
  public int getSensorId() {
    return sensorId;
  }

  public void setSensorId(int sensorId) {
    this.sensorId = sensorId;
  }

  @DynamoDBRangeKey(attributeName="sensor_timestamp")
  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  @DynamoDBAttribute(attributeName="sensor_data_type")
  public String getSensorDataType() {
    return sensorDataType;
  }

  public void setSensorDataType(String sensorDataType) {
    this.sensorDataType = sensorDataType;
  }

  @DynamoDBAttribute(attributeName="sensor_data_value")
  public String getSensorDataValue() {
    return sensorDataValue;
  }

  public void setSensorDataValue(String sensorDataValue) {
    this.sensorDataValue = sensorDataValue;
  }
}
