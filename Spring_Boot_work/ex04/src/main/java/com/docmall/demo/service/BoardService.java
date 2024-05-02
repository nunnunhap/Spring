package com.docmall.demo.service;

import com.docmall.demo.domain.BoardVO;

public interface BoardService {
	
	// 글쓰기 저장
	void write(BoardVO vo);
}
