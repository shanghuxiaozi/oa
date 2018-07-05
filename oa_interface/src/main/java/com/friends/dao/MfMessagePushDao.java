package com.friends.dao;


import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.friends.entity.MfMessagePush;


public interface MfMessagePushDao extends BaseDao<MfMessagePush>{

	//查询系统消息个数
	@Query(value="select count(1) from mf_message_push where message_type=1 and type=0 and user_id=?1",nativeQuery=true)
	public Integer querySystemNumber(String id);
	//查询活动消息个数
	@Query(value="select count(1) from mf_message_push where message_type=2 and type=0 and user_id=?1",nativeQuery=true)
	public Integer queryActivityNumber(String id);
	//查询非用户须知系统消息
	@Query(value="select * from mf_message_push where message_type=1 and is_newuser_tips=0 and user_id=?1 ORDER BY creat_time desc",nativeQuery=true)
	public List<MfMessagePush> querySystemMessage(String id);
	//查询可读取的站内信
	@Query(value="select * from mf_message_push where (message_type=3 or message_type=4) and is_newuser_tips=0 and user_id=?1 ORDER BY creat_time desc",nativeQuery=true)
	public List<MfMessagePush> queryMail(String id);
	//查询用户须知消息
	@Query(value="select * from mf_message_push where message_type=1 and is_newuser_tips=1 and user_id=?1",nativeQuery=true)
	public MfMessagePush queryUserTipsMessage(String id);
	//查询活动消息
	@Query(value="select * from mf_message_push where message_type=2 and user_id=?1 ORDER BY creat_time desc",nativeQuery=true)
	public List<MfMessagePush> queryActivityMessage(String id);
}
