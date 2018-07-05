package com.friends.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.friends.entity.MfLikeTable;
import com.friends.entity.vo.MfLikeTableDynamicQueryVo;
import com.friends.utils.Result;


public interface MfLikeTableService  {
	public Result queryDynamic(MfLikeTableDynamicQueryVo mfLikeTableDynamicQueryVo);
	public MfLikeTable add(MfLikeTable t);
	public void delete(String id);
	public MfLikeTable update(MfLikeTable t);
	public MfLikeTable queryById(String id);
	public List<MfLikeTable> queryByExample(MfLikeTable t);
	public List<MfLikeTable> queryByExamplePageable(MfLikeTable t,PageRequest pageRequest);
	public void batchAdd(List<MfLikeTable> ts);
	public void batchDelete(List<MfLikeTable> ts);
	public Result judgeWhetherToLikePraise(String memberStyleId, String id);

	//根据会员风采ID和用户ID  查询是否点赞
	public MfLikeTable queryByStyleIdAndUserId(String memberStyleId,String userId);

}
