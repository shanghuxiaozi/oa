package com.friends.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import com.friends.entity.MfUserAttention;
import com.friends.entity.vo.MfUserAttentionDynamicQueryVo;
import com.friends.utils.Result;


public interface MfUserAttentionService  {
	
	/**
	 * 通过用户关注表vo参数查询用户关注表列表
	 * @param userAttentionVo 用户关注表vo
	 * @return Result(列表数据，分页数据，消息数据，以及)
	 */
	public Result queryDynamic(MfUserAttentionDynamicQueryVo userAttentionVo);//通过用户关注表vo参数查询用户关注表列表
	public MfUserAttention add(MfUserAttention t);//用户关注表添加
	public void delete(String id);//用户关注表删除
	public MfUserAttention updateSelective(MfUserAttention t);//用户关注表更新
	public MfUserAttention update(MfUserAttention t);//用户关注表更新
	public MfUserAttention queryById(String id);//通过用户关注表主键值查询用户关注表信息
	public List<MfUserAttention> queryByExample(MfUserAttention t);//通过用户关注表vo参数查询用户关注表列表
	public List<MfUserAttention> queryByExamplePageable(MfUserAttention t,PageRequest pageRequest);
	public void batchAdd(List<MfUserAttention> ts);//批量添加用户关注表
	public void batchDelete(List<MfUserAttention> ts);//批量删除用户关注表
	public Result queryDynamicSpecification(MfUserAttentionDynamicQueryVo userAttentionVo);//动态查询用户关注表
	public List<String> findmyFriendsByuId(String id);	//根据用户id查用户好友
	public List<MfUserAttention>  queryAttentionByFidAndUid(String friendsId,String uId ); //根据用户及被关注用户id查记录
	public List<String> findmyFansByuId(String id);//根据用户id查其粉丝
	public List<String> findByDelUid(String id);//查询被删除的好友id
	public List<MfUserAttention>  queryAttentionByType(String friendsId,String uId );//根据用户id和被关注用户id以及状态为1（好友）查记录
	public List<String> findmyAttentionByuId(String id);//根据用户id查用户关注id
	public List<MfUserAttention>  queryAttention(String friendsId,String uId );//根据用户id和被关注用户id查记录
	public List<MfUserAttention>  query(String friendsId,String uId );//根据用户id和被关注用户id查记录
	
}
