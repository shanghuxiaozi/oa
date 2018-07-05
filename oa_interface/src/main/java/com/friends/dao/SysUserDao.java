package com.friends.dao;

import org.springframework.data.jpa.repository.Query;
import com.friends.entity.SysUser;

public interface SysUserDao extends BaseDao<SysUser> {
	
	/** 通过username查找用户信息; */
	public SysUser findByUserName(String username);

	@Query("select count(*) from SysUser u where u.userName=?1")
	public Integer countByUserName(String userName);
}
