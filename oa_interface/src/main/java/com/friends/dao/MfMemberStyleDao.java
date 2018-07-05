package com.friends.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.friends.entity.MfMemberStyle;


public interface MfMemberStyleDao extends BaseDao<MfMemberStyle>{
/**
 * 查询所有的发布消息条数，不区分关注与否
 * @return
 */
	@Query(value="select count(*) from mf_member_style ",nativeQuery=true)
	public Integer queryAllNumMemberStyle();
  /**
   * 查询所关注的发布条数
   */
	@Query(value="select count(*) from mf_member_style where user_id in (?1)",nativeQuery=true)
	public Integer queryGuNumMemberStyle(String userId);
	
	/**
	 * 分页查询总条数
	 */
	/*
   SELECT  
T1.img_url as 图片地址, 
T1.mv_url as 视频地址 ,
T1.create_date as 发布时间 ,
T1.messages as 发布内容 , 
T2.nickname as 昵称 ,
T2.head_img as 头像路径,

(SELECT T7.user_id 
FROM mf_like_table T7 
WHERE T7.member_style_id = T1.id 
   AND 
T7.user_id = '2c9297fb604e850b01604e8efe230000') AS 点赞了没有,

(SELECT group_concat(T6.nickname SEPARATOR ',')  FROM (
SELECT T3.member_style_id,T4.nickname 
FROM mf_like_table T3
RIGHT JOIN  mf_user_data_details T4 ON T3.user_id=T4.user_id ) T6 
WHERE T6.member_style_id=T1.id ) AS 点赞的人

FROM
    mf_member_style T1 
LEFT JOIN
    mf_user_data_details T2
ON
    T1.user_id=t2.user_id
ORDER BY T1.create_date DESC
LIMIT 0,10
*/
/*String sql="SELECT "+  
			"T1.img_url as imgUrl,"+  
			"T1.mv_url as mvUrl ,"+  
			"T1.create_date as cerateDate ,"+  
			"T1.messages as messages ,"+  
			"T2.nickname as nickname ,"+  
			"T2.head_img as headImg,"+  

			"(SELECT T7.user_id"+   
			"FROM mf_like_table T7"+   
			"WHERE T7.member_style_id = T1.id"+   
			  "AND"+   
			"T7.user_id =?1) AS isLike,"+  

			"(SELECT group_concat(T6.nickname SEPARATOR ',')  FROM ("+  
			"SELECT T3.member_style_id,T4.nickname"+   
			"FROM mf_like_table T3"+  
			"RIGHT JOIN  mf_user_data_details T4 ON T3.user_id=T4.user_id ) T6"+   
			"WHERE T6.member_style_id=T1.id ) AS likeNum"+  

			"FROM"+  
			    "mf_member_style T1"+   
			"LEFT JOIN"+  
			    "mf_user_data_details T2"+  
			"ON"+  
			    "T1.user_id=t2.user_id"+  
			"ORDER BY T1.create_date DESC"+  
			"LIMIT ?3,?4";*/
	/*
	@Query(value="SELECT "+ 
			 "T1.img_url as imgUrl, "+
			 "T1.mv_url as mvUrl, "+
			 "T1.create_date as cerateDate, "+
			 "T1.messages as messages, "+
			 "T2.nickname as nickname, "+
			 "T2.head_img as headImg, "+ 
             "(SELECT T7.user_id "+  
			 "FROM mf_like_table T7 "+  
			 "WHERE T7.member_style_id = T1.id "+ 
			 "  AND "+
			 "T7.user_id ='111111111111111') AS isLike, "+ 
            "(SELECT group_concat(T6.nickname SEPARATOR ',')  FROM ( "+
			 "SELECT T3.member_style_id,T4.nickname "+  
			 "FROM mf_like_table T3 "+ 
			 "RIGHT JOIN  mf_user_data_details T4 ON T3.user_id=T4.user_id ) T6 "+
			 "WHERE T6.member_style_id=T1.id ) AS likeNum "+
			 "FROM "+
			  "  mf_member_style T1 "+ 
			 "LEFT JOIN "+
			  "  mf_user_data_details T2 "+
			 "ON  "+
			  "  T1.user_id=t2.user_id "+  
			"ORDER BY T1.create_date DESC "+  
			 "LIMIT 0,10",nativeQuery=true)*/
	
	/*@Query(value="SELECT " 
	                + "T1.img_url as imgUrl, " 
		            + "T1.mv_url as mvUrl, "
					+ "T1.create_date as createDate, "
		            + "T1.messages as messages, " 
					+ "T2.nickname as nickname, "
					+ "T2.head_img as headImg, "
					+ "(SELECT T7.user_id " 
					+ "FROM mf_like_table T7 "
					+ "WHERE T7.member_style_id = T1.id " + "  AND " 
					+ "T7.user_id ='111111111111111') AS sLike, "
					+ "(SELECT group_concat(T6.nickname SEPARATOR ',')  FROM ( "
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
					+ " LIMIT 0,10",nativeQuery=true)*/
	//select  t.img_url as 图片地址, t.mv_url as 视频地址 , t.create_date as 发布时间 ,t.messages as 发布内容 , d.user_id as 点赞了没有 ,p.nickname as 昵称 ,p.head_img as 头像路径 from mf_member_style t left join mf_user_data_details p on t.user_id=p.user_id left join mf_like_table d on t.id=d.member_style_id and t.user_id=d.user_id order by create_date desc ;
	//查询点赞的人
	//select d.nickname from mf_like_table t right join mf_user_data_details d on t.user_id=d.user_id where t.member_style_id=2
}
