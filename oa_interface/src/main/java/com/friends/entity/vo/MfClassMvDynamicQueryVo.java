package com.friends.entity.vo;
import java.util.Date;

import com.friends.entity.MfClassMv;



public class MfClassMvDynamicQueryVo{
  private  MfClassMv mfClassMv;
  private Integer page;
  private Integer pageSize;

  public MfClassMv getMfClassMv() {
    return mfClassMv;
  }
  public void setMfClassMv(MfClassMv mfClassMv) {
    this.mfClassMv = mfClassMv;
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

