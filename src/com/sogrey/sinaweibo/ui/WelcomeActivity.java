/**
 * @author Sogrey
 * @date 2015-9-9 下午4:30:35
 */
package com.sogrey.sinaweibo.ui;

import android.view.KeyEvent;

import com.sogrey.sinaweibo.R;
import com.sogrey.sinaweibo.ui.base.SwipeBackActivity;

/**
 * @author Sogrey
 * @date 2015-9-9 下午4:30:35
 */
public class WelcomeActivity extends SwipeBackActivity {

	@Override
	public int setLayout_1() {
		return R.layout.activity_login;
	}

	@Override
	public void initViews_3() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initDatas_5() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			WelcomeActivity.this.finish();
		}
		return true;
	}
}
