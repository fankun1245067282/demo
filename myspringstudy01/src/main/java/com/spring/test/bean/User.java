package com.spring.test.bean;

public class User {
	private int id = 1;
	private String name ="fankun";
	public User() {
		System.out.println("=== === === === === ===");
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
	
}
