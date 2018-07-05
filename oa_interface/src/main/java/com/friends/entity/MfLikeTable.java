package com.friends.entity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


//
@Entity
@Table(name="mf_like_table")
public class MfLikeTable{
  //ID主键 唯一标识
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
  private String id;
  //会员风采发布内容的id
  private String memberStyleId;
  //点赞者的id
  private String userId;

  public void setId(String id){
    this.id=id;
  }
  public String getId(){
    return id;
  }
  public void setMemberStyleId(String memberStyleId){
    this.memberStyleId=memberStyleId;
  }
  public String getMemberStyleId(){
    return memberStyleId;
  }
  public void setUserId(String userId){
    this.userId=userId;
  }
  public String getUserId(){
    return userId;
  }
}

