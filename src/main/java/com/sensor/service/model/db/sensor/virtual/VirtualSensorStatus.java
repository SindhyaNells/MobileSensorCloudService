package com.sensor.service.model.db.sensor.virtual;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Created by rnellaiappan on 12/6/16.
 */

@DynamoDBTable(tableName = "virtual_sensor_status")
public class VirtualSensorStatus {
  
  
  private String uniqueKey;
  private String startTime;
  private String stopTime;
  private Integer virtualSensorId;
  private String user_id;
  private Double amount;

  @DynamoDBHashKey(attributeName="uniqueKey")
  public String getUniqueKey() {
    return uniqueKey;
  }

  public void setUniqueKey(String uniqueKey) {
    this.uniqueKey = uniqueKey;
  }

  @DynamoDBAttribute(attributeName="start_time")
  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  @DynamoDBAttribute(attributeName="stop_time")
  public String getStopTime() {
    return stopTime;
  }

  public void setStopTime(String stopTime) {
    this.stopTime = stopTime;
  }

  @DynamoDBAttribute(attributeName="virtual_sensor_id")
  public Integer getVirtualSensorId() {
    return virtualSensorId;
  }

  public void setVirtualSensorId(Integer virtualSensorId) {
    this.virtualSensorId = virtualSensorId;
  }

  @DynamoDBAttribute(attributeName="amount")
  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  @DynamoDBAttribute(attributeName="user_id")
  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }
}
