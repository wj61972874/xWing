/*
 * 作者		www.TheWk.cn.vc
 * 开发环境	Windows7 64位 MyEclipse8.6 JDK1.6.0_37
 * 开发日期	2013-11-13
 */
package com.xwin.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <hr/>
 * 
 * @author www.TheWk.cn.vc
 * @version 1.0 2013-11-13
 * @class common.utils.DateUtils
 */
public abstract class DateUtils {

	private static final DateFormat DFYYYYMMDD = new SimpleDateFormat("yyMMdd");
	private static final DateFormat DFyyyyMMddHHmmssSSS = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS");
	private static final DateFormat DFyyMMddHHmmssSSS = new SimpleDateFormat(
			"yyMMddHHmmssSSS");

	private DateUtils() {

	}

	/**
	 * 获取明天的日期
	 */
	public static Date getToday() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}
	/**
	 * 获取昨天的日期
	 */
	public static Date getYesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * 获取明天的日期
	 */
	public static Date getTomorrow() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 传入日期,增减该日期的秒数
	 */
	public static Date addSeconds(Date source, int seconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(source);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}

	/**
	 * 传入日期,增减该日期的天数
	 */
	public static Date addDays(Date source, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(source);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();

	}

	/**
	 * 传入日期,增减该日期的月份
	 */
	public static Date addMonths(Date source, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(source);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}

	/**
	 * 获取当天日期的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatStringByDFYYMMDD(Date date) {
		return DFYYYYMMDD.format(date);
	}

	/**
	 * 获取当天包含毫秒的日期的字符串(四位年)
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatStringByDFyyyyMMddHHmmssSSS(Date date) {
		return DFyyyyMMddHHmmssSSS.format(date);
	}
	
	/**
	 * 获取当天包含毫秒的日期的字符串(两位年)
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatStringByDFyyMMddHHmmssSSS(Date date) {
		return DFyyMMddHHmmssSSS.format(date);
	}

	/**
	 * 比较两个日期是否同一天
	 * 
	 * @param day1
	 * @param day2
	 * @return
	 */
	public static boolean isSameDay(Date day1, Date day2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ds1 = sdf.format(day1);
		String ds2 = sdf.format(day2);
		if (ds1.equals(ds2)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 比较两个日期 相差的的天数，不管小时数。即使今天23点和明天1点之间也是1天
	 * 
	 * @param fDate
	 * @param oDate
	 * @return
	 */
	public static int daysOfTwo(Date fDate, Date oDate) {
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.setTime(fDate);
		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
		aCalendar.setTime(oDate);
		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
		return day2 - day1;
	}

	//获取本周的开始时间
	public static Date getBeginDayOfWeek() {
		Date date = new Date();
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek);
		return getDayStartTime(cal.getTime());
	}
	//获取本周的结束时间
	public static Date getEndDayOfWeek(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(getBeginDayOfWeek());
		cal.add(Calendar.DAY_OF_WEEK, 6);
		Date weekEndSta = cal.getTime();
		return getDayEndTime(weekEndSta);
	}


	//获取某个日期的开始时间
	public static Timestamp getDayStartTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if(null != d) calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),    calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	//获取某个日期的结束时间
	public static Timestamp getDayEndTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if(null != d) calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),    calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return new Timestamp(calendar.getTimeInMillis());
	}

	public static void main(String[] args) {
		System.out.println(getFormatStringByDFyyMMddHHmmssSSS(new Date()));
	}

}
