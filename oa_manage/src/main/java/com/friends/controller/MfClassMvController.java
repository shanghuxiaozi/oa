package com.friends.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.friends.service.impl.QiniuMediaUtilServiceImpl;
import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.friends.entity.MfClassMv;
import com.friends.entity.vo.MfClassMvDynamicQueryVo;
import com.friends.service.MfClassMvService;
import com.friends.utils.Result;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;

@Controller
@RequestMapping(value = "mfClassMv")
public class MfClassMvController {
	private static Logger logger = LoggerFactory.getLogger(MfClassMvController.class);

	@Value("${friends.accessKey}")
	private String accessKey; // 上传凭证key
	@Value("${friends.secretKey}")
	private String secretKey; // 秘钥key
	@Value("${friends.bucketname}")
	private String bucketname; // 要上传的七牛云空间名称
	@Value("${friends.imgprefix}")
	private String imgprefix; // 七牛云图片路劲前缀

	@Autowired
	private MfClassMvService mfClassMvService;
	@Autowired
	private QiniuMediaUtilServiceImpl qiniuMediaUtilServiceImpl;

	@RequestMapping(value = "page")
	public ModelAndView page() {
		return new ModelAndView("classMV/classMV");
	}

	// 单个新增
	@RequestMapping(value = "add")
	@ResponseBody
	public Result add(String name, String address, String messages) {
		MfClassMv mfClassMv = new MfClassMv();
		mfClassMv.setName(name);
		mfClassMv.setAddress(address);
		mfClassMv.setMessages(messages);
		// 加入自定义参数校验，后期将直接支持JSR303
		System.out.println("进入添加");
		mfClassMv.setCreateDate(new Date());
		mfClassMv = mfClassMvService.add(mfClassMv);
		return new Result(200, "新增成功");
	}

