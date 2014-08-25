package cn.edu.zju.isst.v2.archive.net;

import org.json.JSONObject;

import android.content.Context;

import cn.edu.zju.isst.constant.Constants;
import cn.edu.zju.isst.v2.archive.data.CSTArchive;
import cn.edu.zju.isst.v2.archive.data.CSTArchiveDataDelegate;
import cn.edu.zju.isst.v2.data.CSTJsonParser;
import cn.edu.zju.isst.v2.net.CSTJsonResponse;

/**
 * Created by i308844 on 8/25/14.
 */
public class ArchiveResponse extends CSTJsonResponse {

    private boolean isClearDatabase;

    public ArchiveResponse(Context context, boolean isClearDatabase) {
        super(context);
        this.isClearDatabase = isClearDatabase;
    }

    @Override
    public void onResponse(JSONObject response) {
        CSTArchive archive = (CSTArchive) CSTJsonParser.parseJson(response, new CSTArchive());
        if (archive.getStatusInfo().status != Constants.STATUS_REQUEST_SUCCESS) {
            onErrorStatus(archive.getStatusInfo());
            return;
        }
        if (isClearDatabase) {
            //TODO unhandled clearDatabase flag for caching mechanism
        }
        CSTArchiveDataDelegate.saveArchiveList(mContext, archive);
    }
}
