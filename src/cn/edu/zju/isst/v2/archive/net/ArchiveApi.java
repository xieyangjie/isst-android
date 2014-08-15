/**
 *
 */
package cn.edu.zju.isst.v2.archive.net;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cn.edu.zju.isst.api.ArchiveCategory;
import cn.edu.zju.isst.api.CSTApi;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.util.J;
import cn.edu.zju.isst.util.L;
import cn.edu.zju.isst.v2.net.CSTHttpUtil;
import cn.edu.zju.isst.v2.net.Response.ArchiveDetailResponse;
import cn.edu.zju.isst.v2.net.Response.ArchiveListResponse;
import cn.edu.zju.isst.v2.net.VolleyImpl;

/**
 * 归档接口
 *
 * @author theasir
 *         <p/>
 *         TODO WIP
 */
public class ArchiveApi extends CSTApi {

    private static final String SUB_URL = "/api/archives/";

    /**
     * 获取百科列表
     *
     * @param page     页数
     * @param pageSize 页面大小
     * @param keywords 关键字
     * @param listener 回调对象
     */
    public static void getWikiList(int page, int pageSize,
            String keywords, ArchiveListResponse listener) {
        getArchiveList(ArchiveCategory.ENCYCLOPEDIA, page, pageSize, keywords,
                listener);
    }

    /**
     * 获取归档列表
     *
     * @param category 类别
     * @param page     页数
     * @param pageSize 页面大小
     * @param keywords 关键字
     * @param listener 回调对象
     */
    public static void getArchiveList(ArchiveCategory category, int page,
            int pageSize, String keywords, ArchiveListResponse listener) {
        L.i("API---getArchiveList----enter");
        StringBuilder sb = new StringBuilder();
        sb.append(SUB_URL).append("categories/").append(category.getSubUrl());
        String paramsMapString = null;

        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("page", "" + page);
        paramsMap.put("pageSize", "" + pageSize);
        paramsMap.put("keywords", keywords);

        try {
            paramsMapString = (J.isNullOrEmpty(paramsMap) ? ""
                    : ("?" + CSTHttpUtil.paramsToString(paramsMap)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        VolleyImpl.requestJsonObject(Request.Method.GET,
                "http://www.cst.zju.edu.cn/isst" + sb.toString() + paramsMapString, null,
                listener);
//        request("GET", sb.toString(), paramsMap, listener);
    }

    /**
     * 获取归档详情
     *
     * @param id       id
     * @param listener 回调对象
     */
    public static void getArchiveDetail(int id, ArchiveDetailResponse listener) {
//        request("GET", SUB_URL + id, null, listener);
        L.i("getArchiveDetail" + id);
        VolleyImpl.requestJsonObject(Request.Method.GET,
                "http://www.cst.zju.edu.cn/isst" + SUB_URL + id, null,
                listener);
    }
}
