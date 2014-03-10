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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sartino.huayi.app.R;
import com.sartino.huayi.app.ui.adapter.MyPageApdater;

public class JoinActivity extends BaseActivity {
	private static final String TAG = "JoinActivity";

	private ActionBar mActionBar = null;
	private ViewPager mViewPager = null;
	private PagerTitleStrip mPagerTitleStrip = null;
	private List<View> mViewList = new ArrayList<View>();
	private List<String> mTitleList = new ArrayList<String>();
	private LayoutInflater mLayoutInflator = null;
	private View mJoin_advantage = null, mJoin_process = null,
			mJoin_Retrun = null, mJoin_Network = null, mJoin_Policy = null;
	private MyPageApdater mMyPageApdater = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join);

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
		mActionBar.setTitle(R.string.join);

	}

	/**
	 * init viewpage & pagertabstrip
	 */
	public void initViewPageandPagerStrip() {
		mViewPager = (ViewPager) this.findViewById(R.id.join);
		mPagerTitleStrip = (PagerTitleStrip) this
				.findViewById(R.id.join_pagertab);
		mPagerTitleStrip.setTextSpacing(50);
		mLayoutInflator = getLayoutInflater();
		mJoin_advantage = mLayoutInflator
				.inflate(R.layout.join_advantage, null);
		mJoin_Network = mLayoutInflator.inflate(R.layout.join_network, null);
		mJoin_Policy = mLayoutInflator.inflate(R.layout.join_policy, null);
		mJoin_process = mLayoutInflator.inflate(R.layout.join_process, null);
		mJoin_Retrun = mLayoutInflator.inflate(R.layout.join_retrun, null);

		mViewList.add(mJoin_advantage);
		mViewList.add(mJoin_Network);
		mViewList.add(mJoin_process);
		mViewList.add(mJoin_Policy);
		mViewList.add(mJoin_Retrun);

		mTitleList.add(getResources().getString(R.string.join_advantage));
		mTitleList.add(getResources().getString(R.string.join_market));
		mTitleList.add(getResources().getString(R.string.join_process));
		mTitleList.add(getResources().getString(R.string.join_policy));
		mTitleList.add(getResources().getString(R.string.join_return));

		mMyPageApdater = new MyPageApdater(this, mViewList, mTitleList);
		mViewPager.setAdapter(mMyPageApdater);
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
