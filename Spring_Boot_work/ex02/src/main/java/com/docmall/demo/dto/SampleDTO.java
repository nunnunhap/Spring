package com.docmall.demo.dto;

public class SampleDTO {
	private String name;
	private int age;
	
	public SampleDTO() {
		System.out.println("SampleDTO 생성자 호출");
	}
	
	public String getName() {
		System.out.println("getName 호출");
		return name;
	}
	public void setName(String name) {
		System.out.println("setName 호출");
		this.name = name;
	}
	public int getAge() {
		System.out.println("getAge 호출");
		return age;
	}
	public void setAge(int age) {
		System.out.println("setAge 호출");
		this.age = age;
	}

	@Override
	public String toString() {
		return "SampleDTO [name=" + name + ", age=" + age + "]";
	}	
}
