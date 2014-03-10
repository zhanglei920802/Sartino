package com.sartino.huayi.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import com.sartino.huayi.app.api.APIClient;
import com.sartino.huayi.app.bean.DetailInfo;
import com.sartino.huayi.app.bean.Result;
import com.sartino.huayi.app.bean.SimpleDatas;
import com.sartino.huayi.app.bean.SimpleInfo;
import com.sartino.huayi.app.bean.User;
import com.sartino.huayi.app.common.CyptoUtils;
import com.sartino.huayi.app.common.DateUtil;
import com.sartino.huayi.app.common.MethodsCompat;
import com.sartino.huayi.app.common.StringUtils;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.CacheManager;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * 
 * @author Administrator
 * 
 */
public class AppContext extends Application {
	public static final int NETYPE_WIFI = 0x01;
	public static final int NETTYPE_CMWAP = 0x02;
	public static final int NETTYPE_CMNET = 0x02;

	public static final int PAGE_SIZE = 10;
	public static final int CACHE_TIME = 60 * 6000;// 缓存时间

	public static final String TYPE_SIMPLE = "simpleinfo";
	public static final String TYPE_DETAIL = "detailinfo";

	public static final String FORMAT_JSON = "json";
	public static final String FORMAT_XML = "xml";

	public static final int CATEGORY_BRAND_OVERVIEW = 0x01;// 理念
	public static final int CATEGORY_BRAND_CULTURE = 0x02;// 文化
	public static final int CATEGORY_CONCEPT_CONCEPT = 0x03;// 理念
	public static final int CATEGORY_DISPLAY_DISPLAY = 0x04;// 展示
	public static final int CATEGORY_DESIGN_SHOWCASE = 0x05;// 橱窗
	public static final int CATEGORY_DESIGN_MEETING = 0x06;// 会议
	public static final int CATEGORY_DESIGN_WEDDING = 0x07;// 婚庆
	public static final int CATEGORY_DESIGN_DISPLAY = 0x08;// 美陈
	public static final int CATEGORY_DESIGN_LANDSCAPE = 0x09;// 景观
	public static final int CATEGORY_DESIGN_INNER = 0x0A;// 室内
	public static final int CATEGORY_DESIGN_DESIGNER = 0x0B;// 设计师11
	public static final int CATEGORY_HUALI_WESTERN = 0x0C;// 西式风格
	public static final int CATEGORY_HUALI_EAST = 0x0D;// 东方流派
	public static final int CATEGORY_HUAYI_WESTERN = 0x0E;// 西式风格
	public static final int CATEGORY_HUAYI_EAST = 0x0F;// 东方流派
	public static final int CATEGORY_JOIN_ADVANTAGE = 0x10;// 优势
	public static final int CATEGORY_JOIN_PROCESS = 0x11;// 加盟流程
	public static final int CATEGORY_JOIN_RETURN = 0x12;// 回报
	public static final int CATEGORY_JOIN_MARKETING = 0x13;// 营销网络
	public static final int CATEGORY_JOIN_POLICY = 0x14;// 加盟优势
	public static final int CATEGORY_DOWNLOAD_DOWNLOAD = 0x15;// 下载

	public static final boolean DEBUG = true;
	private boolean login = false;
	private int loginUid = 0;
	private Hashtable<String, Object> memCacheRegion = new Hashtable<String, Object>();

	private User user = null;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getLoginUid() {
		return loginUid;
	}

	public void setLoginUid(int loginUid) {
		this.loginUid = loginUid;
	}

	private static AppContext mAppcontex = null;

	@Override
	public void onCreate() {
		super.onCreate();

		// 初始化cache

		// 注册App异常崩溃处理器
		Thread.setDefaultUncaughtExceptionHandler(AppException
				.getAppExceptionHandler());
	}

	public static AppContext getInstance() {
		if (mAppcontex == null) {
			mAppcontex = new AppContext();
		}
		return mAppcontex;
	}

	/**
	 * 获取当前网络类型
	 * 
	 * @return 0:没有网络 1:wifi网络 2:wap网络
	 */

	public int getNetWorkType() {
		int netType = 0;

		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netWorkInfo = connectivityManager.getActiveNetworkInfo();

		if (netWorkInfo == null) {
			return netType;
		}

		int nType = netWorkInfo.getType();
		if (nType == ConnectivityManager.TYPE_MOBILE) {
			String extraInfo = netWorkInfo.getExtraInfo();
			if (!StringUtils.isEmpty(extraInfo)) {
				netType = NETTYPE_CMNET;
			} else {
				netType = NETTYPE_CMWAP;
			}
		} else if (nType == ConnectivityManager.TYPE_WIFI) {
			netType = NETYPE_WIFI;
		}

		return netType;
	}

