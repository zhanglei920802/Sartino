package com.sartino.huayi.config;

public class NetConfig {
	public static String POTROL = "http://";
	public static String IP = "http://www.sartino.com";
	public static String DB_HOST = "www.sartino.com";
	public static String PORT = ":80";

	public static String Thumbs_URL_PREFIX = POTROL + IP + PORT;
	public static String WEBPATH = "D:/website/v7";
	public static String WEBPAT1 = "D:\\website\\v7";
	public static String MOBILE_PATH = WEBPATH + "/mobile/huayi/";
	
	// 访问服务的格式：
	// http://vmw2k3:8080/Server/servlet/ListServlet?format=json&&type=imageInfo&id=5/1_20121230121209_5exib
	//详细信息获取格式
	//www.sartino.com:8080/Server/servlet/ListServlet?format=xml&id=168&type=detailinfo
}
