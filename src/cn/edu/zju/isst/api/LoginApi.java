/**
 * 
 */
package cn.edu.zju.isst.api;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.edu.zju.isst.db.User;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.util.L;

/**
 * @author theasir
 * 
 */
public class LoginApi extends CSTApi {

	private static final String SUB_URL = "/api/login";

	private static final char[] SECRET = "vq8ukG8MKrNC7XqsbIbd7PxvX81ufNz9"
			.toCharArray();

	public static void validate(String userName, String password,
			Double longitude, Double latitude, RequestListener listener) {
		Map<String, String> paramsMap = new ConcurrentHashMap<String, String>();
		paramsMap.put("username", userName);
		paramsMap.put("password", password);
		paramsMap.put(
				"token",
				getToken(
						String.valueOf(SECRET) + userName + password
								+ getTimeStamp()).toLowerCase(Locale.ENGLISH));
		paramsMap.put("timestamp", getTimeStamp());
		paramsMap.put("longitude", String.valueOf(longitude));
		paramsMap.put("latitude", String.valueOf(latitude));
		
		L.i("LoginToken", "token=" + paramsMap.get("token") + "&" + "timestamp="
				+ paramsMap.get("timestamp"));

		request("POST", SUB_URL, paramsMap, listener);
	}

	public static void update(User currentUser, Double longitude,
			Double latitude, RequestListener listener) {
		Map<String, String> paramsMap = new ConcurrentHashMap<String, String>();
		paramsMap.put("userId", String.valueOf(currentUser.getId()));
		paramsMap.put(
				"token",
				getToken(
						String.valueOf(SECRET)
								+ String.valueOf(currentUser.getId())
								+ currentUser.getPassword() + getTimeStamp())
						.toLowerCase(Locale.ENGLISH));
		paramsMap.put("timestamp", getTimeStamp());
		paramsMap.put("longitude", String.valueOf(longitude));
		paramsMap.put("latitude", String.valueOf(latitude));

		L.i("TEST", "token=" + paramsMap.get("token") + "&" + "timestamp="
				+ paramsMap.get("timestamp"));

		request("POST", SUB_URL + "/update", paramsMap, listener);
	}

	private static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}

	private static String getToken(String rawString) {
		return encryptWithMD5(rawString);
	}

	private static String encryptWithMD5(String str) {
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
