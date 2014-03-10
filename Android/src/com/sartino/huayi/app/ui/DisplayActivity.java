package com.sartino.huayi.app.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.sartino.huayi.app.AppContext;
import com.sartino.huayi.app.AppException;
import com.sartino.huayi.app.R;
import com.sartino.huayi.app.bean.Notice;
import com.sartino.huayi.app.bean.SimpleInfo;
import com.sartino.huayi.app.common.DateUtil;
import com.sartino.huayi.app.common.UIHelper;
import com.sartino.huayi.app.ui.adapter.MyPageApdater;
import com.sartino.huayi.app.ui.adapter.SimpleInfoAdapter;
import com.sartino.huayi.app.ui.widgets.PullToRefreshListView;
import com.sartino.huayi.app.ui.widgets.PullToRefreshListView.OnRefreshListener;

public class DisplayActivity extends BaseActivity {
	public static final String TAG = "DisplayActivity";

	private ActionBar mActionBar = null;
	protected AppContext mAppcontext;
	private View mDisplay_display = null;
	private List<SimpleInfo> mDSDisplay_Display = new ArrayList<SimpleInfo>();
	private Handler mHDisplay_display = null;
	private LayoutInflater mLayoutInflator = null;
	private PullToRefreshListView mLvDisplay_display = null;
	private MyPageApdater mMyPageApdater = null;
	private PagerTitleStrip mPagerTitleStrip = null;
	private SimpleInfoAdapter mSADisplay_dislpay = null;
	private List<String> mTitleList = new ArrayList<String>();
	private TextView mVfooter_moreDisplay_Display = null;
	private ProgressBar mVfooter_progressDisplay_Display = null;
	private View mVfooterDisplay_Display = null;
	private List<View> mViewList = new ArrayList<View>();

	private ViewPager mViewPager = null;

