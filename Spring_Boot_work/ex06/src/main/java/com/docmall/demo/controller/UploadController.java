package com.docmall.demo.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.docmall.demo.service.UploadService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/upload/*")
public class UploadController {
	
	// 주입작업
	private final UploadService uploadService;
	
	// properties에서 가지고 온 주소
	@Value("${org.docmall.upload.path}")
	private String uploadFolder;
	
	// 업로드 방식 <form> 태그를 이용한 방식
	@GetMapping("uploadForm")
	public void uploadForm() {
		
	}
	
	// 1) <form>을 이용한 업로드 방식
	@PostMapping("uploadFormAction")
	// 파일이 multiple때문에 복수로 넘어오기 때문에 배열로 적어줌.
	// com.docmall.demo.config 패키지의 MultipartResolver클래스 안에 multipartResolver bean이 업로드파일을 참조하여
	// MultipartFile[] uploadFile이 파라미터로 사용하게 해줌.
	public void uploadFormPost(MultipartFile[] uploadFile) {
		for(MultipartFile multipartFile : uploadFile) {
			log.info("===============================");
			log.info("파일 이름 : " + multipartFile.getOriginalFilename());
			log.info("파일 크기 : " + multipartFile.getSize());
			
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			
			try {
				multipartFile.transferTo(saveFile);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	// 2) ajax를 이용한 업로드 방식
	@GetMapping("uploadAjax")
	public void uploadAjax() {
		
	}
	
	
}
