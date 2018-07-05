package com.friends.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.friends.entity.SysPermission;
import com.friends.entity.SysUser;
import com.friends.entity.vo.SysPermissionTreeVo;
import com.friends.service.SysPermissionService;
import com.friends.utils.Okhttp;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

/**
 * 首页控制器 名称：IndexController.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2017年6月1日 下午1:59:12 <br>
 * 
 * @since 2017年6月1日
 * @authour wenxun
 */
@RequestMapping(value = "/")
@Controller
public class IndexController {
	private static Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private SysPermissionService sysPermissionService;

	@RequestMapping(value = "")
	public ModelAndView indexs(ModelAndView model) {
		List<SysPermission> list = sysPermissionService.queryPermByParentId("0");
		List<SysPermissionTreeVo> listvo = new ArrayList<SysPermissionTreeVo>();
		for (int i = 0; i < list.size(); i++) {
			SysPermission sysPerm = list.get(i);
			listvo.add(JSON.parseObject(JSON.toJSONString(sysPerm), SysPermissionTreeVo.class));
		}
		for (int i = listvo.size() - 1; i >= 0; i--) {
			SysPermissionTreeVo vo = listvo.get(i);
			if (vo.getIsParent() == true) {
				getChild(vo);
			}
		}
		model.addObject("list", listvo);
		logger.info("-----------加载首页数据：------");
		model.setViewName("index");
		return model;
	}

	/**
	 * 加载首页数据
	 * 
	 * @param model
	 * @return
	 * @param id
	 *            String 唯一标识
	 * @param personid
	 *            String 用户唯一标识
	 * @return rtobj BaseReturn 基本返回对象
	 * @变更记录 2017年6月1日 下午2:00:25 wenxun创建
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index(ModelAndView model) {
		List<SysPermission> list = sysPermissionService.queryPermByParentId("0");
		List<SysPermissionTreeVo> listvo = new ArrayList<SysPermissionTreeVo>();
		for (int i = 0; i < list.size(); i++) {
			SysPermission sysPerm = list.get(i);
			listvo.add(JSON.parseObject(JSON.toJSONString(sysPerm), SysPermissionTreeVo.class));
		}
		for (int i = listvo.size() - 1; i >= 0; i--) {
			SysPermissionTreeVo vo = listvo.get(i);
			if (vo.getIsParent() == true) {
				getChild(vo);
			}
		}

		model.addObject("list", listvo);
		model.setViewName("index");
		return model;
	}

	public void getChild(SysPermissionTreeVo vo) {
		List<SysPermission> list = sysPermissionService.queryPermByParentId(vo.getId());
		List<SysPermissionTreeVo> listvo = new ArrayList<SysPermissionTreeVo>();
		for (int i = 0; i < list.size(); i++) {
			SysPermission perm = list.get(i);
			listvo.add(JSON.parseObject(JSON.toJSONString(perm), SysPermissionTreeVo.class));
		}
		vo.setChildren(listvo);
		for (int i = 0; i < listvo.size(); i++) {
			SysPermissionTreeVo permvo = listvo.get(i);
			if (permvo.getIsParent() == true) {
				getChild(permvo);
			}
		}
	}

	/**
	 * 登录
	 * 
	 * @param model
	 * @return
	 * @return rtobj BaseReturn 基本返回对象
	 * @变更记录 2017年8月22日 下午5:13:35 陈树宏创建
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(ModelAndView model) {
		Session session = SecurityUtils.getSubject().getSession();
		SysUser userinfo = (SysUser) session.getAttribute("sysUser");
		model.setViewName("login");
		if (userinfo != null) {
			model.addObject("msg", "用户已登录！");
		}
		return model;
	}

	// 登录提交地址和applicationontext-shiro.xml配置的loginurl一致。 (配置文件方式的说法)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map)
			throws Exception {
		logger.info("HomeController.login()");
		// 登录失败从request中获取shiro处理的异常信息。
		// shiroLoginFailure:就是shiro异常类的全类名.
		String exception = (String) request.getAttribute("shiroLoginFailure");
		logger.info("exception=" + exception);
		String msg = "";
		if (exception != null) {
			if (UnknownAccountException.class.getName().equals(exception)) {
				logger.info("账号不存在：");
				msg = "账号不存在：";
				request.getSession().invalidate();
			} else if (ExcessiveAttemptsException.class.getName().equals(exception)) {
				logger.info("登录失败次数过多,请一分钟以后再登录！");
				msg = "登录失败次数过多：";
				request.getSession().invalidate();
			} else if (IncorrectCredentialsException.class.getName().equals(exception)) {
				logger.info("密码不正确：");
				msg = "密码不正确：";
				request.getSession().invalidate();
			} else if ("kaptchaValidateFailed".equals(exception)) {
				logger.info("验证码错误");
				msg = "验证码错误";
				request.getSession().invalidate();
			} else {
				msg = "else >> " + exception;
				logger.info("else -- >" + exception);
				request.getSession().invalidate();
			}
		} else {
			Session session = SecurityUtils.getSubject().getSession();
			SysUser user = (SysUser) session.getAttribute("sysUser");
			if (user != null) {
				response.sendRedirect("/index");
			}

		}
		map.put("msg", "用户名或密码不正确，请重新输入！！！");
		// 此方法不处理登录成功,由shiro进行处理.
		return "/login";
	}

	/**
	 * 退出登录
	 * 
	 * @param request
	 * @return
	 * @param id
	 *            String 唯一标识
	 * @param personid
	 *            String 用户唯一标识
	 * @return rtobj BaseReturn 基本返回对象
	 * @变更记录 2017年8月22日 下午5:13:20 陈树宏创建
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "login";
	}

	// 获取code,在菜单里面填入请求code的路径，然后回调地址写这个接口的地址用于获取openid
	// 获取openid
	@SuppressWarnings("serial")
	@RequestMapping(value = "getOpenid", method = RequestMethod.GET)
	public void getOpenid(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "code", required = true) String code) {
		try {
			// 打印日志看下
			Okhttp okhttp = new Okhttp();
			Gson gson = new Gson();

			String appid = "";
			String secret = "";
			String url = " https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + secret
					+ "&code=" + code + "&grant_type=authorization_code";
			String response2 = null;
			try {
				response2 = okhttp.run(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
			HashMap<String, String> weixinOpenid = gson.fromJson(response2, new TypeToken<HashMap<String, String>>() {
			}.getType());
			// 获取openid和网页的access_token
			String openid = weixinOpenid.get("openid");
			String access_token = weixinOpenid.get("access_token");

			response.sendRedirect(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
