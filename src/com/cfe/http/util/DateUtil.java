package com.cfe.http.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static Date stringToDateyyyyMMddHHmmss(String str){
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = formatter.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public static String splitStringDate(String str, int flag){
		String[] strList = str.split(" "); 
		if(flag !=0 || flag != 1){
			return "";
		}
		if(flag <= strList.length)
			return strList[flag];
		else 
			return "";		
	}

}
