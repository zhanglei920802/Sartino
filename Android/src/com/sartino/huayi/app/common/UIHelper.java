package com.sartino.huayi.app.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sartino.huayi.app.AppContext;
import com.sartino.huayi.app.AppManager;
import com.sartino.huayi.app.R;
import com.sartino.huayi.app.bean.User;
import com.sartino.huayi.app.ui.AboutActivity;
import com.sartino.huayi.app.ui.BaseActivity;
import com.sartino.huayi.app.ui.BrandActivity;
import com.sartino.huayi.app.ui.ConceptActivity;
import com.sartino.huayi.app.ui.DesignActivity;
import com.sartino.huayi.app.ui.DetailActivity;
import com.sartino.huayi.app.ui.DisplayActivity;
import com.sartino.huayi.app.ui.DownloadActivity;
import com.sartino.huayi.app.ui.FeedBack;
import com.sartino.huayi.app.ui.HualiActivity;
import com.sartino.huayi.app.ui.HuayiActivity;
import com.sartino.huayi.app.ui.JoinActivity;
import com.sartino.huayi.app.ui.LoginActivity;
import com.sartino.huayi.app.ui.MyProfileActivity;
import com.sartino.huayi.app.ui.PhotoViewActivity;
import com.sartino.huayi.app.ui.RegisterActivity;

import com.sartino.huayi.app.ui.MainActivity;
import com.sartino.huayi.app.ui.SettingActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 */
public class UIHelper {
	public final static int LISTVIEW_ACTION_REFRESH = 0x00;
	public final static int LISTVIEW_ACTION_SCROLL = 0x01;

	public final static int LISTVIEW_ACTION_INIT = 0x03;
	public final static int LISTVIEW_ACTION_CHANGE_CATALOG = 0x04;

	public final static int LISTVIEW_DATA_MORE = 0x01;
	public final static int LISTVIEW_DATA_LOADING = 0x02;
	public final static int LISTVIEW_DATA_FULL = 0x03;
	public final static int LISTVIEW_DATA_EMPTY = 0x04;

	public final static int LISTVIEW_DATATYPE_NEWS = 0x01;
	public final static int LISTVIEW_DATATYPE_BLOG = 0x02;
	public final static int LISTVIEW_DATATYPE_POST = 0x03;
	public final static int LISTVIEW_DATATYPE_TWEET = 0x04;
	public final static int LISTVIEW_DATATYPE_ACTIVE = 0x05;
	public final static int LISTVIEW_DATATYPE_MESSAGE = 0x06;
	public final static int LISTVIEW_DATATYPE_COMMENT = 0x07;

	public final static int REQUEST_CODE_FOR_RESULT = 0x01;
	public final static int REQUEST_CODE_FOR_REPLY = 0x02;

