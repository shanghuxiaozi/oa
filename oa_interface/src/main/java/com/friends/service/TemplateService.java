package com.friends.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import com.friends.entity.Template;
import com.friends.entity.vo.TemplateVo;
import com.friends.utils.Result;


public interface TemplateService  {
	/**
	 * 通过模块vo参数查询模块列表
	 * @param templateVo 模块vo
	 * @return Result(列表数据，分页数据，消息数据，以及)
	 */
	public Result queryDynamic(TemplateVo templateVo);//通过模块vo参数查询模块列表
	public Template add(Template t);//模块添加
	public void delete(String id);//模块删除
	public Template updateSelective(Template t);//模块更新
	public Template update(Template t);//模块更新
	public Template queryById(String id);//通过模块主键值查询模块信息
	public List<Template> queryByExample(Template t);//通过模块vo参数查询模块列表
	public List<Template> queryByExamplePageable(Template t,PageRequest pageRequest);
	public void batchAdd(List<Template> ts);
	public void batchDelete(List<Template> ts);
	public Result queryDynamicSpecification(TemplateVo templateVo);
	
}
