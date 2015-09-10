/**
 * @author Sogrey
 * @date 2015-9-9 下午4:27:26
 */
package com.sogrey.sinaweibo.ui.base;

import java.util.LinkedHashMap;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.TextView;

import com.sogrey.sinaweibo.R;
import com.sogrey.sinaweibo.views.DialogUtils;

/**
 * @author Sogrey
 * @date 2015-9-9 下午4:27:26
 */
public abstract class BaseActivity extends Activity {
	private ActivityManager manager = ActivityManager.getActivityManager();

	private LinkedHashMap<String, Boolean> mLinkedMap;
	private String mLoginId = "";
	private DialogUtils mDailogUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		manager.putActivity(this);
		if (mLinkedMap == null) {
			mLinkedMap = new LinkedHashMap<String, Boolean>();
		}
		int layoutResID = setLayout_1();
		if (0 != layoutResID)
			setContentView(layoutResID);
		init_2();
		initViews_3();
		setListener_4();
		initDatas_5();
	}

	/**
	 * 设置布局xml文件ID
	 * 
	 * @author Sogrey
	 * @date 2015年5月19日
	 */
	public abstract int setLayout_1();

	/**
	 * 初始化-获取Intent参数等等
	 * 
	 * @author Sogrey
	 * @date 2015年5月19日
	 */
	public  void init_2() {
	}

	/**
	 * 布局控件初始化
	 * 
	 * @author Sogrey
	 * @date 2015年5月19日
	 */
	public abstract void initViews_3();

	/**
	 * 设置监听事件
	 * 
	 * @author Sogrey
	 * @date 2015年5月19日
	 */
	public  void setListener_4() {
	}

	/**
	 * 设置数据
	 * 
	 * @author Sogrey
	 * @date 2015年5月19日
	 */
	public abstract void initDatas_5();

	// 退出提示;
	public void showExitDialog() {
		if (mDailogUtils != null && mDailogUtils.isShowing()) {
			mDailogUtils.dismiss();
		}
		if (mDailogUtils == null) {
			mDailogUtils = new DialogUtils(this, R.style.CircularDialog) {

				@Override
				public void ok() {
					exit();
					toCancle();
				}

				@Override
				public void cancle() {
					toCancle();
				}
			};
			mDailogUtils.show();
			TextView tv = new TextView(this);
			tv.setText("确定退出程序？");
			tv.setGravity(Gravity.CENTER);
			mDailogUtils.setContent(tv);
			mDailogUtils.setDialogTitle("退出");
			mDailogUtils.setDialogCancleBtn("取消");
			mDailogUtils.setDialogOkBtn("确定");
			mDailogUtils.setDialogCancleBtnColor(getResources().getColor(
					R.color.s_dark_green));
			mDailogUtils.setDialogOkBtnColor(getResources().getColor(
					R.color.s_dark_red));
		} else {
			mDailogUtils.show();
		}
	}

	public void exit() {
		if (mLinkedMap != null) {
			mLinkedMap.clear();
			mLinkedMap = null;
		}
		manager.exit();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		manager.removeActivity(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (TextUtils.equals("", mLoginId)) {
				showExitDialog();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	public void putNetWorkFlag(String key, boolean val) {
		if (mLinkedMap == null) {
			mLinkedMap = new LinkedHashMap<String, Boolean>();
		}
		mLinkedMap.put(key, val);
	}

	public boolean getNetWorkFlag(String key, boolean val) {
		if (mLinkedMap == null) {
			mLinkedMap = new LinkedHashMap<String, Boolean>();
		}
		if (mLinkedMap.containsKey(key)) {
			val = mLinkedMap.get(key);
		} else {
			val = false;
		}
		return val;
	}

	public void clearAllNetWorkFlag() {
		if (mLinkedMap == null) {
			mLinkedMap = new LinkedHashMap<String, Boolean>();
		}
		mLinkedMap.clear();
		mLinkedMap = null;
	}

	public void setLoginId(String content) {
		mLoginId = content;
	}
}
