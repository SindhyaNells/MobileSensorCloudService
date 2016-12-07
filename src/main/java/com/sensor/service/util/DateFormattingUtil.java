package com.sensor.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by rnellaiappan on 12/4/16.
 */
public class DateFormattingUtil {
  private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
  
  public static String convertDateToString(Date date){
    dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    return dateFormatter.format(date);
  }
  
  public static Date convertStringToDate(String date){
    if (date == null)
      return null;
    dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
    Date convertedDate = new Date();
    try {
      convertedDate = dateFormatter.parse(date);
    } catch (ParseException e) {
      System.out.println(e);
    }
    return convertedDate;
  }
}
