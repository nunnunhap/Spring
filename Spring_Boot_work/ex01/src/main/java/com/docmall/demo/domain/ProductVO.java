package com.docmall.demo.domain;

// Oracle DB에 Product 테이블을 생성하고 구조를 정의한 클래스
public class ProductVO {
	private String name;
	private double price;
	
	// 실제 실무에선 생성자를 만들어 쓰진 않지만 db가 준비가 안되어 있어서 만들어서 쓸 예정.
	public ProductVO(String name, double price) {
		super(); // Object클래스를 가리킴. 컴파일 과정에서 자동으로 생성해줌.
		this.name = name;
		this.price = price;
	}
	
	// getter, setter 메서드
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProductVO [name=" + name + ", price=" + price + "]";
	}
}
