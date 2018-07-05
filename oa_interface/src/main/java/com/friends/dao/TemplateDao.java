package com.friends.dao;

import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.friends.entity.Template;

/**
 * Dao层模板 名称：TemplateDao.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2017年5月5日 上午11:42:05 <br>
 * 
 * @since 2017年5月5日
 * @authour ChenRenhao
 */
public interface TemplateDao extends BaseDao<Template> {

	// 单表的写入操作建议使用baseService
	// 这里只是示例通过sql更新，删除，插入。 写操作需要加上事务
	@Modifying
	@Transactional
	@Query(value = "update template t set t.template_name = ?1 where t.template_name = ?2", nativeQuery = true)
	int update(String newName, String originName);

	// 查询 精确约束和模糊约束下的单表查询建议使用baseService下example模式查询
	// 涉及动态结构查询 统一采用在Service中通过entityManager进行查询 支持多表 可扩展性强 推荐
	// 涉及范围约束下的单表查询就得使用下面几种方式，sql是通用标准，所以推荐使用nativeQuery;

	// 原生sql方式查询
	@Query(value = "select * from template where template_name like %?1% and create_time<?2", nativeQuery = true)
	List<Template> queryByNameAndCreateTime(String templateName, Date createTime);

	// hql方式查询
	@Query(value = "from Template")
	List<Template> queryAll();

	// 借助动态代理根据方法名称自动生成sql
	List<Template> findByTemplateType(Integer temlateType);

}
