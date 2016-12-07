package com.sensor.service.domain;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.sensor.service.model.*;
import com.sensor.service.model.db.sensor.physical.Sensor;
import com.sensor.service.model.db.sensor.virtual.VirtualSensor;
import com.sensor.service.model.db.sensor.virtual.VirtualSensorStatus;
import com.sensor.service.model.db.vendor.Vendor;
import com.sensor.service.util.DateFormattingUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by sindhya on 11/20/16.
 */
public class VirtualSensorDAO {

    private List<VirtualSensor> virtual_sensor_list=new ArrayList<>();
    private List<VirtualSensor> virtualSensorResult=new ArrayList<>();
    private List<Sensor> sensorResult=new ArrayList<>();
    private List<Vendor> vendorResult=new ArrayList<>();
    private static final Double PRICE_PER_HOUR = 0.5;

    static BasicAWSCredentials credentials = new BasicAWSCredentials(ApiConstants.ACCESS_KEY, ApiConstants.SECRET_KEY);
    static AmazonDynamoDBClient client = new AmazonDynamoDBClient(credentials).withRegion(Regions.US_WEST_2);

    public void createSensor(VirtualSensor virtualSensor){

        DynamoDBMapper mapper=new DynamoDBMapper(client);

        List<VirtualSensor> sensorScanList=new ArrayList<>();
        DynamoDBScanExpression scanExpression=new DynamoDBScanExpression().withLimit(50);
        sensorScanList=mapper.scan(VirtualSensor.class,scanExpression);
        int id=sensorScanList.size()+1;

        VirtualSensor newVirtualSensor=new VirtualSensor();
        newVirtualSensor.setVirtualSensorId(id);
        newVirtualSensor.setVirtualSensorName(virtualSensor.getVirtualSensorName());
        newVirtualSensor.setSensorId(virtualSensor.getSensorId());
        newVirtualSensor.setUserEmail(virtualSensor.getUserEmail());

        mapper.save(newVirtualSensor);

    }

