package com.friends.service;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import com.friends.entity.MfUserDataDetails;
import com.friends.entity.vo.MfUserDataDetailsDynamicQueryVo;
import com.friends.entity.vo.MfUserInfoDynamicQueryVo;
import com.friends.utils.Result;


public interface MfUserDataDetailsService  {

	
	/**
	 * 通过用户信息表vo参数查询用户信息表列表
	 * @param userInformationVo 用户信息表vo
	 * @return Result(列表数据，分页数据，消息数据，以及)
	 */
	public Result queryDynamic(MfUserDataDetailsDynamicQueryVo userInformationVo);//通过用户信息表vo参数查询用户信息表列表
	public MfUserDataDetails add(MfUserDataDetails t);//用户信息表添加
	public void delete(String id);//用户信息表删除
	public MfUserDataDetails updateSelective(MfUserDataDetails t);//用户信息表更新
	public MfUserDataDetails update(MfUserDataDetails t);//用户信息表更新
	public MfUserDataDetails queryById(String id);//通过用户信息表主键值查询用户信息表信息
	public List<MfUserDataDetails> queryByExample(MfUserDataDetails t);//通过用户信息表vo参数查询用户信息表列表
	public List<MfUserDataDetails> queryByExamplePageable(MfUserDataDetails t,PageRequest pageRequest);
	public void batchAdd(List<MfUserDataDetails> ts);//批量添加用户信息表
	public void batchDelete(List<MfUserDataDetails> ts);//批量删除用户信息表
	public Result queryDynamicSpecification(MfUserDataDetailsDynamicQueryVo userInformationVo);//动态查询用户信息表
	public MfUserDataDetails findUserInformationByuId(String uId);	//根据用户id查用户信息表
	// 籍贯  房车  查询 
	public List<MfUserDataDetails> queryLaoxiang(String hometown,MfUserInfoDynamicQueryVo sysUserinfoVo);
	public Integer findInformationByuId(String id);//根据用户id查用户信息是否存在
	/**
	 * 根据择偶标准，查询匹配的用户
	 * @param mateStandard
	 * @param id String 唯一标识
	 * @param personid String 用户唯一标识
	 * @return rtobj BaseReturn 基本返回对象
	 * @变更记录 2017年11月6日 下午3:16:42 wenxun创建
	 */
	public Result queryByMateStandard(String sex,String[] mateStandard,PageRequest pageRequest);
	/**
	 * 随机推送，缘分信息. <br>
	 * @param pageRequest
	 * @param id String 唯一标识
	 * @param personid String 用户唯一标识
	 * @return rtobj BaseReturn 基本返回对象
	 * @变更记录 2017年11月8日 上午10:28:46 wenxun创建
	 */
	public Result queryByRandom(String sex,PageRequest pageRequest);
	/**
	 * 根据信誉积分找到 前10名的信息<br>
	 * @param pageRequest
	 * @param id String 唯一标识
	 * @param personid String 用户唯一标识
	 * @return rtobj BaseReturn 基本返回对象
	 * @变更记录 2017年11月8日 下午1:57:16 wenxun创建
	 */
	public Result queryByReputationIntegral(PageRequest pageRequest,String userId);
	
	/**
	 * 根据各种参数，查询
	 * @param userInformationVo
	 * @param pageRequest
	 * @param id String 唯一标识
	 * @param personid String 用户唯一标识
	 * @return rtobj BaseReturn 基本返回对象
	 * @变更记录 2017年11月13日 下午1:47:19 wenxun创建
	 */
	public Result queryByParam(MfUserDataDetailsDynamicQueryVo userInformationVo,PageRequest pageRequest);
	
	/**
	 * 根据家乡 查询同城老乡<br>
	 * @param hometown<br>
	 * @param pageRequest<br>
	 * @param id String 唯一标识<br>
	 * @param personid String 用户唯一标识<br>
	 * @return rtobj BaseReturn 基本返回对象<br>
	 * @变更记录 2017年12月25日 下午3:15:37 wenxun创建
	 */
	public Result queryBySameCity(String hometown,PageRequest pageRequest,String userId);

	/**
	 * 功能描述: 根据开始时间结束时间统计用户信息
	 * @param: [startTime, enTime]
	 * @return: com.friends.utils.Result
	 * @auther: wenxun
	 * @date: 2018/4/12 15:24
	 */
	public Result statisticsByTime(String startTime,String enTime);

}
