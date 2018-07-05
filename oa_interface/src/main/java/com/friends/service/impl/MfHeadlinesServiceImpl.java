package com.friends.service.impl;



import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.friends.dao.MfHeadlinesDao;
import com.friends.entity.MfHeadlines;
import com.friends.entity.vo.MfHeadlinesDynamicQueryVo;
import com.friends.service.MfHeadlinesService;
import com.friends.utils.Result;

@Service
public class MfHeadlinesServiceImpl extends BaseService<MfHeadlinesDao, MfHeadlines> implements MfHeadlinesService {
	@Autowired
	MfHeadlinesDao mfHeadlinesDao;
	@Autowired
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	public Result queryDynamic(MfHeadlinesDynamicQueryVo mfHeadlinesDynamicQueryVo) {
		int page = mfHeadlinesDynamicQueryVo.getPage();
		int pageSize = mfHeadlinesDynamicQueryVo.getPageSize();
		MfHeadlines mfHeadlines = mfHeadlinesDynamicQueryVo.getT();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_headlines t where 1=1 ");
		
		if( null != mfHeadlines.getTitle()&& !StringUtils.isEmpty(mfHeadlines.getTitle()) ){
			sqlCommonBody.append(" and title like :title");
		}
		
		if (null != mfHeadlines && StringUtils.isNotEmpty(mfHeadlines.getType())) {
			sqlCommonBody.append(" and type= :type ");
		}
		
		if (null != mfHeadlines && StringUtils.isNotEmpty(mfHeadlines.getId())) {
			sqlCommonBody.append(" and id= :id ");
		}
		// 遍历每个字段 发现约束类型不为NO的，则根据情况进行约束
		// 如果判空也得分类型：字符串与非字符串
		// 时间要模糊约束怎么办

		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString() + "  order by t.head_time desc limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfHeadlines.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);
		if( null != mfHeadlines.getTitle()&& !StringUtils.isEmpty(mfHeadlines.getTitle()) ){
			dataQuery.setParameter("title", "%" + mfHeadlines.getTitle() + "%");
			countQuery.setParameter("title", "%" + mfHeadlines.getTitle() + "%");
		}
		if (null != mfHeadlines && StringUtils.isNotEmpty(mfHeadlines.getType())) {
			dataQuery.setParameter("type", mfHeadlines.getType());
			countQuery.setParameter("type", mfHeadlines.getType());
		}
		if (null != mfHeadlines && StringUtils.isNotEmpty(mfHeadlines.getId())) {
			dataQuery.setParameter("id", mfHeadlines.getId());
			countQuery.setParameter("id", mfHeadlines.getId());
		}
		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<MfHeadlines> list = dataQuery.getResultList();
		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(list, total);
	}

}
