package com.friends.dao;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.friends.entity.MfUserImage;

//
public interface MfUserImageDao extends BaseDao<MfUserImage> {

	// 根据用户id查用户头像
	@Query(value = "select image_name from mf_user_image  where image_type =1 and is_use=1 and user_id=?1 order by create_time desc  limit 1 ", nativeQuery = true)
	public String findUserHeadImgByuId(String uId);

	// 根据用户id查用户图片
	@Query(value = "select image_name from mf_user_image where image_type=2 and is_use=1 and user_id=?1 ", nativeQuery = true)
	public List<String> findUserPhotoByuId(String uId);

	// 查询交友活动首页轮播图片
	@Query(value = "SELECT * from mf_user_image where image_type=3 ORDER BY create_time desc LIMIT 4 ", nativeQuery = true)
	public List<MfUserImage> findCarouselPhoto();

	// 根据用户id查用户头像
	@Query(value = "select count(*) from mf_user_image where user_id=?1 and image_type=?2", nativeQuery = true)
	public Integer queryIsExist(String headImg, String code);
	
	// 根据用户id查用户照片
	@Query(value = "select count(*) from mf_user_image where image_name=?1 and user_id=?2 and image_type=?3", nativeQuery = true)
	public Integer queryIsExistByUser(String headImg, String userId, String code);
	
}
