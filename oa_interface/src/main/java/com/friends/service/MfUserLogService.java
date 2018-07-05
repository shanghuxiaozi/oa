package com.friends.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import com.friends.entity.MfUserLog;
import com.friends.entity.vo.MfUserLogDynamicQueryVo;
import com.friends.utils.Result;


public interface MfUserLogService  {
	/**
	 * 通过用户操作日志表vo参数查询用户操作日志表列表
	 * @param userLogVo 用户操作日志表vo
	 * @return Result(列表数据，分页数据，消息数据，以及)
	 */
	public Result queryDynamic(MfUserLogDynamicQueryVo userLogVo);//通过用户操作日志表vo参数查询用户操作日志表列表
	public MfUserLog add(MfUserLog t);//用户操作日志表添加
	public void delete(String id);//用户操作日志表删除
	public MfUserLog updateSelective(MfUserLog t);//用户操作日志表更新
	public MfUserLog update(MfUserLog t);//用户操作日志表更新
	public MfUserLog queryById(String id);//通过用户操作日志表主键值查询用户操作日志表信息
	public List<MfUserLog> queryByExample(MfUserLog t);//通过用户操作日志表vo参数查询用户操作日志表列表
	public List<MfUserLog> queryByExamplePageable(MfUserLog t,PageRequest pageRequest);
	public void batchAdd(List<MfUserLog> ts);//批量添加用户操作日志表
	public void batchDelete(List<MfUserLog> ts);//批量删除用户操作日志表
	public Result queryDynamicSpecification(MfUserLogDynamicQueryVo userLogVo);//动态查询用户操作日志表
	public Result queryMeSeeWho(MfUserLogDynamicQueryVo userLogVo);//根据用户信息查询对应信息
	
}
