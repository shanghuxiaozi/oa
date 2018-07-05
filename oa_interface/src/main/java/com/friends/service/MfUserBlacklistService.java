package com.friends.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import com.friends.entity.MfUserBlacklist;
import com.friends.entity.vo.MfUserBlacklistDynamicQueryVo;
import com.friends.utils.Result;


public interface MfUserBlacklistService  {
	
	/**
	 * 通过用户黑名单表vo参数查询用户黑名单表列表
	 * @param userBlacklistVo 用户黑名单表vo
	 * @return Result(列表数据，分页数据，消息数据，以及)
	 */
	public Result queryDynamic(MfUserBlacklistDynamicQueryVo userBlacklistVo);//通过用户黑名单表vo参数查询用户黑名单表列表
	public MfUserBlacklist add(MfUserBlacklist t);//用户黑名单表添加
	public void delete(String id);//用户黑名单表删除
	public MfUserBlacklist updateSelective(MfUserBlacklist t);//用户黑名单表更新
	public MfUserBlacklist update(MfUserBlacklist t);//用户黑名单表更新
	public MfUserBlacklist queryById(String id);//通过用户黑名单表主键值查询用户黑名单表信息
	public List<MfUserBlacklist> queryByExample(MfUserBlacklist t);//通过用户黑名单表vo参数查询用户黑名单表列表
	public List<MfUserBlacklist> queryByExamplePageable(MfUserBlacklist t,PageRequest pageRequest);
	public void batchAdd(List<MfUserBlacklist> ts);//批量添加用户黑名单表
	public void batchDelete(List<MfUserBlacklist> ts);//批量删除用户黑名单表
	public Result queryDynamicSpecification(MfUserBlacklistDynamicQueryVo userBlacklistVo);//动态查询用户黑名单表
	public List<String> findmyBlackListByuId(String id);//根据用户id查其黑名单
	public List<MfUserBlacklist> findmyBlackList(String uId,String byBlackId);//根据用户id查黑名单记录
	public List<MfUserBlacklist> blackListUser(String uId,String byBlackId);//根据用户id和被拉黑用户id查黑名单记录
	public List<String> blackListUserByuId(String uId);	
	public List<String> blackListDelId(String uId);	//查用户黑名单已删除id
	public List<String> NoRemoveblackId(String uId);	//查未移出黑名单用户id
	
	public MfUserBlacklist queryBlacklistById(String uId,String byBlackId);//根据用户id和被拉黑用户id查黑名单记录
}
