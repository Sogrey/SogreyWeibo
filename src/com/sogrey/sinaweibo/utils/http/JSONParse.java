/**
 * @author Sogrey
 * @date 2015年6月26日
 */
package com.sogrey.sinaweibo.utils.http;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.text.TextUtils;

/**
 * @author Sogrey
 * @date 2015年6月26日
 */
public class JSONParse {
	private Gson mGson;
	@SuppressWarnings("rawtypes")
	private LinkedList<GetListBeansAsyn> _mGetListBeansAsyns = new LinkedList<JSONParse.GetListBeansAsyn>();
	@SuppressWarnings("rawtypes")
	private LinkedList<GetBeanAsyn> _mGetBeanAsyns = new LinkedList<JSONParse.GetBeanAsyn>();

	/** 单例模式 对象 */
	private static JSONParse sInstance;

	/**
	 * 单例模式 <br>
	 * 一个类最多只能有一个实例 <br>
	 * 1、有一个私有静态成员 <br>
	 * 2、有一个公开静态方法getInstance得到这个私有静态成员 <br>
	 * 3、有一个私有的构造方法（不允许被实例化） <br>
	 */

	public static JSONParse getInstance() {
		if (sInstance == null) {
			synchronized (JSONParse.class) {
				if (sInstance == null) {
					sInstance = new JSONParse();
				}
			}
		}
		return sInstance;
	}

	private JSONParse() {
		mGson = new Gson();
	}

