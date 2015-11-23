package com.zykj.loveattention.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.zykj.loveattention.R;
import com.zykj.loveattention.base.BaseActivity;
import com.zykj.loveattention.data.AppValue;
import com.zykj.loveattention.utils.Tools;

/**
 * @author lss 2015年8月8日 	引导图
 *
 */
public class A1_IntroActivity extends BaseActivity implements OnPageChangeListener {

	private ViewPager viewPager;
	int a = 0;
	/**
	 * 图片资源id
	 */
	private int[] imgIdArray;

	private ImageView[] mImageViews;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_a1_intro);
	}

	public void initView(int viewId) {
		super.initView(viewId);

		imgIdArray = new int[] { R.drawable.introduce_1,R.drawable.introduce_2, R.drawable.introduce_3 };

//		ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
		viewPager = (ViewPager) findViewById(R.id.viewPager);

		// // 将点点加入到ViewGroup中
		// tips = new ImageView[imgIdArray.length];
		// for (int i = 0; i < tips.length; i++) {
		// ImageView imageView = new ImageView(this);
		// imageView.setLayoutParams(new LayoutParams(10, 10));
		// tips[i] = imageView;
		// if (i == 0) {
		// tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
		// } else {
		// tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
		// }
		// LinearLayout.LayoutParams layoutParams = new
		// LinearLayout.LayoutParams(
		// new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
		// LayoutParams.WRAP_CONTENT));
		// layoutParams.leftMargin = 5;
		// layoutParams.rightMargin = 5;
		// group.addView(imageView, layoutParams);
		// }

		// 将图片装载到数组中
		mImageViews = new ImageView[imgIdArray.length];
		for (int i = 0; i < mImageViews.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setTag(i);
			imageView.setScaleType(ScaleType.FIT_XY);
//			imageView.setAdjustViewBounds(true);
			imageView.setOnClickListener(this);

			mImageViews[i] = imageView;
			imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), imgIdArray[i]));
		}

		// 设置Adapter
		viewPager.setAdapter(new MyAdapter());
		// 设置监听，主要是设置点点的背景
		viewPager.setOnPageChangeListener(this);
	}

	public class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mImageViews.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
		}
		/**
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			try {
				((ViewPager) container).addView(mImageViews[position
						% mImageViews.length], 0);

			} catch (Exception e) {
			}

			return mImageViews[position % mImageViews.length];
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		if (arg0 == mImageViews.length - 1) {// 进入下一页
			// 存储版本号
			if(a>0){

				putSharedPreferenceValue(AppValue.VERSION,
						Tools.getAppVersion(this) + "");
				// 存储已经进行过索引的标识
				putSharedPreferenceValue(AppValue.IS_INTRO, "1");
				
				Intent intent = new Intent(this, B0_MainActivity.class);
				startActivity(intent);
				finish();
			}else{
				a=a+1;
			}
		} 
	}

	@Override
	public void onPageSelected(int arg0) {
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		Tools.Log("点击事件");
		int position = (Integer) v.getTag();
		if (position == mImageViews.length - 1) {// 进入下一页
			// 存储版本号
			putSharedPreferenceValue(AppValue.VERSION,
					Tools.getAppVersion(this) + "");
			// 存储已经进行过索引的标识
			putSharedPreferenceValue(AppValue.IS_INTRO, "1");
			
			Intent intent = new Intent(this, B0_MainActivity.class);
			startActivity(intent);
			finish();
		} else {
			viewPager.setCurrentItem(position + 1);
		}
	}
}