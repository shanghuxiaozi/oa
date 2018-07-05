package com.friends.service.impl;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.friends.dao.MfUserSingleAuthDao;
import com.friends.entity.MfUserSingleAuth;
import com.friends.entity.vo.MfUserSingleAuthDynamicQueryVo;
import com.friends.service.MfUserSingleAuthService;
import com.friends.utils.Result;
import com.friends.utils.StringUtil;

@Service
public class MfUserSingleAuthServiceImpl extends BaseService<MfUserSingleAuthDao, MfUserSingleAuth>
		implements MfUserSingleAuthService {

	@Autowired
	MfUserSingleAuthDao mfUserSingleAuthDao;
	@Autowired
	EntityManager em;

	// 通用方法在base层已经有了

	// 动态结构查询
	// Spring-data-jpa-specification形式较简单,但只适用于单表动态结构查询
	// entityManager形式稍复杂，但能实现多表动态约束查询。但多表时jpa返回的是object[][],需要借助工具转成对象。
	// entityManager方式
	@SuppressWarnings("unchecked")
	public Result queryDynamic(MfUserSingleAuthDynamicQueryVo userSingleAuthDynamicQueryVo) {
		int page = userSingleAuthDynamicQueryVo.getPage();
		int pageSize = userSingleAuthDynamicQueryVo.getPageSize();
		MfUserSingleAuth userSingleAuth = userSingleAuthDynamicQueryVo.getT();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_user_single_auth a left join mf_user_info u on(u.id=a.user_id) where 1=1 ");

		// 查询条件
		if (userSingleAuth != null && !StringUtils.isEmpty(userSingleAuth.getAuthResult())) {
			sqlCommonBody.append(" and auth_result=:authResult  ");
		}
		
		// 账号对应工会id
		if(userSingleAuth != null && StringUtils.isNotEmpty(userSingleAuth.getLabourUnionId())){
			sqlCommonBody.append(" and labour_union_id=:labourUnionId ");
		}
		String sqlDataQuery = "select distinct u.nickname,u.sex,u.phone,u.user_type,a.id,a.real_name,a.idcard,a.creation_time,a.idcard_img,a.auth_result "
				+ sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = "select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery);
		// 返回的参数直接用sql方式返回
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Query countQuery = em.createNativeQuery(sqlCountQuery);
		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);
		
		// 查询条件
		if (userSingleAuth != null && !StringUtils.isEmpty(userSingleAuth.getAuthResult())) {
			dataQuery.setParameter("authResult", userSingleAuth.getAuthResult());
			countQuery.setParameter("authResult", userSingleAuth.getAuthResult());
		}
		
		// 账号对应工会id
		if(userSingleAuth != null && StringUtils.isNotEmpty(userSingleAuth.getLabourUnionId())){
			dataQuery.setParameter("labourUnionId", userSingleAuth.getLabourUnionId());
			countQuery.setParameter("labourUnionId", userSingleAuth.getLabourUnionId());
		}

		// 连表查询 需要用List<Map<String,Object>> 接收
		List<Map<String, Object>> listMap = dataQuery.getResultList();
		listMap = StringUtil.tranferMapsKeyToCamel(listMap, null);

		return new Result(listMap, Integer.valueOf(countQuery.getSingleResult().toString()));
	}

	@Override
	public void delete(MfUserSingleAuth t) {
		mfUserSingleAuthDao.delete(t);

	}

	@Override
	public MfUserSingleAuth queryByUserId(String userId) {
		return mfUserSingleAuthDao.findByUserId(userId);
	}

}
