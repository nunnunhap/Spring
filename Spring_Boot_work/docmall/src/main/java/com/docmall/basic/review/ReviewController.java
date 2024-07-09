package com.docmall.basic.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docmall.basic.common.dto.Criteria;
import com.docmall.basic.common.dto.PageDTO;

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
	
	
}
