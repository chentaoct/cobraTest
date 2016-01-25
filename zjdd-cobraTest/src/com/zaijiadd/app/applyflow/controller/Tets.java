/**
 * @(#)Tets.java 2015年12月1日 Copyright 2015 it.kedacom.com, Inc. All rights
 *               reserved.
 */

package com.zaijiadd.app.applyflow.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zaijiadd.app.applyflow.entity.ApplyStore;
import com.zaijiadd.app.common.utils.DateUtils;

/**
 * (用一句话描述类的主要功能)
 * @author chentao
 * @date 2015年12月1日
 */

public class Tets {

	/**
	 * (用一句话描述方法的主要功能)
	 * @param args
	 * @throws ParseException
	 */

	public static void main(String[] args) throws ParseException {
		// testTime();
		// testBigDecimal();
		// testJson();
		// testrand();
		// testTime1();
		// json2Map();
		// testBigDecimal2();
		// testInteger();

		// testBigDecimal2();
		// testDate();
		// testRandom();
		// testRandom2();
		// testPos();
		// testAdd();
		// testFastJson();
		String path = "/home/admin/apache-tomcat-8.0.29/webapps/cobra/"; 
		path = path.substring(0, path.lastIndexOf("webapps")) + "webapps" + File.separator + "upload";
		System.out.println(format(120000));

	}

	public static String format(long ms) {// 将毫秒数换算成x天x时x分x秒x毫秒
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		String strDay = day < 10 ? "0" + day : "" + day;
		String strHour = hour < 10 ? "0" + hour : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		String strSecond = second < 10 ? "0" + second : "" + second;
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
		return strDay + " " + strHour + ":" + strMinute + ":" + strSecond + " " + strMilliSecond;
	}

	/**
	 * (用一句话描述方法的主要功能)
	 */

	private static void testFastJson() {

	}

	/**
	 * (用一句话描述方法的主要功能)
	 */

	private static void testAdd() {
		int a = 0;
		System.out.println(a++);
		System.out.println(a);
	}

	/**
	 * (用一句话描述方法的主要功能)
	 */

