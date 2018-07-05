package com.friends.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import com.friends.entity.MfActivityReview;
import com.friends.entity.vo.MfActivityReviewDynamicQueryVo;
import com.friends.utils.Result;



public interface MfActivityReviewService  {
	
	public Result queryDynamic(MfActivityReviewDynamicQueryVo mfActivityReviewDynamicQueryVo);
	public MfActivityReview add(MfActivityReview t);
	public void delete(String id);
	public MfActivityReview update(MfActivityReview t);
	public MfActivityReview queryById(String id);
	public List<MfActivityReview> queryByExample(MfActivityReview t);
	public List<MfActivityReview> queryByExamplePageable(MfActivityReview t,PageRequest pageRequest);
	public void batchAdd(List<MfActivityReview> ts);
	public void batchDelete(List<MfActivityReview> ts);
	
}
