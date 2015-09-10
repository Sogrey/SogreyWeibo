/**
 * @author Sogrey
 * @date 2015年6月26日
 */
package com.sogrey.sinaweibo.utils.http;

/**
 * @author Sogrey
 * @date 2015年6月26日
 */
public class HttpTags {
	/** 网络异常 */
	public static final String _NETWORK_EXCEPTION = "NetworkException";
	/** IO异常 */
	public static final String _IO_EXCEPTION = "IOException";
	/**
	 * <pre>
	 * 这个异常一般有两种原因导致：
	 * 	1、URL协议、格式或者路径错误， 好好检查下你程序中的代码
	 * 	如果是路径问题，最好不要包含中文路径，因为有时中文路径会乱码，导致无法识别
	 * 
	 * 	2、jar问题：用jdom解析xml文件，如果应用路径里有gnujaxp.jar包的话，jdom就会  
	 * 	调用它去解析xml文件，导致上述异常的出现。
	 * </pre>
	 */
	public static final String _MALFORMED_URL_EXCEPTION = "MalformedURLException";
	/** ProtocolException */
	public static final String _PROTOCOL_EXCEPTION = "ProtocolException";
	/** 请求数据异步开启 */
	public static final String _LOADING = "Loading";
	/** 请求数据异步完成 */
	public static final String _LOADED = "Loaded";
	// ======================以下为各接口TAG==================================
	/** 登录 */
	public static final String TAG_LOGIN = "Login";
}
