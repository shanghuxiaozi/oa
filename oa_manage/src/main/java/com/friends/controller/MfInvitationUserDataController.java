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
import com.friends.service.MfUserDataDetailsService;
import com.friends.service.MfUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
@RequestMapping(value = "InvitationUser")
public class MfInvitationUserDataController {
	private static Logger logger = LoggerFactory.getLogger(MfInvitationUserDataController.class);

	@Autowired
	MfInvitationUserDataService mfInvitationUserDataService;
	@Autowired
	MfUserDataDetailsService mfUserDataDetailsService;
	@Autowired
	MfUserInfoService mfUserInfoService;

	@RequestMapping(value = "page")
	public ModelAndView page() {
		return new ModelAndView("user/InvitationUserList");
	}

	// 单个新增
	@RequestMapping(value = "add")
	@ResponseBody
	public Result add(MfInvitationUserData mfInvitationUserData) {
		logger.info("添加");
		// 加入自定义参数校验，后期将直接支持JSR303
		mfInvitationUserData = mfInvitationUserDataService.add(mfInvitationUserData);
		return new Result(200, "新增成功");
	}

	// 单个修改
	@RequestMapping(value = "update")
	@ResponseBody
	public Result update(String id,String json) {
		logger.info("修改受邀用户资料");
		MfInvitationUserData mfInvitationUserData = JSON.parseObject(json,MfInvitationUserData.class);
		MfInvitationUserData invitationUserData = mfInvitationUserDataService.queryById(id);
		invitationUserData.setRealName(mfInvitationUserData.getRealName());
		invitationUserData.setBirthTime(mfInvitationUserData.getBirthTime());
		invitationUserData.setGrowingPlace(mfInvitationUserData.getGrowingPlace());

		invitationUserData.setEducation(mfInvitationUserData.getEducation());
		invitationUserData.setHeight(mfInvitationUserData.getHeight());
		invitationUserData.setIdcard(mfInvitationUserData.getIdcard());
		invitationUserData.setPhone(mfInvitationUserData.getPhone());
		invitationUserData.setSex(mfInvitationUserData.getSex());
		// ============================
		invitationUserData.setNation(mfInvitationUserData.getNation());						//民族
		invitationUserData.setPoliticalVisage(mfInvitationUserData.getPoliticalVisage());	//政治面貌
		invitationUserData.setBirthplace(mfInvitationUserData.getBirthplace());				//籍贯
		invitationUserData.setWorkUnit(mfInvitationUserData.getWorkUnit());					//工作单位
		invitationUserData.setMaritalStatus(mfInvitationUserData.getMaritalStatus());  		//婚姻状态
		invitationUserData.setUnitAttribute(mfInvitationUserData.getUnitAttribute()); 		//单位属性
		invitationUserData.setPostNature(mfInvitationUserData.getPostNature());				//岗位性质

		mfInvitationUserData = mfInvitationUserDataService.add(invitationUserData);

		//把用户资料详情表  也给更改了。
		if(StringUtils.isNoneEmpty(invitationUserData.getUserId())){
			logger.info("受邀用户已经注册，同步修改用户详情，用户表："+invitationUserData.getUserId());
			// 同步修改用户详情表
			MfUserDataDetails userDataDetails = mfUserDataDetailsService.findUserInformationByuId(invitationUserData.getUserId());
			if(null != userDataDetails){
				userDataDetails.setRealName(mfInvitationUserData.getRealName());
				userDataDetails.setBirthTime(mfInvitationUserData.getBirthTime());
				userDataDetails.setBirthPlace(mfInvitationUserData.getGrowingPlace());  //出生地
				userDataDetails.setEducation(mfInvitationUserData.getEducation());
				userDataDetails.setHeight(mfInvitationUserData.getHeight());
				userDataDetails.setIdcard(mfInvitationUserData.getIdcard());
				userDataDetails.setPhone(mfInvitationUserData.getPhone());
				userDataDetails.setSex(mfInvitationUserData.getSex());

				userDataDetails.setNation(mfInvitationUserData.getNation());			//民族
				userDataDetails.setHometown(mfInvitationUserData.getBirthplace());     //籍贯
				userDataDetails.setPoliticalVisage(mfInvitationUserData.getPoliticalVisage());
				userDataDetails.setWorkUnit(mfInvitationUserData.getWorkUnit());		//工作单位
				userDataDetails.setMarriageStatus(mfInvitationUserData.getMaritalStatus());		//婚姻状态
				userDataDetails.setUnitAttribute(mfInvitationUserData.getUnitAttribute());	//单位属性
				userDataDetails.setPostNature(mfInvitationUserData.getPostNature()); 		//岗位性质
				mfUserDataDetailsService.update(userDataDetails);
			}
			// 同步修改用户表
			MfUserInfo userInfo = mfUserInfoService.queryById(userDataDetails.getUserId());
			userInfo.setName(mfInvitationUserData.getRealName());
			userInfo.setSex(mfInvitationUserData.getSex());
			userInfo.setPhone(mfInvitationUserData.getPhone());
			mfUserInfoService.update(userInfo);
		}
		return new Result(200, "修改成功");
	}

