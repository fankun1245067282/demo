package com.spring.test.model;

public class XiaoXingXing implements Person{

	private String sex = "女";
	private String name = "小星星";
	@Override
	public void findLove() {
		System.out.println(""+name+" 性别："+sex+"  我的要求：");
		System.out.println("高富帅");
		System.out.println("身高180cm");
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
