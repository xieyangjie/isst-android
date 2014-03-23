package cn.edu.zju.isst.api;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import android.R.bool;

import cn.edu.zju.isst.net.RequestListener;

public class SpittlesApi extends CSTApi {

	private static String subUrl = "/isst/api/users/";
	
	public SpittlesApi() {
		// TODO Auto-generated constructor stub
		//subUrl += userID+"/spittles"; 
	}
	
	//发布吐槽
	public void PublicSpittles(int userID, String content, RequestListener listener)
	{
		Map<String,String> map = new HashMap<String,String>();
		TreeMap<String,String> TM = new TreeMap<String,String>();
		String url = subUrl + userID + "/spittles";
		map.put("content", content);
		TM.put("content", content);
		TM.put("userId", String.valueOf(userID));
		request("post",url,map,TM,listener);
	}
	
	//删除：
	//URL: /isst/api/users/{user_id}/spittles/{spittle_id}, DELETE
	public void DeleteSpittles(int userID, int spittleID, RequestListener listener)
	{
		String url = subUrl + userID+"/spittles/" + spittleID;
		Map<String,String> map = new HashMap<String,String>();
		TreeMap<String,String> TM = new TreeMap<String,String>();
		map.put("_method","delete");
		request("post",url,map,TM,listener);
	}
	
	//获取列表：
	//URL: /isst/api/users/{userId}/spittles?id=int&page=int&pageSize=int, GET
	public void GetSpittleslist(int userID,int id,int page, int pageSize, RequestListener listener)
	{
		String url = subUrl + userID + "/spittles?";
		if(id>0)
			url +="id="+ id + "&page=" + page + "&pageSize="+pageSize;
		else 
			url +="page=" + page + "&pageSize="+pageSize;
		Map<String,String> map = new HashMap<String,String>();
		TreeMap<String,String> TM = new TreeMap<String,String>();
		TM.put("userId", String.valueOf(userID));
		TM.put("page", String.valueOf(page));
		TM.put("pageSize", String.valueOf(pageSize));
		if(id > 0)
			TM.put("id", String.valueOf(id));
		
		request("get",url,map,TM,listener);
		
	}
	
	//点赞：
	//URL: /isst/api/users/{user_id}/spittles/{spittle_id}/likes, POST{is_like:0|1}
	public void ChangeLikeState(int userID, int spittleID, int likeState, RequestListener listener)
	{
		String url = subUrl + userID + "/spittles/" +  spittleID + "/likes";
		Map<String,String>map = new HashMap<String, String>();
		TreeMap<String,String> TM = new TreeMap<String,String>();
		map.put("isLike",Integer.toString(likeState));
		TM.put("isLike",Integer.toString(likeState));
		TM.put("user_id",Integer.toString(userID));
		TM.put("spittle_id",Integer.toString(spittleID));
		//map.put("_method")
		request("post",url,map,TM,listener);
	}
	
	//获取最赞的列表：
	//URL: /isst/api/users/{userId}/spittles/likes?isLike=0|1&count=int, GET
	public void GetLikesList(int userID, int isLike, int count ,RequestListener listner)
	{
		String url = subUrl + userID + "/spittles/likes?isLike=" + isLike +"&count=" + count;
		Map<String,String>map = new HashMap<String,String>();
		TreeMap<String,String> TM = new TreeMap<String,String>();
		TM.put("userId", String.valueOf(userID));
		TM.put("isLike", String.valueOf(isLike));
		TM.put("count", String.valueOf(count));
		request("get",url,map,TM,listner);
	}
}
