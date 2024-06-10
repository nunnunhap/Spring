package com.docmall.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.docmall.demo.domain.BoardVO;
import com.docmall.demo.dto.Criteria;
import com.docmall.demo.dto.PageDTO;
import com.docmall.demo.service.BoardService;

import lombok.extern.slf4j.Slf4j;

// 클라이언트로부터 요청받는 클래스
@Slf4j // log객체 지원
@RequestMapping("/board/*")
@Controller
public class BoardController {
	// 로그객체
//	private static final log log = logFactory.getlog(BoardController.class);
	
	// 의존성 주입
	@Autowired
	private BoardService boardService; // 부모 인터페이스 타입으로 작업
	
	// 글쓰기 폼
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
