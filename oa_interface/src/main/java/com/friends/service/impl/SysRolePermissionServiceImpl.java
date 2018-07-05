package com.friends.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.friends.dao.SysRolePermissionDao;
import com.friends.entity.SysRolePermission;
import com.friends.entity.vo.SysRolePermissionVo;
import com.friends.service.SysRolePermissionService;
import com.friends.utils.Result;

@Service
@Transactional
public class SysRolePermissionServiceImpl extends BaseService<SysRolePermissionDao, SysRolePermission>
		implements SysRolePermissionService {

	@Autowired
	SysRolePermissionDao sysRolePermissionDao;
	@Autowired
	EntityManager em;

	@SuppressWarnings("unchecked")
	public Result queryDynamic(SysRolePermissionVo SysRolePermissionVo) {
		int page = SysRolePermissionVo.getPage();
		int pageSize = SysRolePermissionVo.getPageSize();

		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from sys_role_permission t where 1=1 ");
		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, SysRolePermission.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);
		// 鍙傛暟娉ㄥ叆
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);
		List<SysRolePermission> list = dataQuery.getResultList();
		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(list, total);
	}

	@SuppressWarnings("unchecked")
	public List<SysRolePermission> queryPermIds(String roleId) {
		String sql = "select * from sys_role_permission s where s.role_id=:role_id ";
		Query dataQuery = em.createNativeQuery(sql, SysRolePermission.class);
		dataQuery.setParameter("role_id", roleId);
		return dataQuery.getResultList();
	}

	@Transactional
	public void saveOrUpdate(String roleId, List<Integer> menuIdList) {
		if (menuIdList.size() == 0) {
			return;
		}
		SysRolePermission sysRole = new SysRolePermission();
		sysRole.setId(roleId);
		sysRolePermissionDao.delete(sysRole);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		map.put("menuIdList", menuIdList);
	}

	public Integer queryCountById(String id) {
		String sql = "select count(*) from sys_role_permission s where s.permission_id=:permission_id and s.role_id is not null";
		Query countQuery = em.createNativeQuery(sql);
		countQuery.setParameter("permission_id", id);
		return Integer.valueOf(countQuery.getSingleResult().toString());
	}

}
