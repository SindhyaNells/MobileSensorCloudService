package com.sensor.service.model.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by rnellaiappan on 12/5/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSensorDataTimeRange {
  String from;
  String to;

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }
}
