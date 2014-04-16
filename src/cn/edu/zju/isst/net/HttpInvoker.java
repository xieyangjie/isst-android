/**
 * 
 */
package cn.edu.zju.isst.net;

import static cn.edu.zju.isst.constant.Constants.HTTP_CONNECT_TIMEOUT;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import android.webkit.CookieManager;
import cn.edu.zju.isst.util.J;
import cn.edu.zju.isst.util.L;

/**
 * 前辈遗留，感谢
 * 
 * @deprecated
 * 
 */
public class HttpInvoker {

	public static String postRequest(final String url,
			final Map<String, String> params) throws InterruptedException,
			ExecutionException {
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {

					@Override
					public String call() throws Exception {
						StringBuilder sbParams = new StringBuilder();
						if (!J.isNullOrEmpty(params)) {
							for (Map.Entry<String, String> entry : params
									.entrySet()) {
								sbParams.append(entry.getKey()).append('=');
								sbParams.append(URLEncoder.encode(
										entry.getValue(), "UTF-8"));
								sbParams.append('&');
							}
							sbParams.deleteCharAt(sbParams.length() - 1);
						}
						byte[] entity = sbParams.toString().getBytes();

						URL postUrl = new URL(url);
						HttpURLConnection conn = (HttpURLConnection) postUrl
								.openConnection();
						conn.setDoOutput(true);
						conn.setDoInput(true);
						conn.setRequestMethod("POST");
						conn.setUseCaches(false);
						conn.setInstanceFollowRedirects(true);
						// conn.setConnectTimeout(CONNECT_TIMEOUT);
						conn.setRequestProperty("Content-Type",
								"application/x-www-form-urlencoded");
						// Gzip compression can be disabled by setting the
						// acceptable encodings in the request header:
						// conn.setRequestProperty("Accept-Encoding",
						// "identity");

						CookieManager cookieManager = CookieManager
								.getInstance();
						String cookie = cookieManager.getCookie(conn.getURL()
								.toString());
						if (!J.isNullOrEmpty(cookie)) {
							conn.setRequestProperty("Cookie", cookie);
						}

						L.i("HttpInvoker POST cookie: " + cookie);
						L.i("HttpInvoker Before POST getOutputStream!");

						conn.getOutputStream().write(entity);

						L.i("HttpInvoker ResponseCode: "
								+ conn.getResponseCode());

						if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
							List<String> cookieList = conn.getHeaderFields()
									.get("Set-Cookie");
							if (!J.isNullOrEmpty(cookieList)) {
								for (String cookieTemp : cookieList) {
									cookieManager.setCookie(conn.getURL()
											.toString(), cookieTemp);
								}
							}

							BufferedReader br = new BufferedReader(
									new InputStreamReader(conn.getInputStream()));
							StringBuilder sbResult = new StringBuilder();
							String line = br.readLine();
							while (!J.isNullOrEmpty(line)) {
								sbResult.append(line + "\n");
							}
							br.close();

							conn.disconnect();

							L.i(sbResult.toString());
							return sbResult.toString();
						}

						conn.disconnect();
						return null;
					}
				});
		new Thread(task).start();
		return task.get();
	}

	public static String getRequest(final String url)
			throws InterruptedException, ExecutionException {
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {

					@Override
					public String call() throws Exception {
						URL getUrl = new URL(url);
						HttpURLConnection conn = (HttpURLConnection) getUrl
								.openConnection();
						conn.setDoOutput(false);
						conn.setDoInput(true);
						conn.setRequestMethod("GET");
						// conn.setUseCaches(false);
						conn.setInstanceFollowRedirects(true);
						conn.setConnectTimeout(HTTP_CONNECT_TIMEOUT);
						conn.setRequestProperty("Content-Type",
								"application/x-www-form-urlencoded");

						CookieManager cookieManager = CookieManager
								.getInstance();
						String cookie = cookieManager.getCookie(conn.getURL()
								.toString());
						if (!J.isNullOrEmpty(cookie)) {
							conn.setRequestProperty("Cookie", cookie);
						}

						L.i("HttpInvoker GET cookie: " + cookie);
						L.i("HttpInvoker Before GET connect!");

						conn.connect();

						L.i("HttpInvoker ResponseCode: "
								+ conn.getResponseCode());

						if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
							List<String> cookieList = conn.getHeaderFields()
									.get("Set-Cookie");
							if (!J.isNullOrEmpty(cookieList)) {
								for (String cookieTemp : cookieList) {
									cookieManager.setCookie(conn.getURL()
											.toString(), cookieTemp);
								}
							}

							BufferedReader br = new BufferedReader(
									new InputStreamReader(conn.getInputStream()));
							StringBuilder sbResult = new StringBuilder();
							String line = br.readLine();
							while (!J.isNullOrEmpty(line)) {
								sbResult.append(line + "\n");
							}
							br.close();

							conn.disconnect();

							L.i(sbResult.toString());
							return sbResult.toString();
						}

						conn.disconnect();
						return null;
					}
				});
		new Thread(task).start();
		return task.get();
	}
}
