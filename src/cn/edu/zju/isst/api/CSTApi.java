package cn.edu.zju.isst.api;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import android.util.Log;

import cn.edu.zju.isst.net.AsyncWebServiceRunner;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.util.L;

public abstract class CSTApi {

	private static final String SECRET_WORD = "vq8ukG8MKrNC7XqsbIbd7PxvX81ufNz9";
	// /**
	// * 测试环境与正式环境调换~~~
	// */
	// private static final String PREFIX = "http://www.zjucst.com";
	private static final String PREFIX = "http://yplan.cloudapp.net:8080";// 测试使用

	protected void request(final String methodName, final String subUrl,
			final Map<String, String> params, final TreeMap<String, String> TM,
			RequestListener listener) {
		String url = PREFIX + subUrl;

		if (url.contains("?")) {
			url = url + "&token=" + getToken(TM);
		} else {
			url = url + "?token=" + getToken(TM);
		}
		AsyncWebServiceRunner.request(methodName, url, params, listener);
		L.i("path:" + url);
		Log.v("demo", url);
	}

	/**
	 * 获取当前时间戳
	 * 
	 * @return
	 */
	private String getTimestamp() {
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * 获得token
	 * 
	 * @param TM
	 * @return
	 */
	private String getToken(TreeMap<String, String> TM) {

		String timestamp = getTimestamp();
		String res = SECRET_WORD + timestamp;
		Iterator it = TM.keySet().iterator();
		while (it.hasNext()) {
			res = res + TM.get(it.next());
		}
		return toMD5(res) + "&expire=" + timestamp;

		// return res;
	}

	/**
	 * MD5加密类
	 * 
	 * @param str
	 *            要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String toMD5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] byteDigest = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < byteDigest.length; offset++) {
				i = byteDigest[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位加密
			return buf.toString();
			// 16位的加密
			// return buf.toString().substring(8, 24);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

	}
}
