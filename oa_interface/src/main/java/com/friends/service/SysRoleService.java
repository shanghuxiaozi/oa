package com.friends.service;

import java.util.List;
import java.util.Map;

import com.friends.entity.SysRole;
import com.friends.entity.vo.SysRoleVo;
import com.friends.utils.Result;


public interface SysRoleService  {
	/**
	 * 通过角色信息查询角色列表，用于页面的列表展示及条件查询
	 * @param sysRoleVo
	 * @return 角色的列表，角色的数量
	 */
	public Result queryDynamic(SysRoleVo sysRoleVo);
	/**
	 * 通过角色信息添加角色
	 * @param t 页面请求的参数信息
	 * @return 角色信息
	 */
	public SysRole add(SysRole t);
	/**
	 * 通过角色信息删除角色
	 * @param t 角色信息，其中存有角色的ID数据
	 */
	public void delete(String id);
	/**
	 * 通过角色信息和id修改角色
	 * @param t 角色信息
	 * @param id  角色ID
	 * @return 角色信息
	 */
	public SysRole updateSelective(SysRole t);
	/**
	 * 通过角色信息和id修改角色
	 * @param t 角色信息
	 * @param id  角色ID
	 * @return 角色信息
	 */
	public SysRole update(SysRole t);
	/**
	 * 通过角色id查询角色
	 * @param id  角色ID
	 * @return 角色信息
	 */
	public SysRole queryById(String id);
	/**
	 * 通过角色信息查询角色集合
	 * @param t 角色信息
	 * @return 角色集合
	 */
	public List<SysRole> queryByExample(SysRole t);
	/**
	 * 批量添加角色
	 * @param ts 要添加的列表集合
	 */
	public void batchAdd(List<SysRole> ts);
	/**
	 * 批量删除角色
	 * @param ts 要删除的列表集合
	 */
	public void batchDelete(List<SysRole> ts);
	/**
	 * 通过用户id查询角色
	 * @param id  角用户ID
	 * @return 角色信息
	 */
	public List<SysRole> queryByUserId(String id);
	/**
	 * 通过角色名查询角色的数量
	 * @param role角色名
	 */
	public Integer countByName(String name);
	/**
	 * 通过用户ID获取所有的角色名称
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> queryRoleByUserId(String id);
}
