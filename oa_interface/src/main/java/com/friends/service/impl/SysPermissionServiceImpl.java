package com.friends.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.friends.dao.SysPermissionDao;
import com.friends.entity.SysPermission;
import com.friends.entity.vo.SysPermissionVo;
import com.friends.service.SysPermissionService;
import com.friends.utils.Result;

@Service
@Transactional
public class SysPermissionServiceImpl extends BaseService<SysPermissionDao, SysPermission>
		implements SysPermissionService {

	@Autowired
	SysPermissionDao sysPermissionDao;
	@Autowired
	EntityManager em;

	@SuppressWarnings("unchecked")
	public Result queryDynamic(SysPermissionVo sysPermissionVo) {
		int page = sysPermissionVo.getPage();
		int pageSize = sysPermissionVo.getPageSize();
		SysPermission sysPermission=sysPermissionVo.getT();
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from sys_permission t where 1=1 ");
		if (!StringUtils.isEmpty(sysPermission.getName())) {
			sqlCommonBody.append(" and t.name like '%"+sysPermission.getName()+"%' ");
		}
		
		if (sysPermission != null && !StringUtils.isEmpty(sysPermission.getPid())) {
			sqlCommonBody.append(" and t.pid = '"+sysPermission.getPid()+"'");
		}

		if (sysPermission.getType()!=null) {
			sqlCommonBody.append(" and t.type = "+sysPermission.getType()+"");
		}

		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString() + " order by order_num ASC limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, SysPermission.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);
		// 鍙傛暟娉ㄥ叆
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		List<SysPermission> list = dataQuery.getResultList();

		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(list, total);
	}

	@SuppressWarnings("unchecked")
	public List<SysPermission> queryByRoleId(String roleId) {
		String sql="select p.* from sys_permission p LEFT JOIN sys_role_permission rp on rp.permission_id=p.id where rp.role_id=:role_id ";
		Query dataQuery = em.createNativeQuery(sql, SysPermission.class);
		dataQuery.setParameter("role_id", roleId);
		return dataQuery.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<SysPermission> queryMenu(String resourceType) {
		String sql="";
		if(null!=resourceType){
			sql=" select * from sys_permission  where type !=:type  order by order_num ASC  ";
		}else{
			sql=" select * from sys_permission   order by order_num ASC   ";
		}
		Query dataQuery = em.createNativeQuery(sql, SysPermission.class);
		dataQuery.setParameter("type", resourceType);
		return dataQuery.getResultList();
	}
	
	
	
	public List<SysPermission> queryRoleMenu() {
		return sysPermissionDao.queryRoleMenu();
	}
	
	public List<SysPermission> queryPermMenu(Integer type){
		return sysPermissionDao.queryPermMenu(type);
	}
	
	public Integer countByField(String value,String field){
		String sql="select count(*) from sys_permission  where "+field+"='"+value+"' and type!=2 ";
		Query dataQuery = em.createNativeQuery(sql);
		Integer count=Integer.valueOf(dataQuery.getSingleResult().toString());
		return count;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SysPermission> queryPermByParentId(String pid){
		String sql="select *from sys_permission where pid=:pid  order by order_num ASC ";
		Query dataQuery = em.createNativeQuery(sql, SysPermission.class);
		dataQuery.setParameter("pid", pid);
		return dataQuery.getResultList();
	}
	public Integer queryPermCountByParentId(String pid){
		String sql="select count(*) from sys_permission where pid=:pid ";
		Query dataQuery = em.createNativeQuery(sql);
		dataQuery.setParameter("pid", pid);
		Integer count=Integer.valueOf(dataQuery.getSingleResult().toString());
		return count;
	}

	@Override
	public Integer countByFieldAndParentId(String value, String field, String parentId) {
		String sql="select count(*) from sys_permission  where "+field+"='"+value+"' and  pid='"+parentId+"'";
		Query dataQuery = em.createNativeQuery(sql);
		Integer count=Integer.valueOf(dataQuery.getSingleResult().toString());
		return count;
	}

	@Override
	public void delete(String id) {
		sysPermissionDao.delete(id);
		
	}

	@Override
	public SysPermission queryById(String id) {
		return sysPermissionDao.findOne(id);
	}

	
}
