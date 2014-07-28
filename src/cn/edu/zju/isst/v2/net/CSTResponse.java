package cn.edu.zju.isst.v2.net;

import com.android.volley.NetworkResponse;

/**
 * Created by i308844 on 7/28/14.
 */
public class CSTResponse extends NetworkResponse {

    public CSTResponse(NetworkResponse response) {
        super(response.statusCode, response.data, response.headers, response.notModified);
    }
}
