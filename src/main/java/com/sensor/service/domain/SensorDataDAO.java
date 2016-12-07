package com.sensor.service.domain;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.sensor.service.model.ApiConstants;
import com.sensor.service.model.db.sensor.data.SensorData;
import com.sensor.service.model.service.SensorDataRequest;
import com.sensor.service.model.db.users.Users;
import com.sensor.service.util.DateFormattingUtil;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sindhya on 11/20/16.
 */
public class SensorDataDAO {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    static BasicAWSCredentials credentials = new BasicAWSCredentials(ApiConstants.ACCESS_KEY, ApiConstants.SECRET_KEY);
    static AmazonDynamoDBClient client = new AmazonDynamoDBClient(credentials).withRegion(Regions.US_WEST_2);
    static DynamoDBMapper mapper;


    public SensorDataDAO(){
        mapper = new DynamoDBMapper(client);
    }

    public List<SensorData> sensorDataListWithDate(Integer sensor_id, Date fromDate, Date toDate){
        if (sensor_id == null)
            return null;
        if (toDate == null) 
            toDate = new Date();
        if (fromDate == null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(toDate);
            cal.add(Calendar.DATE, -7);
            fromDate = cal.getTime();
        }
        Map<String, AttributeValue> eav = new HashMap<>();
        List<SensorData> sensorDataResult=new ArrayList<>();
        eav.put(":val1", new AttributeValue().withN(sensor_id.toString()));
        eav.put(":val2", new AttributeValue().withS(DateFormattingUtil.convertDateToString(fromDate)));
        eav.put(":val3", new AttributeValue().withS(DateFormattingUtil.convertDateToString(toDate)));

        DynamoDBQueryExpression<SensorData> queryExpression = new DynamoDBQueryExpression<SensorData>()
            .withKeyConditionExpression("sensor_id = :val1 and sensor_timestamp between :val2 and :val3")
            .withExpressionAttributeValues(eav); 
        sensorDataResult = mapper.query(SensorData.class, queryExpression);
        for (SensorData result: sensorDataResult){
            System.out.println(result.getSensorDataValue());
        }
        return sensorDataResult;
    }
    
    
    public void persistSensorData (SensorDataRequest sensorDataRequest) {
        if (sensorDataRequest == null || sensorDataRequest.getSensorId() <= 0)
            return;
        SensorData sd = new SensorData();
        sd.setSensorId(sensorDataRequest.getSensorId());
        sd.setTimestamp(DateFormattingUtil.convertDateToString(sensorDataRequest.getTimestamp()));
        sd.setSensorDataType(sensorDataRequest.getSensorDataType());
        sd.setSensorDataValue(sensorDataRequest.getSensorDataValue());
        mapper.save(sd);
    }
}
