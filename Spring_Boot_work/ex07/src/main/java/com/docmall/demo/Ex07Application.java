package com.docmall.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.docmall.demo.mapper"})
public class Ex07Application {

	public static void main(String[] args) {
		SpringApplication.run(Ex07Application.class, args);
	}

}
