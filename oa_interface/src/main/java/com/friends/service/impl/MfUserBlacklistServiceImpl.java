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

import com.friends.dao.MfUserBlacklistDao;
import com.friends.entity.MfUserBlacklist;
import com.friends.entity.vo.MfUserBlacklistDynamicQueryVo;
import com.friends.service.MfUserBlacklistService;
import com.friends.utils.Result;

@Service
public class MfUserBlacklistServiceImpl extends BaseService<MfUserBlacklistDao, MfUserBlacklist>
		implements MfUserBlacklistService {

	@Autowired
	MfUserBlacklistDao mfUserBlacklistDao;
	@Autowired
	EntityManager em;

	// 通用方法在base层已经有了

	// 动态结构查询
	// Spring-data-jpa-specification形式较简单,但只适用于单表动态结构查询
	// entityManager形式稍复杂，但能实现多表动态约束查询。但多表时jpa返回的是object[][],需要借助工具转成对象。
	// entityManager方式
	@SuppressWarnings("unchecked")
	public Result queryDynamic(MfUserBlacklistDynamicQueryVo userBlacklistVo) {
		int page = userBlacklistVo.getPage();
		int pageSize = userBlacklistVo.getPageSize();
		MfUserBlacklist userBlacklist = userBlacklistVo.getT();
		Map<String, Object> map = new HashMap<String, Object>();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_user_blacklist t where 1=1 ");

		String sqlDataQuery = " select distinct  t.id as id, t.user_id as userId, t.by_user_id as byUserId, t.create_time as createTime, t.is_remove as isRemove "
				+ sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfUserBlacklist.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);

		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		// 用户黑名单表参数设置
		for (String key : map.keySet()) {
			dataQuery.setParameter(key, map.get(key));
			countQuery.setParameter(key, map.get(key));
		}

		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<MfUserBlacklist> list = dataQuery.getResultList();

		return new Result(list, Integer.valueOf(countQuery.getSingleResult().toString()));
	}

	// Specification的方式
	public Result queryDynamicSpecification(MfUserBlacklistDynamicQueryVo userBlacklistVo) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		int page = userBlacklistVo.getPage();
		int pageSize = userBlacklistVo.getPageSize();
		MfUserBlacklist userBlacklist = userBlacklistVo.getT();
		PageRequest pageRequest = new PageRequest(page, pageSize, new Sort(Direction.DESC, "createTime"));

		// 构造动态约束条件
		Specification<MfUserBlacklist> spec = new Specification<MfUserBlacklist>() {
			@Override
			public Predicate toPredicate(Root<MfUserBlacklist> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate restraint = cb.and();

				/**
				 * if
				 * (!StringUtils.isEmpty(userBlacklist.getUserBlacklistName()))
				 * { Predicate p =
				 * cb.equal(root.get("userBlacklistName").as(String.class),
				 * userBlacklist.getUserBlacklistName()); restraint = cb.and(p,
				 * restraint); }
				 */
				return restraint;
			}
		};
		Page<MfUserBlacklist> usersPage = mfUserBlacklistDao.findAll(spec, pageRequest);
		int total = (int) mfUserBlacklistDao.count(spec);
		hashMap.put("usersPage", usersPage);
		hashMap.put("total", total);
		return new Result(200, usersPage.getContent(), total);
	}

	@Override
	public void delete(String id) {
		mfUserBlacklistDao.delete(id);
	}

	@Override
	public MfUserBlacklist queryById(String id) {
		// TODO Auto-generated method stub
		return mfUserBlacklistDao.findOne(id);
	}

	@Override
	public List<String> findmyBlackListByuId(String id) {

		return mfUserBlacklistDao.findmyBlackListByuId(id);
	}

	@Override
	public List<MfUserBlacklist> findmyBlackList(String uId, String byBlackId) {

		return mfUserBlacklistDao.findmyBlackList(uId, byBlackId);
	}

	@Override
	public List<MfUserBlacklist> blackListUser(String uId, String byBlackId) {

		return mfUserBlacklistDao.blackListUser(uId, byBlackId);
	}

	@Override
	public List<String> blackListUserByuId(String uId) {

		return mfUserBlacklistDao.blackListUserByuId(uId);
	}

	@Override
	public List<String> blackListDelId(String uId) {

		return mfUserBlacklistDao.blackListDelId(uId);
	}

	@Override
	public List<String> NoRemoveblackId(String uId) {

		return mfUserBlacklistDao.NoRemoveblackId(uId);
	}

	@Override
	public MfUserBlacklist queryBlacklistById(String uId, String byBlackId) {
		return mfUserBlacklistDao.findBlacklistById(uId,byBlackId);
	}

}
