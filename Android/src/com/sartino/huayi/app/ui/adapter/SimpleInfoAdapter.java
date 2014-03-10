package com.sartino.huayi.app.ui.adapter;

import java.util.List;

import com.sartino.huayi.app.R;
import com.sartino.huayi.app.bean.SimpleInfo;
import com.sartino.huayi.app.common.BitmapManager;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleInfoAdapter extends BaseAdapter {
	private static final String TAG = "SimpleInfoAdapter";
	private Context mCtx = null;
	private List<SimpleInfo> mDatas = null;
	private LayoutInflater mLayoutInflator = null;
	private int mLayoutItemResouce = R.layout.listviewitem;
	private BitmapManager bmp = null;

	static class ListItemView {
		public ImageView thumbs = null;
		public TextView zhTitle = null;
		public TextView enTitle = null;
	};

	public SimpleInfoAdapter() {
		super();
	}

	public SimpleInfoAdapter(Context ctx, List<SimpleInfo> datas) {
		super();
		this.mCtx = ctx;
		this.mDatas = datas;
		this.mLayoutInflator = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.bmp = new BitmapManager(BitmapFactory.decodeResource(
				mCtx.getResources(), R.drawable.ic_launcher));
	}

	public List<SimpleInfo> getmDatas() {
		return mDatas;
	}

	public void setmDatas(List<SimpleInfo> mDatas) {
		this.mDatas = mDatas;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemView listItem = null;
		if (convertView == null) {
			convertView = mLayoutInflator.inflate(mLayoutItemResouce, null);
			listItem = new ListItemView();
			listItem.thumbs = (ImageView) convertView.findViewById(R.id.thumb);
			listItem.zhTitle = (TextView) convertView
					.findViewById(R.id.zh_title);
			listItem.enTitle = (TextView) convertView
					.findViewById(R.id.en_title);
			convertView.setTag(listItem);
		} else {
			listItem = (ListItemView) convertView.getTag();
		}

		listItem.zhTitle.setText(mDatas.get(position).getZh_title());
		listItem.enTitle.setText(mDatas.get(position).getEn_title());

		bmp.loadBitmap(mDatas.get(position).getImg().getThumbUrl(),
				listItem.thumbs);
		return convertView;
	}

}
