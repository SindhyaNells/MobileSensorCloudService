package com.sensor.service.domain;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.sensor.service.model.ApiConstants;

import java.text.SimpleDateFormat;

/**
 * Created by sindhya on 12/6/16.
 */
public class VirtualSensorDataDAO {


    static BasicAWSCredentials credentials = new BasicAWSCredentials(ApiConstants.ACCESS_KEY, ApiConstants.SECRET_KEY);
    static AmazonDynamoDBClient client = new AmazonDynamoDBClient(credentials).withRegion(Regions.US_WEST_2);
   static DynamoDBMapper mapper;

    public VirtualSensorDataDAO(){
        mapper=new DynamoDBMapper(client);
    }



}
