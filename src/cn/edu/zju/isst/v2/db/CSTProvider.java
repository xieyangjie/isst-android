package cn.edu.zju.isst.v2.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

import cn.edu.zju.isst.v2.user.data.CSTUserProvider;

/**
 * Created by i308844 on 7/28/14.
 */
public class CSTProvider extends ContentProvider {

    private static final int TABLE_USER_CODE = 1;

    private static final String AUTHORITY = "cn.edu.zju.isst.v2.db.cstprovider";

    private static final String DATABASE_NAME = "isst_database.db";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static final int VER_03_ALPHA = 1;

    private static final int DB_VERSION = VER_03_ALPHA;

    private Map<String, Provider> mProviderMap = new HashMap<>();

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, CSTUserProvider.TABLE_NAME, TABLE_USER_CODE);
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        private DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            for (Provider provider : mProviderMap.values()) {
                provider.onCreate(db);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            int version = oldVersion;

            switch (oldVersion) {

            }

            if (version != DB_VERSION) {
                for (Provider provider : mProviderMap.values()) {
                    provider.resetContents(db);
                }
            }
        }
    }

    private DatabaseHelper mDatabaseHelper;

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DatabaseHelper(getContext());
        mProviderMap
                .put(CSTUserProvider.TABLE_NAME, new CSTUserProvider(getContext()));

        SQLiteDatabase writableDatabase = mDatabaseHelper.getWritableDatabase();
        for (Provider provider : mProviderMap.values()) {
            provider.setDBRef(writableDatabase);
        }
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        return getProvider(uri).query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        return null;//not used for now
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return getProvider(uri).insert(uri, values);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return getProvider(uri).delete(uri, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return getProvider(uri).update(uri, values, selection, selectionArgs);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        return getProvider(uri).bulkInsert(uri, values);
    }

    private Provider getProvider(Uri uri) {
        Provider provider = mProviderMap.get(getTableName(uri));
        if (provider == null) {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return provider;
    }

    public String getTableName(Uri uri) {
        switch (sURIMatcher.match(uri)) {
            case TABLE_USER_CODE:
                return CSTUserProvider.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
