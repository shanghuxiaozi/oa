package com.friends.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import com.friends.entity.Pay;
import com.friends.entity.vo.PayDynamicQueryVo;
import com.friends.utils.Result;



public interface PayService  {
	public Result queryDynamic(PayDynamicQueryVo payDynamicQueryVo);
	public Pay add(Pay t);
	public void delete(Pay t);
	public Pay updateSelective(Pay t);
	public Pay queryById(String id);
	public List<Pay> queryByExample(Pay t);
	public List<Pay> queryByExamplePageable(Pay t,PageRequest pageRequest);
	public void batchAdd(List<Pay> ts);
	public void batchDelete(List<Pay> ts);
	
	// 根据银行卡查薪资
	public Pay queryByIdcard(String bankCardNumber);
	


	// 根据各参数 查询受邀用户
	public List<Pay> queryByParam(String pay,String username,int regular);



}
