package com.sartino.huayi.app.api;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.sartino.huayi.app.AppContext;
import com.sartino.huayi.app.AppException;
import com.sartino.huayi.app.bean.DetailInfo;
import com.sartino.huayi.app.bean.SimpleInfo;
import com.sartino.huayi.app.bean.URLs;
import com.sartino.huayi.app.bean.User;
import com.sartino.huayi.app.common.StreamUtils;
import com.sartino.huayi.app.common.XMLUtil;

/**
 * 客户端用户访问网络
 * 
 * @author Administrator
 * 
 */
public class APIClient {
	public static final String UTF_8 = "UTF-8";
	public static final String DESC = "descend";
	public static final String ASC = "ascend";

	private final static int TIMEOUT_CONNECTION = 20000;
	private final static int TIMEOUT_SOCKET = 20000;
	private final static int RETRY_TIME = 3;

	private static String appCookie;
	private static String appUserAgent;

	/**
	 * 创建请求url
	 * 
	 * @param p_url
	 * @param params
	 * @return
	 */
	private static String _MakeURL(String p_url, Map<String, Object> params) {
		StringBuilder url = new StringBuilder(p_url);
		if (url.indexOf("?") < 0)// 如果没有参数
			url.append('?');

		for (String name : params.keySet()) {
			url.append('&');
			url.append(name);
			url.append('=');
			url.append(String.valueOf(params.get(name)));

		}

		return url.toString().replace("?&", "?");
	}

	/**
	 * 获取简要信息
	 * 
	 * @param appContext
	 *            设备上下文
	 * @param format
	 *            格式:xml或者是json
	 * @param type
	 *            类别
	 * @param category
	 *            类型
	 * @param pageindex
	 *            页面索引
	 * @param pagesize
	 *            页面大小
	 * @param action
	 *            动作
	 * @return
	 * @throws AppException
	 */
	public static List<SimpleInfo> getSimpleInfo(AppContext appContext,
			final String format, final String type, final int category,
			final int pageindex, final int pagesize, final int action)
			throws AppException {
		String newUrl = _MakeURL(URLs.URL_API_HOST,
				new HashMap<String, Object>() {
					{
						put("format", format);
						put("type", type);
						put("category", category);
						put("pageindex", pageindex);
						put("pagesize", pagesize);
						put("action", action);
					}
				});

		try {
			return XMLUtil.getSimpleInfoInBatch(http_get(appContext, newUrl));

		} catch (Exception e) {

			if (e instanceof AppException)
				// throw (AppException) e;
				throw AppException.network(e);

		}
		return null;
	}

	// format=xml&id=168&type=detailinfo
	public static DetailInfo getDetailInfo(AppContext appcontex,
			final String format, final int id, final String type)
			throws AppException {

		String newUrl = _MakeURL(URLs.URL_API_HOST,
				new HashMap<String, Object>() {
					{
						put("format", format);
						put("id", id);
						put("type", type);
					}
				});

		try {
			return DetailInfo.parse(http_get(appcontex, newUrl));
		} catch (AppException e) {
			if (e instanceof AppException) {
				throw AppException.http(e);
			}

		}

		return null;

	}

	/**
	 * get请求URL
	 * 
	 * @param url
	 * @throws AppException
	 */
	@SuppressWarnings("unused")
	public static InputStream http_get(AppContext appContext, String url)
			throws AppException {
		String cookie = getCookie(appContext);
		String userAgent = getUserAgent(appContext);

		HttpClient httpClient = null;
		GetMethod httpGet = null;

		String responseBody = "";
		int time = 0;

		do {
			try {
				httpClient = getHttpClient();
				httpGet = getHttpGet(url, cookie, userAgent);
				int statusCode = httpClient.executeMethod(httpGet);
				if (statusCode != HttpStatus.SC_OK) {
					throw AppException.http(statusCode);
				}
				responseBody = httpGet.getResponseBodyAsString();
				break;
			} catch (HttpException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw AppException.network(e);
			} finally {
				// 释放连接
				httpGet.releaseConnection();
				httpClient = null;
			}
		} while (time < RETRY_TIME);

		responseBody = responseBody.replaceAll("\\p{Cntrl}", "");

		return new ByteArrayInputStream(responseBody.getBytes());
	}