	// 单个更新
	@RequestMapping(value = "update")
	@ResponseBody
	public Result update(String id, String name, String address, String messages, String createDate) {
		MfClassMv mfClassMv = new MfClassMv();
		mfClassMv.setId(id);
		mfClassMv.setName(name);
		mfClassMv.setAddress(address);
		mfClassMv.setMessages(messages);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			mfClassMv.setCreateDate(sdf.parse(createDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 加入自定义参数校验，后期将直接支持JSR303
		mfClassMv.setUpdateDate(new Date());
		mfClassMvService.update(mfClassMv, mfClassMv.getId());
		return new Result(200, "更新成功");
	}

	@RequestMapping(value = "/modify")
	@ResponseBody
	public Result modify(String id) {
		MfClassMv mfClassMv = mfClassMvService.queryById(id);
		return new Result(200, "查询成功", mfClassMv);
	}

	// 单个删除
	@RequestMapping(value = "delete")
	@ResponseBody
	public Result delete(MfClassMv mfClassMv) {
		// 加入自定义参数校验，后期将直接支持JSR303
		mfClassMvService.delete(mfClassMv);
		return new Result(200, "删除成功");
	}

	// 批量删除
	// 数组参数以json方式传递。
	@RequestMapping(value = "batchDelete")
	@ResponseBody
	public Result batchDelete(String mfClassMvArrayJson) {
		List<MfClassMv> mfClassMvs = JSON.parseArray(mfClassMvArrayJson, MfClassMv.class);
		// 加入自定义参数校验，后期将直接支持JSR303
		mfClassMvService.batchDelete(mfClassMvs);
		return new Result(200, "批量删除成功");
	}

	// 查询 包括动态结构查询 跟选择的约束方式有关
	// 建议不管有没有约束，都统一使用此查询方式进行查询
	// 范围约束如何传递参数？单独的参数 属性名称Min 属性名称Max 对于这个接口service和dao有对应关系
	@RequestMapping(value = "queryDynamic")
	@ResponseBody
	public Result queryDynamic(String queryRestraintJson) {
		MfClassMvDynamicQueryVo mfClassMvDynamicQueryVo = JSON.parseObject(queryRestraintJson,
				MfClassMvDynamicQueryVo.class);
		Result mapResult = mfClassMvService.queryDynamic(mfClassMvDynamicQueryVo);
		return new Result(200, "查询成功", mapResult.getData(), mapResult.getTotal());
	}

	/*************************
	@RequestMapping(value = "indexImgUpLoad")
	@ResponseBody
	public Result uploadImg(HttpServletRequest request, HttpServletResponse response, MultipartFile file) {
		ByteArrayInputStream inputStream = null;

		String str = "http://p0ufihw8r.bkt.clouddn.com/1516443735847n.mp4";

		File file3 = new File("D:\\Myphoto\\11111.mp4");
		String strUrl = qiniuMediaUtilServiceImpl.qiNiuMediaPrtScreen(file3.getPath(),"png");
		logger.info("截屏路劲："+strUrl);
		try {
			String name = System.currentTimeMillis()+"n.mp4";				// 时间不会出现重复
			inputStream = new ByteArrayInputStream(file.getBytes());
			boolean isTrue = qiniuMediaUtilServiceImpl.uploadFile(name,inputStream);
			if(isTrue){
				String fileName = qiniuMediaUtilServiceImpl.getFileResourceUrl(name);
				logger.info("视频上传成功，截取一张图片做为封面");
				String str = "http://p0ufihw8r.bkt.clouddn.com/1516443735847n.mp4";
				File file3 = new File("D:\\Myphoto\\11111.mp4");
				String strUrl = qiniuMediaUtilServiceImpl.qiNiuMediaPrtScreen(file3.getPath(),"png");
				logger.info("截屏路劲："+strUrl);
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("文件路劲不存在");
		} catch (AuthException e) {
			e.printStackTrace();
			logger.info("文件路劲不存在");
		} catch (JSONException e) {
			e.printStackTrace();
			logger.info("文件路劲不存在");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	 ****************/


	@RequestMapping(value = "indexImgUpLoad")
	@ResponseBody
	public Result uploadImg(HttpServletRequest request, HttpServletResponse response, MultipartFile file) {
		// 获取 Mac
		Mac mac = new Mac(accessKey, secretKey);
		logger.info("mac设置成功");
		// 获取 token
		PutPolicy putPolicy = new PutPolicy(bucketname);
		logger.info("bucketname设置成功");
		String uptoken = null;
		try {
			uptoken = putPolicy.token(mac);
			logger.info("uptoken设置成功");
		} catch (JSONException e1) {
			logger.info("uptoken获取失败" + e1.getMessage());
			return new Result(400, "uptoken获取失败，请联系管理员");
		} catch (AuthException e1) {
			logger.info("uptoken获取失败" + e1.getMessage());
			return new Result(400, "uptoken获取失败，请联系管理员");
		}
		if (file == null) {
			logger.info("视频不能为空");
			return new Result(400, "视频不能为空");
		}
		long size = file.getSize();
		if (size > 1024 * 1024 * 500) {
			logger.info("视频不能超过500M");
			return new Result(400, "视频不能超过500M");
		}
		String names = file.getOriginalFilename();// 获取文件全名
		if (names != null && names.length() <= 3) {
			logger.info("视频格式不正确");
			return new Result(400, "视频格式不正确");
		}
		long startTime = System.currentTimeMillis();// 获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");// 设置名字以确保不重复
		String datetime = sdf.format(startTime);	// 格式化时间
		String fileName = datetime;					// 时间+文件名 不会出现重复
		ByteArrayInputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(file.getBytes());
			logger.info("视频文件解析成功!");
		} catch (IOException e) {
			logger.info("视频文件解析错误!" + e.getMessage());
			return new Result(400, "视频文件解析错误!请联系管理员！");
		}
		PutExtra extra = new PutExtra();
		// 上传文件
		long start = System.currentTimeMillis();
		PutRet ret = IoApi.Put(uptoken, fileName, inputStream, extra);
		long endTime = System.currentTimeMillis();
		if (ret.ok()) {
			logger.info("视频文件上传成功!");
			logger.info(ret.getHash());
			logger.info(ret.getKey());
			logger.info(ret.getResponse());
			long endTimes = System.currentTimeMillis();
			logger.info("视频文件上传用时：" + (endTimes - startTime) / 1000 + "s");
		} else {
			logger.error("视频文件上传失败!");
			logger.info(ret.getException().getMessage());
			return new Result(400, "视频上传失败！");
		}
		String name = names.substring(names.length() - 3).toUpperCase();
		if (name.equalsIgnoreCase("MP4")) {
			logger.error("视频文件为MP4格式，无需转码");
			return new Result(200, "视频上传成功", imgprefix + ret.getKey());
		} else {
			logger.error("正在转码");
			Auth auth = Auth.create(accessKey, secretKey);
			OperationManager operationMgr = new OperationManager(auth, new Configuration(Zone.zone0()));
			String newName = sdf.format(System.currentTimeMillis()) + ".mp4";
			String saveAs = UrlSafeBase64.encodeToString(bucketname + ":" + newName);
			String fops = "avthumb/mp4/vcodec/libx264|saveas/" + saveAs+";vframe/jpg/offset/7/w/480/h/360";
			String pipeline = "bucketname"; // 处理队列
			try {
				// 执行转码和另存 操作
				String persistentId = operationMgr.pfop(bucketname, ret.getKey(), fops,
						new StringMap().put("persistentPipeline", pipeline));
				logger.error("转码成功");
				return new Result(200, "视频上传成功" , imgprefix + newName);
			} catch (QiniuException e) {
				String errorCode = String.valueOf(e.response.statusCode);
				logger.error(errorCode + ":转码失败" + e.getMessage());
				return new Result(200, "视频上传成功", imgprefix + ret.getKey());
			}
		}

	}


}
