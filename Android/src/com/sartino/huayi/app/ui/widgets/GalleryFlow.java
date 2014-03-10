package com.sartino.huayi.app.ui.widgets;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryFlow extends Gallery {
	public static final String TAG = "GalleryFlow";
	private Camera mCamera = new Camera();
	private int mMaxRotationAngle = 60;
	private int mMaxZoom = -120;
	private int mCoveflowCenter;
	// ----------------------------缩放手势相关------------------------------------------------
	public static final byte ZOOM = 0x01;// 缩放
	public static final byte Drag = 0x02;// 拖拽
	public static final byte NONE = 0x00;// 无动作

	private Matrix marix = new Matrix();// 当前矩阵
	private Matrix mSavedMarix = new Matrix();// 保存的矩阵

	private PointF start = new PointF();// 起点坐标
	private PointF mid = new PointF();// 中点坐标

	private float scaleRate = 1.0f;// 缩放比
	private float oldDistance = 0.0f;// 初始距离

	private byte mode = NONE;// 初始化

	public GalleryFlow(Context context) {
		super(context);
		this.setStaticTransformationsEnabled(true);
	}

	public GalleryFlow(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setStaticTransformationsEnabled(true);
	}

	public GalleryFlow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setStaticTransformationsEnabled(true);
	}

	public int getMaxRotationAngle() {
		return mMaxRotationAngle;
	}

	public void initTouch() {
		this.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView imageView = (ImageView) v;
				switch (event.getAction() & MotionEvent.ACTION_MASK) {// 多点触摸
				case MotionEvent.ACTION_DOWN:
					marix.set(imageView.getImageMatrix());
					mSavedMarix.set(marix);
					start.set(event.getX(), event.getY());
					mode = Drag;
					break;

				case MotionEvent.ACTION_UP:
					mode = NONE;
					break;
				case MotionEvent.ACTION_POINTER_UP:
					mode = NONE;
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					oldDistance = CalcuHypotenuse(event);
					if (oldDistance > 10f) {
						mSavedMarix.set(marix);
						getMidCoordinate(mid, event);
						mode = ZOOM;
					}

					break;
				case MotionEvent.ACTION_MOVE:
					if (mode == Drag) {
						marix.set(mSavedMarix);

						marix.postTranslate(event.getX() - start.x,
								event.getY() - start.y);
						System.out.println("PhotoViewAdapter.onTouch()" + "拖动");
					} else if (mode == ZOOM) {
						float newDistance = CalcuHypotenuse(event);
						scaleRate = newDistance / oldDistance;
						System.out.println("PhotoViewAdapter.onTouch()" + "缩放");
						marix.postScale(scaleRate, scaleRate, mid.x, mid.y);
					}
					break;
				}

				imageView.setImageMatrix(marix);
				return true;
			}
		});
	}

	public void setMaxRotationAngle(int maxRotationAngle) {
		mMaxRotationAngle = maxRotationAngle;
	}

	public int getMaxZoom() {
		return mMaxZoom;
	}

	public void setMaxZoom(int maxZoom) {
		mMaxZoom = maxZoom;
	}

	private int getCenterOfCoverflow() {
		return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2
				+ getPaddingLeft();
	}

	private static int getCenterOfView(View view) {
		return view.getLeft() + view.getWidth() / 2;
	}

	@Override
	protected boolean getChildStaticTransformation(View child, Transformation t) {

		final int childCenter = getCenterOfView(child);
		final int childWidth = child.getWidth();
		int rotationAngle = 0;

		t.clear();
		t.setTransformationType(Transformation.TYPE_MATRIX);

		if (childCenter == mCoveflowCenter) {
			transformImageBitmap((ImageView) child, t, 0);
		} else {
			rotationAngle = (int) (((float) (mCoveflowCenter - childCenter) / childWidth) * mMaxRotationAngle);
			if (Math.abs(rotationAngle) > mMaxRotationAngle) {
				rotationAngle = (rotationAngle < 0) ? -mMaxRotationAngle
						: mMaxRotationAngle;
			}
			transformImageBitmap((ImageView) child, t, rotationAngle);
		}

		return true;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mCoveflowCenter = getCenterOfCoverflow();
		super.onSizeChanged(w, h, oldw, oldh);
	}

	private void transformImageBitmap(ImageView child, Transformation t,
			int rotationAngle) {
		mCamera.save();
		final Matrix imageMatrix = t.getMatrix();
		final int imageHeight = child.getLayoutParams().height;
		final int imageWidth = child.getLayoutParams().width;
		final int rotation = Math.abs(rotationAngle);

		// 在Z轴上正向移动camera的视角，实际效果为放大图片。
		// 如果在Y轴上移动，则图片上下移动；X轴上对应图片左右移动。
		mCamera.translate(0.0f, 0.0f, 100.0f);

		// As the angle of the view gets less, zoom in
		if (rotation < mMaxRotationAngle) {
			float zoomAmount = (float) (mMaxZoom + (rotation * 1.5));
			mCamera.translate(0.0f, 0.0f, zoomAmount);
		}

		// 在Y轴上旋转，对应图片竖向向里翻转。
		// 如果在X轴上旋转，则对应图片横向向里翻转。
		// mCamera.rotateY(rotationAngle);
		// mCamera.getMatrix(imageMatrix);
		// imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));
		// imageMatrix.postTranslate((imageWidth / 2), (imageHeight / 2));
		mCamera.restore();
	}

	/**
	 * 
	 * 处理滑动事件
	 * 
	 * @param e1
	 *            The first down motion event that started the fling.
	 * @param e2
	 *            The move motion event that triggered the current onFling.
	 * @param velocityX
	 *            在X方向的速度
	 * @param velocityY
	 *            在Y方向的速度
	 * 
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		System.out.println("MyGallery.onFling()");

		Log.i(TAG, "velocityX=" + String.valueOf(velocityX));
		Log.i(TAG, "velocityY=" + String.valueOf(velocityY));
		int kEvent;
		if (isScrollingLeft(e1, e2)) {
			// Check if scrolling left
			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
		} else {
			// Otherwise scrolling right
			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
		}

		onKeyDown(kEvent, null);

		return true;

	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > e1.getX();
	}

	/**
	 * 拖动事件
	 * 
	 * @param e1
	 * 
	 * @param e2
	 * 
	 * @param distanceX
	 *            在单位时间内，在X前后两点移动的距离
	 * @param distanceY
	 *            在单位时间内，在Y前后两点移动的距离
	 */
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		System.out.println("MyGallery.onScroll()");
		Log.i(TAG, String.valueOf(e2.getX() - e1.getX()));

		return super.onScroll(e1, e2, distanceX, 0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		System.out.println("MyGallery.onKeyDown()");
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 计算直角边
	 * 
	 * @param e
	 * @return
	 */
	private float CalcuHypotenuse(MotionEvent event) {
		// float x = e.getX(0) - e.getX(1);
		// float y = e.getY(0) - e.getY(1);
		//
		// return FloatMath.sqrt(x * x + y * y);

		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	/**
	 * 计算中点坐标
	 * 
	 * @param mid
	 * @param e
	 */
	private void getMidCoordinate(PointF mid, MotionEvent e) {
		mid.set((e.getX(0) + e.getX(1)) / 2, (e.getY(0) + e.getY(1)) / 2);
	}
}
