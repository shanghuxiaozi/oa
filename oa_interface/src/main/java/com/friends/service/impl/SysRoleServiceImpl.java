package com.friends.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.friends.dao.SysRoleDao;
import com.friends.entity.SysRole;
import com.friends.entity.vo.SysRoleVo;
import com.friends.service.SysRoleService;
import com.friends.utils.Result;

@Service
@Transactional
public class SysRoleServiceImpl extends BaseService<SysRoleDao, SysRole> implements SysRoleService {

	@Autowired
	SysRoleDao sysRoleDao;
	@Autowired
	EntityManager em;

	@SuppressWarnings("unchecked")
	public Result queryDynamic(SysRoleVo SysRoleVo) {
		int page = SysRoleVo.getPage();
		int pageSize = SysRoleVo.getPageSize();
		SysRole sysRole = SysRoleVo.getT();
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from sys_role t where 1=1 ");

		if (!StringUtils.isEmpty(sysRole.getName())) {
			sqlCommonBody.append(" and t.name like :name ");
			map.put("name", "%" + sysRole.getName() + "%");
		}
		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString()
				+ " order by order_num asc limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString() + " order by order_num asc ";
		Query dataQuery = em.createNativeQuery(sqlDataQuery, SysRole.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);
		for (String key : map.keySet()) {
			dataQuery.setParameter(key, map.get(key));
			countQuery.setParameter(key, map.get(key));
		}
		List<SysRole> list = dataQuery.getResultList();
		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(list, total);
	}

	@SuppressWarnings("unchecked")
	public List<SysRole> queryByUserId(String id) {
		String sql = " select r.* from sys_user_role ur left join sys_role r on ur.role_id=r.id where ur.user_id=:id ";
		Query dataQuery = em.createNativeQuery(sql, SysRole.class);
		dataQuery.setParameter("id", id);
		return dataQuery.getResultList();
	}

	/**
	 * 通过用户id获取所有的角色名称
	 * 
	 * @param id
	 *            用户id
	 * @return 所有的角色名称
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryRoleByUserId(String id) {
		String sql = " select r.role from sys_user_role ur left join sys_role r on ur.role_id=r.id where ur.user_id=:id ";
		Query query = em.createNativeQuery(sql);
		query.setParameter("id", id);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> maps = query.getResultList();
		return maps;
	}

	/**
	 * 通过角色名称获取角色数量
	 */
	public Integer countByName(String name) {
		return sysRoleDao.countByName(name);
	}

}
