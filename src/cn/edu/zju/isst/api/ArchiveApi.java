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
public class ArchiveApi extends CSTApi {

	private static final String SUB_URL = "/api/archives/";

	/**
	 * 获取百科列表
	 * 
	 * @param page
	 *            页数
	 * @param pageSize
	 *            页面大小
	 * @param keywords
	 *            关键字
	 * @param listener
	 *            回调对象
	 */
	public static void getWikiList(Integer page, Integer pageSize,
			String keywords, RequestListener listener) {
		getArchiveList(ArchiveCategory.ENCYCLOPEDIA, page, pageSize, keywords, listener);
	}
	
	/**
	 * 获取归档列表
	 * 
	 * @param category
	 *            类别
	 * @param page
	 *            页数
	 * @param pageSize
	 *            页面大小
	 * @param keywords
	 *            关键字
	 * @param listener
	 *            回调对象
	 */
	public static void getArchiveList(ArchiveCategory category, Integer page,
			Integer pageSize, String keywords, RequestListener listener) {
		StringBuilder sb = new StringBuilder();
		sb.append(SUB_URL).append("categories/").append(category.getSubUrl())
				.append("?");

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

		L.i("getArchiveList:" + sb.toString());

		request("GET", sb.toString(), null, listener);
	}

	/**
	 * 获取归档详情
	 * 
	 * @param id
	 *            id
	 * @param listener
	 *            回调对象
	 */
	public static void getArchiveDetail(int id, RequestListener listener) {
		request("GET", SUB_URL + id, null, listener);
	}
}
