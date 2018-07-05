package com.friends.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.friends.entity.MfModuleTimes;
import com.friends.entity.MfSpotPush;
import com.friends.entity.vo.SpotModuleVo;


public interface MfSpotPushService {
	public MfSpotPush add(MfSpotPush t);
	public void delete(String id);
	public MfSpotPush update(MfSpotPush t);
	public MfSpotPush queryById(String id);
	public List<MfSpotPush> queryByExample(MfSpotPush t);
	public List<MfSpotPush> queryByExamplePageable(MfSpotPush t,PageRequest pageRequest);
	public void batchAdd(List<MfSpotPush> ts);
	public void batchDelete(List<MfSpotPush> ts);
	public List<SpotModuleVo> checkSpotAndModule(MfSpotPush t,MfModuleTimes m);
	public SpotModuleVo checkSpotAndModuleByModuleId(MfSpotPush t, MfModuleTimes m , String moduleId);
}
