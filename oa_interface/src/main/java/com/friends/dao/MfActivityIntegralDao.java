package com.friends.dao;

import org.springframework.data.jpa.repository.Query;
import com.friends.entity.MfActivityIntegral;

//
public interface MfActivityIntegralDao extends BaseDao<MfActivityIntegral>{

	//根据用户id查积分表
	@Query(value="select * from mf_activity_integral where user_id=?1 ",nativeQuery=true)
	public MfActivityIntegral findIntegralByuId(String uId);
	
}
