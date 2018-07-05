package com.friends.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;

import com.friends.entity.SysUserRole;
import com.friends.entity.vo.SysUserRoleVo;
import com.friends.utils.Result;


public interface SysUserRoleService  {
	public Result queryDynamic(SysUserRoleVo sysUserRoleVo);
	public SysUserRole add(SysUserRole t);
	public void delete(String t);
	public SysUserRole updateSelective(SysUserRole t);
	public SysUserRole update(SysUserRole t);
	public SysUserRole queryById(String id);
	public List<SysUserRole> queryByExample(SysUserRole t);
	public List<SysUserRole> queryByExamplePageable(SysUserRole t,PageRequest pageRequest);
	public void batchAdd(List<SysUserRole> ts);
	public void batchDelete(List<SysUserRole> ts);
}
