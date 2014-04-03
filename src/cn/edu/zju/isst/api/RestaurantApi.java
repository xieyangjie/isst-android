/**
 * 
 */
package cn.edu.zju.isst.api;

import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.util.Judgement;
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

	public static void getRestaurantList(Integer page, Integer pageSize,
			String keywords, RequestListener listener) {
		StringBuilder sb = new StringBuilder();
		sb.append(SUB_URL).append("?");

		if (!Judgement.isNullOrEmpty(page)) {
			sb.append("page=" + String.valueOf(page) + "&");
		}
		if (!Judgement.isNullOrEmpty(pageSize)) {
			sb.append("pageSize=" + String.valueOf(pageSize) + "&");
		}
		if (!Judgement.isNullOrEmpty(keywords)) {
			sb.append("keywords" + keywords + "&");
		}
		sb.deleteCharAt(sb.toString().length() - 1);

		L.i("getRestaurantList:" + sb.toString());

		request("GET", sb.toString(), null, listener);
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
