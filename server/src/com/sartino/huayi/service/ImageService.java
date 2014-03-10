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
	 * �������ݼ�¼������һ��list����
	 * 
	 * @param index
	 *            ��ʼ����
	 * @param pagesize
	 *            ���ݿ��С
	 * @param typeid
	 *            ���
	 * @param queeryType
	 *            ��ѯ��ʽ 1:���� 0 :����
	 * @return
	 */
	public abstract List<SimpleInfo> getSimpleImageInfo(int index,
			int pagesize, int typeid, int queeryType);
	
	/**
	 * ����id����һ����¼
	 * @param id
	 * @return
	 */
	public abstract DetailInfo getDetialImageInfo(int id);
}