package com.friends.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.friends.entity.MfActivityEnroll;
import com.friends.entity.vo.MfActivityEnrollDynamicQueryVo;
import com.friends.utils.Result;

public interface MfActivityEnrollService  {
	
	/**
	 * 通过活动报名表vo参数查询活动报名表列表
	 * @param activityEnrollVo 活动报名表vo
	 * @return Result(列表数据，分页数据，消息数据，以及)
	 */
	public Result queryDynamic(MfActivityEnrollDynamicQueryVo activityEnrollVo);//通过活动报名表vo参数查询活动报名表列表
	public MfActivityEnroll add(MfActivityEnroll t);//活动报名表添加
	public void delete(String id);//活动报名表删除
	public MfActivityEnroll updateSelective(MfActivityEnroll t);//活动报名表更新
	public MfActivityEnroll update(MfActivityEnroll t);//活动报名表更新
	public MfActivityEnroll queryById(String id);//通过活动报名表主键值查询活动报名表信息
	public List<MfActivityEnroll> queryByExample(MfActivityEnroll t);//通过活动报名表vo参数查询活动报名表列表
	public List<MfActivityEnroll> queryByExamplePageable(MfActivityEnroll t,PageRequest pageRequest);
	public void batchAdd(List<MfActivityEnroll> ts);//批量添加活动报名表
	public void batchDelete(List<MfActivityEnroll> ts);//批量删除活动报名表
	public Result queryDynamicSpecification(MfActivityEnrollDynamicQueryVo activityEnrollVo);//动态查询活动报名表
	public Integer countByActivityId(String id);
	public Integer queryBaoming(String userId, String activityId);//根据用户ID和活动ID查询该用户是否报名
	public MfActivityEnroll queryInformation(MfActivityEnroll activityEnroll);//获取该用户报名参加该活动的ID
	public Integer queryIshavingjoin(String id, String activityId);//判断该用户是否取消过此活动的报名	
	/**
	 * 根据用户Id查询活动Id 
	 * @param userId
	 * @param id String 唯一标识
	 * @param personid String 用户唯一标识
	 * @return rtobj BaseReturn 基本返回对象
	 * @变更记录 2017年10月17日 下午2:11:22 wenxun创建
	 */
	public List<String> queryByUserId(String userId);
	
	/**
	 * 根据活动ID 查询活动记录
	 * @return
	 */
	public List<MfActivityEnroll> queryActivityEnrollByActivityId(String id);
	/**
	 * 根据活动ID 用户ID 查询活动记录
	 * @return
	 */
	public MfActivityEnroll findActivityEnrollByUidAndAid(String activityId,String userId);
	
}
