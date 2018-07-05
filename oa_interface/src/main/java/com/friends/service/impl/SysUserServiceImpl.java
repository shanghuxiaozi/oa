package com.friends.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.friends.dao.SysUserDao;
import com.friends.entity.SysUser;
import com.friends.entity.vo.SysUserVo;
import com.friends.service.SysRoleService;
import com.friends.service.SysUserService;
import com.friends.utils.Result;

@Service
@Transactional
public class SysUserServiceImpl extends BaseService<SysUserDao, SysUser> implements SysUserService {

	@Autowired
	SysUserDao userInfoDao;
	@Autowired
	SysRoleService sysRoleService;
	@Autowired
	EntityManager em;

	public SysUser findByUserName(String username) {
		return userInfoDao.findByUserName(username);
	}
	// 通用方法在base层已经有了

	// 动态结构查询
	// Spring-data-jpa-specification形式较简单,但只适用于单表动态结构查询
	// entityManager形式稍复杂，但能实现多表动态约束查询。但多表时jpa返回的是object[][],需要借助工具转成对象。
	// entityManager方式
	public Result queryDynamic(SysUserVo sysUserVo) {
		int page = sysUserVo.getPage();
		int pageSize = sysUserVo.getPageSize();
		SysUser SysUser = sysUserVo.getT();
		StringBuilder sqlCommonBody = new StringBuilder();
		Map<String, Object> map = new HashMap<String, Object>();
		sqlCommonBody.append(" from sys_user t where 1=1 ");
		if (SysUser.getUserName() != null) {
			sqlCommonBody.append(" and t.user_name like :user_name ");
			map.put("user_name", "%" + SysUser.getUserName() + "%");
		}
		if (!StringUtils.isEmpty(SysUser.getLocked())) {
			sqlCommonBody.append(" and t.locked = " + SysUser.getLocked() + "");
		}
		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, SysUser.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);
		for (String key : map.keySet()) {
			dataQuery.setParameter(key, map.get(key));
			countQuery.setParameter(key, map.get(key));
		}

		return new Result(dataQuery.getResultList(), Integer.valueOf(countQuery.getSingleResult().toString()));
	}

	public Integer countByUserName(String userName) {
		return userInfoDao.countByUserName(userName);
	}

}
