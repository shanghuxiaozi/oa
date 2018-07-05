package com.friends.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.friends.entity.MfActivityIntegral;
import com.friends.entity.MfIntegralDetails;
import com.friends.entity.MfMessagePush;
import com.friends.entity.MfUserInfo;
import com.friends.entity.MfUserTipoff;
import com.friends.service.MfMessagePushService;
import com.friends.service.MfUserTipoffService;
import com.friends.utils.Result;
import com.friends.utils.enums.MessagePushEnum;
import com.friends.utils.enums.tipOffEnum;

/**
 * 站内信管理
 * @author wenxun
 * @Date: 2018/4/16 13:49
 * @Description：
 */
@Controller
@RequestMapping("/mail")
public class MailController {
	private static Logger logger = LoggerFactory.getLogger(MailController.class);
	
	@Autowired
	private MfUserTipoffService mfUserTipoffService;
	
	@Autowired
	private MfMessagePushService messagePushService;
	
	@RequestMapping("/toPage")
	public ModelAndView toPage(ModelAndView model){
		model.setViewName("manage/message/mail");
		return model;
	}
	
	/**
	 * 平台<br>
	 * 
	 * @param id<br>
	 * @param content<br>
	 * @param 唯一标识<br>
	 * @param 用户唯一标识<br>
	 * @return BaseReturn 基本返回对象<br>
	 * @变更记录 2018年1月5日 上午11:44:43 songqi创建
	 */
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	@ResponseBody
	public Result sendOne(String userId,String content,String messageType){
		
		// 发送站内信点对点给 平台的人，消息支持前端可编辑.
		MfMessagePush push = new MfMessagePush();
		push.setCreatTime(new Date());
		push.setIsNewuserTips(MessagePushEnum.NO.getCode());
		push.setMessageContent(content);
		push.setMessageType(messageType);
		push.setType(MessagePushEnum.UNREAD.getCode());
		push.setUserId(userId);
		messagePushService.add(push);
		
		return new Result(200,"处理成功");
	}
	
	/**
	 * 平台<br>
	 * 
	 * @param id<br>
	 * @param content<br>
	 * @param 唯一标识<br>
	 * @param 用户唯一标识<br>
	 * @return BaseReturn 基本返回对象<br>
	 * @变更记录 2018年1月5日 上午11:44:43 songqi创建
	 */
	@RequestMapping(value = "/sendSome", method = RequestMethod.POST)
	@ResponseBody
	public Result sendSome(String json){
		List<MfMessagePush> list = JSON.parseArray(json,MfMessagePush.class);
		messagePushService.batchAdd(list);
		return new Result(200,"处理成功");
	}
	
	
	/**
	 * 平台<br>
	 * 
	 * @param id<br>
	 * @param content<br>
	 * @param 唯一标识<br>
	 * @param 用户唯一标识<br>
	 * @return BaseReturn 基本返回对象<br>
	 * @变更记录 2018年1月5日 上午11:44:43 songqi创建
	 */
	@RequestMapping(value = "/updateOne", method = RequestMethod.POST)
	@ResponseBody
	public Result updateOne(String id,String isNewuserTips){
		
		// 发送站内信点对点给 平台的人，消息支持前端可编辑.
		MfMessagePush push = messagePushService.queryById(id);
		push.setCreatTime(new Date());
		push.setIsNewuserTips(isNewuserTips);
		messagePushService.update(push);
		
		return new Result(200,"更新成功");
	}
	
	/**
	 * 查询非用户须知站内信
	 * 
	 */
	@RequestMapping(value = "/querySystemMessage", method = RequestMethod.POST)
	@ResponseBody
	public Result querySystemMessage(Integer page, Integer pageSize,String userId,String messageType) {
		MfMessagePush t = new MfMessagePush();
		t.setUserId(userId);
		t.setMessageType(messageType);
		PageRequest pageRequest = new PageRequest(page-1,pageSize);
		List<MfMessagePush> SystemMessage = messagePushService.queryByExamplePageable(t,pageRequest);
		if (SystemMessage != null && SystemMessage.size() > 0) {
			return new Result(200, "查询成功", SystemMessage);
		} else {
			return new Result(200, "没有数据");
		}

	}
	
}
