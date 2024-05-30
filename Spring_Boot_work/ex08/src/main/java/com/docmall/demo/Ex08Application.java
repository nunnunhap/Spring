package com.docmall.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.docmall.demo.mapper"})
public class Ex08Application {

	public static void main(String[] args) {
		SpringApplication.run(Ex08Application.class, args);
	}

}
