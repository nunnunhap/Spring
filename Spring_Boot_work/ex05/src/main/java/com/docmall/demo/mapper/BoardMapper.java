package com.docmall.demo.mapper;

import java.util.List;

import com.docmall.demo.domain.BoardVO;
import com.docmall.demo.dto.Criteria;

public interface BoardMapper {
	// 글쓰기 저장
	void write(BoardVO vo);
	
	// 글 목록
	List<BoardVO> list();
	
	// 검색조건이 추가되는 목록
	List<BoardVO> listWithPaging(Criteria cri);
	
	// 검색조건이 추가된 총 데이터 개수(그래서 Criteria를 가지고 들어옴)
	int getTotalCount(Criteria cri);
	
	// 게시물 조회
	BoardVO get(Long bno);
	
	// 조회수 증가
	void readCount(Long bno);
	
	// 게시물 수정
	void modify(BoardVO vo);
	
	// 게시글 삭제
	void delete(Long bno);
}
