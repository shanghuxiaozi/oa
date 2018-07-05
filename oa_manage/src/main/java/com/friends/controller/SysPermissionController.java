package com.friends.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.friends.entity.SysPermission;
import com.friends.entity.vo.SysPermissionVo;
import com.friends.service.SysPermissionService;
import com.friends.service.SysRolePermissionService;
import com.friends.utils.Result;
import com.friends.utils.enums.PermEnum;

/**
 * 权限管理
 * 
 * @author lzl
 *
 */
@Controller
@RequestMapping(value = "sysPermission")
public class SysPermissionController {
	// private static Logger
	// logger=LoggerFactory.getLogger(SysPermissionController.class);
	@Autowired
	SysPermissionService sysPermissionService;
	@Autowired
	SysRolePermissionService sysRolePermissionService;

	/**
	 * 进入菜单列表
	 * 
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "menuList", method = RequestMethod.GET)
	public ModelAndView menuList(ModelAndView model, HttpSession httpSession) {
		List<SysPermission> sysPermissions = sysPermissionService.queryPermMenu(1);
		httpSession.setAttribute("sysPermissionlist", JSON.toJSONString(sysPermissions));
		model.addObject("sysPermissions", sysPermissions);
		model.setViewName("system/permMenuList");
		return model;
	}

	// 查询 包括动态结构查询 跟选择的约束方式有关
	// 建议不管有没有约束，都统一使用此查询方式进行查询
	// 范围约束如何传递参数？单独的参数 属性名称Min 属性名称Max 对于这个接口service和dao有对应关系
	@RequestMapping(value = "queryDynamic", method = RequestMethod.POST)
	@ResponseBody
	public Result queryDynamic(String queryRestraintJson) {
		SysPermissionVo SysPermissionVo = JSON.parseObject(queryRestraintJson, SysPermissionVo.class);
		Result Result = sysPermissionService.queryDynamic(SysPermissionVo);
		List<SysPermission> obj = (List<SysPermission>) Result.getData();
		for (SysPermission o : obj) {
			if (!StringUtils.isEmpty(o.getType())) {
				if ("1".equals(o.getType())) {
					o.setType(1);
				} else if ("2".equals(o.getType())) {
					o.setType(2);
				}
			}
			if (!StringUtils.isEmpty(o.getIconCode())) {
				o.setIconCode("<i class=\"layui-icon\">" + o.getIconCode() + "</i>");
			}
		}
		return new Result(200, "查询成功", obj, Result.getTotal());
	}

	/**
	 * 进入菜单添加页面
	 * 
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "menuAdd", method = RequestMethod.GET)
	public ModelAndView menuAdd(ModelAndView model, HttpSession httpSession, String id) {
		List<SysPermission> sysPermissions = sysPermissionService.queryPermMenu(1);
		httpSession.setAttribute("sysPermissionlist", JSON.toJSONString(sysPermissions));
		model.addObject("sysPermissions", sysPermissions);
		if (id != null) {
			SysPermission sysPermission = sysPermissionService.queryById(id);
			model.addObject("parentPerm", sysPermission);
		} else {
			SysPermission sysPermission = sysPermissionService.queryById("0");
			model.addObject("parentPerm", sysPermission);
		}
		model.setViewName("system/permMenuAdd");
		return model;
	}

	/**
	 * 保存菜单
	 * 
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "menuSave", method = RequestMethod.POST)
	@ResponseBody
	public Result menuSave(SysPermission sysPermission) {
		if (StringUtils.isEmpty(sysPermission.getPid())) {
			sysPermission.setPid("0");
		}
		sysPermission.setAvailable(true);
		sysPermissionService.add(sysPermission);
		List<SysPermission> sysPermissions = sysPermissionService.queryPermMenu(1);
		return new Result(200, "查询成功", sysPermissions);
	}

	/**
	 * 进入菜单修改页面
	 * 
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "menuEdit", method = RequestMethod.GET)
	public ModelAndView menuEdit(ModelAndView model, HttpSession httpSession, String id) {
		List<SysPermission> sysPermissions = sysPermissionService.queryPermMenu(1);
		httpSession.setAttribute("sysPermissionlist", JSON.toJSONString(sysPermissions));
		SysPermission sysPermission = sysPermissionService.queryById(id);
		model.addObject("perm", sysPermission);
		model.setViewName("system/permMenuEdit");
		return model;
	}

	/**
	 * 修改菜单
	 * 
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "menuUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Result menuUpdate(ModelAndView model, HttpSession httpSession, SysPermission sysPermission) {
		sysPermissionService.updateSelective(sysPermission);
		List<SysPermission> sysPermissions = sysPermissionService.queryPermMenu(1);
		return new Result(200, "查询成功", sysPermissions);
	}

	/**
	 * 进入按钮列表页面
	 * 
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "buttonList", method = RequestMethod.GET)
	public ModelAndView buttonList(ModelAndView model, HttpSession httpSession) {
		List<SysPermission> sysPermissions = sysPermissionService.queryPermMenu(1);
		httpSession.setAttribute("sysPermissionlist", JSON.toJSONString(sysPermissions));
		model.addObject("sysPermissions", sysPermissions);
		model.setViewName("system/permButtonList");
		return model;
	}

	/**
	 * 进入按钮添加页面
	 * 
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "buttonAdd", method = RequestMethod.GET)
	public ModelAndView buttonAdd(ModelAndView model, HttpSession httpSession, String id) {
		initPerm(model, id);
		SysPermission sysPermission = sysPermissionService.queryById(id);
		model.addObject("parentPerm", sysPermission);
		model.setViewName("system/permButtonAdd");
		return model;
	}

	/**
	 * 初始化增删改查权限
	 * 
	 * @return
	 */
	public void initPerm(ModelAndView model, String id) {
		StringBuffer sb = new StringBuffer();
		int count = sysPermissionService.queryPermCountByParentId(id);
		String ids = "";
		if (count > 0) {// 有按钮
			List<SysPermission> listperm = sysPermissionService.queryPermByParentId(id);
			for (SysPermission perm : listperm) {
				ids += perm.getId() + ",";
				if (perm.getAvailable() == true) {
					sb.append("<input type=\"checkbox\" name=\"perms\"  title=\"" + perm.getName() + "\" value=\""
							+ perm.getId() + "\"  checked  />");
				} else {
					sb.append("<input type=\"checkbox\" name=\"perms\"  title=\"" + perm.getName() + "\" value=\""
							+ perm.getId() + "\" />");
				}
			}
		} else {// 无按钮
			SysPermission sysPermission = sysPermissionService.queryById(id);
			String[] obj = { "add", "delete", "update", "query" };
			List<SysPermission> listperm = new ArrayList<SysPermission>();
			int s = 0;
			for (String o : obj) {
				SysPermission sp = new SysPermission();
				sp.setPermissionName(sysPermission.getPermissionName());
				sp.setPermissionValue(o);
				sp.setName(PermEnum.getEnum(o).getDesc());
				sp.setOrderNum(++s);
				sp.setPid(sysPermission.getId());
				sp.setPname(sysPermission.getName());
				sp.setType(2);
				sp.setAvailable(false);
				listperm.add(sp);

			}
			sysPermissionService.batchAdd(listperm);
			for (SysPermission perm : listperm) {
				sb.append("<input type=\"checkbox\" name=\"perms\"  title=\"" + perm.getName() + "\" value=\""
						+ perm.getId() + "\"  />");
				ids += perm.getId() + ",";
			}
		}
		model.addObject("ids", ids);
		model.addObject("permStr", sb);
	}

