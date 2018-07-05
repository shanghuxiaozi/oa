package com.friends.dao;

import org.springframework.data.jpa.repository.Query;
import com.friends.entity.SysRole;

public interface SysRoleDao extends BaseDao<SysRole> {

	@Query("select count(*) from SysRole role where role.name=?1")
	public Integer countByName(String name);

}
