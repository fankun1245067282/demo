package com.fk.test.lambda;

import java.util.Comparator;

public class Test {
	public static void main(String[] args) {
		//两种方式都对
		Comparator<String> sortByName = (String s1, String s2) -> {
			return s1.compareTo(s2);
		};  
		
		Comparator<String> sortByName1 = (String s1, String s2) ->(s1.compareTo(s2));
	}
}