	/**
	 * 进入按钮添加页面
	 * 
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "buttonSave", method = RequestMethod.POST)
	@ResponseBody
	public Result buttonSave(SysPermission sysPermission, String[] perms, String ids) {
		String[] objs = ids.split(",");
		List<String> listId = Arrays.asList(objs);
		List<String> listIds = Arrays.asList(perms);
		if (perms != null && perms.length > 0) {
			for (String s : perms) {
				SysPermission perm = new SysPermission();
				perm.setId(s);
				perm.setAvailable(true);
				sysPermissionService.updateSelective(perm);
			}
			for (String s : listId) {
				if (!listIds.contains(s)) {
					SysPermission perm = new SysPermission();
					perm.setId(s);
					perm.setAvailable(false);
					sysPermissionService.updateSelective(perm);
				}
			}
			return new Result(200, "添加成功");
		} else {
			for (String id : listIds) {
				SysPermission perm = new SysPermission();
				perm.setId(id);
				perm.setAvailable(false);
				sysPermissionService.updateSelective(perm);
			}
			return new Result(200, "添加成功", sysPermission.getId().toString());
		}

	}

	/**
	 * 进入按钮添加页面
	 * 
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "buttonPermSave", method = RequestMethod.POST)
	@ResponseBody
	public Result buttonPermSave(SysPermission sysPermission) {
		sysPermissionService.add(sysPermission);
		return new Result(200, "添加成功", sysPermission.getId().toString());
	}

	/**
	 * 进入按钮修改页面
	 * 
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "buttonEdit", method = RequestMethod.GET)
	public ModelAndView buttonEdit(ModelAndView model, HttpSession httpSession, String id) {
		SysPermission sysPermission = sysPermissionService.queryById(id);
		model.addObject("perm", sysPermission);
		model.setViewName("system/permButtonEdit");
		return model;
	}

	/**
	 * 按钮修改
	 * 
	 * @param model
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(value = "buttonUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Result buttonUpdate(SysPermission sysPermission) {
		sysPermissionService.updateSelective(sysPermission);
		return new Result(200, "修改成功");
	}

	/**
	 * 批量删除
	 * 
	 * @param ids：权限id集合
	 * @return
	 */
	@RequestMapping(value = "batchDelete", method = RequestMethod.POST)
	@ResponseBody
	public Result batchDelete(String[] ids, HttpSession httpSession) {
		List<SysPermission> sysPermissions = new ArrayList<SysPermission>();
		for (String id : ids) {
			Integer num = sysPermissionService.queryPermCountByParentId(id);
			Integer count = sysRolePermissionService.queryCountById(id);
			SysPermission sysPermission = sysPermissionService.queryById(id);
			if (num > 0) {
				if (sysPermission.getIsParent() == false) {
					return new Result(300, "'" + sysPermission.getName() + "'" + "有子集，请前往模块管理删除！");
				} else {
					return new Result(300, "'" + sysPermission.getName() + "'" + "有子集，不能删除！");
				}

			}
			if (count > 0) {
				return new Result(300, "'" + sysPermission.getName() + "'" + "关联了角色，不能删除！");
			}
			sysPermissions.add(sysPermission);
		}
		sysPermissionService.batchDelete(sysPermissions);
		List<SysPermission> sysPermissionss = sysPermissionService.queryMenu(2 + "");
		// 添加顶级菜单
		SysPermission root = new SysPermission();
		root.setId("0");
		root.setName("根目录");
		root.setPid("-1");
		root.setOpen(true);
		sysPermissionss.add(root);
		httpSession.setAttribute("sysPermissionlist", sysPermissionss);
		List<SysPermission> sysPerms = sysPermissionService.queryPermMenu(1);
		return new Result(200, "更新成功", sysPerms);
	}

