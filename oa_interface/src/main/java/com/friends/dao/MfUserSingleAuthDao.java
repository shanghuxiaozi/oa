package com.friends.dao;

import org.springframework.data.jpa.repository.Query;

import com.friends.entity.MfUserSingleAuth;

//
public interface MfUserSingleAuthDao extends BaseDao<MfUserSingleAuth>{

	/** 根据用户id 查询单身认证信息  **/
	@Query(value="select * from mf_user_single_auth where user_id=?1 ",nativeQuery=true)
	public MfUserSingleAuth findByUserId(String userId);
  
}