	/**
	 * 显示首页
	 * 
	 * @param activity
	 */
	public static void showHome(Activity activity) {
		Intent intent = new Intent(activity, MainActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}

	public static void showActivity(Activity activity, String name) {
		String id = name.trim();
		int tmp = 0;

		if (BaseActivity.BRAND.equals(id)) {
			tmp = 0;
		} else if (BaseActivity.CONCEPT.equals(id)) {
			tmp = 1;
		} else if (BaseActivity.DESIGN.equals(id)) {
			tmp = 2;
		} else if (BaseActivity.HUAYI.equals(id)) {
			tmp = 3;
		} else if (BaseActivity.HUALI.equals(id)) {
			tmp = 4;
		} else if (BaseActivity.DOWNLOAD.equals(id)) {
			tmp = 5;
		} else if (BaseActivity.JOIN.equals(id)) {
			tmp = 6;
		} else if (BaseActivity.DISPLAY.equals(id)) {
			tmp = 7;
		}

		ShowDetailActivity(tmp, activity);

	}

	/**
	 * 按照id启动Activity，以后可以再次优化
	 * 
	 * @param id
	 *            id
	 */
	public static void ShowDetailActivity(int id, Activity activity) {
		Intent intent = null;

		Bundle bundle = new Bundle();
		switch (id) {
		case 0:// brand
			intent = new Intent(activity, BrandActivity.class);
			break;
		case 1:
			intent = new Intent(activity, ConceptActivity.class);
			break;
		case 2:
			intent = new Intent(activity, DesignActivity.class);
			break;
		case 3:
			intent = new Intent(activity, HuayiActivity.class);
			break;
		case 4:
			intent = new Intent(activity, HualiActivity.class);
			break;
		case 5:
			intent = new Intent(activity, DownloadActivity.class);
			break;
		case 6:
			intent = new Intent(activity, JoinActivity.class);
			break;
		case 7:
			intent = new Intent(activity, DisplayActivity.class);
			break;
		}
		bundle.putInt("id", id);
		intent.putExtras(bundle);
		activity.startActivity(intent);
		// activity.finish();
		System.out.println(id);
		intent = null;
		bundle = null;
	}

	/**
	 * 显示设置界面
	 */

	public static void showSettingActivity(Activity activity) {
		Intent intent = new Intent(activity, SettingActivity.class);
		activity.startActivity(intent);

	}

	public static void showAboutActivity(Activity activity) {

		Intent intent = new Intent(activity, AboutActivity.class);
		activity.startActivity(intent);

	}

	/**
	 * 显示用户反馈
	 * 
	 * @param context
	 */
	public static void showFeedBack(Context context) {
		Intent intent = new Intent(context, FeedBack.class);
		context.startActivity(intent);
	}

	/**
	 * 显示登录页面
	 * 
	 * @param activity
	 */
	public static void showLoginActivity(Activity context, int tag) {
		// Intent intent = new Intent(context, LoginDialog.class);
		// if (context instanceof Main)
		// intent.putExtra("LOGINTYPE", LoginDialog.LOGIN_MAIN);
		// else if (context instanceof Setting)
		// intent.putExtra("LOGINTYPE", LoginDialog.LOGIN_SETTING);
		// else
		// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// context.startActivity(intent);

		Intent intent = new Intent(context, LoginActivity.class);
		if (tag == LoginActivity.TAG_LOGIN) {// 返回到主界面
			intent.putExtra("tag", LoginActivity.TAG_LOGIN);
		} else if (tag == LoginActivity.TAG_PROFILE) {// 返回到资料界面
			intent.putExtra("tag", LoginActivity.TAG_PROFILE);
		} else {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 以新的任务栈进行存放,保证任务的独立性
		}
		context.startActivity(intent);
		context.finish();
	}

	public static void showDetailActivity(Activity activity, Bundle data) {

		Intent intent = new Intent(activity, DetailActivity.class);
		intent.putExtras(data);
		activity.startActivity(intent);
	}

	public static void ShowPhotoViewActivity(Activity activity, Bundle data) {

		Intent intent = new Intent(activity, PhotoViewActivity.class);
		intent.putExtras(data);
		activity.startActivity(intent);
	}

	/**
	 * 获取id
	 * 
	 * @param activity
	 * @return
	 */
	public static int getItent(Activity activity) {
		int id = 0;
		Bundle bundle = activity.getIntent().getExtras();

		try {
			id = bundle.getInt("id");
		} catch (NullPointerException e) {
			System.out.println("UIHelper.getItent()" + "发生了空指针异常");
			id = 0;
		}

		return id;
	}

	/**
	 * 清除文字
	 * 
	 * @param cont
	 * @param editer
	 */
	public static void showClearWordsDialog(final Context cont,
			final EditText editer, final TextView numwords) {
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setTitle(R.string.clearwords);
		builder.setPositiveButton(R.string.sure,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 清除文字
						editer.setText("");
						numwords.setText("160");
					}
				});
		builder.setNegativeButton(R.string.cancle,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.show();
	}

	/**
	 * 弹出Toast消息
	 * 
	 * @param msg
	 */
	public static void ToastMessage(Context cont, String msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, int msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, String msg, int time) {
		Toast.makeText(cont, msg, time).show();
	}