	// 单个删除
	@RequestMapping(value = "delete")
	@ResponseBody
	public Result delete(String id) {
		logger.info("删除受邀用户资料");
		MfInvitationUserData invitationUserData = mfInvitationUserDataService.queryById(id);
		if(null != invitationUserData){
			//删除关联信息
			if(StringUtils.isNoneEmpty(invitationUserData.getUserId())){
				logger.info("检测到该用户已经注册，同步删除其注册信息");
				MfUserDataDetails userDataDetails = new MfUserDataDetails();
				userDataDetails.setUserId(invitationUserData.getUserId());
				List<MfUserDataDetails> mfUserDataDetailList = mfUserDataDetailsService.queryByExample(userDataDetails);
				mfUserDataDetailsService.batchDelete(mfUserDataDetailList);
				mfUserInfoService.delete(invitationUserData.getUserId());
			}
			mfInvitationUserDataService.delete(invitationUserData);
			return new Result(200, "删除成功");
		}
		return new Result(400, "删除失败，数据已经不存在！");
	}

	// 批量删除
	// 数组参数以json方式传递。
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public Result batchDelete(String mfInvitationUserDataArrayJson) {
		List<MfInvitationUserData> mfInvitationUserDatas = JSON.parseArray(mfInvitationUserDataArrayJson,
				MfInvitationUserData.class);
		// 加入自定义参数校验，后期将直接支持JSR303
		mfInvitationUserDataService.batchDelete(mfInvitationUserDatas);
		return new Result(200, "批量删除成功");
	}

