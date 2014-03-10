package com.sartino.huayi.app.ui;

import com.sartino.huayi.app.AppContext;
import com.sartino.huayi.app.AppException;
import com.sartino.huayi.app.R;
import com.sartino.huayi.app.bean.User;
import com.sartino.huayi.app.common.StringUtils;
import com.sartino.huayi.app.common.UIHelper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends BaseActivity implements
		android.view.View.OnClickListener, OnFocusChangeListener {
	private static final String TAG = "RegisterActivity";

	private boolean DEBUG = true;
	private EditText medt_userName = null;
	private EditText medt_pwd = null;
	private EditText medt_Repeat_Pwd = null;
	private Button mbtn_Register = null;

	// public static final String ACTIVITY_MYPROFILE =
	private AppContext mAc = null;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:// 注册成功
				User user = (User) msg.obj;
				if (user != null) {
					UIHelper.ToastMessage(RegisterActivity.this,
							getString(R.string.reg_success));
					UIHelper.showMyProfileActivity(RegisterActivity.this, user);
				}

				break;

			case 0:
				UIHelper.ToastMessage(RegisterActivity.this,
						getString(R.string.reg_fail) + msg.obj);
				break;
			case -1:
				((AppException) msg.obj).makeToast(RegisterActivity.this);
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		this.initView();
	}

	private void initView() {
		medt_userName = (EditText) findViewById(R.id.reg_edt_user);
		medt_pwd = (EditText) findViewById(R.id.reg_edt_pwd);
		medt_Repeat_Pwd = (EditText) findViewById(R.id.reg_edt_repeat_pwd);
		mbtn_Register = (Button) findViewById(R.id.reg_commit);

		medt_Repeat_Pwd.setOnFocusChangeListener(this);
		mbtn_Register.setOnClickListener(this);
		mAc = (AppContext) getApplication();
	}

	@Override
	public void onClick(View v) {
		if (mbtn_Register.getId() == v.getId()) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					if (chkInput()) {// 输入正确
						Bundle bundle = new Bundle();
						bundle.putString("username", medt_userName.getText()
								.toString());
						bundle.putString("pwd", medt_pwd.getText().toString());
						Message msg = mHandler.obtainMessage();
						User user = null;

						try {
							user = mAc.regiter(bundle);
							if (user.getValidate().REG_OK()) {// 注册成功
								user.setUserName(bundle.getString("username"));
								user.setPwd(bundle.getString("pwd"));
								mAc.setUser(user);
								mAc.SaveUserInfo(user);
								msg.what = 1;
								msg.obj = user;
							} else {
								mAc.clearUserInfo(user);// /清除指定用户的信息
								mAc.setUser(null);
								msg.what = 0;
								msg.obj = user.getValidate().getErrorMessage();
							}
						} catch (AppException e) {
							mAc.setUser(null);
							if (DEBUG) {
								e.printStackTrace();
							}
							msg.obj = e;
							msg.what = -1;
						}

						mHandler.sendMessage(msg);
					}
				}
			}).start();
		}
	}

	protected boolean chkInput() {
		if (StringUtils.isEmpty(medt_userName.getText().toString())
				|| StringUtils.isEmpty(medt_pwd.getText().toString())) {
			UIHelper.ToastMessage(this, R.string.register_not_null);
			return false;
		}
		return true;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (v.getId() == medt_Repeat_Pwd.getId() && !hasFocus) {
			if (!medt_pwd.getText().equals(medt_Repeat_Pwd.getText())) {
				UIHelper.ToastMessage(RegisterActivity.this,
						R.string.register_not_match);
				medt_pwd.setText("");
				medt_Repeat_Pwd.setText("");
				// medt_pwd.setFocusable(true);
			}
		}
	}

}
