package com.sartino.huayi.app.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

import android.widget.LinearLayout;
import android.widget.Scroller;

public class MyLinearLayout extends LinearLayout {
	private static final String TAG = "ScrollLayout";
	private Scroller mScroller;
	private VelocityTracker mVelocityTracker;// ���ֿؼ�
	private int mCurScreen;// ��ǰ��Ļ
	private int mDefaultScreen = 0;// Ĭ����Ļ
	private static final int TOUCH_STATE_REST = 0;// ��Ϣ��
	private static final int TOUCH_STATE_SCROLLING = 1;// ������
	private int mTouchState = TOUCH_STATE_REST;// Ĭ��û�й���
	private int mTouchSlop;// �����ڸ�ֵ��ʱ����Ϊ��������
	private float mLastMotionX;// ���µ�����
	private float mLastMotionY;
	private boolean isScroll = true;
	private static final int SNAP_VELOCITY = 600;// ���������
	private OnViewChangeListener mOnViewChangeListener;// ������

	public MyLinearLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public MyLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MyLinearLayout(Context context) {
		super(context);
		init(context);
	}

	public void init(Context context) {
		System.out.println(context);
		mScroller = new Scroller(context);
		mCurScreen = mDefaultScreen;
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	public void setIsScroll(boolean b) {
		this.isScroll = b;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE)// ����true������Touch�¼����д���
				&& (mTouchState != TOUCH_STATE_REST)) {
			return true;
		}

		final float x = ev.getX();
		final float y = ev.getY();
		switch (action) {
		case MotionEvent.ACTION_MOVE:// �ƶ��¼�
			final int xDiff = (int) Math.abs(mLastMotionX - x);
			if (xDiff > mTouchSlop) {
				mTouchState = TOUCH_STATE_SCROLLING;// true
			}
			break;
		case MotionEvent.ACTION_DOWN:// �Ƿ�������
			mLastMotionX = x;
			mLastMotionY = y;
			mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST
					: TOUCH_STATE_SCROLLING;// ���������ֱ�ӽ����ӿؼ����д���
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP://
			mTouchState = TOUCH_STATE_REST;
			break;
		}
		return mTouchState != TOUCH_STATE_REST;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
		final int action = event.getAction();
		final float x = event.getX();
		final float y = event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (!mScroller.isFinished()) {// ����Ѿ���������,��ôֹͣ����
				mScroller.abortAnimation();
			}
			mLastMotionX = x;
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_UP:
			final VelocityTracker velocityTracker = mVelocityTracker;
			velocityTracker.computeCurrentVelocity(1000);
			int velocityX = (int) velocityTracker.getXVelocity();// ������ٶ�

			System.out.println("��ǰ����:" + velocityX);

			if (velocityX > SNAP_VELOCITY && mCurScreen > 0) {// ��ǰ��Ļ����0���������ҡ���ǩ��Ӧ�������ƶ�

				snapToScreen(mCurScreen - 1);

			} else if (velocityX < -SNAP_VELOCITY
					&& mCurScreen < getChildCount() - 1) {// �������󣬱�ǩ��Ӧ�������ƶ�
				snapToScreen(mCurScreen + 1);

			} else {// ����������Ǳ����ڵ�0��

				snapToDestination();
			}

			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}

			mTouchState = TOUCH_STATE_REST;
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaX = (int) (mLastMotionX - x);
			int deltaY = (int) (mLastMotionY - y);
			System.out.println("MyLinearLayout.onTouchEvent()" + ":deltaX="
					+ deltaX + " delataY=" + deltaY + " slop="
					+ this.mTouchSlop);
			// ���x����Ĺ�������ľ���ֵС��200��y�������10����ôֹͣ

			if (Math.abs(deltaX) < 200 && Math.abs(deltaY) > 10)
				break;
			mLastMotionY = y;
			mLastMotionX = x;
			scrollBy(deltaX, 0);// ���й�������x�����Ͻ��й���

			break;
		case MotionEvent.ACTION_CANCEL:
			mTouchState = TOUCH_STATE_REST;
			break;
		}

		return true;

	}

	public void snapToScreen(int whichScreen) {

		scrollToScreen(whichScreen);
	}

	public void scrollToScreen(int whichScreen) {
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		System.out.println("MyLinearLayout.scrollToScreen() whichscreen"
				+ whichScreen);
		
		if (getScrollX() != (whichScreen * getWidth())) {
			final int delta = whichScreen * getWidth() - getScrollX();
			mScroller.startScroll(getScrollX(), 0, delta, 0,
					Math.abs(delta) * 1);// ��������ʱ�� �Ժ���Ϊ��λ
			mCurScreen = whichScreen;
			invalidate(); // Redraw the layout

			if (mOnViewChangeListener != null) {
				mOnViewChangeListener.OnViewChange(mCurScreen);
			}

		}

	}

	public void setToScreen(int whichScreen) {
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 2));
		mCurScreen = whichScreen;
		scrollTo(whichScreen * getWidth(), 0);

		if (mOnViewChangeListener != null) {
			mOnViewChangeListener.OnViewChange(mCurScreen);
		}
	}

	public int getCurScreen() {
		return mCurScreen;
	}

	/**
	 * According to the position of current layout scroll to the destination
	 * page.
	 */
	public void snapToDestination() {
		final int screenWidth = getWidth();

		final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;

		Log.e(TAG, "screenWidth=" + screenWidth + " destScreen " + destScreen
				+ "getScrollX=" + getScrollX());

		snapToScreen(destScreen);
	}

	/**
	 * ������Ļ�л�������
	 * 
	 * @param listener
	 */
	public void SetOnViewChangeListener(OnViewChangeListener listener) {
		mOnViewChangeListener = listener;
	}

	/**
	 * ��Ļ�л�������
	 * 
	 * @author liux
	 */
	public interface OnViewChangeListener {
		public void OnViewChange(int view);
	}

	/**
	 * �����������ˢ��
	 */
	@Override
	public void computeScroll() {
		// System.out.println("MyLinearLayout.computeScroll()");
		if (mScroller.computeScrollOffset()) {//
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}

}
