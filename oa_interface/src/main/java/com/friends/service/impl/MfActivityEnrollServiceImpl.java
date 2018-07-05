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
import com.friends.dao.MfActivityEnrollDao;
import com.friends.entity.MfActivityEnroll;
import com.friends.entity.vo.MfActivityEnrollDynamicQueryVo;
import com.friends.service.MfActivityEnrollService;
import com.friends.utils.Result;


@Service
public class MfActivityEnrollServiceImpl extends BaseService<MfActivityEnrollDao, MfActivityEnroll>
		implements MfActivityEnrollService {

	@Autowired
	MfActivityEnrollDao mfActivityEnrollDao;
	@Autowired
	EntityManager em;

	// 通用方法在base层已经有了

	// 动态结构查询
	// Spring-data-jpa-specification形式较简单,但只适用于单表动态结构查询
	// entityManager形式稍复杂，但能实现多表动态约束查询。但多表时jpa返回的是object[][],需要借助工具转成对象。
	// entityManager方式
	@SuppressWarnings("unchecked")
	public Result queryDynamic(MfActivityEnrollDynamicQueryVo activityEnrollVo) {
		int page = activityEnrollVo.getPage();
		int pageSize = activityEnrollVo.getPageSize();
		MfActivityEnroll activityEnroll = activityEnrollVo.getT();
		Map<String, Object> map = new HashMap<String, Object>();
		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_activity_enroll t where 1=1 ");
		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfActivityEnroll.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);

		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		// 活动报名表参数设置
		for (String key : map.keySet()) {
			dataQuery.setParameter(key, map.get(key));
			countQuery.setParameter(key, map.get(key));
		}
		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<MfActivityEnroll> list = dataQuery.getResultList();

		return new Result(list, Integer.valueOf(countQuery.getSingleResult().toString()));
	}

	// Specification的方式
	public Result queryDynamicSpecification(MfActivityEnrollDynamicQueryVo activityEnrollVo) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		int page = activityEnrollVo.getPage();
		int pageSize = activityEnrollVo.getPageSize();
		MfActivityEnroll activityEnroll = activityEnrollVo.getT();
		PageRequest pageRequest = new PageRequest(page, pageSize, new Sort(Direction.DESC, "createTime"));

		// 构造动态约束条件
		Specification<MfActivityEnroll> spec = new Specification<MfActivityEnroll>() {
			@Override
			public Predicate toPredicate(Root<MfActivityEnroll> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate restraint = cb.and();

				/**
				 * if
				 * (!StringUtils.isEmpty(activityEnroll.getActivityEnrollName())
				 * ) { Predicate p =
				 * cb.equal(root.get("activityEnrollName").as(String.class),
				 * activityEnroll.getActivityEnrollName()); restraint =
				 * cb.and(p, restraint); }
				 */
				return restraint;
			}
		};
		Page<MfActivityEnroll> usersPage = mfActivityEnrollDao.findAll(spec, pageRequest);
		int total = (int) mfActivityEnrollDao.count(spec);
		hashMap.put("usersPage", usersPage);
		hashMap.put("total", total);
		return new Result(200, usersPage.getContent(), total);
	}

	@Override
	public void delete(String id) {
		mfActivityEnrollDao.delete(id);
	}

	@Override
	public MfActivityEnroll queryById(String id) {
		return mfActivityEnrollDao.findOne(id);
	}

	@Override
	public Integer countByActivityId(String id) {
		return mfActivityEnrollDao.countByActivityId(id);
	}

	@Override
	public Integer queryBaoming(String userId, String activityId) {
		return mfActivityEnrollDao.queryBaoming(userId, activityId);
	}

	@Override
	public MfActivityEnroll queryInformation(MfActivityEnroll activityEnroll) {
		return mfActivityEnrollDao.queryInformation(activityEnroll.getIsJoin(), activityEnroll.getUserId(),
				activityEnroll.getActivityId());
	}

	@Override
	public Integer queryIshavingjoin(String userId, String activityId) {
		return mfActivityEnrollDao.queryIshavingjoin(userId, activityId);
	}

	@Override
	public List<String> queryByUserId(String userId) {
		return mfActivityEnrollDao.findByUserId(userId);
	}

	@Override
	public List<MfActivityEnroll> queryActivityEnrollByActivityId(String id) {
		return mfActivityEnrollDao.findActivityEnrollById(id);
	}

	@Override
	public MfActivityEnroll findActivityEnrollByUidAndAid(String activityId, String userId) {
		return mfActivityEnrollDao.findActivityEnrollByUidAndAid(activityId, userId);
	}

}
