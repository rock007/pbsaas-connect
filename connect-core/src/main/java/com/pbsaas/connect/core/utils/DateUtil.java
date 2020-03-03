package com.pbsaas.connect.core.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {

	public static SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");

	public static SimpleDateFormat dtfull_sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static SimpleDateFormat yyyymmdd_sd = new SimpleDateFormat("yyyy-MM-dd");

	public static SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");

	public static SimpleDateFormat HHmmss_sd = new SimpleDateFormat("HH:mm:ss");

	public static String GetCurDT() {
		return sd.format(new java.util.Date());
	}

	public static String CurTime(Date m) {
		return HHmmss_sd.format(m);
	}

	public static String yyyymmdd(Date m) {
		return yyyymmdd_sd.format(m);
	}

	public static String fullDateStr(Date m) {
		return dtfull_sd.format(m);
	}

	public static Date getDate(String m, SimpleDateFormat sdf) {

		Date date = null;
		try {
			date = sdf.parse(m);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return date;
	}

	public static Date parseDateYYYYMMdd(String m) {

		return getDate(m, yyyymmdd);
	}

	public static String getDate(Date date, SimpleDateFormat sd) {

		if (date == null)
			return "";
		return sd.format(date);
	}

	/**
	 * 默认 日期时间 格式 yyyy-MM-dd HH:mm:ss
	 */
	public static final String PATTERN_STANDARD = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 默认 日期格式 yyyy-MM-dd
	 */
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	/**
	 * 默认 时间格式
	 */
	public static final String PATTERN_TIME = "HH:mm:ss";
	/**
	 * 每月1日
	 */
	public static final String PATTERN_MONTH = "yyyy-MM-01";
	/**
	 * 自动匹配字符串格式
	 */
	public static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
			"yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" };

	/**
	 * Timestamp 格式化成字符串，使用默认格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String timestamp2String(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		return DateFormatUtils.format(timestamp, PATTERN_STANDARD);
	}

	/**
	 * Timestamp 格式化 自定义格式
	 * 
	 * @param timestamp
	 * @param pattern
	 * @return
	 */
	public static String timestamp2String(Timestamp timestamp, String pattern) {
		if (timestamp == null) {
			return null;
		}
		return DateFormatUtils.format(timestamp, pattern);
	}

	/**
	 * Date 格式化成字符串，使用默认格式 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String date2String(Date date) {
		if (date == null) {
			return null;
		}
		return DateFormatUtils.format(date, PATTERN_DATE);
	}

	/**
	 * Date 格式化 自定义格式
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String date2String(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static Timestamp currentTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 获取当前日期yyyy-MM-dd String
	 * 
	 * @return
	 */
	public static String currentDateToString() {

		return date2String(new Date());
	}

	/**
	 * 获取当前时间 HH:mm:ss String类型
	 * 
	 * @return
	 */
	public static String currentTimeToString() {

		return date2String(new Date(), PATTERN_TIME);

	}

	/**
	 * 获取当前日期yyyy-MM-dd HH:mm:ss String
	 * 
	 * @return
	 */
	public static String currentDateTimeToString() {

		return date2String(new Date(), PATTERN_STANDARD);
	}

	/**
	 * 两个时间相减
	 * 
	 * @param firsttime
	 * @param secondtime
	 * @return
	 */
	public static long subtractTime(String firsttime, String secondtime) {

		return string2Date(firsttime, PATTERN_TIME).getTime() - string2Date(secondtime, PATTERN_TIME).getTime();

	}

	/**
	 * 字符串转换为 Timestamp 自动匹配格式
	 * 
	 * @param strDateTime
	 * @return 如果传入字符串为null，或者空字符串，则返回null
	 */
	public static Timestamp string2Timestamp(String strDateTime) {

		return new Timestamp(string2Date(strDateTime).getTime());
	}

	/**
	 * 字符串 转换为 Timestamp 传入字符串格式
	 * 
	 * @param strDateTime
	 * @param pattern
	 * @return
	 */
	public static Timestamp string2Timestamp(String strDateTime, String pattern) {

		return new Timestamp(string2Date(strDateTime, pattern).getTime());
	}

	/**
	 * 字符串转换为 Date 自动匹配格式
	 * 
	 * @param strDate
	 * @return 如果传入字符串为null，或者空字符串，则返回null
	 */
	public static Date string2Date(String strDate) {
		if (StringUtils.isBlank(strDate)) {
			return null;
		}
		try {
			return DateUtils.parseDate(strDate.trim(), parsePatterns);
		} catch (ParseException e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 字符串转换为 Date 传入字符串格式
	 * 
	 * @param strDate 时间
	 * @param pattern 字符串 格式
	 * @return 如果传入字符串为null，或者空字符串，则返回null
	 */
	public static Date string2Date(String strDate, String pattern) {

		if (StringUtils.isBlank(strDate)) {
			return null;
		}
		try {
			return DateUtils.parseDate(strDate.trim(), new String[] { pattern });
		} catch (ParseException e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 前后移动日期
	 * 
	 * @param date
	 * @param move -1:向前移动一天，1:向后移动一天
	 * @return
	 */
	public static Date moveDate(Date date, int move) {

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, move);// 把日期往后增加一天.整数往后推,负数往前移动
		return calendar.getTime(); // 这个时间就是日期移动之后的时间

	}

	/***
	 * 时间字符串转换成long
	 * 
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static long string2long(String strDate, String pattern) {
		if (strDate == null || strDate.equals("")) {
			return 0l;
		}
		if (pattern == null || pattern.equals("")) {
			pattern = DateUtil.PATTERN_DATE;
		}
		Date d = string2Date(strDate, pattern);
		if (d == null) {
			return 0L;
		}
		return d.getTime();
	}

	/**
	 * 获得当前月初日期 yyyy-MM-01
	 * 
	 * @return
	 */
	public static String currentMonthDateToString() {

		return date2String(new Date(), PATTERN_MONTH);
	}

	/**
	 * 获取当天开始时间
	 * 
	 * @return
	 */
	public static String currentDayDateStartToString(String stime) {
		// (stime.split(" ")[0], DateUtil.PATTERN_DATE);
		Date d = string2Date(stime.split(" ")[0], DateUtil.PATTERN_DATE);

		return date2String(d, "yyyy-MM-dd 00:00:01");
	}

	/**
	 * 获取当天结束时间
	 * 
	 * @return
	 */
	public static String currentDayEndDateToString(String stime) {
		// (stime.split(" ")[0], DateUtil.PATTERN_DATE);
		Date d = string2Date(stime.split(" ")[0], DateUtil.PATTERN_DATE);

		return date2String(d, "yyyy-MM-dd 23:59:59");
	}

	public static String formatDate(Date m, String format1) {

		if (m == null)
			return "";

		try {
			// return format1.format(m);
			return DateFormatUtils.format(m, format1);
		} catch (Exception ex) {

		}

		return "";
	}

	/*
	 * 解决MYSQL5。7 以上数据库dateTime为0000-00-00 00:00:00的问题
	 */
	public static Timestamp getCurrentTimeStamp() {
		return DateUtil.string2Timestamp(DateUtil.currentDateTimeToString());
	}

	public static int getDayLeft(Date now, String endDateStr) {

		Date endDateTemp = string2Date(endDateStr, "yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		cal.setTime(endDateTemp);

		Date endDate = cal.getTime();

		Long days = (endDate.getTime() - now.getTime()) / (24 * 60 * 60 * 1000);

		return days.intValue();

	}

	public static int getDaySpan(String beginDateStr, String endDateStr) {

		Date now = string2Date(beginDateStr, "yyyy-MM-dd");
		Date endDateTemp = string2Date(endDateStr, "yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		cal.setTime(endDateTemp);

		Date endDate = cal.getTime();

		Long days = (endDate.getTime() - now.getTime()) / (24 * 60 * 60 * 1000);

		return days.intValue();

	}

	/**
	 * 根据时间（yyyy-mm-dd）加减天数获取新的日期
	 * @param beginDate
	 * @param days
	 * @return
	 */
	public static String getDayPlus(String beginDate, Integer days) {

		Date now = string2Date(beginDate, "yyyy-MM-dd");
		//Date endDateTemp = string2Date(endDateStr, "yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		cal.setTime(now);

		cal.add(Calendar.DAY_OF_MONTH, days);
		
		Date endDate = cal.getTime();

		return formatDate(endDate,"yyyy-MM-dd");
	}
	
	public static String getDatePlus(String beginDate, int field, int amount) {

		Date now = string2Date(beginDate, "yyyy-MM-dd");
		//Date endDateTemp = string2Date(endDateStr, "yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		cal.setTime(now);

		cal.add(field, amount);
		
		Date endDate = cal.getTime();

		return formatDate(endDate,"yyyy-MM-dd");
	}
	
	public static Date getThisMonthFirstDay() {
		Calendar cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		return cale.getTime();
	}

	public static Date getThisMonthLastDay() {
		Calendar cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		return cale.getTime();
	}

	public static int getDaysOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static String getNewDate() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss ");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai")); // 格式差8小时
		return sdf.format(d);
	}

	// 获取今日零点时间
	public static String getDayZero() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date zero = calendar.getTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(zero);
	}

	public static String getYesterDay() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return sdf.format(date);
	}

	public static String getLastMonth() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		date = calendar.getTime();
		return sdf.format(date);
	}

	// 将string类型时间格式转化为date时间格式
	public static Date getDate(String date) {

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			return sf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 获取两个时间之间相差多少天
	public static int differentDaysByMillisecond(Date date1, Date date2) {

		int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));

		return days;
	}

	public static int getMonthDiff(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		if (c1.getTimeInMillis() < c2.getTimeInMillis())
			return 0;
		int year1 = c1.get(Calendar.YEAR);
		int year2 = c2.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int month2 = c2.get(Calendar.MONTH);
		int day1 = c1.get(Calendar.DAY_OF_MONTH);
		int day2 = c2.get(Calendar.DAY_OF_MONTH);
		// 获取年的差值 假设 d1 = 2015-8-16 d2 = 2011-9-30
		int yearInterval = year1 - year2;
		// 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
		if (month1 < month2 || month1 == month2 && day1 < day2)
			yearInterval--;
		// 获取月数差值
		int monthInterval = (month1 + 12) - month2;
		if (day1 < day2)
			monthInterval--;
		monthInterval %= 12;
		return yearInterval * 12 + monthInterval;
	}

	/**
	 * 获取未来 第 past 天的日期
	 * 
	 * @param past
	 * @return
	 */
	public static String getFetureDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		return result;
	}
}
