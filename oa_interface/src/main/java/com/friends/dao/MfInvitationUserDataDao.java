package com.friends.dao;

import com.friends.entity.MfInvitationUserData;

//
public interface MfInvitationUserDataDao extends BaseDao<MfInvitationUserData>{

	public MfInvitationUserData findByIdcard(String idcard);
	
	public MfInvitationUserData findByUserId(String userId);
}
