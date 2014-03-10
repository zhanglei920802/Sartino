package com.sartino.huayi.service.impl;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import com.sartino.huayi.config.NetConfig;
import com.sartino.huayi.domain.DetailInfo;
import com.sartino.huayi.domain.Image;
import com.sartino.huayi.domain.NavigationInfo;
import com.sartino.huayi.domain.PhotoInfo;
import com.sartino.huayi.domain.SimpleInfo;
import com.sartino.huayi.domain.TitleTypeInfo;
import com.sartino.huayi.service.DBConnection_jdbc;
import com.sartino.huayi.service.DBFactory;
import com.sartino.huayi.service.ImageService;
import com.sartino.huayi.util.FileUtil;
import com.sartino.huayi.util.ImageClip;
import com.sartino.huayi.util.ListUtil;
import com.sun.org.apache.bcel.internal.classfile.Field;

public class ImageServiceBean implements ImageService {
	public List<PhotoInfo> getPhoto(String type_id, String offset,
			String maxresult, int queryType) {

		List<PhotoInfo> photo = new ArrayList<PhotoInfo>();

		try {
			DBFactory factory = new DBFactory();
			DBConnection_jdbc conn = factory.getDBConnectionInstance();
			if (type_id == null)
				type_id = "5";
			String sql = null;
			switch (queryType) {
			case 0:// 升序
				sql = "select * from `v7`.`qb_pic` where `type_id`=" + type_id
						+ " order by `pic_id` asc limit " + offset + ","
						+ maxresult;
				break;
			case 1:// 降序
				sql = "select * from `v7`.`qb_pic` where `type_id`=" + type_id
						+ " order by `pic_id` asc limit " + offset + ","
						+ maxresult;
				break;

			}
			System.out.println(sql);
			Statement stat = conn.getConnection().createStatement();

			ResultSet rs = stat.executeQuery(sql);
			int i = 0;
			while (rs.next()) {
				PhotoInfo photoInfo = new PhotoInfo();
				photoInfo.setPic_id(rs.getShort(1));
				photoInfo.setType_id(rs.getShort(2));
				photoInfo.setRelative_path_of_pic(rs.getString(3));
				photoInfo.setRelative_path_of_origin(rs.getString(4));
				photoInfo.setName_of_pic(rs.getString(5));
				photoInfo.setWidth_of_pic(rs.getString(6));
				photoInfo.setHeight_of_pic(rs.getString(7));
				photoInfo.setTitle_of_pic(rs.getString(8));
				photoInfo.setContent_of_pic(rs.getString(9));
				photoInfo.setOverview_of_pic(rs.getString(10));
				photoInfo.setEn_title_of_pic(rs.getString(11));
				photoInfo.setPrice(rs.getShort(12));
				photoInfo.setStars(rs.getShort(13));

				photo.add(i, photoInfo);
				i++;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return photo;

	}

	public List<NavigationInfo> getNavigations() {

		List<NavigationInfo> photo = new ArrayList<NavigationInfo>();

		try {
			DBFactory factory = new DBFactory();
			DBConnection_jdbc conn = factory.getDBConnectionInstance();

			String sql = "select * from `v7`.`qb_navigation` ";

			Statement stat = conn.getConnection().createStatement();

			ResultSet rs = stat.executeQuery(sql);
			int i = 0;
			while (rs.next()) {
				NavigationInfo navigationInfo = new NavigationInfo();
				navigationInfo.setId(rs.getShort(1));
				navigationInfo.setNavigation_id(rs.getShort(2));
				navigationInfo.setEn_name_of_navigation(rs.getString(3));
				navigationInfo.setZh_name_of_navigation(rs.getString(4));
				navigationInfo.setZh_introduce(rs.getString(5));
				navigationInfo.setDisplay_pic(rs.getString(6));
				navigationInfo.setEn_introduce(rs.getString(7));
				photo.add(i, navigationInfo);
				i++;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return photo;
	}

	public List<TitleTypeInfo> getTitleType() {

		List<TitleTypeInfo> title = new ArrayList<TitleTypeInfo>();

		try {
			DBFactory factory = new DBFactory();
			DBConnection_jdbc conn = factory.getDBConnectionInstance();

			String sql = "select * from `v7`.`qb_title_type` ";
			System.out.println(sql);
			Statement stat = conn.getConnection().createStatement();

			ResultSet rs = stat.executeQuery(sql);
			int i = 0;
			while (rs.next()) {
				TitleTypeInfo titleTypeInfo = new TitleTypeInfo();
				titleTypeInfo.setType_id(rs.getShort(1));
				titleTypeInfo.setNavigation_id(rs.getShort(2));
				titleTypeInfo.setEn_name_of_title_type(rs.getString(3));

				titleTypeInfo.setZh_name_of_title_type(rs.getString(4));

				title.add(i, titleTypeInfo);
				i++;
				// photo.add(i, title);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return title;
	}

	public String getImageDetailInfo() {// 不用连接数据库，只需对文件目录进行便利

		return null;
	}

	public List<SimpleInfo> getSimpleImageInfo(int index, int pagesize,
			int typeid, int queryType) {
		List<SimpleInfo> lists = new ArrayList<SimpleInfo>();

		try {

			DBFactory factory = new DBFactory();
			DBConnection_jdbc conn = factory.getDBConnectionInstance();

			if (typeid == 0) {
				return null;
			}

			String sql = null;

			switch (queryType) {
			case 1://scroll
				sql = "select * from `v7`.`qb_pic` where `type_id`="
						+ String.valueOf(typeid) + " and pic_id<="
						+ String.valueOf(index)
						+ " order by `pic_id` asc limit " + 0 + ","
						+ String.valueOf(pagesize);
				break;
			case 0://reresh
				sql = "select * from `v7`.`qb_pic` where `type_id`="
						+ String.valueOf(typeid) + " and pic_id >="
						+ String.valueOf(index)
						+ " order by `pic_id` asc limit " + 0 + ","
						+ String.valueOf(pagesize);
				break;

			}
			System.out.println(sql);
			Statement stat = conn.getConnection().createStatement();

			ResultSet rs = stat.executeQuery(sql);

			while (rs.next()) {

				SimpleInfo tmp = new SimpleInfo();
				tmp.setId(rs.getInt(1));
				tmp.setCategory(rs.getInt(2));

				tmp.setZh_title(rs.getString(8));
				tmp.setZh_title(tmp.getZh_title().replace("&", "BBBBB"));

				tmp.setEn_title(rs.getString(11));
				tmp.setEn_title(tmp.getEn_title().replace("&", "BBBBB"));

				tmp.setDetail(null);

				Image imgTmp = new Image();
				imgTmp.setWidth(rs.getInt(6));
				imgTmp.setHeight(rs.getInt(7));
				imgTmp.setName(rs.getString(5));

				String originUrl = NetConfig.WEBPATH + "/" + rs.getString(3)
						+ rs.getString(5);
				String newUrl = NetConfig.MOBILE_PATH + rs.getString(3)
						+ rs.getString(5);
				if (!new File(originUrl).isFile()) {
					tmp = null;
					continue;
				}
				File newFile = new File(newUrl);
				if (!newFile.exists()) {
					ImageClip.scaledImage(originUrl, 72, 72, newUrl, "png");
				}

				imgTmp.setUrl(NetConfig.IP + "/" + rs.getString(3)
						+ rs.getString(5));
				imgTmp.setThumbUrl(NetConfig.IP + "/mobile/huayi/"
						+ rs.getString(3) + rs.getString(5));
				tmp.setImg(imgTmp);
				lists.add(tmp);
			}

		} catch (Exception e) {
			System.out.println("ImageServiceBean.getSimpleImageInfo()"
					+ e.getMessage());
		}

		return ListUtil.reverse(lists);
	}

	public DetailInfo getDetialImageInfo(int id) {
		DetailInfo detailInfo = new DetailInfo();
		DBFactory factory = new DBFactory();
		String sql = "select * from `v7`.`qb_pic` where pic_id=" + id;
		try {
			Statement st = factory.getDBConnectionInstance().getConnection()
					.createStatement();

			ResultSet rs = st.executeQuery(sql);

			rs.next();
			detailInfo.setmId(id);
			detailInfo.setmOverView(rs.getString(10));
			detailInfo.setmOverView(detailInfo.getmOverView().replace("<br />",
					"AAAAA"));
			detailInfo.setmOverView(detailInfo.getmOverView().replace("&",
					"BBBBB"));
			detailInfo.setmContent(rs.getString(9));
			detailInfo.setmContent(detailInfo.getmContent().replace("<br />",
					"AAAAA"));
			detailInfo.setmContent(detailInfo.getmContent().replace("&",
					"BBBBB"));
			detailInfo.setUrls(FileUtil.getFileListName(new File(
					NetConfig.WEBPATH + "/" + rs.getString(4))));
			detailInfo.setMsize(detailInfo.getUrls().size());
			
			System.out.println(detailInfo);
		} catch (SQLException e) {
			System.out.println("ImageServiceBean.getDetialImageInfo()"
					+ e.getMessage());
		}

		return detailInfo;
	}
}
