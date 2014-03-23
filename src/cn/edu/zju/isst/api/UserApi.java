package cn.edu.zju.isst.api;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import cn.edu.zju.isst.net.RequestListener;;

public class UserApi extends CSTApi {

	
	private static String subUrl = "/isst/api/users/";
	public UserApi() {
		// TODO Auto-generated constructor stub			
	}
	
	//private static String subUrl = "";//测试用
	
	//验证用户
	public void validateUser(String username, String password, RequestListener listener)
	{
		Map<String,String> map = new HashMap<String,String>();
		TreeMap<String,String> TM = new TreeMap<String,String>();
		String url = subUrl +"validation";
		map.put("name", username);
		map.put("password", password);
		TM.put("name", username);
		TM.put("password", password);

		request("post", url, map,TM, listener);
	}
	
	//获取用户信息
	public void getUserInfo(int userID,RequestListener listener)
	{
		String url = subUrl + userID;
		Map<String,String>map = new HashMap<String,String>();
		TreeMap<String,String> TM = new TreeMap<String,String>();
		TM.put("userId", String.valueOf(userID));
		request("get",url,map,TM,listener);
	}
	
	//请求修改用户信息
	public void modifyUserInfo(String userID,String NickName,RequestListener listener)
	{
		String url = subUrl + userID;
		Map<String,String>map = new HashMap<String,String>();
		TreeMap<String,String> TM = new TreeMap<String,String>();
		map.put("nickname", NickName);
		map.put("_method", "put");
		TM.put("nickname", NickName);
		TM.put("userId", String.valueOf(userID));
		request("post",url,map,TM,listener);
	}
}
