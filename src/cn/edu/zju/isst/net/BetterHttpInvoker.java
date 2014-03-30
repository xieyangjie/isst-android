/**
 * 
 */
package cn.edu.zju.isst.net;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static cn.edu.zju.isst.constant.Constants.*;

import cn.edu.zju.isst.util.L;

/**
 * @author theasir
 * 
 */
public class BetterHttpInvoker {

	private static BetterHttpInvoker INSTANCE = new BetterHttpInvoker();

	private BetterHttpInvoker() {
	}

	public static BetterHttpInvoker getInstance() {
		return INSTANCE;
	}

	public CSTResponse get(URL uri, Map<String, List<String>> headers) {
		GET get = new GET(uri, headers);
		return getOrPost(get);
	}

	public CSTResponse post(URL uri, Map<String, List<String>> headers,
			byte[] body) {
		POST post = new POST(uri, headers, body);
		return getOrPost(post);
	}

	private CSTResponse getOrPost(Request request) {
		HttpURLConnection conn = null;
		CSTResponse response = null;
		System.setProperty("http.keepAlive", "false");
		try {
			conn = (HttpURLConnection) request.getUri().openConnection();
			L.i("BetterHttpInvoker openConnection URL = "
					+ request.getUri().toString());

			if (!request.getHeaders().isEmpty()) {
				for (String header : request.getHeaders().keySet()) {
					for (String value : request.getHeaders().get(header)) {
						conn.addRequestProperty(header, value);
					}
				}
			}

			if (request instanceof POST) {
				byte[] payload = ((POST) request).getBody();
				conn.setDoOutput(true);
				conn.setFixedLengthStreamingMode(payload.length);
//				conn.setDoInput(true);
//				conn.setRequestMethod("POST");
//				conn.setUseCaches(false);
//				conn.setInstanceFollowRedirects(true);
//				conn.setRequestProperty("Content-Type",
//						"application/x-www-form-urlencoded");
				conn.setConnectTimeout(CONNECT_TIMEOUT);
				conn.setReadTimeout(10000);

				L.i("BetterHttpInvoker Before POST getOutputStream()");

				conn.getOutputStream().write(payload);
				final int status = conn.getResponseCode();

				L.i("BetterHttpInvoker After POST getResponseCode() = "
						+ status);
				if (status != HttpURLConnection.HTTP_OK) {
					response = new CSTResponse(status,
							new ConcurrentHashMap<String, List<String>>(), conn
									.getResponseMessage().getBytes());
				}
			}

			if (response == null) {
				L.i("BetterHttpInvoker Before getInputStream()");
				BufferedInputStream in = new BufferedInputStream(
						conn.getInputStream());
				byte[] body = readStream(in);
				L.i("BetterHttpInvoker After getResponseCode() = "
						+ conn.getResponseCode());
				response = new CSTResponse(conn.getResponseCode(),
						conn.getHeaderFields(), body);
			}

		} catch (IOException e) {
			if (L.isDebuggable()) {
				e.printStackTrace();
			}
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return response;
	}

	private static byte[] readStream(InputStream in) throws IOException {
		byte[] buf = new byte[1024];
		int count = 0;
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		while ((count = in.read(buf)) != -1) {
			out.write(buf, 0, count);
		}
		return out.toByteArray();
	}

	private class Request {
		private URL uri;
		private Map<String, List<String>> headers;

		public Request(URL uri, Map<String, List<String>> headers) {
			this.uri = uri;
			this.headers = headers;
		}

		/**
		 * @return the uri
		 */
		public URL getUri() {
			return uri;
		}

		/**
		 * @return the headers
		 */
		public Map<String, List<String>> getHeaders() {
			return headers;
		}

	}

	private class POST extends Request {
		private byte[] body;

		public POST(URL uri, Map<String, List<String>> headers, byte[] body) {
			super(uri, headers);
			this.body = body;
		}

		/**
		 * @return the body
		 */
		public byte[] getBody() {
			return body;
		}

	}

	private class GET extends Request {
		public GET(URL uri, Map<String, List<String>> headers) {
			super(uri, headers);
		}
	}
}
