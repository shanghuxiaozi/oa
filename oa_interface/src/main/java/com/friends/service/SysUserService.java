package com.friends.service;

import java.util.List;

import com.friends.entity.SysUser;
import com.friends.entity.vo.SysUserVo;
import com.friends.utils.Result;


public interface SysUserService  {
	/**通过username查找用户信息;*/
	public SysUser findByUserName(String username);
	/**
	 * 通过用户信息查询用户列表，用于页面的列表展示及条件查询
	 * @param SysUserVo
	 * @return 用户的列表，用户的数量
	 */
	public Result queryDynamic(SysUserVo sysUserVo);
	/**
	 * 通过用户信息添加用户
	 * @param t 页面请求的参数信息
	 * @return 用户信息
	 */
	public SysUser add(SysUser t);
	/**
	 * 通过用户信息删除用户
	 * @param t 用户信息，其中存有用户的ID数据
	 */
	public void delete(String id);
	/**
	 * 通过用户信息和id修改用户
	 * @param t 用户信息
	 * @param id  用户ID
	 * @return 用户信息
	 */
	public SysUser updateSelective(SysUser t);
	/**
	 * 通过用户信息和id修改用户
	 * @param t 用户信息
	 * @param id  用户ID
	 * @return 用户信息
	 */
	public SysUser update(SysUser t);
	/**
	 * 通过用户id查询用户
	 * @param id  用户ID
	 * @return 用户信息
	 */
	public SysUser queryById(String id);
	/**
	 * 通过用户信息查询用户集合
	 * @param t 用户信息
	 * @return 用户集合
	 */
	public List<SysUser> queryByExample(SysUser t);
	/**
	 * 批量添加用户
	 * @param ts 要添加的列表集合
	 */
	public void batchAdd(List<SysUser> ts);
	/**
	 * 批量删除用户
	 * @param ts 要删除的列表集合
	 */
	public void batchDelete(List<SysUser> ts);
	/**
	 * 通过用户名查询用户的数量
	 * @param userName用户名
	 */
	public Integer countByUserName(String userName);
}
