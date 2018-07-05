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

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.friends.dao.TemplateDao;
import com.friends.entity.Template;
import com.friends.entity.vo.TemplateVo;
import com.friends.service.TemplateService;
import com.friends.utils.MapsToLists;
import com.friends.utils.Result;
import com.friends.utils.StringUtil;

@Service
@Transactional
public class TemplateServiceImpl extends BaseService<TemplateDao, Template> implements TemplateService {

	@Autowired
	TemplateDao templateDao;
	@Autowired
	EntityManager em;

	// 通用方法在base层已经有了

	// 动态结构查询
	// Spring-data-jpa-specification形式较简单,但只适用于单表动态结构查询
	// entityManager形式稍复杂，但能实现多表动态约束查询。但多表时jpa返回的是object[][],需要借助工具转成对象。
	// entityManager方式
	@SuppressWarnings("unchecked")
	public Result queryDynamic(TemplateVo templateVo) {
		int page = templateVo.getPage();
		int pageSize = templateVo.getPageSize();
		Template template = templateVo.getT();
		Map<String, Object> map = new HashMap<String, Object>();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from template t where 1=1 ");

		// 模板名称
		if (!StringUtils.isEmpty(template.getTemplateName())) {
			sqlCommonBody.append(" and t.template_name like :template_name ");
			map.put("template_name", "%" + template.getTemplateName() + "%");
		}

		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, Template.class);
		Query dataQuerys = em.createNativeQuery(sqlDataQuery);
		dataQuerys.setParameter("from", (page * pageSize));
		dataQuerys.setParameter("to", pageSize);
		dataQuerys.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> maps = dataQuerys.getResultList();
		List<Map<String, Object>> newMaps = StringUtil.tranferMapsKeyToCamel(maps, null);
		try {
			List<Template> listS = MapsToLists.listMapToListBean(maps, Template.class);
			System.out.println(JSON.toJSONString(listS));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Query countQuery = em.createNativeQuery(sqlCountQuery);

		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		// 模板名称
		for (String key : map.keySet()) {
			dataQuery.setParameter(key, map.get(key));
			countQuery.setParameter(key, map.get(key));
		}

		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<Template> list = dataQuery.getResultList();

		return new Result(list, Integer.valueOf(countQuery.getSingleResult().toString()));
	}

	// Specification的方式
	public Result queryDynamicSpecification(TemplateVo templateVo) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		int page = templateVo.getPage();
		int pageSize = templateVo.getPageSize();
		Template template = templateVo.getT();
		PageRequest pageRequest = new PageRequest(page, pageSize, new Sort(Direction.DESC, "createTime"));

		// 构造动态约束条件
		Specification<Template> spec = new Specification<Template>() {
			@Override
			public Predicate toPredicate(Root<Template> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate restraint = cb.and();

				if (!StringUtils.isEmpty(template.getTemplateName())) {
					Predicate p = cb.equal(root.get("templateName").as(String.class), template.getTemplateName());
					restraint = cb.and(p, restraint);
				}
				return restraint;
			}
		};
		Page<Template> usersPage = templateDao.findAll(spec, pageRequest);
		int total = (int) templateDao.count(spec);
		hashMap.put("usersPage", usersPage);
		hashMap.put("total", total);
		return new Result(200, usersPage.getContent(), total);
	}

}
