package com.sartino.huayi.app.ui;

import java.util.ArrayList;
import java.util.List;

import com.sartino.huayi.app.R;
import com.sartino.huayi.app.ui.adapter.MyPageApdater;
import com.sartino.huayi.app.ui.widgets.PullToRefreshListView;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class BrandActivity extends BaseActivity {
	private static final String TAG = "BrandActivity";

	private ActionBar mActionBar = null;
	private ViewPager mViewPager = null;
	private PagerTitleStrip mPagerTitleStrip = null;
	private List<View> mViewList = new ArrayList<View>();
	private List<String> mTitleList = new ArrayList<String>();
	private LayoutInflater mLayoutInflator = null;
	private View mBrandOverview = null, mBrandCulture = null;
	private MyPageApdater mMyPageApdater = null;
	private PullToRefreshListView mLvBrandOverview = null;
	private PullToRefreshListView mLvBrandCulture = null;
	private Handler mBrandOverviewHandler = null;
	private Handler mBrandCultureHandler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.brand);

		this.initActionBar();
		this.initViewPageandPagerStrip();

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
		mActionBar.setTitle(R.string.brand);

	}

	/**
	 * init viewpage & pagertabstrip
	 */
	public void initViewPageandPagerStrip() {
		mViewPager = (ViewPager) this.findViewById(R.id.vp_brand);
		mPagerTitleStrip = (PagerTitleStrip) this
				.findViewById(R.id.brand_pagertab);
		mPagerTitleStrip.setTextSpacing(50);
		mLayoutInflator = getLayoutInflater();
		mBrandOverview = mLayoutInflator.inflate(R.layout.brand_overview, null);
		mBrandCulture = mLayoutInflator.inflate(R.layout.brand_culture, null);
		mViewList.add(mBrandOverview);
		mViewList.add(mBrandCulture);
		mTitleList.add(getResources().getString(R.string.brand_overview));
		mTitleList.add(getResources().getString(R.string.brand_culture));
		mMyPageApdater = new MyPageApdater(this, mViewList, mTitleList);
		mViewPager.setAdapter(mMyPageApdater);
	}

	/**
	 * init pulltorefreshlistview
	 */
	public void initPullToRefreshListView() {
		mLvBrandOverview = (PullToRefreshListView) this
				.findViewById(R.id.brand_overview);
		mLvBrandCulture = (PullToRefreshListView) this
				.findViewById(R.id.brand_culture);

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
