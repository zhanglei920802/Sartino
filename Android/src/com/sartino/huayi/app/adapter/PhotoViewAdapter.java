package com.sartino.huayi.app.adapter;

/**
 * 图片适配器
 *用于将图片数据源和gridview进行相互关联 
 * 
 */

import java.util.List;

import com.sartino.huayi.app.common.BitmapManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class PhotoViewAdapter extends BaseAdapter {

	private static final String TAG = "GalleryAdapter";
	private List<String> data;// 数据
	int mGalleryItemBackground;
	LayoutInflater layoutInflater;// 布局填充器
	private Context ctx = null;
	private BitmapManager mBitmapManager = null;

	/**
	 * 
	 * @param context
	 *            设备上下文
	 * @param data
	 *            将要被绑定的数据
	 * @param listviewItem
	 *            条目id
	 * @param cache
	 *            缓存目录
	 * @declare 这里必须将设备上下文传递进来，以便进行数据的绑定
	 */

	public PhotoViewAdapter(Context context, List<String> data) {
		this.ctx = context;
		this.data = data;

		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mBitmapManager = new BitmapManager();

	}

	/**
	 * 获取将要绑定数据的大小
	 */
	@Override
	public int getCount() {
		return data.size();
	}

	/**
	 * 以指定的id获取条目内容
	 * 
	 * @param int position
	 * 
	 */
	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	/**
	 * 获取item id
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 获取指定位置View，他将用来显示数据集中地内容
	 * 
	 * @param position
	 *            int 条目的位置
	 * @param convertView
	 *            这是一个缓存的条目
	 * @param parent
	 *            布局组
	 * 
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(this.ctx);
		imageView.setScaleType(ImageView.ScaleType.MATRIX);

		Gallery.LayoutParams lp = new Gallery.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		imageView.setLayoutParams(lp);
		imageView.setAdjustViewBounds(true);

		mBitmapManager.loadBitmap(data.get(position), imageView);
		// imageView.setOnTouchListener(this);
		return imageView;

	}

}
