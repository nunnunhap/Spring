package com.docmall.basic.review;

import java.util.List;

import org.springframework.stereotype.Service;

import com.docmall.basic.common.dto.Criteria;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	
	private final ReviewMapper reviewMapper;
	
	List<ReviewVo> rev_list(Integer pro_num, Criteria cri) {
		return reviewMapper.rev_list(pro_num, cri);
	}
	
	int getCountReviewByPro_num(Integer pro_num) {
		return reviewMapper.getCountReviewByPro_num(pro_num);
	}
	
	void review_save(ReviewVo vo) {
		reviewMapper.review_save(vo);
	}
	
	void review_delete(Long rev_code) {
		reviewMapper.review_delete(rev_code);
	}
	
	
}
