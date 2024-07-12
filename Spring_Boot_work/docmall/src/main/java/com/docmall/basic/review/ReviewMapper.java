package com.docmall.basic.review;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.docmall.basic.common.dto.Criteria;

public interface ReviewMapper {
	
	List<ReviewVo> rev_list(@Param("pro_num")Integer pro_num, @Param("cri")Criteria cri);
	
	int getCountReviewByPro_num(Integer pro_num); // 검색이 안 들어가니까 Criteria가 따라 들어오지 않음.
	
	void review_save(ReviewVo vo);
	
	void review_delete(Long rev_code);
	
	ReviewVo review_modify(Long rev_code);
	
	void review_update(ReviewVo vo);
	
}
