package com.sartino.huayi.app.ui;

import java.util.ArrayList;
import java.util.List;

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

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class HuayiActivity extends BaseActivity {
	private static final String TAG = "HuayiActivity";

	private ActionBar mActionBar = null;
	private ViewPager mViewPager = null;
	private PagerTitleStrip mPagerTitleStrip = null;
	private List<View> mViewList = new ArrayList<View>();
	private List<String> mTitleList = new ArrayList<String>();
	private LayoutInflater mLayoutInflator = null;
	private View mHuayi_western = null, mHuayi_east = null;
	private MyPageApdater mMyPageApdater = null;
	private PullToRefreshListView mLvHuayi_western = null;
	private PullToRefreshListView mLvHuayi_east = null;
	private Handler mHuayi_westernHandler = null;
	private Handler mHuayi_eastrnHandler = null;
	private SimpleInfoAdapter mSA_Huayi_Western = null;
	private SimpleInfoAdapter mSA_Huayi_East = null;
	private TextView mVfooter_moreHuayi_western = null;
	private ProgressBar mVfooter_progressHuayi_western = null;
	private List<SimpleInfo> mDS_Huayi_Western = new ArrayList<SimpleInfo>();
	private List<SimpleInfo> mDS_Huayi_East = new ArrayList<SimpleInfo>();
	private TextView mVfooter_moreHuayi_eastrn = null;
	private ProgressBar mVfooter_progressHuayi_eastrn = null;
	private View mLV_Footer_Huayi_Western = null;
	private View mLv_Footer_Huayi_East = null;
	private int mCurCategory = 14;
	private AppContext mAppcontext = null;

	/**
	 * init actionbar
	 */
	@SuppressLint("InlinedApi")
	public void initActionBar() {
		mAppcontext = (AppContext) getApplication();
		mActionBar = getActionBar();
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME
				| ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle(R.string.huayi);

	}

	private void initHandler() {
		mHuayi_eastrnHandler = getHandler(mLvHuayi_east, mSA_Huayi_East,
				mVfooter_moreHuayi_eastrn, mVfooter_progressHuayi_eastrn);
		mHuayi_westernHandler = getHandler(mLvHuayi_western, mSA_Huayi_Western,
				mVfooter_moreHuayi_western, mVfooter_progressHuayi_western);

		if (mDS_Huayi_Western.isEmpty()) {
			LoadhuayiEastData(UIHelper.LISTVIEW_ACTION_REFRESH,
					AppContext.CATEGORY_HUAYI_WESTERN);
			mSA_Huayi_Western.notifyDataSetChanged();
		}

	}

	private Handler getHandler(final PullToRefreshListView lv,
			final BaseAdapter adapter, final TextView tv,
			final ProgressBar progressBar) {
		return new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what >= 0) {// 处理数据

					Notice notice = processDesignData(msg.what, msg.obj,
							msg.arg1, msg.arg2);

					if (msg.what < AppContext.PAGE_SIZE && msg.what != 0) {// 获取的数据大小小于页面大小，说明已经加载完毕
						lv.setTag(UIHelper.LISTVIEW_DATA_FULL);
						adapter.notifyDataSetChanged();
						tv.setText(R.string.load_finsh);
					} else if (msg.what == AppContext.PAGE_SIZE) {
						lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
						adapter.notifyDataSetChanged();
						tv.setText(R.string.load_more);
					} else if (msg.what == -1) {// 加载数据发生错误
						lv.setTag(UIHelper.LISTVIEW_DATA_MORE);
						tv.setText(R.string.load_error);
						((AppException) msg.obj).makeToast(HuayiActivity.this);
					} else if (msg.what == 0) {// 加载数据发生错误
						lv.setTag(UIHelper.LISTVIEW_DATA_FULL);
						tv.setText(R.string.load_finsh);

					} else {// 适配器所绑定的数据源中没有数据
						lv.setTag(UIHelper.LISTVIEW_DATA_EMPTY);
						tv.setText(R.string.no_data);
					}

					progressBar.setVisibility(View.GONE);

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

	protected Notice processDesignData(int what, Object obj, int arg1, int arg2) {
		List<SimpleInfo> datas = (List<SimpleInfo>) obj;
		Notice notice = new Notice();
		int newMessage = 0;
		switch (arg2) {
		case AppContext.CATEGORY_HUALI_EAST:

			if (what > 0) {// 有数据
				// 判断原有数据集中是否存在数据,没有数据，则直接添加。有数据则将数据与存在的数据进行比对，删除重复的数据
				if (!mSA_Huayi_East.isEmpty()) {
					// 主要是用来提示用户更新了多少条记录
					for (SimpleInfo simpleInfo : mDS_Huayi_East) {

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
				mDS_Huayi_East.addAll(0, datas);
			} else if (arg1 == UIHelper.LISTVIEW_ACTION_SCROLL) {// 滚动
				mDS_Huayi_Western.addAll(datas);
			}
			datas = null;

			return notice;
		case AppContext.CATEGORY_HUAYI_WESTERN:

			if (what > 0) {// 有数据
				// 判断原有数据集中是否存在数据,没有数据，则直接添加。有数据则将数据与存在的数据进行比对，删除重复的数据
				if (!mSA_Huayi_Western.isEmpty()) {
					// 主要是用来提示用户更新了多少条记录
					for (SimpleInfo simpleInfo : mDS_Huayi_Western) {

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
				mDS_Huayi_Western.addAll(0, datas);
			} else if (arg1 == UIHelper.LISTVIEW_ACTION_SCROLL) {// 滚动
				mDS_Huayi_Western.addAll(datas);
			}
			datas = null;

			return notice;
		}

		return notice;
	}

	/**
	 * init pulltorefreshlistview
	 */
	public void initPullToRefreshListView() {
		mSA_Huayi_Western = new SimpleInfoAdapter(HuayiActivity.this,
				mDS_Huayi_Western);
		mSA_Huayi_East = new SimpleInfoAdapter(HuayiActivity.this,
				mDS_Huayi_East);

		mLvHuayi_east = (PullToRefreshListView) mHuayi_east
				.findViewById(R.id.huayi_east);
		mLvHuayi_western = (PullToRefreshListView) mHuayi_western
				.findViewById(R.id.huayi_western);

		mLV_Footer_Huayi_Western = mLayoutInflator.inflate(
				R.layout.listviewfooter, null);
		mLv_Footer_Huayi_East = mLayoutInflator.inflate(
				R.layout.listviewfooter, null);
		mVfooter_moreHuayi_western = (TextView) mLV_Footer_Huayi_Western
				.findViewById(R.id.listview_foot_more);
		mVfooter_moreHuayi_eastrn = (TextView) mLv_Footer_Huayi_East
				.findViewById(R.id.listview_foot_more);
		mVfooter_progressHuayi_western = (ProgressBar) mLV_Footer_Huayi_Western
				.findViewById(R.id.listview_foot_progress);
		mVfooter_progressHuayi_eastrn = (ProgressBar) mLv_Footer_Huayi_East
				.findViewById(R.id.listview_foot_progress);
		mVfooter_progressHuayi_eastrn.setVisibility(View.GONE);
		mVfooter_progressHuayi_western.setVisibility(View.GONE);
		mLvHuayi_east.addFooterView(mLv_Footer_Huayi_East);
		mLvHuayi_western.addFooterView(mLV_Footer_Huayi_Western);
		mLvHuayi_east.setAdapter(mSA_Huayi_East);
		mLvHuayi_western.setAdapter(mSA_Huayi_Western);

		this.addhuayiWesternListener();
		this.addhuayiEastListener();
	}

	private void addhuayiEastListener() {
		mLvHuayi_east.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 点击头部、底部栏无效
				if (position == 0 || view == mLv_Footer_Huayi_East)
					return;

				SimpleInfo post = mSA_Huayi_East.getmDatas().get(position - 1);
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", post);
				// 跳转
				UIHelper.showDetailActivity(HuayiActivity.this, bundle);

			}
		});

		mLvHuayi_east.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				mCurCategory = getCategory();
				LoadhuayiEastData(UIHelper.LISTVIEW_ACTION_REFRESH,
						AppContext.CATEGORY_HUAYI_EAST);
			}
		});

		mLvHuayi_east.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				mLvHuayi_east.onScroll(view, firstVisibleItem,
						visibleItemCount, totalItemCount);
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				mLvHuayi_east.onScrollStateChanged(view, scrollState);

				if (mDS_Huayi_East.isEmpty()) {
					return;
				}

				// 判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if (view.getPositionForView(mLv_Footer_Huayi_East) == view
							.getLastVisiblePosition())
						scrollEnd = true;
				} catch (Exception e) {
					scrollEnd = false;
				}

				if (scrollEnd) {
					// 滚动到底部,加载数据开始
					mVfooter_moreHuayi_eastrn.setText(R.string.loading);
					mVfooter_progressHuayi_eastrn.setVisibility(View.VISIBLE);
					mCurCategory = getCategory();
					LoadhuayiEastData(UIHelper.LISTVIEW_ACTION_SCROLL,
							AppContext.CATEGORY_HUAYI_EAST);
				}
			}
		});
	}

	protected void LoadhuayiEastData(final int action, final int category) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = mHuayi_eastrnHandler.obtainMessage();
				boolean isReresh = false;
				if (action == com.sartino.huayi.app.common.UIHelper.LISTVIEW_ACTION_REFRESH
						|| action == com.sartino.huayi.app.common.UIHelper.LISTVIEW_ACTION_SCROLL) {
					isReresh = true;
				}
				List<SimpleInfo> datas = null;
				// 只有当当前类别等于传进来的参数相同之时
				if (category == mCurCategory) {
					// 获取数据
					try {
						int pageIndex = 0;
						if (mDS_Huayi_East.size() > 0) {

							if (action == UIHelper.LISTVIEW_ACTION_REFRESH) {
								pageIndex = mDS_Huayi_East.get(0).getId() + 1;
							} else if (action == UIHelper.LISTVIEW_ACTION_SCROLL) {
								pageIndex = mDS_Huayi_East.get(
										mDS_Huayi_East.size() - 1).getId() - 1;
							}
						}

						datas = mAppcontext.getSimpleInfo(category, pageIndex,
								action, isReresh);

						msg.obj = datas;
						msg.what = datas.size();

					} catch (AppException e) {
						msg.what = -1;
						msg.obj = e;
					}

				}
				msg.arg1 = action;
				msg.arg2 = category;

				// if (mViewPager.getCurrentItem() == category) {// 数据发送
				mHuayi_eastrnHandler.sendMessage(msg);
				// }

			}

		}).start();

	}

	protected int getCategory() {
		int id = mViewPager.getCurrentItem();
		switch (id) {
		case 1:
			return AppContext.CATEGORY_HUAYI_EAST;
			// break;

		case 0:
			return AppContext.CATEGORY_HUAYI_WESTERN;
			// break;
		}
		return mCurCategory;
	}

	protected void LoadhuayiWesternData(final int action, final int category) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = mHuayi_westernHandler.obtainMessage();
				boolean isReresh = false;
				if (action == com.sartino.huayi.app.common.UIHelper.LISTVIEW_ACTION_REFRESH
						|| action == com.sartino.huayi.app.common.UIHelper.LISTVIEW_ACTION_SCROLL) {
					isReresh = true;
				}
				List<SimpleInfo> datas = null;
				// 只有当当前类别等于传进来的参数相同之时
				if (category == mCurCategory) {
					// 获取数据
					try {
						int pageIndex = 0;
						if (mDS_Huayi_Western.size() > 0) {

							if (action == UIHelper.LISTVIEW_ACTION_REFRESH) {
								pageIndex = mDS_Huayi_Western.get(0).getId() + 1;
							} else if (action == UIHelper.LISTVIEW_ACTION_SCROLL) {
								pageIndex = mDS_Huayi_Western.get(
										mDS_Huayi_Western.size() - 1).getId() - 1;
							}
						}

						datas = mAppcontext.getSimpleInfo(category, pageIndex,
								action, isReresh);

						msg.obj = datas;
						msg.what = datas.size();

					} catch (AppException e) {
						msg.what = -1;
						msg.obj = e;
					}

				}
				msg.arg1 = action;
				msg.arg2 = category;

				// if (mViewPager.getCurrentItem() == category) {// 数据发送
				mHuayi_westernHandler.sendMessage(msg);
				// }

			}

		}).start();

	}

	private void addhuayiWesternListener() {
		mLvHuayi_western.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 点击头部、底部栏无效
				if (position == 0 || view == mLV_Footer_Huayi_Western)
					return;

				SimpleInfo post = mSA_Huayi_Western.getmDatas().get(
						position - 1);
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", post);
				// 跳转
				UIHelper.showDetailActivity(HuayiActivity.this, bundle);

			}
		});

		mLvHuayi_western.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				mCurCategory = getCategory();
				LoadhuayiWesternData(UIHelper.LISTVIEW_ACTION_REFRESH,
						AppContext.CATEGORY_HUAYI_WESTERN);
			}
		});

		mLvHuayi_western
				.setOnScrollListener(new AbsListView.OnScrollListener() {

					@Override
					public void onScroll(AbsListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {
						mLvHuayi_western.onScroll(view, firstVisibleItem,
								visibleItemCount, totalItemCount);
					}

					@Override
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						mLvHuayi_western
								.onScrollStateChanged(view, scrollState);

						if (mDS_Huayi_Western.isEmpty()) {
							return;
						}

						// 判断是否滚动到底部
						boolean scrollEnd = false;
						try {
							if (view.getPositionForView(mLV_Footer_Huayi_Western) == view
									.getLastVisiblePosition())
								scrollEnd = true;
						} catch (Exception e) {
							scrollEnd = false;
						}

						if (scrollEnd) {
							// 滚动到底部,加载数据开始
							mVfooter_moreHuayi_western
									.setText(R.string.loading);
							mVfooter_progressHuayi_western
									.setVisibility(View.VISIBLE);
							mCurCategory = getCategory();
							LoadhuayiWesternData(
									UIHelper.LISTVIEW_ACTION_SCROLL,
									AppContext.CATEGORY_HUAYI_WESTERN);
						}
					}
				});
	}

	/**
	 * init viewpage & pagertabstrip
	 */
	public void initViewPageandPagerStrip() {
		mViewPager = (ViewPager) this.findViewById(R.id.vp_huayi);
		mPagerTitleStrip = (PagerTitleStrip) this
				.findViewById(R.id.huayi_pagertab);
		mPagerTitleStrip.setTextSpacing(50);
		mLayoutInflator = getLayoutInflater();
		mHuayi_east = mLayoutInflator.inflate(R.layout.huayi_east, null);
		mHuayi_western = mLayoutInflator.inflate(R.layout.huayi_westeran, null);
		mViewList.add(mHuayi_western);
		mViewList.add(mHuayi_east);

		mTitleList.add(getResources().getString(R.string.huayi_western));
		mTitleList.add(getResources().getString(R.string.huayi_east));

		mMyPageApdater = new MyPageApdater(this, mViewList, mTitleList);
		mViewPager.setAdapter(mMyPageApdater);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.huayi);

		this.initActionBar();
		this.initViewPageandPagerStrip();
		this.initPullToRefreshListView();
		this.initHandler();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
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
}
