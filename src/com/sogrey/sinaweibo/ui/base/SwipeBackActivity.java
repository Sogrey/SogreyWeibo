package com.sogrey.sinaweibo.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.sogrey.sinaweibo.R;
import com.sogrey.sinaweibo.views.SwipeBackLayout;

/**
 * 想要实现向右滑动删除Activity效果只需要继承SwipeBackActivity即可，如果当前页面含有ViewPager
 * 只需要调用SwipeBackLayout的addViewPager()方法即可
 * @author Sogrey
 * @date 2015-9-10 下午3:46:57
 */
public abstract class SwipeBackActivity extends BaseActivity {
	protected SwipeBackLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
				R.layout.swipe_back_base_layout, null);
		layout.attachToActivity(this);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.base_slide_right_in,
				R.anim.base_slide_remain);
	}

	// Press the back button in mobile phone
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, R.anim.base_slide_right_out);
	}

	@Override
	public abstract int setLayout_1();

	@Override
	public abstract void initViews_3();

	@Override
	public abstract void initDatas_5();

}
