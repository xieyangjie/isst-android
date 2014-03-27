/**
 * 
 */
package cn.edu.zju.isst.api;

import java.util.HashMap;
import java.util.Map;

import cn.edu.zju.isst.net.RequestListener;

/**
 * @author theasir
 * 
 */
public class LoginApi extends CSTApi {

	private static final String SUB_URL = "/api/login/";
	
	public static void validate(String userName, String password, Double longitude, Double latitude, RequestListener listener){
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("username", userName);
		paramsMap.put("password", password);
		paramsMap.put("longitude", String.valueOf(longitude));
		paramsMap.put("latitude", String.valueOf(latitude));
		
		request("post", SUB_URL, paramsMap, listener);
	}
}
