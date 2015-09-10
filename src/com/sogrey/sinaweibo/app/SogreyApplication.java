/**
 * @author Sogrey
 * @date 2015-9-9 下午4:28:33
 */
package com.sogrey.sinaweibo.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sogrey.sinaweibo.BuildConfig;
import com.sogrey.sinaweibo.R;

/**
 * @author Sogrey
 * @date 2015-9-9 下午4:28:33
 */
public class SogreyApplication extends Application {
	// 屏幕像素;
	public static int wpx = 0;
	public static int hpx = 0;

	private RequestQueue mRequestQueue;

	private static SogreyApplication sInstance;

	private static Context applicationContext;

	public static final String TAG = "BaseApplication";

	@Override
	public void onCreate() {
		super.onCreate();
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		WindowManager mWm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		mWm.getDefaultDisplay().getMetrics(mDisplayMetrics);
		wpx = mDisplayMetrics.widthPixels;
		hpx = mDisplayMetrics.heightPixels;
		sInstance = this;
		applicationContext = this.getApplicationContext();

		// 极光
		// JPushInterface.setDebugMode(true); // 设置开启日志,发布时请关闭日志
		// JPushInterface.init(this); // 初始化 JPush

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.empty_photo)
				.showImageOnFail(R.drawable.empty_photo).cacheInMemory(true)
				.cacheOnDisc(true).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.defaultDisplayImageOptions(defaultOptions)
				.discCacheSize(50 * 1024 * 1024)//
				.discCacheFileCount(100)// 缓存一百张图片
				.writeDebugLogs().build();
		ImageLoader.getInstance().init(config);
		if (!BuildConfig.DEBUG) {
			CrashHandler.getInstance().init(getApplicationContext());// 发布后开启，初始化异常监控
		}
	}

	public static Context getContext() {
		return applicationContext;
	}

	public static synchronized SogreyApplication getInstance() {
		return sInstance;
	}

	public RequestQueue getRequestQueue() {

		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(this);
		}
		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		VolleyLog.d("Adding request to queue: %s", req.getUrl());
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		addToRequestQueue(req, null);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
}
