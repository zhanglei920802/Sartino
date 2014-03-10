package com.sartino.huayi.app.ui;

import greendroid.widget.MyQuickAction;
import greendroid.widget.QuickActionGrid;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;

import java.util.HashMap;
import java.util.List;

import com.sartino.huayi.app.AppContext;
import com.sartino.huayi.app.R;
import com.sartino.huayi.app.adapter.Configure;
import com.sartino.huayi.app.adapter.GridViewMenuListAdapter;
import com.sartino.huayi.app.common.UIHelper;
import com.sartino.huayi.app.ui.widgets.DragGrid;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends BaseActivity {

	public static final String TAG = "MainActivity";

	private List<HashMap<String, Object>> mDatas = null;
	private List<HashMap<String, Object>> tmp = null;
	private GridViewMenuListAdapter mMenulistAdapter = null;
	private DragGrid mGV = null;
	private QuickActionGrid mMenu = null;
	private OnQuickActionClickListener mMenuListener = new OnQuickActionClickListener() {

		@Override
		public void onQuickActionClicked(QuickActionWidget widget, int position) {

		}

	};

	private String mCurClick = BaseActivity.BRAND;// default
	private OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			String tag = view.findViewById(R.id.imv_menu_item).getTag()
					.toString().trim();
			System.out.println(tag);
			if (BaseActivity.BRAND.equals(tag)) {
				mCurClick = BaseActivity.BRAND;

			} else if (BaseActivity.CONCEPT.equals(tag)) {
				mCurClick = BaseActivity.CONCEPT;
			} else if (BaseActivity.DESIGN.equals(tag)) {
				mCurClick = BaseActivity.DESIGN;
			} else if (BaseActivity.DISPLAY.equals(tag)) {
				mCurClick = BaseActivity.DISPLAY;
			} else if (BaseActivity.HUALI.equals(tag)) {
				mCurClick = BaseActivity.HUALI;
			} else if (BaseActivity.HUAYI.equals(tag)) {
				mCurClick = BaseActivity.HUAYI;
			} else if (BaseActivity.JOIN.equals(tag)) {
				mCurClick = BaseActivity.JOIN;
			} else if (BaseActivity.DOWNLOAD.equals(tag)) {
				mCurClick = BaseActivity.DOWNLOAD;
			}
			UIHelper.showActivity(MainActivity.this, mCurClick);

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		android.os.Debug.startMethodTracing(TAG);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(Window.FEATURE_NO_TITLE,
				Window.FEATURE_CUSTOM_TITLE);
		Configure.init(this);

		setContentView(R.layout.main);

		findControl();
		init();
	}

	public void findControl() {
		mGV = (DragGrid) findViewById(R.id.gv_menu_list);

	}

	public void init() {

		mDatas = UIHelper.getThumbs(AppContext.getInstance().getThumbsDir(
				MainActivity.this));
		mMenulistAdapter = new GridViewMenuListAdapter(this, mDatas);
		mGV.setAdapter(mMenulistAdapter);
		mGV.setOnItemClickListener(mItemClickListener);

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.main_menu_settings) {

			UIHelper.showSettingActivity(this);

		} else if (id == R.id.main_menu_exit) {
			UIHelper.Exit(MainActivity.this);
		}

		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			UIHelper.Exit(MainActivity.this);
		}
		return super.onKeyDown(keyCode, event);

	}

	/**
	 * 菜单的初始化
	 */
	public void prepareQuickActionGrid() {
		mMenu = new QuickActionGrid(this);

		mMenu.addQuickAction(new MyQuickAction(this, 0,
				R.string.main_menu_login));
		mMenu.addQuickAction(new MyQuickAction(this, 0,
				R.string.main_menu_setting));

		mMenu.setOnQuickActionClickListener(mMenuListener);
	}

	@Override
	protected void onDestroy() {
		android.os.Debug.stopMethodTracing();
		super.onDestroy();
	}

}
