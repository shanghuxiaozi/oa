package com.friends.service;

import java.util.List;

import com.friends.entity.SysPermission;
import com.friends.entity.vo.SysPermissionVo;
import com.friends.utils.Result;

public interface SysPermissionService  {
	/**
	 * 通过角色信息查询角色列表，用于页面的列表展示及条件查询
	 * @param userInfoDynamicQueryVo
	 * @return 角色的列表，角色的数量
	 */
	public Result queryDynamic(SysPermissionVo SysPermissionVo);
	/**
	 * 通过角色信息添加角色
	 * @param t 页面请求的参数信息
	 * @return 角色信息
	 */
	public SysPermission add(SysPermission t);
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
	public SysPermission updateSelective(SysPermission t);
	/**
	 * 通过角色信息和id修改角色
	 * @param t 角色信息
	 * @param id  角色ID
	 * @return 角色信息
	 */
	public SysPermission update(SysPermission t);
	/**
	 * 通过角色id查询角色
	 * @param id  角色ID
	 * @return 角色信息
	 */
	public SysPermission queryById(String id);
	/**
	 * 通过角色信息查询角色集合
	 * @param t 角色信息
	 * @return 角色集合
	 */
	public List<SysPermission> queryByExample(SysPermission t);
	/**
	 * 批量添加角色
	 * @param ts 要添加的列表集合
	 */
	public void batchAdd(List<SysPermission> ts);
	/**
	 * 批量删除角色
	 * @param ts 要删除的列表集合
	 */
	public void batchDelete(List<SysPermission> ts);
	/**
	 * 通过角色ID查询权限集合
	 * @param roleId 
	 * @return 权限集合
	 */
	public List<SysPermission> queryByRoleId(String roleId);
	/**
	 * 通过资源类型查询页面菜单列表的数据
	 * @param resourceType
	 * @return 权限集合
	 */
	public List<SysPermission> queryMenu(String resourceType);
	
	public List<SysPermission> queryRoleMenu();
	
	public List<SysPermission> queryPermMenu(Integer type);
	/**
	 * 通过字段名称和值查询权限的数量
	 * @param value  值
	 * @param field  字段
	 * @return 权限数量
	 */
	public Integer countByField(String value,String field);
	/**
	 * 通过字段名称和值以及父ID查询权限的数量
	 * @param value 值
	 * @param field 字段
	 * @param parentId  父ID
	 * @return  权限数量
	 */
	public Integer countByFieldAndParentId(String value,String field,String parentId);
	/**
	 * 通过父ID查询权限
	 * @param parentId
	 * @return 权限集合
	 */
	public List<SysPermission> queryPermByParentId(String parentId);
	/**
	 * 通过父ID查询权限的数量
	 * @param parentId
	 * @return 权限的数量
	 */
	public Integer queryPermCountByParentId(String parentId);
}
