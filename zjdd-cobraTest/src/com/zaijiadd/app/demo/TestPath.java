package com.zaijiadd.app.demo;

import java.net.URL;

public class TestPath {

	public static void main(String[] args) {
		 testPath1();
	}

	private static void testPath1() {
		URL resource = TestPath.class.getResource("/");
		System.out.println(resource);
		System.out.println(TestPath.class.getResource("/biz/biz-context.xml"));
	}

}
