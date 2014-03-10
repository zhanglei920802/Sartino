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
 * ȫ��Ӧ�ó����ࣺ���ڱ���͵���ȫ��Ӧ�����ü�������������
 * 
 * @author Administrator
 * 
 */
public class AppContext extends Application {
	public static final int NETYPE_WIFI = 0x01;
	public static final int NETTYPE_CMWAP = 0x02;
	public static final int NETTYPE_CMNET = 0x02;

	public static final int PAGE_SIZE = 10;
	public static final int CACHE_TIME = 60 * 6000;// ����ʱ��

	public static final String TYPE_SIMPLE = "simpleinfo";
	public static final String TYPE_DETAIL = "detailinfo";

	public static final String FORMAT_JSON = "json";
	public static final String FORMAT_XML = "xml";

	public static final int CATEGORY_BRAND_OVERVIEW = 0x01;// ����
	public static final int CATEGORY_BRAND_CULTURE = 0x02;// �Ļ�
	public static final int CATEGORY_CONCEPT_CONCEPT = 0x03;// ����
	public static final int CATEGORY_DISPLAY_DISPLAY = 0x04;// չʾ
	public static final int CATEGORY_DESIGN_SHOWCASE = 0x05;// ����
	public static final int CATEGORY_DESIGN_MEETING = 0x06;// ����
	public static final int CATEGORY_DESIGN_WEDDING = 0x07;// ����
	public static final int CATEGORY_DESIGN_DISPLAY = 0x08;// ����
	public static final int CATEGORY_DESIGN_LANDSCAPE = 0x09;// ����
	public static final int CATEGORY_DESIGN_INNER = 0x0A;// ����
	public static final int CATEGORY_DESIGN_DESIGNER = 0x0B;// ���ʦ11
	public static final int CATEGORY_HUALI_WESTERN = 0x0C;// ��ʽ���
	public static final int CATEGORY_HUALI_EAST = 0x0D;// ��������
	public static final int CATEGORY_HUAYI_WESTERN = 0x0E;// ��ʽ���
	public static final int CATEGORY_HUAYI_EAST = 0x0F;// ��������
	public static final int CATEGORY_JOIN_ADVANTAGE = 0x10;// ����
	public static final int CATEGORY_JOIN_PROCESS = 0x11;// ��������
	public static final int CATEGORY_JOIN_RETURN = 0x12;// �ر�
	public static final int CATEGORY_JOIN_MARKETING = 0x13;// Ӫ������
	public static final int CATEGORY_JOIN_POLICY = 0x14;// ��������
	public static final int CATEGORY_DOWNLOAD_DOWNLOAD = 0x15;// ����

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

		// ��ʼ��cache

		// ע��App�쳣����������
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
	 * ��ȡ��ǰ��������
	 * 
	 * @return 0:û������ 1:wifi���� 2:wap����
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
	 * ��ȡApp��װ����Ϣ
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
	 * ��ȡAppΨһ��ʶ
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
	 * �Ƿ񷢳���ʾ��
	 * 
	 * @return
	 */
	public boolean isVoice(String userid) {
		String perf_voice = getProperty(AppConfig.CONF_VOICE, userid);
		// Ĭ���ǿ�����ʾ����
		if (StringUtils.isEmpty(perf_voice))
			return true;
		else
			return StringUtils.toBool(perf_voice);
	}

	/**
	 * �����Ƿ񷢳���ʾ��
	 * 
	 * @param b
	 */
	public void setConfigVoice(boolean b) {
		// setProperty(AppConfig.CONF_VOICE, String.valueOf(b));
	}

	/**
	 * �Ƿ�����������
	 * 
	 * @return
	 */
	public boolean isCheckUp(String userid) {
		String perf_checkup = getProperty(AppConfig.CONF_CHECKUP, userid);
		// Ĭ���ǿ���
		if (StringUtils.isEmpty(perf_checkup))
			return true;
		else
			return StringUtils.toBool(perf_checkup);
	}

	/**
	 * ��������������
	 * 
	 * @param b
	 */
	public void setConfigCheckUp(boolean b) {
		// setProperty(AppConfig.CONF_CHECKUP, String.valueOf(b));
	}

	/**
	 * �Ƿ����һ���
	 * 
	 * @return
	 */
	public boolean isScroll(String userid) {
		String perf_scroll = getProperty(AppConfig.CONF_SCROLL, userid);
		// Ĭ���ǹر����һ���
		if (StringUtils.isEmpty(perf_scroll))
			return false;
		else
			return StringUtils.toBool(perf_scroll);
	}

	/**
	 * �����Ƿ����һ���
	 * 
	 * @param b
	 */
	public void setConfigScroll(boolean b) {
		// setProperty(AppConfig.CONF_SCROLL, String.valueOf(b));
	}

