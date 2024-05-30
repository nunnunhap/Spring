package com.docmall.demo.util;

import java.io.File;
import java.nio.file.Files;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

// 교과서 같은 내용
// 여러 Controller에서 파일 업로드하는 기능을 사용할 것이니 이렇게 따로 빼서 재사용성을 높임.
@Component // 이 어노테이션 사용 시 Bean으로 동작하여 스프링에서 자동관리함.
public class FileUtils {

	public static ResponseEntity<byte[]> getFile(String uploadPath, String fileName) throws Exception {
		ResponseEntity<byte[]> entity = null;
		
		File file = new File(uploadPath, fileName);
		
		if(!file.exists()) {
			return entity; // entity가 null값을 가지고 있음.
		}
		
		HttpHeaders headers = new HttpHeaders(); // springframwork인 HttpHeaders
		// Files.probeContentType(file.toPath()) : MIME-TYPE 정보 예> image/jpeg
		headers.add("Content-Type", Files.probeContentType(file.toPath()));
		
		entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
		
		return entity;
	}

}