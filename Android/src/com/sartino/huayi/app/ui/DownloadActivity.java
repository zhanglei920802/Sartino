package com.sartino.huayi.app.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.sartino.huayi.app.R;
import com.sartino.huayi.app.ui.adapter.MyPageApdater;

public class DownloadActivity extends BaseActivity {
	public static final String TAG = "DownloadActivity";

	private ActionBar mActionBar = null;
	private ViewPager mViewPager = null;
	private PagerTitleStrip mPagerTitleStrip = null;
	private List<View> mViewList = new ArrayList<View>();
	private List<String> mTitleList = new ArrayList<String>();
	private LayoutInflater mLayoutInflator = null;
	private View mDownload_DownLoad = null;
	private MyPageApdater mMyPageApdater = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download);

		this.initActionBar();
		this.initViewPageandPagerStrip();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
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
		mActionBar.setTitle(R.string.download);

	}

	/**
	 * init viewpage & pagertabstrip
	 */
	public void initViewPageandPagerStrip() {
		mViewPager = (ViewPager) this.findViewById(R.id.vp_download);
		mPagerTitleStrip = (PagerTitleStrip) this
				.findViewById(R.id.download_pagertab);
		mPagerTitleStrip.setTextSpacing(50);
		mLayoutInflator = getLayoutInflater();
		mDownload_DownLoad = mLayoutInflator.inflate(
				R.layout.download_download, null);
		mViewList.add(mDownload_DownLoad);
		mTitleList.add(getResources().getString(R.string.download_download));
		mMyPageApdater = new MyPageApdater(this, mViewList, mTitleList);
		mViewPager.setAdapter(mMyPageApdater);
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