	/**
	 * 对象转JSON字符串
	 * 
	 * @param bean
	 * @return
	 */
	public <T> String Bean2Json(T bean) {
		try {
			return mGson.toJson(bean);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * JSON字符串转对象
	 * 
	 * @param json
	 * @param classOfT
	 * @return
	 */
	public <T> Object Json2Bean(String json, Class<T> classOfT) {
		try {
			return mGson.fromJson(json, classOfT);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 生成字符串;
	 * 
	 * @param text
	 * @return
	 */
	public static String generateJson(String text, String key) {
		String json = "";
		try {
			JSONObject obj = new JSONObject(text);
			json = obj.getString(key).toString().trim();
		} catch (Exception e) {
			json = "";
		}
		return json;
	}

	/**
	 * 对象集合转JSON字符串
	 * 
	 * @param beans
	 * @param date
	 * @param version
	 * @return
	 */
	public <T> String ListBean2Json(List<T> beans, String date, double version) {
		try {
			GsonBuilder builder = new GsonBuilder();
			builder.excludeFieldsWithoutExposeAnnotation();// 不导出实体中没有@Expose
			builder.enableComplexMapKeySerialization();// 支持map key 更为复杂的格式
			builder.serializeNulls().setDateFormat(date);
			builder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);// 字段首字母大写，@SerializedName注解的不会生效
			builder.setPrettyPrinting();// 格式化输出结果;
			builder.setVersion(version);
			mGson = builder.create();
			return mGson.toJson(beans);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * JSON字符串转对象集合
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public <T> List<T> Json2ListBean(String json, Class<T[]> clazz) {
		List<T> mlist = null;
		try {
			T[] arr = mGson.fromJson(json, clazz);
			return Arrays.asList(arr);
		} catch (Exception e) {
			e.printStackTrace();
			return mlist;
		}
	}

	/**
	 * 异步解析JSON-解析集合<br>
	 * 须先调用generateJson(text, key)找到集合
	 * 
	 * @author Sogrey
	 * @date 2015年6月26日
	 * @param flag
	 *            数据结果类型：DATA_FLAG.BEAN:实体对象，DATA_FLAG.LIST_BEAN:实体对象集合
	 * @param tag
	 * @param json
	 *            JSON字符串
	 * @param classOfT
	 *            集合中item的model
	 * @param _listener
	 *            监听器
	 */
	public <T> void parseListBeansInAsyncTask(String tag, String json,
			Class<T[]> classOfT, HttpResponsListener _listener) {
		try {
			GetListBeansAsyn<T> asyn = new GetListBeansAsyn<T>(tag, json,
					classOfT, _listener);
			_mGetListBeansAsyns.add(asyn);
			asyn.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 异步解析JSON-解析实体对象<br>
	 * 
	 * @author Sogrey
	 * @date 2015年6月29日
	 * @param flag
	 *            数据结果类型：DATA_FLAG.BEAN:实体对象，DATA_FLAG.LIST_BEAN:实体对象集合
	 * @param tag
	 * @param json
	 *            JSON字符串
	 * @param classOfT
	 *            集合中item的model
	 * @param _listener
	 *            监听器
	 */
	public <T> void parseBeanInAsyncTask(String tag, String json,
			Class<T> classOfT, HttpResponsListener _listener) {
		try {
			GetBeanAsyn<T> asyn = new GetBeanAsyn<T>(tag, json, classOfT,
					_listener);
			_mGetBeanAsyns.add(asyn);
			asyn.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析集合异步
	 * 
	 * @author Sogrey
	 * @date 2015年6月29日
	 * @param <T>
	 */
	private class GetListBeansAsyn<T> extends AsyncTask<Void, Void, Boolean> {
		private String _tag = "";// 任务tag标识，区分异步任务
		private String _json = "";// 请求回来的json字符串
		private Class<T[]> _classOfT;// 解析的目标对象modle
		private HttpResponsListener _listener;// 监听器

		private List<T> _jsonListBean;// 目标集合

		public GetListBeansAsyn(String tag, String json, Class<T[]> classOfT,
				HttpResponsListener listener) {
			this._tag = tag;
			this._json = json;
			this._classOfT = classOfT;
			this._listener = listener;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			System.out.println("开始解析....");
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			_jsonListBean = Json2ListBean(this._json, this._classOfT);
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			System.out.println("解析完成...");
			if (_jsonListBean != null) {// 成功解析
				if (this._listener != null) {
					this._listener.onHttpResponsParseListBean(_tag,
							_jsonListBean);
				}
			} else {// 解析失败
				if (this._listener != null) {
					this._listener.onHttpResponsParseJsonErr(_tag);
				}
			}
			if (_listener != null)
				_listener.onHttpResponsErr(HttpTags._LOADED, "");// 异步请求结束
			this.cancel(true);
		}
	}

	/**
	 * 解析实体对象异步
	 * 
	 * @author Sogrey
	 * @date 2015年6月29日
	 * @param <T>
	 */
	private class GetBeanAsyn<T> extends AsyncTask<Void, Void, Boolean> {
		private String _tag = "";// 任务tag标识，区分异步任务
		private String _json = "";// 请求回来的json字符串
		private Class<T> _classOfT;// 解析的目标对象modle
		private HttpResponsListener _listener;// 监听器

		private Object _jsonBean;// 目标实体

		public GetBeanAsyn(String tag, String json, Class<T> classOfT,
				HttpResponsListener listener) {
			this._tag = tag;
			this._json = json;
			this._classOfT = classOfT;
			this._listener = listener;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			System.out.println("开始解析....");
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			_jsonBean = Json2Bean(this._json, this._classOfT);
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			System.out.println("解析完成...");
			if (_jsonBean != null) {// 成功解析
				if (this._listener != null) {
					this._listener.onHttpResponsParseBean(_tag, _jsonBean);
				}
			} else {// 解析失败
				if (this._listener != null) {
					this._listener.onHttpResponsParseJsonErr(_tag);
				}
			}
			if (_listener != null)
				_listener.onHttpResponsErr(HttpTags._LOADED, "");// 异步请求结束
			this.cancel(true);
		}
	}

	/**
	 * 取消指定tag异步解析任务
	 * 
	 * @param tag
	 *            异步解析任务标识tag
	 */

	@SuppressWarnings("rawtypes")
	public void cancleAsyncTask(String tag) {
		if (_mGetListBeansAsyns != null && _mGetListBeansAsyns.size() > 0) {
			for (GetListBeansAsyn asyn : _mGetListBeansAsyns) {
				if (TextUtils.equals(tag, asyn._tag)) {
					asyn.cancel(true);
				}
			}
		}
		if (_mGetBeanAsyns != null && _mGetBeanAsyns.size() > 0) {
			for (GetBeanAsyn asyn : _mGetBeanAsyns) {
				if (TextUtils.equals(tag, asyn._tag)) {
					asyn.cancel(true);
				}
			}
		}
	}

	/**
	 * 取消所有异步解析任务
	 */
	@SuppressWarnings("rawtypes")
	public void cancleAllAsynctask() {
		if (_mGetListBeansAsyns != null && _mGetListBeansAsyns.size() > 0) {
			for (GetListBeansAsyn asyn : _mGetListBeansAsyns) {
				asyn.cancel(true);
			}
			_mGetListBeansAsyns.clear();
		}
		if (_mGetBeanAsyns != null && _mGetBeanAsyns.size() > 0) {
			for (GetBeanAsyn asyn : _mGetBeanAsyns) {
				asyn.cancel(true);
			}
			_mGetBeanAsyns.clear();
		}
	}
}
