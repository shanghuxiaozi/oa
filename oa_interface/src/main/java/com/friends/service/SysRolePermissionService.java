package com.friends.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import com.friends.entity.SysRolePermission;
import com.friends.entity.vo.SysRolePermissionVo;
import com.friends.utils.Result;


public interface SysRolePermissionService  {
	public Result queryDynamic(SysRolePermissionVo SysRolePermissionVo);
	public SysRolePermission add(SysRolePermission t);
	public void delete(String id);
	public SysRolePermission updateSelective(SysRolePermission t);
	public SysRolePermission update(SysRolePermission t);
	public SysRolePermission queryById(String id);
	public List<SysRolePermission> queryByExample(SysRolePermission t);
	public List<SysRolePermission> queryByExamplePageable(SysRolePermission t,PageRequest pageRequest);
	public void batchAdd(List<SysRolePermission> ts);
	public void batchDelete(List<SysRolePermission> ts);
	public List<SysRolePermission> queryPermIds(String roleId);
	public Integer queryCountById(String id);
}
