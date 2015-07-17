package com.tsu.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * 可以拖动的ImageView，此为上下拖动，可根据需求修改为全屏幕拖动
 * @author dds
 *
 */
public class GragUpDownImagView extends ImageView{

	private int mPreviousx = 0;
	private int mPreviousy = 0;
	private boolean flag =false;
	public GragUpDownImagView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int iAction = event.getAction();
		final int iCurrentx = (int) event.getX();
		final int iCurrenty = (int) event.getY();
		switch (iAction) {
		case MotionEvent.ACTION_DOWN:
			mPreviousx = iCurrentx;
			mPreviousy = iCurrenty;
			break;
		case MotionEvent.ACTION_MOVE:
			flag = true;
			int iDeltx = iCurrentx - mPreviousx;
			int iDelty = iCurrenty - mPreviousy;
			final int iLeft = getLeft();
			final int iTop = getTop();
			if (iDeltx != 0 || iDelty != 0)
				layout(iLeft, iTop + iDelty, iLeft + getWidth(), iTop + iDelty
						+ getHeight());

			mPreviousx = iCurrentx - iDeltx;
			mPreviousy = iCurrenty - iDelty;
			return true;
		case MotionEvent.ACTION_UP:
		
			break;
		}

		if(flag){
			flag =false;
			return true;
		}else{
			return super.onTouchEvent(event);
		}
		
	}
	
	public void setOnClick(View.OnClickListener clickListener){
		this.setOnClickListener(clickListener);
	}
}
