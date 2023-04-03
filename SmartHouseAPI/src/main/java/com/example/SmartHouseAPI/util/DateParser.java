package com.example.SmartHouseAPI.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
	
    public static Date parseDate(String date) {
    	try {
    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    		dateFormat.setLenient(false);
    		Date ret = dateFormat.parse(date);
    		return ret;
    	} catch (Exception e) {
    		e.printStackTrace();    	
    	}
    	return null;
    }
    
    public static String stringifyDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setLenient(false);
        String dateString = dateFormat.format(date);
        return dateString;
    }
}
