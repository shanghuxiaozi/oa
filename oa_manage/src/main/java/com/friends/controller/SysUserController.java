package com.friends.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.friends.entity.SysRole;
import com.friends.entity.SysUser;
import com.friends.entity.SysUserRole;
import com.friends.entity.vo.SysUserVo;
import com.friends.service.SysRoleService;
import com.friends.service.SysUserRoleService;
import com.friends.service.SysUserService;
import com.friends.utils.PasswordHelper;
import com.friends.utils.Result;

/**
 * 添加角色
 * 
 * @author lzl
 *
 */
@Controller
@RequestMapping(value = "sysUser")
public class SysUserController {
	private static Logger logger = LoggerFactory.getLogger(SysUserController.class);
	@Autowired
	SysUserService sysUserService;
	@Autowired
	SysRoleService sysRoleService;
	@Autowired
	private SysUserRoleService SysUserRoleService;

	/**
	 * 进入添加角色页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView page(ModelAndView model) {
		logger.info("进入用户管理页面");
		model.setViewName("system/userList");
		return model;
	}

	// 查询 包括动态结构查询 跟选择的约束方式有关
	// 建议不管有没有约束，都统一使用此查询方式进行查询
	// 范围约束如何传递参数？单独的参数 属性名称Min 属性名称Max 对于这个接口service和dao有对应关系
	@RequestMapping(value = "queryDynamic", method = RequestMethod.POST)
	@ResponseBody
	public Result queryDynamic(String queryRestraintJson) {
		SysUserVo userInfoDynamicQueryVo = JSON.parseObject(queryRestraintJson, SysUserVo.class);
		Result mapResult = sysUserService.queryDynamic(userInfoDynamicQueryVo);
		return new Result(200, "查询成功", mapResult.getData(), mapResult.getTotal());
	}

	/**
	 * 初始进入添加角色页面
	 * 
	 * @return 添加页面
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add(ModelAndView model) {
		model.setViewName("system/userAdd");
		String rolestr = "";
		List<SysRole> roles = sysRoleService.queryByExample(new SysRole());
		for (int i = 0; i < roles.size(); i++) {
			SysRole role = roles.get(i);
			rolestr += "<input type=\"checkbox\" name=\"role\"  title=\"" + role.getName() + "\" value=\""
					+ role.getId() + "\" id=\"" + role.getId() + "\"/>";
		}
		model.addObject("rolestr", rolestr);
		return model;
	}

	/**
	 * 根据用户template数据添加用户信息
	 * 
	 * @param template
	 * @return Result
	 */

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(SysUser userInfo, String[] role) {
		logger.info("add");
		List<SysUserRole> list = new ArrayList<SysUserRole>();
		PasswordHelper helper = new PasswordHelper();
		helper.encryptPassword(userInfo);
		userInfo.setCreateDate(new Date());
		userInfo = sysUserService.add(userInfo);
		if (role != null && role.length > 0) {
			for (String s : role) {
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setUserId(userInfo.getId());
				sysUserRole.setRoleId(s);
				list.add(sysUserRole);
			}
		}
		SysUserRoleService.batchAdd(list);
		return new Result(200, "新增成功");
	}

	/**
	 * 初始进入用户修改页面
	 * 
	 * @param id
	 *            用户id
	 * @return 进入页面
	 */

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(ModelAndView model, String id) {
		model.setViewName("system/userEdit");
		String rolestr = "";
		SysUser userInfo = sysUserService.queryById(id);
		if (userInfo != null) {
			List<SysRole> rolelist = sysRoleService.queryByUserId(userInfo.getId());
			// System.out.println(rolelist.size());
			List<SysRole> roles = sysRoleService.queryByExample(new SysRole());

			for (int i = 0; i < roles.size(); i++) {
				SysRole role = roles.get(i);
				if (rolelist.contains(role)) {
					rolestr += "<input type=\"checkbox\" name=\"role\"  title=\"" + role.getName() + "\" value=\""
							+ role.getId() + "\" id=\"" + role.getId() + "\" checked />";
				} else {
					rolestr += "<input type=\"checkbox\" name=\"role\"  title=\"" + role.getName() + "\" value=\""
							+ role.getId() + "\" id=\"" + role.getId() + "\"/>";
				}
			}
			model.addObject("rolestr", rolestr);
		}
		model.addObject("sysUser", userInfo);
		model.addObject("id", id);
		return model;
	}

