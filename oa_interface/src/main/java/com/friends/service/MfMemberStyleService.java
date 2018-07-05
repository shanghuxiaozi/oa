package com.friends.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.friends.entity.MfMemberStyle;
import com.friends.entity.vo.MfMemberStyleDynamicQueryVo;
import com.friends.utils.Result;


public interface MfMemberStyleService  {
	// 连表动态查询
	public Result queryJoinTableDynamic(MfMemberStyleDynamicQueryVo mfMemberStyleDynamicQueryVo);
	public Result queryDynamic(MfMemberStyleDynamicQueryVo mfMemberStyleDynamicQueryVo);
	public MfMemberStyle add(MfMemberStyle t);
	public void delete(String id);
	public MfMemberStyle update(MfMemberStyle t);
	public MfMemberStyle queryById(String id);
	public List<MfMemberStyle> queryByExample(MfMemberStyle t);
	public List<MfMemberStyle> queryByExamplePageable(MfMemberStyle t,PageRequest pageRequest);
	public void batchAdd(List<MfMemberStyle> ts);
	public void batchDelete(List<MfMemberStyle> ts);
	public Result queryAllMemberStyle(String num,String userId);
	public Result queryAttentionMemberStyle(String num,String userId);
	public Result findMyUserNickNameAndHideImg(String userid);

	//查询所有会员动态
	public Result queryAllMemberStyle(PageRequest pageRequest);
	//查询我的动态
	public Result queryMyStyle(PageRequest pageRequest,String userId);
	//查询我的好友动态（包括关注的）
	public Result queryFriendsDynamic(PageRequest pageRequest,String userId);
	//根据发布动态者ID 查询点赞的人
	public Result queryByMemberStyleId(String memberStyleId);
}
