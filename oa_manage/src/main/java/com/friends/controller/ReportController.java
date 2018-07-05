package com.friends.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.friends.entity.MfMessagePush;
import com.friends.entity.MfUserInfo;
import com.friends.entity.MfActivityIntegral;
import com.friends.entity.MfIntegralDetails;
import com.friends.entity.MfInvitationUserData;
import com.friends.entity.MfUserTipoff;
import com.friends.entity.vo.MfUserTipoffDynamicQueryVo;
import com.friends.service.MfMessagePushService;
import com.friends.service.MfActivityIntegralService;
import com.friends.service.MfIntegralDetailsService;
import com.friends.service.MfInvitationUserDataService;
import com.friends.service.MfUserInfoService;
import com.friends.service.MfUserTipoffService;
import com.friends.utils.Result;
import com.friends.utils.enums.MessagePushEnum;
import com.friends.utils.enums.UserTypeEnum;
import com.friends.utils.enums.tipOffEnum;

/**
 * 举报,投诉 controller <br>
 * 名称：ReportController.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2017年11月9日 上午10:05:49 <br>
 * 
 * @since 2017年11月9日
 * @authour wenxun
 */
@Controller
@RequestMapping(value = "report")
public class ReportController {

	private static Logger logger = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	private MfUserTipoffService mfUserTipoffService;
	@Autowired
	private MfActivityIntegralService mfActivityIntegralService;
	@Autowired
	private MfIntegralDetailsService mfIntegralDetailsService;
	@Autowired
	private MfMessagePushService messagePushService;
	@Autowired
	private MfUserInfoService sysUserinfoService;
	@Autowired
	private MfInvitationUserDataService mfInvitationUserDataService;

	/**
	 * 进入投诉列表页面
	 * 
	 * @return 列表页面
	 */
	@RequestMapping(value = "reportList", method = RequestMethod.GET)
	public ModelAndView page() {
		return new ModelAndView("report/reportList");
	}
	
	/**
	 * 进入平台留言列表页面
	 * 
	 * @return 列表页面
	 */
	@RequestMapping(value = "platformList", method = RequestMethod.GET)
	public ModelAndView page2() {
		return new ModelAndView("report/platform_msg");
	}

	/**
	 * 根据模板相关条件queryRestraintJson查询模板数据
	 * 
	 * @param queryRestraintJson
	 * @return 模板列表
	 */
	@RequestMapping(value = "queryDynamic", method = RequestMethod.POST)
	@ResponseBody
	public Result queryDynamic(Integer page, Integer pageSize, String queryRestraintJson) {
		MfUserTipoffDynamicQueryVo tipOffDynamicQueryVo = JSON.parseObject(queryRestraintJson,
				MfUserTipoffDynamicQueryVo.class);
		tipOffDynamicQueryVo.setPage(page - 1);
		tipOffDynamicQueryVo.setPageSize(pageSize);
		Result result = mfUserTipoffService.queryDynamic(tipOffDynamicQueryVo);
		return new Result(200, "查询成功", result.getData(), result.getTotal());
	}

