/**
 * 
 */
package cn.edu.zju.isst.api;

import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.util.Judgement;
import cn.edu.zju.isst.util.L;

/**
 * 归档接口
 * 
 * @author theasir
 * 
 *         TODO WIP
 */
public class AlumniApi extends CSTApi {

	private static final String SUB_URL = "/api";

	public static void getUserList( Integer id,	String name,
			Integer gender,Integer grade,Integer classId,
			Integer majorId,Integer cityId,String company,
			RequestListener listener) {
		StringBuilder sb = new StringBuilder();
		sb.append(SUB_URL).append("/alumni?");

		if (!Judgement.isNullOrEmpty(id)) {
			sb.append("id=" + id + "&");
		}
		if (!Judgement.isNullOrEmpty(name)) {
			sb.append("name=" + name + "&");
		}
		if (!Judgement.isNullOrEmpty(gender)) {
			sb.append("gender=" + gender + "&");
		}
		if (!Judgement.isNullOrEmpty(grade)) {
			sb.append("grade=" + grade + "&");
		}
		if (!Judgement.isNullOrEmpty(classId)) {
			sb.append("classId=" + classId + "&");
		}
		if (!Judgement.isNullOrEmpty(majorId)) {
			sb.append("majorId=" + majorId + "&");
		}
		if (!Judgement.isNullOrEmpty(cityId)) {
			sb.append("cityId=" + cityId + "&");
		}
		if (!Judgement.isNullOrEmpty(company)) {
			sb.append("company=" + company + "&");
		}
		sb.deleteCharAt(sb.toString().length() - 1);

		L.i("yyy -- getUserList:" + sb.toString());

		request("GET", sb.toString(), null, listener);
	}
	
	public static void getClassesList( Integer id,	String name, RequestListener listener)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(SUB_URL).append("/classes?");
		
		if (!Judgement.isNullOrEmpty(id)) {
			sb.append("id=" + String.valueOf(id) + "&");
		}
		if (!Judgement.isNullOrEmpty(name)) {
			sb.append("name=" + name + "&");
		}
		
		L.i("yyy --- getClassesList:" + sb.toString());
		
		request("GET", sb.toString(), null, listener);
	}

	public static void getUser( Integer id,	RequestListener listener) {
		StringBuilder sb = new StringBuilder();
		sb.append(SUB_URL).append("/alumni");
		
		if (!Judgement.isNullOrEmpty(id))
		{
			sb.append("/" + id + "");
		}
		L.i("yyy --- getUser:" +sb.toString());
		request("GET", sb.toString(), null, listener);
	}
}
	
	
