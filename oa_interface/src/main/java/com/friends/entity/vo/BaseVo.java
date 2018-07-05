package com.friends.entity.vo;

public class BaseVo<T> implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer page;
	private Integer pageSize;
	private T t;

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
