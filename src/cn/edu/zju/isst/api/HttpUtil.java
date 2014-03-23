package cn.edu.zju.isst.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class HttpUtil {
	// 创建HttpClient对象
	public static HttpClient httpClient = new DefaultHttpClient();

	/**
	 * 
	 * @param url
	 *            发送请求的URL
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static String getRequest(final String url) throws Exception {
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						// 创建HttpGet对象。
						HttpGet get = new HttpGet(url);
						synchronized (httpClient) {
							//请求超时
							httpClient.getParams().setIntParameter(
									CoreConnectionPNames.CONNECTION_TIMEOUT, 7000);
							// 发送GET请求
							HttpResponse httpResponse = httpClient.execute(get);
							// 如果服务器成功地返回响应
							if (httpResponse.getStatusLine().getStatusCode() == 200) {
								// 获取服务器响应字符串
								String result = EntityUtils
										.toString(httpResponse.getEntity());
								System.out.println(result);
								return result;
							}
							return null;
						}
					}
				});
		new Thread(task).start();
		return task.get();
	}

	/**
	 * @param url
	 *            发送请求的URL
	 * @param params
	 *            请求参数
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static String postRequest(final String url,
			final Map<String, String> rawParams) throws Exception {
		// System.out.println("post has been quest");
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						// 创建HttpPost对象。
						HttpPost post = new HttpPost(url);
						// 如果传递参数个数比较多的话可以对传递的参数进行封装
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						for (String key : rawParams.keySet()) {
							// 封装请求参数
							params.add(new BasicNameValuePair(key, rawParams
									.get(key)));
						}
						// 设置请求参数
						post.setEntity(new UrlEncodedFormEntity(params, "utf8"));
						// 发送POST请求
						//请求超时
						httpClient.getParams().setIntParameter(
								CoreConnectionPNames.CONNECTION_TIMEOUT, 7000);
						HttpResponse httpResponse = httpClient.execute(post);
						// 如果服务器成功地返回响应
						System.out.println("HttpUtil___post is ok");
						if (httpResponse.getStatusLine().getStatusCode() == 200) {
							System.out.println("post response");
							// 获取服务器响应字符串
							String result = EntityUtils.toString(httpResponse
									.getEntity());
							System.out.println(result);
							return result;
						}
						return null;
					}
				});
		new Thread(task).start();
		return task.get();
	}
}
