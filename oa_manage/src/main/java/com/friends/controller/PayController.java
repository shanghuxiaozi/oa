package com.friends.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.friends.entity.MfUserDataDetails;
import com.friends.entity.MfUserInfo;
import com.friends.entity.Pay;
import com.friends.entity.SysUser;
import com.friends.service.MfUserDataDetailsService;
import com.friends.service.MfUserInfoService;
import com.friends.service.PayService;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.friends.entity.MfInvitationUserData;
import com.friends.entity.vo.MfInvitationUserDataDynamicQueryVo;
import com.friends.entity.vo.PayDynamicQueryVo;
import com.friends.service.MfInvitationUserDataService;
import com.friends.utils.Result;
import com.friends.utils.TimeHelper;
import com.friends.utils.excel.ExcelUtil;

/**
 * 受邀用户资料   controller <br>
 * 名称：MfInvitationUserDataController.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2017年12月25日 上午11:18:51 <br>
 * @since  2017年12月25日
 * @authour wenxun
 */
@Controller
@RequestMapping(value = "pay")
public class PayController {
	private static Logger logger = LoggerFactory.getLogger(PayController.class);

	@Autowired
	PayService payService;
	@Autowired
	MfUserDataDetailsService mfUserDataDetailsService;
	@Autowired
	MfUserInfoService mfUserInfoService;

	@RequestMapping(value = "index")
	public ModelAndView page() {
		return new ModelAndView("pay/payIndex");
	}

	// 单个新增
	@RequestMapping(value = "add")
	@ResponseBody
	public Result add(Pay pay) {
		logger.info("添加");
		// 加入自定义参数校验，后期将直接支持JSR303
		pay = payService.add(pay);
		return new Result(200, "新增成功");
	}

	// 单个修改
	@RequestMapping(value = "update")
	@ResponseBody
	public Result update(String id,String json) {
		logger.info("修改受邀用户资料");
		Pay pay = JSON.parseObject(json,Pay.class);
		Pay invitationUserData = payService.queryById(id);
		invitationUserData.setUsername(pay.getUsername());;
		invitationUserData.setAttendance(pay.getAttendance());
		invitationUserData.setActual(pay.getActual());
		invitationUserData.setAbsenteeism(pay.getAbsenteeism());
		invitationUserData.setWages(pay.getWages());
		invitationUserData.setPayWages(pay.getPayWages());
		invitationUserData.setRealWages(pay.getRealWages());
		invitationUserData.setFirstAmount(pay.getFirstAmount());
		invitationUserData.setSecondAmount(invitationUserData.getSecondAmount());
		invitationUserData.setBankCardNumber(pay.getBankCardNumber());
		invitationUserData.setReimbursement(pay.getReimbursement());
		invitationUserData.setAchievements(pay.getAchievements());
		invitationUserData.setWithholding(pay.getWithholding());
		invitationUserData.setRegular(pay.getRegular());
		invitationUserData.setReason(pay.getReason());

		pay = payService.add(invitationUserData);

		
		
		return new Result(200, "修改成功");
	}

	// 单个删除
	@RequestMapping(value = "delete")
	@ResponseBody
	public Result delete(String id) {
		logger.info("删除受邀用户资料");
		Pay invitationUserData = payService.queryById(id);
		if(null != invitationUserData){
			
			payService.delete(invitationUserData);
			return new Result(200, "删除成功");
		}
		return new Result(400, "删除失败，数据已经不存在！");
	}

	// 批量删除
	// 数组参数以json方式传递。
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public Result batchDelete(String mfInvitationUserDataArrayJson) {
		List<Pay> mfInvitationUserDatas = JSON.parseArray(mfInvitationUserDataArrayJson,
				Pay.class);
		// 加入自定义参数校验，后期将直接支持JSR303
		payService.batchDelete(mfInvitationUserDatas);
		return new Result(200, "批量删除成功");
	}

	// 查询 包括动态结构查询 跟选择的约束方式有关
	// 建议不管有没有约束，都统一使用此查询方式进行查询
	// 范围约束如何传递参数？单独的参数 属性名称Min 属性名称Max 对于这个接口service和dao有对应关系
	@RequestMapping(value = "queryDynamic")
	@ResponseBody
	public Result queryDynamic(Integer page, Integer pageSize, String queryRestraintJson) {
		PayDynamicQueryVo mfInvitationUserDataDynamicQueryVo = JSON.parseObject(queryRestraintJson,
				PayDynamicQueryVo.class);
		mfInvitationUserDataDynamicQueryVo.setPage(page - 1);
		mfInvitationUserDataDynamicQueryVo.setPageSize(pageSize);
		Result mapResult = payService.queryDynamic(mfInvitationUserDataDynamicQueryVo);
		List<Pay> list = (List<Pay>)mapResult.getData();
		return new Result(200, "查询成功", list, mapResult.getTotal());
	}

