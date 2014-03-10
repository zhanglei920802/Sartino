package com.sartino.huayi.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 应用程序配置类：用于保存用户相关信息及设置
 * 
 * @author Administrator
 * 
 */
public class AppConfig {
	public static final String TAG = "AppConfig";

	private final static String APP_CONFIG = "config";
	private AppContext mContext = null;
	private static AppConfig mAppConfig = null;

	public final static String CONF_APP_UNIQUEID = "APP_UNIQUEID";
	public final static String CONF_VOICE = "perf_voice";
	public final static String CONF_CHECKUP = "perf_checkup";
	public final static String CONF_LOAD_IMAGE = "perf_loadimage";
	public final static String CONF_SCROLL = "perf_scroll";
	public final static String CONF_HTTPS_LOGIN = "perf_httpslogin";
	public final static String CONF_COOKIE = "cookie";

	public static AppConfig getAppConfig(AppContext context) {
		if (mAppConfig == null) {
			mAppConfig = new AppConfig();

		}
		mAppConfig.mContext = context;
		return mAppConfig;
	}

	public static SharedPreferences getSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	public String get(String userid, String key) {
		Properties props = get(userid);
		return (props != null) ? props.getProperty(key) : null;
	}

	public Properties get(String userid) {
		FileInputStream fis = null;
		Properties props = new Properties();
		try {
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			if (userid == null || userid == "") {
				fis = new FileInputStream(dirConf.getPath() + File.separator
						+ APP_CONFIG);
			}else{
				fis = new FileInputStream(dirConf.getPath() + File.separator
						+ userid);
			}
			

			props.load(fis);
		} catch (Exception e) {
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return props;
	}

	private void setProps(Properties p, String fileName) {
		FileOutputStream fos = null;
		try {
			// 把config建在files目录下
			// fos = activity.openFileOutput(APP_CONFIG, Context.MODE_PRIVATE);

			// 把config建在(自定义)app_config的目录下
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			System.out.println("AppConfig.setProps()" + mContext);
			File conf = null;
			if (fileName != null) {
				conf = new File(dirConf, fileName);
			} else {
				conf = new File(dirConf, APP_CONFIG);
			}

			fos = new FileOutputStream(conf);

			p.store(fos, null);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}

	public void set(Properties ps, String fileName) {
		Properties props = get(fileName);
		props.putAll(ps);
		setProps(props, fileName);
	}

	public void set(String key, String value, String fileName) {
		Properties props = get(fileName);
		props.setProperty(key, value);
		setProps(props, fileName);
	}

	public void remove(String fileName, String... key) {
		Properties props = get(fileName);
		for (String k : key)
			props.remove(k);
		setProps(props, fileName);
	}

	public String[] getAllUserWithString() {

		File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
		if (dirConf.isDirectory()) {
			return dirConf.list();
		}
		return null;
	}
}
