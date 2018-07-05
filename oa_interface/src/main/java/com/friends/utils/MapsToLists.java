package com.friends.utils;

//关键包引用列举一下
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class MapsToLists {
	/**
	 * 将 Map对象转化为JavaBean 此方法已经测试通过
	 * 
	 * @author wyply115
	 * @param type
	 *            要转化的类型
	 * @param map
	 * @return Object对象
	 * @version 2016年3月20日 11:03:01
	 */
	public static <T> T convertMap2Bean(Map map, Class T) throws Exception {
		if (map == null || map.size() == 0) {
			return null;
		}
		BeanInfo beanInfo = Introspector.getBeanInfo(T);
		T bean = (T) T.newInstance();
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0, n = propertyDescriptors.length; i < n; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			/*String upperPropertyName = propertyName.toUpperCase();*/
			if (map.containsKey((Object)propertyName)) {
				Object value = map.get(propertyName);
				// 这个方法不会报参数类型不匹配的错误。
				BeanUtils.copyProperty(bean, propertyName, value);
				// 用这个方法对int等类型会报参数类型不匹配错误，需要我们手动判断类型进行转换，比较麻烦。
				// descriptor.getWriteMethod().invoke(bean, value);
				// 用这个方法对时间等类型会报参数类型不匹配错误，也需要我们手动判断类型进行转换，比较麻烦。
				// BeanUtils.setProperty(bean, propertyName, value);
			}
		}
		return bean;
	}

	/**
	 * 将 JavaBean对象转化为 Map 此方法未测试
	 * 
	 * @author wyply115
	 * @param bean
	 *            要转化的类型
	 * @return Map对象
	 * @version 2016年3月20日 11:03:01
	 */
	public static Map beanToMap(Object bean) throws Exception {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0, n = propertyDescriptors.length; i < n; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

	/**
	 * 将 List<Map>对象转化为List<JavaBean> 此方法已通过测试
	 * 
	 * @author wyply115
	 * @param type
	 *            要转化的类型
	 * @param map
	 * @return Object对象
	 * @version 2016年3月20日 11:03:01
	 */
	public static <T> List<T> listMapToListBean(List<Map<String, Object>> listMap, Class T) throws Exception {
		List<T> beanList = new ArrayList<T>();
		for (int i = 0, n = listMap.size(); i < n; i++) {
			Map<String, Object> map = listMap.get(i);
			T bean = convertMap2Bean(map, T);
			beanList.add(bean);
		}
		return beanList;
	}

	/**
	 * 将 List<JavaBean>对象转化为List<Map>
	 * 
	 * @author wyply115
	 * @param type
	 *            要转化的类型
	 * @param map
	 * @return Object对象
	 * @version 2016年3月20日 11:03:01
	 */
	public static List<Map<String, Object>> listBeanToListMap(List<Object> beanList) throws Exception {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (int i = 0, n = beanList.size(); i < n; i++) {
			Object bean = beanList.get(i);
			Map map = beanToMap(bean);
			mapList.add(map);
		}
		return mapList;
	}
}
