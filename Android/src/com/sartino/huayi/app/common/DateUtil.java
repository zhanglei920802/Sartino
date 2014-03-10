package com.sartino.huayi.app.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 * 
 * @author Administrator
 * 
 */
public class DateUtil {
	/**
	 * 获取当前时间
	 * 
	 * @return 返回格式化的日期数据
	 */
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		Date date = calendar.getTime();
		return sdf.format(date);
	}

	/**
	 * 获取时间
	 */
	public static String getDafaultDate() {
		return new Date().toLocaleString();
	}

	/**
	 * 用于比较两个时间的大小
	 * 
	 * @param date1
	 *            Date格式
	 * @param date2
	 *            字符串格式:如17:30：00
	 * @return 如果前者大于后者，返回真。否则返回false
	 */
	public static boolean compare(Date date1, String date2) {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date dateStr = null;
		try {
			date1 = (Date) sdf.parseObject(sdf.format(date1));
			dateStr = sdf.parse(date2);
		} catch (ParseException e) {

			System.out.println("DateUtil.compare()::::" + "时间转换出错了");
		}

		return date1.after(dateStr) ? true : false;

	}
}
