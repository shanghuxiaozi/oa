package com.friends.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.friends.entity.MfWorkUnits;


public interface MfWorkUnitsDao extends BaseDao<MfWorkUnits>{

	//查询工作单位
	@Query(value = "select work_units from  mf_work_units", nativeQuery = true)
	public List<String> findWorkUnits();
	
}
