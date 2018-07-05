package com.friends.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.PageRequest;

import com.friends.entity.MfClassMv;
import com.friends.entity.vo.MfClassMvDynamicQueryVo;
import com.friends.utils.Result;


public interface MfClassMvService  {
	public Result queryDynamic(MfClassMvDynamicQueryVo mfClassMvDynamicQueryVo);
	public MfClassMv add(MfClassMv t);
	public void delete(MfClassMv t);
	public MfClassMv updateSelective(MfClassMv t,String id);
	public MfClassMv update(MfClassMv t,String id);
	public MfClassMv queryById(String id);
	public List<MfClassMv> queryByExample(MfClassMv t);
	public List<MfClassMv> queryByExamplePageable(MfClassMv t,PageRequest pageRequest);
	public void batchAdd(List<MfClassMv> ts);
	public void batchDelete(List<MfClassMv> ts);
}
