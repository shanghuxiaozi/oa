package com.friends.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import com.friends.entity.MfUserSingleAuth;
import com.friends.entity.vo.MfUserSingleAuthDynamicQueryVo;
import com.friends.utils.Result;


public interface MfUserSingleAuthService  {
	
	public Result queryDynamic(MfUserSingleAuthDynamicQueryVo userSingleAuthVo);
	public MfUserSingleAuth add(MfUserSingleAuth t);
	public void delete(MfUserSingleAuth t);
	public MfUserSingleAuth queryById(String id);
	public List<MfUserSingleAuth> queryByExample(MfUserSingleAuth t);
	public List<MfUserSingleAuth> queryByExamplePageable(MfUserSingleAuth t,PageRequest pageRequest);
	public void batchAdd(List<MfUserSingleAuth> ts);
	public void batchDelete(List<MfUserSingleAuth> ts);
	
	/**
	 * 根据用户Id查询 单身认证信息
	 * @param userId
	 * @param id String 唯一标识
	 * @param personid String 用户唯一标识
	 * @return rtobj BaseReturn 基本返回对象
	 * @变更记录 2017年10月26日 下午3:09:36 wenxun创建
	 */
	public MfUserSingleAuth queryByUserId(String userId);
	
}
