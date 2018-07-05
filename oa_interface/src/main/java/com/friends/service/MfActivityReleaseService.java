package com.friends.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import com.friends.entity.MfActivityRelease;
import com.friends.entity.vo.MfActivityReleaseDynamicQueryVo;
import com.friends.utils.Result;


public interface MfActivityReleaseService  {
	

	public Result queryDynamic(MfActivityReleaseDynamicQueryVo sysActivityDynamicQueryVo);
	public MfActivityRelease add(MfActivityRelease t);
	public void delete(MfActivityRelease t);
	public MfActivityRelease updateSelective(MfActivityRelease t);
	public MfActivityRelease queryById(String id);
	public List<MfActivityRelease> queryByExample(MfActivityRelease t);
	public List<MfActivityRelease> queryByExamplePageable(MfActivityRelease t, PageRequest pageRequest);
	public void batchAdd(List<MfActivityRelease> ts);
	public void batchDelete(List<MfActivityRelease> ts);
	
	public Result queryAllActivity();
	
}
