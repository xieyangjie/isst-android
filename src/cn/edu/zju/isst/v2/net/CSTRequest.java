package cn.edu.zju.isst.v2.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;

import java.util.Map;

/**
 * Created by i308844 on 7/28/14.
 */
public abstract class CSTRequest<T> extends Request<T> {

    private Response.Listener<T> mListener;

    private Map<String, String> mParams;

    private Map<String, String> mHeaders;

    public CSTRequest(int method, String url, Map<String, String> params,
            NewCSTResponse<T> response) {
        super(method, url, response);
        this.mListener = response;
        this.mParams = params;
        this.mHeaders = CSTHttpUtil.getCookiesHeaders(url);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
