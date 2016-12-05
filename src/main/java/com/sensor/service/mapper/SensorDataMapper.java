package com.sensor.service.mapper;

import com.sensor.service.model.db.sensor.data.SensorData;
import com.sensor.service.model.service.GetSensorDataResponse;
import com.sensor.service.model.service.SensorDataPoint;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rnellaiappan on 12/5/16.
 */
public class SensorDataMapper {
  
  public static void mapSensorData(List<SensorData> sensorDataList, 
                                   GetSensorDataResponse getSensorDataResponse){
    if (sensorDataList !=null) {
      List<SensorDataPoint> responseList = new ArrayList<>();
      for (SensorData data : sensorDataList) {
        getSensorDataResponse.setSensorDataType(data.getSensorDataType());
        responseList.add(new SensorDataPoint(data.getTimestamp(), data.getSensorDataValue()));
      }
      if(!CollectionUtils.isEmpty(responseList)){
        getSensorDataResponse.setSensorDataPoints(responseList);
      }
    }
  }
}