	/**
	 * 获取App安装包信息
	 */

	public PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null) {
			info = new PackageInfo();
		}
		return info;
	}

	/**
	 * 获取App唯一标识
	 * 
	 * @return
	 */
	public String getAppId(String userid) {
		String uniqueID = getProperty(AppConfig.CONF_APP_UNIQUEID, userid);
		if (StringUtils.isEmpty(uniqueID)) {
			uniqueID = UUID.randomUUID().toString();
			// setProperty(AppConfig.CONF_APP_UNIQUEID, uniqueID);
		}
		return uniqueID;
	}

	/**
	 * 是否发出提示音
	 * 
	 * @return
	 */
	public boolean isVoice(String userid) {
		String perf_voice = getProperty(AppConfig.CONF_VOICE, userid);
		// 默认是开启提示声音
		if (StringUtils.isEmpty(perf_voice))
			return true;
		else
			return StringUtils.toBool(perf_voice);
	}

	/**
	 * 设置是否发出提示音
	 * 
	 * @param b
	 */
	public void setConfigVoice(boolean b) {
		// setProperty(AppConfig.CONF_VOICE, String.valueOf(b));
	}

	/**
	 * 是否启动检查更新
	 * 
	 * @return
	 */
	public boolean isCheckUp(String userid) {
		String perf_checkup = getProperty(AppConfig.CONF_CHECKUP, userid);
		// 默认是开启
		if (StringUtils.isEmpty(perf_checkup))
			return true;
		else
			return StringUtils.toBool(perf_checkup);
	}

	/**
	 * 设置启动检查更新
	 * 
	 * @param b
	 */
	public void setConfigCheckUp(boolean b) {
		// setProperty(AppConfig.CONF_CHECKUP, String.valueOf(b));
	}

	/**
	 * 是否左右滑动
	 * 
	 * @return
	 */
	public boolean isScroll(String userid) {
		String perf_scroll = getProperty(AppConfig.CONF_SCROLL, userid);
		// 默认是关闭左右滑动
		if (StringUtils.isEmpty(perf_scroll))
			return false;
		else
			return StringUtils.toBool(perf_scroll);
	}

	/**
	 * 设置是否左右滑动
	 * 
	 * @param b
	 */
	public void setConfigScroll(boolean b) {
		// setProperty(AppConfig.CONF_SCROLL, String.valueOf(b));
	}

	/**
	 * 是否Https登录
	 * 
	 * @return
	 */
	public boolean isHttpsLogin(String userid) {
		String perf_httpslogin = getProperty(AppConfig.CONF_HTTPS_LOGIN, userid);
		// 默认是http
		if (StringUtils.isEmpty(perf_httpslogin))
			return false;
		else
			return StringUtils.toBool(perf_httpslogin);
	}

	/**
	 * 设置是是否Https登录
	 * 
	 * @param b
	 */
	public void setConfigHttpsLogin(boolean b) {
		// setProperty(AppConfig.CONF_HTTPS_LOGIN, String.valueOf(b));
	}

	/**
	 * 清除保存的缓存
	 */
	public void cleanCookie() {
		removeProperty(AppConfig.CONF_COOKIE);
	}

	/**
	 * 判断缓存数据是否可读
	 * 
	 * @param cachefile
	 * @return
	 */
	private boolean isReadDataCache(String cachefile) {
		// return readObject(cachefile) != null;
		return true;
	}

	/**
	 * 判断缓存是否存在
	 * 
	 * @param cachefile
	 * @return
	 */
	private boolean isExistDataCache(String cachefile) {
		boolean exist = false;
		File data = getFileStreamPath(cachefile);
		if (data.exists())
			exist = true;
		return exist;
	}

	/**
	 * 判断缓存是否失效
	 * 
	 * @param cachefile
	 * @return
	 */
	public boolean isCacheDataFailure(String cachefile) {
		boolean failure = false;
		File data = getFileStreamPath(cachefile);
		if (data.exists()
				&& (System.currentTimeMillis() - data.lastModified()) > CACHE_TIME)
			failure = true;
		else if (!data.exists())
			failure = true;
		return failure;
	}

	/**
	 * 清除app缓存
	 */
	public void clearAppCache() {
		// 清除webview缓存
		File file = CacheManager.getCacheFileBaseDir();
		if (file != null && file.exists() && file.isDirectory()) {
			for (File item : file.listFiles()) {
				item.delete();
			}
			file.delete();
		}

		// 清除数据缓存
		clearCacheFolder(getFilesDir(), System.currentTimeMillis());
		clearCacheFolder(getCacheDir(), System.currentTimeMillis());
		// 2.2版本才有将应用缓存转移到sd卡的功能
		if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
			clearCacheFolder(MethodsCompat.getExternalCacheDir(this),
					System.currentTimeMillis());
		}
		// 清除编辑器保存的临时内容
		// Properties props = getProperties();
		// for (Object key : props.keySet()) {
		// String _key = key.toString();
		// if (_key.startsWith("temp"))
		// removeProperty(_key);
		// }
	}

	/**
	 * 清除缓存目录
	 * 
	 * @param dir
	 *            目录
	 * @param numDays
	 *            当前系统时间
	 * @return
	 */
	private int clearCacheFolder(File dir, long curTime) {
		int deletedFiles = 0;
		if (dir != null && dir.isDirectory()) {
			try {
				for (File child : dir.listFiles()) {
					if (child.isDirectory()) {
						deletedFiles += clearCacheFolder(child, curTime);
					}
					if (child.lastModified() < curTime) {
						if (child.delete()) {
							deletedFiles++;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return deletedFiles;
	}

	/**
	 * 将对象保存到内存缓存中
	 * 
	 * @param key
	 * @param value
	 */
	public void setMemCache(String key, Object value) {
		memCacheRegion.put(key, value);
	}

	/**
	 * 从内存缓存中获取对象
	 * 
	 * @param key
	 * @return
	 */
	public Object getMemCache(String key) {
		return memCacheRegion.get(key);
	}

	/**
	 * 保存磁盘缓存
	 * 
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public void setDiskCache(String key, String value) throws IOException {
		FileOutputStream fos = null;
		try {
			fos = openFileOutput("cache_" + key + ".data", Context.MODE_PRIVATE);
			fos.write(value.getBytes());
			fos.flush();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 获取磁盘缓存数据
	 * 
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public String getDiskCache(String key) throws IOException {
		FileInputStream fis = null;
		try {
			fis = openFileInput("cache_" + key + ".data");
			byte[] datas = new byte[fis.available()];
			fis.read(datas);
			return new String(datas);
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 保存对象
	 * 
	 * @param ser
	 * @param file
	 * @throws IOException
	 */
	public boolean saveObject(Serializable ser, String file) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = openFileOutput(file, MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(ser);
			oos.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				oos.close();
			} catch (Exception e) {
			}
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 读取对象
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Serializable readObject(String file) {
		if (!isExistDataCache(file))
			return null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = openFileInput(file);
			ois = new ObjectInputStream(fis);
			Serializable s = (Serializable) ois.readObject();
			return s;
		} catch (FileNotFoundException e) {
			System.out.println("AppContext.readObject()" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			// 反序列化失败 - 删除缓存文件
			if (e instanceof InvalidClassException) {
				File data = getFileStreamPath(file);
				data.delete();
			}
		} finally {
			try {
				ois.close();
			} catch (Exception e) {
				System.out.println("AppContext.readObject()" + e.getMessage());
			}
			try {
				fis.close();
			} catch (Exception e) {
				System.out.println("AppContext.readObject()" + e.getMessage());
			}
		}
		return null;
	}

	public boolean containsProperty(String userid, String key) {
		Properties props = getProperties(userid);
		return props.containsKey(key);
	}

	public void setProperties(Properties ps, String filename) {
		AppConfig.getAppConfig(this).set(ps, filename);
	}

	public Properties getProperties(String userid) {
		return AppConfig.getAppConfig(this).get(userid);
	}

	public void setProperty(String key, String value, String filename) {
		AppConfig.getAppConfig(this).set(key, value, filename);
	}

	public String getProperty(String userid, String key) {
		return AppConfig.getAppConfig(this).get(userid, key);
	}

	public void removeProperty(String filename, String... key) {
		AppConfig.getAppConfig(this).remove(filename, key);
	}

	/**
	 * 判断当前版本是否兼容目标版本的方法
	 * 
	 * @param VersionCode
	 * @return
	 */
	public static boolean isMethodsCompat(int VersionCode) {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		return currentVersion >= VersionCode;
	}

	/**
	 * 用户是否登录
	 * 
	 * @return
	 */
	public boolean isLogin() {
		return login;
	}

	/**
	 * 用户注销
	 */
	public void Logout() {
		// ApiClient.cleanCookie();
		this.cleanCookie();
		this.login = false;
		this.loginUid = 0;
	}

	/**
	 * 判断cache目录是否存在
	 */
	public boolean cacheExits() {

		File cache = new File(Environment.getExternalStorageDirectory(),
				"Android/data/com.sartino.huayi/");

		if (cache.isDirectory()) {
			return true;
		}
		return false;
	}

	/**
	 * 产生cache目录
	 */
	public void creareCache() {
		if (!cacheExits()) {
			File cache = new File(Environment.getExternalStorageDirectory(),
					"Android/data/com.sartino.huayi/");
			cache.mkdirs();
		}
	}

	/**
	 * 产生缩略图目录
	 */

	public File createThumbs(Context ctx) {
		File thumbs = null;
		if (!thumbsExits(ctx)) {
			File dirConf = ctx.getDir("files", Context.MODE_PRIVATE);
			thumbs = new File(dirConf, "thumbs");

			thumbs.mkdirs();
			System.out.println("目录" + thumbs.getPath().toString());
		}

		return thumbs;
	}

	/**
	 * 判断缩略图是否存在
	 */

	public boolean thumbsExits(Context ctx) {
		System.out.println(ctx);
		File thumbsDir = ctx.getDir("files", Context.MODE_PRIVATE);
		if (thumbsDir.isDirectory()) {
			return true;
		}

		return false;
	}

	/**
	 * 获取缩略图目录
	 * 
	 */

	public File getThumbsDir(Context ctx) {
		if (thumbsExits(ctx)) {
			File thumbsdir = ctx.getDir("files", Context.MODE_PRIVATE);
			System.out.println("目录" + thumbsdir.getPath().toString());
			return thumbsdir;
		} else {
			return createThumbs(ctx);
		}

	}

	/**
	 * 加载简略信息
	 */

	public List<SimpleInfo> getSimpleList(int category, int pageIndex,
			boolean isRefresh) {
		List<SimpleInfo> list = null;
		String key = "postlist_" + category + "_" + pageIndex + "_" + PAGE_SIZE;// 缓存key
		if (isNetworkConnected() && (!isReadDataCache(key) || isRefresh)) {
			// 获取数据

		}

		return null;
	}

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * 用于访问简单信息
	 * 
	 * @param category
	 *            类别
	 * @param pageindex
	 *            页面索引
	 * @param pagesize
	 *            页面大小
	 * @param action
	 *            动作
	 * @return
	 * @throws AppException
	 */
	@SuppressWarnings("unchecked")
	public List<SimpleInfo> getSimpleInfo(int category, int pageindex,
			int action, boolean isReresh) throws AppException {
		// 判断是否存在缓存

		SimpleDatas list = new SimpleDatas();
		String key = "simple" + category + "_" + pageindex + "_" + PAGE_SIZE;
		if (isNetworkConnected() && (!isReadDataCache(key) || isReresh)) {// 有网络连接,
																			// 缓存读取失败，且需要刷新

			try {
				list.setDatas(APIClient.getSimpleInfo(this,
						AppContext.FORMAT_XML, AppContext.TYPE_SIMPLE,
						category, pageindex, PAGE_SIZE, action));
				if (list.getDatas().size() != 0) {// /数据保存,只保存第一页
					list.setCacheKey(key);
					saveObject(list, key);
				} else {
					System.out.println(0);
				}
			} catch (AppException e) {// 抛出异常
				list = (SimpleDatas) readObject(key);
				if (list == null) {
					throw e;
				}

			}

		} else {// 缓存数据读取

			SimpleDatas data = (SimpleDatas) readObject(key);
			try {
				list.setDatas(data.getDatas());
			} catch (Exception e) {
				System.out.println("网络连接出错");
			}

			if (list.getDatas() == null) {
				list.setDatas(new ArrayList<SimpleInfo>());
			}

		}

		return list.getDatas();

	}

	public DetailInfo getDetailInfo(int id) throws AppException {
		DetailInfo info = null;
		String key = "detail" + "_" + id;
		// System.out.println(isReadDataCache(key));// assert false;
		if (isNetworkConnected()) {// 有网络连接,读取缓存失败(就是没有缓存文件,需要刷新)

			try {
				info = APIClient.getDetailInfo(this, FORMAT_XML, id,
						TYPE_DETAIL);
				if (info != null) {// /数据保存
					info.setCacheKey(key);
					saveObject(info, key);
				}
			} catch (AppException e) {
				info = (DetailInfo) readObject(key);
				if (info == null) {
					throw e;
				}
			}

		} else {// 缓存数据读取
			try {
				info = (DetailInfo) readObject(key);
			} catch (Exception e) {
				System.out.println("请检查网络连接");
			}

		}

		return info;
	}

	/**
	 * 处理用户登录，主要是将数据进行加密
	 * 
	 * 
	 * 
	 * @param bundle
	 * @return
	 * @throws AppException
	 * @throws NotFoundException
	 */
	public User Login(Bundle bundle) throws AppException {
		// 测试三,网络无连接
		if (!isNetworkConnected()) {// 如果没有网络连接,那么直接不用往下调用
			throw AppException.network(new Exception(getResources().getString(
					R.string.login_fail_no_network)));
		}
		User user;
		if (AppContext.DEBUG) {
			System.out.println("AppContext.Login()密码检查"
					+ bundle.getString("pwd"));
		}
		user = APIClient.userLogin(bundle.getString("username"),
				CyptoUtils.encode("sartinoapp", bundle.getString("pwd")));
		user.setLastLogin(DateUtil.getCurrentDate());
		return user;
		// // 测试一，登录成功

		// Result rs = new Result();
		// if (bundle.getString("pwd").equals("admin")) {
		// user.setId(Integer.valueOf(bundle.getString("username")));
		// rs.setErrorCode(1);
		// user.setValidate(rs);
		// } else {
		// // 测试二,登录失败
		// user.setValidate(rs);
		// rs.setErrorCode(2);
		// rs.setErrorMessage("与服务器建立连接失败");
		// }

		// return APIClient.login(this, bundle);
	}

	/**
	 * 保存用户信息 这里根据用户的id建立配置文件
	 * 
	 * 注意，如果当前未登录,那么直接使用默认的即可
	 */
	public void SaveUserInfo(final User user) {
		this.login = true;
		this.loginUid = user.getId();
		System.out.println("AppContext.SaveUserInfo()" + this);
		setProperties(new Properties() {
			{

				setProperty("id", String.valueOf(user.getId()));
				setProperty("user", String.valueOf(user.getmUserName()));
				setProperty(
						"pwd",
						String.valueOf(CyptoUtils.encode("sartinoapp",
								user.getPwd())));
				setProperty("lastlogin", AppContext.this.user.getLastLogin());
			}
		}, user.getmUserName());
	}

	public void clearUserInfo(User user) {
		this.login = false;
		this.loginUid = 0;

		removeProperty(String.valueOf(user.getmUserName()), "id", "user",
				"pwd", "lastlogin");
	}

	/**
	 * 获取用户信息，主要对用户信息解密
	 * 
	 * @return
	 */
	public User getUserInfo(String userid) {

		User user = new User();
		AppConfig appconfig = AppConfig.getAppConfig(this);
		user.setUserName(appconfig.get(userid, "username"));
		user.setPwd(CyptoUtils.decode("sartinoapp",
				appconfig.get(userid, "pwd")));
		user.setLastLogin(appconfig.get(userid, "lastlogindate"));
		appconfig = null;

		return user;

	}

	public String[] getSuggetionsUser() {

		AppConfig appconfig = AppConfig.getAppConfig(this);

		String tmp[] = appconfig.getAllUserWithString();
		if (tmp == null || tmp.length == 0) {
			return null;
		}
		String data[] = new String[tmp.length - 1];

		int j = 0;
		for (int i = 0; i < tmp.length; i++) {
			try {
				if (!tmp[i].equals("config")) {
					data[j] = tmp[i];
					j++;
				}
			} catch (Exception e) {

				break;

			}

		}
		tmp = null;
		appconfig = null;
		System.out.println("AppContext.getSuggetionsUser()" + data);
		return data;

	}

	/**
	 * 用户注册
	 * 
	 * @param bundle
	 * @return
	 * @throws AppException
	 */
	public User regiter(Bundle bundle) throws AppException {
		User user = new User();
		if (!isNetworkConnected()) {// 网络连接的情况下进行注册
			throw AppException.network(new Exception(
					getString(R.string.register_no_network)));
		}

		user = APIClient.userRegister(bundle.getString("username"),
				CyptoUtils.encode("sartinoapp", bundle.getString("pwd")));
		user.setLastLogin(DateUtil.getCurrentDate());

		return user;
	}
}
