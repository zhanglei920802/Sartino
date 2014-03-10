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
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 设计模块
 * 
 * 
 * 
 * @author Administrator
 * 
 */
public class DesignActivity extends BaseActivity implements
		ViewPager.OnPageChangeListener {
	private static final String TAG = "DesignActivity";

	private Context _this = this;
	private ActionBar mActionBar = null;
	private AppContext mAppcontext = null;
	private SimpleInfoAdapter mCurAdapter = null;
	private int mCurCategory = 8;
	private PullToRefreshListView mCurPRL = null;
	private int mDesginDesignerData = 0;
	private int mDesginDisplaySumData = 0;
	private int mDesginInnerSumData = 0;
	private int mDesginLandScapeData = 0;
	private int mDesginMeetingData = 0;
	private int mDesginShowCaseData = 0;
	private int mDesginWeddingData = 0;
	private View mDesignDisplay = null, mDesignLandscape = null,
			mDesignMetting = null, mDesignShowCase = null,
			mDesignWedding = null, mDesginInner = null, mDesginDesigner = null;
	private List<SimpleInfo> mDSDesignCase = new ArrayList<SimpleInfo>();
	private List<SimpleInfo> mDSDesignDesigner = new ArrayList<SimpleInfo>();
	private List<SimpleInfo> mDSDesignDisplay = new ArrayList<SimpleInfo>();

	private List<SimpleInfo> mDSDesignInner = new ArrayList<SimpleInfo>();
	private List<SimpleInfo> mDSDesignLandscape = new ArrayList<SimpleInfo>();
	private List<SimpleInfo> mDSDesignMeeting = new ArrayList<SimpleInfo>();
	private List<SimpleInfo> mDSDesignWedding = new ArrayList<SimpleInfo>();
	private Handler mHDesignCase = null;
	private Handler mHDesignDesigner = null;
	private Handler mHDesignDisplay = null;

	private Handler mHDesignInner = null;
	private Handler mHDesignLandscape = null;
	private Handler mHDesignMeeting = null;
	private Handler mHDesignWedding = null;
	private LayoutInflater mLayoutInflator = null;
	private MyPageApdater mMyPageApdater = null;
	private PagerTitleStrip mPagerTitleStrip = null;

	private PullToRefreshListView mPRLDesignCase = null;
	private PullToRefreshListView mPRLDesignDesigner = null;
	private PullToRefreshListView mPRLDesignDisplay = null;
	private PullToRefreshListView mPRLDesignInner = null;
	private PullToRefreshListView mPRLDesignLandscape = null;
	private PullToRefreshListView mPRLDesignMeeting = null;
	private PullToRefreshListView mPRLDesignWedding = null;

	private SimpleInfoAdapter mSADesignCase = null;
	private SimpleInfoAdapter mSADesignDesigner = null;
	private SimpleInfoAdapter mSADesignDisplay = null;
	private SimpleInfoAdapter mSADesignInner = null;
	private SimpleInfoAdapter mSADesignLandscape = null;
	private SimpleInfoAdapter mSADesignMeeting = null;
	private SimpleInfoAdapter mSADesignWedding = null;
	private List<String> mTitleList = new ArrayList<String>();
	private TextView mVfooter_moreDesignCase = null;
	private TextView mVfooter_moreDesignDesigner = null;
	private TextView mVfooter_moreDesignDisplay = null;
	private TextView mVfooter_moreDesignInner = null;
	private TextView mVfooter_moreDesignLandscape = null;
	private TextView mVfooter_moreDesignMeeting = null;
	private TextView mVfooter_moreDesignWedding = null;
	private ProgressBar mVfooter_progressDesignCase = null;
	private ProgressBar mVfooter_progressDesignDesigner = null;
	private ProgressBar mVfooter_progressDesignDisplay = null;
	private ProgressBar mVfooter_progressDesignInner = null;
	private ProgressBar mVfooter_progressDesignLandscape = null;
	private ProgressBar mVfooter_progressDesignMeeting = null;

	private ProgressBar mVfooter_progressDesignWedding = null;
	private View mVfooterDesignCase = null;
	private View mVfooterDesignDesigner = null;
	private View mVfooterDesignDisplay = null;
	private View mVfooterDesignInner = null;
	private View mVfooterDesignLandscape = null;
	private View mVfooterDesignMeeting = null;

	private View mVfooterDesignWedding = null;
	private List<View> mViewList = new ArrayList<View>();
	private ViewPager mViewPager = null;

	/**
	 * 获取类别
	 */
	private int getCategory() {
		int id = mViewPager.getCurrentItem();
		switch (id) {
		case 0:
			System.out.println("display");

			return AppContext.CATEGORY_DESIGN_DISPLAY;// 美陈
		case 1:
			System.out.println("inner");
			return AppContext.CATEGORY_DESIGN_INNER;// 室内
			// break;
		case 2:
			System.out.println("landscape");
			return AppContext.CATEGORY_DESIGN_LANDSCAPE;// 景观
			// break;

		case 3:
			System.out.println("meeting");
			return AppContext.CATEGORY_DESIGN_MEETING;// 会议
			// break;
		case 4:
			System.out.println("case");
			return AppContext.CATEGORY_DESIGN_SHOWCASE;// 橱窗
			// break;

		case 5:
			System.out.println("wedding");
			return AppContext.CATEGORY_DESIGN_WEDDING;// 婚礼
			// break;
		default:
			System.out.println("designer");
			return AppContext.CATEGORY_DESIGN_DESIGNER;// 设计师
			// break;

		}

	}

	/**
	 * 获取Hanlder
	 * 
	 * @param lv
	 * @param adapter
	 * @param tv
	 * @param progressBar
	 * @return
	 */
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
						((AppException) msg.obj).makeToast(_this);
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
		mActionBar.setTitle(R.string.design);

	}

	/**
	 * 初始化橱窗模块监听器,包括点击,下拉刷新，滚动等
	 */
	public void initDesignCaseListener() {
		mPRLDesignCase.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 点击头部、底部栏无效
				if (position == 0 || view == mVfooterDesignCase)
					return;

				SimpleInfo post = mSADesignCase.getmDatas().get(position);
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", post);
				// 跳转
				UIHelper.showDetailActivity(DesignActivity.this, bundle);
				// 跳转
			}
		});

		mPRLDesignCase.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {// 怎样获取数据呢
				mCurCategory = getCategory();
				LoadDesignCaseData(UIHelper.LISTVIEW_ACTION_REFRESH,
						AppContext.CATEGORY_DESIGN_SHOWCASE);
			}
		});

		mPRLDesignCase.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				mPRLDesignCase.onScroll(view, firstVisibleItem,
						visibleItemCount, totalItemCount);
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				mPRLDesignCase.onScrollStateChanged(view, scrollState);

				if (mDSDesignCase.isEmpty()) {
					return;
				}

				// 判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if (view.getPositionForView(mVfooterDesignCase) == view
							.getLastVisiblePosition())
						scrollEnd = true;
				} catch (Exception e) {
					scrollEnd = false;
				}

				if (scrollEnd) {
					// 滚动到底部,加载数据开始
					mVfooter_moreDesignCase.setText(R.string.loading);
					mVfooter_progressDesignCase.setVisibility(View.VISIBLE);
					mCurCategory = getCategory();
					LoadDesignCaseData(UIHelper.LISTVIEW_ACTION_SCROLL,
							AppContext.CATEGORY_DESIGN_SHOWCASE);
				}

			}
		});
	}

	/**
	 * 初始化设计师监听器,包括点击，下拉,滚动
	 */
	public void initDesignDesignerListener() {
		mPRLDesignDesigner.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 点击头部、底部栏无效
				if (position == 0 || view == mVfooterDesignDesigner)
					return;

				SimpleInfo post = mSADesignDesigner.getmDatas().get(
						position - 1);
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", post);
				// 跳转
				UIHelper.showDetailActivity(DesignActivity.this, bundle);
			}
		});

		mPRLDesignDesigner.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {

				mCurCategory = getCategory();
				LoadDesignDesignerData(UIHelper.LISTVIEW_ACTION_REFRESH,
						AppContext.CATEGORY_DESIGN_DESIGNER);

			}
		});

		mPRLDesignDesigner
				.setOnScrollListener(new AbsListView.OnScrollListener() {

					@Override
					public void onScroll(AbsListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {
						mPRLDesignDesigner.onScroll(view, firstVisibleItem,
								visibleItemCount, totalItemCount);
					}

					@Override
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						mPRLDesignDesigner.onScrollStateChanged(view,
								scrollState);

						if (mDSDesignDesigner.isEmpty()) {
							return;
						}

						// 判断是否滚动到底部
						boolean scrollEnd = false;
						try {
							if (view.getPositionForView(mVfooterDesignDesigner) == view
									.getLastVisiblePosition())
								scrollEnd = true;
						} catch (Exception e) {
							scrollEnd = false;
						}

						if (scrollEnd) {
							// 滚动到底部,加载数据开始
							mVfooter_moreDesignDesigner
									.setText(R.string.loading);
							mVfooter_progressDesignDesigner
									.setVisibility(View.VISIBLE);
							mCurCategory = getCategory();
							LoadDesignDesignerData(
									UIHelper.LISTVIEW_ACTION_SCROLL,
									AppContext.CATEGORY_DESIGN_DESIGNER);
						}

					}
				});
	}

	/**
	 * 初始化美陈设计模块，包括点击,下拉刷新，滚动等
	 */
	public void initDesignDisplayListener() {
		mPRLDesignDisplay.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 点击头部、底部栏无效
				if (position == 0 || view == mVfooterDesignDisplay)
					return;

				SimpleInfo post = mSADesignDisplay.getmDatas()
						.get(position - 1);
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", post);
				// 跳转
				UIHelper.showDetailActivity(DesignActivity.this, bundle);

			}
		});

		mPRLDesignDisplay.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				mCurCategory = getCategory();
				LoadDesignDisplayData(UIHelper.LISTVIEW_ACTION_REFRESH,
						AppContext.CATEGORY_DESIGN_DISPLAY);

			}
		});

		mPRLDesignDisplay
				.setOnScrollListener(new AbsListView.OnScrollListener() {

					@Override
					public void onScroll(AbsListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {
						mPRLDesignDisplay.onScroll(view, firstVisibleItem,
								visibleItemCount, totalItemCount);
					}

					@Override
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						mPRLDesignDisplay.onScrollStateChanged(view,
								scrollState);

						if (mDSDesignDisplay.isEmpty()) {
							return;
						}

						// 判断是否滚动到底部
						boolean scrollEnd = false;
						try {
							if (view.getPositionForView(mVfooterDesignDisplay) == view
									.getLastVisiblePosition())
								scrollEnd = true;
						} catch (Exception e) {
							scrollEnd = false;
						}

						if (scrollEnd) {
							// 滚动到底部,加载数据开始
							mVfooter_moreDesignDisplay
									.setText(R.string.loading);
							mVfooter_progressDesignDisplay
									.setVisibility(View.VISIBLE);
							mCurCategory = getCategory();
							LoadDesignDisplayData(
									UIHelper.LISTVIEW_ACTION_SCROLL,
									AppContext.CATEGORY_DESIGN_DISPLAY);
						}
					}
				});
	}

	/**
	 * 初始化室内模块，包括下拉，点击，刷新等等
	 */
	public void initDesignInnerListener() {
		mPRLDesignInner.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// 点击头部、底部栏无效
				if (position == 0 || view == mVfooterDesignInner)
					return;
				SimpleInfo post = mSADesignInner.getmDatas().get(position - 1);
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", post);
				// 跳转
				UIHelper.showDetailActivity(DesignActivity.this, bundle);

			}
		});

		mPRLDesignInner.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				mCurCategory = getCategory();
				LoadDesignInnerData(UIHelper.LISTVIEW_ACTION_REFRESH,
						AppContext.CATEGORY_DESIGN_INNER);
			}
		});

		mPRLDesignInner.setOnScrollListener(new AbsListView.OnScrollListener() {

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				mPRLDesignInner.onScroll(view, firstVisibleItem,
						visibleItemCount, totalItemCount);

			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				mPRLDesignInner.onScrollStateChanged(view, scrollState);

				if (mDSDesignInner.isEmpty()) {
					return;
				}

				// 判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if (view.getPositionForView(mVfooterDesignInner) == view
							.getLastVisiblePosition())
						scrollEnd = true;
				} catch (Exception e) {
					scrollEnd = false;
				}

				if (scrollEnd) {
					// 滚动到底部,加载数据开始
					mVfooter_moreDesignInner.setText(R.string.loading);
					mVfooter_progressDesignInner.setVisibility(View.VISIBLE);
					mCurCategory = getCategory();

					LoadDesignInnerData(UIHelper.LISTVIEW_ACTION_SCROLL,
							AppContext.CATEGORY_DESIGN_INNER);
				}

			}
		});
	}

	/**
	 * 初始化景观模块监听器，包括点击，下拉刷新，滚动等
	 */
	public void initDesignLandscapeListener() {
		mPRLDesignLandscape.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 点击头部、底部栏无效
				if (position == 0 || view == mVfooterDesignDesigner)
					return;

				SimpleInfo post = mSADesignLandscape.getmDatas().get(
						position - 1);
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", post);
				// 跳转
				UIHelper.showDetailActivity(DesignActivity.this, bundle);

				// 跳转
			}
		});

		mPRLDesignLandscape.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				mCurCategory = getCategory();
				LoadDesignLandscapeData(UIHelper.LISTVIEW_ACTION_REFRESH,
						AppContext.CATEGORY_DESIGN_LANDSCAPE);
			}
		});

		mPRLDesignLandscape
				.setOnScrollListener(new AbsListView.OnScrollListener() {

					@Override
					public void onScroll(AbsListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {
						mPRLDesignLandscape.onScroll(view, firstVisibleItem,
								visibleItemCount, totalItemCount);
					}

					@Override
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						mPRLDesignLandscape.onScrollStateChanged(view,
								scrollState);

						if (mDSDesignLandscape.isEmpty()) {
							return;
						}

						// 判断是否滚动到底部
						boolean scrollEnd = false;
						try {
							if (view.getPositionForView(mVfooterDesignLandscape) == view
									.getLastVisiblePosition())
								scrollEnd = true;
						} catch (Exception e) {
							scrollEnd = false;
						}

						if (scrollEnd) {
							// 滚动到底部,加载数据开始
							mVfooter_moreDesignLandscape
									.setText(R.string.loading);
							mVfooter_progressDesignLandscape
									.setVisibility(View.VISIBLE);
							mCurCategory = getCategory();
							LoadDesignLandscapeData(
									UIHelper.LISTVIEW_ACTION_SCROLL,
									AppContext.CATEGORY_DESIGN_LANDSCAPE);
						}
					}
				});
	}

	/**
	 * 初始化会议模块，包括下拉，点击，刷新等等
	 */
	public void initDesignMeetingListener() {
		mPRLDesignMeeting.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 点击头部、底部栏无效
				if (position == 0 || view == mVfooterDesignMeeting)
					return;

				SimpleInfo post = mSADesignMeeting.getmDatas()
						.get(position - 1);
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", post);
				// 跳转
				UIHelper.showDetailActivity(DesignActivity.this, bundle);
			}
		});

		mPRLDesignMeeting.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {// 下拉刷新
				mCurCategory = getCategory();
				LoadDesignMeetingData(UIHelper.LISTVIEW_ACTION_REFRESH,
						AppContext.CATEGORY_DESIGN_MEETING);
			}
		});

		mPRLDesignMeeting
				.setOnScrollListener(new AbsListView.OnScrollListener() {

					@Override
					public void onScroll(AbsListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {
						mPRLDesignMeeting.onScroll(view, firstVisibleItem,
								visibleItemCount, totalItemCount);
					}

					@Override
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						mPRLDesignMeeting.onScrollStateChanged(view,
								scrollState);

						if (mDSDesignMeeting.isEmpty()) {
							return;
						}

						// 判断是否滚动到底部
						boolean scrollEnd = false;
						try {
							if (view.getPositionForView(mVfooterDesignMeeting) == view
									.getLastVisiblePosition())
								scrollEnd = true;
						} catch (Exception e) {
							scrollEnd = false;
						}

						if (scrollEnd) {
							// 滚动到底部,加载数据开始
							mVfooter_moreDesignMeeting
									.setText(R.string.loading);
							mVfooter_progressDesignMeeting
									.setVisibility(View.VISIBLE);
							mCurCategory = getCategory();
							LoadDesignMeetingData(
									UIHelper.LISTVIEW_ACTION_SCROLL,
									AppContext.CATEGORY_DESIGN_MEETING);
						}
					}
				});
	}

	/**
	 * 初始化婚礼模块，包括下拉点击刷新等
	 */
	public void initDesignWeddingListener() {
		mPRLDesignWedding.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 点击头部、底部栏无效
				if (position == 0 || view == mVfooterDesignWedding)
					return;
				SimpleInfo post = mSADesignWedding.getmDatas()
						.get(position - 1);
				Bundle bundle = new Bundle();
				bundle.putSerializable("data", post);
				// 跳转
				UIHelper.showDetailActivity(DesignActivity.this, bundle);
			}
		});

		mPRLDesignWedding.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				mCurCategory = getCategory();
				LoadDesignWeddingData(UIHelper.LISTVIEW_ACTION_REFRESH,
						AppContext.CATEGORY_DESIGN_WEDDING);
			}
		});

		mPRLDesignWedding
				.setOnScrollListener(new AbsListView.OnScrollListener() {

					@Override
					public void onScroll(AbsListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {
						mPRLDesignWedding.onScroll(view, firstVisibleItem,
								visibleItemCount, totalItemCount);
					}

					@Override
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						mPRLDesignWedding.onScrollStateChanged(view,
								scrollState);

						if (mDSDesignWedding.isEmpty()) {
							return;
						}

						// 判断是否滚动到底部
						boolean scrollEnd = false;
						try {
							if (view.getPositionForView(mVfooterDesignWedding) == view
									.getLastVisiblePosition())
								scrollEnd = true;
						} catch (Exception e) {
							scrollEnd = false;
						}

						if (scrollEnd) {
							// 滚动到底部,加载数据开始
							mVfooter_moreDesignWedding
									.setText(R.string.loading);
							mVfooter_progressDesignWedding
									.setVisibility(View.VISIBLE);
							mCurCategory = getCategory();
							LoadDesignWeddingData(
									UIHelper.LISTVIEW_ACTION_SCROLL,
									AppContext.CATEGORY_DESIGN_WEDDING);
						}

					}
				});
	}

	/**
	 * 初始化Handler
	 */
	private void initHandler() {
		mHDesignCase = getHandler(mPRLDesignCase, mSADesignCase,
				mVfooter_moreDesignCase, mVfooter_progressDesignCase);
		mHDesignDesigner = getHandler(mPRLDesignDesigner, mSADesignDesigner,
				mVfooter_moreDesignDesigner, mVfooter_progressDesignDesigner);
		mHDesignDisplay = getHandler(mPRLDesignDisplay, mSADesignDisplay,
				mVfooter_moreDesignDisplay, mVfooter_progressDesignDisplay);
		mHDesignInner = getHandler(mPRLDesignInner, mSADesignInner,
				mVfooter_moreDesignInner, mVfooter_progressDesignInner);
		mHDesignLandscape = getHandler(mPRLDesignLandscape, mSADesignLandscape,
				mVfooter_moreDesignLandscape, mVfooter_progressDesignLandscape);
		mHDesignMeeting = getHandler(mPRLDesignMeeting, mSADesignMeeting,
				mVfooter_moreDesignMeeting, mVfooter_progressDesignMeeting);
		mHDesignWedding = getHandler(mPRLDesignWedding, mSADesignWedding,
				mVfooter_moreDesignWedding, mVfooter_progressDesignWedding);

		if (mDSDesignDisplay.isEmpty()) {
			LoadDesignDisplayData(UIHelper.LISTVIEW_ACTION_REFRESH,
					AppContext.CATEGORY_DESIGN_DISPLAY);
		}

		// LoadDesignInnerData(UIHelper.LISTVIEW_ACTION_REFRESH,
		// AppContext.CATEGORY_DESIGN_INNER);
		// LoadDesignLandscapeData(UIHelper.LISTVIEW_ACTION_REFRESH,
		// AppContext.CATEGORY_DESIGN_LANDSCAPE);
		// LoadDesignMeetingData(UIHelper.LISTVIEW_ACTION_REFRESH,
		// AppContext.CATEGORY_DESIGN_MEETING);
		// LoadDesignCaseData(UIHelper.LISTVIEW_ACTION_REFRESH,
		// AppContext.CATEGORY_DESIGN_SHOWCASE);
		// LoadDesignWeddingData(UIHelper.LISTVIEW_ACTION_REFRESH,
		// AppContext.CATEGORY_DESIGN_WEDDING);
		// LoadDesignDesignerData(UIHelper.LISTVIEW_ACTION_REFRESH,
		// AppContext.CATEGORY_DESIGN_DESIGNER);
	}

	/**
	 * init pulltorefreshlistview
	 */
	public void initPullToRefreshListView() {

		mSADesignCase = new SimpleInfoAdapter(_this, mDSDesignCase);
		mSADesignDisplay = new SimpleInfoAdapter(_this, mDSDesignDisplay);
		mSADesignInner = new SimpleInfoAdapter(_this, mDSDesignInner);
		mSADesignLandscape = new SimpleInfoAdapter(_this, mDSDesignLandscape);
		mSADesignMeeting = new SimpleInfoAdapter(_this, mDSDesignMeeting);
		mSADesignWedding = new SimpleInfoAdapter(_this, mDSDesignWedding);
		mSADesignDesigner = new SimpleInfoAdapter(_this, mDSDesignDesigner);

		// addTestData();

		if (mLayoutInflator == null) {
			mLayoutInflator = getLayoutInflater();
		}
		mVfooterDesignCase = mLayoutInflator.inflate(R.layout.listviewfooter,
				null);
		mVfooter_moreDesignCase = (TextView) mVfooterDesignCase
				.findViewById(R.id.listview_foot_more);
		mVfooter_progressDesignCase = (ProgressBar) mVfooterDesignCase
				.findViewById(R.id.listview_foot_progress);

		mVfooter_progressDesignCase.setTag("mVfooter_progressDesignCase");
		mVfooter_progressDesignCase.setVisibility(View.GONE);

		mVfooterDesignDesigner = mLayoutInflator.inflate(
				R.layout.listviewfooter, null);
		mVfooter_moreDesignDesigner = (TextView) mVfooterDesignDesigner
				.findViewById(R.id.listview_foot_more);
		mVfooter_progressDesignDesigner = (ProgressBar) mVfooterDesignDesigner
				.findViewById(R.id.listview_foot_progress);
		mVfooter_progressDesignDesigner
				.setTag("mVfooter_progressDesignDesigner");
		mVfooter_progressDesignDesigner.setVisibility(View.GONE);

		mVfooterDesignDisplay = mLayoutInflator.inflate(
				R.layout.listviewfooter, null);
		mVfooter_moreDesignDisplay = (TextView) mVfooterDesignDisplay
				.findViewById(R.id.listview_foot_more);
		mVfooter_progressDesignDisplay = (ProgressBar) mVfooterDesignDisplay
				.findViewById(R.id.listview_foot_progress);
		mVfooter_progressDesignDisplay.setTag("mVfooter_progressDesignDisplay");
		mVfooter_progressDesignDisplay.setVisibility(View.GONE);

		mVfooterDesignInner = mLayoutInflator.inflate(R.layout.listviewfooter,
				null);
		mVfooter_moreDesignInner = (TextView) mVfooterDesignInner
				.findViewById(R.id.listview_foot_more);
		mVfooter_progressDesignInner = (ProgressBar) mVfooterDesignInner
				.findViewById(R.id.listview_foot_progress);
		mVfooter_progressDesignInner.setTag("mVfooter_progressDesignInner");
		mVfooter_progressDesignInner.setVisibility(View.GONE);

		mVfooterDesignLandscape = mLayoutInflator.inflate(
				R.layout.listviewfooter, null);
		mVfooter_moreDesignLandscape = (TextView) mVfooterDesignLandscape
				.findViewById(R.id.listview_foot_more);
		mVfooter_progressDesignLandscape = (ProgressBar) mVfooterDesignLandscape
				.findViewById(R.id.listview_foot_progress);
		mVfooter_progressDesignLandscape
				.setTag("mVfooter_progressDesignLandscape");
		mVfooter_progressDesignLandscape.setVisibility(View.GONE);

		mVfooterDesignMeeting = mLayoutInflator.inflate(
				R.layout.listviewfooter, null);
		mVfooter_moreDesignMeeting = (TextView) mVfooterDesignMeeting
				.findViewById(R.id.listview_foot_more);
		mVfooter_progressDesignMeeting = (ProgressBar) mVfooterDesignMeeting
				.findViewById(R.id.listview_foot_progress);
		mVfooter_progressDesignMeeting.setTag("mVfooter_progressDesignMeeting");
		mVfooter_progressDesignMeeting.setVisibility(View.GONE);

		mVfooterDesignWedding = mLayoutInflator.inflate(
				R.layout.listviewfooter, null);
		mVfooter_moreDesignWedding = (TextView) mVfooterDesignWedding
				.findViewById(R.id.listview_foot_more);
		mVfooter_progressDesignWedding = (ProgressBar) mVfooterDesignWedding
				.findViewById(R.id.listview_foot_progress);
		mVfooter_progressDesignWedding.setTag("mVfooter_progressDesignWedding");
		mVfooter_progressDesignWedding.setVisibility(View.GONE);

		mPRLDesignCase = (PullToRefreshListView) mDesignShowCase
				.findViewById(R.id.design_showcase);
		mPRLDesignDisplay = (PullToRefreshListView) mDesignDisplay
				.findViewById(R.id.desgin_display);
		mPRLDesignInner = (PullToRefreshListView) mDesginInner
				.findViewById(R.id.design_inner);
		mPRLDesignLandscape = (PullToRefreshListView) mDesignLandscape
				.findViewById(R.id.design_landscape);
		mPRLDesignMeeting = (PullToRefreshListView) mDesignMetting
				.findViewById(R.id.design_meetings);
		mPRLDesignWedding = (PullToRefreshListView) mDesignWedding
				.findViewById(R.id.design_wedding);
		mPRLDesignDesigner = (PullToRefreshListView) mDesginDesigner
				.findViewById(R.id.design_designer);

		mPRLDesignCase.addFooterView(mVfooterDesignCase);
		mPRLDesignDisplay.addFooterView(mVfooterDesignDisplay);
		mPRLDesignLandscape.addFooterView(mVfooterDesignLandscape);
		mPRLDesignInner.addFooterView(mVfooterDesignInner);
		mPRLDesignMeeting.addFooterView(mVfooterDesignMeeting);
		mPRLDesignWedding.addFooterView(mVfooterDesignWedding);
		mPRLDesignDesigner.addFooterView(mVfooterDesignDesigner);

		mPRLDesignCase.setAdapter(mSADesignCase);
		mPRLDesignDisplay.setAdapter(mSADesignDisplay);
		mPRLDesignInner.setAdapter(mSADesignInner);
		mPRLDesignLandscape.setAdapter(mSADesignLandscape);
		mPRLDesignMeeting.setAdapter(mSADesignMeeting);
		mPRLDesignWedding.setAdapter(mSADesignWedding);
		mPRLDesignDesigner.setAdapter(mSADesignDesigner);

		this.initDesignCaseListener();
		this.initDesignDisplayListener();
		this.initDesignInnerListener();
		this.initDesignLandscapeListener();
		this.initDesignMeetingListener();
		this.initDesignWeddingListener();
		this.initDesignDesignerListener();
	}

	/**
	 * init viewpage & pagertabstrip
	 */
	public void initViewPageandPagerStrip() {
		mViewPager = (ViewPager) this.findViewById(R.id.vp_design);
		mViewPager.setOnPageChangeListener(this);
		mPagerTitleStrip = (PagerTitleStrip) this
				.findViewById(R.id.design_pagertab);
		mPagerTitleStrip.setTextSpacing(50);
		mLayoutInflator = getLayoutInflater();

		mDesignDisplay = mLayoutInflator.inflate(R.layout.design_display, null);
		mDesignLandscape = mLayoutInflator.inflate(R.layout.design_landscape,
				null);
		mDesignMetting = mLayoutInflator.inflate(R.layout.design_meeting, null);
		mDesignShowCase = mLayoutInflator.inflate(R.layout.design_showcase,
				null);
		mDesignWedding = mLayoutInflator.inflate(R.layout.design_wedding, null);
		mDesginInner = mLayoutInflator.inflate(R.layout.design_inner, null);
		mDesginDesigner = mLayoutInflator.inflate(R.layout.design_designer,
				null);

		mViewList.add(mDesignDisplay);
		mViewList.add(mDesginInner);
		mViewList.add(mDesignLandscape);
		mViewList.add(mDesignMetting);
		mViewList.add(mDesignShowCase);
		mViewList.add(mDesignWedding);
		mViewList.add(mDesginDesigner);

		mTitleList.add(getResources().getString(R.string.design_display));
		mTitleList.add(getResources().getString(R.string.design_inner));
		mTitleList.add(getResources().getString(R.string.design_landscape));
		mTitleList.add(getResources().getString(R.string.design_meeting));
		mTitleList.add(getResources().getString(R.string.design_showcase));
		mTitleList.add(getResources().getString(R.string.design_wedding));
		mTitleList.add(getResources().getString(R.string.design_designer));

		mMyPageApdater = new MyPageApdater(this, mViewList, mTitleList);
		mViewPager.setAdapter(mMyPageApdater);
	}

	/**
	 * 获取展示数据
	 * 
	 * @param action
	 * @param category
	 */
	public void LoadDesignCaseData(final int action, final int category) {
		mVfooter_progressDesignCase.setVisibility(View.VISIBLE);
		new Thread(new Runnable() {// 数据加载

					@Override
					public void run() {

						Message msg = mHDesignCase.obtainMessage();
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
								if (mDSDesignCase.size() > 0) {

									if (action == UIHelper.LISTVIEW_ACTION_REFRESH) {
										pageIndex = mDSDesignCase.get(0)
												.getId() + 1;
									} else if (action == UIHelper.LISTVIEW_ACTION_SCROLL) {
										pageIndex = mDSDesignCase.get(
												mDSDesignCase.size() - 1)
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

						}
						msg.arg1 = action;
						msg.arg2 = category;

						// if (mViewPager.getCurrentItem() == category) {// 数据发送
						mHDesignCase.sendMessage(msg);
						// }

					}
				}).start();
	}

	/**
	 * 获取设计师数据
	 * 
	 * @param action
	 * @param category
	 */
	public void LoadDesignDesignerData(final int action, final int category) {
		mVfooter_progressDesignDesigner.setVisibility(View.VISIBLE);
		new Thread(new Runnable() {// 数据加载

					@Override
					public void run() {

						Message msg = mHDesignDesigner.obtainMessage();
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
								if (mDSDesignDesigner.size() > 0) {

									if (action == UIHelper.LISTVIEW_ACTION_REFRESH) {
										pageIndex = mDSDesignDesigner.get(0)
												.getId() + 1;
									} else if (action == UIHelper.LISTVIEW_ACTION_SCROLL) {
										pageIndex = mDSDesignDesigner.get(
												mDSDesignDesigner.size() - 1)
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

						}
						msg.arg1 = action;
						msg.arg2 = category;

						// if (mViewPager.getCurrentItem() == category) {// 数据发送
						mHDesignDesigner.sendMessage(msg);
						// }

					}
				}).start();
	}

	/**
	 * 获取美陈数据
	 * 
	 * @param action
	 * @param category
	 */
	public void LoadDesignDisplayData(final int action, final int category) {
		mVfooter_progressDesignDisplay.setVisibility(View.VISIBLE);
		new Thread(new Runnable() {// 数据加载

					@Override
					public void run() {

						Message msg = mHDesignDisplay.obtainMessage();
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
								if (mDSDesignDisplay.size() > 0) {

									if (action == UIHelper.LISTVIEW_ACTION_REFRESH) {
										pageIndex = mDSDesignDisplay.get(0)
												.getId() + 1;
									} else if (action == UIHelper.LISTVIEW_ACTION_SCROLL) {
										pageIndex = mDSDesignDisplay.get(
												mDSDesignDisplay.size() - 1)
												.getId() - 1;
										System.out.println("display"
												+ pageIndex);
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

						}
						msg.arg1 = action;
						msg.arg2 = category;

						// if (mViewPager.getCurrentItem() == category) {// 数据发送
						mHDesignDisplay.sendMessage(msg);
						// }

					}
				}).start();
	}

	/**
	 * 获取室内数据
	 * 
	 * @param action
	 * @param category
	 */
	public void LoadDesignInnerData(final int action, final int category) {
		mVfooter_progressDesignInner.setVisibility(View.VISIBLE);
		new Thread(new Runnable() {// 数据加载

					@Override
					public void run() {

						Message msg = mHDesignInner.obtainMessage();
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
								if (mDSDesignInner.size() > 0) {

									if (action == UIHelper.LISTVIEW_ACTION_REFRESH) {
										pageIndex = mDSDesignInner.get(0)
												.getId() + 1;
									} else if (action == UIHelper.LISTVIEW_ACTION_SCROLL) {
										pageIndex = mDSDesignInner.get(
												mDSDesignInner.size() - 1)
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

						}
						msg.arg1 = action;
						msg.arg2 = category;

						// if (mViewPager.getCurrentItem() == category) {// 数据发送
						mHDesignInner.sendMessage(msg);
						// }

					}
				}).start();
	}

	/**
	 * 获取景观数据
	 * 
	 * @param action
	 * @param category
	 */
	public void LoadDesignLandscapeData(final int action, final int category) {
		mVfooter_progressDesignLandscape.setVisibility(View.VISIBLE);
		new Thread(new Runnable() {// 数据加载

					@Override
					public void run() {

						Message msg = mHDesignLandscape.obtainMessage();
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
								if (mDSDesignLandscape.size() > 0) {

									if (action == UIHelper.LISTVIEW_ACTION_REFRESH) {
										pageIndex = mDSDesignLandscape.get(0)
												.getId() + 1;
									} else if (action == UIHelper.LISTVIEW_ACTION_SCROLL) {
										pageIndex = mDSDesignLandscape.get(
												mDSDesignLandscape.size() - 1)
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

						}
						msg.arg1 = action;
						msg.arg2 = category;

						// if (mViewPager.getCurrentItem() == category) {// 数据发送
						mHDesignLandscape.sendMessage(msg);
						// }

					}
				}).start();
	}

	/**
	 * 获取会议数据
	 * 
	 * @param action
	 * @param category
	 */
	public void LoadDesignMeetingData(final int action, final int category) {
		mVfooter_progressDesignMeeting.setVisibility(View.VISIBLE);
		new Thread(new Runnable() {// 数据加载

					@Override
					public void run() {

						Message msg = mHDesignMeeting.obtainMessage();
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
								if (mDSDesignMeeting.size() > 0) {

									if (action == UIHelper.LISTVIEW_ACTION_REFRESH) {
										pageIndex = mDSDesignMeeting.get(0)
												.getId() + 1;
									} else if (action == UIHelper.LISTVIEW_ACTION_SCROLL) {
										pageIndex = mDSDesignMeeting.get(
												mDSDesignMeeting.size() - 1)
												.getId() - 1;
										System.out.println("Meetings"
												+ pageIndex);
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

						}
						msg.arg1 = action;
						msg.arg2 = category;

						// if (mViewPager.getCurrentItem() == category) {// 数据发送
						mHDesignMeeting.sendMessage(msg);
						// }

					}
				}).start();
	}

	/**
	 * 获取婚庆数据
	 * 
	 * @param action
	 * @param category
	 */
	public void LoadDesignWeddingData(final int action, final int category) {
		mVfooter_progressDesignWedding.setVisibility(View.VISIBLE);
		new Thread(new Runnable() {// 数据加载

					@Override
					public void run() {

						Message msg = mHDesignWedding.obtainMessage();
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
								if (mDSDesignWedding.size() > 0) {

									if (action == UIHelper.LISTVIEW_ACTION_REFRESH) {
										pageIndex = mDSDesignWedding.get(0)
												.getId() + 1;
									} else if (action == UIHelper.LISTVIEW_ACTION_SCROLL) {
										pageIndex = mDSDesignWedding.get(
												mDSDesignWedding.size() - 1)
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

						}
						msg.arg1 = action;
						msg.arg2 = category;

						// if (mViewPager.getCurrentItem() == category) {// 数据发送
						mHDesignWedding.sendMessage(msg);
						// }

					}
				}).start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("DesignActivity.onCreate()");
		super.onCreate(savedInstanceState);
//		AppManager.getAppManager().addActivity(this);
		setContentView(R.layout.design);

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
	protected void onDestroy() {
		super.onDestroy();
//		AppManager.getAppManager().finishActivity(this);
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

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageSelected(int arg0) {

	}

	@Override
	protected void onRestart() {
		System.out.println("DesignActivity.onRestart()");
		super.onRestart();

	}

	@Override
	protected void onPause() {
		System.out.println("DesignActivity.onPause()");
		super.onPause();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {

		// if (savedInstanceState != null) {
		// System.out.println("获取保存数据"+(List<SimpleInfo>) savedInstanceState
		// .getSerializable("data"));
		// mDSDesignInner.clear();
		// mDSDesignInner.addAll((List<SimpleInfo>) savedInstanceState
		// .getSerializable("data"));
		// mSADesignInner.notifyDataSetChanged();
		//
		// }
		System.out.println("DesignActivity.onRestoreInstanceState()");
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onResume() {
		System.out.println("DesignActivity.onResume()");
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		System.out.println("DesignActivity.onSaveInstanceState()");
		// DesignDataSave data = new DesignDataSave();
		// data.setmInnerData(mDSDesignInner);
		// System.out.println("DesignActivity.onSaveInstanceState()"+data.getmInnerData().toString());
		// outState.putSerializable("data", data);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onStart() {
		System.out.println("DesignActivity.onStart()"
				+ mDSDesignInner.toString());
		super.onStart();
	}

	@Override
	protected void onStop() {
		System.out.println("DesignActivity.onStop()");
		super.onStop();
	}

	/**
	 * 用于处理从子线程发送过来的数据
	 * 
	 * @param what
	 *            数据大小
	 * @param obj
	 *            数据
	 * @param arg1
	 *            action
	 * @param arg2
	 *            类别
	 * @return Notice 通知实体类
	 * 
	 */
	protected Notice processDesignData(int what, Object obj, int arg1, int arg2) {
		List<SimpleInfo> datas = (List<SimpleInfo>) obj;
		Notice notice = new Notice();
		int newMessage = 0;
		switch (arg2) {
		case AppContext.CATEGORY_DESIGN_DESIGNER:

			if (what > 0) {// 有数据
				// 判断原有数据集中是否存在数据,没有数据，则直接添加。有数据则将数据与存在的数据进行比对，删除重复的数据
				if (!mDSDesignDesigner.isEmpty()) {
					// 主要是用来提示用户更新了多少条记录
					for (SimpleInfo simpleInfo : mDSDesignDesigner) {

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
				mDSDesignDesigner.addAll(0, datas);
			} else if (arg1 == UIHelper.LISTVIEW_ACTION_SCROLL) {// 滚动
				mDSDesignDesigner.addAll(datas);
			}
			datas = null;

			return notice;
			// break;

		case AppContext.CATEGORY_DESIGN_DISPLAY:
			if (what > 0) {// 有数据
				// 判断原有数据集中是否存在数据,没有数据，则直接添加。有数据则将数据与存在的数据进行比对，删除重复的数据
				if (!mDSDesignDesigner.isEmpty()) {
					for (SimpleInfo simpleInfo : mDSDesignDisplay) {

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

			}

			notice.setmNewMessage(newMessage);

			if (arg1 == UIHelper.LISTVIEW_ACTION_REFRESH) {// 刷新
				mDSDesignDisplay.addAll(0, datas);
			} else if (arg1 == UIHelper.LISTVIEW_ACTION_SCROLL) {// 滚动
				mDSDesignDisplay.addAll(datas);
			}

			datas = null;

			return notice;

		case AppContext.CATEGORY_DESIGN_INNER:
			if (what > 0) {// 有数据
				// 判断原有数据集中是否存在数据,没有数据，则直接添加。有数据则将数据与存在的数据进行比对，删除重复的数据
				if (!mDSDesignInner.isEmpty()) {
					for (SimpleInfo simpleInfo : mDSDesignInner) {

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

			}
			notice.setmNewMessage(newMessage);
			if (arg1 == UIHelper.LISTVIEW_ACTION_REFRESH) {// 刷新
				mDSDesignInner.addAll(0, datas);
			} else if (arg1 == UIHelper.LISTVIEW_ACTION_SCROLL) {// 滚动
				mDSDesignInner.addAll(datas);
			}
			datas = null;

			return notice;

		case AppContext.CATEGORY_DESIGN_LANDSCAPE:
			if (what > 0) {// 有数据
				// 判断原有数据集中是否存在数据,没有数据，则直接添加。有数据则将数据与存在的数据进行比对，删除重复的数据
				if (!mDSDesignLandscape.isEmpty()) {
					for (SimpleInfo simpleInfo : mDSDesignLandscape) {

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

			}
			notice.setmNewMessage(newMessage);
			if (arg1 == UIHelper.LISTVIEW_ACTION_REFRESH) {// 刷新
				mDSDesignLandscape.addAll(0, datas);
			} else if (arg1 == UIHelper.LISTVIEW_ACTION_SCROLL) {// 滚动
				mDSDesignLandscape.addAll(datas);
			}
			datas = null;

			return notice;

		case AppContext.CATEGORY_DESIGN_MEETING:
			if (what > 0) {// 有数据
				// 判断原有数据集中是否存在数据,没有数据，则直接添加。有数据则将数据与存在的数据进行比对，删除重复的数据
				if (!mDSDesignWedding.isEmpty()) {
					for (SimpleInfo simpleInfo : mDSDesignWedding) {

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

			}
			notice.setmNewMessage(newMessage);
			// mDSDesignWedding.clear();
			if (arg1 == UIHelper.LISTVIEW_ACTION_REFRESH) {// 刷新
				mDSDesignMeeting.addAll(0, datas);
			} else if (arg1 == UIHelper.LISTVIEW_ACTION_SCROLL) {// 滚动
				mDSDesignMeeting.addAll(datas);
			}
			datas = null;

			return notice;

		case AppContext.CATEGORY_DESIGN_SHOWCASE:
			if (what > 0) {// 有数据
				// 判断原有数据集中是否存在数据,没有数据，则直接添加。有数据则将数据与存在的数据进行比对，删除重复的数据
				if (!mDSDesignCase.isEmpty()) {
					for (SimpleInfo simpleInfo : mDSDesignCase) {

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

			}
			notice.setmNewMessage(newMessage);
			// mDSDesignCase.clear();
			if (arg1 == UIHelper.LISTVIEW_ACTION_REFRESH) {// 刷新
				mDSDesignCase.addAll(0, datas);
			} else if (arg1 == UIHelper.LISTVIEW_ACTION_SCROLL) {// 滚动
				mDSDesignCase.addAll(datas);
			}
			datas = null;

			return notice;

		case AppContext.CATEGORY_DESIGN_WEDDING:

			if (what > 0) {// 有数据
				// 判断原有数据集中是否存在数据,没有数据，则直接添加。有数据则将数据与存在的数据进行比对，删除重复的数据
				if (!mDSDesignWedding.isEmpty()) {
					for (SimpleInfo simpleInfo : mDSDesignWedding) {

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

			}
			notice.setmNewMessage(newMessage);
			// mDSDesignWedding.clear();
			if (arg1 == UIHelper.LISTVIEW_ACTION_REFRESH) {// 刷新
				mDSDesignWedding.addAll(0, datas);
			} else if (arg1 == UIHelper.LISTVIEW_ACTION_SCROLL) {// 滚动
				mDSDesignWedding.addAll(datas);
			}
			datas = null;

			return notice;
		}
		return notice;

	}

}
