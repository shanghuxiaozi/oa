package com.friends.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.friends.entity.MfLikeTable;
/**
 * 
 * @author 鬼火三十六丈高
 * @ClassName: MfLikeTableDao 
 * @Description: TODO(点赞信息Dao层) 
 * @author A18ccms a18ccms_gmail_com 
 * @date 修改时间2017年12月29日 上午10:22:15 
 * TODO
 */
public interface MfLikeTableDao extends BaseDao<MfLikeTable>{

	/**
	 * 
	 * 修改人:鬼火三十六丈高
	 * @param userId 点赞人id
	 * @param memberStyleId 发布信息id
	 * @return 返回删除成功
	 * 时间:2017年12月29日 上午10:21:21
	 */
	@Modifying
	@Transactional
	@Query(value="delete from mf_like_table where user_id=?1 and member_style_id=?2",nativeQuery=true)
	public Integer deleteLikeTableByUserIdAnaMembleId(String userId,String memberStyleId );
	
	@Query(value="select * from mf_like_table where member_style_id=?1 and user_id=?2",nativeQuery=true)
	public List<MfLikeTable> judgeWhetherToLikePraise(String memberStyleId,String userId );

	@Query(value="select * from mf_like_table where member_style_id=?1 and user_id=?2 ",nativeQuery=true)
	public MfLikeTable findByMemberStyleIdAndUserId(String memberStyleId,String userId);

}
