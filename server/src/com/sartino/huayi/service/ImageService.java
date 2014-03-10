package com.sartino.huayi.service;

import java.util.List;

import com.sartino.huayi.domain.DetailInfo;
import com.sartino.huayi.domain.NavigationInfo;
import com.sartino.huayi.domain.PhotoInfo;
import com.sartino.huayi.domain.SimpleInfo;
import com.sartino.huayi.domain.TitleTypeInfo;

public interface ImageService {

	public abstract List<PhotoInfo> getPhoto(String type_id, String offset,
			String maxresult, int queryType);

	public abstract List<NavigationInfo> getNavigations();

	public abstract List<TitleTypeInfo> getTitleType();

	public abstract String getImageDetailInfo();

	/**
	 * 查找数据记录，返回一个list集合
	 * 
	 * @param index
	 *            开始索引
	 * @param pagesize
	 *            数据快大小
	 * @param typeid
	 *            类别
	 * @param queeryType
	 *            查询方式 1:升序 0 :讲叙
	 * @return
	 */
	public abstract List<SimpleInfo> getSimpleImageInfo(int index,
			int pagesize, int typeid, int queeryType);
	
	/**
	 * 根据id返回一条记录
	 * @param id
	 * @return
	 */
	public abstract DetailInfo getDetialImageInfo(int id);
}