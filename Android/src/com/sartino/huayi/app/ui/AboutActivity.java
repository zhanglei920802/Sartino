package com.sartino.huayi.app.ui;

import com.sartino.huayi.app.R;
import com.sartino.huayi.app.common.UIHelper;

import android.os.Bundle;
import android.view.View;

public class AboutActivity extends BaseActivity {
	private static final String TAG = "AboutActivity";

	/**
	 *
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		AppManager.getAppManager().addActivity(this);
		setContentView(R.layout.about);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		AppManager.getAppManager().finishActivity(this);
	};

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {

		return super.onOptionsItemSelected(item);

	}

	public void chkUpdate(View view) {
			UIHelper.ToastMessage(AboutActivity.this, R.string.lastupdate);
	}
}
