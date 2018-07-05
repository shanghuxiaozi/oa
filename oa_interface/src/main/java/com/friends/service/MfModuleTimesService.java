package com.friends.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.friends.entity.MfModuleTimes;


public interface MfModuleTimesService {
	public MfModuleTimes add(MfModuleTimes t);
	public void delete(String id);
	public MfModuleTimes update(MfModuleTimes t);
	public MfModuleTimes queryById(String id);
	public List<MfModuleTimes> queryByExample(MfModuleTimes t);
	public List<MfModuleTimes> queryByExamplePageable(MfModuleTimes t,PageRequest pageRequest);
	public void batchAdd(List<MfModuleTimes> ts);
	public void batchDelete(List<MfModuleTimes> ts);
}
