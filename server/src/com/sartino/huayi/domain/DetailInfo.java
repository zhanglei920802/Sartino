package com.sartino.huayi.domain;

import java.util.ArrayList;
import java.util.List;

public class DetailInfo extends Base {

	private static final long serialVersionUID = 3927411515436321641L;
	public int mId = 0;
	public String mOverView = null;
	public String mContent = null;
	public List<String> urls = new ArrayList<String>();
	public int msize = 0;
	public int getmId() {
		return mId;
	}

	
	public int getMsize() {
		return msize;
	}


	public void setMsize(int msize) {
		this.msize = msize;
	}


	public void setmId(int mId) {
		this.mId = mId;
	}

	public String getmOverView() {
		return mOverView;
	}

	public void setmOverView(String mOverView) {
		this.mOverView = mOverView;
	}

	public String getmContent() {
		return mContent;
	}

	public void setmContent(String mContent) {
		this.mContent = mContent;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	

	public DetailInfo(int mId, String mOverView, String mContent,
			List<String> urls, int msize) {
		super();
		this.mId = mId;
		this.mOverView = mOverView;
		this.mContent = mContent;
		this.urls = urls;
		this.msize = msize;
	}


	public DetailInfo() {
		super();
	}

	@Override
	public String toString() {
		return "DetailInfo [mId=" + mId + ", mOverView=" + mOverView
				+ ", mContent=" + mContent + ", urls=" + urls.toString() + "]";
	}

}
