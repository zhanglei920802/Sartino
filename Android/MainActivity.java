package com.sartino.huayi.app.ui;

import greendroid.widget.MyQuickAction;
import greendroid.widget.QuickActionGrid;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;

import java.util.HashMap;
import java.util.List;
import com.sartino.huayi.app.AppContext;
import com.sartino.huayi.app.R;
import com.sartino.huayi.app.adapter.GridViewMenuListAdapter;
import com.sartino.huayi.app.common.UIHelper;

import com.sartino.huayi.app.ui.widgets.GridViewInterceptor;
import com.sartino.huayi.app.ui.widgets.GridViewInterceptor.DropListener;

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
	private GridViewInterceptor mGV = null;
	private QuickActionGrid mMenu = null;
	private OnQuickActionClickListener mMenuListener = new OnQuickActionClickListener() {

		@Override
		public void onQuickActionClicked(QuickActionWidget widget, int position) {
			System.out.println("菜单被点击了");
		}

	};
	private DropListener mDropListener = new DropListener() {

		@Override
		public void drop(int from, int to) {

			// tmp = mMenulistAdapter.getMdatas();
			//
			// if (from > to) {
			// for (int i = from; i < tmp.size() - 1; i++) {
			// tmp.set(i, tmp.get(i + 1));
			// mMenulistAdapter.notifyDataSetChanged();
			// }
			// tmp.set(tmp.size() - 1, map);
			// mMenulistAdapter.notifyDataSetChanged();
			//
			// }
			// if (from < to) {
			// for (int i = tmp.size() - 1; i > to; i--) {
			// tmp.set(i, tmp.get(i - 1));
			// mMenulistAdapter.notifyDataSetChanged();
			// }
			// tmp.set(to, map);
			// mMenulistAdapter.notifyDataSetChanged();
			// }
			HashMap<String, Object> map = (HashMap<String, Object>) mMenulistAdapter
					.getItem(from);
			mMenulistAdapter.getMdatas().remove(map);
			mMenulistAdapter.getMdatas().add(to, map);
			mMenulistAdapter.notifyDataSetChanged();
		}
	};

	private String mCurClick = BRAND;// default
	private OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			String tag = view.findViewById(R.id.imv_menu_item).getTag()
					.toString().trim();
			System.out.println(tag);
			if (BRAND.equals(tag)) {
				mCurClick = BRAND;

			} else if (CONCEPT.equals(tag)) {
				mCurClick = CONCEPT;
			} else if (DESIGN.equals(tag)) {
				mCurClick = DESIGN;
			} else if (DISPLAY.equals(tag)) {
				mCurClick = DISPLAY;
			} else if (HUALI.equals(tag)) {
				mCurClick = HUALI;
			} else if (HUAYI.equals(tag)) {
				mCurClick = HUAYI;
			} else if (JOIN.equals(tag)) {
				mCurClick = JOIN;
			} else if (DOWNLOAD.equals(tag)) {
				mCurClick = DOWNLOAD;
			}
			UIHelper.showActivity(MainActivity.this, mCurClick);

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		android.os.Debug.startMethodTracing(TAG);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		findControl();
		init();
	}

	public void findControl() {
		mGV = (GridViewInterceptor) findViewById(R.id.gv_menu_list);
		mGV.setDropListener(mDropListener);
	}

	public void init() {

		mDatas = UIHelper.getThumbs(AppContext.getInstance().getThumbsDir());
		mMenulistAdapter = new GridViewMenuListAdapter(this, mDatas);
		mGV.setAdapter(mMenulistAdapter);
		mGV.setOnItemClickListener(mItemClickListener);

	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.main_menu_settings) {

			UIHelper.showSettingActivity(this);

		} else if (id == R.id.main_menu_login) {

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
		boolean flag = true;

		// if (keyCode == KeyEvent.KEYCODE_MENU) {
		// // mMenu.show(mGV, true);
		// } else {
		// flag = super.onKeyDown(keyCode, event);
		// }
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
