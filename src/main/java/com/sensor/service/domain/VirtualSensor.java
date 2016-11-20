package com.sensor.service.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Created by sindhya on 11/20/16.
 */

@DynamoDBTable(tableName = "user_sensor")
public class VirtualSensor {

    private int virtual_sensor_id;
    private int sensor_id;
    private String user_email;

    @DynamoDBHashKey(attributeName = "virtual_sensor_id")
    public int getVirtual_sensor_id() {
        return virtual_sensor_id;
    }


    public void setVirtual_sensor_id(int virtual_sensor_id) {
        this.virtual_sensor_id = virtual_sensor_id;
    }

    @DynamoDBAttribute(attributeName = "sensor_id")
    public int getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(int sensor_id) {
        this.sensor_id = sensor_id;
    }

    @DynamoDBAttribute(attributeName = "user_email")
    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
