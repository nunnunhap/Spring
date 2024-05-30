package com.docmall.demo.controller;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.docmall.demo.domain.BoardVO;
import com.docmall.demo.dto.Criteria;
import com.docmall.demo.dto.PageDTO;
import com.docmall.demo.service.BoardService;
import com.docmall.demo.util.FileUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

// 클라이언트로부터 요청받는 클래스
@Slf4j // log객체 지원
@RequestMapping("/board/*") // jsp파일을 관리하기 위한 board폴더 생성
@Controller
public class BoardController {
	// 로그객체
//	private static final log log = logFactory.getlog(BoardController.class);
	
	// CKeditor 파일 업로드 경로
	@Value("${file.ckdir}")
	private String uploadCkPath;
	
	
	// 의존성 주입
	@Autowired
	private BoardService boardService; // 부모 인터페이스 타입으로 작업
	
	// 글쓰기 폼 // Service -> Mapper 작업이 필요없음.
	@GetMapping("write")
	public void write() {
		log.info("called write ...");
	}
	
	// 글쓰기 저장
	@PostMapping("write")
	public String write(BoardVO vo) {
		log.info("게시판 입력데이터 : " + vo);
		
		// db 저장 작업
		boardService.write(vo);
		
		return "redirect:/board/list";
	}
	
	// CKeditor 업로드
	// MultipartFile upload의 upload이름은 '파일선택'의 이름이 이것이기 때문.
	// HttpServletRequest request : 클라이언트의 요청정보를 가지고 응답하는 객체
	// HttpServletResponse response : 서버에서 클라이언트에게 보낼 정보를 응답하는 객체
	@PostMapping("imageupload") // upload 주소
	public void imageupload(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) {
		OutputStream out = null; // 클라이언트에 내용을 내보이고 싶을 때 출력스트림 사용. 이건 바이트기반 스트림.
		// 자바의 입출력 스트림에 대한 이론적 공부를 다시 할 필요성이 있음.
		PrintWriter printWriter = null;// 서버에서 클라이언트에게 응답정보를 보낼 때 사용(업로드한 이미지 정볼르 브라우저
		
		try {
			// 1) CKeditor를 통한 파일 업로드 처리
			String fileName = upload.getOriginalFilename(); // 업로드할 클라이언트 파일 이름
			byte[] bytes = upload.getBytes(); // 업로드할 생각의 바이너리배열
			
			// "C:\\Dev\\upload\\ckeditor\\" + "abc.gif" 이걸 위해서 \\를 마지막에 넣어주었음.
			String ckUploadPath = uploadCkPath + fileName;
			
		// "C:\\Dev\\upload\\ckeditor\\abc.gif" 생성(크기는 0byte). 나중에 사용자가 첨부한 이미지파일의 바이트배열을 안에 채워주는 것임.
			out = new FileOutputStream(ckUploadPath);
			
			out.write(bytes); // Stream의 공간에 업로드할 파일의 바이트를 채운 상태.
			// flush()에 의하여 크기가 채워진 정상적인 파일로 인식
			out.flush();
			
			// 2) 업로드한 이미지파일에 대한 정보를 클라이언트에게 보내는 작업
			
			printWriter = response.getWriter();
			
			String fileUrl = "/board/display/" + fileName; // 매핑주소/이미지파일명
			// String fileUrl = fileName;
			
			//CKeditor 4.12 에서 파일 정볼를 다음과 같이 구성하여 보내야 함.
			// js의 json문법구조로 발송해달라 함.
			// {"filename" + "abc.gif", "uploaded":1, "url": "/ckupload/abc.gif"}
			// {"filename" + 변수, "uploaded":1, "url": 변수}
			printWriter.println("{\"filename\" :\"" + fileName + "\", \"uploaded\":1, \"url\":\"" + fileUrl
					+ "\"}"); // 이스케이프 시퀀스(\) 사용
			printWriter.flush(); // 클라이언트로 보내줌.
			
		} catch (Exception ex) {
			ex.printStackTrace();			
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
			if(printWriter != null) printWriter.close();
		}
	}
	
