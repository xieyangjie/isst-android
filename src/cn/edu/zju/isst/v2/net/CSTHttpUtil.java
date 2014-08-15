package cn.edu.zju.isst.v2.net;

import android.webkit.CookieManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import cn.edu.zju.isst.util.J;
import cn.edu.zju.isst.util.L;

/**
 * Created by i308844 on 7/28/14.
 */
public class CSTHttpUtil {

    public static Map<String, String> getCookiesHeaders(String url) {
        Map<String, String> headers = new HashMap<String, String>();
        CookieManager.getInstance().removeExpiredCookie();
        String cookieString = CookieManager.getInstance().getCookie(url);
        if (!J.isNullOrEmpty(cookieString)) {
            headers.put("Cookie", cookieString);
        }
        return headers;
    }

    public static void refreshCookies(String url, Map<String, String> headers) {
        String cookie = headers.get("Set-Cookie");
        if (!J.isNullOrEmpty(cookie)) {
            CookieManager.getInstance().setCookie(url, cookie);
        }
    }

    /**
     * 将参数转化为字节流（用于POST请求）
     *
     * @param params 参数
     * @return 字节流
     * @throws java.io.UnsupportedEncodingException 未处理异常
     */
    public static String paramsToString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder sbParams = new StringBuilder();
        if (!J.isNullOrEmpty(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (!J.isNullOrEmpty(entry.getValue())) {
                    sbParams.append(entry.getKey()).append('=');
                    sbParams.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                    sbParams.append('&');
                }
            }
            sbParams.deleteCharAt(sbParams.length() - 1);
        }

        L.i("Params", sbParams.toString());

        return sbParams.toString();
    }
}
