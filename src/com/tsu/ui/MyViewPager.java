package com.tsu.ui;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.tsu.utils.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 1.设置viewpager adapter 2.设置小圆点 3.定时滚动 4.处理点击事件
 * 5.viewPager能够无限左右循环的原理在于：我设置条目为Integer.MAX_VALUE个view。当前为第50个
 * 6.当用户拖动viewPager时取消定时器，当拖动结束时，继续自动滚动
 * 
 * @author dds
 * 
 */


public class MyViewPager extends RelativeLayout {

	private ViewPager ad_Pager;
	private LinearLayout circle_layout;
	private List<ImageView> views;
	private Context context;
	private int len;
	private LayoutInflater layoutInflater;
	private Timer timer;
	private TimerTask timerTask;
	private int position = 50;
	private boolean isDrag = false;
	private long time;
	private PageViewOnClickListener listener;

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.layout_view_pager, this, true);
		ad_Pager = (ViewPager) view.findViewById(R.id.pager);
		circle_layout = (LinearLayout) findViewById(R.id.cicle_layout);
		this.context = context;
	}

	/**
	 * 设置adapter
	 * @param views view
	 * @param time 滚动时间，如果不需要自动滚动设为 -1
	 * @param listener 点击事件
	 * @param isScroll 是否需要无限滚动
	 */
	public void setAdapter(List<ImageView> views, long time,
			PageViewOnClickListener listener,boolean isScroll) {
		this.listener = listener;
		this.views = views;
		this.time = time;
		len = views.size();
		for (int i = 0; i < len; i++) {

			ImageView circle_view = new ImageView(context);
			circle_view.setImageResource(R.drawable.point02);
			android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(
					android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
					android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(5, 0, 0, 0);
			circle_view.setLayoutParams(params);
			circle_layout.addView(circle_view);
		}
		if (listener != null)
			for (int i = 0; i < views.size(); i++) {
				final int j = i;
				views.get(i).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						MyViewPager.this.listener.onClick(ad_Pager, j);
					}
				});
			}
		
		int circle_choice ;
		if(isScroll){
			ad_Pager.setCurrentItem(position);
			circle_choice = 100 % len;
			ad_Pager.setAdapter(new MyScrollPagerAdapter());
			ad_Pager.setOnPageChangeListener(new MyScrollPageChangeListener());
		}else{
			ad_Pager.setCurrentItem(0);
			circle_choice = 0;
			ad_Pager.setAdapter(new MyPagerAdapter());
			time = -1;
			ad_Pager.setOnPageChangeListener(new MyPageChangeListener());
		}
		
		
		ImageView imageView = (ImageView) circle_layout
				.getChildAt(circle_choice);
		imageView.setImageResource(R.drawable.point01);
		if(time!=-1){
			this.startTimerTask(time);
		}
		
	}

	private void startTimerTask(long time) {
		// TODO Auto-generated method stub
		this.time = time;
		timer = new Timer();
		timerTask = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				position++;
				handler.sendEmptyMessage(1);
			}
		};
		timer.schedule(timerTask, 2000, time);
	}

	public interface PageViewOnClickListener {
		void onClick(ViewPager ad_Pager, int position);
	}

	/**
	 * adapter
	 * 
	 * @author dds
	 * 
	 */
	private final class MyScrollPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 100;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return (arg0 == arg1);
		}


		@Override
		public Object instantiateItem(View container, int position) {
			View view = views.get(position % len);
			try {
				if (((ViewPager) container).getChildCount() == len) {
					((ViewPager) container).removeView(view);
				}

				((ViewPager) container).addView(view, 0);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return (len > 0) ? view : null;

		}

		@Override
		public void destroyItem(View arg0, int position, Object arg2) {

		}

	}

	/**
	 * adapter
	 * 
	 * @author dds
	 * 
	 */
	private final class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return (arg0 == arg1);
		}


		@Override
		public Object instantiateItem(View container, int position) {
			View view = views.get(position);
			return view;

		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object arg2) {
			container.removeView(container.getChildAt(position));
		}

	}
	
	private final class MyScrollPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
			if (state == ViewPager.SCROLL_STATE_DRAGGING) {
				isDrag = true;
				timer.cancel();
			} else if (state == ViewPager.SCROLL_STATE_IDLE) {
				if (isDrag) {
					position = ad_Pager.getCurrentItem();
					startTimerTask(time);
					isDrag = false;
				}
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int position) {
			int circle_choice = position % len;
			ImageView circle_view = (ImageView) circle_layout
					.getChildAt(circle_choice);
			circle_view.setImageResource(R.drawable.point01);
			for (int i = 0; i < len; i++) {
				if (i != circle_choice) {
					ImageView view = (ImageView) circle_layout.getChildAt(i);
					view.setImageResource(R.drawable.point02);
				}
			}
		}

	}

	private final class MyPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int position) {
			int circle_choice = position;
			ImageView circle_view = (ImageView) circle_layout
					.getChildAt(circle_choice);
			circle_view.setImageResource(R.drawable.point01);
			for (int i = 0; i < len; i++) {
				if (i != circle_choice) {
					ImageView view = (ImageView) circle_layout.getChildAt(i);
					view.setImageResource(R.drawable.point02);
				}
			}
		}

	}
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			ad_Pager.setCurrentItem(position);
		};
	};

}