	@GetMapping("display/{fileName}") // 사실 static은 class.~~으로 접근하는게 좋음.
	public ResponseEntity<byte[]> getFile(@PathVariable("fileName") String fileName) {
		
		log.info("파일 이미지 : " + fileName);
		
		ResponseEntity<byte[]> entity = null;
		
		try {
			entity = FileUtils.getFile(uploadCkPath, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	
	/*
	// 글 목록 저장
	@GetMapping("list")
	public void list(Model model) { // 어떤 데이터 소스(list)를 jsp에서 사용할 경우에는 Model 파라미터를 써줌.
		List<BoardVO> list = boardService.list();
		model.addAttribute("list", list); // (jsp에서 사용할 이름, 데이터)
		
		log.info("리스트");
	}
	*/
	
	@GetMapping("list")
	// 자바에선 Criteria객체와 Model객체를 생성해서 대입해줘야 하지만 spring에선 알아서 해줌.
	// 메서드의 파라미터로 Criteria cri로 사용한 이유? 클라이언트로부터 pageNum, amount, type, keyword 필드의 값을 받기 위한 목적.
	// 처음에는 클라이언트로부터 받은 데이터가 없어서 기본 생성자가 호추로디어 필드값이 사용됨.
	public void list(Criteria cri, Model model) {
		List<BoardVO> list = boardService.listWithPaging(cri); // mapper.xml의 sql에 까지 
		log.info("게시물 목록 데이터 : " + list);
		// 1) 게시물 목록 10건
		model.addAttribute("list", list); // 두 파라미터 값은 이름이 같지 않아도 됨.
		int total = boardService.getTotalCount(cri);
		PageDTO pageDTO = new PageDTO(cri, total);
		log.info("페이징 기능 데이터 : " + pageDTO);
		// 2) 페이징 기능. 1 2 3 4 5 6 7 8 9 10 [next]
		model.addAttribute("pageMaker", pageDTO);
	}
	
	// 게시물 조회/ 게시물 수정
	// 클라이언트 측에서 get, modify 둘 중 아무거나 요청해도 이 get으로 받아짐. 근데 void형식이라서 get으로 요청받으면 get, modify면 modify로 작동
	@GetMapping(value = {"get", "modify"})
	// jsp에 보여주기 위해 Model파라미터 추가함. Model은 항상 맨 뒤에 추가해줌.
	// 그래서 Select문이 들어간 쿼리가 있으면 controller에선 Model이 들어가게 되는 것이지.
	public void get(Long bno, @ModelAttribute("cri")Criteria cri, Model model) {
		// 이렇게 log를 찍을 필욘 없지만 처음 하면 이렇게 일일히 확인해줘야 함.
		log.info("게시물 번호 : " + bno);
		
		// 1) 조회 수 증가
		// 2) 증가된 조회 수가 적용된 게시물 읽어오기
		BoardVO boardVo = boardService.get(bno);
		model.addAttribute("boardVO", boardVo);
		
		// Criteria는 게시물 조회 후 다시 기존의 list로 돌아가기 위하여 매개변수로 받음. @ModelAttribute("a")는 jsp에서 사용할 이름임.
	}
	
	// 게시글 수정하기
	@PostMapping("modify")
	public String modify(BoardVO vo, Criteria cri) {
		log.info("수정 데이터 : " + vo);
		boardService.modify(vo);
		return "redirect:/board/list" + cri.getListLink();
	}
	
	// 게시글 삭제하기
	@GetMapping("delete")
	public String delete(Long bno, Criteria cri) {
		log.info("삭제 글 번호 : " + bno);
		boardService.delete(bno);
		
		/* 이렇게 작업 안 하고 Criteria에서 작업해서 데려오는 것도 방법일듯?
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		*/
		
		// 위의 rttr 작업을 해주면 아래 주소("redirect:/board/list")는 오른쪽과 같이 변함. "redirect:/board/list?pageNum=2&amount=10&type=T&keyword=사과"
		return "redirect:/board/list" + cri.getListLink();
	}
}
