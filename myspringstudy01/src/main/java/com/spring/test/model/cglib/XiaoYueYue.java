package com.spring.test.model.cglib;

/**
 * 被代理对象（小月月找对象）
 */
public class XiaoYueYue {

		String name = "小月月";
		public void findLove() {
			System.out.println(this.name+"要找到一个高富帅");
		}
}
