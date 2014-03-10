package com.sartino.huayi.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.sartino.huayi.config.NetConfig;

public class FileUtil {

	/**
	 * 返回制定文件目录的内容
	 * 
	 * @param dir
	 * @return
	 */
	public static List<String> getFileListName(File dir) {
		if (!dir.isDirectory() || dir == null) {
			return null;
		}
		String Suffix = StringProcess(dir.getPath());
		// System.out.println(Suffix);
		List<String> datas = new ArrayList<String>();
		File[] lists = dir.listFiles();
		for (File list : lists) {
			datas.add(Suffix + "/" + list.getName());
		}
		return datas;
	}

	/**
	 * D:\website\v7\images\huayi\origin\12\1_20130105120122_wqosr\1
	 * _20130105120106_ssfjd.jpg
	 * 
	 * @param dir
	 * @return
	 */
	private static String StringProcess(String dir) {

		String tmp = dir.replace(NetConfig.WEBPAT1, NetConfig.IP);

		return tmp.replace("\\", "/");
	}

}
