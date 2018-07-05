package com.friends.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.friends.entity.SysPermission;

public interface SysPermissionDao extends BaseDao<SysPermission> {
	
	/** 本系统只需要菜单级别按钮，所以改成 1=1  [如需要按钮级别的  p.available=1   按钮级别有bug]  **/
	@Query(value = "select * from sys_permission p where 1=1 order by order_num ASC ", nativeQuery = true)
	public List<SysPermission> queryRoleMenu();

	@Query(value = "select * from sys_permission p where p.type=?1 order by order_num ASC ", nativeQuery = true)
	public List<SysPermission> queryPermMenu(Integer type);
}
