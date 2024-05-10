package com.docmall.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.docmall.demo.domain.ReplyVO;
import com.docmall.demo.dto.Criteria;
import com.docmall.demo.dto.PageDTO;
import com.docmall.demo.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 댓글 기능과 관련된 매핑주소를 관리하는 클래스
// 게시판의 get.jsp에서 작업이 필요한 내용을 가지고 있는 것.
@Slf4j // log객체 지원
@RequiredArgsConstructor
@RestController
@RequestMapping("/replies/*")
public class ReplyController {
	private final ReplyService replyService;
	
	// 클라이언트의 요청주소 1) /replies/pages/100/1(Rest API기술이론에서 권장하는 주소형태. @RestController와 연관) 또는 2) /replies?pages=1&bno=100(쿼리스트링: 전통적인 주소형태)도 가능하긴 함. /를 권장
	// /replies/pages/100/1 주소(경로)에 존재하는 값을 사용할 때 구분되는 위치에 {이름}을 사용
	// 왜 리턴타입에 Map을 했는가? list컬렉션과 pageDTO 타입 둘 다 커버할 수 있는게 Object임. 그래서 Map 컬렉션 사용하는 것임. 서로 다른 데이터 타입을 모두 추가시킬 수 있음. 
	// produces는 생산. 즉, JSON이나 XML로. 근데 이렇게 중간줄 그어진 것이 depricated임.
	@GetMapping(value = "/pages/{bno}/{page}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<Map<String, Object>> getList(@PathVariable("bno") Long bno, @PathVariable("page") int page) {
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> map = new HashMap<>();

		// 1) 댓글 목록 작업
		Criteria cri = new Criteria(page, 5); // Criteria(1, 5)로 썼는데 1때문에 고정이 되어서 이걸 page로 변경해줌.
		List<ReplyVO> list = replyService.getListPaging(cri, bno);
		map.put("list", list);
		
		// 2) 페이징 작업
		int total = replyService.getCountByBno(bno);
		PageDTO pageDTO = new PageDTO(cri, total);
		map.put("pageMaker", pageDTO);
		
		// ResponseEntity는 클라이언트에게 보낼 때 JSON포맷으로 보내줌. 오른쪽은 http상태코드 값(여기선 200)도 보내줄 수 있음.
		entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		
		return entity;
	}
	
}
