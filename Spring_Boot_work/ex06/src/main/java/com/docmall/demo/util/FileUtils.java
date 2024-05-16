package com.docmall.demo.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

// 교과서 같은 내용
// 여러 Controller에서 파일 업로드하는 기능을 사용할 것이니 이렇게 따로 빼서 재사용성을 높임.
public class FileUtils {
	
	// pds폴더 안에 날짜별로 폴더를 정리할 수 있도록
	// 기능? 현재 폴더를 운영체제에 맞게 문자열로 반환하는 기능
	public static String getDataFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(); // 오늘 날짜 정보
		
		String str = sdf.format(date); // "2024-05-16" 폴더명 문자열
		
		// File.seperator : 이 코드를 실행하는 운영체제별로 파일의 경로구분자를 기턴.
		return str.replace("-", File.separator);
		/*
		 * 유닉스, 맥, 리눅스 구분자 : /	예> "2024-05-16" => "2024/05/16"
		 * 윈도우즈 구분자 : \	예> "2024-05-16" => "2024\05\16"
		 */
		// charsequence는 string보다 ...
		// 직접적으로 -를 /라고 하라고 하지 말고 이런 명령어를 사용해서 한 번에 바꿔줄 것.
	}
	
	// 기능? 파일 업로드
	/*
	 * String uploadFolder : 업로드 폴더명. C:\\Dev\\upload\\pds
	 * String dateFolder : 날짜 폴더명 "2024\\05\\16"
	 * MultipartFile uploadFile : 클라이언트에서 전송한 파일 
	 */
	public static String uploadFile(String uploadFolder, String dateFolder, MultipartFile uploadFile) {
		
		String realUploadFileName = "";
		
		// File클래스 : JDK 제공. 파일과 폴더관련 기능을 제공
		/* File file = new File(파일 또는 폴더정보 구성); 아래의 셋 중 하나의 목적일 때 File클래스 생성 file.명령어(속성과 메서드)
		 * 파일 또는 폴더 존재여부 확인
		 * 존재하지 않으면 파일 또는 폴더 생성
		 * 존재하면 파일 또는 폴더 속성 확인
		 */
		File file = new File(uploadFolder, dateFolder); // 예> "C:\\Dev\\upload\\pds" "2024\\05\\16"
		
		// 2024\05\16 폴더가 존재하지 않는다면 해당 폴더 생성
		if(file.exists() == false) {
			file.mkdirs();
		}
		
		// 클라이언트에서 보낸 파일명 // abc.png
		String clientFileName = uploadFile.getOriginalFilename();
		
		// UUID : 범용 고유 식별자(universally unique identifier)로써, 2f48f241-9d64-4d16-bf56-70b9d4e0e79a 이와같은 형태로 고유한 값을 생성
		UUID uuid = UUID.randomUUID();
	
		realUploadFileName = uuid.toString() + "_" + clientFileName;
		
		// 예외처리작업
		try {
			File saveFile = new File(file, realUploadFileName);
			uploadFile.transferTo(saveFile); // 파일복사(원본)
			
			// Thumbnail 작업 (원본파일에서 해상도크기를 줄여, 썸네일 이미지 생성, 파일이 이미지가 아니면 이 작업이 진행될 수 없음)
			if(checkImageType(saveFile)) {
				// Thumbnail 파일명 : s_2f48f241-9d64-4d16-bf56-70b9d4e0e79a_abc.png
				File thumbnailFile = new File(file, "s_" + realUploadFileName);
				
				BufferedImage bo_img = ImageIO.read(saveFile);
				double ratio = 3;
				int width = (int) (bo_img.getWidth() / ratio);
				int height = (int) (bo_img.getHeight() / ratio);
				
				Thumbnails.of(saveFile)
						.size(width, height)
						.toFile(thumbnailFile);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return realUploadFileName;
	}

	// 기능? 업로드 파일의 MIME타입 확인. 즉, 이미지파일 또는 일반파일 여부를 확인
	private static boolean checkImageType(File saveFile) {
		boolean isImageType = false;
		
		try {
			// MIME : text/html, text/plain, image/jpeg, ...
			// 클라이언트에서 전송한 파일의 MIME 정보 추출
			String contentType = Files.probeContentType(saveFile.toPath());
			
			// contentType의 변수의 내용이 image라는 문자열로 시작하는가?(boolean)
			isImageType = contentType.startsWith("image");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return isImageType;
	}
	
	// 기능? 이미지 파일을 웹 브라우저 화면에 보이는 작업
	// <img src="abc.gif"> <img src="매핑주소">
	// 모든 파일은 byte로 구성되어 있음. 그래서 리턴타입이 byte[]인 것.
	/*
	 * String uploadPath : 서버 업로드 폴더 예> C:/Dev/upload/pds
	 * String fileName : 이미지 파일명(날짜폴더명 포함)
	 */
	public static ResponseEntity<byte[]> getFile(String uploadPath, String fileName) throws Exception {
		ResponseEntity<byte[]> entity = null;
		
		File file = new File(uploadPath, fileName);
		
		if(!file.exists()) {
			return entity;
		}
		
		HttpHeaders headers = new HttpHeaders(); // springframwork인 HttpHeaders
		// Files.probeContentType(file.toPath()) : MIME-TYPE 정보 예> image/jpeg
		headers.add("Content-Type", Files.probeContentType(file.toPath()));
		
		entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
		
		return entity;
	}
	
	// 기능? 이미지 파일 삭제(우리는 이미지파일만 쓸 것이긴 하나 일반파일도 상관없음)
	/*
	 * String uploadPath : 서버 업로드 폴더
	 * String folderName : 날짜 폴더명
	 * String fileName : 파일명
	 */
	public static void delete(String uploadPath, String folderName, String fileName) {
		// 1) 원본파일
		// 리눅스면 /로 바꿔주고 윈도우면 \로 바꿔주는 것.
		File file1 = new File((uploadPath + folderName + "\\" + fileName).replace('\\', File.separatorChar));
		if(file1.exists()) file1.delete();
		
		// 2) thumbnail 파일
		File file2 = new File((uploadPath + folderName + "\\" + "_s" + fileName).replace('\\', File.separatorChar));
		if(file2.exists()) file2.delete();
	}
	
	
}