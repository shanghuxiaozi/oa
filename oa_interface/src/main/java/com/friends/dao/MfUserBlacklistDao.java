package com.friends.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.friends.entity.MfUserBlacklist;

//
public interface MfUserBlacklistDao extends BaseDao<MfUserBlacklist> {

	// 根据用户id查其黑名单用户id
	@Query(value = "select distinct by_user_id from mf_user_blacklist where is_del=0 and is_remove=0 and user_id=?1 ", nativeQuery = true)
	public List<String> findmyBlackListByuId(String id);

	// 根据用户id及黑名单用户ID查黑名单记录
	@Query(value = "select distinct * from mf_user_blacklist where is_del=0 and is_remove=0 and user_id=?1  and by_user_id=?2 ", nativeQuery = true)
	public List<MfUserBlacklist> findmyBlackList(String uId, String byBlackId);

	// 根据用户id和被拉黑用户id查黑名单记录
	@Query(value = "select distinct * from mf_user_blacklist where  is_del=0 and is_remove=0 and user_id=?1 and by_user_id =?2", nativeQuery = true)
	public List<MfUserBlacklist> blackListUser(String uId, String byBlackId);

	// 根据用户id查询黑名单未移出用户，包括删除的
	@Query(value = "select distinct by_user_id from mf_user_blacklist where  is_remove=0 and  user_id=?1", nativeQuery = true)
	public List<String> blackListUserByuId(String uId);

	// 查用户黑名单已删除id
	@Query(value = "select distinct by_user_id from mf_user_blacklist where is_del=1 and user_id=?1", nativeQuery = true)
	public List<String> blackListDelId(String uId);

	// 查未移出黑名单用户id
	@Query(value = "select distinct by_user_id from mf_user_blacklist where is_del=0 and is_remove=0 and user_id=?1", nativeQuery = true)
	public List<String> NoRemoveblackId(String uId);
	
	// 根据用户id和被拉黑用户id查黑名单记录
	@Query(value = "select distinct * from mf_user_blacklist where user_id=?1 and by_user_id =?2", nativeQuery = true)
	public MfUserBlacklist findBlacklistById(String uId, String byBlackId);
}
