package com.sartino.huayi.app.ui;

import com.sartino.huayi.app.R;
import com.sartino.huayi.app.bean.User;
import com.sartino.huayi.app.common.GroupConfig;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class MyProfileActivity extends BaseActivity {
	private static final String TAG = "MyProfileActivity";
	private ActionBar mActionBar = null;

	private TextView mTv_user = null;
	private TextView mTv_Level = null;
	private TextView mTv_LastLogin = null;
	private TextView mTv_Add = null;
	private TextView mTv_Summary = null;
	private ImageView mIg_user = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myprofile);
		this.initActionBar();
		this.initView();

		this.getData();
	}

	private void initView() {
		mTv_user = (TextView) findViewById(R.id.username);
		mTv_Level = (TextView) findViewById(R.id.userlevel);
		mTv_LastLogin = (TextView) findViewById(R.id.lastlogin_profile);
		mTv_Add = (TextView) findViewById(R.id.add);
		mTv_Summary = (TextView) findViewById(R.id.summary);
		mIg_user = (ImageView) findViewById(R.id.user);

	}

	private void getData() {
		try {
			User user = (User) getIntent().getExtras().getSerializable("user");
			mTv_user.setText(user.getmUserName());
			mTv_LastLogin.setText(user.getLastLogin());
			if (user.getIntroduce() != null
					&& user.getIntroduce().length() != 0) {
				mTv_Summary.setText(user.getIntroduce());
			}

			processLevel(user.getGid());

		} catch (Exception e) {
			System.out.println("MyProfileActivity.getData()" + "³ö´íle");
		}

	}

	private void processLevel(int gid) {
		switch (gid) {

		case 3:
			mTv_Level.setText(GroupConfig.GROUP_3);
			break;
		case 4:
			mTv_Level.setText(GroupConfig.GROUP_4);
			break;

		case 8:
			mTv_Level.setText(GroupConfig.GROUP_8);
			break;
		case 9:
			mTv_Level.setText(GroupConfig.GROUP_9);
			break;

		case 10:
			mTv_Level.setText(GroupConfig.GROUP_10);
			break;
		default:
			mTv_Level.setText(GroupConfig.GROUP_2);

		}

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
		mActionBar.setTitle(R.string.brand);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
