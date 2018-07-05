package com.friends.entity.vo;

import java.io.Serializable;
import java.util.List;

public class SysPermissionTreeVo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String url;
	private String permissionValue;
	private String pid;
	private String iconCode;
	private Boolean isParent;
	private String permissionName;
	private List<SysPermissionTreeVo> children;

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getIconCode() {
		return iconCode;
	}

	public void setIconCode(String iconCode) {
		this.iconCode = iconCode;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public List<SysPermissionTreeVo> getChildren() {
		return children;
	}

	public void setChildren(List<SysPermissionTreeVo> children) {
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPermissionValue() {
		return permissionValue;
	}

	public void setPermissionValue(String permissionValue) {
		this.permissionValue = permissionValue;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
}
