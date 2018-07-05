package com.friends.service.impl;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.friends.dao.MfLikeTableDao;
import com.friends.entity.MfLikeTable;
import com.friends.entity.vo.MfLikeTableDynamicQueryVo;
import com.friends.service.MfLikeTableService;
import com.friends.utils.Result;


@Service
public class MfLikeTableServiceImpl extends BaseService<MfLikeTableDao,MfLikeTable> implements MfLikeTableService {

  @Autowired
  MfLikeTableDao mfLikeTableDao;
  @Autowired
  EntityManager em;
  
  @SuppressWarnings("unchecked")
  public Result queryDynamic(MfLikeTableDynamicQueryVo mfLikeTableDynamicQueryVo) {
    int page = mfLikeTableDynamicQueryVo.getPage();
    int pageSize =mfLikeTableDynamicQueryVo.getPageSize();
    MfLikeTable mfLikeTable=mfLikeTableDynamicQueryVo.getMfLikeTable();
    
    
    
    //多表sqlCommonBody查询  返回单表数据
    StringBuilder sqlCommonBody=new StringBuilder();
    sqlCommonBody.append(" from mf_like_table t where 1=1 ");
    
    
    //遍历每个字段  发现约束类型不为NO的，则根据情况进行约束
    //如果判空也得分类型：字符串与非字符串
    //时间要模糊约束怎么办

    

    

    
    
    String sqlDataQuery=" select distinct t.* "+sqlCommonBody.toString()+" limit :from, :to ";
    String sqlCountQuery=" select count(1) "+sqlCommonBody.toString();
    Query dataQuery = em.createNativeQuery(sqlDataQuery,MfLikeTable.class);
    Query countQuery = em.createNativeQuery(sqlCountQuery);
    //参数注入
    dataQuery.setParameter("from",(page*pageSize));
    dataQuery.setParameter("to",pageSize);

    //多表需要使用object[][]来接收   并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
    List<MfLikeTable> list = dataQuery.getResultList();
    //BeanHelper.objectsListToEntity(List<Object[]> objectsList, Class<T> c)
    int total = Integer.valueOf(countQuery.getSingleResult().toString());
    return new Result(list, total);
  }


@Override
public Result judgeWhetherToLikePraise(String memberStyleId, String userid) {
	if(memberStyleId==null){
		return new Result(400,"此信息已被删除");
	}
	if(userid==null){
		return new Result(400,"获取用户信息失败！请稍后重试！");
	}
	
	List<MfLikeTable>list=null;
	try {
		list=mfLikeTableDao.judgeWhetherToLikePraise(memberStyleId,userid);
	} catch (Exception e) {
		return new Result(400,"数据异常！请稍后重试！");
	}

	if(list==null){
		return new Result(400,"数据异常！请稍后重试！");
	}
	
	if(list.size()==0){
		MfLikeTable mfliketable=new MfLikeTable();
		mfliketable.setMemberStyleId(memberStyleId);
		mfliketable.setUserId(userid);
		try {
			mfLikeTableDao.save(mfliketable);
			return new Result(200,"点赞成功！","1");
		} catch (Exception e) {
			return new Result(400,"点赞失败！");
		}
	}else{
		
		try {
			mfLikeTableDao.deleteLikeTableByUserIdAnaMembleId(userid, memberStyleId);
			return new Result(200,"删除点赞成功！","0");
		} catch (Exception e) {
			return new Result(400,"删除点赞失败！");
		}
		
	}
	
}

	@Override
	public MfLikeTable queryByStyleIdAndUserId(String memberStyleId, String userId) {
		return mfLikeTableDao.findByMemberStyleIdAndUserId(memberStyleId,userId);
	}

}


