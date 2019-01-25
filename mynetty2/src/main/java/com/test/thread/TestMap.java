package com.test.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestMap {
	public static void main(String[] args) {
		Double d = Double.parseDouble("10.12");
		System.out.println(d);
		
		Map<String,String> map = new ConcurrentHashMap<>();
		String str = map.putIfAbsent("a", "b");
		System.out.println("str:"+str);
		str = map.putIfAbsent("a", "c");
		System.out.println("str:"+str);
	}
}	