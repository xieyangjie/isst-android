package cn.edu.zju.isst.v2.net.Response;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import cn.edu.zju.isst.v2.net.CSTStatusInfo;
import cn.edu.zju.isst.v2.net.NewCSTResponse;

/**
 * Created by always on 13/08/2014.
 */
public class UpdateLoginResponse extends NewCSTResponse<JSONObject> {

    protected UpdateLoginResponse(Context context) {
        super(context);
    }


    @Override
    public void onResponse(JSONObject jsonObject) {
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //TODO handle error
        super.onErrorResponse(error);
    }

    @Override
    public void onSuccessStatus() {
        //TODO handle status == 0
        super.onSuccessStatus();
    }

    @Override
    public Object onErrorStatus(CSTStatusInfo statusInfo) {
        //TODO handle status != 0
        return super.onErrorStatus(statusInfo);
    }

    @Override
    protected void dispatchStatus(CSTStatusInfo statusInfo) {
        //TODO dispatch status
        super.dispatchStatus(statusInfo);
    }
}

