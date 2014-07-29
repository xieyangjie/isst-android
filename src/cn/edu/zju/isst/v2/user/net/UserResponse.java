package cn.edu.zju.isst.v2.user.net;

import com.android.volley.Response;

import android.content.Context;

import java.io.IOException;

import cn.edu.zju.isst.v2.data.CSTJsonParser;
import cn.edu.zju.isst.v2.model.CSTRawParsedJsonObject;
import cn.edu.zju.isst.v2.user.data.CSTUser;
import cn.edu.zju.isst.v2.user.data.CSTUserDataDelegate;

/**
 * Created by theasir on 7/29/14.
 */
public class UserResponse implements Response.Listener<byte[]> {

    private Context mContext;

    private boolean clearDatabase;

    public UserResponse(Context context, boolean clearDatabase) {
        this.mContext = context;
        this.clearDatabase = clearDatabase;
    }

    @Override
    public void onResponse(byte[] bytes) {
        CSTUser user = new CSTUser();
        try {
            CSTRawParsedJsonObject rawParsedJsonObject = CSTJsonParser
                    .readByte(bytes, CSTRawParsedJsonObject.class);
            user = CSTJsonParser
                    .readJsonObject(rawParsedJsonObject.getJsonObjectBody(), CSTUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (clearDatabase) {
            CSTUserDataDelegate.deleteAllUsers(mContext);
        }
        CSTUserDataDelegate.saveUser(mContext, user);
    }
}
