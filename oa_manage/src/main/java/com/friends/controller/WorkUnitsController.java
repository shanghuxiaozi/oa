package com.friends.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.friends.entity.MfWorkUnits;
import com.friends.entity.vo.MfWorkUnitsDynamicQueryVo;
import com.friends.service.MfWorkUnitsService;
import com.friends.utils.Result;

@Controller
@RequestMapping(value = "workUnits")
public class WorkUnitsController {

	private static Logger logger = LoggerFactory.getLogger(WorkUnitsController.class);

	@Autowired
	MfWorkUnitsService mfWorkUnitsService;

	@RequestMapping(value = "page")
	public ModelAndView page() {
		return new ModelAndView("workunit/workunitList");
	}

	// 单个新增
	@RequestMapping(value = "add")
	@ResponseBody
	public Result add(String pId,Integer type,String unitName) {
		logger.info("添加工作单位");
		MfWorkUnits workUnits = new MfWorkUnits();
		workUnits.setWorkUnits(unitName);
		workUnits.setCreationTime(new Date());
		workUnits.setpId(pId);
		workUnits.setType(String.valueOf(type+1));
		mfWorkUnitsService.add(workUnits);
		return new Result(200, "新增成功");
	}

	@RequestMapping(value = "getAll")
	@ResponseBody
	public Result getAll(){
		List<MfWorkUnits> allMfWorkUnits = mfWorkUnitsService.queryAll();
		return new Result(200, "查询成功", allMfWorkUnits);
	}
	
	
	// 单个删除
	@RequestMapping(value = "delete")
	@ResponseBody
	public Result delete(String id) {
		mfWorkUnitsService.delete(id);
		return new Result(200, "删除成功");
	}

	// 批量删除
	// 数组参数以json方式传递。
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public Result batchDelete(String workUnitsArrayJson) {
		List<MfWorkUnits> workUnitss = JSON.parseArray(workUnitsArrayJson, MfWorkUnits.class);
		// 加入自定义参数校验，后期将直接支持JSR303
		mfWorkUnitsService.batchDelete(workUnitss);
		return new Result(200, "批量删除成功");
	}

	// 查询 包括动态结构查询 跟选择的约束方式有关
	// 建议不管有没有约束，都统一使用此查询方式进行查询
	// 范围约束如何传递参数？单独的参数 属性名称Min 属性名称Max 对于这个接口service和dao有对应关系
	@RequestMapping(value = "queryDynamic")
	@ResponseBody
	public Result queryDynamic(Integer page, Integer pageSize, String queryRestraintJson) {
		MfWorkUnitsDynamicQueryVo workUnitsVo = JSON.parseObject(queryRestraintJson, MfWorkUnitsDynamicQueryVo.class);
		workUnitsVo.setPage(page - 1);
		workUnitsVo.setPageSize(pageSize);
		Result mapResult = mfWorkUnitsService.queryDynamic(workUnitsVo);
		return new Result(200, "查询成功", mapResult.getData(), mapResult.getTotal());
	}

}
