/**
 * 
 */
package cn.edu.zju.isst.api;

import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.util.L;

/**
 * @author theasir
 *
 */
public class RestaurantApi extends CSTApi{

	/**
	 * 接口子网址
	 */
	private static final String SUB_URL = "/api/restaurants";
	
	public static void getRestaurantList(Integer page, Integer pageSize,
			String keywords, RequestListener listener) {
		StringBuilder sb = new StringBuilder();
		sb.append(SUB_URL).append("?");

		if (page != null) {
			sb.append("page=" + String.valueOf(page) + "&");
		}
		if (pageSize != null) {
			sb.append("pageSize=" + String.valueOf(pageSize) + "&");
		}
		if (keywords != null) {
			sb.append("keywords" + keywords + "&");
		}
		sb.deleteCharAt(sb.toString().length() - 1);

		L.i("getRestaurantList:" + sb.toString());

		request("GET", sb.toString(), null, listener);
	}
}
