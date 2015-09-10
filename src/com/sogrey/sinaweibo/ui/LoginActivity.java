/**
 * @author Sogrey
 * @date 2015-9-9 下午4:31:25
 */
package com.sogrey.sinaweibo.ui;

import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.widget.LoginButton;
import com.sina.weibo.sdk.widget.LoginoutButton;
import com.sogrey.sinaweibo.R;
import com.sogrey.sinaweibo.global.AppConstans;
import com.sogrey.sinaweibo.ui.base.BaseActivity;
import com.sogrey.sinaweibo.utils.AppUtil;

/**
 * 登录页
 * 
 * @author Sogrey
 * @date 2015-9-9 下午4:31:25
 */
public class LoginActivity extends BaseActivity {
	LoginButton mBtnLogin;
	TextView mTxtVersion;
	protected Button mCurrentClickedButton;
	private TextView mTokenView;

	/** 登陆认证对应的listener */
	private AuthListener mLoginListener = new AuthListener();
	private AuthInfo mAuthInfo;

	@Override
	public int setLayout_1() {
		return R.layout.activity_login;
	}

	@Override
	public void init_2() {
		// 创建授权认证信息
		mAuthInfo = new AuthInfo(this, AppConstans.APP_KEY,
				AppConstans.REDIRECT_URL, AppConstans.SCOPE);
	}

	@Override
	public void initViews_3() {
		mBtnLogin = (LoginButton) findViewById(R.id.btn_login);
		mTxtVersion = (TextView) findViewById(R.id.txt_version_name);
		mTokenView = (TextView) findViewById(R.id.txt_token);
	}

	@Override
	public void setListener_4() {
		/**
		 * 登陆按钮（默认样式）
		 */
		mBtnLogin.setWeiboAuthInfo(mAuthInfo, mLoginListener);
		// mLoginBtnStyle2.setStyle(LoginButton.LOGIN_INCON_STYLE_1);
		/**
		 * 请注意：为每个 Button 设置一个额外的 Listener 只是为了记录当前点击的 是哪一个 Button，用于在
		 * {@link #onActivityResult} 函数中进行区分。 通常情况下，我们的应用不需要调用该函数。
		 */
		mBtnLogin.setExternalOnClickListener(mButtonClickListener);
	}

	@Override
	public void initDatas_5() {
		mTxtVersion.setText(getResources().getString(R.string.version_name,
				AppUtil.getVerName(getApplicationContext())));
	}

	/**
	 * 当 SSO 授权 Activity 退出时，该函数被调用。
	 * 
	 * @see {@link Activity#onActivityResult}
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (mCurrentClickedButton != null) {
			if (mCurrentClickedButton instanceof LoginButton) {
				((LoginButton) mCurrentClickedButton).onActivityResult(
						requestCode, resultCode, data);
			} else if (mCurrentClickedButton instanceof LoginoutButton) {
				((LoginoutButton) mCurrentClickedButton).onActivityResult(
						requestCode, resultCode, data);
			}
		}
	}

	/**
	 * 请注意：为每个 Button 设置一个额外的 Listener 只是为了记录当前点击的 是哪一个 Button，用于在
	 * {@link #onActivityResult} 函数中进行区分。 通常情况下，我们的应用不需要定义该 Listener。
	 */
	private OnClickListener mButtonClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v instanceof Button) {
				mCurrentClickedButton = (Button) v;
			}
		}
	};

	/**
	 * 登入按钮的监听器，接收授权结果。
	 */
	private class AuthListener implements WeiboAuthListener {
		@Override
		public void onComplete(Bundle values) {
			Oauth2AccessToken accessToken = Oauth2AccessToken
					.parseAccessToken(values);
			if (accessToken != null && accessToken.isSessionValid()) {
				String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
						.format(new java.util.Date(accessToken.getExpiresTime()));
				String format = getString(R.string.weibosdk_demo_token_to_string_format_1);
				mTokenView.setText(String.format(format,
						accessToken.getToken(), date));

				// AccessTokenKeeper.writeAccessToken(getApplicationContext(),
				// accessToken);
			}
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(LoginActivity.this, e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(LoginActivity.this, "取消", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 登出按钮的监听器，接收登出处理结果。（API 请求结果的监听器）
	 */
	private class LogOutRequestListener implements RequestListener {
		@Override
		public void onComplete(String response) {
			if (!TextUtils.isEmpty(response)) {
				try {
					JSONObject obj = new JSONObject(response);
					String value = obj.getString("result");

					if ("true".equalsIgnoreCase(value)) {
						// AccessTokenKeeper.clear(LoginActivity.this);
						mTokenView.setText("授权成功");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onWeiboException(WeiboException e) {
			mTokenView.setText("授权失败");
		}
	}
}
