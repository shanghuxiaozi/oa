package com.friends.controller;

import java.util.List;

import com.friends.utils.enums.UserTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.friends.entity.MfUserInfo;
import com.friends.entity.vo.MfUserInfoDynamicQueryVo;
import com.friends.service.MfUserInfoService;
import com.friends.utils.Result;

/**
 * controller该用户模块管理前台用户！ 用户管理 名称：SysUserinfoController.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2017年10月16日 下午4:37:55 <br>
 * 
 * @since 2017年10月16日
 * @authour wenxun
 */
@Controller
@RequestMapping(value = "sysUserinfo")
public class SysUserinfoController {

	private static Logger logger = LoggerFactory.getLogger(SysUserinfoController.class);

	@Autowired
	MfUserInfoService mfUserInfoService;

	@RequestMapping(value = "page")
	public ModelAndView page() {
		return new ModelAndView("user/userList");
	}

	// 单个新增
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Result add(String json) {
		MfUserInfo sysUserinfo = JSON.parseObject(json, MfUserInfo.class);
		// 加入自定义参数校验，后期将直接支持JSR303
		sysUserinfo = mfUserInfoService.add(sysUserinfo);
		return new Result(200, "新增成功");
	}

	// 单个更新
	@RequestMapping(value = "update")
	@ResponseBody
	public Result update(MfUserInfo sysUserinfo) {
		// 加入自定义参数校验，后期将直接支持JSR303
		mfUserInfoService.update(sysUserinfo);
		return new Result(200, "更新成功");
	}

	// 单个删除
	@RequestMapping(value = "delete")
	@ResponseBody
	public Result delete(MfUserInfo sysUserinfo) {
		// 加入自定义参数校验，后期将直接支持JSR303
		mfUserInfoService.delete(sysUserinfo.getId());
		return new Result(200, "删除成功");
	}

	// 批量删除
	// 数组参数以json方式传递。
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public Result batchDelete(String sysUserinfoArrayJson) {
		List<MfUserInfo> sysUserinfos = JSON.parseArray(sysUserinfoArrayJson, MfUserInfo.class);
		// 加入自定义参数校验，后期将直接支持JSR303
		mfUserInfoService.batchDelete(sysUserinfos);
		return new Result(200, "批量删除成功");
	}

	// 查询 包括动态结构查询 跟选择的约束方式有关
	// 建议不管有没有约束，都统一使用此查询方式进行查询
	// 范围约束如何传递参数？单独的参数 属性名称Min 属性名称Max 对于这个接口service和dao有对应关系
	@RequestMapping(value = "queryDynamic")
	@ResponseBody
	public Result queryDynamic(Integer page, Integer pageSize, String queryRestraintJson) {
		MfUserInfoDynamicQueryVo sysUserinfoVo = JSON.parseObject(queryRestraintJson, MfUserInfoDynamicQueryVo.class);
		sysUserinfoVo.setPage(page - 1);
		sysUserinfoVo.setPageSize(pageSize);
		Result mapResult = mfUserInfoService.queryDynamic(sysUserinfoVo);
		return new Result(200, "查询成功", mapResult.getData(), mapResult.getTotal());
	}

	@RequestMapping(value = "queryDynamic2")
	@ResponseBody
	public Result queryDynamic2(Integer page, Integer pageSize, String queryRestraintJson) {
		MfUserInfoDynamicQueryVo sysUserinfoVo = JSON.parseObject(queryRestraintJson, MfUserInfoDynamicQueryVo.class);
		sysUserinfoVo.setPage(page - 1);
		sysUserinfoVo.setPageSize(pageSize);
		Result mapResult = mfUserInfoService.queryDynamic2(sysUserinfoVo);
		return new Result(200, "查询成功", mapResult.getData(), mapResult.getTotal());
	}

	/**
	*  锁定用户
	**/
	@RequestMapping(value = "LockUser")
	@ResponseBody
	public Result LockUser(String userId){
		MfUserInfo mfUserInfo = mfUserInfoService.queryById(userId);
		if(null != mfUserInfo){
			mfUserInfo.setUserType(UserTypeEnum.CLOSE_USER.getCode());
			mfUserInfo.setStatus(UserTypeEnum.CLOSE.getCode());
			mfUserInfoService.update(mfUserInfo);
			return new Result(200,"封号成功！");
		}
		return new Result(400,"用户不存在！");
	}

	/*
	*  解除锁定
	**/
	@RequestMapping(value = "unlock")
	@ResponseBody
	public Result unlock(String userId){
		MfUserInfo mfUserInfo = mfUserInfoService.queryById(userId);
		if(null != mfUserInfo){
			mfUserInfo.setUserType(UserTypeEnum.SINGLE_USER.getCode());
			mfUserInfo.setStatus(UserTypeEnum.PERFECT_DATA.getCode());
			mfUserInfoService.update(mfUserInfo);
			return new Result(200,"解封成功！");
		}
		return new Result(400,"用户不存在！");
	}

}
