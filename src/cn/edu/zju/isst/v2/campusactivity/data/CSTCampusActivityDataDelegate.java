package cn.edu.zju.isst.v2.campusactivity.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import cn.edu.zju.isst.v2.campusactivity.*;

/**
 * Created by lqynydyxf on 2014/8/5.
 */
public class CSTCampusActivityDataDelegate {
    public static CSTCampusActivity getCampusActivity(Cursor cursor) {
        CSTCampusActivity campusactivity = new CSTCampusActivity();

        campusactivity.id = cursor.getInt(cursor.getColumnIndex(CSTCampusActivityProvider.Columns.ID.key));
        campusactivity.title = cursor
                .getString(cursor.getColumnIndex(CSTCampusActivityProvider.Columns.TITLE.key));
        campusactivity.picture = cursor.getString(cursor.getColumnIndex(CSTCampusActivityProvider.Columns.PICTURE.key));
        campusactivity.description = cursor.getString(cursor.getColumnIndex(CSTCampusActivityProvider.Columns.DESCRIPTION.key));
        campusactivity.content = cursor.getString(cursor.getColumnIndex(CSTCampusActivityProvider.Columns.CONTENT.key));
        campusactivity.publishername = cursor.getString(cursor.getColumnIndex(CSTCampusActivityProvider.Columns.PUBLISHER_NAME.key));
        campusactivity.updatedAt = cursor.getInt(cursor.getColumnIndex(CSTCampusActivityProvider.Columns.UPDATEAT.key));
        campusactivity.startTime = cursor.getInt(cursor.getColumnIndex(CSTCampusActivityProvider.Columns.STARTTIME.key));
        campusactivity.expireTime = cursor.getInt(cursor.getColumnIndex(CSTCampusActivityProvider.Columns.EXPIRETIME.key));
        return campusactivity;
    }

    public static CSTCampusActivity getCampusActivity(Context context, String id) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver
                .query(CSTCampusActivityProvider.CONTENT_URI, null, CSTCampusActivityProvider.Columns.ID.key + " = ?",
                        new String[]{
                                id
                        }, null
                );
        if (cursor != null && cursor.moveToFirst()) {
            try {
                return getCampusActivity(cursor);
            } finally {
                cursor.close();
            }
        }
        return null;//Should throw exception to avoid null pointer?
    }
    public static void saveCampusActivity(Context context,CSTCampusActivity campusactivity) {
        ContentResolver resolver = context.getContentResolver();
        resolver.insert(CSTCampusActivityProvider.CONTENT_URI, getCampusActivityValue(campusactivity));
    }

    public static void deleteAllCampusActivity(Context context) {
        ContentResolver resolver = context.getContentResolver();
        resolver.delete(CSTCampusActivityProvider.CONTENT_URI, null, null);
    }
    private static ContentValues getCampusActivityValue(CSTCampusActivity campusactivity) {
        ContentValues values = new ContentValues();
        values.put(CSTCampusActivityProvider.Columns.ID.key, campusactivity.id);
        values.put(CSTCampusActivityProvider.Columns.TITLE.key,campusactivity.title);
        values.put(CSTCampusActivityProvider.Columns.PICTURE.key,campusactivity.picture);
        values.put(CSTCampusActivityProvider.Columns.DESCRIPTION.key,campusactivity.description);
        values.put(CSTCampusActivityProvider.Columns.CONTENT.key,campusactivity.content);
        values.put(CSTCampusActivityProvider.Columns.PUBLISHER_NAME.key,campusactivity.publishername);
        values.put(CSTCampusActivityProvider.Columns.UPDATEAT.key,campusactivity.updatedAt);
        values.put(CSTCampusActivityProvider.Columns.STARTTIME.key,campusactivity.startTime);
        values.put(CSTCampusActivityProvider.Columns.EXPIRETIME.key,campusactivity.expireTime);
        return values;
    }
}
