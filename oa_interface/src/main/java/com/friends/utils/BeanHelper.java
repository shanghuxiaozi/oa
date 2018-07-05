package com.friends.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;


public class BeanHelper {
	/**
	 * source的非空同名属性拷贝到target中去 (从左拷到右)
	 * 
	 * @param source
	 * @param target
	 * @param id
	 *            String 唯一标识
	 * @param personid
	 *            String 用户唯一标识
	 * @return BaseReturn 基本返回对象
	 * @变更记录 2017年2月17日 上午11:41:06 陈仁浩创建
	 */
	public static void copyPropertiesIgnoreNull(Object source, Object target) {
		// 获取一个Bean的非空属性
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		String[] nullPropertyNames = emptyNames.toArray(result);
		BeanUtils.copyProperties(source, target, nullPropertyNames);
	}

	/**
	 * 此工具用于解决jpa查询多表返回的List<Object[]缺少key的问题 用来将List<Object[]转成相应实体；
	 * 注意：T的属性顺序和数量要与select出的字段要相同
	 * 
	 * @param objectsList
	 * @param c
	 * @return
	 * @param id
	 *            String 唯一标识
	 * @param personid
	 *            String 用户唯一标识
	 * @return BaseReturn 基本返回对象
	 * @变更记录 2017年2月22日 上午8:45:01 陈仁浩创建
	 */
	public static <T> List<T> objectsListToEntity(List<Object[]> objectsList, Class<T> c) {
		try {
			// 遍历List<Object[]>
			List<T> entities = new ArrayList<T>();
			for (Object[] objects : objectsList) {
				T t = c.newInstance();
				Field[] fields = t.getClass().getDeclaredFields();
				int i = 0;
				for (Field field : fields) {
					if (objects[i] != null) {
						field.setAccessible(true);
						Class<?> type = field.getType();
						String typeName = type.toString();
						// 得到此属性的类型
						String objectTypeName = objects[i].getClass().getTypeName();
						// 得到数据库对应字段类型
						if (objectTypeName.endsWith("BigDecimal")) {
							// 判断转为Integer/Long/Double
							if (typeName.endsWith("Double")) {
								field.set(t, Double.valueOf(objects[i].toString()));
							} else if (typeName.endsWith("Integer")) {
								field.set(t, Integer.valueOf(objects[i].toString()));
							} else if (typeName.endsWith("Long")) {
								field.set(t, Long.valueOf(objects[i].toString()));
							}
						} else {
							// 其他类型可以自动转换
							field.set(t, objects[i]);
						}
					}
					i++;
				}
				entities.add(t);
			}
			return entities;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    
	//
	public static void convertMapToObject(Map<String, Object> source, Object target) {
		ConvertUtils.register(new Converter() {
			public Object convert(Class type, Object value) {
				if (value instanceof Date) {
					return value;
				}
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try {
					return simpleDateFormat.parse(value.toString());
				} catch (ParseException e) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					try{
						return dateFormat.parse(value.toString());
					}catch (ParseException e2) {
						e2.printStackTrace();
					}
				}
				return null;
			}
		}, Date.class);
		try {
			org.apache.commons.beanutils.BeanUtils.populate(target, source);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