	// 查询 包括动态结构查询 跟选择的约束方式有关
	// 建议不管有没有约束，都统一使用此查询方式进行查询
	// 范围约束如何传递参数？单独的参数 属性名称Min 属性名称Max 对于这个接口service和dao有对应关系
	@RequestMapping(value = "queryDynamic")
	@ResponseBody
	public Result queryDynamic(Integer page, Integer pageSize, String queryRestraintJson) {
		MfInvitationUserDataDynamicQueryVo mfInvitationUserDataDynamicQueryVo = JSON.parseObject(queryRestraintJson,
				MfInvitationUserDataDynamicQueryVo.class);
		mfInvitationUserDataDynamicQueryVo.setPage(page - 1);
		mfInvitationUserDataDynamicQueryVo.setPageSize(pageSize);
		Result mapResult = mfInvitationUserDataService.queryDynamic(mfInvitationUserDataDynamicQueryVo);
		List<MfInvitationUserData> list = (List<MfInvitationUserData>)mapResult.getData();
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
		String[] colunmNames = {"姓名","性别","民族","出生年月","籍贯","出生地","政治面貌","学历","婚姻状况","身高cm","身份证号码","联系手机","工作单位及职务","单位属性","岗位性质",
				"入职时间","离职时间","合同开始时间","合同结束时间","基本工资","是否离职1是0否","是否在项目中1是0不是","项目名"};
		String[] atterNames = { "realName", "sex", "nation", "birthTime","birthplace","growingPlace","politicalVisage","education","maritalStatus","height","idcard","phone","workUnit","unitAttribute","postNature",
				"startTime","endTime","contractStartTime","contractEndTime","basyPay","isLeave","isJob","whichOne"};
		// 加入一条范例
		MfInvitationUserData invitationUserData = new MfInvitationUserData();
		invitationUserData.setRealName("李某某");
		invitationUserData.setSex("1");
		invitationUserData.setNation("汉族");
		invitationUserData.setBirthTime(new Date());
		invitationUserData.setBirthplace("广东省");
		invitationUserData.setGrowingPlace("广东省深圳市宝安区xxx");
		invitationUserData.setPoliticalVisage("群众");
		invitationUserData.setEducation("本科");
		invitationUserData.setMaritalStatus("未婚");
		invitationUserData.setHeight(180);
		invitationUserData.setIdcard("620821197603108241");
		invitationUserData.setPhone("13766668888");
		invitationUserData.setWorkUnit("华讯方舟集团有限公司");
		invitationUserData.setUnitAttribute("单位属性");
		invitationUserData.setPostNature("岗位性质");
		invitationUserData.setStartTime(new Date());
		invitationUserData.setEndTime(new Date());
		invitationUserData.setContractStartTime(new Date());
		invitationUserData.setContractEndTime(new Date());
		invitationUserData.setBasyPay(new BigDecimal(10000));
		invitationUserData.setIsLeave(0);
		invitationUserData.setIsJob(1);
		invitationUserData.setWhichOne("餐饮");

		XSSFWorkbook book = new XSSFWorkbook();
		XSSFSheet sheet = book.createSheet("单身职工个人信息模板");
		for (int i = 0; i < 15; i++) {
			sheet.setColumnWidth(i, 6000);
		}
		ExcelUtil.appendRowToSheet(sheet, colunmNames, true);
		ExcelUtil.appendRowObjectToSheetSelective(sheet, invitationUserData, atterNames);

		response.setCharacterEncoding("UTF-8");
		response.addHeader("Content-type", " application/octet-stream");
		String fileName = new String(("单身职工个人信息模板").getBytes(), "ISO8859_1");
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
	public void downloadUserData(String idcard,String phone,String status,String unitAttribute,String postNature,String wordUnit,HttpServletResponse response)throws Exception{
		String[] colunmNames = {"姓名","性别","民族","出生年月","籍贯","出生地","政治面貌","学历","婚姻状况","身高cm","身份证号码","联系手机","工作单位及职务","单位属性","岗位性质",
				"入职时间","离职时间","合同开始时间","合同结束时间","基本工资","是否离职1是0否","是否在项目中1是0不是","项目名"};
		String[] atterNames = { "realName", "sex", "nation", "birthTime","birthplace","growingPlace","politicalVisage","education","maritalStatus","height","idcard","phone","workUnit","unitAttribute","postNature",
				"startTime","endTime","contractStartTime","contractEndTime","basyPay","isLeave","isJob","whichOne"};

		List<MfInvitationUserData> invitationUserDataList = mfInvitationUserDataService.queryByParam(idcard,phone,status,unitAttribute,postNature,wordUnit);

		XSSFWorkbook book = new XSSFWorkbook();
		XSSFSheet sheet = book.createSheet("单身职工个人信息模板");
		for (int i = 0; i < 15; i++) {
			sheet.setColumnWidth(i, 6000);
		}
		ExcelUtil.appendRowToSheet(sheet, colunmNames, true);
		if(null != invitationUserDataList && invitationUserDataList.size() > 0){
			for(MfInvitationUserData invitationUserData : invitationUserDataList){
				ExcelUtil.appendRowObjectToSheetSelective(sheet, invitationUserData, atterNames);
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Content-type", " application/octet-stream");
		String fileName = new String(("单身职工个人信息模板").getBytes(), "ISO8859_1");
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
		int[] columnIndexs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,13,14,15,16,17,18,19,20,21,22};
		String[] colunmNames = {"姓名","性别","民族","出生年月","籍贯","出生地","政治面貌","学历","婚姻状况","身高cm","身份证号码","联系手机","工作单位及职务","单位属性","岗位性质",
				"入职时间","离职时间","合同开始时间","合同结束时间","基本工资","是否离职1是0否","是否在项目中1是0不是","项目名"};
		String[] atterNames = { "realName", "sex", "nation", "birthTime","birthplace","growingPlace","politicalVisage","education","maritalStatus","height","idcard","phone","workUnit","unitAttribute","postNature",
				"startTime","endTime","contractStartTime","contractEndTime","basyPay","isLeave","isJob","whichOne"};
		// 检查第一行字段内容是否匹配
		boolean isCorrect = ExcelUtil.checkImportExecl(file.getInputStream(), columnIndexs, colunmNames);
		if (!isCorrect) {
			return new Result(420, "导入文件数据字段不正确");
		}
//		Session session = SecurityUtils.getSubject().getSession();
//		MfUserInfo userInfo = (MfUserInfo) session.getAttribute("sysUser");
		List<MfInvitationUserData> invitationUserDataList = ExcelUtil.loadListFromExecl(MfInvitationUserData.class,
				file.getInputStream(), columnIndexs, atterNames, 1);
		/*for (MfInvitationUserData invitationUserData : invitationUserDataList) {
			// 并补充部分差异信息
//			List<MfInvitationUserData> result = mfInvitationUserDataService.queryByExample(invitationUserData);
			Integer sex = Double.valueOf(invitationUserData.getSex()).intValue();
			SimpleDateFormat dataFormat =  new SimpleDateFormat("yyyy-MM-dd");
			String strDate = dataFormat.format(invitationUserData.getBirthTime());
			invitationUserData.setSex(String.valueOf(sex));
			invitationUserData.setBirthTime(dataFormat.parse(strDate));

			if(invitationUserData.getBirthplace().contains("黑龙")){
				invitationUserData.setBirthplace("黑龙江");
			}else if(invitationUserData.getBirthplace().contains("内蒙")){
				invitationUserData.setBirthplace("内蒙古");
			}else {
				invitationUserData.setBirthplace(invitationUserData.getBirthplace().substring(0,2));
			}
//			mfInvitationUserDataService.add(invitationUserData);
		}*/
		mfInvitationUserDataService.batchAdd(invitationUserDataList);
		return new Result(200, "导入成功");
	}





}
