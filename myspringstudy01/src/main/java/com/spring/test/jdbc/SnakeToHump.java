package com.spring.test.jdbc;
/**
 * 蛇形转换为驼峰
 * 规则只有满足（.*[^_]_[a-z].*）才会被转换，否则不会转换
 * （.*__b）不会转换 （_bc）不会转换
 * mysql字段 命名规范
 * 采用26个英文字母(区分大小写)和0-9这十个自然数,加上下划线'_'、'$'组成
 * @author Administrator
 *
 */
public class SnakeToHump {
	
	public static String covert(String val) {
		if(val==null) {
			return "";
		}
		val = val.toLowerCase();
		char[] valarr = val.toCharArray();
		StringBuilder sb = new StringBuilder();
//		a-z:97-122
//		A-Z:65-90
		for (int i = 0; i < valarr.length; i++) {
			//"-"不是第一个，也不是最后一个
//			System.out.println('_'==valarr[i] );
			if('_'==valarr[i] 
				&& i!=0
				&& '_'!=valarr[i-1] 
				&& i!=valarr.length-1
				&& valarr[i+1]>96
				&& valarr[i+1]<123) {
				//a转换为大写 (char)(a-26)
				sb.append((char)(valarr[i+1]-32));
				i++;
			}else {
				sb.append(valarr[i]);
			}
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		System.out.println("a-z:"+(int)'a'+"-"+(int)'z');
		System.out.println("A-Z:"+(int)'A'+"-"+(int)'Z');
		//System.out.println('_'=='_');
		String str = "w23$243WexUIIdd";
		System.out.println(str.toLowerCase());
		String str1 = "my$_name1_is_fankun__Haha_6ya_$yuan_w";
		System.out.println("oldstr:"+str1+"  \ncovert:" +covert(str1));
	}
}
