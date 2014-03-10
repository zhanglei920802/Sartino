package com.sartino.huayi.util;

import java.io.File;
import java.util.List;
import com.sartino.huayi.config.NetConfig;
import com.sartino.huayi.domain.NavigationInfo;
import com.sartino.huayi.domain.PhotoInfo;
import com.sartino.huayi.domain.TitleTypeInfo;

/**
 * 
 * @author Administrator
 * 
 */
public class DataProcess {
	/**
	 * 
	 * @param images
	 *            �ӷ��������ص�ͼƬ����
	 * @return
	 * @throws Exception
	 */
	public static StringBuilder ProcessImages(List<PhotoInfo> images)
			throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		String webPath = "E:/workspace/website/v7/";
		String mobliePath = "E:/workspace/website/v7/mobile/huayi/";

		File file;

		for (PhotoInfo image : images) {
			String tmpPath = null;
			builder.append('{');
			builder.append("pic_id:").append(image.getPic_id()).append(',');
			builder.append("type_id:").append(image.getType_id()).append(',');

			tmpPath = webPath + image.getRelative_path_of_pic()
					+ image.getName_of_pic();
			System.out.println(tmpPath);

			String newUrl = mobliePath + image.getRelative_path_of_pic()
					+ image.getName_of_pic();

//			String moblieNewUrl = NetConfig.POTROL + NetConfig.IP
//					+ NetConfig.PORT + NetConfig.MOBILE
//					+ image.getRelative_path_of_pic() + image.getName_of_pic();//name

			System.out.println(newUrl);
			file = new File(newUrl);
			if (!file.exists()) {
				// ����ͼƬ72,72
				ImageClip.scaledImage(tmpPath, 72, 72, newUrl, "png");
			}
//			builder.append("imagePath:\"").append(moblieNewUrl).append("\",");

			builder.append("title_of_pic:\"").append(image.getTitle_of_pic())
					.append("\",");

			builder.append("content_of_pic:\"")
					.append(image.getContent_of_pic()).append("\",");

			builder.append("overview_of_pic:\"")
					.append(image.getOverview_of_pic()).append("\",");

			builder.append("en_title_of_pic:\"")
					.append(image.getEn_title_of_pic()).append("\",");

			builder.append("price:").append(image.getPrice()).append(',');
			builder.append("stars:").append(image.getStars());
			builder.append("},");
		}
		// builder.append("},");
		builder.deleteCharAt(builder.length() - 1);
		builder.append(']');
		System.out.println(builder);
		return builder;

	}

	public static StringBuilder ProcessNavigation(
			List<NavigationInfo> navigation) {
		StringBuilder builder = new StringBuilder();
		builder.append('[');

		for (NavigationInfo nav : navigation) {
			builder.append('{');
			builder.append("id:").append(nav.getId()).append(',');
			builder.append("navigation_id:").append(nav.getNavigation_id())
					.append(',');

			builder.append("zh_name_of_navigation:\"")
					.append(nav.getZh_name_of_navigation()).append("\",");
			builder.append("en_name_of_navigation:\"")
					.append(nav.getEn_name_of_navigation()).append("\"");
			builder.append("},");

		}

		// builder.append("},");
		builder.deleteCharAt(builder.length() - 1);
		builder.append(']');
		System.out.println(builder);
		return builder;
	}

	public static StringBuilder ProcessTitleType(List<TitleTypeInfo> titles) {

		StringBuilder builder = new StringBuilder();
		builder.append('[');

		for (TitleTypeInfo title : titles) {
			builder.append('{');
			builder.append("type_id:").append(title.getType_id()).append(',');
			builder.append("navigation_id:").append(title.getNavigation_id())
					.append(',');
			builder.append("en_name_of_title_type:\"")
					.append(title.getEn_name_of_title_type()).append("\",");
			builder.append("zh_name_of_title_type:\"")
					.append(title.getZh_name_of_title_type()).append("\"");

			builder.append("},");

		}

		// builder.append("},");
		builder.deleteCharAt(builder.length() - 1);
		builder.append(']');

		return builder;
	}

	public static StringBuilder getPicList(String fileDir) {
		
		if (null == fileDir) {// ������ݽ�����Ŀ¼�ǿ�,�����˳�

			return null;
		}

		File file = new File("E:/workspace/website/v7/images/huayi/origin/"
				+ fileDir);

		File[] fileList = file.listFiles();

		if (null == fileList) {// ����˳���·��������ʾһ��Ŀ¼����ô�˷��������� null,�����˳�
			return null;
		}

		
		StringBuilder builder = new StringBuilder();
		//builder.append("[{");

		for (File singleFile : fileList) {// ���ļ���Ϣ���浽һ������֮�У�Ȼ����뵽list����

			builder.append(singleFile.getName()).append("AAAA");
			
		}

		builder.delete(builder.length() - 4, builder.length());
	
		//builder.append("}]");

		System.out.println(builder.toString());
		return builder;

	}
}