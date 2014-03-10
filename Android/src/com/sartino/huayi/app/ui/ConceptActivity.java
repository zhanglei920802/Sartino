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
import android.view.MenuItem;
import android.view.View;

public class ConceptActivity extends BaseActivity {
	public static final String TAG = "ConceptActivity";

	private ActionBar mActionBar = null;
	private ViewPager mViewPager = null;
	private PagerTitleStrip mPagerTitleStrip = null;
	private List<View> mViewList = new ArrayList<View>();
	private List<String> mTitleList = new ArrayList<String>();
	private LayoutInflater mLayoutInflator = null;
	private View mConcept_concept = null;
	private MyPageApdater mMyPageApdater = null;
	private PullToRefreshListView mLvConcept_concept = null;
	private Handler mConceptconceptHandler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.concept);
		this.initActionBar();
//		this.initViewPageandPagerStrip();
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
		mActionBar.setTitle(R.string.concept);

	}

	/**
	 * init viewpage & pagertabstrip
	 */
	public void initViewPageandPagerStrip() {
		mViewPager = (ViewPager) this.findViewById(R.id.concept);
		mPagerTitleStrip = (PagerTitleStrip) this
				.findViewById(R.id.concept_pagertab);
		mPagerTitleStrip.setTextSpacing(50);
		mLayoutInflator = getLayoutInflater();
		mConcept_concept = mLayoutInflator.inflate(R.layout.concept_concept,
				null);
		mViewList.add(mConcept_concept);
		mTitleList.add(getResources().getString(R.string.concept_concept));
		mMyPageApdater = new MyPageApdater(this, mViewList, mTitleList);
		mViewPager.setAdapter(mMyPageApdater);
	}

	/**
	 * init pulltorefreshlistview
	 */
	public void initPullToRefreshListView() {
		mLvConcept_concept = (PullToRefreshListView) this
				.findViewById(R.id.concept_concept);

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
