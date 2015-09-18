/**
 * @author Sogrey
 * @date 2015-9-9 下午4:30:35
 */
package com.sogrey.sinaweibo.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.sogrey.sinaweibo.R;
import com.sogrey.sinaweibo.ui.base.SwipeBackActivity;

/**
 * @author Sogrey
 * @date 2015-9-9 下午4:30:35
 */
public class WelcomeActivity extends SwipeBackActivity {
TextView test ;
	@Override
	public int setLayout_1() {
		return R.layout.activity_welcome;
	}

	@Override
	public void initViews_3() {
		// TODO Auto-generated method stub
		test = 	(TextView) findViewById(R.id.txt_test);
	}

	@Override
	public void initDatas_5() {
		// TODO Auto-generated method stub
		test.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				joinQQGroup("dBCV4okIdk4a3jzLLDHrpNqNjDowLbUc");
			}
		});
	}

	/****************
	*
	* 发起添加群流程。群号：【游戏】风云无双 43(122641313) 的 key 为： dBCV4okIdk4a3jzLLDHrpNqNjDowLbUc
	* 调用 joinQQGroup(dBCV4okIdk4a3jzLLDHrpNqNjDowLbUc) 即可发起手Q客户端申请加群 【游戏】风云无双 43(122641313)
	*
	* @param key 由官网生成的key
	* @return 返回true表示呼起手Q成功，返回fals表示呼起失败
	******************/
	public boolean joinQQGroup(String key) {
	    Intent intent = new Intent();
	    intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
	   // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
	    try {
	        startActivity(intent);
	        return true;
	    } catch (Exception e) {
	        // 未安装手Q或安装的版本不支持
	        return false;
	    }
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			WelcomeActivity.this.finish();
		}
		return true;
	}
}
