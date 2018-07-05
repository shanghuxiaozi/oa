package com.friends.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.friends.entity.MfUserAttention;

//
public interface MfUserAttentionDao extends BaseDao<MfUserAttention> {

	// 根据用户id查用户粉丝id
	@Query(value = "select distinct attention_user from mf_user_attention where is_friends=0 and is_black=0 and is_del=0 and by_tention_user=?1 ", nativeQuery = true)
	public List<String> findmyFansByuId(String id);

	// 根据用户id查用户关注id
	@Query(value = "select distinct by_tention_user  from mf_user_attention where is_friends=0 and is_black=0  and is_del=0 and is_allow=0 and attention_user =?1 ", nativeQuery = true)
	public List<String> findmyAttentionByuId(String id);

	// 根据用户id查用户好友id
	@Query(value = "select distinct attention_user from mf_user_attention where is_friends=1 and is_black=0  and is_del=0 and by_tention_user=?1", nativeQuery = true)
	public List<String> findmyFriendsByuId(String id);

	// 根据用户id及被关注用户id查未被拉黑记录
	@Query(value = "select distinct * from mf_user_attention where is_friends=0 and is_del=0 and is_black=0 and  attention_user=?1 and by_tention_user=?2", nativeQuery = true)
	public List<MfUserAttention> queryAttentionByFidAndUid(String friendsId, String uId);

	// 查询被删除的好友id
	@Query(value = "select distinct attention_user from mf_user_attention where is_friends=1 and is_del=1 and is_black=0 and  by_tention_user=?1", nativeQuery = true)
	public List<String> findByDelUid(String id);

	// 根据用户id和被关注用户id以及状态为1（好友）查记录
	@Query(value = "select distinct * from mf_user_attention where is_friends=1 and is_del=0 and is_black=0 and attention_user=?1 and by_tention_user=?2", nativeQuery = true)
	public List<MfUserAttention> queryAttentionByType(String friendsId, String uId);

	// 根据用户id和被关注用户id查未删除未拉黑记录
	@Query(value = "select distinct * from mf_user_attention where is_del=0 and is_black=0 and attention_user=?1 and by_tention_user=?2", nativeQuery = true)
	public List<MfUserAttention> queryAttention(String friendsId, String uId);

	// 根据用户id和被关注用户id查记录
	@Query(value = "select distinct * from mf_user_attention where attention_user=?1 and by_tention_user=?2", nativeQuery = true)
	public List<MfUserAttention> query(String friendsId, String uId);

}
