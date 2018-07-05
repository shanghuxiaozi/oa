package com.friends.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.friends.entity.MfWorkUnits;
import com.friends.entity.vo.MfWorkUnitsDynamicQueryVo;
import com.friends.utils.Result;


public interface MfWorkUnitsService  {
	
	public Result queryDynamic(MfWorkUnitsDynamicQueryVo workUnitsVo);
	public MfWorkUnits add(MfWorkUnits t);
	public void delete(String id);
	public MfWorkUnits queryById(String id);
	public List<MfWorkUnits> queryByExample(MfWorkUnits t);
	public List<MfWorkUnits> queryByExamplePageable(MfWorkUnits t,PageRequest pageRequest);
	public void batchAdd(List<MfWorkUnits> ts);
	public void batchDelete(List<MfWorkUnits> ts);
	public List<String> findWorkUnits();//查询工作单位
	
	public List<MfWorkUnits> queryAll();
}
