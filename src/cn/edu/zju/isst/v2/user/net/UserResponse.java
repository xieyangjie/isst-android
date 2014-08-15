package cn.edu.zju.isst.v2.user.net;

import org.json.JSONObject;

import android.content.Context;

import cn.edu.zju.isst.util.L;
import cn.edu.zju.isst.v2.data.CSTJsonParser;
import cn.edu.zju.isst.v2.net.NewCSTResponse;
import cn.edu.zju.isst.v2.user.data.CSTUser;
import cn.edu.zju.isst.v2.user.data.CSTUserDataDelegate;

/**
 * Created by theasir on 7/29/14.
 */
public class UserResponse extends NewCSTResponse<JSONObject> {

    private boolean clearDatabase;

    public UserResponse(Context context, boolean clearDatabase) {
        super(context);
        this.clearDatabase = clearDatabase;
    }

    @Override
    public void onResponse(JSONObject response) {
        CSTUser user = (CSTUser) CSTJsonParser.parseJson(response, new CSTUser());
        dispatchStatus(user.getStatusInfo());

        L.d("user", user.toString());

        //TODO must not pass null or empty user
        if (clearDatabase) {
            CSTUserDataDelegate.deleteAllUsers(mContext);
        }
        CSTUserDataDelegate.saveUser(mContext, user);
    }
}