	private static HttpClient getHttpClient() {
		HttpClient httpClient = new HttpClient();
		// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		// 设置 默认的超时重试处理策略
		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 设置 连接超时时间
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(TIMEOUT_CONNECTION);
		// 设置 读数据超时时间
		httpClient.getHttpConnectionManager().getParams()
				.setSoTimeout(TIMEOUT_SOCKET);
		// 设置 字符集
		httpClient.getParams().setContentCharset(UTF_8);
		return httpClient;
	}

	private static String getUserAgent(AppContext appContext) {
		if (appUserAgent == null || appUserAgent == "") {
			StringBuilder ua = new StringBuilder("OSChina.NET");
			// ua.append('/' + appContext.getPackageInfo().versionName + '_'
			// + appContext.getPackageInfo().versionCode);// App版本
			ua.append("/Android");// 手机系统平台
			ua.append("/" + android.os.Build.VERSION.RELEASE);// 手机系统版本
			ua.append("/" + android.os.Build.MODEL); // 手机型号
			// ua.append("/" + appContext.getAppId());// 客户端唯一标识
			appUserAgent = ua.toString();
		}
		return appUserAgent;
	}

	private static String getCookie(AppContext appContext) {
		if (appCookie == null || appCookie == "") {
			// appCookie = appContext.getProperty("cookie");
		}
		return appCookie;
	}

	private static PostMethod getHttpPost(String url, String cookie,
			String userAgent) {
		PostMethod httpPost = new PostMethod(url);
		// 设置 请求超时时间
		httpPost.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpPost.setRequestHeader("Host", URLs.HOST);
		httpPost.setRequestHeader("Connection", "Keep-Alive");
		httpPost.setRequestHeader("Cookie", cookie);
		httpPost.setRequestHeader("User-Agent", userAgent);
		return httpPost;
	}

	private static GetMethod getHttpGet(String url, String cookie,
			String userAgent) {
		GetMethod httpGet = new GetMethod(url);
		// 设置 请求超时时间
		httpGet.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpGet.setRequestHeader("Host", URLs.HOST);
		httpGet.setRequestHeader("Connection", "Keep-Alive");
		httpGet.setRequestHeader("Cookie", cookie);
		httpGet.setRequestHeader("User-Agent", userAgent);
		return httpGet;
	}

	/**
	 * 获取网络图片
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getNetBitmap(String url) throws AppException {
		// System.out.println("image_url==> "+url);
		HttpClient httpClient = null;
		GetMethod httpGet = null;
		Bitmap bitmap = null;
		int time = 0;
		do {
			try {
				httpClient = getHttpClient();
				httpGet = getHttpGet(url, null, null);
				int statusCode = 0;
				try {
					statusCode = httpClient.executeMethod(httpGet);
				} catch (Exception e) {
					System.out.println("APIClient.getNetBitmap()"
							+ e.getMessage());
				}

				if (statusCode != HttpStatus.SC_OK) {
					throw AppException.http(statusCode);
				}

				InputStream inStream = httpGet.getResponseBodyAsStream();
				bitmap = BitmapFactory.decodeStream(inStream);
				inStream.close();
				break;
			} catch (HttpException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw AppException.network(e);
			} finally {
				// 释放连接
				httpGet.releaseConnection();
				httpClient = null;
			}
		} while (time < RETRY_TIME);
		return bitmap;
	}

	/**
	 * 登录， 自动处理cookie
	 * 
	 * @param url
	 * @param username
	 * @param pwd
	 * @return
	 * @throws AppException
	 */
	public static User login(AppContext appContext, Bundle bundle)
			throws AppException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", bundle.getString("username"));
		params.put("pwd", bundle.get("pwd"));
		params.put("keep_login", 1);

		String loginurl = URLs.LOGIN_VALIDATE_HTTP;

		try {
			// 数据处理
			// return User.parse(_post(appContext, loginurl, params, null));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
		return null;
	}

