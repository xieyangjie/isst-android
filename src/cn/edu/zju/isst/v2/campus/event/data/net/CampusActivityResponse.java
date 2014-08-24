package cn.edu.zju.isst.v2.campus.event.data.net;

import org.json.JSONObject;

import android.content.Context;

import cn.edu.zju.isst.v2.campus.event.data.data.CSTCampusEvent;
import cn.edu.zju.isst.v2.campus.event.data.data.CSTCampusEventDataDelegate;
import cn.edu.zju.isst.v2.data.CSTJsonParser;
import cn.edu.zju.isst.v2.net.CSTJsonResponse;
import cn.edu.zju.isst.v2.user.data.CSTUser;
import cn.edu.zju.isst.v2.user.data.CSTUserDataDelegate;

/**
 * Created by always on 22/08/2014.
 */
public class CampusActivityResponse extends CSTJsonResponse {

    private boolean clearDatabase;

    public CampusActivityResponse(Context context, boolean clearDatabase) {
        super(context);
        this.clearDatabase = clearDatabase;
    }

    @Override
    public void onResponse(JSONObject response) {
        CSTCampusEvent event = (CSTCampusEvent) CSTJsonParser.parseJson(response, new CSTCampusEvent());
        //TODO must not pass null or empty user
        if (clearDatabase) {
            CSTCampusEventDataDelegate.deleteAllCampusActivity(mContext);
        }
        CSTCampusEventDataDelegate.saveCampusActivity(mContext, event);
    }
}
