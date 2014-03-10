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
import android.view.Menu;
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

public class HualiActivity extends BaseActivity {
	private static final String TAG = "HualiActivity";

	private ActionBar mActionBar = null;
	private ViewPager mViewPager = null;
	private PagerTitleStrip mPagerTitleStrip = null;
	private List<View> mViewList = new ArrayList<View>();
	private List<String> mTitleList = new ArrayList<String>();
	private LayoutInflater mLayoutInflator = null;
	private View mHuali_western = null, mHuali_east = null;
	private MyPageApdater mMyPageApdater = null;
	private PullToRefreshListView mLvHuali_western = null;
	private PullToRefreshListView mLvHuali_east = null;
	private Handler mHuali_westernHandler = null;
	private Handler mHuali_eastrnHandler = null;
	private SimpleInfoAdapter mSA_Huali_Western = null;
	private SimpleInfoAdapter mSA_HuaLi_East = null;
	private TextView mVfooter_moreHuali_western = null;
	private ProgressBar mVfooter_progressHuali_western = null;
	private List<SimpleInfo> mDS_Huali_Western = new ArrayList<SimpleInfo>();
	private List<SimpleInfo> mDS_HuaLi_East = new ArrayList<SimpleInfo>();
	private TextView mVfooter_moreHuali_eastrn = null;
	private ProgressBar mVfooter_progressHuali_eastrn = null;
	private View mLV_Footer_Huali_Western = null;
	private View mLv_Footer_Huali_East = null;
	private int mCurCategory = 12;
	private AppContext mAppcontext = null;

