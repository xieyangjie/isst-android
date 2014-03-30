/**
 * 
 */
package cn.edu.zju.isst.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import cn.edu.zju.isst.util.L;

import android.os.Build;
import android.webkit.CookieManager;

import static cn.edu.zju.isst.constant.Constants.*;

/**
 * @author theasir
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
						if (params != null && !params.isEmpty()) {
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
//						conn.setConnectTimeout(CONNECT_TIMEOUT);
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
						if (cookie != null) {
							conn.setRequestProperty("Cookie", cookie);
						}

						L.i("HttpInvoker POST cookie: " + cookie);
						L.i("HttpInvoker Before POST getOutputStream!");

						// 下面的这段代码运行会出现这样的问题：登录时第一次调用HttpInvoker请求的话conn.getOutputStream()会抛出EOF异常，第二次请求通常是正常的。原因有待考证。
						// conn.connect();
						// OutputStream os = conn.getOutputStream();
						// os.write(entity);
						// os.close();

						conn.getOutputStream().write(entity);

						L.i("HttpInvoker ResponseCode: "
								+ conn.getResponseCode());

						if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
							List<String> cookieList = conn.getHeaderFields()
									.get("Set-Cookie");
							if (cookieList != null) {
								for (String cookieTemp : cookieList) {
									cookieManager.setCookie(conn.getURL()
											.toString(), cookieTemp);
								}
							}

							BufferedReader br = new BufferedReader(
									new InputStreamReader(conn.getInputStream()));
							StringBuilder sbResult = new StringBuilder();
							String line;
							while ((line = br.readLine()) != null) {
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
						conn.setConnectTimeout(CONNECT_TIMEOUT);
						conn.setRequestProperty("Content-Type",
								"application/x-www-form-urlencoded");

						CookieManager cookieManager = CookieManager
								.getInstance();
						String cookie = cookieManager.getCookie(conn.getURL()
								.toString());
						if (cookie != null) {
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
							if (cookieList != null) {
								for (String cookieTemp : cookieList) {
									cookieManager.setCookie(conn.getURL()
											.toString(), cookieTemp);
								}
							}

							BufferedReader br = new BufferedReader(
									new InputStreamReader(conn.getInputStream()));
							StringBuilder sbResult = new StringBuilder();
							String line;
							while ((line = br.readLine()) != null) {
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