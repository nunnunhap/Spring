package com.docmall.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
// Rest API 개발 방법
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
		/*
		 * 일단 여기 보면 @RestController가 작성되어 있음. 본래는 @Controller였는데 이 @RestController는 @Controller와 @ResponseBody가 결합되어 있다고 보면 됨.
		 * 그래서 사실상 지금 모든 메서드에 @ResponseBody가 붙여있는 것임. 그리고 Map<String, Object>인 이유는 List<>와 PageDTO를 같이 받기 위하여 Object를 쓴 것이고,
		 * String은 map.put("list", list)에서 "list", "pageMaker"가 String 타입이기 때문임.
		 * 이건 꼭 get.jsp랑 같이 봐야 함. 
		 * Spring Framework에서 제공하는 클래스 중 HttpEntity라는 클래스가 존재한다.
		 * 이것은 응답자체의 독립된 값이나 표현형태로, 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스이다.
		 * 따라서 HttpStatus, HttpHeaders, HttpBody를 포함함.
		 * RestTemplate(서버와 서버간 통신을 쉽게 해줌) 및 @Controller 메서드에 사용함.
		 * Response header : 웹서버가 웹브라우저에 응답하는 메시지, Reponse body : 데이터 값
		 */
	}
	
	// 댓글 저장
	// ajax의 경우 ResponseEntity를 사용함.
	// consumes 클라이언트에서 보내는 데이터는 json이어야 함.
	// produces {MediaType.TEXT_PLAIN_VALUE} : 서버에서 클라이언트로 보내는 응답데이터는 text임.
	@PostMapping(value = "/new", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	// @RequestBody : json데이터를 ReplyVO vo로 변환해주는 기능. jackson-databind라이브러리가 실제 json 관련 작업수행.
	public ResponseEntity<String> create(@RequestBody ReplyVO vo) {
		ResponseEntity<String> entity = null; // create() 메서드의 리턴타입이 ResponseEntity<String>이므로 이걸로 변수를 하나 만들어줌.
		
		log.info("댓글 데이터 : " + vo);
		
		replyService.insert(vo);
		
		// 정상적으로 실행되면 ajax 호출한 곳으로 이 success값이 넘어가게 됨.
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// 댓글 수정, put or patch
	@PutMapping(value = "/modify", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo) {
		ResponseEntity<String> entity = null; // create() 메서드의 리턴타입이 ResponseEntity<String>이므로 이걸로 변수를 하나 만들어줌.
		
		log.info("댓글수정 데이터 : " + vo);
		
		// 댓글 수정작업
		replyService.update(vo);
		
		// 정상적으로 실행되면 ajax 호출한 곳으로 이 success값이 넘어가게 됨.
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}	
	
	// 댓글 삭제, delete/ 그동안은 /delete?rno=댓글번호 였는데 REST API에서 요구하는대로 경로형태 주소 사용함.
	@DeleteMapping(value = "/delete/{rno}", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	// @DeleteMapping에 써있던 rno값을 @PathVariable()이 받아서 그것을 Integer rno에 대입시켜줌.
	public ResponseEntity<String> delete(@PathVariable("rno") Integer rno) {
		ResponseEntity<String> entity = null;
		
		// 댓글 삭제작업
		replyService.delete(rno);
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	
	
	
	
}
