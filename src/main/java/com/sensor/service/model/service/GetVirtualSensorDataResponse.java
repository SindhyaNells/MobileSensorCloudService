package com.sensor.service.model.service;

import java.util.List;

/**
 * Created by sindhya on 12/6/16.
 */
public class GetVirtualSensorDataResponse {
    private List<GetSensorDataResponse>  allSensorData;

    public List<GetSensorDataResponse> getAllSensorData() {
        return allSensorData;
    }

    public void setAllSensorData(List<GetSensorDataResponse> allSensorData) {
        this.allSensorData = allSensorData;
    }
}
