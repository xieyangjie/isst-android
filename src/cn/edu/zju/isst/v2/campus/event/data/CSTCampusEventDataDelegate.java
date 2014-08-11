package cn.edu.zju.isst.v2.campus.event.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * Created by lqynydyxf on 2014/8/5.
 */
public class CSTCampusEventDataDelegate {

    public static CSTCampusEvent getCampusEvent(Cursor cursor) {
        CSTCampusEvent campusevent = new CSTCampusEvent();

        campusevent.id = cursor
                .getInt(cursor.getColumnIndex(CSTCampusEventProvider.Columns.ID.key));
        campusevent.title = cursor
                .getString(cursor.getColumnIndex(CSTCampusEventProvider.Columns.TITLE.key));
        campusevent.picture = cursor
                .getString(cursor.getColumnIndex(CSTCampusEventProvider.Columns.PICTURE.key));
        campusevent.description = cursor
                .getString(cursor.getColumnIndex(CSTCampusEventProvider.Columns.DESCRIPTION.key));
        campusevent.content = cursor
                .getString(cursor.getColumnIndex(CSTCampusEventProvider.Columns.CONTENT.key));
        campusevent.pubName = cursor.getString(
                cursor.getColumnIndex(CSTCampusEventProvider.Columns.PUBLISHER_NAME.key));
        campusevent.updatedAt = cursor
                .getInt(cursor.getColumnIndex(CSTCampusEventProvider.Columns.UPDATEAT.key));
        campusevent.startTime = cursor
                .getInt(cursor.getColumnIndex(CSTCampusEventProvider.Columns.STARTTIME.key));
        campusevent.expireTime = cursor
                .getInt(cursor.getColumnIndex(CSTCampusEventProvider.Columns.EXPIRETIME.key));
        return campusevent;
    }

    public static CSTCampusEvent getCampusEvent(Context context, String id) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver
                .query(CSTCampusEventProvider.CONTENT_URI, null,
                        CSTCampusEventProvider.Columns.ID.key + " = ?",
                        new String[]{
                                id
                        }, null
                );
        if (cursor != null && cursor.moveToFirst()) {
            try {
                return getCampusEvent(cursor);
            } finally {
                cursor.close();
            }
        }
        return null;//Should throw exception to avoid null pointer?
    }

    public static void saveCampusActivity(Context context, CSTCampusEvent campusevent) {
        ContentResolver resolver = context.getContentResolver();
        resolver.insert(CSTCampusEventProvider.CONTENT_URI, getCampusActivityValue(campusevent));
    }

    public static void deleteAllCampusActivity(Context context) {
        ContentResolver resolver = context.getContentResolver();
        resolver.delete(CSTCampusEventProvider.CONTENT_URI, null, null);
    }

    private static ContentValues getCampusActivityValue(CSTCampusEvent campusevent) {
        ContentValues values = new ContentValues();
        values.put(CSTCampusEventProvider.Columns.ID.key, campusevent.id);
        values.put(CSTCampusEventProvider.Columns.TITLE.key, campusevent.title);
        values.put(CSTCampusEventProvider.Columns.PICTURE.key, campusevent.picture);
        values.put(CSTCampusEventProvider.Columns.DESCRIPTION.key, campusevent.description);
        values.put(CSTCampusEventProvider.Columns.CONTENT.key, campusevent.content);
        values.put(CSTCampusEventProvider.Columns.PUBLISHER_NAME.key, campusevent.pubName);
        values.put(CSTCampusEventProvider.Columns.UPDATEAT.key, campusevent.updatedAt);
        values.put(CSTCampusEventProvider.Columns.STARTTIME.key, campusevent.startTime);
        values.put(CSTCampusEventProvider.Columns.EXPIRETIME.key, campusevent.expireTime);
        return values;
    }
}
