package com.docmall.demo.kakaopay;

import lombok.Data;

@Data
public class Amount {
	
	public int total; // 전체 결제 금액
	public int tax_free; // 비과세 금액
	public int vat; // 부가세 금액
	public int point; // 사용할 포인트 금액
	public int discount; // 할인 금액
	
}
