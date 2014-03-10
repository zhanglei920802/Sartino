package com.sartino.huayi.app.adapter;

/**
 * ͼƬ������
 *���ڽ�ͼƬ����Դ��gridview�����໥���� 
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
	private List<String> data;// ����
	int mGalleryItemBackground;
	LayoutInflater layoutInflater;// ���������
	private Context ctx = null;
	private BitmapManager mBitmapManager = null;

	/**
	 * 
	 * @param context
	 *            �豸������
	 * @param data
	 *            ��Ҫ���󶨵�����
	 * @param listviewItem
	 *            ��Ŀid
	 * @param cache
	 *            ����Ŀ¼
	 * @declare ������뽫�豸�����Ĵ��ݽ������Ա�������ݵİ�
	 */

	public PhotoViewAdapter(Context context, List<String> data) {
		this.ctx = context;
		this.data = data;

		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mBitmapManager = new BitmapManager();

	}

	/**
	 * ��ȡ��Ҫ�����ݵĴ�С
	 */
	@Override
	public int getCount() {
		return data.size();
	}

	/**
	 * ��ָ����id��ȡ��Ŀ����
	 * 
	 * @param int position
	 * 
	 */
	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	/**
	 * ��ȡitem id
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * ��ȡָ��λ��View������������ʾ���ݼ��е�����
	 * 
	 * @param position
	 *            int ��Ŀ��λ��
	 * @param convertView
	 *            ����һ���������Ŀ
	 * @param parent
	 *            ������
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
