package com.sartino.huayi.app.ui;

import com.sartino.huayi.app.AppManager;
import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
	public static final String TAG = "BaseActivity";

	public static final String BRAND = "brand";
	public static final String CONCEPT = "concept";
	public static final String DESIGN = "design";
	public static final String DISPLAY = "display";
	public static final String DOWNLOAD = "download";
	public static final String HUALI = "huali";
	public static final String HUAYI = "huayi";
	public static final String JOIN = "join";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ���Activity����ջ
		AppManager.getAppManager().addActivity(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// ����Activity&�Ӷ�ջ���Ƴ�
		AppManager.getAppManager().finishActivity(this);
	}
}