	private void addHuaLiEastListener() {
		mLvHuali_east.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 点击头部、底部栏无效
				if (position == 0 || view == mLv_Footer_Huali_East)
					return;

				SimpleInfo post = mSA_HuaLi_East.getmDatas().get(position - 1);
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", post);
				// 跳转
				UIHelper.showDetailActivity(HualiActivity.this, bundle);

			}
		});

		mLvHuali_east.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				mCurCategory = getCategory();
				LoadHuaLiEastData(UIHelper.LISTVIEW_ACTION_REFRESH,
						AppContext.CATEGORY_HUALI_EAST);
			}
		});

		mLvHuali_east.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				mLvHuali_east.onScroll(view, firstVisibleItem,
						visibleItemCount, totalItemCount);
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				mLvHuali_east.onScrollStateChanged(view, scrollState);

				if (mDS_HuaLi_East.isEmpty()) {
					return;
				}

				// 判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if (view.getPositionForView(mLv_Footer_Huali_East) == view
							.getLastVisiblePosition())
						scrollEnd = true;
				} catch (Exception e) {
					scrollEnd = false;
				}

				if (scrollEnd) {
					// 滚动到底部,加载数据开始
					mVfooter_moreHuali_eastrn.setText(R.string.loading);
					mVfooter_progressHuali_eastrn.setVisibility(View.VISIBLE);
					mCurCategory = getCategory();
					LoadHuaLiEastData(UIHelper.LISTVIEW_ACTION_SCROLL,
							AppContext.CATEGORY_HUALI_EAST);
				}
			}
		});
	}

	private void addHuaLiWesternListener() {
		mLvHuali_western.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 点击头部、底部栏无效
				if (position == 0 || view == mLV_Footer_Huali_Western)
					return;

				SimpleInfo post = mSA_Huali_Western.getmDatas().get(
						position - 1);
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", post);
				// 跳转
				UIHelper.showDetailActivity(HualiActivity.this, bundle);

			}
		});

		mLvHuali_western.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				mCurCategory = getCategory();
				LoadHuaLiWesternData(UIHelper.LISTVIEW_ACTION_REFRESH,
						AppContext.CATEGORY_HUALI_WESTERN);
			}
		});

		mLvHuali_western
				.setOnScrollListener(new AbsListView.OnScrollListener() {

					@Override
					public void onScroll(AbsListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {
						mLvHuali_western.onScroll(view, firstVisibleItem,
								visibleItemCount, totalItemCount);
					}

					@Override
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						mLvHuali_western
								.onScrollStateChanged(view, scrollState);

						if (mDS_Huali_Western.isEmpty()) {
							return;
						}

						// 判断是否滚动到底部
						boolean scrollEnd = false;
						try {
							if (view.getPositionForView(mLV_Footer_Huali_Western) == view
									.getLastVisiblePosition())
								scrollEnd = true;
						} catch (Exception e) {
							scrollEnd = false;
						}

						if (scrollEnd) {
							// 滚动到底部,加载数据开始
							mVfooter_moreHuali_western
									.setText(R.string.loading);
							mVfooter_progressHuali_western
									.setVisibility(View.VISIBLE);
							mCurCategory = getCategory();
							LoadHuaLiWesternData(
									UIHelper.LISTVIEW_ACTION_SCROLL,
									AppContext.CATEGORY_HUALI_WESTERN);
						}
					}
				});
	}

	protected int getCategory() {
		int id = mViewPager.getCurrentItem();
		switch (id) {
		case 1:
			return AppContext.CATEGORY_HUALI_EAST;
			// break;

		case 0:
			return AppContext.CATEGORY_HUALI_WESTERN;
			// break;
		}
		return mCurCategory;
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
						((AppException) msg.obj).makeToast(HualiActivity.this);
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
		mActionBar.setTitle(R.string.huali);

	}

	private void initHandler() {
		mHuali_eastrnHandler = getHandler(mLvHuali_east, mSA_HuaLi_East,
				mVfooter_moreHuali_eastrn, mVfooter_progressHuali_eastrn);
		mHuali_westernHandler = getHandler(mLvHuali_western, mSA_Huali_Western,
				mVfooter_moreHuali_western, mVfooter_progressHuali_western);

		if (mDS_Huali_Western.isEmpty()) {
			LoadHuaLiEastData(UIHelper.LISTVIEW_ACTION_REFRESH,
					AppContext.CATEGORY_HUALI_WESTERN);
			mSA_Huali_Western.notifyDataSetChanged();
		}

	}

	/**
	 * init pulltorefreshlistview
	 */
	public void initPullToRefreshListView() {
		mSA_Huali_Western = new SimpleInfoAdapter(HualiActivity.this,
				mDS_Huali_Western);
		mSA_HuaLi_East = new SimpleInfoAdapter(HualiActivity.this,
				mDS_HuaLi_East);

		mLvHuali_east = (PullToRefreshListView) mHuali_east
				.findViewById(R.id.huali_east);
		mLvHuali_western = (PullToRefreshListView) mHuali_western
				.findViewById(R.id.huali_western);

		mLV_Footer_Huali_Western = mLayoutInflator.inflate(
				R.layout.listviewfooter, null);
		mLv_Footer_Huali_East = mLayoutInflator.inflate(
				R.layout.listviewfooter, null);
		
		mVfooter_moreHuali_western = (TextView) mLV_Footer_Huali_Western
				.findViewById(R.id.listview_foot_more);
		mVfooter_moreHuali_eastrn = (TextView) mLv_Footer_Huali_East
				.findViewById(R.id.listview_foot_more);
		
		mVfooter_progressHuali_western = (ProgressBar) mLV_Footer_Huali_Western
				.findViewById(R.id.listview_foot_progress);
		mVfooter_progressHuali_eastrn = (ProgressBar) mLv_Footer_Huali_East
				.findViewById(R.id.listview_foot_progress);
		
		mVfooter_progressHuali_eastrn.setVisibility(View.GONE);
		mVfooter_progressHuali_western.setVisibility(View.GONE);
		
		mLvHuali_east.addFooterView(mLv_Footer_Huali_East);
		mLvHuali_western.addFooterView(mLV_Footer_Huali_Western);
		mLvHuali_east.setAdapter(mSA_HuaLi_East);
		mLvHuali_western.setAdapter(mSA_Huali_Western);

		this.addHuaLiWesternListener();
		this.addHuaLiEastListener();
	}

	/**
	 * init viewpage & pagertabstrip
	 */
	public void initViewPageandPagerStrip() {
		mViewPager = (ViewPager) this.findViewById(R.id.vp_huali);
		mPagerTitleStrip = (PagerTitleStrip) this
				.findViewById(R.id.huali_pagertab);
		mPagerTitleStrip.setTextSpacing(50);
		mLayoutInflator = getLayoutInflater();
		mHuali_east = mLayoutInflator.inflate(R.layout.huali_east, null);
		mHuali_western = mLayoutInflator.inflate(R.layout.huali_western, null);
		mViewList.add(mHuali_western);
		mViewList.add(mHuali_east);

		mTitleList.add(getResources().getString(R.string.huali_western));
		mTitleList.add(getResources().getString(R.string.huali_east));

		mMyPageApdater = new MyPageApdater(this, mViewList, mTitleList);
		mViewPager.setAdapter(mMyPageApdater);
	}

	protected void LoadHuaLiEastData(final int action, final int category) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = mHuali_eastrnHandler.obtainMessage();
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
						if (mDS_HuaLi_East.size() > 0) {

							if (action == UIHelper.LISTVIEW_ACTION_REFRESH) {
								pageIndex = mDS_HuaLi_East.get(0).getId() + 1;
							} else if (action == UIHelper.LISTVIEW_ACTION_SCROLL) {
								pageIndex = mDS_HuaLi_East.get(
										mDS_HuaLi_East.size() - 1).getId() - 1;
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
				mHuali_eastrnHandler.sendMessage(msg);
				// }

			}

		}).start();

	}

	protected void LoadHuaLiWesternData(final int action, final int category) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Message msg = mHuali_westernHandler.obtainMessage();
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
						if (mDS_Huali_Western.size() > 0) {

							if (action == UIHelper.LISTVIEW_ACTION_REFRESH) {
								pageIndex = mDS_Huali_Western.get(0).getId() + 1;
							} else if (action == UIHelper.LISTVIEW_ACTION_SCROLL) {
								pageIndex = mDS_Huali_Western.get(
										mDS_Huali_Western.size() - 1).getId() - 1;
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
				mHuali_westernHandler.sendMessage(msg);
				// }

			}

		}).start();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.huali);

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

	protected Notice processDesignData(int what, Object obj, int arg1, int arg2) {
		List<SimpleInfo> datas = (List<SimpleInfo>) obj;
		Notice notice = new Notice();
		int newMessage = 0;
		switch (arg2) {
		case AppContext.CATEGORY_HUALI_EAST:

			if (what > 0) {// 有数据
				// 判断原有数据集中是否存在数据,没有数据，则直接添加。有数据则将数据与存在的数据进行比对，删除重复的数据
				if (!mSA_HuaLi_East.isEmpty()) {
					// 主要是用来提示用户更新了多少条记录
					for (SimpleInfo simpleInfo : mDS_HuaLi_East) {

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
				mDS_HuaLi_East.addAll(0, datas);
			} else if (arg1 == UIHelper.LISTVIEW_ACTION_SCROLL) {// 滚动
				mDS_Huali_Western.addAll(datas);
			}
			datas = null;

			return notice;
		case AppContext.CATEGORY_HUALI_WESTERN:

			if (what > 0) {// 有数据
				// 判断原有数据集中是否存在数据,没有数据，则直接添加。有数据则将数据与存在的数据进行比对，删除重复的数据
				if (!mSA_Huali_Western.isEmpty()) {
					// 主要是用来提示用户更新了多少条记录
					for (SimpleInfo simpleInfo : mDS_Huali_Western) {

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
				mDS_Huali_Western.addAll(0, datas);
			} else if (arg1 == UIHelper.LISTVIEW_ACTION_SCROLL) {// 滚动
				mDS_Huali_Western.addAll(datas);
			}
			datas = null;

			return notice;
		}

		return notice;
	}
}