	@RequestMapping(value = "queryDynamicByaccount")
	@ResponseBody
	public Result queryDynamicByaccount(Integer page, Integer pageSize, String queryRestraintJson) {
		logger.info("json:"+queryRestraintJson);
		Session session = SecurityUtils.getSubject().getSession();
		SysUser userinfo = (SysUser) session.getAttribute("sysUser");
		if (userinfo == null) {
			return new Result(201, "未登录");
		}
		PayDynamicQueryVo mfInvitationUserDataDynamicQueryVo = JSON.parseObject(queryRestraintJson,
				PayDynamicQueryVo.class);
		mfInvitationUserDataDynamicQueryVo.setPage(page - 1);
		mfInvitationUserDataDynamicQueryVo.setPageSize(pageSize);
		Pay pay = new Pay();
		logger.info("从session中获取的account="+userinfo.getUserName());
		pay.setAccount(userinfo.getUserName());
		mfInvitationUserDataDynamicQueryVo.setT(pay);
		Result mapResult = payService.queryDynamic(mfInvitationUserDataDynamicQueryVo);
		List<Pay> list = (List<Pay>)mapResult.getData();
		return new Result(200, "查询成功", list, mapResult.getTotal());
	}

	/**********
	@RequestMapping(value = "downLoadTemplate")
	public void downLoadTemplate(String queryRestraintJson, HttpServletResponse resp) throws Exception {
		logger.info("下载模板");
		String[] colunmNames = {"姓名","性别","民族","出生年月","籍贯","出生地","政治面貌","学历","婚姻状况","身高cm","身份证号码","联系手机","工作单位及职务"};
		String[] atterNames = { "realName", "sex", "nation", "birthTime","birthplace","growingPlace","politicalVisage","education","maritalStatus","height","idcard","phone","workUnit"};
		List<MfInvitationUserData> invitationUserDataList =  mfInvitationUserDataService.queryByExample(new MfInvitationUserData());
		System.out.println("数量"+invitationUserDataList.size());

		XSSFWorkbook book = new XSSFWorkbook();
		XSSFSheet sheet = book.createSheet("卫计数据");
		ExcelUtil.appendRowToSheet(sheet, colunmNames, true);
		for(int j=0;j<invitationUserDataList.size();j++){
			MfInvitationUserData invitationUser = invitationUserDataList.get(j);
			for (int i = 0; i < 15; i++) {
				sheet.setColumnWidth(i, 6000);
			}
			ExcelUtil.appendRowObjectToSheetSelective(sheet, invitationUser, atterNames);
		}
		resp.setCharacterEncoding("UTF-8");
		resp.addHeader("Content-type", " application/octet-stream");
		String fileName = new String(("数据"+invitationUserDataList.size()+"人").getBytes(), "ISO8859_1");
		resp.addHeader("Content-Disposition", new StringBuffer().append("attachment;filename=")
				.append(fileName + TimeHelper.dateToStrShort(new Date()) + ".xlsx").toString());

		ServletOutputStream outputStream = resp.getOutputStream();
		book.write(outputStream);
		outputStream.close();
	}
	***************/


	@RequestMapping(value = "downLoadTemplate")
	public void downLoadTemplate(String queryRestraintJson, HttpServletResponse response) throws Exception {
		logger.info("下载模板");
		String[] colunmNames = {"姓名","账号","发工资时间","应出勤天数","实际出勤天数","缺勤天数","工资","应付工资","实际工资","第一次发金额","第二次罚金额","社保","公积金","银行卡号","报销","绩效","扣发","转正1转0没转","扣发原因"
		};
		String[] atterNames = { "username","account", "payrollTime","attendance", "actual", "absenteeism","wages","payWages","realWages","firstAmount","secondAmount","socialSecurity","accumulationFund","bankCardNumber","reimbursement","achievements","withholding","regular","reason"
		};
		// 加入一条范例
		Pay invitationUserData = new Pay();
		invitationUserData.setUsername("李四");
		invitationUserData.setAccount("lisi");
		invitationUserData.setPayrollTime(new Date());
		invitationUserData.setAttendance(20);
		invitationUserData.setActual(17);
		invitationUserData.setAbsenteeism(3);
		invitationUserData.setWages(new BigDecimal(10000));
		invitationUserData.setPayWages(new BigDecimal(10000));
		invitationUserData.setRealWages(new BigDecimal(10000));
		invitationUserData.setFirstAmount(new BigDecimal(10000));
		invitationUserData.setSecondAmount(new BigDecimal(10000));
		invitationUserData.setBankCardNumber("6214837827480181");
		invitationUserData.setReimbursement(new BigDecimal(10000));
		invitationUserData.setAchievements(new BigDecimal(10000));
		invitationUserData.setWithholding(new BigDecimal(10000));
		invitationUserData.setRegular(1);
		invitationUserData.setReason("补发");

		XSSFWorkbook book = new XSSFWorkbook();
		XSSFSheet sheet = book.createSheet("工资模板");
		for (int i = 0; i < 15; i++) {
			sheet.setColumnWidth(i, 6000);
		}
		ExcelUtil.appendRowToSheet(sheet, colunmNames, true);
		ExcelUtil.appendRowObjectToSheetSelective(sheet, invitationUserData, atterNames);

		response.setCharacterEncoding("UTF-8");
		response.addHeader("Content-type", " application/octet-stream");
		String fileName = new String(("工资模板").getBytes(), "ISO8859_1");
		response.addHeader("Content-Disposition", new StringBuffer().append("attachment;filename=")
				.append(fileName + TimeHelper.dateToStrShort(new Date()) + ".xlsx").toString());

		ServletOutputStream outputStream = response.getOutputStream();
		book.write(outputStream);
		outputStream.close();
	}

