package com.sartino.huayi.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.sartino.huayi.app.common.UIHelper;
import com.sartino.huayi.app.ui.MainActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

/**
 * welcome
 * 
 * @author Administrator
 * 
 */
public class AppStart extends Activity {
	public static final String TAG = "AppStart";
	private List<HashMap<String, Object>> mDatas = null;
	private boolean manimIsEnd = false;
	private boolean mIsLoadOver = false;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			if (msg.what == 0) {
				doJudge();
			} else if (msg.what == 1) {
				doJudge();
			}
		}

	};

	private AnimationListener mAnimationListener = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {

		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			manimIsEnd = true;
			mHandler.sendEmptyMessage(1);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		android.os.Debug.startMethodTracing(AppStart.TAG);
		super.onCreate(savedInstanceState);

		final View view = View.inflate(this, R.layout.start, null);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(view);

		AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
		aa.setDuration(2000);
		view.startAnimation(aa);
		clipImages();
		aa.setAnimationListener(mAnimationListener);
	}

	public void redirectToHome() {
		Intent intent = new Intent(AppStart.this, MainActivity.class);
		this.startActivity(intent);
		this.finish();
	}

	/**
	 * Í¼Æ¬µÄ²Ã¼ô
	 */
	public void clipImages() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				mDatas = UIHelper.getDrawables();
				if (UIHelper.getThumbs(AppContext.getInstance().getThumbsDir(
						AppStart.this)) != null) {

					mDatas = UIHelper.getThumbs(AppContext.getInstance()
							.getThumbsDir(AppStart.this));

				} else {

					try {
						mDatas = UIHelper.createThumbs(mDatas, AppContext
								.getInstance().getThumbsDir(AppStart.this),
								AppStart.this);
					} catch (IOException e) {

						System.out
								.println("AppStart.clipImages().new Runnable() {...}.run()::"
										+ "»ñÈ¡Í¼Æ¬Ê§°Ü");
					}

				}
				mIsLoadOver = true;
				mHandler.sendEmptyMessage(0);
			}
		}).start();
	}

	@Override
	protected void onDestroy() {
		android.os.Debug.stopMethodTracing();
		super.onDestroy();
	}

	public void doJudge() {
		if (manimIsEnd && mIsLoadOver) {
			redirectToHome();
		}
	}
}
