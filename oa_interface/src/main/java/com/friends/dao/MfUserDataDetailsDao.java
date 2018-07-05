package com.friends.dao;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import com.friends.entity.MfUserDataDetails;

//
public interface MfUserDataDetailsDao extends BaseDao<MfUserDataDetails> {

	// 根据用户id查用户信息表
	@Query(value = "select * from mf_user_data_details where user_id=?1 ", nativeQuery = true)
	public MfUserDataDetails findUserInformationByuId(String uId);

	// 根据用户id查用户信息表
	@Query(value = "select count(*) from mf_user_data_details where user_id=?1 ", nativeQuery = true)
	public Integer findInformationByuId(String uId);
	
}
