package com.friends.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import com.friends.entity.MfUserTipoff;
import com.friends.entity.vo.MfUserTipoffDynamicQueryVo;
import com.friends.utils.Result;


public interface MfUserTipoffService  {
	
	public Result queryDynamic(MfUserTipoffDynamicQueryVo tipOffDynamicQueryVo);
	public MfUserTipoff add(MfUserTipoff t);
	public void delete(MfUserTipoff t);
	public MfUserTipoff queryById(String id);
	public List<MfUserTipoff> queryByExample(MfUserTipoff t);
	public List<MfUserTipoff> queryByExamplePageable(MfUserTipoff t,PageRequest pageRequest);
	public void batchAdd(List<MfUserTipoff> ts);
	public void batchDelete(List<MfUserTipoff> ts);
	
	// 根据我的ID 获取被我投诉的用户信息
	public Result queryByBeComplainsId(String id);
	
	// 根据投诉ID  获取详细信息
	public Result queryComplaintDetails(String id);
	
}
