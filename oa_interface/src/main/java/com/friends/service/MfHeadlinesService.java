package com.friends.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.friends.entity.MfHeadlines;
import com.friends.entity.vo.MfHeadlinesDynamicQueryVo;
import com.friends.utils.Result;

public interface MfHeadlinesService {
	public Result queryDynamic(MfHeadlinesDynamicQueryVo mfHeadlinesDynamicQueryVo);
	public MfHeadlines add(MfHeadlines t);
	public void delete(String id);
	public MfHeadlines update(MfHeadlines t);
	public MfHeadlines queryById(String id);
	public List<MfHeadlines> queryByExample(MfHeadlines t);
	public List<MfHeadlines> queryByExamplePageable(MfHeadlines t,PageRequest pageRequest);
	public void batchAdd(List<MfHeadlines> ts);
	public void batchDelete(List<MfHeadlines> ts);
}
