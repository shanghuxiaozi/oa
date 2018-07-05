package com.friends.service.impl;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.friends.dao.MfUserInfoDao;
import com.friends.entity.MfActivityEnroll;
import com.friends.entity.MfUserInfo;
import com.friends.entity.vo.MfUserInfoDynamicQueryVo;
import com.friends.service.MfActivityEnrollService;
import com.friends.service.MfUserInfoService;
import com.friends.utils.Result;
import com.friends.utils.StringUtil;

@Service(value="mfUserInfoService")
public class MfUserInfoServiceImpl extends BaseService<MfUserInfoDao, MfUserInfo> implements MfUserInfoService {

	@Autowired
	MfUserInfoDao mfUserInfoDao;
	@Autowired
	EntityManager em;
	@Autowired
	private MfActivityEnrollService mfActivityEnrollService;

	// 通用方法在base层已经有了

	// 动态结构查询
	// Spring-data-jpa-specification形式较简单,但只适用于单表动态结构查询
	// entityManager形式稍复杂，但能实现多表动态约束查询。但多表时jpa返回的是object[][],需要借助工具转成对象。
	// entityManager方式
	@SuppressWarnings("unchecked")
	public Result queryDynamic(MfUserInfoDynamicQueryVo sysUserinfoVo) {
		int page = sysUserinfoVo.getPage();
		int pageSize = sysUserinfoVo.getPageSize();
		MfUserInfo sysUserinfo = sysUserinfoVo.getT();
		Map<String, Object> map = new HashMap<String, Object>();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_user_info t where 1=1 ");

		// 查询参数 用户名是否为空
		if (!StringUtils.isEmpty(sysUserinfo.getName())) {
			sqlCommonBody.append(" and name=:userName ");
		}
		// 查询参数 积分范围是否为空
		if (sysUserinfoVo.getIntegralStart() != null) {
			sqlCommonBody.append(" and i.integral >=:startIntegral and i.integral <=:endIntegral ");
		}

		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfUserInfo.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);

		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);
		// 查询参数 用户名是否为空
		if (!StringUtils.isEmpty(sysUserinfo.getName())) {
			dataQuery.setParameter("userName", sysUserinfo.getName());
			countQuery.setParameter("userName", sysUserinfo.getName());
		}
		// 查询参数 积分范围是否为空
		if (sysUserinfoVo.getIntegralStart() != null) {
			dataQuery.setParameter("startIntegral", sysUserinfoVo.getIntegralStart());
			dataQuery.setParameter("endIntegral", sysUserinfoVo.getIntegralEnd());
			countQuery.setParameter("startIntegral", sysUserinfoVo.getIntegralStart());
			countQuery.setParameter("endIntegral", sysUserinfoVo.getIntegralEnd());
		}
		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<MfUserInfo> list = dataQuery.getResultList();

		return new Result(list, Integer.valueOf(countQuery.getSingleResult().toString()));
	}

	@SuppressWarnings("unchecked")
	public Result queryDynamic2(MfUserInfoDynamicQueryVo sysUserinfoVo) {
		int page = sysUserinfoVo.getPage();
		int pageSize = sysUserinfoVo.getPageSize();
		MfUserInfo sysUserinfo = sysUserinfoVo.getT();
		Map<String, Object> map = new HashMap<String, Object>();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_user_info t left join mf_activity_integral i on t.id = i.user_id where 1=1 ");

		// 查询参数 用户名是否为空
		if (!StringUtils.isEmpty(sysUserinfo.getName())) {
			sqlCommonBody.append(" and name=:userName ");
		}
		// 判断用户类型 是否为空
		if(!sysUserinfo.getStatus().equals("0")){
			sqlCommonBody.append(" and status=:status ");
		}
		// 查询参数 积分范围是否为空
		if (sysUserinfoVo.getIntegralStart() != null) {
			sqlCommonBody.append(" and i.integral >=:startIntegral and i.integral <=:endIntegral ");
		}

		String sqlDataQuery = " select distinct t.id,t.create_time,t.huanxin_flag,t.name,t.nickname,t.openid,t.phone,t.sex,t.user_type,i.integral " + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery);
		// 返回的参数直接用sql方式返回
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Query countQuery = em.createNativeQuery(sqlCountQuery);

		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		// 查询参数 用户名是否为空
		if (!StringUtils.isEmpty(sysUserinfo.getName())) {
			dataQuery.setParameter("userName", sysUserinfo.getName());
			countQuery.setParameter("userName", sysUserinfo.getName());
		}
		// 判断用户类型 是否为空
		if(!sysUserinfo.getStatus().equals("0")){
			dataQuery.setParameter("status", sysUserinfo.getStatus());
			countQuery.setParameter("status", sysUserinfo.getStatus());
		}
		// 查询参数 积分范围是否为空
		if (sysUserinfoVo.getIntegralStart() != null) {
			dataQuery.setParameter("startIntegral", sysUserinfoVo.getIntegralStart());
			dataQuery.setParameter("endIntegral", sysUserinfoVo.getIntegralEnd());
			countQuery.setParameter("startIntegral", sysUserinfoVo.getIntegralStart());
			countQuery.setParameter("endIntegral", sysUserinfoVo.getIntegralEnd());
		}
		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sequel中结果集字段对应的bean
		List<Map<String, Object>> listMap = dataQuery.getResultList();
		listMap = StringUtil.tranferMapsKeyToCamel(listMap, null);
		return new Result(listMap, Integer.valueOf(countQuery.getSingleResult().toString()));
	}

	// Specification的方式
	public Result queryDynamicSpecification(MfUserInfoDynamicQueryVo sysUserinfoVo) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		int page = sysUserinfoVo.getPage();
		int pageSize = sysUserinfoVo.getPageSize();
		MfUserInfo sysUserinfo = sysUserinfoVo.getT();
		PageRequest pageRequest = new PageRequest(page, pageSize, new Sort(Direction.DESC, "createTime"));

		// 构造动态约束条件
		Specification<MfUserInfo> spec = new Specification<MfUserInfo>() {
			@Override
			public Predicate toPredicate(Root<MfUserInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate restraint = cb.and();

				/**
				 * if (!StringUtils.isEmpty(sysUserinfo.getSysUserinfoName())) {
				 * Predicate p =
				 * cb.equal(root.get("sysUserinfoName").as(String.class),
				 * sysUserinfo.getSysUserinfoName()); restraint = cb.and(p,
				 * restraint); }
				 */
				return restraint;
			}
		};
		Page<MfUserInfo> usersPage = mfUserInfoDao.findAll(spec, pageRequest);
		int total = (int) mfUserInfoDao.count(spec);
		hashMap.put("usersPage", usersPage);
		hashMap.put("total", total);
		return new Result(200, usersPage.getContent(), total);
	}

	@Override
	public void delete(String id) {
		mfUserInfoDao.delete(id);
	}

	@Override
	public MfUserInfo queryById(String id) {
		return mfUserInfoDao.findOne(id);
	}

	@Override
	public MfUserInfo queryUserByOpenid(String openid) {
		return mfUserInfoDao.queryUserByOpenid(openid);
	}

	@Override
	public Integer queryPhone(String phone) {
		return mfUserInfoDao.queryPhone(phone);
	}

	@Override
	public Integer queryIsByOpenid(String openId) {
		return mfUserInfoDao.queryIsByOpenid(openId);
	}

	@Override
	public MfUserInfo queryUser(String phone) {
		return mfUserInfoDao.queryUser(phone);
	}

	/**
	 * 活动报名：通过活动id查用户信息
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Result queryByActivityId(String id) {
		List<List<Map<String, Object>>> listMapData = new ArrayList<List<Map<String, Object>>>();
		List<MfActivityEnroll> activityEnrollList = mfActivityEnrollService.queryActivityEnrollByActivityId(id);
		if (activityEnrollList != null && activityEnrollList.size() > 0) {
			for (int i = 0; i < activityEnrollList.size(); i++) {
				String querySql = "select a.activity_id,a.type,a.user_id,u.name,u.phone from mf_activity_enroll a left join mf_user_info u on (u.id=a.user_id) "
						+ " where a.id='" + activityEnrollList.get(i).getId() + "' and u.id='"
						+ activityEnrollList.get(i).getUserId() + "' ";
				Query queryData = em.createNativeQuery(querySql);
				queryData.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List<Map<String, Object>> dataList = queryData.getResultList();
				dataList = StringUtil.tranferMapsKeyToCamel(dataList, null);
				listMapData.add(dataList);
			}
			return new Result(listMapData);
		}
		return new Result(null);
	}

	@Override
	public String queryUserNickname(String id) {

		return mfUserInfoDao.queryUserNickname(id);
	}

	@Override
	public List<String> queryUserId() {

		return mfUserInfoDao.queryUserId();
	}

	@Override
	public Result queryByTimeSlot(String userType,String startDate,String endDate) {
		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_user_info t where 1=1 ");
		// 判断用户类型 是否为空
		if(!userType.equals("0")){
			sqlCommonBody.append(" and user_type=:userType ");
		}
		// 查询参数 时间范围是否为空
		if (StringUtils.isNoneEmpty(startDate)  && StringUtils.isNoneEmpty(endDate)) {
			sqlCommonBody.append(" and create_time between DATE(:startDate) and DATE(:endDate) ");
		}
		String sqlDataQuery = "select distinct t.* " + sqlCommonBody.toString() ;
		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfUserInfo.class);
		// 参数注入
		if(!userType.equals("0")){
			dataQuery.setParameter("userType", userType);
		}
		if (StringUtils.isNoneEmpty(startDate)  && StringUtils.isNoneEmpty(endDate)) {
			dataQuery.setParameter("startDate", startDate);
			dataQuery.setParameter("endDate", endDate);
		}
		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<MfUserInfo> list = dataQuery.getResultList();
		return new Result(list);
	}



}
