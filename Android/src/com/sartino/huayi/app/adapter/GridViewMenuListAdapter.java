package com.sartino.huayi.app.adapter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import com.sartino.huayi.app.R;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GridViewMenuListAdapter extends BaseAdapter {
	public static final String TAG = "GridViewMenuListAdapter";

	private LayoutInflater mLayoutInflator = null;
	private List<HashMap<String, Object>> mdatas = null;
	private boolean isChanged = false;
	private boolean ShowItem = false;
	private int holdPosition;
	private Context mctx = null;

	public GridViewMenuListAdapter(Context ctx,
			List<HashMap<String, Object>> data) {
		super();
		this.mctx = ctx;
		this.mLayoutInflator = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mdatas = data;

	}

	public void showDropItem(boolean showItem) {
		this.ShowItem = showItem;
		// notifyDataSetInvalidated();
	}

	@SuppressWarnings("unchecked")
	public void exchange(int startPosition, int endPosition) {
		holdPosition = endPosition;
		Object startObject = getItem(startPosition);
		if (startPosition < endPosition) {
			mdatas.add(endPosition + 1, (HashMap<String, Object>) startObject);
			mdatas.remove(startPosition);
		} else {
			mdatas.add(endPosition, (HashMap<String, Object>) startObject);
			mdatas.remove(startPosition + 1);
		}
		isChanged = true;
		notifyDataSetChanged();

	}

	@Override
	public int getCount() {
		return mdatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mdatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(mctx).inflate(R.layout.main_item,
				null);
		HashMap map = null;
		ImageView menuItem = null;

		String path = (String) mdatas.get(position).get("path");
		String name = (String) mdatas.get(position).get("name");
		menuItem = (ImageView) convertView.findViewById(R.id.imv_menu_item);
		menuItem.setTag(name.substring(0, name.indexOf(".")));
		menuItem.setImageURI(Uri.fromFile(new File(path)));

		if (isChanged) {
			if (position == holdPosition) {
				if (!ShowItem) {
					convertView.setVisibility(View.INVISIBLE);
				}
			}
		}

		return convertView;
	}

	public List<HashMap<String, Object>> getMdatas() {
		return mdatas;
	}

	public void setMdatas(List<HashMap<String, Object>> mdatas) {
		this.mdatas = mdatas;
	}

}
