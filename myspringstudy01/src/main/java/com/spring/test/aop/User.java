package com.spring.test.aop;

public class User {
	private String cardId;
	private String userName;
	private int age;
	
	public User(String cardId, String userName, int age) {
		super();
		this.cardId = cardId;
		this.userName = userName;
		this.age = age;
	}
	public User() {}
	
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "User [cardId=" + cardId + ", userName=" + userName + ", age=" + age + "]";
	}
	
}
