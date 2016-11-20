package com.sensor.service.domain;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sindhya on 11/20/16.
 */
public class VirtualSensorDAO {

    private List<VirtualSensor> virtual_sensor_list=new ArrayList<>();
    private List<VirtualSensor> virtualSensorResult=new ArrayList<>();
    static AmazonDynamoDBClient client = new AmazonDynamoDBClient(new ProfileCredentialsProvider()).withRegion(Regions.US_WEST_2);

    public void createSensor(VirtualSensor virtualSensor){

        VirtualSensor newVirtualSensor=new VirtualSensor();

        DynamoDBMapper mapper=new DynamoDBMapper(client);

        newVirtualSensor.setVirtual_sensor_id(virtualSensor.getVirtual_sensor_id());
        newVirtualSensor.setSensor_id(virtualSensor.getSensor_id());
        newVirtualSensor.setUser_email(virtualSensor.getUser_email());

        mapper.save(newVirtualSensor);

    }

    public List virtualSensorList(int sensor_id){

        try {

            DynamoDBMapper mapper = new DynamoDBMapper(client);

            if(!(sensor_id==0)) {
                Map<String, AttributeValue> filterMap = new HashMap<>();
                filterMap.put(":val1", new AttributeValue().withN(String.valueOf(sensor_id)));

                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                        .withFilterExpression("sensor_id=:val1").withExpressionAttributeValues(filterMap);
                virtualSensorResult=mapper.scan(VirtualSensor.class,scanExpression);

            }else{
                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withLimit(50);
                virtualSensorResult=mapper.scan(VirtualSensor.class,scanExpression);
            }

            for(int i=0;i<virtualSensorResult.size();i++){
                VirtualSensor virtualsensorObj=new VirtualSensor();
                virtualsensorObj.setSensor_id(virtualSensorResult.get(i).getSensor_id());
                virtualsensorObj.setVirtual_sensor_id(virtualSensorResult.get(i).getVirtual_sensor_id());
                virtualsensorObj.setUser_email(virtualSensorResult.get(i).getUser_email());

                virtual_sensor_list.add(virtualsensorObj);

            }

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return virtual_sensor_list;
    }


}
