package com.friends.service;

import java.util.List;
import org.springframework.data.domain.PageRequest;
import com.friends.entity.MfUserImage;
import com.friends.entity.vo.MfUserImageDynamicQueryVo;
import com.friends.utils.Result;


public interface MfUserImageService  {
	
	/**
	 * 通过用户图片表vo参数查询用户图片表列表
	 * @param userImageVo 用户图片表vo
	 * @return Result(列表数据，分页数据，消息数据，以及)
	 */
	public Result queryDynamic(MfUserImageDynamicQueryVo userImageVo);//通过用户图片表vo参数查询用户图片表列表
	public MfUserImage add(MfUserImage t);//用户图片表添加
	public void delete(String id);//用户图片表删除
	public MfUserImage updateSelective(MfUserImage t);//用户图片表更新
	public MfUserImage update(MfUserImage t);//用户图片表更新
	public MfUserImage queryById(String id);//通过用户图片表主键值查询用户图片表信息
	public List<MfUserImage> queryByExample(MfUserImage t);//通过用户图片表vo参数查询用户图片表列表
	public List<MfUserImage> queryByExamplePageable(MfUserImage t,PageRequest pageRequest);
	public void batchAdd(List<MfUserImage> ts);//批量添加用户图片表
	public void batchDelete(List<MfUserImage> ts);//批量删除用户图片表
	public Result queryDynamicSpecification(MfUserImageDynamicQueryVo userImageVo);//动态查询用户图片表
	public String findUserHeadImgByuId(String uId); //根据用户id查用户头像
	public List<String> findUserPhotoByuId(String uId);//根据用户id查用户图片
	public List<MfUserImage> findCarouselPhoto();//查询交友活动首页轮播图片
	public Integer queryIsExist(String headImg, String code);//判断该图片是否存在
	public Integer queryIsExistByUser(String photo, String id, String code);
	
	
}
