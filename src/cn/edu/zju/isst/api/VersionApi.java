package cn.edu.zju.isst.api;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import cn.edu.zju.isst.net.RequestListener;

public class VersionApi extends CSTApi {
	private String url = "/isst/api/android/version";
	
	public VersionApi() {
		// TODO Auto-generated constructor stub
	}
	public void GetVersion(RequestListener listener)
	{
		Map<String,String>map = new HashMap<String,String>();
		TreeMap<String,String> TM = new TreeMap<String,String>();
		request("get",url,map,TM,listener);
	}
}