	/**
	 * 公用post方法
	 * 
	 * @param url
	 * @param params
	 * @param files
	 * @throws AppException
	 */
	private static InputStream _post(AppContext appContext, String url,
			Map<String, Object> params, Map<String, File> files)
			throws AppException {
		// System.out.println("post_url==> "+url);
		String cookie = getCookie(appContext);
		String userAgent = getUserAgent(appContext);

		HttpClient httpClient = null;
		PostMethod httpPost = null;

		// post表单参数处理
		int length = (params == null ? 0 : params.size())
				+ (files == null ? 0 : files.size());
		Part[] parts = new Part[length];
		int i = 0;
		if (params != null)
			for (String name : params.keySet()) {
				parts[i++] = new StringPart(name, String.valueOf(params
						.get(name)), UTF_8);

			}
		if (files != null)
			for (String file : files.keySet()) {
				try {
					parts[i++] = new FilePart(file, files.get(file));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

			}

		String responseBody = "";
		int time = 0;
		do {
			try {
				httpClient = getHttpClient();
				httpPost = getHttpPost(url, cookie, userAgent);
				httpPost.setRequestEntity(new MultipartRequestEntity(parts,
						httpPost.getParams()));
				int statusCode = httpClient.executeMethod(httpPost);
				if (statusCode != HttpStatus.SC_OK) {
					throw AppException.http(statusCode);
				} else if (statusCode == HttpStatus.SC_OK) {
					Cookie[] cookies = httpClient.getState().getCookies();
					String tmpcookies = "";
					for (Cookie ck : cookies) {
						tmpcookies += ck.toString() + ";";
					}
					// 保存cookie
					if (appContext != null && tmpcookies != "") {
						// appContext.setProperty("cookie", tmpcookies);
						appCookie = tmpcookies;
					}
				}
				responseBody = httpPost.getResponseBodyAsString();
				// System.out.println("XMLDATA=====>"+responseBody);
				break;
			} catch (HttpException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw AppException.network(e);
			} finally {
				// 释放连接
				httpPost.releaseConnection();
				httpClient = null;
			}
		} while (time < RETRY_TIME);

		responseBody = responseBody.replaceAll("\\p{Cntrl}", "");
		if (responseBody.contains("result")
				&& responseBody.contains("errorCode")) {
			try {
				// Result res = Result.parse(new
				// ByteArrayInputStream(responseBody.getBytes()));
				// if(res.getErrorCode() == 0){
				// appContext.Logout();
				// appContext.getUnLoginHandler().sendEmptyMessage(1);
				// }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ByteArrayInputStream(responseBody.getBytes());
	}

	/**
	 * 用户登录
	 * 
	 * @throws IOException
	 */

	public static User userLogin(final String userName, final String pwd) {
		User user = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {

			String newUrl = _MakeURL(URLs.URL_LOGIN,
					new HashMap<String, Object>() {
						{
							put("username", userName);
							put("pwd", pwd);

						}
					});
			HttpGet httpget = new HttpGet(newUrl);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();

			JSONArray jsonarray = new JSONArray(new String(
					StreamUtils.readStream(entity.getContent())));
			user = User.parseJson(jsonarray);

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return user;
	}

	/**
	 * 用户注册
	 */
	@SuppressWarnings("deprecation")
	public static User userRegister(final String userName, final String pwd) {
		User user = null;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {

			HttpPost httpPost = null;
			try {
				httpPost = new HttpPost(URLs.URL_REG);
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("username", userName));
				nvps.add(new BasicNameValuePair("password", pwd));
				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				HttpResponse response2 = httpclient.execute(httpPost);
				HttpEntity entity2 = response2.getEntity();
				if (response2.getStatusLine().getStatusCode() == 200) {
					JSONArray jsonarray = new JSONArray(new String(
							StreamUtils.readStream(entity2.getContent())));
					user = User.parseJson(jsonarray);
				}
				entity2.consumeContent();
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			} catch (ClientProtocolException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			} catch (IllegalStateException e) {

				e.printStackTrace();
			} catch (Exception e) {

				e.printStackTrace();
			}

		} finally {
			httpclient.getConnectionManager().shutdown();

		}
		if(AppContext.DEBUG){
			System.out.println("APIClient.userRegister()"+user.toString());
		}
		return user;
	}
}
