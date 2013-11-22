package com.narkii.security;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class HVListView extends ListView {

	/** ���� */
	private GestureDetector mGesture;
	/** ��ͷ */
	public LinearLayout mListHead;
	/** ƫ������ */
	private int mOffset = 0;
	/** ��Ļ��� */
	private int screenWidth;

	/** ���캯�� */
	public HVListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGesture = new GestureDetector(context, mOnGesture);
	}

	/** �ַ������¼� */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		super.dispatchTouchEvent(ev);
		return mGesture.onTouchEvent(ev);
	}

	/** ���� */
	private OnGestureListener mOnGesture = new GestureDetector.SimpleOnGestureListener() {

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			return false;
		}

		/**
		 *  ����
		 *  @param distanceX x�᷽���ƶ����룬ע�⣬��ʱ��distanceXΪ����
		 */
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			synchronized (HVListView.this) {
				int moveX = (int) distanceX;
				int curX = mListHead.getScrollX();//���� ��|��ǰ��ͼ���Ͻ�x-����ͼ���Ͻ�x|)
				int scrollWidth = getWidth();
				int dx = moveX;
				//����Խ������
				if (curX + moveX < 0)//�һ�Խ��
					dx = 0;
				if (curX + moveX + getScreenWidth() > scrollWidth) //��Խ��
				{	dx = scrollWidth - getScreenWidth() - curX;
					Log.d("scroll", "edge:"+scrollWidth+" curx "+curX+" screenwidth "+getScreenWidth());
				}
				mOffset += dx;
				Log.d("scroll", "moffser "+mOffset+" curX "+curX);
				//�������ƹ���Item��ͼ
				for (int i = 0, j = getChildCount(); i < j; i++) {
					View child = ((ViewGroup) getChildAt(i)).getChildAt(2);
					if (child.getScrollX() != mOffset)
						child.scrollTo(mOffset, 0);
				}
				mListHead.scrollBy(dx, 0);
			}
			requestLayout();
			return true;
		}
	};

	
	/**
	 * ��ȡ��Ļ�ɼ���Χ�������Ļ
	 * @return
	 */
	public int getScreenWidth() {
		if (screenWidth == 0) {
			screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
			if (getChildAt(0) != null) {
				screenWidth -= ((ViewGroup) getChildAt(0)).getChildAt(1)
						.getMeasuredWidth();
			} else if (mListHead != null) {
				//��ȥ�̶���һ��
				screenWidth -= mListHead.getChildAt(1).getMeasuredWidth();
			}
		}
		return screenWidth;
	}

	/** ��ȡ��ͷƫ���� */
	public int getHeadScrollX() {
		return mListHead.getScrollX();
	}
}
