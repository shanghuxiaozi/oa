package com.friends.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.friends.dao.MfWorkUnitsDao;
import com.friends.entity.MfWorkUnits;
import com.friends.entity.vo.MfWorkUnitsDynamicQueryVo;
import com.friends.service.MfWorkUnitsService;
import com.friends.utils.Result;

@Service
public class MfWorkUnitsServiceImpl extends BaseService<MfWorkUnitsDao, MfWorkUnits> implements MfWorkUnitsService {

	@Autowired
	MfWorkUnitsDao mfWorkUnitsDao;
	@Autowired
	EntityManager em;

	// 通用方法在base层已经有了

	// 动态结构查询
	// Spring-data-jpa-specification形式较简单,但只适用于单表动态结构查询
	// entityManager形式稍复杂，但能实现多表动态约束查询。但多表时jpa返回的是object[][],需要借助工具转成对象。
	// entityManager方式
	@SuppressWarnings("unchecked")
	public Result queryDynamic(MfWorkUnitsDynamicQueryVo workUnitsVo) {
		int page = workUnitsVo.getPage();
		int pageSize = workUnitsVo.getPageSize();
		MfWorkUnits workUnits = workUnitsVo.getT();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_work_units t where 1=1 ");

		System.out.println("参数:" + workUnits.getWorkUnits());
		if (workUnits != null && StringUtils.isNotEmpty(workUnits.getWorkUnits())) {
			sqlCommonBody.append(" and t.work_units like :unitname ");
		}

		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfWorkUnits.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);
		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		if (workUnits != null && StringUtils.isNotEmpty(workUnits.getWorkUnits())) {
			dataQuery.setParameter("unitname", "%" + workUnits.getWorkUnits() + "%");
			countQuery.setParameter("unitname", "%" + workUnits.getWorkUnits() + "%");
		}

		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<MfWorkUnits> list = dataQuery.getResultList();
		// BeanHelper.objectsListToEntity(List<Object[]> objectsList, Class<T>
		// c)
		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(list, total);
	}

	@Override
	public void delete(String id) {
		mfWorkUnitsDao.delete(id);
	}

	@Override
	public List<String> findWorkUnits() {
	
		return mfWorkUnitsDao.findWorkUnits();
	}

}