	private Handler getHandler(final PullToRefreshListView lv,
			BaseAdapter adapter, final TextView tv, ProgressBar p) {
		return new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what >= 0) {// 处理数据

					Notice notice = processDesignData(msg.what, msg.obj,
							msg.arg1);

					if (msg.what < AppContext.PAGE_SIZE && msg.what != 0) {// 获取的数据大小小于页面大小，说明已经加载完毕
						lv.setTag(UIHelper.LISTVIEW_DATA_FULL);
						mSADisplay_dislpay.notifyDataSetChanged();
//						tv.setText(R.string.load_finsh);
						tv.setVisibility(View.INVISIBLE);
						UIHelper.ToastMessage(DisplayActivity.this,R.string.load_finsh);
					} else if (msg.what == AppContext.PAGE_SIZE) {
						lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
						mSADisplay_dislpay.notifyDataSetChanged();
						tv.setText(R.string.load_more);
					} else if (msg.what == -1) {// 加载数据发生错误
						lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
						tv.setText(R.string.load_error);
						((AppException) msg.obj).makeToast(mAppcontext);
					} else if (msg.what == 0) {// 加载数据发生错误
						lv.setTag(UIHelper.LISTVIEW_DATA_FULL);
						tv.setText(R.string.load_finsh);

					} else {// 适配器所绑定的数据源中没有数据
						lv.setTag(UIHelper.LISTVIEW_DATA_EMPTY);
						tv.setText(R.string.no_data);
					}

					mVfooter_progressDisplay_Display.setVisibility(View.GONE);

					if (msg.arg1 == UIHelper.LISTVIEW_ACTION_REFRESH) {
						lv.onRefreshComplete(getString(R.string.pull_to_refresh_update)
								+ DateUtil.getDafaultDate());
						lv.setSelection(0);
					} else if (msg.arg1 == UIHelper.LISTVIEW_ACTION_CHANGE_CATALOG) {
						lv.onRefreshComplete();
						lv.setSelection(0);
					} else if (msg.arg1 == UIHelper.LISTVIEW_ACTION_SCROLL) {
						lv.onRefreshComplete();

					}

				}
			}

		};
	}

	/**
	 * init actionbar
	 */
	@SuppressLint("InlinedApi")
	public void initActionBar() {
		mActionBar = getActionBar();
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME
				| ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle(R.string.display);

	}

	/**
	 * 初始化景观模块监听器，包括点击，下拉刷新，滚动等
	 */
	public void initDisplay_displayListener() {
		mLvDisplay_display.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 点击头部、底部栏无效
				if (position == 0 || view == mVfooterDisplay_Display)
					return;

				SimpleInfo post = mSADisplay_dislpay.getmDatas().get(
						position - 1);
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", post);
				// 跳转
				UIHelper.showDetailActivity(DisplayActivity.this, bundle);

			}
		});

		mLvDisplay_display.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				LoadDisplay_DisplayData(UIHelper.LISTVIEW_ACTION_REFRESH,
						AppContext.CATEGORY_DISPLAY_DISPLAY);
			}
		});

		mLvDisplay_display
				.setOnScrollListener(new AbsListView.OnScrollListener() {

					@Override
					public void onScroll(AbsListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {
						mLvDisplay_display.onScroll(view, firstVisibleItem,
								visibleItemCount, totalItemCount);
					}

					@Override
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						mLvDisplay_display.onScrollStateChanged(view,
								scrollState);

						if (mDSDisplay_Display.isEmpty()) {
							return;
						}

						// 判断是否滚动到底部
						boolean scrollEnd = false;
						try {
							if (view.getPositionForView(mVfooterDisplay_Display) == view
									.getLastVisiblePosition())
								scrollEnd = true;
						} catch (Exception e) {
							scrollEnd = false;
						}

						if (scrollEnd) {
							// 滚动到底部,加载数据开始
							mVfooter_moreDisplay_Display
									.setText(R.string.loading);
							mVfooter_progressDisplay_Display
									.setVisibility(View.VISIBLE);
							LoadDisplay_DisplayData(
									UIHelper.LISTVIEW_ACTION_SCROLL,
									AppContext.CATEGORY_DISPLAY_DISPLAY);
						}
					}
				});
	}

	private void initHandler() {
		mHDisplay_display = getHandler(mLvDisplay_display, mSADisplay_dislpay,
				mVfooter_moreDisplay_Display, mVfooter_progressDisplay_Display);
		if (mDSDisplay_Display.isEmpty()) {
			LoadDisplay_DisplayData(UIHelper.LISTVIEW_ACTION_REFRESH,
					AppContext.CATEGORY_DISPLAY_DISPLAY);
		}

	}

	/**
	 * init pulltorefreshlistview
	 */
	public void initPullToRefreshListView() {
		mLvDisplay_display = (PullToRefreshListView) mDisplay_display
				.findViewById(R.id.display_display);
		mSADisplay_dislpay = new SimpleInfoAdapter(this, mDSDisplay_Display);

		mVfooterDisplay_Display = mLayoutInflator.inflate(
				R.layout.listviewfooter, null);
		mVfooter_progressDisplay_Display = (ProgressBar) mVfooterDisplay_Display
				.findViewById(R.id.listview_foot_progress);
		mVfooter_moreDisplay_Display = (TextView) mVfooterDisplay_Display
				.findViewById(R.id.listview_foot_more);
		mVfooter_progressDisplay_Display.setVisibility(View.GONE);

		mLvDisplay_display.addFooterView(mVfooterDisplay_Display);
		mLvDisplay_display.setAdapter(mSADisplay_dislpay);

		this.initDisplay_displayListener();
	}

	/**
	 * init viewpage & pagertabstrip
	 */
	public void initViewPageandPagerStrip() {
		mAppcontext = (AppContext) getApplicationContext();
		mViewPager = (ViewPager) this.findViewById(R.id.vp_display);
		mPagerTitleStrip = (PagerTitleStrip) this
				.findViewById(R.id.dislay_pagertab);
		mPagerTitleStrip.setTextSpacing(50);
		mLayoutInflator = getLayoutInflater();
		mDisplay_display = mLayoutInflator.inflate(R.layout.display_display,
				null);
		mViewList.add(mDisplay_display);
		mTitleList.add(getResources().getString(R.string.display_show));
		mMyPageApdater = new MyPageApdater(this, mViewList, mTitleList);
		mViewPager.setAdapter(mMyPageApdater);
	}

	/**
	 * 获取展示数据
	 * 
	 * @param action
	 * @param category
	 */
	public void LoadDisplay_DisplayData(final int action, final int category) {
		mVfooter_progressDisplay_Display.setVisibility(View.VISIBLE);
		new Thread(new Runnable() {// 数据加载

					@Override
					public void run() {

						Message msg = mHDisplay_display.obtainMessage();
						boolean isReresh = false;
						if (action == com.sartino.huayi.app.common.UIHelper.LISTVIEW_ACTION_REFRESH
								|| action == com.sartino.huayi.app.common.UIHelper.LISTVIEW_ACTION_SCROLL) {
							isReresh = true;
						}
						List<SimpleInfo> datas = null;
						// 只有当当前类别等于传进来的参数相同之时
						// 获取数据
						try {

							int pageIndex = 0;

							if (mDSDisplay_Display.size() > 0) {

								if (action == UIHelper.LISTVIEW_ACTION_REFRESH) {
									pageIndex = mDSDisplay_Display.get(0)
											.getId() + 1;
								} else if (action == UIHelper.LISTVIEW_ACTION_SCROLL) {
									pageIndex = mDSDisplay_Display.get(
											mDSDisplay_Display.size() - 1)
											.getId() - 1;
								}
							}
							datas = mAppcontext.getSimpleInfo(category,
									pageIndex, action, isReresh);

							msg.obj = datas;
							msg.what = datas.size();

						} catch (AppException e) {
							msg.what = -1;
							msg.obj = e;
						}

						msg.arg1 = action;
						// msg.arg2 = category;

						// if (mViewPager.getCurrentItem() == category) {// 数据发送
						mHDisplay_display.sendMessage(msg);
						// }

					}
				}).start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display);

		this.initActionBar();
		this.initViewPageandPagerStrip();
		this.initPullToRefreshListView();
		this.initHandler();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	protected Notice processDesignData(int what, Object obj, int arg1) {
		List<SimpleInfo> datas = (List<SimpleInfo>) obj;
		Notice notice = new Notice();
		int newMessage = 0;
		if (what > 0) {// 有数据
			// 判断原有数据集中是否存在数据,没有数据，则直接添加。有数据则将数据与存在的数据进行比对，删除重复的数据
			if (!mDSDisplay_Display.isEmpty()) {
				// 主要是用来提示用户更新了多少条记录
				for (SimpleInfo simpleInfo : mDSDisplay_Display) {

					boolean isSame = false;
					for (SimpleInfo simpleInfo2 : datas) {
						if (simpleInfo.getId() == simpleInfo2.getId()) {
							isSame = true;
						}
					}

					if (!isSame) {// 如果不相同

						newMessage++;
					}
				}
			}

		} else if (what == -1) {// 发生异常

		}
		if (arg1 == UIHelper.LISTVIEW_ACTION_REFRESH) {// 刷新
			mDSDisplay_Display.addAll(0, datas);
		} else if (arg1 == UIHelper.LISTVIEW_ACTION_SCROLL) {// 滚动
			mDSDisplay_Display.addAll(datas);
		}
		datas = null;
		return notice;
	}
}
