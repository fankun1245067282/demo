package com.fk.test;

import java.util.StringTokenizer;

import org.junit.Test;

public class TestStringtokenizer {
/*	��. ���캯����

	1. StringTokenizer(String str) ������һ����������str��StringTokenizer����javaĬ�ϵķָ����ǡ��ո񡱡����Ʊ��(��\t��)���������з�(��\n��)�������س���(��\r��)����
	2. StringTokenizer(String str, String delim) ������һ����������str��StringTokenizer���󣬲��ṩһ��ָ���ķָ�����
	3. StringTokenizer(String str, String delim, boolean returnDelims) ������һ����������str��StringTokenizer���󣬲��ṩһ��ָ���ķָ�����ͬʱ��ָ���Ƿ񷵻طָ�����
	2.  �������

	int     ����countTokens()������nextToken���������õĴ�����������ù��캯��1��2�����صľ��Ƿָ�������
	boolean   hasMoreElements()�������Ƿ��зָ�����
	boolean   hasMoreTokens()��ͬ��
	String����nextToken()�����شӵ�ǰλ�õ���һ���ָ������ַ�����
	Object      nextElement()�����ͬ�ϣ������������ص���Object������String
	String       nextToken(String delim)��ͬnextToken()����ָ���ķָ������ؽ��

	����*/
	@Test
	public void test1() {
		String s = new String("This is a test string");
		StringTokenizer st = new StringTokenizer(s);
		System.out.println( "Token Total: " + st.countTokens() );
//		while( st.hasMoreElements() ){
//		    System.out.println(st.nextToken());
//		}
		
		 System.out.println(st.nextToken());
		 System.out.println(st.nextToken());
	}
	
	//ע�⣬StringTokenizer�ķָ�������Ҫʹ��ת���ַ�
	@Test
	public void test2() {
//		StringTokenizer strToke = new StringTokenizer(str, ":,|");// Ĭ�ϲ���ӡ�ָ���
//		 StringTokenizer strToke=new StringTokenizer(str,":,|",true);//��ӡ�ָ���
		String str = "100|66,55:200|567,90:102|43,54";
		StringTokenizer strToke=new StringTokenizer(str,":,|",false);//����ӡ�ָ���
		while(strToke.hasMoreTokens()){
		    System.out.println(strToke.nextToken());
		}
	}
//	3.��split������
//
//	String.Split����ʹ��������ʽ����StringTokenizer��ֻ��ʹ�����ַ��ѵ��ַ���
//	�������������ʽ��StringTokenizerҲ����ʹ��������ʽ����StringTokenizer�ڽ�ȡ�ַ����е�Ч����ߡ�
}
