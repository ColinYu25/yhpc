package com.safetys.nbyhpc.util;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {

	public static final String SHORT_TIME = "yyyy-MM-dd";
	public static final String SHORT_TIME_PATTERN = "yyyy.MM.dd";
	static final Map<Integer, String> DATE_MAP = new HashMap<Integer, String>();
	static{
		DATE_MAP.put(0, "〇");
		DATE_MAP.put(1, "一");
		DATE_MAP.put(2, "二");
		DATE_MAP.put(3, "三");
		DATE_MAP.put(4, "四");
		DATE_MAP.put(5, "五");
		DATE_MAP.put(6, "六");
		DATE_MAP.put(7, "七");
		DATE_MAP.put(8, "八");
		DATE_MAP.put(9, "九");
	}
	
	/**
	 * 日期添加天数
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDateAddDay(Date date, int day) {
		if (date == null) {
			return null;
		}
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(GregorianCalendar.DATE, day);
		return cal.getTime();
	}
	
	/**
	 * 日期添加天数
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static int getQuarter(int month) {
		int quarter = 0;
		if(month>0 && month<=3){
			quarter = 1;
		}else if(month>3 && month<=6){
			quarter = 2;
		}else if(month>6 && month<=9){
			quarter = 3;
		}else if(month>9 && month<=12){
			quarter = 4;
		}
		return quarter;
	}
	
	/**
	 * 根据字符串格式的时间，返回Date
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date, String pattern){
		DateFormat sdf = null;
		if (pattern == null){
			sdf = new SimpleDateFormat(SHORT_TIME_PATTERN);
		}else{
			sdf = new SimpleDateFormat(pattern);
		}
		
		try {
			ParsePosition pos = new ParsePosition(0);   
			return sdf.parse(date, pos);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	/**
	 * 根据Date，返回字符型日期格式
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String date2Str(Date date, String pattern){
		if (date == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		if (notEmpty(pattern)){
			sdf.applyPattern(pattern);
		}else{
			sdf.applyPattern("yyyy-MM-dd");
		}
		return sdf.format(date);
	}
	
	/**
	 * 多种格式匹配转换，如
	 * Date date = str2Date("2010 年 12 月 12 日", "yyyy-MM-dd", "yyyy 年 MM 月 dd 日");
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static Date str2Date(String str, String... pattern){
		if (pattern == null || pattern.length == 0){
			return null;
		}
		Date result = null;
		for (String p : pattern) {
			result = str2Date(str, p);
			if (result != null){
				return result;
			}
		}
		return null;
	}
	
	public static Date plusMonth(Date date, int amount) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONDAY, amount);
		return calendar.getTime();
	}
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean notEmpty(String str){
		if (str != null && str.trim().length() > 0){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		//str2Date("2010 年 12 月 12 日", "yyyy 年 MM 月 dd 日");
		Date date = str2Date("2011 年 01 月 05 日", "yyyy-MM-dd", "yyyy 年 MM 月 dd 日");
		System.out.println(date2Str(date, null));
	}

}
