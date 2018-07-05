package com.friends.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.friends.entity.MfModuleTimes;
import com.friends.entity.MfSpotPush;
import com.friends.entity.SysUser;
import com.friends.service.MfModuleTimesService;
import com.friends.service.MfSpotPushService;
import com.friends.utils.Result;

/**
 * 热点和模块控制器 controller <br>
 * 名称：MfSpotAndModuleController.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2018年1月3日 上午10:06:30 <br>
 * @since  2018年1月3日
 * @authour 宋琪
 */
@Controller
@RequestMapping(value = "spotpush")
public class MfSpotAndModuleController {
	private static Logger logger = LoggerFactory.getLogger(MfSpotAndModuleController.class);
	
	@Autowired
	MfSpotPushService mfSpotPushService;
	
	@Autowired
	MfModuleTimesService mfModuleTimesService;
	
	//单个新增热点推送
	@RequestMapping(value = "addSpot")
	@ResponseBody
	public Result addSpot(String json) {
		logger.info("json:"+json);
		Session session = SecurityUtils.getSubject().getSession();
		SysUser userinfo = (SysUser) session.getAttribute("sysUser");
		if (userinfo == null) {
			return new Result(201, "未登录");
		}
		MfSpotPush mfSpotPush= JSON.parseObject(json, MfSpotPush.class);
		mfSpotPush.setUserId(userinfo.getId());
		mfSpotPush = mfSpotPushService.add(mfSpotPush);
		return new Result(200, "新增成功");
	}
	
	//单个新增模块计数
	@RequestMapping(value = "addModule")
	@ResponseBody
	public Result addModule(String json) {
		logger.info("json:"+json);
		MfModuleTimes mfModuleTimes= JSON.parseObject(json, MfModuleTimes.class);
		mfModuleTimes = mfModuleTimesService.add(mfModuleTimes);
		return new Result(200, "新增成功");
	}
	
	//单个更新热点推送
	@RequestMapping(value = "updateSpot")
	@ResponseBody
	public Result updateSpot(String json) {
		Session session = SecurityUtils.getSubject().getSession();
		SysUser userinfo = (SysUser) session.getAttribute("sysUser");
		if (userinfo == null) {
			return new Result(201, "未登录");
		}
		MfSpotPush mfSpotPush = JSON.parseObject(json,MfSpotPush.class);
		mfSpotPush.setUserId(userinfo.getId());
		// 加入自定义参数校验，后期将直接支持JSR303
		mfSpotPushService.update(mfSpotPush);
		return new Result(200, "更新成功");
	}

	//单个更新模块计数
	@RequestMapping(value = "updateModule")
	@ResponseBody
	public Result updateModule(String json) {
		MfModuleTimes mfModuleTimes = JSON.parseObject(json,MfModuleTimes.class);
		// 加入自定义参数校验，后期将直接支持JSR303
		mfModuleTimesService.update(mfModuleTimes);
		return new Result(200, "更新成功");
	}
	
	// 单个删除热点推送
	@RequestMapping(value = "deleteSpot")
	@ResponseBody
	public Result deleteSpot(String id) {
		// 加入自定义参数校验，后期将直接支持JSR303
		mfSpotPushService.delete(id);
		return new Result(200, "删除成功");
	}
	
	// 单个删除模块计数
	@RequestMapping(value = "deleteModule")
	@ResponseBody
	public Result deleteModule(String id) {
		// 加入自定义参数校验，后期将直接支持JSR303
		mfModuleTimesService.delete(id);
		return new Result(200, "删除成功");
	}
	

	// 查询热点推送
	@RequestMapping(value = "querySpot")
	@ResponseBody
	public Result querySpot(String json) {
		Session session = SecurityUtils.getSubject().getSession();
		SysUser userinfo = (SysUser) session.getAttribute("sysUser");
		if (userinfo == null) {
			return new Result(201, "未登录");
		}
		MfSpotPush mfSpotPush = JSON.parseObject(json,MfSpotPush.class);
		mfSpotPush.setUserId(userinfo.getId());
		// 加入自定义参数校验，后期将直接支持JSR303
		List<MfSpotPush> list = mfSpotPushService.queryByExample(mfSpotPush);
		Result mapResult = new Result(list);
		return new Result(200, "查询成功",mapResult.getData());
	}
	
	

	// 查询模块计数
	@RequestMapping(value = "queryModule")
	@ResponseBody
	public Result queryModule(String json) {
		MfModuleTimes mfModuleTimes = JSON.parseObject(json,MfModuleTimes.class);
		// 加入自定义参数校验，后期将直接支持JSR303
		List<MfModuleTimes> list = mfModuleTimesService.queryByExample(mfModuleTimes);
		Result mapResult = new Result(list);
		return new Result(200, "查询成功",mapResult.getData());
	}
}
