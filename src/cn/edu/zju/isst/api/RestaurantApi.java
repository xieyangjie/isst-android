/**
 * 
 */
package cn.edu.zju.isst.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.util.J;
import cn.edu.zju.isst.util.L;

/**
 * @author theasir
 * 
 */
public class RestaurantApi extends CSTApi {

	/**
	 * 接口子网址
	 */
	private static final String SUB_URL = "/api/restaurants";

	public static void getRestaurantList(int page, int pageSize,
			String keywords, RequestListener listener) {
		Map<String, String> paramsMap = new ConcurrentHashMap<String, String>();
		paramsMap.put("page", "" + page);
		paramsMap.put("pageSize", "" + pageSize);
		paramsMap.put("keywords", keywords);

		request("GET", SUB_URL, paramsMap, listener);
	}

	/**
	 * 获取商家详情
	 * 
	 * @param id
	 *            id
	 * @param listener
	 *            回调对象
	 */
	public static void getRestaurantDetail(int id, RequestListener listener) {
		request("GET", SUB_URL + "/" + id, null, listener);
	}

	public static void getRestaurantMenu(int id, RequestListener listener) {
		request("GET", SUB_URL + "/" + id + "/menus", null, listener);
	}
}
