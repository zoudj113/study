package com.onemore.controller;

import java.util.HashMap;

public class Test {
	public static void main(String[] args) {
		HashMap<String,Long> map = new HashMap<String,Long>();
		map.put("CUSTOMER_SEGMENT_ID", 100000000001L);
		long l = Long.parseLong(map.get("CUSTOMER_SEGMENT_ID").toString());
		System.out.println(l);
//		long segId =  ((Long)map.get("CUSTOMER_SEGMENT_ID")).longValue();
	}
}
