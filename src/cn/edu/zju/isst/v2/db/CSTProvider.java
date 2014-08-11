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

import cn.edu.zju.isst.v2.campusevent.data.CSTCampusEventProvider;
import cn.edu.zju.isst.v2.city.data.CSTCityProvider;
import cn.edu.zju.isst.v2.citycomment.data.CSTCommentProvider;
import cn.edu.zju.isst.v2.cityevent.data.CSTCityEventProvider;
import cn.edu.zju.isst.v2.cityparticipant.data.CSTCityParticipantProvider;
import cn.edu.zju.isst.v2.publisher.data.CSTPublisherProvider;
import cn.edu.zju.isst.v2.user.data.CSTUserProvider;

/**
 * Created by i308844 on 7/28/14.
 */
public class CSTProvider extends ContentProvider {

    private static final int TABLE_USER_CODE = 1;
    private static final int TABLE_CAMPUSEvent_CODE = 2;
    private static final int TABLE_CITY_CODE = 3;
    private static final int TABLE_PUBLISHER_CODE=4;
    private static final int TABLE_CITYEVENT_CODE=5;
    private static final int TABLE_CITYPARTICIPANT_CODE=6;
    private static final int TABLE_COMMENT_CODE=7;

    private static final String AUTHORITY = "cn.edu.zju.isst.v2.db.cstprovider";

    private static final String DATABASE_NAME = "isst_database.db";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static final int VER_03_ALPHA = 1;

    private static final int DB_VERSION = VER_03_ALPHA;

    private Map<String, Provider> mProviderMap = new HashMap<>();

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, CSTUserProvider.TABLE_NAME, TABLE_USER_CODE);
        sURIMatcher.addURI(AUTHORITY, CSTCampusEventProvider.TABLE_NAME, TABLE_CAMPUSEvent_CODE);
        sURIMatcher.addURI(AUTHORITY, CSTCityProvider.TABLE_NAME, TABLE_CITY_CODE);
        sURIMatcher.addURI(AUTHORITY, CSTPublisherProvider.TABLE_NAME,TABLE_PUBLISHER_CODE);
        sURIMatcher.addURI(AUTHORITY, CSTCityEventProvider.TABLE_NAME,TABLE_CITYEVENT_CODE);
        sURIMatcher.addURI(AUTHORITY, CSTCityParticipantProvider.TABLE_NAME,TABLE_CITYPARTICIPANT_CODE);
        sURIMatcher.addURI(AUTHORITY, CSTCommentProvider.TABLE_NAME,TABLE_COMMENT_CODE);
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
        mProviderMap.
                put(CSTCampusEventProvider.TABLE_NAME, new CSTCampusEventProvider(getContext()));
        mProviderMap.put(CSTCityProvider.TABLE_NAME, new CSTCityProvider(getContext()));
        mProviderMap.put(CSTPublisherProvider.TABLE_NAME,new CSTPublisherProvider(getContext()));
        mProviderMap.put(CSTCityEventProvider.TABLE_NAME,new CSTCityEventProvider(getContext()));
        mProviderMap.put(CSTCityParticipantProvider.TABLE_NAME,new CSTCityParticipantProvider(getContext()));
        mProviderMap.put(CSTCommentProvider.TABLE_NAME,new CSTCommentProvider(getContext()));
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
            case TABLE_CAMPUSEvent_CODE:
                return CSTCampusEventProvider.TABLE_NAME;
            case TABLE_CITY_CODE:
                return CSTCityProvider.TABLE_NAME;
            case TABLE_PUBLISHER_CODE:
                return CSTPublisherProvider.TABLE_NAME;
            case TABLE_CITYEVENT_CODE:
                return CSTCityEventProvider.TABLE_NAME;
            case TABLE_CITYPARTICIPANT_CODE:
                return CSTCityParticipantProvider.TABLE_NAME;
            case TABLE_COMMENT_CODE:
                return CSTCommentProvider.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
