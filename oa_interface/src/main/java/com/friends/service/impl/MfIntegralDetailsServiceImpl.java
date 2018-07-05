package com.friends.service.impl;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.friends.dao.MfIntegralDetailsDao;
import com.friends.entity.MfIntegralDetails;
import com.friends.entity.vo.MfIntegralDetailsDynamicQueryVo;
import com.friends.service.MfIntegralDetailsService;
import com.friends.utils.Result;
import com.friends.utils.StringUtil;

@Service
public class MfIntegralDetailsServiceImpl extends BaseService<MfIntegralDetailsDao, MfIntegralDetails>
		implements MfIntegralDetailsService {

	@Autowired
	MfIntegralDetailsDao mfIntegralDetailsDao;
	@Autowired
	EntityManager em;

	// 通用方法在base层已经有了

	// 动态结构查询
	// Spring-data-jpa-specification形式较简单,但只适用于单表动态结构查询
	// entityManager形式稍复杂，但能实现多表动态约束查询。但多表时jpa返回的是object[][],需要借助工具转成对象。
	// entityManager方式
	@SuppressWarnings("unchecked")
	public Result queryDynamic(MfIntegralDetailsDynamicQueryVo sysIntegralDetailsDynamicQueryVo) {
		int page = sysIntegralDetailsDynamicQueryVo.getPage();
		int pageSize = sysIntegralDetailsDynamicQueryVo.getPageSize();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_integral_details t where 1=1 ");

		// 遍历每个字段 发现约束类型不为NO的，则根据情况进行约束
		// 如果判空也得分类型：字符串与非字符串
		// 时间要模糊约束怎么办

		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfIntegralDetails.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);
		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<MfIntegralDetails> list = dataQuery.getResultList();
		// BeanHelper.objectsListToEntity(List<Object[]> objectsList, Class<T>
		// c)
		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(list, total);

	}

	@Override
	public void delete(MfIntegralDetails t) {
		mfIntegralDetailsDao.delete(t);
	}

	@Override
	public Result queryIntegralDetailsByUserId(String id) {
		String sql = "select u.name,u.phone,u.sex,d.gain_integral,a.title from mf_user_info u "
				+ " left join mf_integral_details d on u.id=d.user_id "
				+ " left join mf_activity_release a on d.activity_id=a.id where u.id='" + id + "'";
		Query dataQuery = em.createNativeQuery(sql);
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<Map<String, Object>> listMap = dataQuery.getResultList();
		listMap = StringUtil.tranferMapsKeyToCamel(listMap, null);
		return new Result(listMap);
	}

	@Override
	public int queryCountByUserId(String id) {
		return mfIntegralDetailsDao.findCountByUserId(id);
	}

}
