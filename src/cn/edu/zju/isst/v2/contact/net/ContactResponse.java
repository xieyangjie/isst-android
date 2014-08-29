package cn.edu.zju.isst.v2.contact.net;

import android.content.Context;

import org.json.JSONObject;

import cn.edu.zju.isst.v2.contact.data.CSTAlumni;
import cn.edu.zju.isst.v2.contact.data.CSTAlumniDataDelegate;
import cn.edu.zju.isst.v2.data.CSTJsonParser;
import cn.edu.zju.isst.v2.net.CSTJsonResponse;

/**
 * Created by tan on 2014/8/25.
 */
public class ContactResponse extends CSTJsonResponse {
    private boolean clearDatabase;

    public ContactResponse(Context context, boolean clearDatabase) {
        super(context);
        this.clearDatabase = clearDatabase;
    }

    @Override
    public void onResponse(JSONObject response) {
        CSTAlumni alumni = (CSTAlumni) CSTJsonParser.parseJson(response, new CSTAlumni());
        //TODO must not pass null or empty user
        if (clearDatabase) {
            //CSTCampusEventDataDelegate.deleteAllCampusActivity(mContext);
        }
        CSTAlumniDataDelegate.saveAlumniList(mContext, alumni);
       // CSTCampusEventDataDelegate.saveCampusActivity(mContext, event);
    }
}
