package com.sartino.huayi.app.ui.widgets;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;
import com.sartino.huayi.app.R;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyDialog extends Dialog {
	private static final int default_width = 160; // Ĭ�Ͽ��
	private static final int default_height = 120;// Ĭ�ϸ߶�
	private static final int default_layout = R.layout.mydialog;// Ĭ�ϲ���
	private static final int default_msg_int = R.string.default_msg;// Ĭ����Ϣ
	public static int style = Style.style_TRANSPARENT;// Ĭ����ʽ
	public static int msgColor = Color.WHITE;// ������ɫ
	public static int msgSize = 14;// �����С
	public static int drawable = Style.Default;// ��ʽ
	public static int drawableBgColor = Color.TRANSPARENT;// ��ʽ������ɫ
	public static int drawableBg = R.drawable.default_bg;// ��ʽ����ͼƬ
	public static int dialogBgColor = Color.TRANSPARENT;// �Ի��򱳾���ɫ
	public static int dialogBgDrawable = R.drawable.default_bg;// �Ի��򱳾�ͼƬ
	public static int padding = 20;// ͼƬ���ֵļ��
	public static float alpha = 0.5f;// ͸����
	private TextView tv = null;
	private GifView gifView = null;

	public MyDialog(Context context) {
		this(context, default_width, default_height, default_layout, style,
				drawable, default_msg_int);
	}

	public MyDialog(Context context, int msg) {
		this(context, default_width, default_height, default_layout, style,
				drawable, msg);
	}

	public MyDialog(Context context, String msg) {
		this(context, default_width, default_height, default_layout, style,
				drawable, msg);
	}

	public MyDialog(Context context, int width, int height, int msg) {
		this(context, width, height, default_layout, style, drawable, msg);
	}

	public MyDialog(Context context, int width, int height, String msg) {
		this(context, width, height, default_layout, style, drawable, msg);
	}

	public MyDialog(Context context, int width, int height) {
		this(context, width, height, default_layout, style, drawable,
				default_msg_int);
	}

	private MyDialog(Context context, int width, int height, int layout,
			int style, int drawable, int msg) {
		super(context, style);

		setContentView(layout);

		// set window params
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		// set width,height by density and gravity
		float density = getDensity(context);
		// params.width = (int) (width * density);
		// params.height = (int) (height * density);
		params.width = LayoutParams.MATCH_PARENT;
		params.height = LayoutParams.MATCH_PARENT;
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);
		tv = (TextView) findViewById(R.id.msg);
		tv.setText(msg);
		tv.setTextColor(msgColor);
		tv.setTextSize(msgSize);
		tv.setPadding(padding, 0, 0, 0);
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
		ll.setBackgroundColor(dialogBgColor);
		ll.setBackgroundResource(dialogBgDrawable);
		gifView = (GifView) findViewById(R.id.gifView);
		gifView.setGifImageType(GifImageType.SYNC_DECODER);
		gifView.setGifImage(drawable);
		gifView.setBackgroundColor(drawableBgColor);
		gifView.setBackgroundResource(drawableBg);

	}

	private MyDialog(Context context, int width, int height, int layout,
			int style, int drawable, String msg) {
		super(context, style);

		setContentView(layout);

		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		// set width,height by density and gravity
		float density = getDensity(context);
		params.alpha = alpha;
		// params.width = (int) (width * density);
		// params.height = (int) (height * density);
		params.width = LayoutParams.MATCH_PARENT;
		params.height = LayoutParams.MATCH_PARENT;
		params.gravity = Gravity.CENTER_VERTICAL;
		window.setAttributes(params);
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
		ll.setBackgroundColor(dialogBgColor);
		ll.setBackgroundResource(dialogBgDrawable);
		tv = (TextView) findViewById(R.id.msg);
		tv.setText(msg);
		tv.setTextColor(msgColor);
		tv.setTextSize(msgSize);
		tv.setPadding(padding, 0, 0, 0);
		gifView = (GifView) findViewById(R.id.gifView);
		gifView.setGifImageType(GifImageType.SYNC_DECODER);
		gifView.setGifImage(drawable);
		gifView.setBackgroundColor(drawableBgColor);
		gifView.setBackgroundResource(drawableBg);

	}

	private float getDensity(Context context) {
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.density;
	}

	public void distory() {
		gifView.setVisibility(View.INVISIBLE);
		tv.setVisibility(View.INVISIBLE);
		this.cancel();

	}
}
