/**
 * @author Sogrey
 * @date 2015年6月26日
 */
package com.sogrey.sinaweibo.utils.http;

import java.util.List;

/**
 * HTTP请求/解析监听器
 * 
 * @author Sogrey
 * @date 2015年6月26日
 */
public interface HttpResponsListener {

	/**
	 * 转发出文本
	 * 
	 * @param tag
	 *            各TAG
	 * @param text
	 *            JSON字符串
	 */
	void onHttpResponsJsonString(String tag, String text);

	/**
	 * 解析实体
	 * 
	 * @param tag
	 *            各TAG
	 * @param <T>
	 *            对象实体model
	 * @param t
	 *            解析出来的对象实体
	 */
	<T> void onHttpResponsParseBean(String tag, T t);

	/**
	 * 解析集合
	 * 
	 * @param tag
	 *            各TAG
	 * @param <T>
	 *            对象实体model
	 * @param list
	 *            解析出来的对象集合
	 */
	<T> void onHttpResponsParseListBean(String tag, List<T> list);

	/**
	 * 解析失败
	 * 
	 * @param tag
	 *            各TAG
	 */
	void onHttpResponsParseJsonErr(String tag);

	/**
	 * 1.转发异常信息，tag：为异常TAG<br>
	 * 2.异步请求开始与结束监控
	 * 
	 * @param tag
	 *            异常TAG/异步开始与结束TAG
	 * @param errMsg
	 *            异常信息/异步开始与结束 errMsg 为空
	 */
	void onHttpResponsErr(String tag, String errMsg);
}
