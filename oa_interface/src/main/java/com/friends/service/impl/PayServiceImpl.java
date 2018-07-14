package com.friends.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.friends.dao.PayDao;
import com.friends.entity.MfInvitationUserData;
import com.friends.entity.Pay;
import com.friends.entity.vo.PayDynamicQueryVo;
import com.friends.service.PayService;
import com.friends.utils.Result;

@Service
public class PayServiceImpl extends BaseService<PayDao, Pay> implements PayService {

	@Autowired
	PayDao payDao;
	@Autowired
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	public Result queryDynamic(PayDynamicQueryVo payDynamicQueryVo) {
		int page = payDynamicQueryVo.getPage();
		int pageSize = payDynamicQueryVo.getPageSize();
		Pay pay= payDynamicQueryVo.getT();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from pay t where 1=1 ");
		
		if(pay != null && StringUtils.isNotEmpty(pay.getBankCardNumber())){
			sqlCommonBody.append(" and bank_card_number=:bankCardNumber ");
		}
		if(pay != null && StringUtils.isNotEmpty(pay.getAccount())){
			sqlCommonBody.append(" and account like :account ");
		}
		if(pay != null && StringUtils.isNotEmpty(pay.getUsername())){
			sqlCommonBody.append(" and username like:username ");
		}
		if(pay != null &&StringUtils.isNotEmpty(payDynamicQueryVo.getQueryDate())){
			sqlCommonBody.append(" and DATE_FORMAT(payroll_time ,'%Y-%m-%d') like:payrollTime ");
		}
		
		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, Pay.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);
		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);
		
		if(pay != null && StringUtils.isNotEmpty(pay.getBankCardNumber())){
			dataQuery.setParameter("bankCardNumber", pay.getBankCardNumber());
			countQuery.setParameter("bankCardNumber", pay.getBankCardNumber());
		}
		if(pay != null && StringUtils.isNotEmpty(pay.getUsername())){
			dataQuery.setParameter("username", "%" +pay.getUsername()+ "%");
			countQuery.setParameter("username", "%" +pay.getUsername()+ "%");
		}
		if(pay != null &&StringUtils.isNotEmpty(payDynamicQueryVo.getQueryDate())){
			dataQuery.setParameter("payrollTime", "%" +payDynamicQueryVo.getQueryDate() + "%");
			countQuery.setParameter("payrollTime","%" + payDynamicQueryVo.getQueryDate() + "%");
		}
		if(pay != null && StringUtils.isNotEmpty(pay.getAccount())){
			dataQuery.setParameter("account", "%" +pay.getAccount() + "%");
			countQuery.setParameter("account","%" + pay.getAccount() + "%");
		}
		
		List<Pay> list = dataQuery.getResultList();
		// BeanHelper.objectsListToEntity(List<Object[]> objectsList, Class<T>
		// c)
		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(list, total);
	}

	
	

	@Override
	public void delete(Pay t) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public Pay queryByIdcard(String bankCardNumber) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<Pay> queryByParam(String pay, String account,String username, int regular) {
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from pay t where 1=1 ");
		// 
		if(null!=pay&&StringUtils.isNoneEmpty(pay)){
			sqlCommonBody.append(" and pay=:pays ");
		}
		if(account != null && StringUtils.isNotEmpty(account)){
			sqlCommonBody.append(" and account like :account ");
		}
		
		// 
		if(null!=username&&StringUtils.isNoneEmpty(username)){
			sqlCommonBody.append(" and username=:usernames ");
		}
		
		// 
		if( regular ==0 || regular==1){
			sqlCommonBody.append(" and regular=:regulars ");
		}
		
		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, Pay.class);
		if(StringUtils.isNoneEmpty(pay)){
			dataQuery.setParameter("pays", pay);
		}
		if(StringUtils.isNoneEmpty(username)) {
			dataQuery.setParameter("usernames", username);
		}
		// 工作单位
		if( regular ==0 || regular==1){
			dataQuery.setParameter("regulars", regular);
		}
		if(account != null && StringUtils.isNotEmpty(account)){
			dataQuery.setParameter("account", "%" + account + "%");
		}
		List<Pay> list = dataQuery.getResultList();
		return list;
	}

}
