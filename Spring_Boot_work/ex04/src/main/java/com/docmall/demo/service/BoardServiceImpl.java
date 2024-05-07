package com.docmall.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.docmall.demo.domain.BoardVO;
import com.docmall.demo.dto.Criteria;
import com.docmall.demo.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	// 의존성 주입
	@Autowired
	private BoardMapper boardMapper;

	@Override
	public void write(BoardVO vo) {
		boardMapper.write(vo);
	}

	@Override
	public List<BoardVO> list() {
		return boardMapper.list();
	}

	@Override
	public BoardVO get(Long bno) {
		// 조회 수 증가
		boardMapper.readCount(bno);
		
		return boardMapper.get(bno);
	}

	@Override
	public void modify(BoardVO vo) {
		boardMapper.modify(vo);
	}

	@Override
	public void delete(Long bno) {
		boardMapper.delete(bno);
	}

	@Override
	public List<BoardVO> listWithPaging(Criteria cri) {
		return boardMapper.listWithPaging(cri);
	}

	@Override
	public int getTotalCount() {
		return boardMapper.getTotalCount();
	}
}
