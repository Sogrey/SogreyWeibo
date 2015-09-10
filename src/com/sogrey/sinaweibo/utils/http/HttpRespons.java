/**
 * @author Sogrey
 * @date 2015年6月26日
 */
package com.sogrey.sinaweibo.utils.http;


/**
 * @author Sogrey
 * @date 2015年6月26日
 */
public class HttpRespons {
	/** URL */
	public String urlString = "";
	/** 默认端口 */
	public int defaultPort;
	/** 域名 */
	public String host = "";
	/** 被请求服务器文件路径（相对路径，加上域名前缀host为完整路径），一般与@path一样 */
	public String file = "";
	/** 被请求服务器路径（相对路径，加上域名前缀host为完整路径），一般与@file一样 */
	public String path = "";
	public int port = 0;
	/** 协议 :例如 HTTP */
	public String protocol = "";
	public String query = "";
	public String ref = "";
	/** 用户信息 */
	public String userInfo = "";
	/** 原生响应体 */
	public String content = "";
	/** 原生响应体编码（utf-8等） */
	public String contentEncoding = "";
	/** 响应码-200表示成功 */
	public int code = 0;
	/** 响应信息-ok表示成功 */
	public String message = "";
	/** 内容类型：例如 JSON：contentType="application/json; charset=UTF-8" */
	public String contentType = "";
	/** 方法 -POST/GET */
	public String method = "";
	/** 连接超时 */
	public int connectTimeout = 0;
	/** 读取超时 */
	public int readTimeout = 0;

	public HttpRespons() {
	}

	@Override
	public String toString() {
		return "HttpRespons [urlString=" + urlString + ",\ndefaultPort="
				+ defaultPort + ", \nfile=" + file + ",\nhost=" + host
				+ ",\npath=" + path + ", \nport=" + port + ", \nprotocol="
				+ protocol + ", \nquery=" + query + ",\nref=" + ref
				+ ", \nuserInfo=" + userInfo + ",\ncontent=" + content
				+ ",\ncontentEncoding=" + contentEncoding + ", \ncode=" + code
				+ ",\nmessage=" + message + ",\ncontentType=" + contentType
				+ ", \nmethod=" + method + ", \nconnectTimeout="
				+ connectTimeout + ", \nreadTimeout=" + readTimeout + "]";
	}

}
