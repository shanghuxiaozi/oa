package com.friends.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.friends.dao.MfUserAttentionDao;
import com.friends.entity.MfUserAttention;
import com.friends.entity.vo.MfUserAttentionDynamicQueryVo;
import com.friends.service.MfUserAttentionService;
import com.friends.utils.Result;

@Service
public class MfUserAttentionServiceImpl extends BaseService<MfUserAttentionDao, MfUserAttention>
		implements MfUserAttentionService {

	@Autowired
	MfUserAttentionDao mfUserAttentionDao;
	@Autowired
	EntityManager em;

	// 通用方法在base层已经有了

	// 动态结构查询
	// Spring-data-jpa-specification形式较简单,但只适用于单表动态结构查询
	// entityManager形式稍复杂，但能实现多表动态约束查询。但多表时jpa返回的是object[][],需要借助工具转成对象。
	// entityManager方式
	@SuppressWarnings("unchecked")
	public Result queryDynamic(MfUserAttentionDynamicQueryVo userAttentionVo) {
		int page = userAttentionVo.getPage();
		int pageSize = userAttentionVo.getPageSize();
		MfUserAttention userAttention = userAttentionVo.getT();
		Map<String, Object> map = new HashMap<String, Object>();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_user_attention t where 1=1 ");

		String sqlDataQuery = " select distinct  t.id as id, t.attention_user as attentionUser, t.by_tention_user as byTentionUser, t.is_allow as isAllow, t.create_time as createTime "
				+ sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfUserAttention.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);

		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		// 用户关注表参数设置
		for (String key : map.keySet()) {
			dataQuery.setParameter(key, map.get(key));
			countQuery.setParameter(key, map.get(key));
		}
		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<MfUserAttention> list = dataQuery.getResultList();

		return new Result(list, Integer.valueOf(countQuery.getSingleResult().toString()));
	}

	// Specification的方式
	public Result queryDynamicSpecification(MfUserAttentionDynamicQueryVo userAttentionVo) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		int page = userAttentionVo.getPage();
		int pageSize = userAttentionVo.getPageSize();
		MfUserAttention userAttention = userAttentionVo.getT();
		PageRequest pageRequest = new PageRequest(page, pageSize, new Sort(Direction.DESC, "createTime"));

		// 构造动态约束条件
		Specification<MfUserAttention> spec = new Specification<MfUserAttention>() {
			@Override
			public Predicate toPredicate(Root<MfUserAttention> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate restraint = cb.and();

				/**
				 * if
				 * (!StringUtils.isEmpty(userAttention.getUserAttentionName()))
				 * { Predicate p =
				 * cb.equal(root.get("userAttentionName").as(String.class),
				 * userAttention.getUserAttentionName()); restraint = cb.and(p,
				 * restraint); }
				 */
				return restraint;
			}
		};
		Page<MfUserAttention> usersPage = mfUserAttentionDao.findAll(spec, pageRequest);
		int total = (int) mfUserAttentionDao.count(spec);
		hashMap.put("usersPage", usersPage);
		hashMap.put("total", total);
		return new Result(200, usersPage.getContent(), total);
	}

	@Override
	public MfUserAttention queryById(String id) {
		return mfUserAttentionDao.findOne(id);
	}

	@Override
	public List<String> findmyFriendsByuId(String id) {

		return mfUserAttentionDao.findmyFriendsByuId(id);
	}

	@Override
	public List<MfUserAttention> queryAttentionByFidAndUid(String friendsId, String uId) {

		return mfUserAttentionDao.queryAttentionByFidAndUid(friendsId, uId);
	}

	@Override
	public List<String> findmyFansByuId(String id) {

		return mfUserAttentionDao.findmyFansByuId(id);
	}

	@Override
	public List<String> findByDelUid(String id) {

		return mfUserAttentionDao.findByDelUid(id);
	}

	@Override
	public List<MfUserAttention> queryAttentionByType(String friendsId, String uId) {

		return mfUserAttentionDao.queryAttentionByType(friendsId, uId);
	}

	@Override
	public List<String> findmyAttentionByuId(String id) {

		return mfUserAttentionDao.findmyAttentionByuId(id);
	}

	@Override
	public List<MfUserAttention> queryAttention(String friendsId, String uId) {

		return mfUserAttentionDao.queryAttention(friendsId, uId);
	}

	@Override
	public List<MfUserAttention> query(String friendsId, String uId) {

		return mfUserAttentionDao.query(friendsId, uId);
	}

}
