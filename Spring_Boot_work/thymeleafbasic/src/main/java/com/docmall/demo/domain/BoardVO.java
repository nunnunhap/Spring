package com.docmall.demo.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardVO {
	
	private Integer idx; // 오라클의 시퀀스로 처리함.
	
	@NotBlank(message = "제목은 필수항목입니다.") // pom.xml에 추가한 validation에서 지원해주는 어노테이션
	@Size(max = 200)
	private String title;

	@NotBlank(message = "저자는 필수항목입니다.") // null, 빈 문자열(""), 공백(" ") 허용안함.
	@Size(max = 30)
	private String author;

	@NotBlank(message = "내용은 필수항목입니다.")
	private String content;
}
