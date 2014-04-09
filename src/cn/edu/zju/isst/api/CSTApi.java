/**
 * 
 */
package cn.edu.zju.isst.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import cn.edu.zju.isst.net.BetterAsyncWebServiceRunner;
import cn.edu.zju.isst.net.CSTResponse;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.util.L;

/**
 * 接口基类
 * 
 * @author theasir
 * 
 */
public class CSTApi {

	/**
	 * 服务器前缀地址
	 */
	// private static final String PREFIX = "http://www.zjucst.com";
	private static final String PREFIX = "http://yplan.cloudapp.net:8080/isst";

	/**
	 * 发送请求
	 * 
	 * @param methodName
	 *            方法类别
	 * @param subUrl
	 *            子网址
	 * @param params
	 *            要传递的参数
	 * @param listener
	 *            回调对象
	 */
	protected static void request(final String methodName, final String subUrl,
			final Map<String, String> params, RequestListener listener) {
		String url = PREFIX + subUrl;
		BetterAsyncWebServiceRunner.getInstance().request(methodName, url,
				params, listener);
		L.i("CSTApi Request URL = " + url);
	}

	protected static CSTResponse responseOfRequest(final String methodName,
			final String subUrl, final Map<String, String> params)
			throws MalformedURLException, IOException {
		String url = PREFIX + subUrl;
		return BetterAsyncWebServiceRunner.getInstance().responseOfRequest(
				methodName, url, params);
	}
}
