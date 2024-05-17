package com.docmall.demo.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.docmall.demo.dto.AttachFileDTO;
import com.docmall.demo.service.UploadService;
import com.docmall.demo.util.FileUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/upload/*")
public class UploadController {
	
	// 주입작업
	private final UploadService uploadService;
	
	private FileUtils fileUtils;
	
	// properties에서 가지고 온 주소
	// 각종 설정과 관련된 정보는 외부에서 가져와서 써야지 성능이 좋음. 변경이 되었을 때도 내부적으로 클래스변환되거나 하는 등의 메모리 소모를 막을 수 있음.
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
			
			// new File(파일 또는 폴더경로)
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			
			try { // multipartFile에는 업로드한 파일을 가진 객체. transferTo()에 multipartFile객체에 썼다.
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
	
	// ajax요청으로 파일 업로드 작업을 진행하고, 업로드된 파일 목록 정보를 return해주는 기능 보유.
	// 지금 이 Controller는 @RestController가 아닌 @Controller니 ajax요청을 받는 매핑주소는 @ResponseBody 어노테이션 필요함.
	// 이 안의 모든 매핑주소가 ajax방식이면 @RestController를 사용하는 것임. 여긴 다는 아니니까 일반적인 @Controller 사용함.
	@ResponseBody
	@PostMapping(value = "uploadAjaxAction", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	// 리턴값은 List<AttachFileDTO> -> JSON으로 변환되어 클라이언트로 전송되어짐. 연관된 라이브러리는 jackson-databind임.
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxAction(MultipartFile[] uploadFile) {
		ResponseEntity<List<AttachFileDTO>> entity = null;
		// 사실상 List<AttachFileDTO>가 실제 리턴타입이고, 보내는 정보가 정상적인 작업에 의하여 보낸다는 상태코드, MIME-TYPE을
		// 부가적으로 보내기 위하여 ResponseEntity클래스로 명시적으로 포장한 것이나 다름없음. 그래서 이 List<>를 따로 작업해줌.
		List<AttachFileDTO> list = new ArrayList<>();
		
		String uploadDateFolder = fileUtils.getDataFolder();
		
		for(MultipartFile multipartFile : uploadFile) {
			AttachFileDTO attachFileDTO = new AttachFileDTO();
			
			// 1) 클라이언트 파일 이름
			String clientFileName = multipartFile.getOriginalFilename();
			attachFileDTO.setFileName(clientFileName);
			
			// 2) 실제 업로드한 파일명
			attachFileDTO.setUuid(fileUtils.uploadFile(uploadFolder, uploadDateFolder, multipartFile));
			
			// 3) 날짜 폴더명
			attachFileDTO.setUploadPath(uploadDateFolder);
			
			File saveFile = new File(uploadFolder, clientFileName);
			
			if(fileUtils.checkImageType(saveFile)) {
				// 4) 이미지파일 여부
				attachFileDTO.setImage(true);
			}
			list.add(attachFileDTO);
		}
		// jsp의 result에 JSON으로 변환된 list가 들어감.
		entity = new ResponseEntity<List<AttachFileDTO>>(list, HttpStatus.OK);
		
		return entity;
	}
	
	// <img src="매핑주소">
	@GetMapping("display") // 사실 static은 class.~~으로 접근하는게 좋음.
	public ResponseEntity<byte[]> getFile(String fileName) {
		
		ResponseEntity<byte[]> entity = null;
		
		try {
			entity = fileUtils.getFile(uploadFolder, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	// 파일삭제
	@PostMapping(value = "deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type) {
		
		log.info("fileName : " + fileName);
		log.info("type : " + type);
		
		ResponseEntity<String> entity = null;
		
		fileUtils.delete(uploadFolder, fileName, type);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		return entity;
	}
	
	
}
