package com.friends.service.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.friends.dao.BaseDao;
import com.friends.entity.MfUserAttention;
import com.friends.utils.BeanHelper;

/**
 * 
 * 名称：BaseService.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2017年5月5日 下午4:37:52 <br>
 * 
 * @since 2017年5月5日
 * @authour ChenRenhao
 */
@Service
public abstract class BaseService<D extends BaseDao<T>, T> {
	@Autowired
	D dao;
	@Autowired
	EntityManager em;

	/**
	 * 新增
	 * 
	 * @param t
	 * @return
	 * @param id
	 *            String 唯一标识
	 * @param personid
	 *            String 用户唯一标识
	 * @return BaseReturn 基本返回对象
	 * @变更记录 2017年5月5日 下午4:38:00 ChenRenhao创建
	 */
	public T add(T t) {
		return dao.save(t);
	}

	/**
	 * 删除
	 * 
	 * @param t
	 * @param id
	 *            String 唯一标识
	 * @param personid
	 *            String 用户唯一标识
	 * @return BaseReturn 基本返回对象
	 * @变更记录 2017年5月5日 下午4:38:29 ChenRenhao创建
	 */
	public void delete(String id) {
		dao.delete(id);
	}

	/**
	 * 更新 只更新不为空的字段 实体 id不允许为空
	 * 
	 * @param t
	 * @param id
	 * @return
	 * @param id
	 *            String 唯一标识
	 * @param personid
	 *            String 用户唯一标识
	 * @return BaseReturn 基本返回对象
	 * @变更记录 2017年5月5日 下午4:38:48 ChenRenhao创建
	 */
	public T updateSelective(T t) {
		String id = getPKValue(t);
		T one = dao.findOne(id);
		BeanHelper.copyPropertiesIgnoreNull(t, one);
		return dao.save(one);
	}

	/**
	 * 更新 更新包括内容为空的字段 实体 id不允许为空
	 * 
	 * @param t
	 * @param id
	 * @return
	 * @param id
	 *            String 唯一标识
	 * @param personid
	 *            String 用户唯一标识
	 * @return BaseReturn 基本返回对象
	 * @变更记录 2017年5月5日 下午4:38:48 ChenRenhao创建
	 */
	public T update(T t) {
		String id = getPKValue(t);
		T one = dao.findOne(id);
		BeanUtils.copyProperties(t, one);
		return dao.save(one);
	}

	/**
	 * 根据ID查询
	 * 
	 * @param id
	 * @return
	 * @param id
	 *            String 唯一标识
	 * @param personid
	 *            String 用户唯一标识
	 * @return BaseReturn 基本返回对象
	 * @变更记录 2017年5月5日 下午4:42:35 ChenRenhao创建
	 */
	public T queryById(String id) {
		return dao.findOne(id);
	}

	/**
	 * 根据探针查询 用途广泛的一个接口 建议使用
	 * 
	 * @param t
	 * @return
	 * @param id
	 *            String 唯一标识
	 * @param personid
	 *            String 用户唯一标识
	 * @return BaseReturn 基本返回对象
	 * @变更记录 2017年5月5日 下午4:42:47 ChenRenhao创建
	 */
	public List<T> queryByExample(T t) {
		Example<T> example = Example.of(t);
		return dao.findAll(example);
	}

	/**
	 * 根据探针查询 带分页 用途广泛的一个接口 建议使用
	 * 
	 * @param t
	 * @param pageRequest
	 * @return
	 * @param id
	 *            String 唯一标识
	 * @param personid
	 *            String 用户唯一标识
	 * @return BaseReturn 基本返回对象
	 * @变更记录 2017年5月5日 下午4:43:59 ChenRenhao创建
	 */
	public List<T> queryByExamplePageable(T t, PageRequest pageRequest) {
		Example<T> example = Example.of(t);
		Page<T> page = dao.findAll(example, pageRequest);
		return page.getContent();
	}

	// 批量操作
	// 批量新增
	@Transactional
	public void batchAdd(List<T> ts) {
		dao.save(ts);
	}

	// 批量删除
	@Transactional
	public void batchDelete(List<T> ts) {
		dao.deleteInBatch(ts);
	}

	public List<T> queryAll() {
		List<T> ts = dao.findAll();
		return ts;
	}

	// 删除所有
	public void deleteAll() {
		dao.deleteAll();
	}

	public String getPKName(Class entityClass) {
		Field[] fields = entityClass.getDeclaredFields();
		for (Field f : fields) {
			Annotation[] annotations = f.getAnnotations();
			if (annotations.length <= 0) {
				String name = f.getName();
				String setMethodName = "get" + StringUtils.left(name, 1).toUpperCase() + StringUtils.substring(name, 1);
				try {
					Method method = entityClass.getDeclaredMethod(setMethodName);
					annotations = method.getAnnotations();
				} catch (Exception e) {
					// do nothing.
				}
			}
			for (Annotation anno : annotations) {
				if (anno.toString().contains("@javax.persistence.Id()"))
					return f.getName();
			}
		}
		return null;
	}
	
	
	public String getPKValue(T t) {
		String name = getPKName(t.getClass());
		final BeanWrapper src = new BeanWrapperImpl(t);
		Object value = src.getPropertyValue(name);
		if (value != null) {
			return value.toString();
		} else {
			return null;
		}
	}

	public String getPKType(T t) {
		String name = getPKName(t.getClass());
		final BeanWrapper src = new BeanWrapperImpl(t);
		Object type = src.getPropertyType(name);
		if (type != null) {
			return type.toString();
		} else {
			return null;
		}
	}

}
