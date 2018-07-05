package com.friends.service.impl;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.friends.dao.MfUserTipoffDao;
import com.friends.entity.MfUserTipoff;
import com.friends.entity.vo.MfUserTipoffDynamicQueryVo;
import com.friends.service.MfUserTipoffService;
import com.friends.utils.Result;
import com.friends.utils.StringUtil;

@Service
public class MfUserTipoffServiceImpl extends BaseService<MfUserTipoffDao, MfUserTipoff> implements MfUserTipoffService {

	@Autowired
	MfUserTipoffDao mfUserTipoffDao;
	@Autowired
	EntityManager em;

	@Override
	public void delete(MfUserTipoff t) {
		mfUserTipoffDao.delete(t);
	}
	
	// 动态结构查询
	// Spring-data-jpa-specification形式较简单,但只适用于单表动态结构查询
	// entityManager形式稍复杂，但能实现多表动态约束查询。但多表时jpa返回的是object[][],需要借助工具转成对象。
	// entityManager方式
	@SuppressWarnings("unchecked")
	public Result queryDynamic1(MfUserTipoffDynamicQueryVo tipOffDynamicQueryVo) {
		int page = tipOffDynamicQueryVo.getPage();
		int pageSize = tipOffDynamicQueryVo.getPageSize();
		MfUserTipoff tipOff = tipOffDynamicQueryVo.getT();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_user_tipoff t where 1=1 ");

		// 遍历每个字段 发现约束类型不为NO的，则根据情况进行约束
		// 如果判空也得分类型：字符串与非字符串
		// 时间要模糊约束怎么办

		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfUserTipoff.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);
		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<MfUserTipoff> list = dataQuery.getResultList();
		// BeanHelper.objectsListToEntity(List<Object[]> objectsList, Class<T>
		// c)
		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(list, total);
	}

	@Override
	public Result queryDynamic(MfUserTipoffDynamicQueryVo tipOffDynamicQueryVo) {
		int page = tipOffDynamicQueryVo.getPage();
		int pageSize = tipOffDynamicQueryVo.getPageSize();
		MfUserTipoff tipOff = tipOffDynamicQueryVo.getT();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_user_tipoff t left join mf_user_info u on(u.id=t.by_tip_off_user_id) ");
		sqlCommonBody.append(" left join mf_user_info s on(s.id = t.tip_off_user_id) where 1=1 ");

		// 遍历每个字段 发现约束类型不为NO的，则根据情况进行约束
		// 如果判空也得分类型：字符串与非字符串
		// 时间要模糊约束怎么办

		if (tipOff != null && !"3".equals(tipOff.getStatus())) {
			sqlCommonBody.append(" and t.status=:status");
		}
		if (tipOff != null && tipOff.getTypes() != null) {
			sqlCommonBody.append(" and t.types=:type");
		}

		String sql = "select t.*,u.name as uname,u.nickname as unickname,u.sex as usex ,s.name as uname2,s.nickname as unickname2,s.sex as usex2";
		String sqlDataQuery = sql + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery);
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Query countQuery = em.createNativeQuery(sqlCountQuery);
		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		if (tipOff != null && !"3".equals(tipOff.getStatus())) {
			dataQuery.setParameter("status", tipOff.getStatus());
			countQuery.setParameter("status", tipOff.getStatus());
		}
		
		if (tipOff != null && tipOff.getTypes() != null) {
			dataQuery.setParameter("type", tipOff.getTypes());
			countQuery.setParameter("type", tipOff.getTypes());
		}
		
		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		// 连表查询 需要用List<Map<String,Object>> 接收
		List<Map<String, Object>> listMap = dataQuery.getResultList();
		//
		listMap = StringUtil.tranferMapsKeyToCamel(listMap, null);

		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(listMap, total);
	}

	
	// 根据被投诉ID 获取 被投诉人信息
	@Override
	public Result queryByBeComplainsId(String id){
		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_user_tipoff t ");
		sqlCommonBody.append(" left join mf_user_data_details u on(t.by_tip_off_user_id=u.user_id) where 1=1 ");
		sqlCommonBody.append(" and t.by_tip_off_user_id in (select by_tip_off_user_id from mf_user_tipoff where tip_off_user_id=:id) ");
		
		String sqlDataQuery = "select t.*,u.nickname,u.head_img " + sqlCommonBody.toString();
		String sqlCountQuery = "select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery);
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Query countQuery = em.createNativeQuery(sqlCountQuery);
		
		dataQuery.setParameter("id", id);
		countQuery.setParameter("id", id);
		List<Map<String, Object>> listMap = dataQuery.getResultList();
		//
		listMap = StringUtil.tranferMapsKeyToCamel(listMap, null);
		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(listMap, total);
	}

	@Override
	public Result queryComplaintDetails(String id) {
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append("select t.*,u.nickname,u.head_img,u.sex,u.height,u.birth_time from mf_user_tipoff t");
		sqlCommonBody.append(" left join mf_user_data_details u on(t.by_tip_off_user_id=u.user_id)");
		sqlCommonBody.append(" where 1=1 and t.id=:id ");
		Query dataQuery = em.createNativeQuery(sqlCommonBody.toString());
		// 连表查询  加上此参数,否则createNativeQuery(sql,xxx.class)后面加上接收的实体类. 
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		dataQuery.setParameter("id", id);
		// 连表查询   用listMap接收， 单表可以就实体接收，em.createNativeQuery(sql, MfUserTipoff.class);
		List<Map<String, Object>> listMap = dataQuery.getResultList();
		//
		listMap = StringUtil.tranferMapsKeyToCamel(listMap, null);
		return new Result(listMap.get(0));
	}
	
}
