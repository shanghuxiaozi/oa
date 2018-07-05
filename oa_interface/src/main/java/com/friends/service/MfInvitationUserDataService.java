package com.friends.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import com.friends.entity.MfInvitationUserData;
import com.friends.entity.vo.MfInvitationUserDataDynamicQueryVo;
import com.friends.utils.Result;



public interface MfInvitationUserDataService  {
	public Result queryDynamic(MfInvitationUserDataDynamicQueryVo mfInvitationUserDataDynamicQueryVo);
	public MfInvitationUserData add(MfInvitationUserData t);
	public void delete(MfInvitationUserData t);
	public MfInvitationUserData updateSelective(MfInvitationUserData t);
	public MfInvitationUserData queryById(String id);
	public List<MfInvitationUserData> queryByExample(MfInvitationUserData t);
	public List<MfInvitationUserData> queryByExamplePageable(MfInvitationUserData t,PageRequest pageRequest);
	public void batchAdd(List<MfInvitationUserData> ts);
	public void batchDelete(List<MfInvitationUserData> ts);
	
	// 根据身份证号码，查询出受邀用户资料
	public MfInvitationUserData queryByIdcard(String idcard);
	
	public MfInvitationUserData queryByUserId(String userId);

	// 根据各参数 查询受邀用户
	public List<MfInvitationUserData> queryByParam(String idcard,String phone,String status,String unitAttribute,String postNature,String wordUnit);

	// 统计受邀用户数据
	public Result statisticsData();

}
