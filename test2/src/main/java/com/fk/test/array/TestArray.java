package com.fk.test.array;

import java.util.Arrays;

public class TestArray {
	
	public static void main(String[] args) {
		String[] arr1 = new String[] {new String("1"),new String("2"),new String("3")};
		String[] arr2 = new String[3];
		arr2 = Arrays.copyOf(arr1, 3);
		System.out.println(Arrays.toString(arr1));
		System.out.println(Arrays.toString(arr2));
		System.out.println(arr1[2]==arr2[2]);//浅层拷贝
	}
}
