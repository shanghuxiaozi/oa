package com.friends.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import com.friends.entity.MfMyCollection;
import com.friends.entity.vo.MfMyCollectionDynamicQueryVo;
import com.friends.utils.Result;


public interface MfMyCollectionService  {
	
	public Result queryDynamic(MfMyCollectionDynamicQueryVo mfMyCollectionDynamicQueryVo);
	public MfMyCollection add(MfMyCollection t);
	public void delete(String id);
	public MfMyCollection update(MfMyCollection t);
	public MfMyCollection queryById(String id);
	public List<MfMyCollection> queryByExample(MfMyCollection t);
	public List<MfMyCollection> queryByExamplePageable(MfMyCollection t,PageRequest pageRequest);
	public void batchAdd(List<MfMyCollection> ts);
	public void batchDelete(List<MfMyCollection> ts);
	
	// 根据用户ID 连表(活动回顾表)查询活动数据. 
	public Result queryByUserId(String id,PageRequest pageRequest);
	// 根据用户ID 连表(交友课堂表)查询课堂视频数据
	public Result queryClassroomById(String id,PageRequest pageRequest);
	
}
