package com.friends.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.friends.dao.MfUserDataDetailsDao;
import com.friends.entity.MfUserDataDetails;
import com.friends.entity.MfUserInfo;
import com.friends.entity.vo.MfUserDataDetailsDynamicQueryVo;
import com.friends.entity.vo.MfUserInfoDynamicQueryVo;
import com.friends.service.MfUserDataDetailsService;
import com.friends.utils.Result;
import com.friends.utils.StringUtil;

import sun.util.logging.resources.logging;

@Service
public class MfUserDataDetailsServiceImpl extends BaseService<MfUserDataDetailsDao, MfUserDataDetails>
		implements MfUserDataDetailsService {

	@Autowired
	MfUserDataDetailsDao mfUserDataDetailsDao;
	@Autowired
	EntityManager em;

	// 通用方法在base层已经有了

	// 动态结构查询
	// Spring-data-jpa-specification形式较简单,但只适用于单表动态结构查询
	// entityManager形式稍复杂，但能实现多表动态约束查询。但多表时jpa返回的是object[][],需要借助工具转成对象。
	// entityManager方式
	@SuppressWarnings("unchecked")
	public Result queryDynamic(MfUserDataDetailsDynamicQueryVo userInformationVo) {
		int page = userInformationVo.getPage();
		int pageSize = userInformationVo.getPageSize();
		MfUserDataDetails userInformation = userInformationVo.getT();
		Map<String, Object> map = new HashMap<String, Object>();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_user_data_details t where 1=1 ");

		if(null != userInformation && StringUtils.isNoneEmpty(userInformation.getIdcard())){
			sqlCommonBody.append("and idcard=:idcard ");
		}
		if(null != userInformation && StringUtils.isNoneEmpty(userInformation.getPhone())){
			sqlCommonBody.append("and phone=:phone ");
		}

		String sqlDataQuery = " select t.*  " + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfUserDataDetails.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);

		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		if(null != userInformation && StringUtils.isNoneEmpty(userInformation.getIdcard())){
			dataQuery.setParameter("idcard",userInformation.getIdcard());
			countQuery.setParameter("idcard",userInformation.getIdcard());
		}
		if(null != userInformation && StringUtils.isNoneEmpty(userInformation.getPhone())){
			sqlCommonBody.append("and phone=:phone");
			dataQuery.setParameter("phone",userInformation.getPhone());
			countQuery.setParameter("phone",userInformation.getPhone());
		}
		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<MfUserDataDetails> list = dataQuery.getResultList();

		return new Result(list, Integer.valueOf(countQuery.getSingleResult().toString()));
	}

	// Specification的方式
	public Result queryDynamicSpecification(MfUserDataDetailsDynamicQueryVo userInformationVo) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		int page = userInformationVo.getPage();
		int pageSize = userInformationVo.getPageSize();
		MfUserDataDetails userInformation = userInformationVo.getT();
		PageRequest pageRequest = new PageRequest(page, pageSize, new Sort(Direction.DESC, "createTime"));

		// 构造动态约束条件
		Specification<MfUserDataDetails> spec = new Specification<MfUserDataDetails>() {
			@Override
			public Predicate toPredicate(Root<MfUserDataDetails> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate restraint = cb.and();

				/**
				 * if
				 * (!StringUtils.isEmpty(userInformation.getUserInformationName(
				 * ))) { Predicate p =
				 * cb.equal(root.get("userInformationName").as(String.class),
				 * userInformation.getUserInformationName()); restraint =
				 * cb.and(p, restraint); }
				 */
				return restraint;
			}
		};
		Page<MfUserDataDetails> usersPage = mfUserDataDetailsDao.findAll(spec, pageRequest);
		int total = (int) mfUserDataDetailsDao.count(spec);
		hashMap.put("usersPage", usersPage);
		hashMap.put("total", total);
		return new Result(200, usersPage.getContent(), total);
	}

	@Override
	public void delete(String id) {
		mfUserDataDetailsDao.delete(id);
	}

	@Override
	public MfUserDataDetails queryById(String id) {
		return mfUserDataDetailsDao.findOne(id);
	}

	/**
	 * 根据用户id查询用户信息表
	 * 
	 * 
	 */
	@Override
	public MfUserDataDetails findUserInformationByuId(String uId) {
		MfUserDataDetails userDataDetails = mfUserDataDetailsDao.findUserInformationByuId(uId);
		// 敏感信息不能传到前台
		userDataDetails.setIdcard("******************");
		return userDataDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MfUserDataDetails> queryLaoxiang(String hometown,MfUserInfoDynamicQueryVo sysUserinfoVo) {
		int page = sysUserinfoVo.getPage();
		int pageSize = sysUserinfoVo.getPageSize();
		MfUserInfo sysUserinfo = sysUserinfoVo.getT();
		Map<String, Object> map = new HashMap<String, Object>();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_user_data_details t left join mf_user_info i on i.id = t.user_id where 1=1 ");

		// 查询故乡是否为空
		if (!StringUtils.isEmpty(hometown)) {
			sqlCommonBody.append(" and t.hometown like :hometown ");
		}
		// 查询异性
		if (sysUserinfo.getSex() != null) {
			sqlCommonBody.append(" and i.sex !=:sex ");
		}
		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfUserDataDetails.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);

		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		// 查询故乡是否为空
		if (!StringUtils.isEmpty(hometown)) {
			dataQuery.setParameter("hometown", "%" + hometown.substring(0, 2) + "%");
			countQuery.setParameter("hometown", "%" + hometown.substring(0, 2) + "%");
		}
		// 查询异性
		if (sysUserinfo.getSex() != null) {
			dataQuery.setParameter("sex", sysUserinfo.getSex());
			countQuery.setParameter("sex", sysUserinfo.getSex());
		}
		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<MfUserDataDetails> list = dataQuery.getResultList();
		return list;
	}

	@Override
	public Integer findInformationByuId(String id) {
		return mfUserDataDetailsDao.findInformationByuId(id);
	}

	@Override
	public Result queryByMateStandard(String sex, String[] mateStandard, PageRequest pageRequest) {
		Integer page = pageRequest.getPageNumber();
		Integer pageSize = pageRequest.getPageSize();

		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_user_data_details u where 1=1 and u.sex !=:sex");

		/*************
		 * // 年龄段 String[] ageGroup = mateStandard[0].replace("岁",
		 * "").trim().split("~"); String startAge = ageGroup[0], endAge =
		 * ageGroup[1]; // 身高段 String[] heightGroup =
		 * mateStandard[1].replace("cm", "").trim().split("~"); String
		 * startHeight = heightGroup[0], endHeight = heightGroup[1]; // 省份
		 * String province = mateStandard[2]; // 学历 String education =
		 * mateStandard[3]; // 房车 String houseCar = mateStandard[4];
		 * 
		 * sqlCommonBody.append(" and u.age >=:startAge and u.age <=:endAge ");
		 * sqlCommonBody.append(
		 * " and u.height >=:startHeight and u.height <=:endHeight ");
		 * sqlCommonBody.append(" and u.education=:education ");
		 * sqlCommonBody.append(" and u.hometown=:province ");
		 * sqlCommonBody.append(" and u.rv_family=:houseCar ");
		 * 
		 ********************/

		// 换一批 ：分页查询
		String sqlDataQuery = "select u.* " + sqlCommonBody.toString() + " limit :from, :to ";
		Query dataQuery = em.createNativeQuery(sqlDataQuery);
		// 返回的参数直接用sql方式返回
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		// 参数注入 分页参数
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);
		dataQuery.setParameter("sex", sex);

		/***********
		 * // 查询参数注入
		 * 
		 * dataQuery.setParameter("startAge", startAge);
		 * dataQuery.setParameter("endAge", endAge);
		 * dataQuery.setParameter("startHeight", startHeight);
		 * dataQuery.setParameter("endHeight", endHeight);
		 * dataQuery.setParameter("education", education);
		 * dataQuery.setParameter("province", province);
		 * dataQuery.setParameter("houseCar", houseCar);
		 ***************/

		// 连表查询 需要用List<Map<String,Object>> 接收
		List<Map<String, Object>> listMap = dataQuery.getResultList();
		listMap = StringUtil.tranferMapsKeyToCamel(listMap, null);
		return new Result(listMap);
	}

	@Override
	public Result queryByRandom(String sex, PageRequest pageRequest) {
		Integer page = pageRequest.getPageNumber();
		Integer pageSize = pageRequest.getPageSize();
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_user_data_details u left join mf_user_info s on(s.id=u.user_id) ");
		sqlCommonBody.append(" where 1=1 and m.image_type=1 ");
		if (StringUtils.isNotEmpty(sex)) {
			sqlCommonBody.append(" and s.sex !=:sex");
		}
		String sqlDataQuery = "select u.*,s.sex " + sqlCommonBody.toString() + " limit :from, :to ";
		Query dataQuery = em.createNativeQuery(sqlDataQuery);
		// 返回的参数直接用sql方式返回
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		// 参数注入 分页参数
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		if (StringUtils.isNotEmpty(sex)) {
			dataQuery.setParameter("sex", sex);
		}
		// 连表查询 需要用List<Map<String,Object>> 接收
		List<Map<String, Object>> listMap = dataQuery.getResultList();
		listMap = StringUtil.tranferMapsKeyToCamel(listMap, null);
		return new Result(listMap);
	}

	@Override
	public Result queryByReputationIntegral(PageRequest pageRequest, String userId) {
		Integer page = pageRequest.getPageNumber();
		Integer pageSize = pageRequest.getPageSize();
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_user_data_details u left join mf_user_info s on(s.id=u.user_id) ");
		sqlCommonBody.append(" left join mf_activity_integral i on(i.user_id=u.user_id) ");
		sqlCommonBody.append(" where 1=1 and i.point=100 ");
		sqlCommonBody.append(" and s.id !='" + userId + "' ");

		String sqlDataQuery = "select u.*,i.integral,i.point,s.name " + sqlCommonBody.toString()
				+ " order by i.integral desc limit :from, :to ";
		Query dataQuery = em.createNativeQuery(sqlDataQuery);

		// 返回的参数直接用sql方式返回
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		// 参数注入 分页参数
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		// 连表查询 需要用List<Map<String,Object>> 接收
		List<Map<String, Object>> listMap = dataQuery.getResultList();
		listMap = StringUtil.tranferMapsKeyToCamel(listMap, null);
		return new Result(listMap);
	}

	@Override
	public Result queryByParam(MfUserDataDetailsDynamicQueryVo userInformationVo, PageRequest pageRequest) {
		Integer page = pageRequest.getPageNumber();
		Integer pageSize = pageRequest.getPageSize();
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_user_data_details u where 1=1 ");
		sqlCommonBody.append(" and u.user_id !=:userId ");
		// 性别
		if (StringUtils.isNotEmpty(userInformationVo.getSex())) {
			sqlCommonBody.append(" and u.sex =:sex");
		}
		// 获取当前时间  年
		Calendar now = Calendar.getInstance();
		String year = (now.get(Calendar.YEAR)+1)+"-01"+"-01";
		if(!userInformationVo.getStartAge().equals(year)){
			sqlCommonBody.append(" and DATE(u.birth_time) <= DATE(:startBirthTime) and DATE(u.birth_time) >= DATE(:endBirthTime) ");
		}
		// 身高
		if (!userInformationVo.getStartHeight().equals("0")) {
			sqlCommonBody.append(" and u.height >=:startHeight and u.height <=:endHeight ");
		}
		// 省份
		if (!userInformationVo.getProvince().contains("不限")) {
			sqlCommonBody.append(" and u.hometown like:hometown");
		}
		// 学历
		if (!userInformationVo.getDiploma().contains("不限")) {
			sqlCommonBody.append(" and u.education =:education ");
		}
		// 单位-岗位
		if(!userInformationVo.getUnitAttribute().equals("不限-不限")){
			String[] str = userInformationVo.getUnitAttribute().split("-");
			userInformationVo.setUnitAttribute(str[0]);
			userInformationVo.setPostNature(str[1]);
			sqlCommonBody.append(" and u.unit_attribute =:unitAttribute ");
			sqlCommonBody.append(" and u.post_nature =:postNature ");
		}

		String sqlDataQuery = "select u.* " + sqlCommonBody.toString() + " limit :from, :to ";
		Query dataQuery = em.createNativeQuery(sqlDataQuery);
		// 返回的参数直接用sql方式返回
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		// 参数注入 分页参数
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);
		dataQuery.setParameter("userId", userInformationVo.getT().getUserId());
		// 性别
		if (StringUtils.isNotEmpty(userInformationVo.getSex())) {
			dataQuery.setParameter("sex", userInformationVo.getSex());
		}
		// 年龄段
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(!userInformationVo.getStartAge().equals(year)){
				Date startBirthTime = dateFormat.parse(userInformationVo.getStartAge());
				Date endBirthTime = dateFormat.parse(userInformationVo.getEndAge());
				dataQuery.setParameter("startBirthTime", startBirthTime);
				dataQuery.setParameter("endBirthTime", endBirthTime);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 身高
		if (!userInformationVo.getStartHeight().equals("0")) {
			dataQuery.setParameter("startHeight", userInformationVo.getStartHeight());
			dataQuery.setParameter("endHeight", userInformationVo.getEndHeight());
		}
		// 省份
		if (!userInformationVo.getProvince().contains("不限")) {
			String hometown = userInformationVo.getProvince();
			String str = hometown.substring(0,hometown.length()-1);
			dataQuery.setParameter("hometown", "%" + str + "%");
		}
		// 学历
		if (!userInformationVo.getDiploma().contains("不限")) {
			dataQuery.setParameter("education", userInformationVo.getDiploma());
		}
		// 单位-岗位
		if(!userInformationVo.getUnitAttribute().equals("不限-不限")){
			dataQuery.setParameter("unitAttribute",userInformationVo.getUnitAttribute());
			dataQuery.setParameter("postNature",userInformationVo.getPostNature());
		}
		// 连表查询 需要用List<Map<String,Object>> 接收
		List<Map<String, Object>> listMap = dataQuery.getResultList();
		listMap = StringUtil.tranferMapsKeyToCamel(listMap, null);
		return new Result(listMap);
	}


	@Override
	public Result queryBySameCity(String hometown, PageRequest pageRequest,String userId) {
		int page = pageRequest.getPageNumber();
		int pageSize = pageRequest.getPageSize();
		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_user_data_details t where 1=1 and t.user_id !=:id");
		sqlCommonBody.append(" and t.hometown like :hometown");
		
		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfUserDataDetails.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);

		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);
		dataQuery.setParameter("hometown", "%"+hometown+"%");
		countQuery.setParameter("hometown", "%"+hometown+"%");
		dataQuery.setParameter("id", userId);
		countQuery.setParameter("id", userId);
		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<MfUserDataDetails> list = dataQuery.getResultList();
		return new Result(list,Integer.valueOf(countQuery.getSingleResult().toString()));
	}

	@Override
	public Result statisticsByTime(String startTime, String enTime) {
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append("select  ");
		sqlCommonBody.append("sum(case when t.user_id is not null then '1' end ) as registered, ");
		sqlCommonBody.append("sum(case when t.user_id is null then '1' end ) as unregistered, ");
		sqlCommonBody.append("sum(case when t.unit_attribute = '机关单位' then '1' end ) as agencyunit, ");
		sqlCommonBody.append("sum(case when t.unit_attribute = '事业单位' then '1' end ) as institution, ");
		sqlCommonBody.append("sum(case when t.unit_attribute = '企业' then '1' end ) as enterprise, ");
		sqlCommonBody.append("sum(case when t.unit_attribute = '其他' then '1' end ) as other, ");

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

		sqlCommonBody.append("sum(case when t.sex = '1' then '1' end ) as male, ");
		sqlCommonBody.append("sum(case when t.sex = '2' then '1' end ) as female, ");
		sqlCommonBody.append("sum(case when CONVERT(t.age,SIGNED) >= 18 and CONVERT(t.age,SIGNED) <= 24  then '1' end) as age1824, ");
		sqlCommonBody.append("sum(case when CONVERT(t.age,SIGNED) >= 25 and CONVERT(t.age,SIGNED) <= 29  then '1' end) as age2529, ");
		sqlCommonBody.append("sum(case when CONVERT(t.age,SIGNED) >= 30 and CONVERT(t.age,SIGNED) <= 34  then '1' end) as age3034, ");
		sqlCommonBody.append("sum(case when CONVERT(t.age,SIGNED) >= 35 and CONVERT(t.age,SIGNED) <= 39  then '1' end) as age3539, ");
		sqlCommonBody.append("sum(case when CONVERT(t.age,SIGNED) >= 40 and CONVERT(t.age,SIGNED) <= 60  then '1' end) as age4060  ");
		sqlCommonBody.append("from (select creation_time,user_id,unit_attribute,post_nature,sex,year(from_days(DATEDIFF(now(),birth_time))) as age from mf_user_data_details ) t ");
		sqlCommonBody.append("where 1=1 ");
		if(StringUtils.isNoneEmpty(startTime) || StringUtils.isNoneEmpty(enTime)){
			sqlCommonBody.append(" and creation_time between DATE(:startTime) and DATE(:enTime) ");
		}
		Query dataQuery = em.createNativeQuery(sqlCommonBody.toString());
		// 返回的参数直接用sql方式返回
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (StringUtils.isNoneEmpty(startTime)  && StringUtils.isNoneEmpty(enTime)) {
			dataQuery.setParameter("startTime", startTime);
			dataQuery.setParameter("enTime", enTime);
		}
		//  需要用List<Map<String,Object>> 接收
		List<Map<String, Object>> listMap = dataQuery.getResultList();
		listMap = StringUtil.tranferMapsKeyToCamel(listMap, null);
		return new Result(listMap);
	}


}