	/**
	 * 点击返回监听事件
	 * 
	 * @param activity
	 * @return
	 */
	public static View.OnClickListener finish(final Activity activity) {
		return new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.finish();
			}
		};
	}

	/**
	 * 用户登录或注销
	 * 
	 * @param activity
	 */
	public static void loginOrLogout(Activity activity) {
		AppContext ac = (AppContext) activity.getApplication();
		if (ac.isLogin()) {
			ac.Logout();
			ToastMessage(activity, "已退出登录");
		} else {
			// showLoginDialog(activity);
		}
	}

	/**
	 * 清除app缓存
	 * 
	 * @param activity
	 */
	public static void clearAppCache(Activity activity) {
		final AppContext ac = (AppContext) activity.getApplication();
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					ToastMessage(ac, "缓存清除成功");
				} else {
					ToastMessage(ac, "缓存清除失败");
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				Message msg = new Message();
				try {
					ac.clearAppCache();
					msg.what = 1;
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = -1;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}

	/**
	 * 发送App异常崩溃报告
	 * 
	 * @param cont
	 * @param crashReport
	 */
	public static void sendAppCrashReport(final Context cont,
			final String crashReport) {
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(R.string.app_error);
		builder.setMessage(R.string.app_error_message);
		builder.setPositiveButton(R.string.submit_report,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 发送异常报告
						// Intent i = new Intent(Intent.ACTION_SEND);
						// // i.setType("text/plain"); //模拟器
						// i.setType("message/rfc822"); // 真机
						// i.putExtra(Intent.EXTRA_EMAIL,
						// new String[] { "jxsmallmouse@163.com" });
						// i.putExtra(Intent.EXTRA_SUBJECT,
						// "开源中国Android客户端 - 错误报告");
						// i.putExtra(Intent.EXTRA_TEXT, crashReport);
						// cont.startActivity(Intent.createChooser(i,
						// "发送错误报告"));
						// 退出
						AppManager.getAppManager().AppExit(cont);
					}
				});
		builder.setNegativeButton(R.string.sure,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 退出
						AppManager.getAppManager().AppExit(cont);
					}
				});
		builder.show();
	}

	/**
	 * 退出程序
	 * 
	 * @param cont
	 */
	public static void Exit(final Context cont) {
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(R.string.app_menu_surelogout);
		builder.setPositiveButton(R.string.sure,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 退出
						AppManager.getAppManager().AppExit(cont);
					}
				});
		builder.setNegativeButton(R.string.cancle,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.show();
	}

	/**
	 * 获取图片列表
	 */

	public List<HashMap<String, Object>> getMenuList(Context ctx, int[] imglists) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		// 图片数组

		return null;
	}

	/**
	 * 获取屏幕的尺寸
	 * 
	 * @author Administrator
	 * @return 0:width 1:height
	 */

	public static int[] getScreenSize(Context ctx) {
		WindowManager windowManager = ((Activity) ctx).getWindow()
				.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		int[] screenSize = new int[2];

		screenSize[0] = display.getWidth();
		screenSize[1] = display.getHeight();
		return screenSize;
	}

	/**
	 * 生成图片缩略图
	 * 
	 * @return
	 * @throws IOException
	 */

	public static List<HashMap<String, Object>> createThumbs(
			List<HashMap<String, Object>> imgs, File dir, Context ctx)
			throws IOException {

		File file = null;
		OutputStream os = null;
		Bitmap bitmap = null;
		List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> tmp = null;
		int id = 0;
		String name = "";
		int mpicSize = (UIHelper.getScreenSize(ctx)[1] - 30) / 2;

		for (HashMap<String, Object> img : imgs) {
			id = (Integer) img.get("id");
			name = (String) img.get("name");
			bitmap = android.media.ThumbnailUtils.extractThumbnail(
					BitmapFactory.decodeResource(ctx.getResources(), id),
					mpicSize, (int) (mpicSize * 0.618));

			file = new File(dir, name + ".png");
			if (!file.isFile()) {
				file.createNewFile();

			}
			os = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 50, os);

			tmp = new HashMap<String, Object>();
			tmp.put("name", name);
			tmp.put("path", file.getPath());

			datas.add(tmp);
		}
		os.close();

		return datas;
	}

	/**
	 * 对图片进行遍历
	 */

	public static List<HashMap<String, Object>> getThumbs(File dir) {

		List<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = null;
		File[] files = dir.listFiles();
		if (files.length == 0) {
			return null;
		} else {
			for (File file : files) {
				map = new HashMap<String, Object>();
				String name = file.getName();
				String path = file.getPath();
				map.put("name", name);
				map.put("path", path);

				lists.add(map);
			}

			return lists;
		}

	}

	/**
	 * 获取图片资源列表
	 */
	public static List<HashMap<String, Object>> getDrawables() {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = null;

		map = new HashMap<String, Object>();
		map.put("id", R.drawable.brand);
		map.put("name", "brand");
		list.add(map);
		map = null;

		map = new HashMap<String, Object>();
		map.put("id", R.drawable.concept);
		map.put("name", "concept");
		list.add(map);
		map = null;

		map = new HashMap<String, Object>();
		map.put("id", R.drawable.display);
		map.put("name", "display");
		list.add(map);
		map = null;

		map = new HashMap<String, Object>();
		map.put("id", R.drawable.design);
		map.put("name", "design");
		list.add(map);
		map = null;

		map = new HashMap<String, Object>();
		map.put("id", R.drawable.huali);
		map.put("name", "huali");
		list.add(map);
		map = null;

		map = new HashMap<String, Object>();
		map.put("id", R.drawable.huayi);
		map.put("name", "huayi");
		list.add(map);
		map = null;

		map = new HashMap<String, Object>();
		map.put("id", R.drawable.join);
		map.put("name", "join");
		list.add(map);
		map = null;

		map = new HashMap<String, Object>();
		map.put("id", R.drawable.download);
		map.put("name", "download");
		list.add(map);
		map = null;

		return list;
	}

	public static void showReigster(Activity activity) {
		Intent intent = new Intent(activity, RegisterActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}

	/**
	 * 启动我的资料界面
	 * 
	 * @param activity
	 * @param user
	 */
	public static void showMyProfileActivity(Activity activity, User user) {
		Bundle bundle = new Bundle();
		bundle.putSerializable("user", user);
		Intent intent = new Intent(activity, MyProfileActivity.class);
		intent.putExtras(bundle);

		activity.startActivity(intent);
		bundle = null;
		intent = null;
		activity.finish();
	}

}
