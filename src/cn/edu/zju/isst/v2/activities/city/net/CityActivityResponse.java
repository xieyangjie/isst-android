package cn.edu.zju.isst.v2.activities.city.net;

import org.json.JSONObject;

import android.content.Context;

import cn.edu.zju.isst.v2.activities.city.event.data.CSTCityEvent;
import cn.edu.zju.isst.v2.activities.city.event.data.CSTCityEventDataDelegate;
import cn.edu.zju.isst.v2.data.CSTJsonParser;
import cn.edu.zju.isst.v2.net.CSTJsonResponse;

/**
 * Created by always on 25/08/2014.
 */
public class CityActivityResponse extends CSTJsonResponse {

    private boolean clearDatabase;

    public CityActivityResponse(Context context, boolean clearDatabase) {
        super(context);
        this.clearDatabase = clearDatabase;
    }

    @Override
    public void onResponse(JSONObject response) {
        CSTCityEvent event = (CSTCityEvent) CSTJsonParser
                .parseJson(response, new CSTCityEvent());
        //TODO must not pass null or empty user
        if (clearDatabase) {
            CSTCityEventDataDelegate.deleteAllCityevent(mContext);
        }
        CSTCityEventDataDelegate.getCityEventListValues(mContext, event);
    }
}
