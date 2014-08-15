package cn.edu.zju.isst.v2.net;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.content.Context;

import cn.edu.zju.isst.constant.Constants;
import cn.edu.zju.isst.util.L;

/**
 * Created by i308844 on 7/28/14.
 */
public abstract class NewCSTResponse<T> implements Response.Listener<T>, Response.ErrorListener,
        CSTResponseStatusListener {

    protected Context mContext;

    protected NewCSTResponse(Context context) {
        this.mContext = context;
    }

    protected NewCSTResponse() {
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onSuccessStatus() {

    }

    @Override
    public Object onErrorStatus(CSTStatusInfo statusInfo) {
        //TODO
        return null;
    }

    protected void dispatchStatus(CSTStatusInfo statusInfo) {
        if (statusInfo.status == Constants.STATUS_REQUEST_SUCCESS) {
            onSuccessStatus();
        } else {
            onErrorStatus(statusInfo);
        }
    }
}