	/**
	 * 根据用户template数据修改用户信息
	 * 
	 * @param template
	 *            用户信息
	 * @return Result
	 */
	@RequestMapping(value = "update", method = RequestMethod.PUT)
	@ResponseBody
	public Result update(SysUser sysUser, String[] role) {
		logger.info("update");
		List<SysUserRole> list = new ArrayList<SysUserRole>();
		// 加入自定义参数校验，后期将直接支持JSR303
		PasswordHelper helper = new PasswordHelper();
		helper.encryptPassword(sysUser);
		sysUser.setUpdateDate(new Date());
		sysUserService.updateSelective(sysUser);
		if (role != null) {
			for (String s : role) {
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setUserId(sysUser.getId());
				sysUserRole.setRoleId(s);
				list.add(sysUserRole);
			}
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId(sysUser.getId());
			List<SysUserRole> lists = SysUserRoleService.queryByExample(userRole);
			SysUserRoleService.batchDelete(lists);
			SysUserRoleService.batchAdd(list);
		} else {
			SysUserRole userRole = new SysUserRole();
			userRole.setUserId(sysUser.getId());
			List<SysUserRole> lists = SysUserRoleService.queryByExample(userRole);
			SysUserRoleService.batchDelete(lists);
		}
		return new Result(200, "更新成功");
	}

	/**
	 * 根据用户template数据删除用户
	 * 
	 * @param ids
	 *            用户id集合
	 * @return Result
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Result delete(String[] ids) {
		List<SysUser> listUser = new ArrayList<SysUser>();
		List<SysUserRole> listUserRole = new ArrayList<SysUserRole>();
		for (String id : ids) {
			SysUser sysUser = new SysUser();
			sysUser.setId(id);
			listUser.add(sysUser);
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setUserId(id);
			listUserRole.addAll(SysUserRoleService.queryByExample(sysUserRole));
		}
		sysUserService.batchDelete(listUser);
		SysUserRoleService.batchDelete(listUserRole);
		return new Result(200, "删除成功");
	}

	/**
	 * 根据用户id获取用户详情
	 * 
	 * @param model
	 *            ModelAndView
	 * @param id
	 *            用户id
	 * @return 用户详情页面
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public ModelAndView detail(ModelAndView model, String id) {
		model.setViewName("system/userEdit");
		String rolestr = "";
		SysUser userInfo = sysUserService.queryById(id);
		if (userInfo != null) {
			List<SysRole> rolelist = sysRoleService.queryByUserId(userInfo.getId());
			// System.out.println(rolelist.size());
			List<SysRole> roles = sysRoleService.queryByExample(new SysRole());

			for (int i = 0; i < roles.size(); i++) {
				SysRole role = roles.get(i);
				if (rolelist.contains(role)) {
					rolestr += "<input type=\"checkbox\" name=\"role\"  title=\"" + role.getName() + "\" value=\""
							+ role.getId() + "\" id=\"" + role.getId() + "\" checked />";
				} else {
					rolestr += "<input type=\"checkbox\" name=\"role\"  title=\"" + role.getName() + "\" value=\""
							+ role.getId() + "\" id=\"" + role.getId() + "\"/>";
				}
			}
			model.addObject("rolestr", rolestr);
		}
		model.addObject("sysUser", userInfo);
		model.addObject("detail", "detail");// 和修改是同一个页面，这个detail用来控制详情数据展示,不能编辑
		return model;
	}

	@RequestMapping(value = "validateUserName", method = RequestMethod.POST)
	@ResponseBody
	public Result validateUserName(String userName) {
		int count = sysUserService.countByUserName(userName);
		if (count > 0) {
			return new Result(300, "验证成功", count);
		} else {
			return new Result(200, "验证成功", count);
		}
	}

}
