package com.friends.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.friends.entity.MfActivityEnroll;

//
public interface MfActivityEnrollDao extends BaseDao<MfActivityEnroll>{

	@Query("select count(*) from MfActivityEnroll u where u.activityId=?1 and u.isJoin = '0'")
	public Integer countByActivityId(String activityId);

	@Query("select count(*) from MfActivityEnroll u where u.isJoin = '0' and u.userId=?1 and u.activityId=?2 ")
	public Integer queryBaoming(String userId, String activityId);
	
	@Query(value="select * from mf_activity_enroll u where u.is_join = ?1 and u.user_id=?2 and u.activity_id=?3 ", nativeQuery = true)
	public MfActivityEnroll queryInformation(String isJoin,String userId, String activityId);
	
	@Query("select count(*) from MfActivityEnroll u where u.isJoin = '1' and u.userId=?1 and u.activityId=?2 ")
	public Integer queryIshavingjoin(String userId, String activityId);
	
	/** 根据用户Id 获取活动Id 并且是参加过的，没退出 **/
	@Query(value="select activity_id from mf_activity_enroll where user_id=?1 and is_join=0 ",nativeQuery=true)
	public List<String> findByUserId(String userId); 
	
	@Query(value="select * from mf_activity_enroll a where 1=1 and a.is_join=0 and a.activity_id=?1 ",nativeQuery=true)
	public List<MfActivityEnroll> findActivityEnrollById(String id);
	
	/** 根据活动ID和用户id获取 ActivityEnroll**/
	@Query(value="select * from mf_activity_enroll a where 1=1 and a.is_join=0 and a.activity_id=?1 and a.user_id=?2",nativeQuery=true)
	public MfActivityEnroll findActivityEnrollByUidAndAid(String activityId,String userId);
	
}
