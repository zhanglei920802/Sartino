package com.sartino.huayi.app.ui;

import java.util.ArrayList;

import com.sartino.huayi.app.AppContext;
import com.sartino.huayi.app.AppException;
import com.sartino.huayi.app.R;
import com.sartino.huayi.app.bean.DetailInfo;
import com.sartino.huayi.app.bean.SimpleInfo;
import com.sartino.huayi.app.common.BitmapManager;
import com.sartino.huayi.app.common.UIHelper;
import com.sartino.huayi.app.ui.widgets.MyDialog;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends BaseActivity {

	private ActionBar mActionBar = null;
	private SimpleInfo data = null;
	private TextView mEntitle = null;
	private TextView mOverview = null;
	private TextView mContent = null;
	private ImageView mThumbs = null;
	private BitmapManager mBM = null;
	private DetailInfo mDetail = null;
	private MyDialog mLoadingDialog = null;
	private AppContext mContext = null;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);
			if (msg.what == 1) {
				if (msg.obj != null) {
					mDetail = (DetailInfo) msg.obj;
					mLoadingDialog.dismiss();
					try {
						updateData();
					} catch (Exception e) {
						// System.out.println("有点儿问题");
					}

				}
			} else if (msg.what == -1) {
				mLoadingDialog.dismiss();
				UIHelper.ToastMessage(DetailActivity.this,R.string.network_not_connected);
			}
		}

	};

	private OnClickListener myOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Bundle data = new Bundle();
			try {
				data.putStringArrayList("data",
						(ArrayList<String>) mDetail.getUrls());
				UIHelper.ShowPhotoViewActivity(DetailActivity.this, data);
			} catch (Exception e) {
				UIHelper.ToastMessage(mContext, R.string.network_conn_fail);

			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			data = (SimpleInfo) getIntent().getExtras().getSerializable("data");
			setContentView(R.layout.detailinfo);
			this.initActionBar();
			this.initTool();
			this.initView();
			this.initData();
			Thread.currentThread().setName("mainThread");

		} catch (Exception e) {
			System.out.println("DetailActivity.onCreate()" + e.getMessage());
		}

	}

	private void updateData() {
		System.out.println("DetailActivity.updateData()"
				+ Thread.currentThread().getName());
		if (mDetail != null) {
			mOverview.setText(mDetail.getmOverView());
			mContent.setText(mDetail.getmContent());
		}

	}

	private void initData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				mContext = (AppContext) getApplication();
				try {
					mDetail = mContext.getDetailInfo(data.getId());
				} catch (AppException e) {
					mHandler.sendEmptyMessage(-1);
				}

				if (mDetail != null) {
					mHandler.handleMessage(mHandler.obtainMessage(1, mDetail));
				}
			}
		}).start();

	}

	private void initTool() {
		mBM = new BitmapManager(BitmapFactory.decodeResource(getResources(),
				R.drawable.test));

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void initView() {
		mEntitle = (TextView) this.findViewById(R.id.enTitle);
		mOverview = (TextView) this.findViewById(R.id.overview);
		mContent = (TextView) this.findViewById(R.id.content);
		mThumbs = (ImageView) this.findViewById(R.id.thumbimg);
		mThumbs.setOnClickListener(myOnClickListener);
		mEntitle.setText(data.getEn_title());

		mBM.loadBitmap(data.getImg().getUrl(), mThumbs);
		mLoadingDialog = new MyDialog(this, R.string.loading);
		mLoadingDialog.show();

	}

	/**
	 * init actionbar
	 */
	@SuppressLint("InlinedApi")
	private void initActionBar() {
		mActionBar = getActionBar();
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME
				| ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle(data.getZh_title());

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
