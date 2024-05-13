package com.docmall.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.docmall.demo.domain.ReplyVO;
import com.docmall.demo.dto.Criteria;
import com.docmall.demo.mapper.ReplyMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final 필드를 이용하여 생성자를 생성 그리고 그 생성자가 주입받는다.
@Service
public class ReplyServiceImpl implements ReplyService {
	private final ReplyMapper replyMapper;
	
	/* @RequiredArgsConstructor 어노테이션을 붙이면 이렇게 작업해줘야 함.
	public ReplyServiceImpl(ReplyMapper replayMapper) {
		this.replyMapper = replyMapper;
	}*/
	
	@Override // 댓글 조회 기능
	public List<ReplyVO> getListPaging(Criteria cri, Long bno) {
		return replyMapper.getListPaging(cri, bno);
	}

	@Override
	public int getCountByBno(Long bno) {
		return replyMapper.getCountByBno(bno);
	}
	
	@Override
	public void insert(ReplyVO vo) {
		replyMapper.insert(vo);
	}
	
	@Override
	public void update(ReplyVO vo) {
		replyMapper.update(vo);
	}
	
}
