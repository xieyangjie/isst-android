package cn.edu.zju.isst.api;

import java.util.Map;

import cn.edu.zju.isst.net.AsyncWebServiceRunner;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.util.L;

public class CSTApi {

	// private static final String PREFIX = "http://www.zjucst.com";//正式服务器
	private static final String PREFIX = "http://yplan.cloudapp.net:8080/isst";// 测试服务器

	protected static void request(final String methodName, final String subUrl,
			final Map<String, String> params, RequestListener listener) {
		String url = PREFIX + subUrl;
		AsyncWebServiceRunner.request(methodName, url, params, listener);
		L.i("path:" + url);
	}
}
