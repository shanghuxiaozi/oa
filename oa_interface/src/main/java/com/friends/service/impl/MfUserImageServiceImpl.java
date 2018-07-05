package com.friends.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.friends.dao.MfUserImageDao;
import com.friends.entity.MfUserImage;
import com.friends.entity.vo.MfUserImageDynamicQueryVo;
import com.friends.service.MfUserImageService;
import com.friends.utils.Result;


@Service
public class MfUserImageServiceImpl extends BaseService<MfUserImageDao, MfUserImage> implements MfUserImageService {

	@Autowired
	MfUserImageDao mfUserImageDao;
	@Autowired
	EntityManager em;

	// 通用方法在base层已经有了

	// 动态结构查询
	// Spring-data-jpa-specification形式较简单,但只适用于单表动态结构查询
	// entityManager形式稍复杂，但能实现多表动态约束查询。但多表时jpa返回的是object[][],需要借助工具转成对象。
	// entityManager方式
	@SuppressWarnings("unchecked")
	public Result queryDynamic(MfUserImageDynamicQueryVo userImageVo) {
		int page = userImageVo.getPage();
		int pageSize = userImageVo.getPageSize();
		MfUserImage userImage = userImageVo.getT();
		Map<String, Object> map = new HashMap<String, Object>();

		// 多表sqlCommonBody查询 返回单表数据
		StringBuilder sqlCommonBody = new StringBuilder();
		sqlCommonBody.append(" from mf_user_image t where 1=1 and t.user_id = '" + userImage.getUserId()
				+ "' and t.image_type = '" + userImage.getImageType() + "'");

		String sqlDataQuery = " select distinct  t.* " + sqlCommonBody.toString()
				+ " order by t.create_time desc limit :from, :to ";
		String sqlCountQuery = " select count(1) " + sqlCommonBody.toString();
		Query dataQuery = em.createNativeQuery(sqlDataQuery, MfUserImage.class);
		Query countQuery = em.createNativeQuery(sqlCountQuery);

		// 参数注入
		dataQuery.setParameter("from", (page * pageSize));
		dataQuery.setParameter("to", pageSize);

		// 用户图片表参数设置
		for (String key : map.keySet()) {
			dataQuery.setParameter(key, map.get(key));
			countQuery.setParameter(key, map.get(key));
		}

		// 多表需要使用object[][]来接收 并通过BeanHelper工具将其转换为与sql中结果集字段对应的bean
		List<MfUserImage> list = dataQuery.getResultList();

		return new Result(list, Integer.valueOf(countQuery.getSingleResult().toString()));
	}

	// Specification的方式
	public Result queryDynamicSpecification(MfUserImageDynamicQueryVo userImageVo) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		int page = userImageVo.getPage();
		int pageSize = userImageVo.getPageSize();
		MfUserImage userImage = userImageVo.getT();
		PageRequest pageRequest = new PageRequest(page, pageSize, new Sort(Direction.DESC, "createTime"));

		// 构造动态约束条件
		Specification<MfUserImage> spec = new Specification<MfUserImage>() {
			@Override
			public Predicate toPredicate(Root<MfUserImage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate restraint = cb.and();

				/**
				 * if (!StringUtils.isEmpty(userImage.getUserImageName())) {
				 * Predicate p =
				 * cb.equal(root.get("userImageName").as(String.class),
				 * userImage.getUserImageName()); restraint = cb.and(p,
				 * restraint); }
				 */
				return restraint;
			}
		};
		Page<MfUserImage> usersPage = mfUserImageDao.findAll(spec, pageRequest);
		int total = (int) mfUserImageDao.count(spec);
		hashMap.put("usersPage", usersPage);
		hashMap.put("total", total);
		return new Result(200, usersPage.getContent(), total);
	}

	@Override
	public void delete(String id) {
		mfUserImageDao.delete(id);
	}

	@Override
	public MfUserImage queryById(String id) {
		return mfUserImageDao.findOne(id);
	}

	@Override
	public String findUserHeadImgByuId(String uId) {

		return mfUserImageDao.findUserHeadImgByuId(uId);
	}

	@Override
	public List<String> findUserPhotoByuId(String uId) {

		return mfUserImageDao.findUserPhotoByuId(uId);
	}

	@Override
	public List<MfUserImage> findCarouselPhoto() {

		return mfUserImageDao.findCarouselPhoto();
	}

	@Override
	public Integer queryIsExist(String headImg, String code) {
		return mfUserImageDao.queryIsExist(headImg, code);
	}

	@Override
	public Integer queryIsExistByUser(String photo, String id, String code) {
		return mfUserImageDao.queryIsExistByUser(photo, id, code);
	}

}
