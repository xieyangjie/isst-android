package cn.edu.zju.isst.v2.activities.city.net;

import org.json.JSONObject;

import android.content.Context;

import cn.edu.zju.isst.v2.net.CSTJsonResponse;

/**
 * Created by always on 28/08/2014.
 */
public class CityActivityDetailResponse extends CSTJsonResponse{
    private boolean clearDatabase;

    protected CityActivityDetailResponse(Context context) {
        super(context);
    }

    @Override
    public void onResponse(JSONObject response) {
        super.onResponse(response);
    }
}
