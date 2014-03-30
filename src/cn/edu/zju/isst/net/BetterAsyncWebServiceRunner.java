/**
 * 
 */
package cn.edu.zju.isst.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;

import android.webkit.CookieManager;

import cn.edu.zju.isst.util.L;

/**
 * @author theasir
 * 
 */
public class BetterAsyncWebServiceRunner {

	private static BetterAsyncWebServiceRunner INSTANCE = new BetterAsyncWebServiceRunner();

	private BetterAsyncWebServiceRunner() {
	}

	public static BetterAsyncWebServiceRunner getInstance() {
		return INSTANCE;
	}

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
	public void request(final String methodName, final String url,
			final Map<String, String> params, final RequestListener listener) {
		new Thread() {
			@Override
			public void run() {
				try {
					JSONObject result = null;
					String resultString;
					CSTResponse response = null;
					if (methodName.equalsIgnoreCase("GET")) {
						L.i("AsyncWebServiceRunner_____get");
						response = BetterHttpInvoker.getInstance().get(
								new URL(url), getHeaders(url));
					} else if (methodName.equalsIgnoreCase("POST")) {
						L.i("AsyncWebServiceRunner_____post");
						response = BetterHttpInvoker.getInstance().post(
								new URL(url), getHeaders(url),
								paramsToBytes(params));
					} else {
						// result = null;
					}

					if (response.getStatus() == HttpURLConnection.HTTP_OK) {
						refreshCookies(url, response.getHeaders());
						resultString = readByte(response.getBody());
						result = new JSONObject(resultString);
					}

					if (result != null)// ?有问题
					{
						listener.onComplete(result);

					} else {
						listener.onError(new Exception(
								"Not Connected OR Unsupport Method"));
					}
				} catch (Exception e) {
					listener.onError(e);
				}

			}
		}.start();
	}

	private Map<String, List<String>> getHeaders(String url) {
		Map<String, List<String>> headers = new ConcurrentHashMap<String, List<String>>();
		List<String> cookieList = new ArrayList<String>();
		String cookieString = CookieManager.getInstance().getCookie(url);
		if (cookieString != null) {
			String[] cookieArray = cookieString.split(";");
			for (String cookie : cookieArray) {
				cookieList.add(cookie);
			}
			headers.put("Cookie", cookieList);
		}

		return headers;

	}

	private void refreshCookies(String url, Map<String, List<String>> headers) {
		List<String> cookieList = headers.get("Set-Cookie");
		if (cookieList != null) {
			for (String cookie : cookieList) {
				CookieManager.getInstance().setCookie(url, cookie);
			}
		}
	}

	private static byte[] paramsToBytes(Map<String, String> params)
			throws UnsupportedEncodingException {
		StringBuilder sbParams = new StringBuilder();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				sbParams.append(entry.getKey()).append('=');
				sbParams.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
				sbParams.append('&');
			}
			sbParams.deleteCharAt(sbParams.length() - 1);
		}
		return sbParams.toString().getBytes();
	}

	private static String readByte(byte[] body) throws Exception {
		return new String(body, "UTF-8");
	}
}
