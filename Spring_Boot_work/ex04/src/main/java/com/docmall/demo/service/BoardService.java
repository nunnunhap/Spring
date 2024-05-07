package com.docmall.demo.service;

import java.util.List;

import com.docmall.demo.domain.BoardVO;
import com.docmall.demo.dto.Criteria;

public interface BoardService {
	
	// 글쓰기 저장
	void write(BoardVO vo);
	
	// 글 목록
	List<BoardVO> list();
	List<BoardVO> listWithPaging(Criteria cri);
	
	// 테이블의 총 데이터 개수
	int getTotalCount();
	
	// 게시물 조회
	BoardVO get(Long bno);
	
	// 게시물 수정
	void modify(BoardVO vo);
	
	// 게시글 삭제
	void delete(Long bno);
}
