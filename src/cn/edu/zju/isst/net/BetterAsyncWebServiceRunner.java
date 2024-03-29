/**
 *
 */
package cn.edu.zju.isst.net;

import org.json.JSONObject;

import android.webkit.CookieManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.zju.isst.util.J;
import cn.edu.zju.isst.util.L;

/**
 * 异步请求类
 *
 * @author theasir
 *         <p/>
 *         TODO 空值处理，数据流或对象为空时的处理
 */
public class BetterAsyncWebServiceRunner {

    /**
     * 单个实例
     */
    private static BetterAsyncWebServiceRunner INSTANCE = new BetterAsyncWebServiceRunner();

    /**
     * 私有构造器，防止初始化新的实例
     */
    private BetterAsyncWebServiceRunner() {
    }

    /**
     * 单例模式
     *
     * @return 单个实例
     */
    public static BetterAsyncWebServiceRunner getInstance() {
        return INSTANCE;
    }

    /**
     * 请求数据，并在获取到数据后通过RequestListener将result回传给调用者
     *
     * @param methodName 要调用的WebService方法名
     * @param url        URL
     * @param params     参数
     * @param listener   回调对象
     */
    public void request(final String methodName, final String url,
            final Map<String, String> params, final RequestListener listener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    // JSONObject result = null;
                    // String resultString = null;
                    // CSTResponse response = null;
                    // if (methodName.equalsIgnoreCase("GET")) {
                    // L.i("BetterAsyncWebServiceRunner_____get");
                    // response = BetterHttpInvoker.getInstance().get(
                    // new URL(url), getHeaders(url));
                    // } else if (methodName.equalsIgnoreCase("POST")) {
                    // L.i("BetterAsyncWebServiceRunner_____post");
                    // response = BetterHttpInvoker.getInstance().post(
                    // new URL(url), getHeaders(url),
                    // paramsToBytes(params));
                    // } else {
                    // L.i("BetterAsyncWebServiceRunner Unsupported Method: "
                    // + (Judgement.isNullOrEmpty(methodName) ? "null"
                    // : methodName));
                    // }
                    //
                    // if (!Judgement.isNullOrEmpty(response)
                    // && response.getStatus() == HttpURLConnection.HTTP_OK) {
                    // refreshCookies(url, response.getHeaders());
                    // resultString = readByte(response.getBody());
                    // if (!Judgement.isNullOrEmpty(resultString)) {
                    // result = new JSONObject(resultString);
                    // }
                    // }
                    JSONObject result = null;
                    CSTResponse response = responseOfRequest(methodName, url,
                            params);
                    if (!J.isNullOrEmpty(response)
                            && response.getStatus() == HttpURLConnection.HTTP_OK) {
                        refreshCookies(url, response.getHeaders());
                    }
                    result = resultOfResponse(response);

                    if (!J.isNullOrEmpty(result)
                            && !J.isNullOrEmpty(response)) {
                        L.i(result.toString());
                        listener.onComplete(result);
                    } else if (!J.isNullOrEmpty(response)) {
                        listener.onHttpError(response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onException(e);
                }

            }
        }.start();
    }

    public CSTResponse responseOfRequest(final String methodName,
            final String url, final Map<String, String> params)
            throws MalformedURLException, IOException {
        CSTResponse response = null;
        if (methodName.equalsIgnoreCase("GET")) {
            L.i("BetterAsyncWebServiceRunner_____get");
            response = BetterHttpInvoker.getInstance().get(new URL(url),
                    getHeaders(url));
        } else if (methodName.equalsIgnoreCase("POST")) {
            L.i("BetterAsyncWebServiceRunner_____post");
            response = BetterHttpInvoker.getInstance().post(new URL(url),
                    getHeaders(url), paramsToString(params).getBytes());
        } else {
            L.i("BetterAsyncWebServiceRunner Unsupported Method: "
                    + (J.isNullOrEmpty(methodName) ? "null"
                    : methodName));
        }
        return response;
    }

    public JSONObject resultOfResponse(final CSTResponse response)
            throws Exception {
        JSONObject result = null;
        if (!J.isNullOrEmpty(response)
                && response.getStatus() == HttpURLConnection.HTTP_OK) {
            String resultString = readByte(response.getBody());
            if (!J.isNullOrEmpty(resultString)) {
                result = new JSONObject(resultString);
            }
        }
        return result;
    }

    /**
     * 将参数转化为字节流（用于POST请求）
     *
     * @param params 参数
     * @return 字节流
     * @throws UnsupportedEncodingException 未处理异常
     */
    public String paramsToString(Map<String, String> params)
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

    /**
     * 获取Http Headers，此处的主要目的是获取cookie
     *
     * @param url URL
     * @return Http Headers
     */
    private Map<String, List<String>> getHeaders(String url) {
        if (J.isNullOrEmpty(url)) {
            return null;
        }
        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        List<String> cookieList = new ArrayList<String>();
        String cookieString = CookieManager.getInstance().getCookie(url);
        if (!J.isNullOrEmpty(cookieString)) {
            String[] cookieArray = cookieString.split(";");
            for (String cookie : cookieArray) {
                cookieList.add(cookie);
            }
            headers.put("Cookie", cookieList);
        }

        return headers;

    }

    /**
     * 刷新cookie
     *
     * @param url     URL
     * @param headers Http Headers
     */
    private void refreshCookies(String url, Map<String, List<String>> headers) {
        List<String> cookieList = headers.get("Set-Cookie");
        if (!J.isNullOrEmpty(cookieList)) {
            for (String cookie : cookieList) {
                CookieManager.getInstance().setCookie(url, cookie);
            }
        }
    }

    /**
     * 读取字节流
     *
     * @param body 目标字节流
     * @return 字符串
     * @throws Exception 未处理异常
     */
    private String readByte(byte[] body) throws Exception {
        return new String(body, "UTF-8");
    }
}
