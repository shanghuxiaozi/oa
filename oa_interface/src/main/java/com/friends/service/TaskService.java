package com.friends.service;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.PageRequest;

import com.friends.entity.Daily;
import com.friends.entity.Pay;
import com.friends.entity.vo.DailyDynamicQueryVo;
import com.friends.entity.vo.PayDynamicQueryVo;
import com.friends.utils.Result;



public interface TaskService  {
	public Result queryDynamic(DailyDynamicQueryVo dailyDynamicQueryVo);
	public Daily add(Daily t);
	public void delete(Daily t);
	public Daily updateSelective(Daily t);
	public Daily queryById(String id);
	public List<Daily> queryByExample(Daily t);
	public List<Daily> queryByExamplePageable(Daily t,PageRequest pageRequest);
	public void batchAdd(List<Daily> ts);
	public void batchDelete(List<Daily> ts);
	
	// 根据银行卡查薪资
	public Daily queryByIdcard(String userName);
	


	// 根据各参数 查询受邀用户
	//public List<Daily> queryByParam(String userName,Date writeTime);



}
