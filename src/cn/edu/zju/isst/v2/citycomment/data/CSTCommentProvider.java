package cn.edu.zju.isst.v2.citycomment.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import cn.edu.zju.isst.v2.db.CSTProvider;
import cn.edu.zju.isst.v2.db.SimpleTableProvider;

/**
 * Created by lqynydyxf on 2014/8/10.
 */
public class CSTCommentProvider extends SimpleTableProvider {
    public enum Columns {
        ID("id"),
        CONTENT("content"),
        CREATED_AT("created_at"),
        CSTUSER("user");
        public String key;

        private Columns(String key) {
            this.key = key;
        }
    }

    public static final String TABLE_NAME = "citycomment";
    public static final Uri CONTENT_URI = CSTProvider.CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
    public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY, "
            + Columns.ID.key + " INTEGER, "
            + Columns.CONTENT.key + " VARCHAR(255), "
            + Columns.CREATED_AT.key + " INTEGER, "
            + Columns.CSTUSER.key + " BLOB, "
            + "UNIQUE (" + Columns.ID.key + ") ON CONFLICT REPLACE)";

    public CSTCommentProvider(Context context) {
        super(context);
    }

    @Override
    protected Uri getBaseContentUri() {
        return CSTProvider.CONTENT_URI;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }
}
