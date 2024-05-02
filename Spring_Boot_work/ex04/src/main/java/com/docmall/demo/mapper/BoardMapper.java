package com.docmall.demo.mapper;

import com.docmall.demo.domain.BoardVO;

public interface BoardMapper {
	// 글쓰기 저장
	void write(BoardVO vo);
}
