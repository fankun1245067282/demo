package com.fk.test;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class TestUnicode {
	public static void main(String[] args) {
		String str = "\u1F511";
		String  s  = "\uD83D\uDD11"; //字符'🔑'
		System.out.println(str); 
		//1101 1011 1111 1111 
	}

	@Test
	public void printCharacterCode() throws UnsupportedEncodingException {
		String s = "\uD83D\uDD11"; // 字符'🔑'
//		String s = "\uAF511";
		System.out.println("UTF8:" + bytesToBits(s.getBytes("utf-8")));
		System.out.println("UTF16:" + bytesToBits(s.getBytes("utf-16")));
		System.out.println("UTF32:" + bytesToBits(s.getBytes("utf-32")));
	}

	public static String byteToBit(byte b) {
		return "" + ((b >> 7) & 0x1) + ((b >> 6) & 0x1) + ((b >> 5) & 0x1) 
				+ ((b >> 4) & 0x1) + ((b >> 3) & 0x1)
				+ ((b >> 2) & 0x1) + ((b >> 1) & 0x1)
				+ ((b >> 0) & 0x1);
	}

	public static String bytesToBits(byte[] bytes) {
		String s = "";
		for (byte b : bytes) {
			s += byteToBit(b) + " ";
//			s += (byte)b + " ";
		}
		return s;
	}
}