	/**
	 * 验证菜单权限
	 * 
	 * @return
	 */
	@RequestMapping(value = "validateMenu", method = RequestMethod.POST)
	@ResponseBody
	public Result validateMenu(String value, String field) {
		if (value.indexOf(";") != -1) {
			String[] objs = value.split(";");
			for (String obj : objs) {
				int count = sysPermissionService.countByField(obj, field);
				if (count > 0) {
					return new Result(300, "验证成功", obj);
				} else {
					return new Result(200, "验证成功", count);
				}
			}
		} else {
			int count = sysPermissionService.countByField(value, field);
			if (count > 0) {
				return new Result(300, "验证成功", value);
			} else {
				return new Result(200, "验证成功", count);
			}
		}
		return new Result(200, "验证成功", 0);
	}

	/**
	 * 验证菜单权限
	 * 
	 * @return
	 */
	@RequestMapping(value = "validateButton", method = RequestMethod.POST)
	@ResponseBody
	public Result validateButton(String value, String field, String pid) {
		if (value.indexOf(";") != -1) {
			String[] objs = value.split(";");
			for (String obj : objs) {
				int count = sysPermissionService.countByFieldAndParentId(obj, field, pid);
				if (count > 0) {
					return new Result(300, "验证成功", obj);
				} else {
					return new Result(200, "验证成功", count);
				}
			}
		} else {
			int count = sysPermissionService.countByFieldAndParentId(value, field, pid);
			if (count > 0) {
				return new Result(300, "验证成功", value);
			} else {
				return new Result(200, "验证成功", count);
			}
		}
		return new Result(200, "验证成功", 0);
	}

	/*
	 * 菜单权限
	 */
	@RequestMapping(value = "validatePermByParentId", method = RequestMethod.POST)
	@ResponseBody
	public Result validatePermByParentId(String value, String field, String parentId) {
		if (value.indexOf(";") != -1) {
			String[] objs = value.split(";");
			for (String obj : objs) {
				int count = sysPermissionService.countByFieldAndParentId(obj, field, parentId);
				if (count > 0) {
					return new Result(300, "验证成功", obj);
				} else {
					return new Result(200, "验证成功", count);
				}
			}
		} else {
			int count = sysPermissionService.countByFieldAndParentId(value, field, parentId);
			if (count > 0) {
				return new Result(300, "验证成功", value);
			} else {
				return new Result(200, "验证成功", count);
			}
		}
		return new Result(200, "验证成功", 0);
	}

	/*
	 * 查询根目录包括按钮的菜单数据
	 */
	@RequestMapping(value = "validateIsLeaf", method = RequestMethod.POST)
	@ResponseBody
	public Result validateIsLeaf(String id) {
		SysPermission sysPermission = sysPermissionService.queryById(id);
		if (sysPermission.getIsParent() == false) {
			return new Result(200, "验证成功", 0);
		} else {
			return new Result(300, "验证失败", 0);
		}
	}
}
