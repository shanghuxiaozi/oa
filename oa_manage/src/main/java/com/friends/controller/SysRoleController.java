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
import com.friends.entity.SysPermission;
import com.friends.entity.SysRole;
import com.friends.entity.SysRolePermission;
import com.friends.entity.vo.SysRoleVo;
import com.friends.service.SysPermissionService;
import com.friends.service.SysRolePermissionService;
import com.friends.service.SysRoleService;
import com.friends.utils.Result;

/**
 * 角色管理
 * 
 * @author lzl
 *
 */
@Controller
@RequestMapping(value = "sysRole")
public class SysRoleController {
	private static Logger logger = LoggerFactory.getLogger(SysRoleController.class);
	@Autowired
	SysRoleService sysRoleService;
	@Autowired
	SysRolePermissionService sysRolePermissionService;
	@Autowired
	SysPermissionService sysPermissionService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView page() {
		return new ModelAndView("system/roleList");
	}

	// 查询 包括动态结构查询 跟选择的约束方式有关
	// 建议不管有没有约束，都统一使用此查询方式进行查询
	// 范围约束如何传递参数？单独的参数 属性名称Min 属性名称Max 对于这个接口service和dao有对应关系
	@RequestMapping(value = "queryDynamic", method = RequestMethod.POST)
	@ResponseBody
	public Result queryDynamic(String queryRestraintJson) {
		SysRoleVo sysRoleVo = JSON.parseObject(queryRestraintJson, SysRoleVo.class);
		Result Result = sysRoleService.queryDynamic(sysRoleVo);
		return new Result(200, "查询成功", Result.getData(), Result.getTotal());
	}

	/**
	 * 初始进入角色添加页面
	 * 
	 * @return 添加页面
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add(ModelAndView model) {
		model.setViewName("system/roleAdd");
		return model;
	}

	// 单个新增
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Result add(SysRole sysRole, String[] menuIdList) {
		List<SysRolePermission> list = new ArrayList<SysRolePermission>();
		sysRole.setCreateDate(new Date());
		sysRole = sysRoleService.add(sysRole);
		if (menuIdList != null && menuIdList.length > 0) {
			for (String s : menuIdList) {
				SysRolePermission sp = new SysRolePermission();
				sp.setRoleId(sysRole.getId());
				sp.setPermissionId(s);
				list.add(sp);
			}
			sysRolePermissionService.batchAdd(list);
		}
		return new Result(200, "新增成功");
	}

	// 进入修改页面
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView initInfo(ModelAndView model, String id) {
		logger.info("进入修改页面");
		model.setViewName("system/roleEdit");
		SysRole sysRole = sysRoleService.queryById(id);
		model.addObject("role", sysRole);
		return model;
	}

	// 修改角色
	@RequestMapping(value = "update", method = RequestMethod.PUT)
	@ResponseBody
	public Result update(SysRole sysRole, String[] menuIdList) {
		List<SysRolePermission> list = new ArrayList<SysRolePermission>();
		sysRole.setUpdateDate(new Date());
		sysRoleService.updateSelective(sysRole);
		if (menuIdList != null && menuIdList.length > 0) {
			for (String s : menuIdList) {
				SysRolePermission srp = new SysRolePermission();
				srp.setRoleId(sysRole.getId());
				srp.setPermissionId(s);
				list.add(srp);
			}
			SysRolePermission sysRolePermission = new SysRolePermission();
			sysRolePermission.setRoleId(sysRole.getId());
			List<SysRolePermission> lists = sysRolePermissionService.queryByExample(sysRolePermission);
			sysRolePermissionService.batchDelete(lists);
			sysRolePermissionService.batchAdd(list);
		} else {
			SysRolePermission sysRolePermission = new SysRolePermission();
			sysRolePermission.setRoleId(sysRole.getId());
			List<SysRolePermission> lists = sysRolePermissionService.queryByExample(sysRolePermission);
			sysRolePermissionService.batchDelete(lists);
		}
		return new Result(200, "更新成功");
	}

	// 批量删除
	// 数组参数以json方式传递。
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Result batchDelete(String[] ids) {
		List<SysRole> listRole = new ArrayList<SysRole>();
		List<SysRolePermission> listRolePermission = new ArrayList<SysRolePermission>();
		for (String id : ids) {
			Integer count = sysRolePermissionService.queryCountById(id);
			if (count > 0) {
				SysRole sysRole = sysRoleService.queryById(id);
				return new Result(300, "'" + sysRole.getName() + "'" + "关联了用户，不能删除！");
			}
			SysRole sysRole = new SysRole();
			sysRole.setId(id);
			listRole.add(sysRole);
			SysRolePermission sysRolePermission = new SysRolePermission();
			sysRolePermission.setRoleId(id);
			listRolePermission.addAll(sysRolePermissionService.queryByExample(sysRolePermission));
		}
		sysRoleService.batchDelete(listRole);// 删除角色
		sysRolePermissionService.batchDelete(listRolePermission);// 删除关联关系
		return new Result(200, "更新成功");
	}

	/**
	 * 根据角色id获取角色详情
	 * 
	 * @param model
	 *            ModelAndView
	 * @param id
	 *            角色id
	 * @return 角色详情页面
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public ModelAndView detail(ModelAndView model, String id) {
		logger.info("进入增加修改页面");
		model.setViewName("system/roleEdit");
		SysRole sysRole = sysRoleService.queryById(id);
		model.addObject("role", sysRole);
		model.addObject("detail", "detail");// 和修改是同一个页面，这个detail用来控制详情数据展示,不能编辑
		return model;
	}

	@RequestMapping(value = "queryIds", method = RequestMethod.POST)
	@ResponseBody
	public Result queryIds(String roleId) {
		System.out.println("============");
		List<SysRolePermission> listIds = sysRolePermissionService.queryPermIds(roleId);
		return new Result(200, "查询成功", listIds, listIds.size());
	}

	@RequestMapping(value = "validateRole", method = RequestMethod.POST)
	@ResponseBody
	public Result validateRole(String role) {
		int count = sysRoleService.countByName(role);
		if (count > 0) {
			return new Result(300, "验证成功", count);
		} else {
			return new Result(200, "验证成功", count);
		}
	}

	/**
	 * 查询根目录包括按钮的菜单数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "menu", method = RequestMethod.POST)
	@ResponseBody
	public Result queryMenus() {
		List<SysPermission> sysPermissions = sysPermissionService.queryRoleMenu();
		return new Result(200, "查询成功", sysPermissions);
	}

}
