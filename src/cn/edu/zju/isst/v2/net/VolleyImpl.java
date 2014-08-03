package cn.edu.zju.isst.v2.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by i308844 on 2014/7/22.
 */
public class VolleyImpl {

    private static String TAG_JSON = "json_request";

    public static void requestJsonObject(final int method, final String url,
            final Map<String, String> params, final CSTResponse<JSONObject> jsonResponse) {
        CSTRequest<JSONObject> jsonRequest = new CSTRequest<JSONObject>(method, url, params,
                jsonResponse) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(new JSONObject(jsonString),
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };

        VolleyRequestManager.getInstance().addToRequestQueue(jsonRequest);
    }
}
