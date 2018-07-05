package com.friends.entity.vo;
import com.friends.entity.MfLikeTable;



public class MfLikeTableDynamicQueryVo{
  private  MfLikeTable mfLikeTable;
  private Integer page;
  private Integer pageSize;

  public MfLikeTable getMfLikeTable() {
    return mfLikeTable;
  }
  public void setMfLikeTable(MfLikeTable mfLikeTable) {
    this.mfLikeTable = mfLikeTable;
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