	/**
	 * 举报处理结果，举报通过 <br>
	 * 
	 * @param id
	 * @param 唯一标识
	 * @param 用户唯一标识
	 * @return BaseReturn 基本返回对象
	 * @变更记录 2017年11月9日 下午2:27:41 wenxun创建
	 */
	@RequestMapping(value = "auditPassed", method = RequestMethod.POST)
	@ResponseBody
	public Result auditPassed(String id, Integer processingType,String complaintMsg,String beComplainsMsg) {
		// 1根据ID 找到投诉记录实体
		MfUserTipoff tipOff = mfUserTipoffService.queryById(id);
		// 2判断管理员选择的处理类型   1积分增加或减少  2封号
		if(processingType == 1){
			// 发送消息给 投诉人，消息支持前端可编辑.
			MfMessagePush push = new MfMessagePush();
			push.setCreatTime(new Date());
			push.setIsNewuserTips(MessagePushEnum.NO.getCode());
			push.setMessageContent(complaintMsg);
			push.setMessageType(MessagePushEnum.SYSTEM_MESSAGE.getCode());
			push.setType(MessagePushEnum.UNREAD.getCode());
			push.setUserId(tipOff.getTipOffUserId());
			messagePushService.add(push);
			// 查询投诉人的 积分实体类  并 +10积分 .
			MfActivityIntegral integral = mfActivityIntegralService.findIntegralByuId(tipOff.getTipOffUserId());
			integral.setIntegral(integral.getIntegral() + 10);
			mfActivityIntegralService.add(integral);
			// 将投诉人 增加的积分记录 保存下来;
			MfIntegralDetails integralDetails = new MfIntegralDetails();
			integralDetails.setActivityId(null);
			integralDetails.setCreationTime(new Date());
			integralDetails.setGainIntegral(10);
			integralDetails.setRemarks("投诉用户被受理，增加10积分。");
			integralDetails.setUserId(tipOff.getTipOffUserId());
			mfIntegralDetailsService.add(integralDetails);
			// 发送消息给被投诉人 消息支持前端可编辑.
			MfMessagePush push2 = new MfMessagePush();
			push2.setCreatTime(new Date());
			push2.setIsNewuserTips(MessagePushEnum.NO.getCode());
			push2.setMessageContent(beComplainsMsg);
			push2.setMessageType(MessagePushEnum.SYSTEM_MESSAGE.getCode());
			push2.setType(MessagePushEnum.UNREAD.getCode());
			push2.setUserId(tipOff.getByTipOffUserId());
			messagePushService.add(push2);
			// 查询 被投诉人的 积分实体类  并 -10积分 .
			MfActivityIntegral integral2 = mfActivityIntegralService.findIntegralByuId(tipOff.getByTipOffUserId());
			integral2.setIntegral((integral2.getIntegral() - 10));
			mfActivityIntegralService.add(integral2);
			// 将被投诉人 减少的积分记录 保存下来;
			MfIntegralDetails integralDetails2 = new MfIntegralDetails();
			integralDetails2.setActivityId(null);
			integralDetails2.setCreationTime(new Date());
			integralDetails2.setGainIntegral(-10);
			integralDetails2.setRemarks("被用户投诉，减少10积分。");
			integralDetails2.setUserId(tipOff.getByTipOffUserId());
			mfIntegralDetailsService.add(integralDetails2);

			//最后，把这条投诉记录  改为已处理状态。
			tipOff.setStatus(tipOffEnum.ALREADY_PROCESSED.getCode());
			mfUserTipoffService.add(tipOff);

		}else{
			// 封号 
			MfUserInfo userInfo = sysUserinfoService.queryById(tipOff.getByTipOffUserId());
			userInfo.setUserType(UserTypeEnum.CLOSE_USER.getCode());
			userInfo.setStatus(UserTypeEnum.CLOSE.getCode());
			sysUserinfoService.update(userInfo);
			// 把受邀用户给删除  这样他就不能注册了。
			// 不能删
		}
		return new Result(200, "处理成功!");
	}

	
	/**
	 * 给平台留言的人 +积分<br>
	 * 
	 * @param id<br>
	 * @param content<br>
	 * @param 唯一标识<br>
	 * @param 用户唯一标识<br>
	 * @return BaseReturn 基本返回对象<br>
	 * @变更记录 2018年1月5日 上午11:44:43 wenxun创建
	 */
	@RequestMapping(value = "plusIntegral", method = RequestMethod.POST)
	@ResponseBody
	public Result plusIntegral(String id,String content){
		// 1根据ID查询出 平台留言实体，并将实体状态改为已处理。
		MfUserTipoff tipOff = mfUserTipoffService.queryById(id);
		tipOff.setStatus(tipOffEnum.ALREADY_PROCESSED.getCode());
		mfUserTipoffService.add(tipOff);
		// 2发送消息给 平台留言的人，消息支持前端可编辑.
		MfMessagePush push = new MfMessagePush();
		push.setCreatTime(new Date());
		push.setIsNewuserTips(MessagePushEnum.NO.getCode());
		push.setMessageContent(content);
		push.setMessageType(MessagePushEnum.SYSTEM_MESSAGE.getCode());
		push.setType(MessagePushEnum.UNREAD.getCode());
		push.setUserId(tipOff.getTipOffUserId());
		messagePushService.add(push);
		// 查询出 平台留言人的 积分实体类  并+10积分 .
		MfActivityIntegral integral = mfActivityIntegralService.findIntegralByuId(tipOff.getTipOffUserId());
		integral.setIntegral(integral.getIntegral() + 10);
		mfActivityIntegralService.add(integral);
		// 积分详情表  记录下积分来源 
		MfIntegralDetails integralDetails = new MfIntegralDetails();
		integralDetails.setCreationTime(new Date());
		integralDetails.setGainIntegral(10);
		integralDetails.setRemarks("该用户积极给平台反馈意见或建议，被采纳，加10积分。");
		integralDetails.setUserId(tipOff.getTipOffUserId());
		mfIntegralDetailsService.add(integralDetails);
		return new Result(200,"处理成功");
	}
	
	
	/**
	 * 驳回举报的内容<br>
	 * 
	 * @param
	 * @param 唯一标识
	 * @param 用户唯一标识
	 * @return BaseReturn 基本返回对象
	 * @变更记录 2017年11月9日 下午4:20:23 wenxun创建
	 */
	@RequestMapping(value = "reject", method = RequestMethod.POST)
	@ResponseBody
	public Result reject(String id, String status) {
		// 对于驳回的举报 只将举报状态改为 已处理就好了
		if (tipOffEnum.ALREADY_PROCESSED.getCode().equals(status)) {
			return new Result(400, "审核过的数据");
		}
		MfUserTipoff tipOff = mfUserTipoffService.queryById(id);
		tipOff.setStatus(tipOffEnum.ALREADY_PROCESSED.getCode());
		mfUserTipoffService.add(tipOff);
		return new Result(200, "处理成功");
	}
	
	/**
	 * 删除投诉 或 平台留言记录<br>
	 * @param id
	 * @param id 唯一标识<br>
	 * @param 用户唯一标识<br>
	 * @return BaseReturn 基本返回对象<br>
	 * @变更记录 2018年1月5日 下午2:18:24 wenxun创建
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public Result delete(String id){
		MfUserTipoff userTipoff = mfUserTipoffService.queryById(id);
		if(null != userTipoff){
			mfUserTipoffService.delete(userTipoff);
			return new Result(200, "删除成功");
		}
		return new Result(400,"删除失败，该数据可不存在.");
	}
	

}