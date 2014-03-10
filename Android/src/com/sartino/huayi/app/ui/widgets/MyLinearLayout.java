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
	private VelocityTracker mVelocityTracker;// 滚轮控件
	private int mCurScreen;// 当前屏幕
	private int mDefaultScreen = 0;// 默认屏幕
	private static final int TOUCH_STATE_REST = 0;// 休息中
	private static final int TOUCH_STATE_SCROLLING = 1;// 滚动中
	private int mTouchState = TOUCH_STATE_REST;// 默认没有滚动
	private int mTouchSlop;// 当大于该值得时候，认为被滚动了
	private float mLastMotionX;// 最新的坐标
	private float mLastMotionY;
	private boolean isScroll = true;
	private static final int SNAP_VELOCITY = 600;// 渐变的速率
	private OnViewChangeListener mOnViewChangeListener;// 监听器

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
		if ((action == MotionEvent.ACTION_MOVE)// 返回true，交给Touch事件进行处理
				&& (mTouchState != TOUCH_STATE_REST)) {
			return true;
		}

		final float x = ev.getX();
		final float y = ev.getY();
		switch (action) {
		case MotionEvent.ACTION_MOVE:// 移动事件
			final int xDiff = (int) Math.abs(mLastMotionX - x);
			if (xDiff > mTouchSlop) {
				mTouchState = TOUCH_STATE_SCROLLING;// true
			}
			break;
		case MotionEvent.ACTION_DOWN:// 是否滚动完毕
			mLastMotionX = x;
			mLastMotionY = y;
			mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST
					: TOUCH_STATE_SCROLLING;// 滚动完毕则直接交给子控件进行处理
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
			if (!mScroller.isFinished()) {// 如果已经滚到底了,那么停止滚动
				mScroller.abortAnimation();
			}
			mLastMotionX = x;
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_UP:
			final VelocityTracker velocityTracker = mVelocityTracker;
			velocityTracker.computeCurrentVelocity(1000);
			int velocityX = (int) velocityTracker.getXVelocity();// 方向的速度

			System.out.println("当前速率:" + velocityX);

			if (velocityX > SNAP_VELOCITY && mCurScreen > 0) {// 当前屏幕大于0，手势向右。标签栏应该向左移动

				snapToScreen(mCurScreen - 1);

			} else if (velocityX < -SNAP_VELOCITY
					&& mCurScreen < getChildCount() - 1) {// 手势向左，标签栏应该向右移动
				snapToScreen(mCurScreen + 1);

			} else {// 这种情况就是保持在第0屏

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
			// 如果x方向的滚动距离的绝对值小雨200且y方向大于10。那么停止

			if (Math.abs(deltaX) < 200 && Math.abs(deltaY) > 10)
				break;
			mLastMotionY = y;
			mLastMotionX = x;
			scrollBy(deltaX, 0);// 进行滚动，在x方向上进行滚动

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
					Math.abs(delta) * 1);// 持续滚动时间 以毫秒为单位
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
	 * 设置屏幕切换监听器
	 * 
	 * @param listener
	 */
	public void SetOnViewChangeListener(OnViewChangeListener listener) {
		mOnViewChangeListener = listener;
	}

	/**
	 * 屏幕切换监听器
	 * 
	 * @author liux
	 */
	public interface OnViewChangeListener {
		public void OnViewChange(int view);
	}

	/**
	 * 计算滚动。并刷新
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
