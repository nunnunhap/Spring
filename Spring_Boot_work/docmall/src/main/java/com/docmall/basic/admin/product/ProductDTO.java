package com.docmall.basic.admin.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ProductDTO {
	private Integer pro_num;
	private int pro_price;
	private String pro_buy;
}
