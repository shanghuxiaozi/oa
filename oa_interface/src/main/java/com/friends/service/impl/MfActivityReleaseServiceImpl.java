package com.friends.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.friends.dao.MfActivityReleaseDao;
import com.friends.entity.MfActivityRelease;
import com.friends.entity.vo.MfActivityReleaseDynamicQueryVo;
import com.friends.service.MfActivityReleaseService;
import com.friends.utils.Result;

@Service
public class MfActivityReleaseServiceImpl extends BaseService<MfActivityReleaseDao, MfActivityRelease>
		implements MfActivityReleaseService {

	@Autowired
	MfActivityReleaseDao mfActivityReleaseDao;
	@Autowired
	EntityManager em;

	// 通用方法在base层已经有了

	// 动态结构查询
	// Spring-data-jpa-specification形式较简单,但只适用于单表动态结构查询
	// entityManager形式稍复杂，但能实现多表动态约束查询。但多表时jpa返回的是object[][],需要借助工具转成对象。
	// entityManager方式
	@SuppressWarnings("unchecked")
	public Result queryDynamic(MfActivityReleaseDynamicQueryVo sysActivityDynamicQueryVo) {
		int page = sysActivityDynamicQueryVo.getPage();
		int pageSize = sysActivityDynamicQueryVo.getPageSize();
		MfActivityRelease sysActivity = sysActivityDynamicQueryVo.getT();
		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_activity_release t where 1=1 and is_delete = 0");

		if (null != sysActivity && !StringUtils.isEmpty(sysActivity.getTitle())) {
			sqlCommonBody.append(" and title like :title ");
		}
		if (null != sysActivity && StringUtils.isNotEmpty(sysActivity.getType())) {
			sqlCommonBody.append(" and type= :type ");
		}

		// 遍历每个字段 发现约束类型不为NO的，则根据情况进行约束
		// 如果判空也得分类型：字符串与非字符串
		// 时间要模糊约束怎么办
		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString()
				+ " order by t.time desc limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfActivityRelease.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);
		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		if (null != sysActivity && !StringUtils.isEmpty(sysActivity.getTitle())) {
			dataQuery.setParameter("title", "%" + sysActivity.getTitle() + "%");
			countQuery.setParameter("title", "%" + sysActivity.getTitle() + "%");
		}
		if (null != sysActivity && StringUtils.isNotEmpty(sysActivity.getType())) {
			dataQuery.setParameter("type", sysActivity.getType());
			countQuery.setParameter("type", sysActivity.getType());
		}

		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<MfActivityRelease> list = dataQuery.getResultList();
		// BeanHelper.objectsListToEntity(List<Object[]> objectsList, Class<T>
		// c)
		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(list, total);
	}

	@Override
	public void delete(MfActivityRelease t) {
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" update mf_activity_release set  is_delete = 1 where id=?");

	}

	@Override
	public Result queryAllActivity() {
		// Integer page = pageRequest.getPageNumber();
		// Integer pageSize = pageRequest.getPageSize();

		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_activity_release t where 1=1 and t.is_delete = 0");

		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString() + " order by t.time desc ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();

		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfActivityRelease.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);

		// 参数注入
		// dataQuery.setParameter("from", (page * pageSize));
		// dataQuery.setParameter("to", pageSize);

		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<MfActivityRelease> list = dataQuery.getResultList();
		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(list, total);
	}

	
}