	/**
	 * 功能描述: 下载受邀用户资料
	 * @param: [idcard, phone, status]
	 * @return: void
	 * @auther: wenxun
	 * @date: 2018/4/10 15:09
	 */
	@RequestMapping(value = "/downloadUserData",method = RequestMethod.POST)
	public void downloadUserData(/*String pay,String username,int regular ,*/HttpServletResponse response)throws Exception{
		String[] colunmNames = {"姓名","账号","发工资时间","应出勤天数","实际出勤天数","缺勤天数","工资","应付工资","实际工资","第一次发金额","第二次罚金额","社保","公积金","银行卡号","报销","绩效","扣发","转正1转0没转","扣发原因"
		};
		String[] atterNames = { "username","account", "payrollTime","attendance", "actual", "absenteeism","wages","payWages","realWages","firstAmount","secondAmount","socialSecurity","accumulationFund","bankCardNumber","reimbursement","achievements","withholding","regular","reason"
		};

		List<Pay> invitationUserDataList = payService.queryByParam("","",2/*pay,username,regular*/);

		XSSFWorkbook book = new XSSFWorkbook();
		XSSFSheet sheet = book.createSheet("工资模板");
		for (int i = 0; i < 15; i++) {
			sheet.setColumnWidth(i, 6000);
		}
		ExcelUtil.appendRowToSheet(sheet, colunmNames, true);
		if(null != invitationUserDataList && invitationUserDataList.size() > 0){
			for(Pay invitationUserData : invitationUserDataList){
				ExcelUtil.appendRowObjectToSheetSelective(sheet, invitationUserData, atterNames);
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Content-type", " application/octet-stream");
		String fileName = new String(("工资模板").getBytes(), "ISO8859_1");
		response.addHeader("Content-Disposition", new StringBuffer().append("attachment;filename=")
				.append(fileName + TimeHelper.dateToStrShort(new Date()) + ".xlsx").toString());

		ServletOutputStream outputStream = response.getOutputStream();
		book.write(outputStream);
		outputStream.close();

	}


	// 导入
	@RequestMapping(value = "import")
	@ResponseBody
	public Result importMemberLevel(HttpServletRequest request, HttpServletResponse response, MultipartFile file)
			throws Exception {
		logger.info("导入");
		int[] columnIndexs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,13,14,15,16,17,18};
		String[] colunmNames = {"姓名","账号","发工资时间","应出勤天数","实际出勤天数","缺勤天数","工资","应付工资","实际工资","第一次发金额","第二次罚金额","社保","公积金","银行卡号","报销","绩效","扣发","转正1转0没转","扣发原因"
		};
		String[] atterNames = { "username","account", "payrollTime","attendance", "actual", "absenteeism","wages","payWages","realWages","firstAmount","secondAmount","socialSecurity","accumulationFund","bankCardNumber","reimbursement","achievements","withholding","regular","reason"
		};
		// 检查第一行字段内容是否匹配
		boolean isCorrect = ExcelUtil.checkImportExecl(file.getInputStream(), columnIndexs, colunmNames);
		if (!isCorrect) {
			return new Result(420, "导入文件数据字段不正确");
		}
//		Session session = SecurityUtils.getSubject().getSession();
//		MfUserInfo userInfo = (MfUserInfo) session.getAttribute("sysUser");
		List<Pay> invitationUserDataList = ExcelUtil.loadListFromExecl(Pay.class,
				file.getInputStream(), columnIndexs, atterNames, 1);
		for (Pay invitationUserData : invitationUserDataList) {
			// 并补充部分差异信息
//			List<MfInvitationUserData> result = mfInvitationUserDataService.queryByExample(invitationUserData);
		
//			mfInvitationUserDataService.add(invitationUserData);
		}
		payService.batchAdd(invitationUserDataList);
		return new Result(200, "导入成功");
	}





}
