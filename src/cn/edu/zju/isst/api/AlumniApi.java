/**
 *
 */
package cn.edu.zju.isst.api;

import android.os.Message;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.util.J;
import cn.edu.zju.isst.util.L;
import cn.edu.zju.isst.v2.net.Response.GlobalDataCacheResponse;
import cn.edu.zju.isst.v2.net.Response.VersionResponse;
import cn.edu.zju.isst.v2.net.VolleyImpl;

/**
 * 归档接口
 *
 * @author theasir
 *         <p/>
 *         TODO WIP
 */
public class AlumniApi extends CSTApi {

    private static final String SUB_URL = "/api";

    public static void getUserList(int id, String name, int gender, int grade,
            int classId, String major, int cityId, String company,
            RequestListener listener) {
        StringBuilder sb = new StringBuilder();
        sb.append(SUB_URL).append("/alumni");

        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("id", "" + id);
        paramsMap.put("name", name);
        paramsMap.put("gender", "" + gender);
        paramsMap.put("grade", "" + grade);
        paramsMap.put("classId", "" + classId);
        paramsMap.put("major", "" + major);
        paramsMap.put("cityId", "" + cityId);
        paramsMap.put("company", company);

        request("GET", sb.toString(), paramsMap, listener);
    }

    public static void getClassesList(GlobalDataCacheResponse listener) {
        StringBuilder sb = new StringBuilder();
        sb.append(SUB_URL).append("/classes");

        L.i("yyy --- getClassesList:" + sb.toString());

//        request("GET", sb.toString(), null, listener);

        VolleyImpl.requestJsonObject(Request.Method.GET,
                "http://www.cst.zju.edu.cn/isst" + sb, null,listener);
    }

    public static void getMajorList(GlobalDataCacheResponse listener) {
        StringBuilder sb = new StringBuilder();
        sb.append(SUB_URL).append("/majors");

        L.i("yyy --- getMajorList:" + sb.toString());

//        request("GET", sb.toString(), null, listener);

        VolleyImpl.requestJsonObject(Request.Method.GET,
                "http://www.cst.zju.edu.cn/isst" + sb, null,listener);
    }

    public static void getCityList(GlobalDataCacheResponse listener) {
        StringBuilder sb = new StringBuilder();
        sb.append(SUB_URL).append("/cities");

        L.i("yyy --- getCityList:" + sb.toString());

//        request("GET", sb.toString(), null, listener);

        VolleyImpl.requestJsonObject(Request.Method.GET,
                "http://www.cst.zju.edu.cn/isst" + sb, null,listener);
    }

    public static void getUser(int id, RequestListener listener) {
        StringBuilder sb = new StringBuilder();
        sb.append(SUB_URL).append("/alumni").append("/" + id);

        L.i("yyy --- getUser:" + sb.toString());

        request("GET", sb.toString(), null, listener);
    }
}
