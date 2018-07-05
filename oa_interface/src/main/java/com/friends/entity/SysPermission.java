package com.friends.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 权限信息
 * @author lzl
 * 2017-8-22
 */
@Entity
@Table(name = "sys_permission")
public class SysPermission implements Serializable {
	private static final long serialVersionUID = 1L;;
    @Id
    @GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;//编号
    private String pid;//所属上级id
    private String pname;//所属上级名称
    private String name;//名称
    private Boolean open;//打开
    private String url;//请求地址
    private boolean available;//是否可用的
    @Column(name="icon_code")
    private String iconCode;//图标
    private Integer type;//类型(1菜单,按钮)
    @Column(name="is_parent")
    private boolean isParent;//是否有父级
    @Column(name="permission_name")
    private String permissionName;//权限名称
    @Column(name="permission_value")
    private String permissionValue;//权限值
    @Column(name = "order_num")
	private Integer orderNum;//排序
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean getAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public String getIconCode() {
		return iconCode;
	}
	public void setIconCode(String iconCode) {
		this.iconCode = iconCode;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setIsParent(boolean isParent) {
		this.isParent=isParent;
	}
	public boolean getIsParent() {
		return isParent;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public String getPermissionValue() {
		return permissionValue;
	}
	public void setPermissionValue(String permissionValue) {
		this.permissionValue = permissionValue;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

}
