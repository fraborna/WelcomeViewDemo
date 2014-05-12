package com.fraborna.welcomeviewdemo;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ArrayList<View> viewsList = new ArrayList<View>();
	private int currentItem = 0;
	private int flaggingWidth;
	private GestureDetector gestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		// 获取分辨率
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 定义左滑的宽度，到达最后一页时，左滑可以进入应用
		flaggingWidth = dm.widthPixels / 3;

		// 识别手势
		gestureDetector = new GestureDetector(this, new MyGestureListener());

		LayoutInflater layoutInflater = getLayoutInflater();

		View item1 = layoutInflater.inflate(R.layout.guide_image_item, null);
		View item2 = layoutInflater.inflate(R.layout.guide_image_item, null);
		View item3 = layoutInflater.inflate(R.layout.guide_image_item, null);

		ImageView imageView = (ImageView) item1.findViewById(R.id.guide_image);
		imageView.setBackgroundResource(R.drawable.image1);

		imageView = (ImageView) item2.findViewById(R.id.guide_image);
		imageView.setBackgroundResource(R.drawable.image2);

		imageView = (ImageView) item3.findViewById(R.id.guide_image);
		imageView.setBackgroundResource(R.drawable.image3);
		Button enterButton = (Button) item3.findViewById(R.id.enter_button);
		enterButton.setVisibility(View.VISIBLE);
		enterButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "进入你的应用吧~~~",
						Toast.LENGTH_SHORT).show();
			}
		});

		viewsList.add(item1);
		viewsList.add(item2);
		viewsList.add(item3);

		ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(new MyAdapter());
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				currentItem = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// 按回车键能跳过欢迎页，直接进入应用
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Toast.makeText(MainActivity.this, "进入你的应用吧~~~", Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if (gestureDetector.onTouchEvent(ev)) {
			ev.setAction(MotionEvent.ACTION_CANCEL);
		}
		return super.dispatchTouchEvent(ev);
	}

	class MyGestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// 到达最后一页且左滑到一定宽度，进入应用
			if (currentItem == 2) {
				if (Math.abs(e1.getX() - e2.getX()) > Math.abs(e1.getY()
						- e2.getY())
						&& e1.getX() - e2.getX() >= flaggingWidth) {
					Toast.makeText(MainActivity.this, "进入你的应用吧~~~",
							Toast.LENGTH_SHORT).show();
					return true;
				}
			}
			return false;
		}
	}

	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return viewsList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView(viewsList.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			container.addView(viewsList.get(position));
			return viewsList.get(position);
		}

	}

}
