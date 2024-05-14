package com.docmall.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.demo.domain.ReplyVO;
import com.docmall.demo.dto.Criteria;

public interface ReplyMapper {
	// Criteria(pageNum, amount, type, keyword)클래스의 필드 중 검색기능을 사용하지 않음.
	// (mybatis)Mapper인터페이스 메서드 파라미터가 2개 이상일 경우 @Param 어노테이션 작업이 들어가야 함.
	// Criteria cri가 @Param("cri")로 들어와서 Mapper.xml에서 "cri"의 이름을 사용하게 되는 것.
	// 댓글 조회 기능
	List<ReplyVO> getListPaging(@Param("cri") Criteria cri, @Param("bno") Long bno);
	
	int getCountByBno(Long bno);
	
	void insert(ReplyVO vo);
	
	void update(ReplyVO vo);
	
	void delete(Integer rno);
}
