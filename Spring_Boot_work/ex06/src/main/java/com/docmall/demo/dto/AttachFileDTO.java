package com.docmall.demo.dto;

import lombok.Data;

@Data // @Getter @Setter @ToString @HashCode 등등 총합적으로 제작함.
public class AttachFileDTO {
	
	private String uuid; // 중복되지 않는 파일명
	private String uploadPath; // 날짜를 이용한 업로드 폴더명
	private String fileName; // 클라이언트에서 보낸 파일명
	private boolean image; // 이미지 파일여부. true는 이미지파일, false는 일반파일
	
}
