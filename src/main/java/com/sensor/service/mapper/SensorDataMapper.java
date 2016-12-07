package com.sensor.service.mapper;

import com.sensor.service.domain.SensorDataDAO;
import com.sensor.service.model.db.sensor.data.SensorData;
import com.sensor.service.model.service.GetSensorDataResponse;
import com.sensor.service.model.service.GetSensorDataTimeRange;
import com.sensor.service.model.service.SensorDataPoint;

import com.sensor.service.util.DateFormattingUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
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
        responseList.add(new SensorDataPoint(DateFormattingUtil.convertStringToDate(data.getTimestamp()),
                data.getSensorDataValue()));
      }
      if(!CollectionUtils.isEmpty(responseList)){
        getSensorDataResponse.setSensorDataPoints(responseList);
      }
    }
  }


  public static GetSensorDataResponse getSensorDataForPhysicalSensor(GetSensorDataTimeRange timeRange,
                                                                   Integer p_sensor_id){
    SensorDataDAO sdDao = new SensorDataDAO();
    Date fromDate = DateFormattingUtil.convertStringToDate(timeRange.getFrom());
    Date toDate = DateFormattingUtil.convertStringToDate(timeRange.getTo());

    List<SensorData> sensorData =  sdDao.sensorDataListWithDate(p_sensor_id, fromDate, toDate);

    if (sensorData==null)
      return null;

    GetSensorDataResponse ssr = new GetSensorDataResponse();
    SensorDataMapper.mapSensorData(sensorData, ssr);
    ssr.setPhysicalSensorId(p_sensor_id);
    return ssr;
  }



}
