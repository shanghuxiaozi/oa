package com.friends.service.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.friends.dao.MfMemberStyleDao;
import com.friends.entity.MfMemberStyle;
import com.friends.entity.vo.MfMemberStyleDynamicQueryVo;
import com.friends.service.MfMemberStyleService;
import com.friends.utils.Result;
import com.friends.utils.StringUtil;

@Service
public class MfMemberStyleServiceImpl extends BaseService<MfMemberStyleDao, MfMemberStyle>
		implements MfMemberStyleService {

	@Autowired
	MfMemberStyleDao mfMemberStyleDao;
	@Autowired
	EntityManager em;

	// 通用方法在base层已经有了
	// 动态结构查询
	// Spring-data-jpa-specification形式较简单,但只适用于单表动态结构查询
	// entityManager形式稍复杂，但能实现多表动态约束查询。但多表时jpa返回的是object[][],需要借助工具转成对象。
	// entityManager方式
	@SuppressWarnings("unchecked")
	public Result queryDynamic(MfMemberStyleDynamicQueryVo mfMemberStyleDynamicQueryVo) {
		int page = mfMemberStyleDynamicQueryVo.getPage();
		int pageSize = mfMemberStyleDynamicQueryVo.getPageSize();
		MfMemberStyle mfMemberStyle = mfMemberStyleDynamicQueryVo.getT();
		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_member_style t where 1=1 ");
		// 遍历每个字段 发现约束类型不为NO的，则根据情况进行约束
		// 如果判空也得分类型：字符串与非字符串
		// 时间要模糊约束怎么办
		String sqlDataQuery = " select distinct t.* " + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfMemberStyle.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);
		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);
		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<MfMemberStyle> list = dataQuery.getResultList();
		// BeanHelper.objectsListToEntity(List<Object[]> objectsList, Class<T>
		// c)
		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(list, total);
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;  
	
	@Override
	public Result queryAllMemberStyle(String nums, String userId) {
		if (userId == null) {
			return new Result(400, "用户名为空，请重试");
		}
		Integer num = 0;
		if (nums == null) {
			num = 0;
		}
		try {
			num = Integer.parseInt(nums);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer start = 0;
		Integer end = 9;
		if (num > 0) {
			start = num * 10;
			end = num * 10 + 9;
		}
		try {
			String sql = "SELECT " + "T1.img_url as imgUrl, " 
					+ "T1.id as styid, "
		            + "T1.mv_url as mvUrl, "
		            + "T1.create_date as createDate, "
		            + "T1.messages as messages, " 
					+ "T2.nickname as nickname, "
					+ "T2.head_img as headImg, "
					+ "(SELECT T7.user_id " 
					+ "FROM mf_like_table T7 "
					+ "WHERE T7.member_style_id = T1.id " + "  AND " 
					+ "T7.user_id ='"+userId+"') AS sLike, "
					+ "(SELECT group_concat(T6.nickname SEPARATOR '、')  FROM ( "
					+ "SELECT T3.member_style_id,T4.nickname " 
					+ "FROM mf_like_table T3 "
					+ "RIGHT JOIN  mf_user_data_details T4 ON T3.user_id=T4.user_id ) T6 "
					+ "WHERE T6.member_style_id=T1.id ) AS likeNum " 
					+ "FROM " 
					+ "  mf_member_style T1 " 
					+ "LEFT JOIN "
					+ "  mf_user_data_details T2 " 
					+ "ON  "
					+ "  T1.user_id=t2.user_id "
					+ "ORDER BY T1.create_date DESC " 
					+ " LIMIT "+start+" ,"+end;
			
			List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
			if(list.size()==0){
				return new Result(400,"没有更多数据了");
			}
			return new Result(200, "查询数据成功",list, num);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(400, "服务器异常,请稍后");
		}
	}
	
	@Override
	public Result findMyUserNickNameAndHideImg(String userid) {
		if(userid==null){
			return new Result(400,"未获取到用户信息！请重新登录！");
		}
		
		try {
			String sql="SELECT nickname, head_img from mf_user_data_details where user_id='"+userid+"'";
			List<Map<String, Object>> list=jdbcTemplate.queryForList(sql);
			if(list.size()==0){
				return new Result(400,"没有获取到用户信息");
			}
			return new Result(200,"获取成功！",list);
		} catch (Exception e) {
			return new Result(400,"未获取到用户信息！请重新登录！");
		}
		
	}

	@Override
	public Result queryAttentionMemberStyle(String nums, String userId) {
		if (userId == null) {
			return new Result(400, "用户名为空，请重试");
		}
		Integer num = 0;
		if (nums == null) {
			num = 0;
		}
		try {
			num = Integer.parseInt(nums);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer start = 0;
		Integer end = 9;
		if (num > 0) {
			start = num * 10;
			end = num * 10 + 9;
		}
		
		
		String sql = "SELECT " + "T1.img_url as imgUrl, " 
				+ "T1.id as styid, "
	            + "T1.mv_url as mvUrl, "
	            + "T1.create_date as createDate, "
	            + "T1.messages as messages, " 
				+ "T2.nickname as nickname, "
				+ "T2.head_img as headImg, "
				+ "(SELECT T7.user_id " 
				+ "FROM mf_like_table T7 "
				+ "WHERE T7.member_style_id = T1.id " + "  AND " 
				+ "T7.user_id ='"+userId+"') AS sLike, "
				+ "(SELECT group_concat(T6.nickname SEPARATOR '、')  FROM ( "
				+ "SELECT T3.member_style_id,T4.nickname " 
				+ "FROM mf_like_table T3 "
				+ "RIGHT JOIN  mf_user_data_details T4 ON T3.user_id=T4.user_id ) T6 "
				+ "WHERE T6.member_style_id=T1.id ) AS likeNum " 
				+ "FROM " 
				+ "  mf_member_style T1 " 
				+ "LEFT JOIN "
				+ "  mf_user_data_details T2 " 
				+ "ON  "
				+ "  T1.user_id=t2.user_id "+
				"WHERE T1.user_id " +
				"   IN (" +
				"   SELECT " +
				"      by_tention_user" +
				"   FROM" +
				"      mf_user_attention" +
				"   WHERE" +
				"     attention_user='"+userId+"')" 
				+ "ORDER BY T1.create_date DESC " 
				+ " LIMIT "+start+" ,"+end;
		
		
		try {
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			if(list.size()==0){
				return new Result(400,"没有更多数据了");
			}
			return new Result(200, "查询数据成功", list, num);

		} catch (Exception e) {
			e.printStackTrace();
			return new Result(400, "服务器异常,请稍后");
		}
	}

	// 连表动态查询  后台用
	@SuppressWarnings("unchecked")
	@Override
	public Result queryJoinTableDynamic(MfMemberStyleDynamicQueryVo mfMemberStyleDynamicQueryVo) {
		int page = mfMemberStyleDynamicQueryVo.getPage();
		int pageSize = mfMemberStyleDynamicQueryVo.getPageSize();
		MfMemberStyle mfMemberStyle = mfMemberStyleDynamicQueryVo.getT();
		
		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_member_style m left join mf_user_info u on(m.user_id=u.id) where 1=1 and m.status=:status order by m.create_date desc ");
		// 遍历每个字段 发现约束类型不为NO的，则根据情况进行约束
		// 如果判空也得分类型：字符串与非字符串
		// 时间要模糊约束怎么办
		String sqlDataQuery = "select m.*,u.nickname,u.name " + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = "select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery);
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Query countQuery = em.createNativeQuery(sqlCountQuery);
		
		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		dataQuery.setParameter("status", mfMemberStyle.getStatus());
		countQuery.setParameter("status", mfMemberStyle.getStatus());
		
		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<Map<String, Object>> listMap = dataQuery.getResultList();
		listMap = StringUtil.tranferMapsKeyToCamel(listMap, null);
		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(listMap, total);
	}

	//查询所有会员风采
	@Override
	public Result queryAllMemberStyle(PageRequest pageRequest) {
		int page = pageRequest.getPageNumber();
		int pageSize = pageRequest.getPageSize();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_member_style m left join mf_user_data_details u on(m.user_id=u.user_id) where 1=1");
		sqlCommonBody.append(" and m.status=2 order by m.create_date desc");
		// 遍历每个字段 发现约束类型不为NO的，则根据情况进行约束
		// 如果判空也得分类型：字符串与非字符串
		// 时间要模糊约束怎么办
		String sqlDataQuery = "select m.*,u.nickname,u.head_img"+ sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = "select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery);
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Query countQuery = em.createNativeQuery(sqlCountQuery);

		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<Map<String, Object>> listMap = dataQuery.getResultList();
		listMap = StringUtil.tranferMapsKeyToCamel(listMap, null);
		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(listMap,total);
	}

	@Override
	public Result queryMyStyle(PageRequest pageRequest,String userId) {

		int page = pageRequest.getPageNumber();
		int pageSize = pageRequest.getPageSize();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_member_style m left join mf_user_data_details u on(m.user_id=u.user_id) where 1=1");
		sqlCommonBody.append(" and m.status=2 and m.user_id=:id order by m.create_date desc");

		// 遍历每个字段 发现约束类型不为NO的，则根据情况进行约束
		// 如果判空也得分类型：字符串与非字符串
		// 时间要模糊约束怎么办
		String sqlDataQuery = "select m.*,u.nickname,u.head_img"+ sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = "select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery);
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Query countQuery = em.createNativeQuery(sqlCountQuery);

		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		dataQuery.setParameter("id", userId);
		countQuery.setParameter("id", userId);

		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<Map<String, Object>> listMap = dataQuery.getResultList();
		listMap = StringUtil.tranferMapsKeyToCamel(listMap, null);
		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(listMap,total);
	}

	//查询我的好友动态（包括关注的）
	@Override
	public Result queryFriendsDynamic(PageRequest pageRequest,String userId) {
		int page = pageRequest.getPageNumber();
		int pageSize = pageRequest.getPageSize();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append("from mf_member_style m left join mf_user_data_details u on(m.user_id=u.user_id) where 1=1 ");
		sqlCommonBody.append(" and m.user_id in(select a.by_tention_user from mf_user_attention a where a.attention_user=:userId)");
		sqlCommonBody.append(" and m.status=2 order by m.create_date desc");
		// 遍历每个字段 发现约束类型不为NO的，则根据情况进行约束
		// 如果判空也得分类型：字符串与非字符串
		// 时间要模糊约束怎么办
		String sqlDataQuery = "select m.*,u.nickname,u.head_img " + sqlCommonBody.toString() + " limit :from, :to ";
		String sqlCountQuery = "select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery);
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Query countQuery = em.createNativeQuery(sqlCountQuery);

		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);
		dataQuery.setParameter("userId", userId);
		countQuery.setParameter("userId", userId);

		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<Map<String, Object>> listMap = dataQuery.getResultList();
		listMap = StringUtil.tranferMapsKeyToCamel(listMap, null);
		int total = Integer.valueOf(countQuery.getSingleResult().toString());
		return new Result(listMap, total);
	}

	@Override
	public Result queryByMemberStyleId(String memberStyleId) {
		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append("from mf_like_table k left join mf_user_data_details ud on(k.user_id=ud.user_id) ");
		sqlCommonBody.append(" where 1=1 and k.member_style_id=:id ");
		// 遍历每个字段 发现约束类型不为NO的，则根据情况进行约束
		// 如果判空也得分类型：字符串与非字符串
		// 时间要模糊约束怎么办
		String sqlDataQuery = "select k.id as like_table_id,ud.nickname " + sqlCommonBody.toString() ;
		Query dataQuery = em.createNativeQuery(sqlDataQuery);
		dataQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		// 参数注入
		dataQuery.setParameter("id", memberStyleId);
		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<Map<String, Object>> listMap = dataQuery.getResultList();
		listMap = StringUtil.tranferMapsKeyToCamel(listMap, null);
		return new Result(listMap);
	}

}
