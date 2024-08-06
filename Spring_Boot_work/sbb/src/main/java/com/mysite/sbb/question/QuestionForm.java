package com.mysite.sbb.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
	
	@NotEmpty(message = "제목은 필수항목입니다.") // Null 또는 빈 문자열 허용 안함.
	@Size(max = 200) // 최대길이 200 바이트를 넘으면 안됨.
	private String subject;
	
	@NotEmpty(message = "내용은 필수항목입니다.")
	private String content;
	
}
