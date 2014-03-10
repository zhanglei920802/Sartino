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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends BaseActivity implements OnClickListener {
	public static final int TAG_LOGIN = 0x00;// ����ص�������
	public static final int TAG_PROFILE = 0x01;// ����ת���ҵ����Ͻ���

	private static final String TAG = "LoginActivity";
	private boolean DEBUG = true;
	private AutoCompleteTextView medt_User = null;
	private EditText medt_Pwd = null;
	private Button mbtn_Login = null;
	private Button mbtn_Reg = null;
	private AppContext mAc = null;

	private int tag = 0;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case -1:// ��¼���󣬷Ƿ���������
				((AppException) msg.obj).makeToast(LoginActivity.this);
				break;
			case 1:// ��¼�ɹ�
				User user = (User) msg.obj;
				if (user != null) {
					UIHelper.ToastMessage(LoginActivity.this,
							getString(R.string.login_success));

					if (tag == LoginActivity.TAG_LOGIN) {// ת��������
						UIHelper.showHome(LoginActivity.this);
					} else if (tag == LoginActivity.TAG_PROFILE) {// /ת���ҵ�����

						UIHelper.showMyProfileActivity(LoginActivity.this, user);
					}

				}
				break;

			case 0:// ����������
				UIHelper.ToastMessage(LoginActivity.this,
						getString(R.string.login_fail) + msg.obj);
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		this.initView();
		tag = getIntent().getIntExtra("tag", LoginActivity.TAG_LOGIN);
	}

	public void initView() {
		mAc = (AppContext) getApplication();
		medt_User = (AutoCompleteTextView) findViewById(R.id.edt_user);
		medt_Pwd = (EditText) findViewById(R.id.edt_pwd);

		mbtn_Login = (Button) findViewById(R.id.login_in);
		mbtn_Reg = (Button) findViewById(R.id.login_register);
		mbtn_Login.setOnClickListener(this);
		mbtn_Reg.setOnClickListener(this);

		final String allUsers[] = mAc.getSuggetionsUser();
		if (allUsers != null && allUsers.length != 0) {
			ArrayAdapter<String> data = new ArrayAdapter<String>(this,
					android.R.layout.simple_dropdown_item_1line, allUsers);
			medt_User.setAdapter(data);
			medt_User.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// System.out
					// .println("LoginActivity.initView().new TextWatcher() {...}.onTextChanged()"
					// + s);

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					//
					// System.out
					// .println("LoginActivity.initView().new TextWatcher() {...}.beforeTextChanged()"
					// + s);

				}

				@Override
				public void afterTextChanged(Editable s) {
					//
					// System.out
					// .println("LoginActivity.initView().new TextWatcher() {...}.afterTextChanged()"
					// + s);

					// �û���Ϣ�ı���������ʾ
					User user = mAc.getUserInfo(s.toString());
					if (user == null)
						return;
					if (!StringUtils.isEmpty(user.getmUserName())) {
						medt_User.setText(user.getmUserName());
					}
					if (!StringUtils.isEmpty(user.getPwd())) {
						medt_Pwd.setText(user.getPwd());
					}
				}
			});
		}

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == mbtn_Login.getId()) {// ��¼
			// ����û���¼
			if (this.chkInput()) {
				this.login();
			} else {
				medt_User.setFocusable(true);
			}
		} else if (v.getId() == mbtn_Reg.getId()) {// ע��
			UIHelper.showReigster(LoginActivity.this);
		}
	}

	private void login() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				Bundle bundle = null;
				Message msg = null;
				User user = null;
				try {
					bundle = new Bundle();
					bundle.putString("username", medt_User.getText().toString()
							.trim());
					bundle.putString("pwd", medt_Pwd.getText().toString());
					// context������ݽ��м���
					msg = mHandler.obtainMessage();
					user = mAc.Login(bundle);

					user.setUserName(bundle.getString("username"));
					user.setPwd(bundle.getString("pwd"));

					if (AppContext.DEBUG) {
						System.out
								.println("LoginActivity.login().new Runnable() {...}.run()"
										+ user);
					}
					bundle = null;

					if (user.getValidate().OK()) {
						mAc.setUser(user);
						mAc.SaveUserInfo(user);
						msg.obj = user;
						msg.what = 1;

					} else {

						mAc.clearUserInfo(user);// /���ָ���û�����Ϣ
						mAc.setUser(null);
						msg.obj = user.getValidate().getErrorMessage();
						msg.what = 0;
					}
				} catch (AppException e) {
					mAc.setUser(null);
					if (DEBUG) {
						e.printStackTrace();
					}
					// Ӧ������˻���Ϣ
					// mAc.clearUserInfo(user);
					msg.obj = e;
					msg.what = -1;
				}

				mHandler.sendMessage(msg);
			}
		}).start();
	}

	/**
	 * ����û�����
	 * 
	 * �����ʽ����Ҫ�󷵻��棬���򷵻ؼ�
	 * 
	 * @return
	 */
	private boolean chkInput() {

		if (StringUtils.isEmpty(medt_User.getText().toString())
				|| StringUtils.isEmpty(medt_Pwd.getText().toString())) {
			UIHelper.ToastMessage(LoginActivity.this, R.string.cannotbenull);
			return false;
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		destoryVar();
		System.gc();
	}

	public void destoryVar() {
		if (DEBUG) {
			System.out.println("LoginActivity.destoryVar()" + "������档������");
		}
		medt_User = null;
		medt_Pwd = null;
		mbtn_Login = null;
		mbtn_Reg = null;

	}

}
