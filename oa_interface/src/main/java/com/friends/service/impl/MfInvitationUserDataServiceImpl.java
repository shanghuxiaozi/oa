package com.friends.service.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.friends.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.friends.dao.MfInvitationUserDataDao;
import com.friends.entity.MfInvitationUserData;
import com.friends.entity.vo.MfInvitationUserDataDynamicQueryVo;
import com.friends.service.MfInvitationUserDataService;
import com.friends.utils.Result;

@Service
public class MfInvitationUserDataServiceImpl extends BaseService<MfInvitationUserDataDao, MfInvitationUserData>
		implements MfInvitationUserDataService {

	@Autowired
	MfInvitationUserDataDao mfInvitationUserDataDao;
	@Autowired
	EntityManager em;

	// 通用方法在base层已经有了

	// 动态结构查询
	// Spring-data-jpa-specification形式较简单,但只适用于单表动态结构查询
	// entityManager形式稍复杂，但能实现多表动态约束查询。但多表时jpa返回的是object[][],需要借助工具转成对象。
	// entityManager方式
	@SuppressWarnings("unchecked")
	public Result queryDynamic(MfInvitationUserDataDynamicQueryVo mfInvitationUserDataDynamicQueryVo) {
		int page = mfInvitationUserDataDynamicQueryVo.getPage();
		int pageSize = mfInvitationUserDataDynamicQueryVo.getPageSize();
		MfInvitationUserData mfInvitationUserData = mfInvitationUserDataDynamicQueryVo.getT();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_invitation_user_data t where 1=1 ");
		
		if(mfInvitationUserData != null && StringUtils.isNotEmpty(mfInvitationUserData.getIdcard())){
			sqlCommonBody.append(" and idcard=:idcardNumber ");
		}
		if(mfInvitationUserData != null && StringUtils.isNotEmpty(mfInvitationUserData.getPhone())){
			sqlCommonBody.append(" and phone=:phoneNumber ");
		}
		// 查询出已注册用户  未注册用户   0全部  1注册的 2未注册的
		if(null != mfInvitationUserData && !mfInvitationUserData.getUserId().equals("0")){
			if(mfInvitationUserData.getUserId().equals("1")){
				sqlCommonBody.append(" and user_id is not null ");
			}else if(mfInvitationUserData.getUserId().equals("2")){
				sqlCommonBody.append(" and user_id is null ");
			}
		}
		// 工作单位
		if(null != mfInvitationUserData && StringUtils.isNoneEmpty(mfInvitationUserData.getWorkUnit())){
			sqlCommonBody.append(" and work_unit like :wordUnit ");
		}
		// 单位属性  岗位性质
		if(null != mfInvitationUserData && !"请选择".equals(mfInvitationUserData.getUnitAttribute())){
			sqlCommonBody.append(" and unit_attribute=:unitAttribute and post_nature=:postNature ");
		}

		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfInvitationUserData.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);
		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);
		
		if(mfInvitationUserData != null && StringUtils.isNotEmpty(mfInvitationUserData.getIdcard())){
			dataQuery.setParameter("idcardNumber", mfInvitationUserData.getIdcard());
			countQuery.setParameter("idcardNumber", mfInvitationUserData.getIdcard());
		}
		if(mfInvitationUserData != null && StringUtils.isNotEmpty(mfInvitationUserData.getPhone())){
			dataQuery.setParameter("phoneNumber", mfInvitationUserData.getPhone());
			countQuery.setParameter("phoneNumber", mfInvitationUserData.getPhone());
		}
		// 工作单位
		if(null != mfInvitationUserData && StringUtils.isNoneEmpty(mfInvitationUserData.getWorkUnit())){
			dataQuery.setParameter("wordUnit", "%"+mfInvitationUserData.getWorkUnit()+"%");
			countQuery.setParameter("wordUnit", "%"+mfInvitationUserData.getWorkUnit()+"%");
		}
		// 单位属性  岗位性质
		if(null != mfInvitationUserData && !"请选择".equals(mfInvitationUserData.getUnitAttribute())){
			dataQuery.setParameter("unitAttribute", mfInvitationUserData.getUnitAttribute());
			countQuery.setParameter("unitAttribute", mfInvitationUserData.getUnitAttribute());
			dataQuery.setParameter("postNature", mfInvitationUserData.getPostNature());
			countQuery.setParameter("postNature", mfInvitationUserData.getPostNature());
		}
		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<MfInvitationUserData> list = dataQuery.getResultList();
		// BeanHelper.objectsListToEntity(List<Object[]> objectsList, Class<T>
		// c)
		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(list, total);
	}

	@Override
	public void delete(MfInvitationUserData t) {
		mfInvitationUserDataDao.delete(t);
	}

	@Override
	public MfInvitationUserData queryByIdcard(String idcard) {
		return mfInvitationUserDataDao.findByIdcard(idcard);
	}

	@Override
	public MfInvitationUserData queryByUserId(String userId) {
		return mfInvitationUserDataDao.findByUserId(userId);
	}

	@Override
	public List<MfInvitationUserData> queryByParam(String idcard, String phone, String status,String unitAttribute,String postNature,String wordUnit) {
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_invitation_user_data t where 1=1 ");
		// 身份证号码
		if(StringUtils.isNoneEmpty(idcard)){
			sqlCommonBody.append(" and idcard=:idcardNumber ");
		}
		// 手机号码
		if(StringUtils.isNoneEmpty(phone)){
			sqlCommonBody.append(" and phone=:phoneNumber ");
		}
		// 查询出已注册用户  未注册用户   0全部  1注册的 2未注册的
		if(StringUtils.isNoneEmpty(status) && !status.equals("0")){
			if(status.equals("1")){
				sqlCommonBody.append(" and user_id is not null ");
			}else if(status.equals("2")){
				sqlCommonBody.append(" and user_id is null ");
			}
		}
		// 工作单位
		if(StringUtils.isNoneEmpty(wordUnit)){
			sqlCommonBody.append(" and work_unit like :wordUnit ");
		}
		// 单位属性  岗位性质
		if(!"请选择".equals(unitAttribute) && !"请选择".equals(postNature)){
			sqlCommonBody.append(" and unit_attribute=:unitAttribute and post_nature=:postNature ");
		}
		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfInvitationUserData.class);
		if(StringUtils.isNoneEmpty(idcard)){
			dataQuery.setParameter("idcardNumber", idcard);
		}
		if(StringUtils.isNoneEmpty(phone)) {
			dataQuery.setParameter("phoneNumber", phone);
		}
		// 工作单位
		if(StringUtils.isNoneEmpty(wordUnit)){
			dataQuery.setParameter("wordUnit", "%"+wordUnit+"%");
		}
		if(!"请选择".equals(unitAttribute) && !"请选择".equals(postNature)){
			dataQuery.setParameter("unitAttribute", unitAttribute);
			dataQuery.setParameter("postNature", postNature);
		}
		List<MfInvitationUserData> list = dataQuery.getResultList();
		return list;
	}

	@Override
	public Result statisticsData() {
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append("select ");
		sqlCommonBody.append("sum(case when t.user_id is not null then '1' end ) as 'registered', ");
		sqlCommonBody.append("sum(case when t.user_id is null then '1' end ) as 'unregistered', ");
		sqlCommonBody.append("sum(case when t.unit_attribute = '机关单位' then '1' end ) as 'agencyunit', ");
		sqlCommonBody.append("sum(case when t.unit_attribute = '事业单位' then '1' end ) as 'institution', ");
		sqlCommonBody.append("sum(case when t.unit_attribute = '企业' then '1' end ) as 'enterprise', ");
		sqlCommonBody.append("sum(case when t.unit_attribute = '其他' then '1' end ) as 'other', ");

		sqlCommonBody.append("sum(case when t.post_nature='公务员' and t.unit_attribute='机关单位' then '1' end ) as 'agencyunit_servant', ");
		sqlCommonBody.append("sum(case when t.post_nature='事业单位职员' and t.unit_attribute='机关单位' then '1' end ) as 'agencyunit_workers', ");
		sqlCommonBody.append("sum(case when t.post_nature='其他' and t.unit_attribute='机关单位' then '1' end ) as 'agencyunit_other', ");

		sqlCommonBody.append("sum(case when t.post_nature='公务员' and t.unit_attribute='事业单位' then '1' end ) as 'institution_servant', ");
		sqlCommonBody.append("sum(case when t.post_nature='事业单位职员' and t.unit_attribute='事业单位' then '1' end ) as 'institution_workers', ");
		sqlCommonBody.append("sum(case when t.post_nature='其他' and t.unit_attribute='事业单位' then '1' end ) as 'institution_other', ");

		sqlCommonBody.append("sum(case when t.post_nature='企业职工管理岗' and t.unit_attribute='企业' then '1' end ) as 'enterprise_manage', ");
		sqlCommonBody.append("sum(case when t.post_nature='企业职工销售岗' and t.unit_attribute='企业' then '1' end ) as 'enterprise_sales', ");
		sqlCommonBody.append("sum(case when t.post_nature='企业职工技术岗' and t.unit_attribute='企业' then '1' end ) as 'enterprise_technology', ");
		sqlCommonBody.append("sum(case when t.post_nature='企业职工生产岗' and t.unit_attribute='企业' then '1' end ) as 'enterprise_production', ");
		sqlCommonBody.append("sum(case when t.post_nature='企业职工研发岗' and t.unit_attribute='企业' then '1' end ) as 'enterprise_research', ");
		sqlCommonBody.append("sum(case when t.post_nature='企业职工综合岗' and t.unit_attribute='企业' then '1' end ) as 'enterprise_comprehensive', ");
		sqlCommonBody.append("sum(case when t.post_nature='其他' and t.unit_attribute='企业' then '1' end ) as 'enterprise_other', ");

		sqlCommonBody.append("sum(case when t.sex = '1' then '1' end ) as 'male', ");
		sqlCommonBody.append("sum(case when t.sex = '2' then '1' end ) as 'female', ");
		sqlCommonBody.append("sum(case when CONVERT(t.age,SIGNED) >= 18 and CONVERT(t.age,SIGNED) <= 24  then '1' end) as 'age1824', ");
		sqlCommonBody.append("sum(case when CONVERT(t.age,SIGNED) >= 25 and CONVERT(t.age,SIGNED) <= 29  then '1' end) as 'age2529', ");
		sqlCommonBody.append("sum(case when CONVERT(t.age,SIGNED) >= 30 and CONVERT(t.age,SIGNED) <= 34  then '1' end) as 'age3034', ");
		sqlCommonBody.append("sum(case when CONVERT(t.age,SIGNED) >= 35 and CONVERT(t.age,SIGNED) <= 39  then '1' end) as 'age3539', ");
		sqlCommonBody.append("sum(case when CONVERT(t.age,SIGNED) >= 40 and CONVERT(t.age,SIGNED) <= 60  then '1' end) as 'age4060' ");
		sqlCommonBody.append(" from ( select user_id,unit_attribute,post_nature,sex,year(from_days(DATEDIFF(now(),birth_time))) as 'age' from mf_invitation_user_data ) t where 1=1 ");

		Query dataQuery = em.createNativeQuery(sqlCommonBody.toString());
		// 返回的参数直接用sql方式返回
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		//  需要用List<Map<String,Object>> 接收
		List<Map<String, Object>> listMap = dataQuery.getResultList();
		listMap = StringUtil.tranferMapsKeyToCamel(listMap, null);
		return new Result(listMap);
	}

}