    public List virtualSensorList(String user_email){

        try {

            DynamoDBMapper mapper = new DynamoDBMapper(client);

            if(!(user_email.equals("0"))) {

                    Map<String, AttributeValue> filterMap = new HashMap<>();
                    filterMap.put(":val1", new AttributeValue().withS(user_email));

                    DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                            .withFilterExpression("user_email=:val1").withExpressionAttributeValues(filterMap);
                    virtualSensorResult = mapper.scan(VirtualSensor.class, scanExpression);

            } else {
                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression().withLimit(50);
                virtualSensorResult=mapper.scan(VirtualSensor.class,scanExpression);

            }

            for(int i=0;i<virtualSensorResult.size();i++){

                Map<String, AttributeValue> filterMap1 = new HashMap<>();
                filterMap1.put(":val2", new AttributeValue().withN(String.valueOf(virtualSensorResult.get(i).getSensorId())));

                DynamoDBScanExpression sensorScanExpression = new DynamoDBScanExpression()
                        .withFilterExpression("sensor_id=:val2").withExpressionAttributeValues(filterMap1);
                sensorResult=mapper.scan(Sensor.class,sensorScanExpression);

                Map<String, AttributeValue> filterMap2 = new HashMap<>();
                filterMap2.put(":val3", new AttributeValue().withS(String.valueOf(sensorResult.get(0).getVendorEmail())));

                DynamoDBScanExpression vendorScanExpression = new DynamoDBScanExpression()
                        .withFilterExpression("vendor_email=:val3").withExpressionAttributeValues(filterMap2);
                vendorResult=mapper.scan(Vendor.class,vendorScanExpression);



                VirtualSensor virtualsensorObj=new VirtualSensor();
                virtualsensorObj.setSensorId(virtualSensorResult.get(i).getSensorId());
                virtualsensorObj.setVirtualSensorName(virtualSensorResult.get(i).getVirtualSensorName());
                virtualsensorObj.setVirtualSensorId(virtualSensorResult.get(i).getVirtualSensorId());
                virtualsensorObj.setUserEmail(virtualSensorResult.get(i).getUserEmail());

                virtualsensorObj.setSensorName(sensorResult.get(0).getSensorName());
                virtualsensorObj.setSensorType(sensorResult.get(0).getSensorType());
                virtualsensorObj.setSensorLocation(sensorResult.get(0).getSensorLocation());
                virtualsensorObj.setSensorStatus(sensorResult.get(0).getSensorStatus());

                virtualsensorObj.setVendorName(vendorResult.get(0).getVendorName());

                virtual_sensor_list.add(virtualsensorObj);
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return virtual_sensor_list;
    }

    public int getPhysicalSensorID(int virtual_sensor_id){

        int physical_sensor_id;

        DynamoDBMapper mapper=new DynamoDBMapper(client);
        Map<String, AttributeValue> filterMap = new HashMap<>();
        filterMap.put(":val1", new AttributeValue().withN(String.valueOf(virtual_sensor_id)));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("virtual_sensor_id=:val1").withExpressionAttributeValues(filterMap);
        virtualSensorResult=mapper.scan(VirtualSensor.class,scanExpression);

        physical_sensor_id=virtualSensorResult.get(0).getSensorId();

        return physical_sensor_id;

    }

    public VirtualSensor findByVirtualSensorId(int virtual_sensor_id){

        DynamoDBMapper mapper = new DynamoDBMapper(client);

        Map<String, AttributeValue> filterMap = new HashMap<>();
        filterMap.put(":val1", new AttributeValue().withN(String.valueOf(virtual_sensor_id)));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("virtual_sensor_id=:val1").withExpressionAttributeValues(filterMap);
        virtualSensorResult=mapper.scan(VirtualSensor.class,scanExpression);

        VirtualSensor virtualsensorObj=null;
        if(virtualSensorResult.size()!=0) {
            virtualsensorObj = new VirtualSensor();
            virtualsensorObj.setVirtualSensorId(virtualSensorResult.get(0).getVirtualSensorId());
            virtualsensorObj.setVirtualSensorName(virtualSensorResult.get(0).getVirtualSensorName());
            virtualsensorObj.setSensorId(virtualSensorResult.get(0).getSensorId());
            virtualsensorObj.setUserEmail(virtualSensorResult.get(0).getUserEmail());
        }

        return virtualsensorObj;
    }

    public void deleteVirtualSensor(int virtual_sensor_id){

        DynamoDBMapper mapper=new DynamoDBMapper(client);
        VirtualSensor virtualsensor=mapper.load(VirtualSensor.class,virtual_sensor_id);
        mapper.delete(virtualsensor);
    }
    
    public String startStopVirtualSensor(int virtual_sensor_id, String  user_id, String status){

        DynamoDBMapper mapper = new DynamoDBMapper(client);
        List<VirtualSensorStatus> vss ;

        if ("stop".equalsIgnoreCase(status)) {
            Map<String, AttributeValue> filterMap = new HashMap<>();
            filterMap.put(":val1", new AttributeValue().withN(String.valueOf(virtual_sensor_id)));
            filterMap.put(":val2", new AttributeValue().withS("now"));

            DynamoDBScanExpression queryExpression = new DynamoDBScanExpression()
                .withFilterExpression("virtual_sensor_id=:val1 and stop_time=:val2")
                .withExpressionAttributeValues(filterMap);
            vss = mapper.scan(VirtualSensorStatus.class, queryExpression);

            if (vss.size() > 0) {
                VirtualSensorStatus rec = vss.get(0);
                Date start_date = DateFormattingUtil.convertStringToDate(rec.getStartTime());
                Date end_date = new Date();
                long diff = start_date.getTime() - end_date.getTime();
                long diffHours = diff / (60 * 60 * 1000);
                rec.setAmount(diffHours * PRICE_PER_HOUR);
                rec.setStopTime(DateFormattingUtil.convertDateToString(end_date));
                mapper.save(rec);
            }
            return "stopped";
        } else {
            VirtualSensorStatus addVss = new VirtualSensorStatus();
            addVss.setUser_id(user_id);
            addVss.setUniqueKey(UUID.randomUUID().toString());
            addVss.setStartTime(DateFormattingUtil.convertDateToString(new Date()));
            addVss.setStopTime("now");
            addVss.setVirtualSensorId(virtual_sensor_id);
            mapper.save(addVss);
            return "started";
        }
    }
    
    
    public Map<Integer,Double> getVirtualSensorBillingInfo(String user_id) {
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        List<VirtualSensorStatus> vss ;
        HashMap<Integer, Double> amountMap = new HashMap<>();
        Map<String, AttributeValue> filterMap = new HashMap<>();
        filterMap.put(":val1", new AttributeValue().withS(String.valueOf(user_id)));
        DynamoDBScanExpression queryExpression = new DynamoDBScanExpression()
            .withFilterExpression("user_id=:val1")
            .withExpressionAttributeValues(filterMap);
        vss = mapper.scan(VirtualSensorStatus.class, queryExpression);
        if (vss.size() > 0) {
            for (VirtualSensorStatus eachVss: vss) {
                Double amt = amountMap.get(eachVss.getVirtualSensorId());
                if (amt !=null) {
                    amt += eachVss.getAmount();
                    amountMap.put(eachVss.getVirtualSensorId(), amt);
                } else {
                    amountMap.put(eachVss.getVirtualSensorId(),eachVss.getAmount());
                }
            }
        }
        return amountMap;
    }


}
