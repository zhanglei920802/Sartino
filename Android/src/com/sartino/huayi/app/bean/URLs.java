package com.sartino.huayi.app.bean;

import java.io.Serializable;

public class URLs implements Serializable {
	public final static String HOST = "www.sartino.com";

	public final static String HTTP = "http://";
	public final static String HTTPS = "https://";
	public final static String WEB_PORT = ":8080";
	public final static String SUFFIX = "/Server/servlet/ListServlet";

	private final static String URL_SPLITTER = "/";
	private final static String URL_UNDERLINE = "_";

	public final static String URL_API_HOST = HTTP + HOST + WEB_PORT + SUFFIX;
	public final static String URL_LOGIN = HTTP + HOST + WEB_PORT
			+ "/Server/login";
	public final static String URL_REG = HTTP + HOST + WEB_PORT
			+ "/Server/Register";
	public static final String LOGIN_VALIDATE_HTTP = null;

}
