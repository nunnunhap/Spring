package com.docmall.basic.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docmall.basic.common.dto.Criteria;
import com.docmall.basic.common.dto.PageDTO;
import com.docmall.basic.user.UserVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// pro_detail.html 내 상품후기 처리
@RestController // 이 안의 모든 메서드는 ajax로 요청받음.
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/review/*")
public class ReviewController {
	
	private final ReviewService reviewService;
	
	
	// 리뷰목록과 페이징   rest api개발방법론   /revlist/상품코드/페이지번호  매핑주소의 파트 부분을 매개변수로 사용하고자 할 경우
	// 전통적인 주소 : /revlist?pro_num=10&page=1
	@GetMapping("/revlist/{pro_num}/{page}")
	public ResponseEntity<Map<String, Object>> revlist(
			@PathVariable("pro_num") int pro_num, @PathVariable("page") int page) throws Exception {
		ResponseEntity<Map<String, Object>> entity = null;
		
		Map<String, Object> map = new HashMap<>();
		
		// 1) 후기 목록
		// 이번에는 Criteria를 파라미터에 안 쓰고 메서드 안에서 클래스 안의 pageNum 하나만 사용하겠음.
		Criteria cri = new Criteria();
		cri.setAmount(5);
		cri.setPageNum(page);
		
		List<ReviewVo> revlist = reviewService.rev_list(pro_num, cri);
		
		// 2) 페이징 정보
		int revcount = reviewService.getCountReviewByPro_num(pro_num);
		PageDTO pageMaker = new PageDTO(cri, revcount);
		
		map.put("revlist", revlist);
		map.put("pageMaker", pageMaker);
		
		entity = new ResponseEntity<Map<String,Object>> (map, HttpStatus.OK);
		
		return entity;
	}
	
	// 상품 후기 저장
	// consumes : 클라이언트에서 넘어오는 값의 포맷(mime), 이번 건은 json으로만 받겠단 뜻임.
	// produces: 스프링에서 클라이언트로 보내는 값. 굳이 안 잡아도 되긴 함.
	// 클라이언트에서 json포맷으로 넘어오는 경우면 바로 ReviewVo vo로 들어오질 못함. 그래서 @RequestBody 넣어주어야 함. json으로 넘어온
	// 데이터가 @RequestBody로 들어와 ReviewVo vo에 저장됨. 이 사이엔 json 관련 라이브러리인 jackson-databind 라이브러리가 관련되어 있음.
	@PostMapping(value = "/review_save", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> review_save(@RequestBody ReviewVo vo, HttpSession session) throws Exception {
		log.info("상품후기 데이터 : " + vo);
		
		String mbsp_id = ((UserVo) session.getAttribute("login_status")).getMbsp_id();
		vo.setMbsp_id(mbsp_id);
		
		reviewService.review_save(vo);
		
		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<String> ("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 상품후기 삭제
	@DeleteMapping("/review_delete/{rev_code}")
	public ResponseEntity<String> review_delete(@PathVariable("rev_code") Long rev_code) throws Exception {
		log.info("후기코드 : " + rev_code);
		reviewService.review_delete(rev_code);
		
		ResponseEntity<String> entity = null;
		entity = new ResponseEntity<String> ("success", HttpStatus.OK);
		
		return entity;
	}
	
	
	
	
	
}
