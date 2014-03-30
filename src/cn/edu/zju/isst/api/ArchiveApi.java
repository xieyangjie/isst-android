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
public class ArchiveApi extends CSTApi {

	private static final String SUB_URL = "/api/archives/";
	
	public static void getNewsList(Integer page, Integer pageSize, String keywords, RequestListener listener){
		getArchiveList(Category.CAMPUS, page, pageSize, keywords, listener);
	}
	
	public static void getArchiveList(Category category, Integer page, Integer pageSize, String keywords, RequestListener listener){
		StringBuilder sb = new StringBuilder();
		sb.append(SUB_URL).append("categories/").append(category.getSubUrl()).append("?");
		
		if(page != null){
			sb.append("page=" + String.valueOf(page) + "&");
		}
		if(pageSize != null){
			sb.append("pageSize=" + String.valueOf(pageSize) + "&");
		}
		if(keywords != null){
			sb.append("keywords" + keywords + "&");
		}
		sb.deleteCharAt(sb.toString().length() - 1);
		
		L.i("getArchiveList:" + sb.toString());
		
		request("GET", sb.toString(), null, listener);
	}
	
	public static void getArchiveDetail(int id, RequestListener listener){
		request("GET", SUB_URL + id, null, listener);
	}
}
