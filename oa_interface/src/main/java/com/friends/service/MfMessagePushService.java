package com.friends.service;

import java.util.List;










import org.springframework.data.domain.PageRequest;

import com.friends.entity.MfMessagePush;
import com.friends.entity.vo.MfMessagePushDynamicQueryVo;
import com.friends.utils.Result;

public interface MfMessagePushService  {
	public Result queryDynamic(MfMessagePushDynamicQueryVo messagePushDynamicQueryVo);
	public MfMessagePush add(MfMessagePush t);
	public MfMessagePush update(MfMessagePush t);
	public void delete(MfMessagePush t);
	public MfMessagePush queryById(String id);
	public List<MfMessagePush> queryByExample(MfMessagePush t);
	public List<MfMessagePush> queryByExamplePageable(MfMessagePush t,PageRequest pageRequest);
	public void batchAdd(List<MfMessagePush> ts);
	public void batchDelete(List<MfMessagePush> ts);
	public Integer querySystemNumber(String id);//查询系统消息个数
	public Integer queryActivityNumber(String id);//查询活动消息个数
	public List<MfMessagePush> querySystemMessage(String id);//查询非用户须知系统消息
	public List<MfMessagePush> queryMail(String id);//查询用户站内信
	public MfMessagePush  queryUserTipsMessage(String id);//查询用户须知消息
	public List<MfMessagePush> queryActivityMessage(String id);//查询活动消息
}
