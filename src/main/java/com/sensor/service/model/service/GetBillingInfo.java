package com.sensor.service.model.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rnellaiappan on 12/7/16.
 */
@JsonIgnoreProperties( ignoreUnknown = true)
public class GetBillingInfo {
  Map<Integer, Double> amountMap;

  public Map<Integer, Double> getAmountMap() {
    return amountMap;
  }

  public void setAmountMap(Map<Integer, Double> amountMap) {
    this.amountMap = amountMap;
  }
}
