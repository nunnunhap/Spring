package com.docmall.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.docmall.demo.domain.BoardVO;
import com.docmall.demo.service.BoardService;

@RequestMapping("/board/*")
@Controller
public class BoardController {
	// 로그객체
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	// 의존성 주입
	@Autowired
	private BoardService boardService; // 부모 인터페이스 타입으로 작업
	
	// 글쓰기 폼
	@GetMapping("write")
	public void write() {
		logger.info("called write ...");
	}
	
	// 글쓰기 저장
	@PostMapping("write")
	public String write(BoardVO vo) {
		logger.info("게시판 입력데이터 : " + vo);
		
		// db 저장 작업
		boardService.write(vo);
		
		return "redirect:/board/list";
	}
	
	// 글 목록 저장
	@GetMapping("list")
	public void list(Model model) { // 어떤 데이터 소스(list)를 jsp에서 사용할 경우에는 Model 파라미터를 써줌.
		List<BoardVO> list = boardService.list();
		model.addAttribute("list", list); // (jsp에서 사용할 이름, 데이터)
		
		logger.info("리스트");
	}
	
	// 게시물 조회/ 게시물 수정
	@GetMapping(value = {"get", "modify"})
	// jsp에 보여주기 위해 Model파라미터 추가함. Model은 항상 맨 뒤에 추가해줌.
	// 그래서 Select문이 들어간 쿼리가 있으면 controller에선 Model이 들어가게 되는 것이지.
	public void get(Long bno, Model model) {
		// 이렇게 logger를 찍을 필욘 없지만 처음 하면 이렇게 일일히 확인해줘야 함.
		logger.info("게시물 번호 : " + bno);
		
		// 1) 조회 수 증가
		
		
		// 2) 증가된 조회 수가 적용된 게시물 읽어오기
		BoardVO boardVo = boardService.get(bno);
		model.addAttribute("boardVO", boardVo);
	}
	
	// 게시글 수정하기
	@PostMapping("modify")
	public String modify(BoardVO vo) {
		logger.info("수정 데이터 : " + vo);
		boardService.modify(vo);
		return "redirect:/board/list";
	}
	
	// 게시글 삭제하기
	@GetMapping("delete")
	public String delete(Long bno) {
		logger.info("삭제 글 번호 : " + bno);
		boardService.delete(bno);
		return "redirect:/board/list";
	}
	
}
