package com.sartino.huayi.app.ui.widgets;

import com.sartino.huayi.app.adapter.GridViewMenuListAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

public class DragGrid extends GridView {

	private int dragPosition;// ��קλ��
	private int dropPosition;// ����λ��
	private int holdPosition;// ����λ��
	private int startPosition;// ��ʼλ��
	private int specialPosition = -1;// ����λ��
	private int leftBottomPosition = -1;// ���½�λ��

	private int nColumns = 2;
	private int nRows;
	private int Remainder;

	private int itemTotalCount;
	private int halfItemWidth;

	private ImageView dragImageView = null;
	private ViewGroup dragItemView = null;

	private WindowManager windowManager = null;
	private WindowManager.LayoutParams windowParams = null;

	private int mLastX, xtox;// ���µ�������Ϣ���Լ��ƶ�����
	private int mLastY, ytoy;
	private int specialItemY;
	private int leftBtmItemY;

	private String LastAnimationID;

	private boolean isCountXY = false;
	private boolean isMoving = false;

	public DragGrid(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DragGrid(Context context) {
		super(context);
	}

	boolean flag = false;

	public void setLongFlag(boolean temp) {
		flag = temp;
	}

	public boolean setOnItemLongClickListener(final MotionEvent ev) {
		this.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {// ���佻��������¼����д���
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				// ��ȡ���°�������
				int x = (int) ev.getX();
				int y = (int) ev.getY();
				mLastX = x;
				mLastY = y;

				startPosition = dragPosition = dropPosition = arg2;// ���浱ǰ���������Ŀ
				if (dragPosition == AdapterView.INVALID_POSITION) {// �Ƿ�Ϊ�Ϸ�λ��

				}

				ViewGroup itemView = (ViewGroup) getChildAt(dragPosition
						- getFirstVisiblePosition());// ��ȡ��Ŀ

				if (!isCountXY) {// ��������������λ�ã��Լ����һ�е���Ŀ
					halfItemWidth = itemView.getWidth() / 2;
					int rows;
					itemTotalCount = getCount();

					rows = itemTotalCount / nColumns;
					Remainder = itemTotalCount % nColumns;
					nRows = Remainder == 0 ? rows : rows + 1;
					specialPosition = itemTotalCount - 1 - Remainder;
					if (Remainder != 1)
						leftBottomPosition = nColumns * (nRows - 1);// ���½�Ԫ��
					isCountXY = true;
				}

				if (specialPosition != dragPosition && dragPosition != -1) {
					specialItemY = getChildAt(specialPosition).getTop();
				} else {

				}

				if (leftBottomPosition != dragPosition && dragPosition != -1) {
					leftBtmItemY = getChildAt(leftBottomPosition).getTop();
				} else {
					leftBtmItemY = -1;
				}

				dragItemView = itemView;
				itemView.destroyDrawingCache();
				itemView.setDrawingCacheEnabled(true);
				itemView.setDrawingCacheBackgroundColor(0x000000);
				Bitmap bm = Bitmap.createBitmap(itemView.getDrawingCache(true));
				Bitmap bitmap = Bitmap.createBitmap(bm, 8, 8,
						bm.getWidth() - 8, bm.getHeight() - 8);
				startDrag(bitmap, x, y);
				hideDropItem();
				itemView.setVisibility(View.INVISIBLE);
				isMoving = false;
				return false;
			};
		});

