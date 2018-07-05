package com.friends.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.friends.entity.Template;
import com.friends.entity.vo.TemplateVo;
import com.friends.service.TemplateService;
import com.friends.utils.Result;

/**
 * 模板控制层
 * 
 * @author lzl
 */
@Controller
@RequestMapping(value = "template")
public class TemplateController {
	private static Logger logger = LoggerFactory.getLogger(TemplateController.class);
	@Autowired
	TemplateService templateService;

	/**
	 * 进入模板列表页面
	 * 
	 * @return 模板列表页面
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView page() {
		return new ModelAndView("system/templateList");
	}

	/**
	 * 根据模板相关条件queryRestraintJson查询模板数据
	 * 
	 * @param queryRestraintJson
	 * @return 模板列表
	 */
	@RequestMapping(value = "queryDynamic", method = RequestMethod.POST)
	@ResponseBody
	public Result queryDynamic(String queryRestraintJson) {
		TemplateVo templateDynamicQueryVo = JSON.parseObject(queryRestraintJson, TemplateVo.class);
		Result result = templateService.queryDynamic(templateDynamicQueryVo);
		return new Result(200, "查询成功", result.getData(), result.getTotal());
	}

	/**
	 * 初始进入模板添加页面
	 * 
	 * @return 添加页面
	 */
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add(ModelAndView model) {
		model.setViewName("system/templateAdd");
		return model;
	}

	/**
	 * 根据模板template数据添加模板信息
	 * 
	 * @param template
	 * @return Result
	 */

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(Template template) {
		logger.info("add");
		// 加入自定义参数校验，后期将直接支持JSR303
		template.setCreateTime(new Date());
		template = templateService.add(template);
		return new Result(200, "新增成功");
	}

	/**
	 * 初始进入模板修改页面
	 * 
	 * @param id
	 *            模板id
	 * @return 进入页面
	 */

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(ModelAndView model, String id) {
		Template template = templateService.queryById(id);
		model.addObject("template", template);
		model.setViewName("system/templateEdit");
		return model;
	}

	/**
	 * 根据模板template数据修改模板信息
	 * 
	 * @param template
	 *            模板信息
	 * @return Result
	 */
	@RequestMapping(value = "update", method = RequestMethod.PUT)
	@ResponseBody
	public Result update(Template template) {
		logger.info("update");
		// 加入自定义参数校验，后期将直接支持JSR303
		templateService.updateSelective(template);
		return new Result(200, "修改成功");
	}

	/**
	 * 根据模板template数据删除模板
	 * 
	 * @param ids
	 *            模板id集合
	 * @return Result
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Result delete(String[] ids) {
		List<Template> list = new ArrayList<Template>();
		for (String id : ids) {
			Template temp = new Template();
			temp.setId(id);
			list.add(temp);
		}
		templateService.batchDelete(list);
		return new Result(200, "删除成功");
	}

	/**
	 * 根据模板id获取模板详情
	 * 
	 * @param model
	 *            ModelAndView
	 * @param id
	 *            模板id
	 * @return 模板详情页面
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public ModelAndView detail(ModelAndView model, String id) {
		Template template = templateService.queryById(id);
		model.addObject("template", template);
		model.addObject("detail", "detail");// 和修改是同一个页面，这个detail用来控制详情数据展示,不能编辑
		model.setViewName("system/templateEdit");
		return model;
	}
}
