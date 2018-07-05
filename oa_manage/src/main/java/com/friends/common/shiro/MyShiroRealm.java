package com.friends.common.shiro;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import com.friends.dao.SysPermissionDao;
import com.friends.dao.SysRoleDao;
import com.friends.entity.SysPermission;
import com.friends.entity.SysRole;
import com.friends.entity.SysUser;
import com.friends.service.SysPermissionService;
import com.friends.service.SysRoleService;
import com.friends.service.SysUserService;

/**
 * 身份校验核心类;
 * 
 * @version v.0.1
 */
public class MyShiroRealm extends AuthorizingRealm {
	private static Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

	// @Override
	// public boolean supports(AuthenticationToken token) {
	//// return super.supports(token);
	// ////仅支持UsernamePasswordToken 类型的Token
	// return token instanceof UsernamePasswordToken;
	// }

	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SysRoleDao sysRoleDao;
	@Resource
	private SysPermissionService sysPermissionService;
	@Resource
	private SysPermissionDao sysPermissionDao;

	/**
	 * 认证信息.(身份验证) : Authentication 是用来验证用户身份
	 * 
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		logger.info("身份验证---->MyShiroRealm.doGetAuthenticationInfo()");

		UsernamePasswordToken tokens=(UsernamePasswordToken) token;
		logger.info("验证当前Subject时获取到token为：" + ReflectionToStringBuilder.toString(tokens, ToStringStyle.MULTI_LINE_STYLE)); 
		// 获取用户输入的密码
	    String password = String.valueOf(tokens.getPassword()); 
	    //5f4dcc3b5aa765d61d8327deb882cf99
		// 获取用户的输入的账号.
		String username = (String) token.getPrincipal();

		// 通过username从数据库中查找 User对象，如果找到，没找到.
		// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
		SysUser sysUser = sysUserService.findByUserName(username);
		
		if (sysUser == null) {
			return null;
		}
		if (0 == sysUser.getLocked()) {
			throw new LockedAccountException(); // 帐号锁定
		}
		Session session = SecurityUtils.getSubject().getSession();
		session.setAttribute("sysUser", sysUser);
		session.setAttribute("userSessionId", sysUser.getId());
		session.setTimeout(1800000);//30min
		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(sysUser, // 用户名
				sysUser.getPassword(), // 密码
				ByteSource.Util.bytes(sysUser.getCredentialsSalt()), // salt=username+salt
				getName() // realm name
		);
		return authenticationInfo;
	}

	/**
	 * 此方法调用 hasRole,hasPermission的时候才会进行回调.
	 * 
	 * 权限信息.(授权): 1、如果用户正常退出，缓存自动清空； 2、如果用户非正常退出，缓存自动清空；
	 * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。 （需要手动编程进行实现；放在service进行调用）
	 * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例， 调用clearCached方法；
	 * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
	 * 
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		/*
		 * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行， 其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
		 * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了， 缓存过期之后会再次执行。
		 */
		logger.info("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		SysUser sysUser = (SysUser) principals.getPrimaryPrincipal();
		if (sysUser.getUserName().equals("sys_admin")) {
			List<SysRole> sysUserRoles = sysRoleDao.findAll();
			for (SysRole role : sysUserRoles) {
				authorizationInfo.addRole(role.getName());
			}
			List<SysPermission> sysSysPermissions = sysPermissionDao.findAll();
			for (SysPermission p : sysSysPermissions) {
				authorizationInfo.addStringPermission(p.getPermissionValue());
			}
		} else {
			List<SysRole> sysUserRoles = sysRoleService.queryByUserId(sysUser.getId());
			for (SysRole role : sysUserRoles) {
				if (role != null) {
					authorizationInfo.addRole(role.getName());
					List<SysPermission> sysSysPermissions = sysPermissionService.queryByRoleId(role.getId());
					for (SysPermission p : sysSysPermissions) {
						authorizationInfo.addStringPermission(p.getPermissionValue());
					}
				}
			}
		}
		return authorizationInfo;
	}

	/**
	 * 将权限对象中的 权限code取出.
	 * 
	 * @param permissions
	 * @return
	 */
	// public Set<String> getStringPermissions(Set<SysPermission> permissions){
	// Set<String> stringPermissions = new HashSet<String>();
	// if(permissions != null){
	// for(SysPermission p : permissions) {
	// stringPermissions.add(p.getPermission());
	// }
	// }
	// return stringPermissions;
	// }

}
