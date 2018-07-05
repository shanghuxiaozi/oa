package com.friends.service.impl;

import java.util.Calendar;
import java.util.Date;
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
import com.friends.dao.MfUserLogDao;
import com.friends.entity.MfUserLog;
import com.friends.entity.vo.MfUserLogDynamicQueryVo;
import com.friends.service.MfUserLogService;
import com.friends.utils.Result;

@Service
public class MfUserLogServiceImpl extends BaseService<MfUserLogDao, MfUserLog> implements MfUserLogService {

	@Autowired
	MfUserLogDao mfUserLogDao;
	@Autowired
	EntityManager em;

	// 通用方法在base层已经有了

	// 动态结构查询
	// Spring-data-jpa-specification形式较简单,但只适用于单表动态结构查询
	// entityManager形式稍复杂，但能实现多表动态约束查询。但多表时jpa返回的是object[][],需要借助工具转成对象。
	// entityManager方式
	@SuppressWarnings("unchecked")
	public Result queryDynamic(MfUserLogDynamicQueryVo userLogVo) {
		int page = userLogVo.getPage();
		int pageSize = userLogVo.getPageSize();
		MfUserLog userLog = userLogVo.getT();
		Map<String, Object> map = new HashMap<String, Object>();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append("from mf_user_log t where 1=1 and t.be_user_id = :userId and t.create_time between :startTime and :endTime");

		String sqlDataQuery = " select t.* " + sqlCommonBody.toString()
				+ " GROUP BY t.user_id ORDER BY t.create_time desc limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfUserLog.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);
		// 设置开始时间
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // 设置当前日期
		c.add(Calendar.DATE, -7); // 日期加1天
		Date date = c.getTime();
		// 参数注入
		dataQuery.setParameter("userId", userLog.getBeUserId());
		dataQuery.setParameter("startTime", date);
		dataQuery.setParameter("endTime", new Date());
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);
		
		countQuery.setParameter("userId", userLog.getBeUserId());
		countQuery.setParameter("startTime", date);
		countQuery.setParameter("endTime", new Date());

		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<MfUserLog> list = dataQuery.getResultList();

		return new Result(list, Integer.valueOf(countQuery.getSingleResult().toString()));
	}

	// Specification的方式
	public Result queryDynamicSpecification(MfUserLogDynamicQueryVo userLogVo) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		int page = userLogVo.getPage();
		int pageSize = userLogVo.getPageSize();
		MfUserLog userLog = userLogVo.getT();
		PageRequest pageRequest = new PageRequest(page, pageSize, new Sort(Direction.DESC, "createTime"));

		// 构造动态约束条件
		Specification<MfUserLog> spec = new Specification<MfUserLog>() {
			@Override
			public Predicate toPredicate(Root<MfUserLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate restraint = cb.and();

				/**
				 * if (!StringUtils.isEmpty(userLog.getUserLogName())) {
				 * Predicate p =
				 * cb.equal(root.get("userLogName").as(String.class),
				 * userLog.getUserLogName()); restraint = cb.and(p, restraint);
				 * }
				 */
				return restraint;
			}
		};
		Page<MfUserLog> usersPage = mfUserLogDao.findAll(spec, pageRequest);
		int total = (int) mfUserLogDao.count(spec);
		hashMap.put("usersPage", usersPage);
		hashMap.put("total", total);
		return new Result(200, usersPage.getContent(), total);
	}

	@Override
	public void delete(String id) {
		mfUserLogDao.delete(id);
	}

	@Override
	public MfUserLog queryById(String id) {
		return mfUserLogDao.findOne(id);
	}

	@Override
	public Result queryMeSeeWho(MfUserLogDynamicQueryVo userLogVo) {
		int page = userLogVo.getPage();
		int pageSize = userLogVo.getPageSize();
		MfUserLog userLog = userLogVo.getT();
		Map<String, Object> map = new HashMap<String, Object>();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append("from mf_user_log t where 1=1 and t.user_id = :userId and t.create_time between :startTime and :endTime");

		String sqlDataQuery = " select t.* " + sqlCommonBody.toString()
				+ " GROUP BY t.be_user_id ORDER BY t.create_time desc limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfUserLog.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);

		
		// 设置开始时间
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); 		// 设置当前日期
		c.add(Calendar.DATE, -7); 	// 日期加1天
		Date date = c.getTime();
		
		// 参数注入
		dataQuery.setParameter("userId", userLog.getUserId());
		dataQuery.setParameter("startTime", date);
		dataQuery.setParameter("endTime", new Date());
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);
		
		countQuery.setParameter("userId", userLog.getUserId());
		countQuery.setParameter("startTime", date);
		countQuery.setParameter("endTime", new Date());
		// 用户操作日志表参数设置
		for (String key : map.keySet()) {
			dataQuery.setParameter(key, map.get(key));
			countQuery.setParameter(key, map.get(key));
		}
		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<MfUserLog> list = dataQuery.getResultList();

		return new Result(list, Integer.valueOf(countQuery.getSingleResult().toString()));
	}

}
