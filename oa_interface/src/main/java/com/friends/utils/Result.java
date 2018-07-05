package com.friends.utils;

import java.io.Serializable;

/**
 * 统一返回结果类
 * Created by lzl on 2017/2/18.
 */
public class Result  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer code;//响应码(1成功,其他的为失败)
	private String msg;//消息(例："添加成功","删除成功","修改成功")
	private Integer total;//总数
	private Object data;//数据结果值
	
	public Result(Object data){
		this.data=data;
	}
	public Result(Object data,Integer total){
		this.data=data;
		this.total=total;
	}
	
	public Result(Integer code,String msg){
		this.code=code;
		this.msg=msg;
	}
	
	public Result(Integer code,String msg,Integer total){
		this.code=code;
		this.msg=msg;
		this.total=total;
	}
	
	public Result(Integer code,String msg,Object data){
		this.code=code;
		this.msg=msg;
		this.data=data;
	}
	
	public Result(Integer code,Object data,Integer total){
		this.data=data;
		this.total=total;
		this.code=code;
	}
	
	public Result(Integer code,String msg,Object data,Integer total){
		this.code=code;
		this.msg=msg;
		this.data=data;
		this.total=total;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
