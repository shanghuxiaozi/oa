package com.friends.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import com.friends.entity.MfActivityIntegral;
import com.friends.entity.vo.MfActivityIntegralDynamicQueryVo;
import com.friends.utils.Result;


public interface MfActivityIntegralService  {

	public Result queryDynamic(MfActivityIntegralDynamicQueryVo integralDynamicQueryVo);
	public MfActivityIntegral add(MfActivityIntegral t);
	public void delete(MfActivityIntegral t);
	public MfActivityIntegral queryById(String id);
	public List<MfActivityIntegral> queryByExample(MfActivityIntegral t);
	public List<MfActivityIntegral> queryByExamplePageable(MfActivityIntegral t,PageRequest pageRequest);
	public void batchAdd(List<MfActivityIntegral> ts);
	public void batchDelete(List<MfActivityIntegral> ts);
	public MfActivityIntegral updateSelective(MfActivityIntegral t);
	//根据用户id查积分表
	public MfActivityIntegral findIntegralByuId(String uId);
	
}
