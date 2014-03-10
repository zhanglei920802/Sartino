package com.sartino.huayi.app.ui.widgets;

import com.sartino.huayi.app.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class FeedBackDialog extends Dialog {
	private FeedBackClickListener ml_FeedBackClickListener = null;

	public FeedBackDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);

	}

	public FeedBackDialog(Context context, int theme) {
		super(context, theme);

	}

	public FeedBackDialog(Context context) {
		super(context);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
	}

	public void setOnClickFeedBackButton(FeedBackClickListener l) {
		this.ml_FeedBackClickListener = l;
	}

	public void commitFeedBack(View v) {
		System.out.println("FeedBackDialog.commitFeedBack()" + "±»µã»÷ÁË");

		if (ml_FeedBackClickListener != null) {
			ml_FeedBackClickListener.feedBack();

		}
	}

	public interface FeedBackClickListener {
		public void feedBack();
	}
}
