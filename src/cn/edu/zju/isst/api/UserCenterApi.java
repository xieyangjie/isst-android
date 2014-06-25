
package cn.edu.zju.isst.api;

import java.util.HashMap;
import java.util.Map;

import cn.edu.zju.isst.db.User;
import cn.edu.zju.isst.net.RequestListener;

/**
 * @author xyj
 * 
 */
public class UserCenterApi extends CSTApi {

	private static final String SUB_URL = "/api/users";

	public static void  publishRecommend(int id,String title,String content,String company,String position ,int cityId,RequestListener listener)
	{
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("id",""+id);
		paramsMap.put("title", title);
		paramsMap.put("content", content);
		paramsMap.put("company", company);
		paramsMap.put("position", position);
		paramsMap.put("cityId",""+cityId);
		
		request("POST", SUB_URL + "/jobs/recommend", paramsMap, listener);
	}
}