	/**
	 * �Ƿ�Https��¼
	 * 
	 * @return
	 */
	public boolean isHttpsLogin(String userid) {
		String perf_httpslogin = getProperty(AppConfig.CONF_HTTPS_LOGIN, userid);
		// Ĭ����http
		if (StringUtils.isEmpty(perf_httpslogin))
			return false;
		else
			return StringUtils.toBool(perf_httpslogin);
	}

	/**
	 * �������Ƿ�Https��¼
	 * 
	 * @param b
	 */
	public void setConfigHttpsLogin(boolean b) {
		// setProperty(AppConfig.CONF_HTTPS_LOGIN, String.valueOf(b));
	}

	/**
	 * �������Ļ���
	 */
	public void cleanCookie() {
		removeProperty(AppConfig.CONF_COOKIE);
	}

	/**
	 * �жϻ��������Ƿ�ɶ�
	 * 
	 * @param cachefile
	 * @return
	 */
	private boolean isReadDataCache(String cachefile) {
		// return readObject(cachefile) != null;
		return true;
	}

	/**
	 * �жϻ����Ƿ����
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
	 * �жϻ����Ƿ�ʧЧ
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
	 * ���app����
	 */
	public void clearAppCache() {
		// ���webview����
		File file = CacheManager.getCacheFileBaseDir();
		if (file != null && file.exists() && file.isDirectory()) {
			for (File item : file.listFiles()) {
				item.delete();
			}
			file.delete();
		}

		// ������ݻ���
		clearCacheFolder(getFilesDir(), System.currentTimeMillis());
		clearCacheFolder(getCacheDir(), System.currentTimeMillis());
		// 2.2�汾���н�Ӧ�û���ת�Ƶ�sd���Ĺ���
		if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
			clearCacheFolder(MethodsCompat.getExternalCacheDir(this),
					System.currentTimeMillis());
		}
		// ����༭���������ʱ����
		// Properties props = getProperties();
		// for (Object key : props.keySet()) {
		// String _key = key.toString();
		// if (_key.startsWith("temp"))
		// removeProperty(_key);
		// }
	}

	/**
	 * �������Ŀ¼
	 * 
	 * @param dir
	 *            Ŀ¼
	 * @param numDays
	 *            ��ǰϵͳʱ��
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
	 * �����󱣴浽�ڴ滺����
	 * 
	 * @param key
	 * @param value
	 */
	public void setMemCache(String key, Object value) {
		memCacheRegion.put(key, value);
	}

	/**
	 * ���ڴ滺���л�ȡ����
	 * 
	 * @param key
	 * @return
	 */
	public Object getMemCache(String key) {
		return memCacheRegion.get(key);
	}

	/**
	 * ������̻���
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
	 * ��ȡ���̻�������
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
	 * �������
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
	 * ��ȡ����
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
			// �����л�ʧ�� - ɾ�������ļ�
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
	 * �жϵ�ǰ�汾�Ƿ����Ŀ��汾�ķ���
	 * 
	 * @param VersionCode
	 * @return
	 */
	public static boolean isMethodsCompat(int VersionCode) {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		return currentVersion >= VersionCode;
	}

	/**
	 * �û��Ƿ��¼
	 * 
	 * @return
	 */
	public boolean isLogin() {
		return login;
	}

	/**
	 * �û�ע��
	 */
	public void Logout() {
		// ApiClient.cleanCookie();
		this.cleanCookie();
		this.login = false;
		this.loginUid = 0;
	}

	/**
	 * �ж�cacheĿ¼�Ƿ����
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
	 * ����cacheĿ¼
	 */
	public void creareCache() {
		if (!cacheExits()) {
			File cache = new File(Environment.getExternalStorageDirectory(),
					"Android/data/com.sartino.huayi/");
			cache.mkdirs();
		}
	}

	/**
	 * ��������ͼĿ¼
	 */

	public File createThumbs(Context ctx) {
		File thumbs = null;
		if (!thumbsExits(ctx)) {
			File dirConf = ctx.getDir("files", Context.MODE_PRIVATE);
			thumbs = new File(dirConf, "thumbs");

			thumbs.mkdirs();
			System.out.println("Ŀ¼" + thumbs.getPath().toString());
		}

		return thumbs;
	}

	/**
	 * �ж�����ͼ�Ƿ����
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
	 * ��ȡ����ͼĿ¼
	 * 
	 */

	public File getThumbsDir(Context ctx) {
		if (thumbsExits(ctx)) {
			File thumbsdir = ctx.getDir("files", Context.MODE_PRIVATE);
			System.out.println("Ŀ¼" + thumbsdir.getPath().toString());
			return thumbsdir;
		} else {
			return createThumbs(ctx);
		}

	}

	/**
	 * ���ؼ�����Ϣ
	 */

	public List<SimpleInfo> getSimpleList(int category, int pageIndex,
			boolean isRefresh) {
		List<SimpleInfo> list = null;
		String key = "postlist_" + category + "_" + pageIndex + "_" + PAGE_SIZE;// ����key
		if (isNetworkConnected() && (!isReadDataCache(key) || isRefresh)) {
			// ��ȡ����

		}

		return null;
	}

	/**
	 * ��������Ƿ����
	 * 
	 * @return
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * ���ڷ��ʼ���Ϣ
	 * 
	 * @param category
	 *            ���
	 * @param pageindex
	 *            ҳ������
	 * @param pagesize
	 *            ҳ���С
	 * @param action
	 *            ����
	 * @return
	 * @throws AppException
	 */
	@SuppressWarnings("unchecked")
	public List<SimpleInfo> getSimpleInfo(int category, int pageindex,
			int action, boolean isReresh) throws AppException {
		// �ж��Ƿ���ڻ���

		SimpleDatas list = new SimpleDatas();
		String key = "simple" + category + "_" + pageindex + "_" + PAGE_SIZE;
		if (isNetworkConnected() && (!isReadDataCache(key) || isReresh)) {// ����������,
																			// �����ȡʧ�ܣ�����Ҫˢ��

			try {
				list.setDatas(APIClient.getSimpleInfo(this,
						AppContext.FORMAT_XML, AppContext.TYPE_SIMPLE,
						category, pageindex, PAGE_SIZE, action));
				if (list.getDatas().size() != 0) {// /���ݱ���,ֻ�����һҳ
					list.setCacheKey(key);
					saveObject(list, key);
				} else {
					System.out.println(0);
				}
			} catch (AppException e) {// �׳��쳣
				list = (SimpleDatas) readObject(key);
				if (list == null) {
					throw e;
				}

			}

		} else {// �������ݶ�ȡ

			SimpleDatas data = (SimpleDatas) readObject(key);
			try {
				list.setDatas(data.getDatas());
			} catch (Exception e) {
				System.out.println("�������ӳ���");
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
		if (isNetworkConnected()) {// ����������,��ȡ����ʧ��(����û�л����ļ�,��Ҫˢ��)

			try {
				info = APIClient.getDetailInfo(this, FORMAT_XML, id,
						TYPE_DETAIL);
				if (info != null) {// /���ݱ���
					info.setCacheKey(key);
					saveObject(info, key);
				}
			} catch (AppException e) {
				info = (DetailInfo) readObject(key);
				if (info == null) {
					throw e;
				}
			}

		} else {// �������ݶ�ȡ
			try {
				info = (DetailInfo) readObject(key);
			} catch (Exception e) {
				System.out.println("������������");
			}

		}

		return info;
	}

	/**
	 * �����û���¼����Ҫ�ǽ����ݽ��м���
	 * 
	 * 
	 * 
	 * @param bundle
	 * @return
	 * @throws AppException
	 * @throws NotFoundException
	 */
	public User Login(Bundle bundle) throws AppException {
		// ������,����������
		if (!isNetworkConnected()) {// ���û����������,��ôֱ�Ӳ������µ���
			throw AppException.network(new Exception(getResources().getString(
					R.string.login_fail_no_network)));
		}
		User user;
		if (AppContext.DEBUG) {
			System.out.println("AppContext.Login()������"
					+ bundle.getString("pwd"));
		}
		user = APIClient.userLogin(bundle.getString("username"),
				CyptoUtils.encode("sartinoapp", bundle.getString("pwd")));
		user.setLastLogin(DateUtil.getCurrentDate());
		return user;
		// // ����һ����¼�ɹ�

		// Result rs = new Result();
		// if (bundle.getString("pwd").equals("admin")) {
		// user.setId(Integer.valueOf(bundle.getString("username")));
		// rs.setErrorCode(1);
		// user.setValidate(rs);
		// } else {
		// // ���Զ�,��¼ʧ��
		// user.setValidate(rs);
		// rs.setErrorCode(2);
		// rs.setErrorMessage("���������������ʧ��");
		// }

		// return APIClient.login(this, bundle);
	}

	/**
	 * �����û���Ϣ ��������û���id���������ļ�
	 * 
	 * ע�⣬�����ǰδ��¼,��ôֱ��ʹ��Ĭ�ϵļ���
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
	 * ��ȡ�û���Ϣ����Ҫ���û���Ϣ����
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
	 * �û�ע��
	 * 
	 * @param bundle
	 * @return
	 * @throws AppException
	 */
	public User regiter(Bundle bundle) throws AppException {
		User user = new User();
		if (!isNetworkConnected()) {// �������ӵ�����½���ע��
			throw AppException.network(new Exception(
					getString(R.string.register_no_network)));
		}

		user = APIClient.userRegister(bundle.getString("username"),
				CyptoUtils.encode("sartinoapp", bundle.getString("pwd")));
		user.setLastLogin(DateUtil.getCurrentDate());

		return user;
	}
}
