package com.friends.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.friends.entity.MfUserInfo;

//
public interface MfUserInfoDao extends BaseDao<MfUserInfo> {

	@Query(value="select * from mf_user_info u where u.openid=?1 ", nativeQuery = true)
	public MfUserInfo queryUserByOpenid(String openId);
	
	@Query(value="select count(*) from mf_user_info u where u.openid=?1 ", nativeQuery = true)
	public Integer queryIsByOpenid(String openId);
    
	@Query(value="select count(*) from mf_user_info u where u.phone=?1 ", nativeQuery = true)
	public Integer queryPhone(String phone);
	
	@Query(value="select * from mf_user_info u where u.phone=?1 ", nativeQuery = true)
	public MfUserInfo queryUser(String phone);
	
	//根据用户id查用户昵称
	@Query(value="select nickname from mf_user_info where id=?1 ", nativeQuery = true)
	public String queryUserNickname(String id);
	
	//查询所有用户id
	@Query(value="select id  from mf_user_info ", nativeQuery = true)
	public List<String> queryUserId();
}