		return super.onInterceptTouchEvent(ev);
	}

	public void GetItemShadow(int x, int y) {

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {// �����down�¼�
			return setOnItemLongClickListener(ev);
		}
		return super.onInterceptTouchEvent(ev);// ����ontouch�¼�����
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (dragImageView != null
				&& dragPosition != AdapterView.INVALID_POSITION) {// ����϶�view��λ�úϷ�
			// ���浱ǰ��λ��
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			switch (ev.getAction()) {
			case MotionEvent.ACTION_MOVE:// �ƶ�
				if (!isCountXY) {// ������x��y�����ϵ��ƶ����룬������־λ����Ϊ��
					xtox = x - mLastX;
					ytoy = y - mLastY;
					isCountXY = true;
				}
				onDrag(x, y);// �����϶��¼�,ÿ�ε��ö�����д���
				if (!isMoving)// ���û���ƶ�����ʼ�ƶ�
					OnMove(x, y);
				break;
			case MotionEvent.ACTION_UP:
				stopDrag();// ֹͣ�϶�
				onDrop(x, y);// ����
				break;
			}
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * ��ʼ�϶� �û�����һ����Ŀ��ʱ��
	 * 
	 * �������һ���϶�imageView��Ȼ���䱣������
	 * 
	 * @param bm
	 * @param x
	 * @param y
	 */
	private void startDrag(Bitmap bm, int x, int y) {
		stopDrag();
		windowParams = new WindowManager.LayoutParams();
		windowParams.gravity = Gravity.TOP | Gravity.LEFT;
		windowParams.x = dragItemView.getLeft() + 8;
		windowParams.y = dragItemView.getBottom() + 8;
		windowParams.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		windowParams.width = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		windowParams.alpha = 0.5f;

		ImageView iv = new ImageView(getContext());
		iv.setImageBitmap(bm);
		windowManager = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		windowManager.addView(iv, windowParams);

		dragImageView = iv;
	}

	/**
	 * �ƶ�
	 * 
	 * @param x
	 * @param y
	 */
	public void OnMove(int x, int y) {
		int TempPosition = pointToPosition(x, y);// ��һ����ӳ�䵽list��
		int sOffsetY = specialItemY == -1 ? y - mLastY : y - specialItemY
				- halfItemWidth;
		int lOffsetY = leftBtmItemY == -1 ? y - mLastY : y - leftBtmItemY
				- halfItemWidth;
		if (TempPosition != AdapterView.INVALID_POSITION
				&& TempPosition != dragPosition) {
			dropPosition = TempPosition;
		} else if (specialPosition != -1 && dragPosition == specialPosition
				&& sOffsetY >= halfItemWidth) {
			dropPosition = (itemTotalCount - 1);
		} else if (leftBottomPosition != -1
				&& dragPosition == leftBottomPosition
				&& lOffsetY >= halfItemWidth) {
			dropPosition = (itemTotalCount - 1);
		}
		if (dragPosition != startPosition)
			dragPosition = startPosition;
		int MoveNum = dropPosition - dragPosition;
		if (dragPosition != startPosition && dragPosition == dropPosition)
			MoveNum = 0;
		if (MoveNum != 0) {
			int itemMoveNum = Math.abs(MoveNum);
			float Xoffset, Yoffset;
			for (int i = 0; i < itemMoveNum; i++) {
				if (MoveNum > 0) {
					holdPosition = dragPosition + 1;
					Xoffset = (dragPosition / nColumns == holdPosition
							/ nColumns) ? (-1) : (nColumns - 1);
					Yoffset = (dragPosition / nColumns == holdPosition
							/ nColumns) ? 0 : (-1);
				} else {
					holdPosition = dragPosition - 1;
					Xoffset = (dragPosition / nColumns == holdPosition
							/ nColumns) ? 1 : (-(nColumns - 1));
					Yoffset = (dragPosition / nColumns == holdPosition
							/ nColumns) ? 0 : 1;
				}
				ViewGroup moveView = (ViewGroup) getChildAt(holdPosition);
				Animation animation = getMoveAnimation(Xoffset, Yoffset);
				moveView.startAnimation(animation);
				dragPosition = holdPosition;
				if (dragPosition == dropPosition)
					LastAnimationID = animation.toString();
				final GridViewMenuListAdapter adapter = (GridViewMenuListAdapter) this.getAdapter();
				animation
						.setAnimationListener(new Animation.AnimationListener() {

							@Override
							public void onAnimationStart(Animation animation) {
								isMoving = true;
							}

							@Override
							public void onAnimationRepeat(Animation animation) {
							}

							@Override
							public void onAnimationEnd(Animation animation) {
								String animaionID = animation.toString();
								if (animaionID
										.equalsIgnoreCase(LastAnimationID)) {
									adapter.exchange(startPosition,
											dropPosition);
									startPosition = dropPosition;
									isMoving = false;
								}
							}
						});
			}
		}
	}

	/**
	 * ����
	 * 
	 * @param x
	 * @param y
	 */
	private void onDrop(int x, int y) {
		final GridViewMenuListAdapter adapter = (GridViewMenuListAdapter) this.getAdapter();
		adapter.showDropItem(true);
		adapter.notifyDataSetChanged();
	}

	private void onDrag(int x, int y) {
		if (dragImageView != null) {
			windowParams.alpha = 0.8f;
			windowParams.x = (x - mLastX - xtox) + dragItemView.getLeft() + 8;// ����x
			windowParams.y = y
					+ (dragItemView.getBottom() - dragItemView.getTop()) / 2;// ����y
			windowManager.updateViewLayout(dragImageView, windowParams);// ���²���
		}
	}

	/**
	 * ֹͣ�϶� ��imageview�Ӵ�����ɾ��
	 */
	private void stopDrag() {
		if (dragImageView != null) {
			windowManager.removeView(dragImageView);
			dragImageView = null;
		}
	}

	private void hideDropItem() {
		final GridViewMenuListAdapter adapter = (GridViewMenuListAdapter) this.getAdapter();
		adapter.showDropItem(false);
	}

	public Animation getMoveAnimation(float x, float y) {
		TranslateAnimation go = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, x,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, y);
		go.setFillAfter(true);
		go.setDuration(300);
		return go;
	}

}
