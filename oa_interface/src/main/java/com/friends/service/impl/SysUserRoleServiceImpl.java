package com.friends.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.friends.dao.SysUserRoleDao;
import com.friends.entity.SysUserRole;
import com.friends.entity.vo.SysUserRoleVo;
import com.friends.service.SysUserRoleService;
import com.friends.utils.Result;

@Service
@Transactional
public class SysUserRoleServiceImpl extends BaseService<SysUserRoleDao, SysUserRole> implements SysUserRoleService {

	@Autowired
	SysUserRoleDao sysUserRoleDao;
	@Autowired
	EntityManager em;

	@SuppressWarnings("unchecked")
	public Result queryDynamic(SysUserRoleVo SysUserRoleVo) {
		int page = SysUserRoleVo.getPage();
		int pageSize = SysUserRoleVo.getPageSize();

		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from sys_user_role t where 1=1 ");
		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, SysUserRole.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		List<SysUserRole> list = dataQuery.getResultList();
		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(list, total);
	}

	public Integer queryCountById(String id) {
		String sql = "select count(*) from sys_user_role s where s.role_id=:role_id  and s.user_id is not null";
		Query countQuery = em.createNativeQuery(sql);
		countQuery.setParameter("role_id", id);
		return Integer.valueOf(countQuery.getSingleResult().toString());
	}

}
