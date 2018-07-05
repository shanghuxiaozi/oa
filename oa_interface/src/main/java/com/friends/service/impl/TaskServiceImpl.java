package com.friends.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.friends.dao.DailyDao;
import com.friends.entity.Daily;
import com.friends.entity.vo.DailyDynamicQueryVo;
import com.friends.service.PayService;
import com.friends.service.TaskService;
import com.friends.utils.Result;

@Service
public class TaskServiceImpl extends BaseService<DailyDao, Daily> implements TaskService {

	@Autowired
	DailyDao dailyDao;
	@Autowired
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	public Result queryDynamic(DailyDynamicQueryVo dailyDynamicQueryVo) {
		int page = dailyDynamicQueryVo.getPage();
		int pageSize = dailyDynamicQueryVo.getPageSize();
		Daily daily= dailyDynamicQueryVo.getT();
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from daily t where 1=1 ");
		
		if(daily != null && StringUtils.isNotEmpty(daily.getUserName())){
			sqlCommonBody.append(" and user_name=:userName ");
		}
		if(daily != null &&daily.getWriteTime() !=null){
			sqlCommonBody.append(" and write_time=:writeTime ");
		}
		
		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, Daily.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);
		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);
		
		if(daily != null && StringUtils.isNotEmpty(daily.getUserName())){
			dataQuery.setParameter("user_name", daily.getUserName());
			countQuery.setParameter("user_name", daily.getUserName());
		}
		if(daily != null && daily.getWriteTime()!=null){
			dataQuery.setParameter("write_time", daily.getWriteTime());
			countQuery.setParameter("write_time", daily.getWriteTime());
		}
		
		List<Daily> list = dataQuery.getResultList();
		// BeanHelper.objectsListToEntity(List<Object[]> objectsList, Class<T>
		// c)
		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(list, total);
	}

	@Override
	public Daily add(Daily t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Daily t) {
		// TODO Auto-generated method suub

	}

	@Override
	public Daily updateSelective(Daily t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Daily queryById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Daily> queryByExample(Daily t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Daily> queryByExamplePageable(Daily t, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchAdd(List<Daily> ts) {
		// TODO Auto-generated method stub

	}

	@Override
	public void batchDelete(List<Daily> ts) {
		// TODO Auto-generated method stub

	}

	@Override
	public Daily queryByIdcard(String bankCardNumber) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<Pay> queryByParam(String userName,Date writeTime) {
//		StringBuilder sqlCommonBody = new StringBuilder();
//		sqlCommonBody.append(" from pay t where 1=1 ");
//		// 
//		if(StringUtils.isNoneEmpty(pay)){
//			sqlCommonBody.append(" and pay=:pays ");
//		}
//		// 
//		if(StringUtils.isNoneEmpty(username)){
//			sqlCommonBody.append(" and username=:usernames ");
//		}
//		
//		// 
//		if( regular ==0 || regular==1){
//			sqlCommonBody.append(" and regular=:regulars ");
//		}
//		
//		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString();
//		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfInvitationUserData.class);
//		if(StringUtils.isNoneEmpty(pay)){
//			dataQuery.setParameter("pays", pay);
//		}
//		if(StringUtils.isNoneEmpty(username)) {
//			dataQuery.setParameter("usernames", username);
//		}
//		// 工作单位
//		if( regular ==0 || regular==1){
//			dataQuery.setParameter("regulars", regular);
//		}
//	
//		List<Pay> list = dataQuery.getResultList();
//		return list;
//	}

}
