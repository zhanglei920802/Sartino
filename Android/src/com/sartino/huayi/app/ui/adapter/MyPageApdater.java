package com.sartino.huayi.app.ui.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MyPageApdater extends PagerAdapter {
	private List<View> mListViews = null;
	private List<String> mTitleList = null;

	public MyPageApdater(Context ctx, List<View> listViews, List<String> titles) {
		super();
		this.mListViews = listViews;
		this.mTitleList = titles;
	}

	@Override
	public int getCount() {
		return mListViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mListViews.get(position));

	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTitleList.get(position);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(mListViews.get(position));

		return mListViews.get(position);
	}

}
