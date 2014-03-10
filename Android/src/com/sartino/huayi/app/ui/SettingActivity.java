package com.sartino.huayi.app.ui;

import java.io.File;
import com.sartino.huayi.app.AppContext;
import com.sartino.huayi.app.AppManager;

import com.sartino.huayi.app.R;
import com.sartino.huayi.app.common.FileUtils;
import com.sartino.huayi.app.common.MethodsCompat;
import com.sartino.huayi.app.common.UIHelper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SettingActivity extends PreferenceActivity {
	private static final String TAG = "SettingActivity";

	private SharedPreferences mSharedPreference = null;
	private CheckBoxPreference voice = null;
	private AppContext mAC = null;
	private Preference mP_AboutUs = null;
	private Preference mP_ChkUpdate = null;
	private Preference mP_Feedback = null;
	private Preference mP_ClearCache = null;
	private Preference mP_LoginOrLogout = null;
	private Preference mP_MyProfile = null;
	private CheckBoxPreference mP_autoUpdate = null;
	private Handler mH_ChkUpdate = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			UIHelper.ToastMessage(SettingActivity.this, R.string.lastupdate);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		android.os.Debug.startMethodTracing(TAG);
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		addPreferencesFromResource(R.xml.prefence);

		initView();
		mAC = (AppContext) getApplication();
		this.voice();
		this.aboutUs();
		this.chkUpdate();
		this.feedBack();
		this.clearCache();
		this.initAutoUpate();
		this.initLoginOrLogout();
		this.initMyProfile();
	}

	/**
	 * 初始化用户空间界面
	 */
	private void initMyProfile() {
		mP_MyProfile = findPreference("myprofile");

		mP_MyProfile
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {
						if (mAC.isLogin()) {// 已经登录
							// SettingActivity.this.startActivity(new Intent(
							// SettingActivity.this,
							// MyProfileActivity.class));
							// finish();
							// User user = new User();
							// user.setId(mAC.getLoginUid());
							//
							// String lastlogin = mAC.getProperty(
							// String.valueOf(user.getId()), "lastlogin");
							// SimpleDateFormat sm = new SimpleDateFormat(
							// "yyyy-MM-dd HH:mm:ss");
							// Date date = new Date(Long.valueOf(lastlogin));
							// user.setLastLogin(sm.format(date));
							UIHelper.showMyProfileActivity(
									SettingActivity.this, mAC.getUser());
						} else {// 登录
							// SettingActivity.this.startActivity(new Intent(
							// SettingActivity.this, LoginActivity.class));
							// finish();
							UIHelper.showLoginActivity(SettingActivity.this,
									LoginActivity.TAG_PROFILE);
						}

						return true;
					}
				});
	}

	/**
	 * 初始化用户登录
	 */
	private void initLoginOrLogout() {
		mP_LoginOrLogout = findPreference("loginOrloginout");

		System.out.println("SettingActivity.initLoginOrLogout()"
				+ mAC.isLogin());
		if (mAC.isLogin()) {
			mP_LoginOrLogout.setTitle(getResources().getString(
					R.string.preference_login_out));
		} else {
			mP_LoginOrLogout.setTitle(getResources().getString(
					R.string.preference_login_in));
		}

		mP_LoginOrLogout
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {

						if (mAC.isLogin()) {// 已经登录，点击注销
							UIHelper.loginOrLogout(SettingActivity.this);
							mAC.setUser(null);
							mP_LoginOrLogout.setTitle(getResources().getString(
									R.string.preference_login_in));
						} else {
							UIHelper.showLoginActivity(SettingActivity.this,
									LoginActivity.TAG_LOGIN);
						}

						return true;
					}
				});

	}

	private void initAutoUpate() {
		mP_autoUpdate = (CheckBoxPreference) findPreference("autoupdate");
		mP_autoUpdate
				.setChecked(mAC.isCheckUp(String.valueOf(mAC.getLoginUid())));
		if (mAC.isCheckUp(String.valueOf(mAC.getLoginUid()))) {
			mP_autoUpdate.setSummary(getResources().getString(
					R.string.autoUpdate));
		} else {
			mP_autoUpdate.setSummary(getResources().getString(
					R.string.notAutoUpdate));
		}

		mP_autoUpdate
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {
						if (mP_autoUpdate.isChecked()) {
							mP_autoUpdate.setSummary(getResources().getString(
									R.string.autoUpdate));
						} else {
							mP_autoUpdate.setSummary(getResources().getString(
									R.string.notAutoUpdate));
						}
						return true;
					}
				});

	}

	private void clearCache() {
		long fileSize = 0;
		String cacheSize = "0KB";
		File filesDir = getFilesDir();
		File cacheDir = getCacheDir();

		fileSize += FileUtils.getDirSize(filesDir);
		fileSize += FileUtils.getDirSize(cacheDir);
		if (AppContext.isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
			File externalCacheDir = MethodsCompat.getExternalCacheDir(this);
			fileSize += FileUtils.getDirSize(externalCacheDir);
		}
		if (fileSize > 0)
			cacheSize = FileUtils.formatFileSize(fileSize);

		mP_ClearCache = findPreference("clearcache");
		mP_ClearCache.setSummary(cacheSize);
		mP_ClearCache
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {

						UIHelper.clearAppCache(SettingActivity.this);
						mP_ClearCache.setSummary("0 KB");
						return true;
					}
				});
	}

	/**
	 * 意见反馈
	 */
	private void feedBack() {
		mP_Feedback = findPreference("feedback");
		mP_Feedback
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {

						UIHelper.showFeedBack(SettingActivity.this);
						return true;
					}
				});
	}

	/**
	 * 检查更新
	 */
	private void chkUpdate() {
		mP_ChkUpdate = findPreference("checkupdate");
		mP_ChkUpdate
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {

						UIHelper.ToastMessage(SettingActivity.this,
								R.string.chkupdateing);
						final long startTime = System.currentTimeMillis();
						new Thread(new Runnable() {

							@Override
							public void run() {
								// 检测更新
								long endTime = startTime;
								while (endTime - startTime < 2000) {
									endTime = System.currentTimeMillis();
								}
								mH_ChkUpdate.sendEmptyMessage(0);
							}
						}).start();

						return true;
					}
				});
	}

	/**
	 * 关于
	 */
	private void aboutUs() {
		mP_AboutUs = findPreference("about");
		mP_AboutUs
				.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

					@Override
					public boolean onPreferenceClick(Preference preference) {
						UIHelper.showAboutActivity(SettingActivity.this);
						return true;
					}
				});
	}

	@Override
	protected void onDestroy() {
		android.os.Debug.stopMethodTracing();
		super.onDestroy();
		AppManager.getAppManager().finishActivity(this);
	}

	public void initView() {
		mSharedPreference = PreferenceManager.getDefaultSharedPreferences(this);

		ListView localListView = getListView();// 获取到listView
		localListView.setBackgroundColor(0);// 设置属性
		localListView.setCacheColorHint(0);
		((ViewGroup) localListView.getParent()).removeView(localListView);// 从顶层容器中将其删除

		ViewGroup localViewGroup = (ViewGroup) getLayoutInflater().inflate(
				R.layout.setting, null);
		((ViewGroup) localViewGroup.findViewById(R.id.setting_content))
				.addView(localListView, -1, -1);// match parent
		setContentView(localViewGroup);

	}

	public void back(View v) {
		finish();
	}

	/**
	 * 声音
	 */
	public void voice() {
		// 提示声音
		voice = (CheckBoxPreference) findPreference("voice");
		voice.setChecked(mAC.isVoice(String.valueOf(mAC.getLoginUid())));
		if (mAC.isVoice(String.valueOf(mAC.getLoginUid()))) {
			voice.setSummary("已开启提示声音");
		} else {
			voice.setSummary("已关闭提示声音");
		}
		voice.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				mAC.setConfigVoice(voice.isChecked());
				if (voice.isChecked()) {
					voice.setSummary("已开启提示声音");
				} else {
					voice.setSummary("已关闭提示声音");
				}
				return true;
			}
		});
	}

	public void commitFeedBack(View v) {
		// System.out.println("S.commitFeedBack()" + "被点击了");
	}
}
