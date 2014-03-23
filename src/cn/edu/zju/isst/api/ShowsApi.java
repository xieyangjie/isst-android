package cn.edu.zju.isst.api;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import cn.edu.zju.isst.net.RequestListener;

public class ShowsApi extends CSTApi {

	private String subUrl = "/isst/api/";
	public ShowsApi() {
		// TODO Auto-generated constructor stub
	}
	public void GetShowsList(RequestListener listener,String userId)
	{
		String url = subUrl + "users/" + userId + "/shows";
		Map<String,String> map = new HashMap<String,String>();
		TreeMap<String,String> TM = new TreeMap<String,String>();
		TM.put("userId", userId);
		request("get",url,map,TM,listener);
	}
	//投票：
//	URL: /isst/api/users/{user_id}/shows/{show_id}/votes, POST

	public void  ShowsVote(String userId, String showId, RequestListener listener)
	{
		String url = subUrl + "users/"+ userId + "/shows/" + showId +"/votes";
		Map<String,String>map = new HashMap<String,String>();
		TreeMap<String,String> TM = new TreeMap<String,String>();
		TM.put("userId", userId);
		TM.put("showId", showId);
		request("post",url,map,TM,listener);
	}
}
