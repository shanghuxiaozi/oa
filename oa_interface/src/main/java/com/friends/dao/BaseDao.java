package com.friends.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 
 * 名称：BaseDao.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2017年3月21日 下午4:46:58 <br>
 * @since  2017年3月21日
 * @authour 陈仁浩
 */
@NoRepositoryBean
public interface BaseDao<T> extends JpaRepository<T, String>,JpaSpecificationExecutor<T>{

	
	
	
}
