package com.friends.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import com.friends.entity.MfIntegralDetails;
import com.friends.entity.vo.MfIntegralDetailsDynamicQueryVo;
import com.friends.utils.Result;


public interface MfIntegralDetailsService  {

	public Result queryDynamic(MfIntegralDetailsDynamicQueryVo sysIntegralDetailsVo);
	public MfIntegralDetails add(MfIntegralDetails t);
	public void delete(MfIntegralDetails t);
	public MfIntegralDetails queryById(String id);
	public List<MfIntegralDetails> queryByExample(MfIntegralDetails t);
	public List<MfIntegralDetails> queryByExamplePageable(MfIntegralDetails t,PageRequest pageRequest);
	public void batchAdd(List<MfIntegralDetails> ts);
	public void batchDelete(List<MfIntegralDetails> ts);
	
	/**
	 * 根据用户Id 查询参加的活动记录数
	 * @param id
	 * @param id String 唯一标识
	 * @param personid String 用户唯一标识
	 * @return rtobj BaseReturn 基本返回对象
	 * @变更记录 2017年10月20日 上午9:18:04 wenxun创建
	 */
	public int queryCountByUserId(String id);
	
	/**
	 * 根据用户Id 查询积分详情
	 * @param id
	 * @param id String 唯一标识
	 * @param personid String 用户唯一标识
	 * @return rtobj BaseReturn 基本返回对象
	 * @变更记录 2017年10月19日 下午5:50:59 wenxun创建
	 */
	public Result queryIntegralDetailsByUserId(String id);
	
}
