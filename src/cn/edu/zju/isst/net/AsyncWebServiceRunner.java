package cn.edu.zju.isst.net;

import java.util.Map;

import org.json.JSONObject;

import cn.edu.zju.isst.util.L;

public class AsyncWebServiceRunner {

	/**
	 * 请求数据，并在获取到数据后通过RequestListener将result回传给调用者
	 * 
	 * @param methodName
	 *            要调用的WebService方法名
	 * @param url
	 *            URL
	 * @param params
	 *            参数
	 * @param listener
	 *            回调对象
	 */
	public static void request(final String methodName, final String url,
			final Map<String, String> params, final RequestListener listener) {
		new Thread() {
			@Override
			public void run() {
				try {
					JSONObject result;
					String tempResult;
					if (methodName == "get") {
						// HttpUtil.getRequest(url);
						L.i("AsyncWebServiceRunner_____get");
						tempResult = HttpUtil.getRequest(url);
						result = new JSONObject(tempResult);
					} else {
						L.i("AsyncWebServiceRunner_____post");
						tempResult = HttpUtil.postRequest(url, params);
						result = new JSONObject(tempResult);
						L.i("result = " + result.toString());
						// result = new JSONObject(HttpUtil.postRequest(url,
						// params));
					}
					if (result.length() > 0)// ?有问题
					{
						listener.onComplete(result);

					} else {
						listener.onError(new Exception("NotConnected"));
					}
				} catch (Exception e) {
					listener.onError(e);
				}

			}
		}.start();

	}
}
