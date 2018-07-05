package com.friends.dao;

import org.springframework.data.jpa.repository.Query;

import com.friends.entity.MfIntegralDetails;

//
public interface MfIntegralDetailsDao extends BaseDao<MfIntegralDetails>{

	@Query(value="select count(1) from mf_integral_details where user_id=?1 ",nativeQuery=true)
	public int findCountByUserId(String id);
	
	
}