	private static void testPos() {
		String serialNumberStr = "123";
		System.out.println(serialNumberStr.length());
		if (serialNumberStr.length() <= 6) {
			int zeroNum = 6 - serialNumberStr.length();
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < zeroNum; i++) {
				stringBuilder.append(0);

			}
			stringBuilder.append(serialNumberStr);
			System.out.println(stringBuilder.toString());
		}

	}

	/**
	 * (用一句话描述方法的主要功能)
	 */

	private static void testRandom2() {
		Set<String> m = new HashSet<String>();
		for (int i = 0; i < 100000000; i++) {
			Random r = new Random();
			Double d = r.nextDouble();
			String s = d + "";
			s = s.substring(3, 3 + 6);
			if (!m.contains(s)) {
				m.add(s);
			} else {
				System.out.println("用的多少" + m.size());
			}
		}

	}

	/**
	 * (用一句话描述方法的主要功能)
	 */

	private static void testRandom() {

		Set<Integer> m = new HashSet<Integer>();
		Set<Integer> m2 = new HashSet<Integer>();
		for (int i = 0; i < 100000000; i++) {
			int sixRandom = sixRandom();
			if (!m.contains(sixRandom)) {
				m.add(sixRandom);
				System.out.println("不包含" + i);
			} else {
				// m2.add(sixRandom);
				// System.out.println("包含的" + i);
				// if (i == 1868257) {
				// System.out.println("停止");
				// System.out.println("不重复的" + (m2.size() - m.size()));
				// break;
				// }
			}

			System.out.println("长度" + m.size());
		}

	}

	/**
	 * (用一句话描述方法的主要功能)
	 * @return
	 */

	private static int sixRandom() {
		int[] array = {
				0, 1, 2, 3, 4, 5, 6, 7, 8, 9
		};
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < 6; i++) {
			result = result * 10 + array[i];
		}

		return result;
	}

	/**
	 * (用一句话描述方法的主要功能)
	 * @throws ParseException
	 */

	private static void testDate() throws ParseException {
		String dateString = "2015-12-09 15:35:26";
		System.out.println(dateString);
		Long thirdTime = (long) (1 * 72 * 60 * 60 * 1000);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parseDate = simpleDateFormat.parse(dateString);
		long time = parseDate.getTime();
		System.out.println("time" + time);

		long createdDatetTimeThird = time + thirdTime;

		String threeformat = simpleDateFormat.format(new Date(createdDatetTimeThird));
		System.out.println("threeformat" + threeformat);

		long test1 = 1449568552000L;
		String threeformattest1 = simpleDateFormat.format(new Date(test1));
		System.out.println(threeformattest1);
		long createdDatetTimeThird1 = test1 + thirdTime;
		System.out.println(simpleDateFormat.format(new Date(createdDatetTimeThird1)));

		// testBigDecimal2();

	}

	/**
	 * (用一句话描述方法的主要功能)
	 */

	private static void testInteger() {
		BigDecimal cityDealershipMoney = new BigDecimal(1);
		System.out.println(cityDealershipMoney);
	}

	/**
	 * (用一句话描述方法的主要功能)
	 */

	private static void testBigDecimal2() {
		Integer q = 7;
		BigDecimal bigDecimal = new BigDecimal(q);
		System.out.println(bigDecimal);

	}

	private static void json2Map() {
		String jsonData = "{type:1,userId:1,page:1,pageCount:10}";
		@SuppressWarnings("unchecked")
		Map<String, Object> jsonMap = JSON.parseObject(jsonData, HashMap.class);
		String pageKey = "page";
		String countKey = "pageCount";
		//
		if (jsonMap.containsKey(pageKey)) {
			int pageCount = (int) jsonMap.get(countKey);// 每页的数量
			int page = (int) jsonMap.get(pageKey);
			jsonMap.remove(pageKey);
			jsonMap.remove(countKey);
			jsonMap.put("start", (page - 1) * pageCount);
			jsonMap.put("end", pageCount);
		}
		List<HashMap> configDataList = (List<HashMap>) jsonMap.get("configs");

		for (HashMap configData : configDataList) {// 这行出错
			int roleId = (Integer) configData.get("type");
			System.out.println("config.type:" + roleId);
		}

		// testTime1();
		testIf();
	}

	/**
	 * (用一句话描述方法的主要功能)
	 */

	private static void testIf() {
		if (3 > 2) {
			System.out.println(1);
		}
		System.out.println(2);
		System.out.println(3);
		if (3 > 2) {
			System.out.println(1);
		} else {
			System.out.println(2);
		}

		System.out.println(3);
	}

	/**
	 * (用一句话描述方法的主要功能)
	 */

	private static void testTime1() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		System.out.println(date);
		System.out.println(simpleDateFormat.format(date));
		long time = date.getTime();
		System.out.println("今天时间" + date.getTime());
		Long thirdDayTime = (long) (1 * 72 * 60 * 60 * 1000);
		System.out.println("thirdDay:" + thirdDayTime);

		long thirdDay = time + thirdDayTime;
		System.out.println("thirdDay三天之后的Long:" + thirdDay);
		Date date2 = new Date(thirdDay);

		String format = simpleDateFormat.format(date2);
		System.out.println("三天之后：" + format);
		// date.getTime()+thirdDay;thirdDay
		// System.out.println("TIAN"+);
	}

	/**
	 * (用一句话描述方法的主要功能)
	 */

	private static void testrand() {
		Random r = new Random();
		Double d = r.nextDouble();
		System.out.println(d);
		String s = d + "";
		s = s.substring(3, 3 + 6);
		System.out.println(s);
	}

	/**
	 * (用一句话描述方法的主要功能)
	 */

	private static void testJson() {
		ApplyStore applyStore = new ApplyStore();
		String jsonString = JSONObject.toJSONString(applyStore);
		System.out.println(jsonString);
	}

	/**
	 * (用一句话描述方法的主要功能)
	 */

	private static void testBigDecimal() {
		BigDecimal bigDecimal1 = new BigDecimal(0);
		BigDecimal bigDecimal2 = new BigDecimal(2);
		BigDecimal add = bigDecimal1.add(bigDecimal2);
		System.out.println(add);

	}

	/**
	 * (用一句话描述方法的主要功能)
	 */

	private static void testTime() {
		String visitTime = "2015-12-01 09:46:26";
		Date transStringToDate = DateUtils.transStringToDate(visitTime, "yyyy-MM-dd ");
		String string = transStringToDate.toString();
		System.out.println(string);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String format2 = format.format(transStringToDate);
		System.out.println(format2);
		String[] split = visitTime.split(" ");
		System.out.println(split[0] + "11");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("a", "a");
		param.put("a", "b");
		Date date2 = new Date();
		date2.toString();
		System.out.println(date2.getTime());
		System.out.println(param.get("a"));
		long time = 1440432000000L;
		Date date = new Date(date2.getTime());
		String format3 = format.format(date);
		System.err.println("format3" + format3);
	}
}
