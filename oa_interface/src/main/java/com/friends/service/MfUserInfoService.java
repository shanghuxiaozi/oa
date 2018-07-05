package com.friends.service;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import com.friends.entity.MfUserInfo;
import com.friends.entity.vo.MfUserInfoDynamicQueryVo;
import com.friends.utils.Result;


public interface MfUserInfoService  {
	/**
	 * 通过网站用户表vo参数查询网站用户表列表
	 * @param sysUserinfoVo 网站用户表vo
	 * @return Result(列表数据，分页数据，消息数据，以及)
	 */
	public Result queryDynamic(MfUserInfoDynamicQueryVo sysUserinfoVo);//通过网站用户表vo参数查询网站用户表列表
	public Result queryDynamic2(MfUserInfoDynamicQueryVo sysUserinfoVo);  //关联查询
	public MfUserInfo add(MfUserInfo t);//网站用户表添加
	public void delete(String id);//网站用户表删除
	public MfUserInfo updateSelective(MfUserInfo t);//网站用户表更新
	public MfUserInfo update(MfUserInfo t);//网站用户表更新
	public MfUserInfo queryById(String id);//通过网站用户表主键值查询网站用户表信息
	public List<MfUserInfo> queryByExample(MfUserInfo t);//通过网站用户表vo参数查询网站用户表列表
	public List<MfUserInfo> queryByExamplePageable(MfUserInfo t,PageRequest pageRequest);
	public void batchAdd(List<MfUserInfo> ts);//批量添加网站用户表
	public void batchDelete(List<MfUserInfo> ts);//批量删除网站用户表
	public Result queryDynamicSpecification(MfUserInfoDynamicQueryVo sysUserinfoVo);//动态查询网站用户表
	public MfUserInfo queryUserByOpenid(String openid);//根据openId查询用户信息
	public Integer queryPhone(String phone);//查询该手机号是否注册用户
	public Integer queryIsByOpenid(String openId);//判断该openId是否已注册用户
	public MfUserInfo queryUser(String phone);//根据用户手机号查询用户信息	
	public Result queryByActivityId(String id);//通过活动id查用户信息
	public String queryUserNickname(String id);//根据用户id查用户昵称
	public List<String> queryUserId();	//查询所有用户id

	//根据用户类型、时间段 查询用户数据。
	public Result queryByTimeSlot(String userType, String startDate,String endDate);
}
