/**
 *
 */
package cn.edu.zju.isst.api;

import com.android.volley.Request;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import cn.edu.zju.isst.db.User;
import cn.edu.zju.isst.net.BetterAsyncWebServiceRunner;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.util.J;
import cn.edu.zju.isst.util.L;
import cn.edu.zju.isst.v2.net.CSTHttpUtil;
import cn.edu.zju.isst.v2.net.Response.LoginResponse;
import cn.edu.zju.isst.v2.net.Response.UpdateLoginResponse;
import cn.edu.zju.isst.v2.net.Response.VersionResponse;
import cn.edu.zju.isst.v2.net.VolleyImpl;

/**
 * 登录接口
 *
 * @author theasir
 */
public class LoginApi extends CSTApi {

    /**
     * 接口子网址
     */
    private static final String SUB_URL = "/api/login";

    /**
     * MD5加密密钥
     */
    private static final char[] SECRET = "vq8ukG8MKrNC7XqsbIbd7PxvX81ufNz9"
            .toCharArray();

    /**
     * 登录验证
     *
     * @param userName  用户名
     * @param password  密码
     * @param longitude 经度
     * @param latitude  纬度
     * @param listener  回调对象
     */
    public static void validate(String userName, String password,
            double longitude, double latitude, LoginResponse listener) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("username", userName);
        paramsMap.put("password", password);
        paramsMap.put(
                "token",
                getToken(
                        String.valueOf(SECRET) + userName + password
                                + getTimeStamp()
                ).toLowerCase(Locale.ENGLISH)
        );
        paramsMap.put("timestamp", getTimeStamp());
        paramsMap.put("longitude", String.valueOf(longitude));
        paramsMap.put("latitude", String.valueOf(latitude));

        L.i("yyy:" + "username=" + paramsMap.get("username") + "&password=" + paramsMap
                .get("password") + "&" + "token=" + paramsMap.get("token") + "&"
                + "timestamp=" + paramsMap.get("timestamp"));
        L.i("LoginToken", "token=" + paramsMap.get("token") + "&"
                + "timestamp=" + paramsMap.get("timestamp"));

        VolleyImpl.requestJsonObject(Request.Method.POST,
                "http://www.cst.zju.edu.cn/isst/api/login", paramsMap,
                listener);
//        request("POST", SUB_URL, paramsMap, listener);

    }

    /**
     * 模拟（更新）登录
     *
     * @param currentUser 当前用户
     * @param longitude   经度
     * @param latitude    纬度
     * @param listener    回调对象
     */
    public static void update(User currentUser, double longitude,
            double latitude, UpdateLoginResponse listener) {
        Map<String, String> paramsMap = new HashMap<String, String>();
        String paramsMapString = null;
        paramsMap.put("userId", String.valueOf(currentUser.getId()));
        paramsMap.put(
                "token",
                getToken(
                        String.valueOf(SECRET)
                                + String.valueOf(currentUser.getId())
                                + currentUser.getPassword() + getTimeStamp()
                )
                        .toLowerCase(Locale.ENGLISH)
        );
        paramsMap.put("timestamp", getTimeStamp());
        paramsMap.put("longitude", String.valueOf(longitude));
        paramsMap.put("latitude", String.valueOf(latitude));

        L.i("" + currentUser.getId());

        L.i("TEST", "token=" + paramsMap.get("token") + "&" + "timestamp="
                + paramsMap.get("timestamp"));
        try {
            paramsMapString = (J.isNullOrEmpty(paramsMap) ? ""
                    : ("?" + CSTHttpUtil.paramsToString(paramsMap)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        request("POST", SUB_URL + "/update", paramsMap, listener);
        VolleyImpl.requestJsonObject(Request.Method.GET,
                "http://www.cst.zju.edu.cn/isst" + SUB_URL + "/update" + paramsMapString, null,
                listener);
    }

    /**
     * 获取当前时间戳
     *
     * @return 时间戳
     */
    private static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 获取token
     *
     * @param rawString 原始字符串
     * @return 加密token
     */
    private static String getToken(String rawString) {
        return encryptWithMD5(rawString);
    }

    /**
     * MD5加密方法
     *
     * @param str 原始字符串
     * @return 加密字符串
     */
    private static String encryptWithMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            // 32位加密
            return buf.toString();
            // 16位的加密
            // return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }
}
